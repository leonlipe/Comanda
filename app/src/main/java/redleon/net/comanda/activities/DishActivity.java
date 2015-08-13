package redleon.net.comanda.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.DishSizeSpinerAdapter;
import redleon.net.comanda.adapters.ExtraArrayAdapter;
import redleon.net.comanda.dialogs.ExtraIngredientDialog;
import redleon.net.comanda.model.DishSize;
import redleon.net.comanda.model.DishToOrder;
import redleon.net.comanda.model.Extra;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

public class DishActivity extends ActionBarActivity {

    public final static String DISH_ID = "net.redleon.DISH_ID";
    public final static String DINER_ID = "net.redleon.DINER_ID";
    public final static String SERVICE_ID = "net.redleon.SERVICE_ID";

    private Integer dishId;
    private Integer dinerId;
    private Integer serviceId;
    private ArrayList<Extra> mExtras = new ArrayList<Extra>();
    private ArrayList<Extra> extrasForDish = new ArrayList<Extra>();
    private BaseAdapter extrasForDishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dish);
        Intent intent = getIntent();
        setDishId(intent.getIntExtra(DISH_ID, 0));
        setDinerId(intent.getIntExtra(DINER_ID, 0));
        setServiceId(intent.getIntExtra(SERVICE_ID, 0));
        final Activity me = this;
        final ListView listview = (ListView) findViewById(R.id.extras_list);

        extrasForDishAdapter = new ExtraArrayAdapter(
                getApplicationContext(), extrasForDish);
        listview.setAdapter(extrasForDishAdapter);



        Spinner spinner = (Spinner) findViewById(R.id.dish_spin_time);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HttpClient.get("/extras_for_select.json", Network.makeAuthParams(me), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("response");
                    // Do something with the response
                    if (sResponse.equals("ok")) {
                        JSONArray extras = response.getJSONArray("data");
                       // mExtras = new Extra[extras.length()];

                        for (int x=0;x<extras.length();x++){
                            mExtras.add(new Extra(extras.getJSONObject(x).getInt("id"), extras.getJSONObject(x).getString("key"), extras.getJSONObject(x).getString("description")));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());

        HttpClient.get("/dishes/get_sizes/"+getDishId()+".json", Network.makeAuthParams(me), new JsonHttpResponseHandler() {
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
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());

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
     /*   CharSequence[] extras = new CharSequence[mExtras.length];
        for(int x = 0; x<mExtras.length; x++){
            extras[x]=mExtras[x].getDescription();
        }*/
        System.out.println("ZZZZZ:");
        System.out.println(mExtras);
        ((ExtraIngredientDialog) extraIngredient).setItems(mExtras);
        extraIngredient.show(getSupportFragmentManager(),"extraIngredient");

    }

    public void addDish(View view) {
        //TODO: Code for adding a dish to an order
        final Activity me = this;
        Spinner size_spinner = (Spinner)findViewById(R.id.dish_spin_size);
        Spinner tiime_spinner = (Spinner)findViewById(R.id.dish_spin_time);
        TextView notes = (TextView)findViewById(R.id.dish_edit_notes);
        DishSize size = (DishSize) size_spinner.getSelectedItem();
        CharSequence tiime = (CharSequence) tiime_spinner.getSelectedItem();

        DishToOrder dishToOrder = new DishToOrder();
        dishToOrder.setServiceId(getServiceId());
        dishToOrder.setDinerId(getDinerId());
        dishToOrder.setNotes(notes.getText().toString());
        dishToOrder.setDishtiime(Integer.valueOf((String) tiime_spinner.getSelectedItem()));

        dishToOrder.setDishsize(((DishSize) size_spinner.getSelectedItem()).getId());


        ListView extras_list = (ListView) findViewById(R.id.extras_list);
        String[] picker = new String[extras_list.getCount()];
        for(int x = 0; x<extras_list.getCount();x++){
            Extra ex = (Extra) extras_list.getAdapter().getItem(x);
            picker[x]=ex.getId().toString();
        }

        dishToOrder.setPicker(picker);

        String data = new Gson().toJson(dishToOrder);

        RequestParams params = Network.makeAuthParams(this);
         params.put("data", data);

        HttpClient.post("/add_dish_to_order/"+getDishId().toString(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("status");

                    if (sResponse.equals("ok")) {

                    }else{
                        Toast.makeText(me, response.getString("status"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(me, "El platillo se agregó correctamente.",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject){
                Toast.makeText(me, "Ocurrio un error inesperado:"+throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        },getBaseContext());


        this.finish();
    }


    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public void onSelectExtra(Extra selectedValue) {
        if (!extrasForDish.contains(selectedValue)) {
            extrasForDish.add(selectedValue);
            extrasForDishAdapter.notifyDataSetChanged();
        }
        System.out.println("Vals: "+ selectedValue.toString());

    }

    public Integer getDinerId() {
        return dinerId;
    }

    public void setDinerId(Integer dinerId) {
        this.dinerId = dinerId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}
