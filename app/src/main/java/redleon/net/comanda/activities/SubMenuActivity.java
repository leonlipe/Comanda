package redleon.net.comanda.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MenuListAdapter;
import redleon.net.comanda.adapters.SubMenuListAdapter;
import redleon.net.comanda.loaders.SubMenuListLoader;

public class SubMenuActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{

    public final static String TABLE_DESC = "net.redleon.TABLE_DESC";
    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";
    public final static String DINER_ID = "net.redleon.DINER_ID";
    public final static String MENU_ID = "net.redleon.MENU_ID";

    private Integer serviceId;
    private Integer dinerId;
    private Integer menuId;
    private String tableDesc;


    private ListView listView;
    private SubMenuListAdapter subMenuListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_menu);
        Intent intent = getIntent();
        setServiceId(intent.getIntExtra(SERVICE_ID, 0));
        setDinerId(intent.getIntExtra(DINER_ID, 0));
        setMenuId(intent.getIntExtra(MENU_ID, 0));
        setTableDesc(intent.getStringExtra(TABLE_DESC));

        subMenuListAdapter = new SubMenuListAdapter(this);
        listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(subMenuListAdapter);
        listView.setOnItemClickListener(this);

        SubMenuListLoader subMenuListLoader = new SubMenuListLoader(subMenuListAdapter);
        subMenuListLoader.setMenuId(getMenuId());
        subMenuListLoader.execute();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {

            Intent intent = new Intent(this, ServicesActivity.class);
            intent.putExtra(TablesActivity.EXTRA_MESSAGE, getServiceId());
            intent.putExtra(ServicesActivity.TITLE, getTableDesc());
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        redleon.net.comanda.model.MenuItem menuItem =(redleon.net.comanda.model.MenuItem) parent.getItemAtPosition(position);

        Intent intent = new Intent(this, DishActivity.class);
        intent.putExtra(DishActivity.DISH_ID,menuItem.getId());
        intent.putExtra(DishActivity.DINER_ID,getDinerId());
        intent.putExtra(DishActivity.SERVICE_ID,getServiceId());

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

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }
}
