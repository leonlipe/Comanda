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
import redleon.net.comanda.model.Extra;

/**
 * Created by leon on 24/06/15.
 */
public class ExtraArrayAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Extra> mItems;


    public ExtraArrayAdapter(Context context) {
        //super( context, R.layout.extra_dialog_row, items);
       // setmItems(items);
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ExtraArrayAdapter(Context context, ArrayList<Extra> items) {
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
                    R.layout.extra_dialog_row, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView descriptionText = (TextView) itemView.findViewById(R.id.extra_description);
        String description = getmItems().get(position).getDescription().toString();
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

    public ArrayList<Extra> getmItems() {
        return mItems;
    }

    public void setmItems(ArrayList<Extra> mItems) {
        this.mItems = mItems;
    }
}
