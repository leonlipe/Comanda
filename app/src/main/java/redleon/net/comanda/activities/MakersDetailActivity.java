package redleon.net.comanda.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import redleon.net.comanda.R;
import redleon.net.comanda.adapters.MakersDetailListAdapter;
import redleon.net.comanda.loaders.MakersDetailLoader;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.network.HttpClient;

public class MakersDetailActivity extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private Integer commandId;
    public static final String COMMAND_ID = "net.redleon.COMMAND_ID";
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makers_detail);

        Intent intent = getIntent();

        setCommandId(intent.getIntExtra(COMMAND_ID, 0));
        MakersDetailListAdapter makersDetailListAdapter = new MakersDetailListAdapter(this);
        listView = (ListView) findViewById(android.R.id.list);

        listView.setAdapter(makersDetailListAdapter);
        listView.setOnItemClickListener(this);

        MakersDetailLoader loadData = new MakersDetailLoader(makersDetailListAdapter);
        loadData.setDishId(getCommandId());
        loadData.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_makers_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final MakersDetailActivity me = this;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_dispatch_command) {

            HttpClient.post("commands/completed/" + getCommandId(), null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // Pull out the first event on the public timeline

                    Toast.makeText(me, "Se ha despachado la comanda", Toast.LENGTH_SHORT).show();
                }


            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final MakersDetailActivity me = this;
        HttpClient.post("order_dishes/setready/" + ((Dish) parent.getItemAtPosition(position)).getOrder_dishes_id(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                Toast.makeText(me, "Se ha despachado el platillo", Toast.LENGTH_SHORT).show();
            }


        });



    }


}
