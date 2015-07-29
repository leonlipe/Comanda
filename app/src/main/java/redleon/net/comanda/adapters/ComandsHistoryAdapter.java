package redleon.net.comanda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
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
        TextView infoText = (TextView) itemView.findViewById(R.id.listInfo);

        String title = mEntries.get(position).getDish_name();
        titleText.setText(title);
        String description =
                mEntries.get(position).getDish_desc();
        String sInfoText = mEntries.get(position).getStatus() + " en " + mEntries.get(position).getPlace_name();
        if (description.trim().length() == 0) {
            description = "Sorry, no description for this image.";
        }
        descriptionText.setText(description);
        infoText.setText(sInfoText);
        return itemView;
    }

    public void upDateEntries(ArrayList<OrderDishesData> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

}
