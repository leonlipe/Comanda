package redleon.net.comanda;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import redleon.net.comanda.adapters.TablesListAdapter;
import redleon.net.comanda.loaders.TablesListLoader;


public class TablesActivity extends ListActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_tables);

            TablesListAdapter adapter = new TablesListAdapter(this);
            setListAdapter(adapter);

            TablesListLoader loadData = new TablesListLoader(adapter);
            loadData.execute();
        }catch(Exception e){
            e.printStackTrace();
        }



    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent = new Intent(this, ServicesActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "test");
        startActivity(intent);

        //  Uri uri = ContentUris.withAppendedId(this.getIntent().getData(), id);
        // your code

    }


}
