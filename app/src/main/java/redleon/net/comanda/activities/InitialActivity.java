package redleon.net.comanda.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import redleon.net.comanda.ComandaApp;
import redleon.net.comanda.R;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.Tiime;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Encoder;
import redleon.net.comanda.utils.Network;

public class InitialActivity extends ActionBarActivity {
    public static final String PLACE_COCINAS = "COC";
    public static final String PLACE_PIZZERIA = "PIZ";
    public static final String PLACE_MESAS = "MES";
    public static final String PLACE_BARRA = "BAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        goToActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial, menu);
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
            Intent intent = new Intent(this, AppPreferences.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(View view) {
        goToActivity();
    }

    public void goToActivity(){
        System.out.println(Encoder.encode("2015-08-12T13:22:00","XXXXXXXXXXXXXXXXXX"));
        final InitialActivity mySelf = this;
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final Boolean shall_pass = true;
        // Login client

        HttpClient.get("/login_mobile.json", Network.makeAuthParams(mySelf), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {

                    String sResponse = response.getString("status");
                    String sMagicNo = response.getString("magicno");
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("magicno", sMagicNo);
                    editor.commit();
                    showActivity(sp.getString("user_type", "NA"));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mySelf, "Ocurrio un error inesperado", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(mySelf, "Ocurrio un error inesperado: " + throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        }, getBaseContext());



    }
    public void showActivity( String user_type) {

        if (user_type.equals(PLACE_COCINAS)) {
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.PLACE_KEY, "COC");
            startActivity(intent);
        } else if (user_type.equals(PLACE_PIZZERIA)) {
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.PLACE_KEY, "PIZ");
            startActivity(intent);
        } else if (user_type.equals(PLACE_MESAS)) {
            Intent intent = new Intent(this, TablesActivity.class);
            startActivity(intent);
        } else if (user_type.equals(PLACE_BARRA)) {
            Intent intent = new Intent(this, MakersActivity.class);
            intent.putExtra(MakersActivity.PLACE_KEY, "BAA");
            startActivity(intent);
        }
    }
}
