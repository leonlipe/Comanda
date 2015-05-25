package redleon.net.comanda.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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


public class TablesActivity extends ListActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TablesActivity mySelf = this;

        try {
            setContentView(R.layout.activity_tables);

            TablesListAdapter adapter = new TablesListAdapter(this);
            setListAdapter(adapter);

            TablesListLoader loadData = new TablesListLoader(adapter);
            loadData.execute();
            System.out.println("http call");
            HttpClient.get("tiimes_for_menu.json", null, new JsonHttpResponseHandler() {
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
                            if (menu ==null) {
                                ((ComandaApp) mySelf.getApplication()).setMenu(new ArrayList<Tiime>());
                                menu = ((ComandaApp) mySelf.getApplication()).getMenu();
                            }
                            System.out.println(menu.toString());
                            JSONArray tiimes = response.getJSONArray("items");
                            for(int x=0; x< tiimes.length();x++ ){
                                JSONObject tiime = tiimes.getJSONObject(x);
                                JSONArray dishes = tiime.getJSONArray("items");
                                Dish[] timmesDishes = new Dish[dishes.length()];
                                for(int y=0; y< dishes.length();y++ ){
                                    JSONObject dish = dishes.getJSONObject(y);
                                    timmesDishes[y]=new Dish(dish.getInt("id"), dish.getString("description"), dish.getString("name"));
                                }
                                menu.add(new Tiime(tiime.getInt("id"), tiime.getString("name"), tiime.getString("description"), timmesDishes));
                            }
                            System.out.println("menu");
                            for(Tiime time: menu){
                                System.out.println(time.getName());
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }



    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

      final TablesActivity mySelf = this;


            HttpClient.get("services/start/" + ((TablesResult) l.getItemAtPosition(position)).getId(), null, new JsonHttpResponseHandler() {
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
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


                }
            });

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


}
