package redleon.net.comanda;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.loaders.TablesListLoader;
import redleon.net.comanda.model.TablesResult;
import redleon.net.comanda.redleon.net.comanda.HttpClient;

import com.loopj.android.http.*;


public class TablesActivity extends ListActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_tables);

            TablesListAdapter adapter = new TablesListAdapter(this);
            setListAdapter(adapter);

            TablesListLoader loadData = new TablesListLoader(adapter);
            loadData.execute();
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
                        if (sResponse.equals("ok")){
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
