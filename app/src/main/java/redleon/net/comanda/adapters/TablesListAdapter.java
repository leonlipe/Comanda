package redleon.net.comanda.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        CardView itemView;
        if (convertView == null) {
            itemView = (CardView) mLayoutInflater.inflate(
                    R.layout.activity_tables_list, parent, false);

        } else {
            itemView = (CardView) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);
        TextView statusText = (TextView) itemView.findViewById(R.id.tableStatus);
        ImageView imageIcon = (ImageView) itemView.findViewById(R.id.imageIcon);

        String title = mEntries.get(position).getDescription();
        titleText.setText(title);
        String description =
                mEntries.get(position).getStatus_desc();
        statusText.setText(mEntries.get(position).getMesero());
        if (mEntries.get(position).getStatus_desc().equals("Ocupada")){
            imageIcon.setImageResource(R.drawable.dish_and_fork_no);
        }else{
            imageIcon.setImageResource(R.drawable.dish_and_fork_ok);

        }
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
