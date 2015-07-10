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
import redleon.net.comanda.adapters.ComandsHistoryAdapter;
import redleon.net.comanda.model.ComandasResult;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.model.JsonComandasResult;
import redleon.net.comanda.model.JsonDinersResult;
import redleon.net.comanda.model.JsonOrderDishesResult;
import redleon.net.comanda.model.OrderDishesData;

/**
 * Created by leon on 08/07/15.
 */
public class ComandasHistoryLoader extends
        AsyncTask<URL, Integer, ArrayList<OrderDishesData>> {

    private Integer serviceId;
    private Integer dinerId;
    private final String mUrl =
            "http://172.31.1.19:3000/services/get_history/";

    private final ComandsHistoryAdapter mAdapter;
    public ComandasHistoryLoader(ComandsHistoryAdapter adapter) {
        mAdapter = adapter;
    }

    private InputStream retrieveStream(String url) {

        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet httpGet = null;
        httpGet = new HttpGet(url+getDinerId().toString());

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
    protected ArrayList<OrderDishesData> doInBackground(URL... params) {
        InputStream source = retrieveStream(mUrl);
        Reader reader = null;
        try {
            reader = new InputStreamReader(source);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Gson gson = new Gson();
        JsonOrderDishesResult jsonResult = gson.fromJson(reader,JsonOrderDishesResult.class);
        if (!jsonResult.getStatus().equals("ok"))
            new RuntimeException("Error al llamar al backend");
        OrderDishesData[] result = jsonResult.getOrder_dishes_data();
        ArrayList<OrderDishesData> resultados = new ArrayList<OrderDishesData>();
        for(int x=0; x <result.length;x++){
            resultados.add(result[x]);
        }
        return resultados;
    }

    protected void onPostExecute(ArrayList<OrderDishesData> entries) {
        mAdapter.upDateEntries(entries);
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getDinerId() {
        return dinerId;
    }

    public void setDinerId(Integer dinerId) {
        this.dinerId = dinerId;
    }
}