package redleon.net.comanda.loaders;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

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

import redleon.net.comanda.adapters.MenuListAdapter;
import redleon.net.comanda.adapters.SubMenuListAdapter;
import redleon.net.comanda.model.MenuItem;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 24/04/15.
 */
public class SubMenuListLoader extends
        AsyncTask<URL, Integer, ArrayList<MenuItem>> {

    private Integer menuId;

    private final String mUrl =
            "/list_sub_menu/";

    private boolean hadError = false;
    private String errorMsg = "";

    private final SubMenuListAdapter mAdapter;
    public SubMenuListLoader(SubMenuListAdapter adapter) {
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
        String myUrl = "http://"+ip_server+mUrl+getMenuId()+".json";
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

    protected void onPostExecute(ArrayList<MenuItem> entries) {
        if (hadError){
            Toast.makeText(mAdapter.getmContext(), "Ocurrio un error inesperado, tal vez no hay conexion con el servidor. ", Toast.LENGTH_LONG).show();
        }else {
            mAdapter.upDateEntries(entries);
        }
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
