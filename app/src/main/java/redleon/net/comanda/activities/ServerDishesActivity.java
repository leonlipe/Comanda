package redleon.net.comanda.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import redleon.net.comanda.R;
import redleon.net.comanda.adapters.ServedDishesAdapter;
import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.loaders.ServedDishesLoader;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;
import redleon.net.comanda.utils.SwipeListViewActivity;

public class ServerDishesActivity extends SwipeListViewActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView mListView;
    private SwipeRefreshLayout swipeLayout;
    private ServedDishesAdapter serverDishesAdapter;
    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";
    public final static String DINER_ID = "net.redleon.DINER_ID";
    private Integer serviceId;
    private Integer dinerId;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = new ProgressDialog(this);

        setContentView(R.layout.activity_server_dishes);
        mListView = (ListView) findViewById(android.R.id.list);
        swipeLayout = (SwipeRefreshLayout) this.findViewById(R.id.served_dishes_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        Intent intent = getIntent();
        setServiceId(intent.getIntExtra(SERVICE_ID, 0));
        setDinerId(intent.getIntExtra(DINER_ID, 0));
        serverDishesAdapter = new ServedDishesAdapter(this);
        setListAdapter(serverDishesAdapter);
        ServedDishesLoader servedDishesLoader = new ServedDishesLoader(serverDishesAdapter);
        servedDishesLoader.setServiceId(getServiceId());
        servedDishesLoader.execute();
        setTitle("Platillos preparados.");
        Button btnServeAll = (Button) findViewById(R.id.btn_serve_all);
        btnServeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serve_all();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_served_dishes, menu);
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

    }

    @Override
    public void onItemClickListener(ListAdapter adapter, int position) {
        // Toast.makeText(this, "Single tap on item position " + position,
        //        Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {

        ServedDishesLoader servedDishesLoader = new ServedDishesLoader(serverDishesAdapter);
        servedDishesLoader.setServiceId(getServiceId());
        servedDishesLoader.execute();
        swipeLayout.setRefreshing(false);

    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        progressBar.dismiss();
    }
    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        progressBar.dismiss();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        progressBar.dismiss();
    }


    /**
     * Enviar todas las comandas
     */
    public void serve_all() {

        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();

        final Activity me = this;


        HttpClient.post("/order_dishes/serve_all/" + serviceId, Network.makeAuthParams(me), new JsonHttpResponseHandler() {
            // @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.dismiss();
                // Pull out the first event on the public timeline

                try {

                    String sResponse = response.getString("status");
                    // Do something with the response
                    //System.out.println(response.getJSONObject("service").getInt("id"));
                    if (sResponse.equals("ok")) {
                        Toast.makeText(me,
                                "Los platillos se marcaron como seervidos",
                                Toast.LENGTH_SHORT).show();

                       onRefresh();
                    }else{
                        Toast.makeText(me,
                                sResponse,
                                Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                progressBar.dismiss();
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },me.getBaseContext());

    }

}