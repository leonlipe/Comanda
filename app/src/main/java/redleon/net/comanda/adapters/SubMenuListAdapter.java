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
import redleon.net.comanda.model.MenuItem;

/**
 * Created by leon on 24/04/15.
 */
public class SubMenuListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MenuItem> mEntries = new ArrayList<MenuItem>();

    public SubMenuListAdapter(Context context) {
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
        LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.activity_sub_menu_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView menuNameText = (TextView) itemView.findViewById(R.id.menu_name);
        TextView menuDescriptionText = (TextView) itemView.findViewById(R.id.menu_description);
        menuNameText.setText(mEntries.get(position).getName());
        menuDescriptionText.setText(mEntries.get(position).getDescription());
        return itemView;
    }

    public void upDateEntries(ArrayList<MenuItem> entries) {
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
