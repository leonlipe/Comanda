package redleon.net.comanda;

import android.support.v7.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import redleon.net.comanda.adapters.ServicesTabsAdapter;
import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.fragments.DinersFragment;
import redleon.net.comanda.fragments.InvoicesFragment;
import redleon.net.comanda.fragments.PaymentsFragment;


public class ServicesActivity extends ActionBarActivity implements ActionBar.TabListener, ComandasFragment.OnFragmentInteractionListener, DinersFragment.OnFragmentInteractionListener, InvoicesFragment.OnFragmentInteractionListener, PaymentsFragment.OnFragmentInteractionListener{

    Integer idService = 0;
    JSONArray diners = null;

    private ViewPager mViewPager;
    private ServicesTabsAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String [] tabs = { "Menu", "Comandas", "Pagos", "Facturas" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        actionBar = getSupportActionBar();
        System.out.println(actionBar);
        mAdapter = new ServicesTabsAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_name:tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }




        Intent intent = getIntent();
        idService = intent.getIntExtra(TablesActivity.EXTRA_MESSAGE,0);


        /*HttpClient.get("diners/by_service/"+idService.toString() , null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {

                    String sResponse = response.getString("status");
                    // Do something with the response
                    if (sResponse.equals("ok")) {
                        diners = response.getJSONArray("diners");

                        for(int x  = 0;x<diners.length(); x++){
                            System.out.println( ((JSONObject)diners.get(x)).getString("diner_desc"));
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_, menu);
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
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

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
}
