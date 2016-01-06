package redleon.net.comanda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.model.Table;

/**
 * Created by leon on 24/06/15.
 */
public class TableArrayAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Table> mItems;


    public TableArrayAdapter(Context context) {
        //super( context, R.layout.extra_dialog_row, items);
       // setmItems(items);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public TableArrayAdapter(Context context, ArrayList<Table> items) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mItems = items;

    }

    public View getView(int position, View convertView,
                        ViewGroup parent) {

        LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.table_dialog_row, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView descriptionText = (TextView) itemView.findViewById(R.id.table_description);
        String description = getmItems().get(position).getNumber().toString();
        descriptionText.setText(description);


        return itemView;
    }



    @Override
    public int getCount() {

        return getmItems().size();
    }

    @Override
    public Object getItem(int position) {

        return getmItems().get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public ArrayList<Table> getmItems() {
        return mItems;
    }

    public void setmItems(ArrayList<Table> mItems) {
        this.mItems = mItems;
    }
}
