package redleon.net.comanda.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import redleon.net.comanda.ComandaApp;
import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MenuListAdapter;
import redleon.net.comanda.loaders.MenuListLoader;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.MakersCommandItem;
import redleon.net.comanda.model.Tiime;


public class MenuActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public final static String TABLE_DESC = "net.redleon.TABLE_DESC";
    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";
    public final static String DINER_ID = "net.redleon.DINER_ID";

    private Integer serviceId;
    private Integer dinerId;
    private String tableDesc;

    private ListView listView;
    private MenuListAdapter menuListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setServiceId(intent.getIntExtra(SERVICE_ID, 0));
        setDinerId(intent.getIntExtra(DINER_ID, 0));
        setTableDesc(intent.getStringExtra(TABLE_DESC));

        setContentView(R.layout.activity_menu);
        menuListAdapter = new MenuListAdapter(this);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(menuListAdapter);
        listView.setOnItemClickListener(this);

        MenuListLoader menuListLoader = new MenuListLoader(menuListAdapter);
        menuListLoader.execute();

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
        redleon.net.comanda.model.MenuItem menuItem =(redleon.net.comanda.model.MenuItem) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, SubMenuActivity.class);
        intent.putExtra(SubMenuActivity.SERVICE_ID, getServiceId());
        intent.putExtra(SubMenuActivity.DINER_ID,getDinerId());
        intent.putExtra(SubMenuActivity.MENU_ID, menuItem.getId() );
        intent.putExtra(SubMenuActivity.TABLE_DESC, getTableDesc() );
        startActivity(intent);


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

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }
}