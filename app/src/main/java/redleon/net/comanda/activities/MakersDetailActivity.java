package redleon.net.comanda.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MakersDetailListAdapter;
import redleon.net.comanda.loaders.MakersDetailLoader;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

public class MakersDetailActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,  SwipeRefreshLayout.OnRefreshListener{
    private Integer commandId;
    public static final String COMMAND_ID = "net.redleon.COMMAND_ID";
    ListView listView;
    private SwipeRefreshLayout swipeLayout;
    MakersDetailListAdapter makersDetailListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers_detail);

        Intent intent = getIntent();

        setCommandId(intent.getIntExtra(COMMAND_ID, 0));
        makersDetailListAdapter = new MakersDetailListAdapter(this);
        listView = (ListView) findViewById(android.R.id.list);

        listView.setAdapter(makersDetailListAdapter);
        listView.setOnItemClickListener(this);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_makers_detail_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        MakersDetailLoader makersDetailLoader = new MakersDetailLoader(makersDetailListAdapter);
//        makersDetailLoader.setDishId(getCommandId());
//        makersDetailLoader.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_makers_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MakersDetailActivity me = this;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_dispatch_command) {
            RequestParams params = Network.makeAuthParams(this);
            params.put("command_id", getCommandId());
            final ProgressDialog progressBar;
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);
            progressBar.setMessage("Consultado información...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setIndeterminate(true);
            progressBar.show();
            HttpClient.post("/commands/completed/" + getCommandId(), params, new JsonHttpResponseHandler() {
                //@Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressBar.dismiss();
                    try{

                        me.onRefresh();
                        String sResponse = response.getString("status");

                        if (sResponse.equals("ok")) {
                        Toast.makeText(me, "Se ha despachado la comanda", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(me, sResponse, Toast.LENGTH_SHORT).show();
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


            },getBaseContext());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final MakersDetailActivity me = this;
        RequestParams params = Network.makeAuthParams(this);
        params.put("command_id", getCommandId());
        final ProgressDialog progressBar;
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();
        HttpClient.post("/order_dishes/setready/" + ((Dish) parent.getItemAtPosition(position)).getOrder_dishes_id(),params, new JsonHttpResponseHandler() {
           // @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.dismiss();
                try{
                    String sResponse = response.getString("resultado");
                    if (sResponse.equals("ok")) {
                        Toast.makeText(me, "Se ha despachado el platillo", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(me, sResponse, Toast.LENGTH_SHORT).show();
                    }
                    me.onRefresh();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                progressBar.dismiss();
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());



    }

    @Override
    public void onStart(){
        super.onStart();

      // System.out.println(">>>>>>>>>>>>>>>>>OnStart");

    }

    @Override
    public void onResume(){
        super.onResume();
        onRefresh();
        System.out.println(">>>>>>>>>>>>>>>>>>OnResume");


    }


    @Override
    public void onRefresh() {
        MakersDetailLoader makersDetailLoader = new MakersDetailLoader(makersDetailListAdapter);
        makersDetailLoader.setDishId(getCommandId());
        makersDetailLoader.execute();
        swipeLayout.setRefreshing(false);

    }
}
