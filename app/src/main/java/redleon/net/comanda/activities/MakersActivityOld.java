package redleon.net.comanda.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MakersListAdapterOld;
import redleon.net.comanda.loaders.MakersViewLoadersOld;
import redleon.net.comanda.model.MakersCommandItem;

public class MakersActivityOld extends ActionBarActivity implements AdapterView.OnItemClickListener,  SwipeRefreshLayout.OnRefreshListener  {
    ListView listView;
    private String placeKey;
    MakersListAdapterOld makersListAdapterOld;
    private SwipeRefreshLayout swipeLayout;


    public static final String PLACE_KEY = "net.redleon.PLACE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers_activity_old);
        Intent intent = getIntent();

        setPlaceKey(intent.getStringExtra(PLACE_KEY));
        makersListAdapterOld = new MakersListAdapterOld(this);

        listView = (ListView) findViewById(R.id.makers_activity_list);
        listView.setEmptyView(findViewById(R.id.empty_data));

        listView.setAdapter(makersListAdapterOld);
        listView.setOnItemClickListener(this);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_makers_swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        if (getPlaceKey().equals("COC")) {
            setTitle("Cocina - Historial");
        }else
        if (getPlaceKey().equals("PIZ")) {
            setTitle("Pizzeria - Historial");
        }else
        if (getPlaceKey().equals("BAA")) {
            setTitle("Barra - Historial");
        }


//        MakersViewLoaders loadData = new MakersViewLoaders(makersListAdapter);
//        loadData.setPlaceKey(getPlaceKey());
//        loadData.execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_makers_activity_old, menu);
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
        MakersViewLoadersOld makersViewLoadersOld = new MakersViewLoadersOld(makersListAdapterOld);
        makersViewLoadersOld.setPlaceKey(getPlaceKey());
        makersViewLoadersOld.execute();
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
