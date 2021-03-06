package redleon.net.comanda.loaders;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
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

import redleon.net.comanda.model.Invoice;
import redleon.net.comanda.adapters.InvoicesListAdapter;
import redleon.net.comanda.fragments.InvoicesFragment;
import redleon.net.comanda.model.JsonInvoicesResult;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 19/05/15.
 */
public class InvoicesListLoader extends
        AsyncTask<URL, Integer, ArrayList<Invoice>> {
    private ProgressDialog progressBar;
    private boolean hadError = false;
    private String errorMsg = "";
    private Integer serviceId;
    private InvoicesFragment invoicesFragment;
    private final String mUrl =
            "/services/invoices/";

    private final InvoicesListAdapter mAdapter;
    public InvoicesListLoader(InvoicesListAdapter adapter) {
        mAdapter = adapter;
        this.invoicesFragment = invoicesFragment;
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
    protected ArrayList<Invoice> doInBackground(URL... params) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mAdapter.getmContext());
        String ip_server = sp.getString("ip_server", "NA");
        String myUrl = "http://"+ip_server+mUrl+getServiceId();
        myUrl = Network.addAuthParams(myUrl, mAdapter.getmContext());
        InputStream source = retrieveStream(myUrl);
        Reader reader = null;
        ArrayList<Invoice> resultados = new ArrayList<Invoice>();
        try {
            if (source == null){
                throw new Exception("Error en la comunicacion al servidor");
            }
            reader = new InputStreamReader(source);
            Gson gson = new Gson();
            JsonInvoicesResult jsonResult = gson.fromJson(reader,JsonInvoicesResult.class);
            if (!jsonResult.getStatus().equals("ok"))
                new RuntimeException("Error al llamar al backend");
            //TextView total = (TextView) paymentsFragment.getView().findViewById(R.id.payment_list_total);
            //total.setText(jsonResult.getGran_total());
            Invoice[] result = jsonResult.getInvoices();

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
    protected void onPostExecute(ArrayList<Invoice> entries) {
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
}
