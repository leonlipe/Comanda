package redleon.net.comanda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.model.OrderDishesData;

/**
 * Created by leon on 26/06/15.
 */
public class ComandsHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<OrderDishesData> mEntries = new ArrayList<OrderDishesData>();

    public ComandsHistoryAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        return mEntries.size();
    }

    @Override
    public Object getItem(int position) {

        return mEntries.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        RelativeLayout itemView;
        if (convertView == null) {
            itemView = (RelativeLayout) mLayoutInflater.inflate(
                    R.layout.order_dishes_list, parent, false);

        } else {
            itemView = (RelativeLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);

        String title = mEntries.get(position).getDish_name();
        titleText.setText(title);
        String description =
                mEntries.get(position).getDish_desc();
        if (description.trim().length() == 0) {
            description = "Sorry, no description for this image.";
        }
        descriptionText.setText(description);

        return itemView;
    }

    public void upDateEntries(ArrayList<OrderDishesData> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

}
