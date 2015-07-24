package redleon.net.comanda.loaders;

import android.os.AsyncTask;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import redleon.net.comanda.adapters.MakersDetailListAdapter;
import redleon.net.comanda.adapters.MakersListAdapter;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.JsonMakersCommandItemResult;
import redleon.net.comanda.model.JsonMakersDetailResult;
import redleon.net.comanda.model.MakersCommandItem;

/**
 * Created by leon on 22/07/15.
 */
public class MakersDetailLoader extends
        AsyncTask<URL, Integer, ArrayList<Dish>> {

    private Integer dishId;
    private final String mUrl =
            "http://172.31.1.19:3000/commands/details/";

    private final MakersDetailListAdapter mAdapter;

    public MakersDetailLoader(MakersDetailListAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url + getDishId());

        HttpResponse httpResponse = null;
        try {
            httpResponse = client.execute(httpGet);
            HttpEntity getResponseEntity = httpResponse.getEntity();
            return getResponseEntity.getContent();
        } catch (IOException e) {
            e.printStackTrace();
            httpGet.abort();
        }
        return null;
    }

    @Override
    protected ArrayList<Dish> doInBackground(URL... params) {
        InputStream source = retrieveStream(mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        JsonMakersDetailResult jsonResult = gson.fromJson(reader, JsonMakersDetailResult.class);
        if (!jsonResult.getStatus().equals("ok"))
            new RuntimeException("Error al llamar al backend");
        Dish[] result = jsonResult.getDishes();
        ArrayList<Dish> resultados = new ArrayList<Dish>();
        for (int x = 0; x < result.length; x++) {
            resultados.add(result[x]);
        }
        return resultados;
    }

    protected void onPostExecute(ArrayList<Dish> entries) {
        mAdapter.upDateEntries(entries);
    }



    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
