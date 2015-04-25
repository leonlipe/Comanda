package redleon.net.comanda;

import android.app.ListActivity;
import android.os.Bundle;

import redleon.net.comanda.adapters.TablesListAdapter;


public class TablesActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);

        TablesListAdapter adapter = new TablesListAdapter(this);
        setListAdapter(adapter);




    }





}
