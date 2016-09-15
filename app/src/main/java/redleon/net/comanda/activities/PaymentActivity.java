package redleon.net.comanda.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

public class PaymentActivity extends ActionBarActivity {
    public final static String DINERS_ARRAY = "net.redleon.DINERS_ARRAY";
    public final static String GRAN_TOTAL = "net.redleon.GRAN_TOTAL";
    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";

    private ArrayList<Integer> idsArray;
 //   private BigDecimal granTotal;
    private Integer serviceId;
    private String paymentMethod;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressBar = new ProgressDialog(this);
        setContentView(R.layout.activity_payment);
        Intent intent = getIntent();
        setIdsArray(intent.getIntegerArrayListExtra(DINERS_ARRAY));
      //  setGranTotal(new BigDecimal(intent.getStringExtra(GRAN_TOTAL)));
        setServiceId(intent.getIntExtra(SERVICE_ID,0));

        Spinner spinner = (Spinner) findViewById(R.id.payment_method_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_method_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView granTotalText = (TextView) findViewById(R.id.gran_total_text);
        granTotalText.setText(intent.getStringExtra(GRAN_TOTAL));
        Spinner payment_method = (Spinner) findViewById(R.id.payment_method_spin);
        payment_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPaymentMethod(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Integer> getIdsArray() {
        return idsArray;
    }

    public void setIdsArray(ArrayList<Integer> idsArray) {
        this.idsArray = idsArray;
    }

//    public BigDecimal getGranTotal() {
//        return granTotal;
//    }
//
//    public void setGranTotal(BigDecimal granTotal) {
//        this.granTotal = granTotal;
//    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public class PaymentData{

        public PaymentData(String pKind, String pPaymentForm){
            kind = pKind;
            payment_form = pPaymentForm;
        }
        private String kind;
        private String payment_form;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getPayment_form() {
            return payment_form;
        }

        public void setPayment_form(String payment_form) {
            this.payment_form = payment_form;
        }
    }

    public void doPayment(View view) {

        final PaymentActivity me = this;


        RequestParams params = Network.makeAuthParams(this);
        params.put("data",new Gson().toJson(new PaymentData("",getPaymentMethod())) );
        params.put("ids", new Gson().toJson(getIdsArray()));

        progressBar.setCancelable(false);
        progressBar.setMessage("Consultado información...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setIndeterminate(true);
        progressBar.show();
        HttpClient.post("/services/do_payment_together/" + getServiceId(), params, new JsonHttpResponseHandler() {
            //@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressBar.dismiss();

                try {
                    String sResponse = response.getString("status");
                    // Do something with the response
                    if (sResponse.equals("ok")) {

                    }else{
                        Toast.makeText(me, response.getString("status"), Toast.LENGTH_SHORT).show();
                        return;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(me, "Error en el JSON", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(me, "Se generó correctamente la cuenta.", Toast.LENGTH_SHORT).show();
                me.finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                progressBar.dismiss();
                Toast.makeText(me, "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        }, getBaseContext());
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }


    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        progressBar.dismiss();
    }
    @Override
    protected void onStop() {
        super.onStop();  // Always call the superclass method first
        progressBar.dismiss();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();  // Always call the superclass method first
        progressBar.dismiss();
    }
}
