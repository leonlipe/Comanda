package redleon.net.comanda.adapters;

/**
 * Created by leon on 21/05/15.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.DishActivity;
import redleon.net.comanda.activities.MenuActivity;
import redleon.net.comanda.activities.ServicesActivity;
import redleon.net.comanda.model.Dish;

public class MenuFragmentListAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<ArrayList<Dish>> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems;
    private ArrayList<Dish> child;

    public MenuFragmentListAdapter(ArrayList<String> parents, ArrayList<ArrayList<Dish>> childern) {
        this.parentItems = parents;
        this.childtems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<Dish>) childtems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_menu_group, null);
        }

        textView = (TextView) convertView.findViewById(R.id.textView1);
        textView.setText(child.get(childPosition).getDescription());

        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
               // Toast.makeText(activity, child.get(childPosition).getId().toString(),
               //         Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), DishActivity.class);
                intent.putExtra(DishActivity.DISH_ID,child.get(childPosition).getId() );
                view.getContext().startActivity(intent);
             
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_menu_child, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<Dish>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}