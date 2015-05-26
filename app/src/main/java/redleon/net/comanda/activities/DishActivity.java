package redleon.net.comanda.activities;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import redleon.net.comanda.ComandaApp;
import redleon.net.comanda.R;
import redleon.net.comanda.dialogs.ExtraIngredientDialog;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.Extra;
import redleon.net.comanda.model.Tiime;
import redleon.net.comanda.network.HttpClient;

public class DishActivity extends ActionBarActivity {

    public final static String DISH_ID = "net.redleon.DISH_ID";

    private Integer dishId;
    private Extra[] mExtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        Intent intent = getIntent();
        setDishId(intent.getIntExtra(DISH_ID, 0));

        Spinner spinner = (Spinner) findViewById(R.id.dish_spin_time);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HttpClient.get("extras_for_select.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {




                    String sResponse = response.getString("response");
                    // Do something with the response
                    if (sResponse.equals("ok")) {
                        JSONArray extras = response.getJSONArray("data");
                        mExtras = new Extra[extras.length()];

                        for (int x=0;x<extras.length();x++){
                            mExtras[x]=new Extra(extras.getJSONObject(x).getInt("id"), extras.getJSONObject(x).getString("key"), extras.getJSONObject(x).getString("description"));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dish, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add_extra:
                addExtra();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addExtra(){
        DialogFragment extraIngredient = new ExtraIngredientDialog();
        CharSequence[] extras = new CharSequence[mExtras.length];
        for(int x = 0; x<mExtras.length; x++){
            extras[x]=mExtras[x].getDescription();
        }
        ((ExtraIngredientDialog) extraIngredient).setItems(extras);
        extraIngredient.show(getSupportFragmentManager(),"extraIngredient");

    }

    public void addDish(View view) {
        //TODO: Code for adding a dish to an order
        this.finish();
    }


    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
