package redleon.net.comanda.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.loaders.TablesListLoader;
import redleon.net.comanda.model.TablesResult;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

public class TablesReasignActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private GridView gridView;
    TablesListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TablesReasignActivity mySelf = this;

        try {
            setContentView(R.layout.activity_tables_reasign);

            adapter = new TablesListAdapter(this);
            gridView = (GridView) findViewById(R.id.gridview);
            gridView.setEmptyView(findViewById(R.id.empty_data));

            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);

            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_tables_swipe_container);
            swipeLayout.setOnRefreshListener(this);
            swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

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

        RequestParams params = Network.makeAuthParams(this);
        params.put("table_id", 8);

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
