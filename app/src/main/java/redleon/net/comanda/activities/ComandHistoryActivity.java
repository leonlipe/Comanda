package redleon.net.comanda.activities;

import android.app.ListActivity;
import android.content.Intent;
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
import redleon.net.comanda.model.Extra;
import redleon.net.comanda.model.OrderDishesData;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.SwipeListViewActivity;

public class ComandHistoryActivity extends SwipeListViewActivity {
    private ListView mListView;

    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";
    public final static String DINER_ID = "net.redleon.DINER_ID";

    private Integer serviceId;
    private Integer dinerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comand_history);
        mListView = (ListView) findViewById(android.R.id.list);
        Intent intent = getIntent();
        setServiceId(intent.getIntExtra(SERVICE_ID, 0));
        setDinerId(intent.getIntExtra(DINER_ID, 0));
        ComandsHistoryAdapter comandsHistoryAdapter = new ComandsHistoryAdapter(this);
        setListAdapter(comandsHistoryAdapter);
        ComandasHistoryLoader comandasHistoryLoader = new ComandasHistoryLoader(comandsHistoryAdapter);
        comandasHistoryLoader.setDinerId(getDinerId());
        comandasHistoryLoader.execute();


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

        OrderDishesData dish = (OrderDishesData) mListView.getItemAtPosition(position);
        HttpClient.get("/diners/remove_dish/", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("response");
                    // Do something with the response
                    if (sResponse.equals("ok")) {
                        JSONArray extras = response.getJSONArray("data");
                        // mExtras = new Extra[extras.length()];

                        for (int x = 0; x < extras.length(); x++) {
                            mExtras.add(new Extra(extras.getJSONObject(x).getInt("id"), extras.getJSONObject(x).getString("key"), extras.getJSONObject(x).getString("description")));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


            }
        });
    }

    @Override
    public void onItemClickListener(ListAdapter adapter, int position) {
        Toast.makeText(this, "Single tap on item position " + position,
                Toast.LENGTH_SHORT).show();
    }

}
