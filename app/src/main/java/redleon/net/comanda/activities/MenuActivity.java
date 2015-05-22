package redleon.net.comanda.activities;

import java.util.ArrayList;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import redleon.net.comanda.adapters.MenuFragmentListAdapter;
import redleon.net.comanda.model.Dish;


public class MenuActivity extends ExpandableListActivity{

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<ArrayList<Dish>> childItems = new ArrayList<ArrayList<Dish>>();

    private MenuFragmentListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // this is not really  necessary as ExpandableListActivity contains an ExpandableList
        //setContentView(R.layout.main);

        ExpandableListView expandableList = getExpandableListView(); // you can use (ExpandableListView) findViewById(R.id.list)

        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        MenuFragmentListAdapter adapter = new MenuFragmentListAdapter(parentItems, childItems);
        this.mAdapter = adapter;
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }

    public void setGroupParents() {
        parentItems.add("Android");
        parentItems.add("Core Java");

    }

    public void setChildData() {

        // Android
        ArrayList<Dish> child = new ArrayList<Dish>();
        child.add(new Dish(1,"Core"));
        child.add(new Dish(2,"Games"));
        childItems.add(child);

        // Core Java
        child = new ArrayList<Dish>();
        child.add(new Dish(3,"Apache"));
        child.add(new Dish(4,"Applet"));

        childItems.add(child);

        // Desktop Java
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id){
        System.out.println("Click on child");
      return true;
    }

}