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
import redleon.net.comanda.model.MakersCommandItem;

/**
 * Created by leon on 17/07/15.
 */
public class MakersListAdapterOld extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MakersCommandItem> mEntries = new ArrayList<MakersCommandItem>();

    public MakersListAdapterOld(Context context) {
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
                    R.layout.activity_makers_commands_list_old, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView command_number_text = (TextView) itemView.findViewById(R.id.command_number);
        TextView name_waitres_text = (TextView) itemView.findViewById(R.id.command_waitres);
        TextView comand_table_text = (TextView) itemView.findViewById(R.id.command_table);

        String command_number = "Comanda no "+mEntries.get(position).getId().toString();
        String name_waitres = mEntries.get(position).getName();


        command_number_text.setText(name_waitres);
        name_waitres_text.setText(command_number);
        comand_table_text.setText(mEntries.get(position).getTable());
        return itemView;
    }

    public void upDateEntries(ArrayList<MakersCommandItem> entries) {
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
