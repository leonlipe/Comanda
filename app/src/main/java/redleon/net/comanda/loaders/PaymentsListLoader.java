package redleon.net.comanda.loaders;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.widget.TextView;
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

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.DinersListAdapter;
import redleon.net.comanda.adapters.PaymentsListAdapter;
import redleon.net.comanda.fragments.PaymentsFragment;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.model.JsonDinersResult;
import redleon.net.comanda.model.JsonPaymentsResult;
import redleon.net.comanda.model.PaymentsResult;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 19/05/15.
 */
public class PaymentsListLoader extends
        AsyncTask<URL, Integer, ArrayList<PaymentsResult>> {
    private boolean hadError = false;
    private String errorMsg = "";
    private Integer serviceId;
    private PaymentsFragment paymentsFragment;
    private final String mUrl =
            "/services/payment_resume_diners/";

    private final PaymentsListAdapter mAdapter;
    public PaymentsListLoader(PaymentsListAdapter adapter) {
        mAdapter = adapter;
        this.paymentsFragment = paymentsFragment;
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
    protected ArrayList<PaymentsResult> doInBackground(URL... params) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mAdapter.getmContext());
        String ip_server = sp.getString("ip_server", "NA");
        String myUrl = "http://"+ip_server+mUrl;
        myUrl = Network.addAuthParams(myUrl, mAdapter.getmContext());
        InputStream source = retrieveStream(myUrl);
        Reader reader = null;
        ArrayList<PaymentsResult> resultados = new ArrayList<PaymentsResult>();
        try {
            if (source == null){
                throw new Exception("Error en la comunicacion al servidor");
            }
            reader = new InputStreamReader(source);
            Gson gson = new Gson();
            JsonPaymentsResult jsonResult = gson.fromJson(reader,JsonPaymentsResult.class);
            if (!jsonResult.getStatus().equals("ok"))
                new RuntimeException("Error al llamar al backend");
            //TextView total = (TextView) paymentsFragment.getView().findViewById(R.id.payment_list_total);
            //total.setText(jsonResult.getGran_total());
            PaymentsResult[] result = jsonResult.getDiners();

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

    protected void onPostExecute(ArrayList<PaymentsResult> entries) {
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
}
