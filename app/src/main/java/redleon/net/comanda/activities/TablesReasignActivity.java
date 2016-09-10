package redleon.net.comanda.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.dialogs.ExtraIngredientDialog;
import redleon.net.comanda.dialogs.TableNumberDialog;
import redleon.net.comanda.loaders.TablesListLoader;
import redleon.net.comanda.model.Extra;
import redleon.net.comanda.model.Table;
import redleon.net.comanda.model.TablesResult;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

public class TablesReasignActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    TablesListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private ArrayList<Table> mTables = new ArrayList<Table>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TablesReasignActivity me = this;

        try {
            setContentView(R.layout.activity_tables_reasign);

            adapter = new TablesListAdapter(this);
            listView = (ListView) findViewById(R.id.list_view);
            listView.setEmptyView(findViewById(R.id.empty_data));

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_tables_swipe_container);
            swipeLayout.setOnRefreshListener(this);
            swipeLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

            final ProgressDialog progressBar;
            progressBar = new ProgressDialog(this);
            progressBar.setCancelable(false);
            progressBar.setMessage("Consultado información...");
            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressBar.setIndeterminate(true);
            progressBar.show();
            HttpClient.get("/listtables.json", Network.makeAuthParams(me), new JsonHttpResponseHandler() {
                //@Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    progressBar.dismiss();
                    // Pull out the first event on the public timeline

                    try {
                        String sResponse = response.getString("response");
                        // Do something with the response
                        if (sResponse.equals("ok")) {
                            JSONArray extras = response.getJSONArray("data");
                            // mExtras = new Extra[extras.length()];

                            for (int x = 0; x < extras.length(); x++) {
                                mTables.add(new Table(extras.getJSONObject(x).getInt("id"), extras.getJSONObject(x).getInt("number")));
                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                    progressBar.dismiss();
                    Toast.makeText(me, "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

                }


            }, getBaseContext());

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tables_reasign, menu);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final TablesResult tablesResult = (TablesResult) parent.getItemAtPosition(position);
        final TablesReasignActivity me = this;



        DialogFragment tableNumber = new TableNumberDialog();


        ((TableNumberDialog) tableNumber).setItems(mTables);
        ((TableNumberDialog) tableNumber).setOrigin(tablesResult.getId());
        tableNumber.show(getSupportFragmentManager(), "tableNumber");

/*
        HttpClient.post("/services/reassign/"+tablesResult.getId(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("status");

                    if (sResponse.equals("ok")) {

                    }else{
                        Toast.makeText(me, response.getString("status"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(me, "La mesa se reasigno correctamente.",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());

        */
    }


    public void onReasignTable(Table table, Integer origin){
        final TablesReasignActivity me = this;
        RequestParams params = Network.makeAuthParams(this);
        params.put("table_id", table.getId());
        final ProgressDialog progressBar;
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();
        HttpClient.post("/services/reassign/"+origin, params, new JsonHttpResponseHandler() {
            //@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.dismiss();
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("status");

                    if (sResponse.equals("ok")) {

                    }else{
                        Toast.makeText(me, response.getString("reason"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(me, "La mesa se reasigno correctamente.",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                progressBar.dismiss();
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());
    }

    @Override
    public void onRefresh() {
        TablesListLoader tablesListLoader = new TablesListLoader(adapter);
        tablesListLoader.execute();
        swipeLayout.setRefreshing(false);

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
}
