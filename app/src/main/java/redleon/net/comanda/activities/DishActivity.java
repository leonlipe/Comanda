package redleon.net.comanda.activities;

import android.app.Activity;
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

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.DishSizeSpinerAdapter;
import redleon.net.comanda.dialogs.ExtraIngredientDialog;
import redleon.net.comanda.model.DishSize;
import redleon.net.comanda.model.Extra;
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
        final Activity me = this;

        Spinner spinner = (Spinner) findViewById(R.id.dish_spin_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        HttpClient.get("dishes/get_sizes/"+getDishId()+".json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {

                    // Do something with the response

                        JSONArray extras = response.getJSONArray("data");
                        System.out.println(extras.toString());
                        List<DishSize> sizes = new ArrayList<DishSize>();
                        for (int x=0;x<extras.length();x++){
                            sizes.add(new DishSize(extras.getJSONObject(x).getInt("id"), extras.getJSONObject(x).getString("description")));
                        }
                    System.out.println(sizes.toString());
                    Spinner spinner = (Spinner) findViewById(R.id.dish_spin_size);
                    //DishSizeSpinerAdapter dAdapter = new DishSizeSpinerAdapter(me, sizes);
                    ArrayAdapter<DishSize> dAdapter = new ArrayAdapter<DishSize>(me,R.layout.dish_size_spinner, sizes);

                    spinner.setAdapter(dAdapter);
                    //dAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        Spinner size_spinner = (Spinner)findViewById(R.id.dish_spin_size);
        Spinner tiime_spinner = (Spinner)findViewById(R.id.dish_spin_time);
        DishSize size = (DishSize) size_spinner.getSelectedItem();
        CharSequence tiime = (CharSequence) tiime_spinner.getSelectedItem();



        this.finish();
    }


    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
