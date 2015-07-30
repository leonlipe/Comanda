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
import redleon.net.comanda.model.TablesResult;

/**
 * Created by leon on 24/04/15.
 */
public class TablesListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<TablesResult> mEntries = new ArrayList<TablesResult>();
    //private final ImageDownloader mImageDownloader;                      #3

    public TablesListAdapter(Context context) {
        setmContext(context);
        mLayoutInflater = (LayoutInflater) getmContext()
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
                    R.layout.table_layout_list, parent, false);

        } else {
            itemView = (RelativeLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);
        TextView statusText = (TextView) itemView.findViewById(R.id.tableStatus);

        String title = mEntries.get(position).getKey();
        titleText.setText(title);
        String description =
                mEntries.get(position).getDescription();
        statusText.setText(mEntries.get(position).getStatus_desc());
        descriptionText.setText(description);

        return itemView;
    }

    public void upDateEntries(ArrayList<TablesResult> entries) {
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
