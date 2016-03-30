package redleon.net.comanda.adapters;


import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import redleon.net.comanda.R;
import redleon.net.comanda.model.DishSize;

/**
 * Created by leon on 27/05/15.
 */
public class DishSizeSpinerAdapter extends ArrayAdapter<DishSize> {

    private List<DishSize> items;
    private Activity activity;

    public DishSizeSpinerAdapter(Activity activity, List<DishSize> items) {
        super(activity, android.R.layout.simple_list_item_1, items);
        this.items = items;
        this.activity = activity;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        System.out.println("getDropDownView");
       /* TextView v = (TextView) super.getView(position, convertView, parent);

        if (v == null) {
            v = new TextView(activity);
        }
        v.setTextColor(Color.BLACK);
        v.setText(items.get(position).getDescription());
        return v;*/

        return getCustomView(position, convertView, parent);
    }

    @Override
    public DishSize getItem(int position) {
        System.out.println("getItem");
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        System.out.println("getView");
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.spinner_items, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.dish_size);
        lbl.setTextColor(Color.BLACK);
        lbl.setText(items.get(position).getDescription());
        return convertView;
    }
}