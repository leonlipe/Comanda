package redleon.net.comanda.loaders;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

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

import redleon.net.comanda.adapters.ComandsHistoryAdapter;
import redleon.net.comanda.adapters.MakersListAdapter;
import redleon.net.comanda.model.JsonMakersCommandItemResult;
import redleon.net.comanda.model.JsonOrderDishesResult;
import redleon.net.comanda.model.MakersCommandItem;

/**
 * Created by leon on 17/07/15.
 */
public class MakersViewLoaders  extends
        AsyncTask<URL, Integer, ArrayList<MakersCommandItem>> {

    private String placeKey;
    private final String mUrl =
            "/commands/list_by_place/";

    private final MakersListAdapter mAdapter;

    public MakersViewLoaders(MakersListAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url + getPlaceKey());

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
    protected ArrayList<MakersCommandItem> doInBackground(URL... params) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mAdapter.getmContext());
        String ip_server = sp.getString("ip_server", "NA");
        InputStream source = retrieveStream("http://"+ip_server+mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        JsonMakersCommandItemResult jsonResult = gson.fromJson(reader, JsonMakersCommandItemResult.class);
        if (!jsonResult.getStatus().equals("ok"))
            new RuntimeException("Error al llamar al backend");
        MakersCommandItem[] result = jsonResult.getComandas();
        ArrayList<MakersCommandItem> resultados = new ArrayList<MakersCommandItem>();
        for (int x = 0; x < result.length; x++) {
            resultados.add(result[x]);
        }
        return resultados;
    }

    protected void onPostExecute(ArrayList<MakersCommandItem> entries) {
        mAdapter.upDateEntries(entries);
    }

    public String getPlaceKey() {
        return placeKey;
    }

    public void setPlaceKey(String placeKey) {
        this.placeKey = placeKey;
    }
}


