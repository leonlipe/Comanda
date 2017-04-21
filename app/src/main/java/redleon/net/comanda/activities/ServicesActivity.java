package redleon.net.comanda.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
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


public class ServicesActivity extends AppCompatActivity implements ActionBar.TabListener, ComandasFragment.OnFragmentInteractionListener, DinersFragment.OnFragmentInteractionListener, InvoicesFragment.OnFragmentInteractionListener, PaymentsFragment.OnFragmentInteractionListener, ViewPager.OnPageChangeListener{
    public final static String TITLE = "net.redleon.TITLE";

    private Integer serviceId = 0;
    JSONArray diners = null;

    private ViewPager mViewPager;
    private ServicesTabsAdapter mAdapter;
    private ActionBar actionBar;
    private String barTitle;
    // Tab title
    private String [] tabs = { "Menu", "Comandas", "Pagos", "Facturas" };
    private ProgressDialog progressBar;

    private TextView txtViewCount;

    private  int count = 0;
    long startTime = 0;



    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            updateServedDishes();

            timerHandler.postDelayed(this, 10000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = new ProgressDialog(this);
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
        mAdapter.setWindowTitle(intent.getStringExtra(TITLE));
        mViewPager.setAdapter(mAdapter);
        setTitle(getBarTitle());

        Button btnAddPerson = (Button) findViewById(R.id.btn_add_person);
        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddPerson();
            }
        });
        Button btnSendAll = (Button) findViewById(R.id.btn_send);
        btnSendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSendAll();
            }
        });
        Button btnCloseService = (Button) findViewById(R.id.btn_close);
        btnCloseService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeService();
            }
        });


        /**
         * on swiping the viewpager make respective tab selected
         * */
        mViewPager.setOnPageChangeListener(this);

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for(String tab_name:tabs){
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }

        timerHandler.postDelayed(timerRunnable, 0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        menu.findItem(R.id.action_serve_dishes).setIcon(
//                new IconDrawable(this, FontAwesomeIcons.fa_coffee)
//                        .colorRes(R.color.ab_icon)
//                        .actionBarSize());

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_services, menu);
        final Activity me = this;
        final View notificaitons = menu.findItem(R.id.action_serve_dishes).getActionView();

        txtViewCount = (TextView) notificaitons.findViewById(R.id.txtCount);
        updateHotCount(count++);
        txtViewCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(me, ServerDishesActivity.class);
                intent.putExtra(ServerDishesActivity.SERVICE_ID, getServiceId());
                startActivity(intent);
               // return true;
            }
        });
//        txtViewCount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateHotCount(count++);
//            }
//        });
//        notificaitons.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //    TODO
//            }
//        });


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

        if (id == R.id.action_serve_dishes) {

            Intent intent = new Intent(this, ServerDishesActivity.class);
            intent.putExtra(ServerDishesActivity.SERVICE_ID, getServiceId());
            startActivity(intent);
            return true;
        }

       /* if (id == R.id.action_close_service) {

            closeService();
            return true;
        }


        if (id == R.id.action_add_person) {
        //    View view =  mViewPager.findViewWithTag(makeFragmentName(R.id.pager, 0));
            btnAddPerson();

            return true;
        }
        if (id == R.id.action_send_all) {

           btnSendAll();

            return true;
        }*/

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

    private static String makeFragmentName(int viewPagerId, int index) {
        return "android:switcher:" + viewPagerId + ":" + index;
    }

    public void updateForPersonChange(){
        DinersFragment df = (DinersFragment) mAdapter.getFragment(0);
        ComandasFragment cf = (ComandasFragment) mAdapter.getFragment(1);
        PaymentsFragment pf = (PaymentsFragment) mAdapter.getFragment(2);
        if (df != null)
            df.onRefresh();
        if (cf != null)
            cf.onRefresh();
        if (pf != null)
            pf.onRefresh();
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
     * Metodo para agregar a una persona al servicio.
     */

    public void btnAddPerson() {
        final Activity me = this;

        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();
        HttpClient.post("/services/add_diner/" + getServiceId(), Network.makeAuthParams(me), new JsonHttpResponseHandler() {
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
                                "Se agregó una persona al servicio",
                                Toast.LENGTH_SHORT).show();
                        updateForPersonChange();


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
                progressBar.dismiss();
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());
    }


    /**
     * Enviar todas las comandas
     */
    public void btnSendAll() {

        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();

        final Activity me = this;


        HttpClient.post("/commands/sendthem/" + serviceId, Network.makeAuthParams(me), new JsonHttpResponseHandler() {
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
                                "Las comandas se enviaron correctamente.",
                                Toast.LENGTH_SHORT).show();

                        ComandasFragment cf = (ComandasFragment) mAdapter.getFragment(1);
                        if (cf != null)
                            cf.onRefresh();
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

    /**
     * Metodo para cerrar el servicio
     */

    public void closeService(){
        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();

        final Activity me = this;


        HttpClient.post("/services/close/" + getServiceId(), Network.makeAuthParams(me), new JsonHttpResponseHandler() {
            //@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.dismiss();
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
                progressBar.dismiss();
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());
    }

    public void updateHotCount(final int new_hot_number) {
        count = new_hot_number;
        if (count < 0) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (count == 0)
                    txtViewCount.setVisibility(View.GONE);
                else {
                    txtViewCount.setVisibility(View.VISIBLE);
                    txtViewCount.setText(Integer.toString(count));
                    // supportInvalidateOptionsMenu();
                }
            }
        });
    }

    public void updateServedDishes(){
        final Activity me = this;
        HttpClient.get("/services/get_done_order_dishes_count/" + serviceId, Network.makeAuthParams(me), new JsonHttpResponseHandler() {
            // @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.dismiss();
                // Pull out the first event on the public timeline

                try {

                    String sResponse = response.getString("status");
                    // Do something with the response
                    //System.out.println(response.getJSONObject("service").getInt("id"));
                    if (sResponse.equals("ok")) {
                        // Update count

                        updateHotCount(Integer.valueOf(response.getString("order_dishes_count")));
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
