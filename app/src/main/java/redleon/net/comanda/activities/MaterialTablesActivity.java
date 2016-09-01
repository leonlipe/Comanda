package redleon.net.comanda.activities;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import redleon.net.comanda.R;
import redleon.net.comanda.adapters.TablesRecyclerViewAdapter;
import redleon.net.comanda.loaders.TablesRecyclerViewLoader;

public class MaterialTablesActivity extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private TablesRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeContainer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_tables);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)


        mAdapter = new TablesRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        TablesRecyclerViewLoader tablesListLoader = new TablesRecyclerViewLoader(mAdapter);
        tablesListLoader.execute();
    }


    //@Override
    public void onRefresh() {
        TablesRecyclerViewLoader tablesListLoader = new TablesRecyclerViewLoader(mAdapter);
        tablesListLoader.execute();
        swipeContainer.setRefreshing(false);

    }

}
