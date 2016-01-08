package redleon.net.comanda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import redleon.net.comanda.R;
import redleon.net.comanda.model.PaymentsResult;

/**
 * Created by leon on 19/05/15.
 */
public class PaymentsListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<PaymentsResult> mEntries = new ArrayList<PaymentsResult>();

    public PaymentsListAdapter(Context context) {
        setmContext(context);
        mLayoutInflater = (LayoutInflater) getmContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }




    @Override
    public int getCount() {

        return mEntries.size();
    }

    @Override
    public PaymentsResult getItem(int position) {

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
                    R.layout.fragment_payments_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.payment_title);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.payment_desc);
        TextView totalText = (TextView) itemView.findViewById(R.id.payment_total);
        ImageView personIcon = (ImageView) itemView.findViewById(R.id.imageIcon);

        String title = "Persona no " + mEntries.get(position).getDiner_number().toString();
        titleText.setText(title);
        String description = "Servicio: "+ mEntries.get(position).getStatus_desc();

        if (mEntries.get(position).getStatus_desc().equals("Activo")){
            personIcon.setImageResource(R.drawable.person);
        }else {
            personIcon.setImageResource(R.drawable.person_closed);
        }

        descriptionText.setText(description);

        String total = "Total de pedidos: "+ mEntries.get(position).getOrder_dishes().length + " / Total de la persona: $ "+mEntries.get(position).getTotal();

        totalText.setText(total);
        return itemView;
    }

    public void upDateEntries(ArrayList<PaymentsResult> entries) {
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
