package redleon.net.comanda.loaders;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

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


import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.model.TablesResult;

/**
 * Created by leon on 24/04/15.
 */
public class TablesListLoader extends
        AsyncTask<URL, Integer, ArrayList<TablesResult>> {

    private final String mUrl =
            "/tables_for_list.json";

    private final TablesListAdapter mAdapter;
    public TablesListLoader(TablesListAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url);

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
    protected ArrayList<TablesResult> doInBackground(URL... params) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mAdapter.getmContext());
        String ip_server = sp.getString("ip_server", "NA");
        Log.v("Loader", "http://" + ip_server + mUrl);
        InputStream source = retrieveStream("http://"+ip_server+mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<TablesResult>();
        }
        Gson gson = new Gson();
        TablesResult[] result = gson.fromJson(reader,TablesResult[].class);
        ArrayList<TablesResult> resultados = new ArrayList<TablesResult>();
        for(int x=0; x <result.length;x++){
            resultados.add(result[x]);
        }
        return resultados;
    }

    protected void onPostExecute(ArrayList<TablesResult> entries) {
        mAdapter.upDateEntries(entries);
    }
}
