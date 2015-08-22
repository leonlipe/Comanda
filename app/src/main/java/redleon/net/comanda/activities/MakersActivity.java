package redleon.net.comanda.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MakersListAdapter;
import redleon.net.comanda.loaders.MakersViewLoaders;
import redleon.net.comanda.model.MakersCommandItem;
import redleon.net.comanda.model.TablesResult;
import redleon.net.comanda.network.HttpClient;

public class MakersActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,  SwipeRefreshLayout.OnRefreshListener  {
    ListView listView;
    private String placeKey;
    MakersListAdapter makersListAdapter;
    private SwipeRefreshLayout swipeLayout;


    public static final String PLACE_KEY = "net.redleon.PLACE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers);
        Intent intent = getIntent();

        setPlaceKey(intent.getStringExtra(PLACE_KEY));
        makersListAdapter = new MakersListAdapter(this);
        listView = (ListView) findViewById(android.R.id.list);

        listView.setAdapter(makersListAdapter);
        listView.setOnItemClickListener(this);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_makers_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (getPlaceKey().equals("COC")) {
            setTitle("Cocina");
        }else
        if (getPlaceKey().equals("PIZ")) {
            setTitle("Pizzeria");
        }else
        if (getPlaceKey().equals("BAA")) {
            setTitle("Barra");
        }


//        MakersViewLoaders loadData = new MakersViewLoaders(makersListAdapter);
//        loadData.setPlaceKey(getPlaceKey());
//        loadData.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_makers, menu);
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
        Intent intent = new Intent(this, MakersDetailActivity.class);
        intent.putExtra(MakersDetailActivity.COMMAND_ID, ((MakersCommandItem) parent.getItemAtPosition(position)).getId());
        startActivity(intent);


    }

    public String getPlaceKey() {
        return placeKey;
    }

    public void setPlaceKey(String placeKey) {
        this.placeKey = placeKey;
    }

    @Override
    public void onRefresh() {
        MakersViewLoaders makersViewLoaders = new MakersViewLoaders(makersListAdapter);
        makersViewLoaders.setPlaceKey(getPlaceKey());
        makersViewLoaders.execute();
        swipeLayout.setRefreshing(false);

    }

    @Override
    public void onStart(){
        super.onStart();
      //  onRefresh();
      //  System.out.println(">>>>>>>>>>>>>>>>>OnStart");

    }

    @Override
    public void onResume(){
        super.onResume();
        onRefresh();

        System.out.println(">>>>>>>>>>>>>>>>>>OnResume");


    }

}
