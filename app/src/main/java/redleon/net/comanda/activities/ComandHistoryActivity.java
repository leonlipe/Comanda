package redleon.net.comanda.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.ComandsHistoryAdapter;
import redleon.net.comanda.loaders.ComandasHistoryLoader;
import redleon.net.comanda.loaders.ComandasListLoader;
import redleon.net.comanda.model.Extra;
import redleon.net.comanda.model.OrderDishesData;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;
import redleon.net.comanda.utils.SwipeListViewActivity;

public class ComandHistoryActivity extends SwipeListViewActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView mListView;
    private SwipeRefreshLayout swipeLayout;
    private ComandsHistoryAdapter comandsHistoryAdapter;
    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";
    public final static String DINER_ID = "net.redleon.DINER_ID";

    private Integer serviceId;
    private Integer dinerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comand_history);
        mListView = (ListView) findViewById(android.R.id.list);
        swipeLayout = (SwipeRefreshLayout) this.findViewById(R.id.commands_history_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        Intent intent = getIntent();
        setServiceId(intent.getIntExtra(SERVICE_ID, 0));
        setDinerId(intent.getIntExtra(DINER_ID, 0));
        comandsHistoryAdapter = new ComandsHistoryAdapter(this);
        setListAdapter(comandsHistoryAdapter);
        ComandasHistoryLoader comandasHistoryLoader = new ComandasHistoryLoader(comandsHistoryAdapter);
        comandasHistoryLoader.setDinerId(getDinerId());
        comandasHistoryLoader.execute();
        setTitle("Historial de comandas");


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comand_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getDinerId() {
        return dinerId;
    }

    public void setDinerId(Integer dinerId) {
        this.dinerId = dinerId;
    }

    @Override
    public ListView getListView() {
        return mListView;
    }

    @Override
    public void getSwipeItem(boolean isRight, int position) {
        //Toast.makeText(this,
        //        "Swipe to " + (isRight ? "right" : "left") + " direction",
        //        Toast.LENGTH_SHORT).show();
        final ComandHistoryActivity me = this;
        OrderDishesData dish = (OrderDishesData) mListView.getItemAtPosition(position);
        HttpClient.post("/diners/remove_dish/" + dish.getId().toString(), Network.makeAuthParams(me), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("status");
                    // Do something with the response
                    if (sResponse.equals("ok")) {
                        Toast.makeText(me,
                                "El platillo se quitó de la orden",
                                Toast.LENGTH_SHORT).show();
                        me.onRefresh();
                    }else{
                        Toast.makeText(me,
                                "Ocurrió un error: "+response.getString("reason"),
                                Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());
    }

    @Override
    public void onItemClickListener(ListAdapter adapter, int position) {
       // Toast.makeText(this, "Single tap on item position " + position,
        //        Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {

                ComandasHistoryLoader comandasHistoryLoader = new ComandasHistoryLoader(comandsHistoryAdapter);
                comandasHistoryLoader.setDinerId(getDinerId());
                comandasHistoryLoader.execute();
                swipeLayout.setRefreshing(false);

    }

}
