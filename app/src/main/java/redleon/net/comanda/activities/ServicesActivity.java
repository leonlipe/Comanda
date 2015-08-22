package redleon.net.comanda.activities;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.ServicesTabsAdapter;
import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.fragments.DinersFragment;
import redleon.net.comanda.fragments.InvoicesFragment;
import redleon.net.comanda.fragments.PaymentsFragment;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;


public class ServicesActivity extends ActionBarActivity implements ActionBar.TabListener, ComandasFragment.OnFragmentInteractionListener, DinersFragment.OnFragmentInteractionListener, InvoicesFragment.OnFragmentInteractionListener, PaymentsFragment.OnFragmentInteractionListener, ViewPager.OnPageChangeListener{
    public final static String TITLE = "net.redleon.TITLE";

    private Integer serviceId = 0;
    JSONArray diners = null;

    private ViewPager mViewPager;
    private ServicesTabsAdapter mAdapter;
    private ActionBar actionBar;
    private String barTitle;
    // Tab title
    private String [] tabs = { "Menu", "Comandas", "Pagos", "Facturas" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Intent intent = getIntent();
        setServiceId(intent.getIntExtra(TablesActivity.EXTRA_MESSAGE, 0));
        setBarTitle(intent.getStringExtra(TITLE));
        mViewPager = (ViewPager)findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        System.out.println(actionBar);
        mAdapter = new ServicesTabsAdapter(getSupportFragmentManager());
        System.out.println("ServicesActivity:" + getServiceId());
        mAdapter.setServiceId(getServiceId());
        mViewPager.setAdapter(mAdapter);
        setTitle(getBarTitle());

        /**
         * on swiping the viewpager make respective tab selected
         * */
        mViewPager.setOnPageChangeListener(this);

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_name:tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_services, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final Activity me = this;
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_close_service) {
            HttpClient.post("/services/close/" + getServiceId(), Network.makeAuthParams(me), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Pull out the first event on the public timeline

                    try {

                        String sResponse = response.getString("status");
                        // Do something with the response
                        //System.out.println(response.getJSONObject("service").getInt("id"));
                        if (sResponse.equals("ok")) {

                            Toast.makeText(me,
                                    "Se cerró el servicio.",
                                    Toast.LENGTH_SHORT).show();
                            me.finish();
                            return;
                        }else{
                            Toast.makeText(me,
                                    "No se puede cerrar servicio: "+sResponse,
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
            return true;
        }


        if (id == R.id.action_add_person) {
            HttpClient.post("/services/add_diner/" + getServiceId(), Network.makeAuthParams(me), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Pull out the first event on the public timeline

                    try {

                        String sResponse = response.getString("status");
                        // Do something with the response
                        //System.out.println(response.getJSONObject("service").getInt("id"));
                        if (sResponse.equals("ok")) {

                            Toast.makeText(me,
                                    "Se agregó una persona al servicio",
                                    Toast.LENGTH_SHORT).show();
                            mViewPager.findViewWithTag();


                        }else{
                            Toast.makeText(me,
                                    "Ocurrió un error: "+sResponse,
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
            return true;
        }
        if (id == R.id.action_send_all) {
            HttpClient.post("/commands/sendthem/" + getServiceId(), Network.makeAuthParams(me), new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Pull out the first event on the public timeline

                    try {

                        String sResponse = response.getString("status");
                        // Do something with the response
                        //System.out.println(response.getJSONObject("service").getInt("id"));
                        if (sResponse.equals("ok")) {
                            Toast.makeText(me,
                                    "Las comandas se enviaron correctamente.",
                                    Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

                }


            },getBaseContext());

            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

    }


    @Override
    public void onPaymentsFragmentInteraction(String string) {

    }

    @Override
    public void onInvoicesFragmentInteraction(String string) {

    }

    @Override
    public void onComandasFragmentInteraction(String string) {

    }

    @Override
    public void onDinersFragmentInteraction(String string) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        actionBar.setSelectedNavigationItem(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getBarTitle() {
        return barTitle;
    }

    public void setBarTitle(String barTitle) {
        this.barTitle = barTitle;
    }
}
