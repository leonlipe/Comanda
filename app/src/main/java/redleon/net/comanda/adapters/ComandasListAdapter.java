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
import redleon.net.comanda.model.ComandasResult;

/**
 * Created by leon on 19/05/15.
 */
public class ComandasListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ComandasResult> mEntries = new ArrayList<ComandasResult>();

    public ComandasListAdapter(Context context) {
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
                    R.layout.fragment_comandas_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.comanda_title);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.comanda_desc);
        TextView info = (TextView) itemView.findViewById(R.id.comanda_info);

        String title = "Persona no "+mEntries.get(position).getDiner_number().toString();
        String comandaInfo = "Pendientes: "+mEntries.get(position).getPending().toString()+" Enviadas: "+mEntries.get(position).getSended().toString()+" Terminadas: "+mEntries.get(position).getFinished().toString();
        titleText.setText(title);
        String description = "Servicio: "+
                mEntries.get(position).getStatus_desc();
        if (description.trim().length() == 0) {
            description = "Sorry, no description for this image.";
        }
        descriptionText.setText(description);
        info.setText(comandaInfo);
        return itemView;
    }

    public void upDateEntries(ArrayList<ComandasResult> entries) {
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
