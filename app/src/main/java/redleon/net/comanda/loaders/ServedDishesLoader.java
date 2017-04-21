package redleon.net.comanda.loaders;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import redleon.net.comanda.activities.ServerDishesActivity;
import redleon.net.comanda.adapters.ComandsHistoryAdapter;
import redleon.net.comanda.adapters.ServedDishesAdapter;
import redleon.net.comanda.model.JsonOrderDishesResult;
import redleon.net.comanda.model.OrderDishesData;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 08/07/15.
 */
public class ServedDishesLoader extends
        AsyncTask<URL, Integer, ArrayList<OrderDishesData>> {
    private ProgressDialog progressBar;
    private boolean hadError = false;
    private String errorMsg = "";
    private Integer serviceId;
    private Integer dinerId;
    private final String mUrl =
            "/services/get_done_order_dishes/";

    private final ServedDishesAdapter mAdapter;
    public ServedDishesLoader(ServedDishesAdapter adapter) {
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
    protected ArrayList<OrderDishesData> doInBackground(URL... params) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mAdapter.getmContext());
        String ip_server = sp.getString("ip_server", "NA");
        String myUrl = "http://"+ip_server+mUrl+getServiceId();
        myUrl = Network.addAuthParams(myUrl, mAdapter.getmContext());
        InputStream source = retrieveStream(myUrl);
        Reader reader = null;
        ArrayList<OrderDishesData> resultados = new ArrayList<OrderDishesData>();
        try {
            if (source == null){
                throw new Exception("Error en la comunicacion al servidor");
            }
            reader = new InputStreamReader(source);
            Gson gson = new Gson();
            JsonOrderDishesResult jsonResult = gson.fromJson(reader, JsonOrderDishesResult.class);
            if (!jsonResult.getStatus().equals("ok"))
                new RuntimeException("Error al llamar al backend");
            OrderDishesData[] result = jsonResult.getOrder_dishes_data();

            for(int x=0; x <result.length;x++){
                resultados.add(result[x]);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    protected void onPostExecute(ArrayList<OrderDishesData> entries) {
        progressBar.dismiss();
        if (hadError){
            Toast.makeText(mAdapter.getmContext(), "Ocurrio un error inesperado, tal vez no hay conexion con el servidor. ", Toast.LENGTH_LONG).show();
        }else {
            mAdapter.upDateEntries(entries);
        }
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
