package redleon.net.comanda.loaders;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import redleon.net.comanda.adapters.MenuListAdapter;
import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.model.MenuItem;
import redleon.net.comanda.model.TablesResult;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 24/04/15.
 */
public class MenuListLoader extends
        AsyncTask<URL, Integer, ArrayList<MenuItem>> {

    private ProgressDialog progressBar;
    private final String mUrl =
            "/list_menu.json";

    private boolean hadError = false;
    private String errorMsg = "";

    private final MenuListAdapter mAdapter;
    public MenuListLoader(MenuListAdapter adapter) {
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
    protected ArrayList<MenuItem> doInBackground(URL... params) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mAdapter.getmContext());
        String ip_server = sp.getString("ip_server", "NA");
        Log.v("Loader", "http://" + ip_server + mUrl);
        String myUrl = "http://"+ip_server+mUrl;
        myUrl = Network.addAuthParams(myUrl, mAdapter.getmContext());
        InputStream source = retrieveStream(myUrl);
        Reader reader = null;
        ArrayList<MenuItem> resultados = new ArrayList<MenuItem>();

        try {
            if (source == null){
                throw new Exception("Error en la comunicacion al servidor");
            }
            reader = new InputStreamReader(source);
            Gson gson = new Gson();
            MenuItem[] result = gson.fromJson(reader,MenuItem[].class);
            for(int x=0; x <result.length;x++){
                resultados.add(result[x]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return new ArrayList<TablesResult>();
            hadError = true;
            errorMsg = e.getMessage();
            return null;
        }

        return resultados;
    }
    @Override
    protected void onPreExecute(){
        progressBar = new ProgressDialog(mAdapter.getmContext());
        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();
    }
    protected void onPostExecute(ArrayList<MenuItem> entries) {
        progressBar.dismiss();
        if (hadError){
            Toast.makeText(mAdapter.getmContext(), "Ocurrio un error inesperado, tal vez no hay conexion con el servidor. ", Toast.LENGTH_LONG).show();
        }else {
            mAdapter.upDateEntries(entries);
        }
    }
}
