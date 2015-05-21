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
import redleon.net.comanda.model.DinersResult;

/**
 * Created by leon on 19/05/15.
 */
public class ComandasListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<DinersResult> mEntries = new ArrayList<DinersResult>();

    public ComandasListAdapter(Context context) {
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
        LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.fragment_diners_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.diner_title);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.diner_desc);

        String title = mEntries.get(position).getDiner_number().toString();
        titleText.setText(title);
        String description =
                mEntries.get(position).getStatus_desc();
        if (description.trim().length() == 0) {
            description = "Sorry, no description for this image.";
        }
        descriptionText.setText(description);

        return itemView;
    }

    public void upDateEntries(ArrayList<DinersResult> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }
}
