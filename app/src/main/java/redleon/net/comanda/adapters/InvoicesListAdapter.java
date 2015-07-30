package redleon.net.comanda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import redleon.net.comanda.R;
import redleon.net.comanda.model.Invoice;

/**
 * Created by leon on 19/05/15.
 */
public class InvoicesListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Invoice> mEntries = new ArrayList<Invoice>();

    public InvoicesListAdapter(Context context) {
        setmContext(context);
        mLayoutInflater = (LayoutInflater) getmContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public int getCount() {

        return mEntries.size();
    }

    @Override
    public Invoice getItem(int position) {

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
                    R.layout.fragment_invoices_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView idText = (TextView) itemView.findViewById(R.id.invoice_id);
        TextView dateText = (TextView) itemView.findViewById(R.id.invoice_date);
        TextView nameText = (TextView) itemView.findViewById(R.id.invoice_name);
        TextView rfcText = (TextView) itemView.findViewById(R.id.invoice_rfc);

        idText.setText("Ticket no: "+mEntries.get(position).getId().toString());
        dateText.setText(mEntries.get(position).getDate_made());
        nameText.setText(mEntries.get(position).getName());
        rfcText.setText(mEntries.get(position).getRfc());
        return itemView;
    }

    public void upDateEntries(ArrayList<Invoice> entries) {
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
