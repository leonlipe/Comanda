package redleon.net.comanda.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import redleon.net.comanda.ComandaApp;
import redleon.net.comanda.R;
import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.loaders.TablesListLoader;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.TablesResult;
import redleon.net.comanda.model.Tiime;
import redleon.net.comanda.network.HttpClient;

import com.google.gson.Gson;
import com.loopj.android.http.*;

import java.util.ArrayList;
import java.util.List;


public class TablesActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener  {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    ListView listView;
    TablesListAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TablesActivity mySelf = this;

        try {
            setContentView(R.layout.activity_tables);

            adapter = new TablesListAdapter(this);
            listView = (ListView) findViewById(android.R.id.list);

            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);

            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_tables_swipe_container);
            swipeLayout.setOnRefreshListener(this);
            swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);

            TablesListLoader loadData = new TablesListLoader(adapter);
            loadData.execute();
            System.out.println("http call");
            HttpClient.get("/tiimes_for_menu.json", null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Pull out the first event on the public timeline

                    try {

                        String sResponse = response.getString("name");
                        // Do something with the response
                        if (sResponse != null) {
                         /*   Intent intent = new Intent(mySelf, ServicesActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, response.getJSONObject("service").getInt("id"));
                            startActivity(intent);*/
                            List<Tiime> menu = ((ComandaApp) mySelf.getApplication()).getMenu();
                            if (menu == null) {
                                ((ComandaApp) mySelf.getApplication()).setMenu(new ArrayList<Tiime>());
                                menu = ((ComandaApp) mySelf.getApplication()).getMenu();
                            }
                            System.out.println(menu.toString());
                            JSONArray tiimes = response.getJSONArray("items");
                            for (int x = 0; x < tiimes.length(); x++) {
                                JSONObject tiime = tiimes.getJSONObject(x);
                                JSONArray dishes = tiime.getJSONArray("items");
                                Dish[] timmesDishes = new Dish[dishes.length()];
                                for (int y = 0; y < dishes.length(); y++) {
                                    JSONObject dish = dishes.getJSONObject(y);
                                    timmesDishes[y] = new Dish(dish.getInt("id"), dish.getString("description"), dish.getString("name"));
                                }
                                menu.add(new Tiime(tiime.getInt("id"), tiime.getString("name"), tiime.getString("description"), timmesDishes));
                            }
                            System.out.println("menu");
                            for (Tiime time : menu) {
                                System.out.println(time.getName());
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(mySelf, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show();


                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable){
                    Toast.makeText(mySelf, "Ocurrio un error inesperado: "+throwable.getMessage(), Toast.LENGTH_LONG).show();

                }




            },getBaseContext());

        }catch(Exception e){
            e.printStackTrace();
        }



    }


    //@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

      final TablesActivity mySelf = this;


            HttpClient.get("/services/start/" + ((TablesResult) l.getItemAtPosition(position)).getId(), null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Pull out the first event on the public timeline

                    try {

                        String sResponse = response.getString("status");
                        // Do something with the response
                        System.out.println(response.getJSONObject("service").getInt("id"));
                        if (sResponse.equals("ok")) {
                            Intent intent = new Intent(mySelf, ServicesActivity.class);
                            intent.putExtra(EXTRA_MESSAGE, response.getJSONObject("service").getInt("id"));
                            startActivity(intent);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    catch (Exception e){
                        Toast.makeText(mySelf, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                    Toast.makeText(mySelf, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

                }


            },getBaseContext());

       /* String url = "http://172.31.1.19:3000/services/start/"+((TablesResult) l.getItemAtPosition(position)).getId();
        // Llamar al WS para generar un nuevo servicio, y que la actividad de servicios consulte la info del mismo.
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url);

        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);
            HttpEntity getResponseEntity = httpResponse.getEntity();
            InputStream response = getResponseEntity.getContent();
            // llamar al intent
            Intent intent = new Intent(this, ServicesActivity.class);
            intent.putExtra(EXTRA_MESSAGE, ((TablesResult) l.getItemAtPosition(position)).getId());
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            httpGet.abort();
        }*/




        //  Uri uri = ContentUris.withAppendedId(this.getIntent().getData(), id);
        // your code

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tables, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {

            case R.id.action_show_cocina:
                intent = new Intent(this, MakersActivity.class);
                intent.putExtra(MakersActivity.PLACE_KEY, "COC");
                startActivity(intent);
                return true;
            case R.id.action_show_barra:
                intent = new Intent(this, MakersActivity.class);
                intent.putExtra(MakersActivity.PLACE_KEY, "BAA");
                startActivity(intent);
                return true;
            case R.id.action_show_horno:
                intent = new Intent(this, MakersActivity.class);
                intent.putExtra(MakersActivity.PLACE_KEY, "PIZ");
                startActivity(intent);
                return true;
            case R.id.action_settings:
                 intent = new Intent(this, AppPreferences.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final TablesActivity mySelf = this;
        Log.v("ItemClick","Entro");

        HttpClient.get("/services/start/" + ((TablesResult) parent.getItemAtPosition(position)).getId(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {

                    String sResponse = response.getString("status");
                    // Do something with the response
                    System.out.println(response.getJSONObject("service").getInt("id"));
                    if (sResponse.equals("ok")) {
                        Intent intent = new Intent(mySelf, ServicesActivity.class);
                        intent.putExtra(EXTRA_MESSAGE, response.getJSONObject("service").getInt("id"));
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e){
                    Toast.makeText(mySelf, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(mySelf, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());
    }

    @Override
    public void onRefresh() {
        TablesListLoader tablesListLoader = new TablesListLoader(adapter);
        tablesListLoader.execute();
        swipeLayout.setRefreshing(false);

    }
}
