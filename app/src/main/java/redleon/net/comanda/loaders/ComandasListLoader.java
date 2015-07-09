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

import redleon.net.comanda.adapters.ComandasListAdapter;
import redleon.net.comanda.adapters.DinersListAdapter;
import redleon.net.comanda.model.ComandasResult;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.model.JsonComandasResult;
import redleon.net.comanda.model.JsonDinersResult;

/**
 * Created by leon on 19/05/15.
 */
public class ComandasListLoader extends
        AsyncTask<URL, Integer, ArrayList<ComandasResult>> {

    private Integer serviceId;
    private final String mUrl =
            "http://172.31.1.19:3000/services/get_commands_status/";

    private final ComandasListAdapter mAdapter;
    public ComandasListLoader(ComandasListAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url+getServiceId().toString());

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
    protected ArrayList<ComandasResult> doInBackground(URL... params) {
        InputStream source = retrieveStream(mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        JsonComandasResult jsonResult = gson.fromJson(reader,JsonComandasResult.class);
        if (!jsonResult.getStatus().equals("ok"))
            new RuntimeException("Error al llamar al backend");
        ComandasResult[] result = jsonResult.getDiners();
        ArrayList<ComandasResult> resultados = new ArrayList<ComandasResult>();
        for(int x=0; x <result.length;x++){
            resultados.add(result[x]);
        }
        return resultados;
    }

    protected void onPostExecute(ArrayList<ComandasResult> entries) {
        mAdapter.upDateEntries(entries);
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
