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

import redleon.net.comanda.adapters.DinersListAdapter;
import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.model.JsonDinersResult;
import redleon.net.comanda.model.TablesResult;

/**
 * Created by leon on 19/05/15.
 */
public class DinersListLoader  extends
        AsyncTask<URL, Integer, ArrayList<DinersResult>> {

    private Integer serviceId;
    private final String mUrl =
            "http://172.31.1.19:3000/diners/by_service/";

    private final DinersListAdapter mAdapter;
    public DinersListLoader(DinersListAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {
        System.out.println("DinerListLoader:"+getServiceId());

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
    protected ArrayList<DinersResult> doInBackground(URL... params) {
        InputStream source = retrieveStream(mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        JsonDinersResult jsonResult = gson.fromJson(reader,JsonDinersResult.class);
        if (!jsonResult.getStatus().equals("ok"))
            new RuntimeException("Error al llamar al backend");
        DinersResult[] result = jsonResult.getDiners();
        ArrayList<DinersResult> resultados = new ArrayList<DinersResult>();
        for(int x=0; x <result.length;x++){
            resultados.add(result[x]);
        }
        return resultados;
    }

    protected void onPostExecute(ArrayList<DinersResult> entries) {
        mAdapter.upDateEntries(entries);
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
}
