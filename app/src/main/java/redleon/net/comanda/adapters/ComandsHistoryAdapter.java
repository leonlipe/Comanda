package redleon.net.comanda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.order_dishes_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }


        ImageView image_icon = (ImageView) itemView.findViewById(R.id.imageIcon);
        TextView titleText = (TextView) itemView.findViewById(R.id.listTitle);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.listDescription);
        TextView infoText = (TextView) itemView.findViewById(R.id.listInfo);
        TextView helpText = (TextView) itemView.findViewById(R.id.helptext);

        String title = mEntries.get(position).getDish_name();
        titleText.setText(title);
        String description =
                mEntries.get(position).getDish_desc();
        String sInfoText;
        if (mEntries.get(position).getStatus().equals("Ordenado")){
            sInfoText = mEntries.get(position).getStatus() + " para envío a  " + mEntries.get(position).getPlace_name();
            helpText.setText("Deslice para borrar.");
        }else{
            sInfoText = mEntries.get(position).getStatus() + " en " + mEntries.get(position).getPlace_name();
            helpText.setText("El platillo no se puede borrar.");

        }
        descriptionText.setText(description);
        infoText.setText(sInfoText);


        if (mEntries.get(position).getStatus().equals("Terminado")){
            image_icon.setImageResource(R.drawable.dish_ready);
        }else {
            image_icon.setImageResource(R.drawable.dish_not_ready);
        }
       // TextView separatorView = (TextView) view.findViewById(R.id.separator);

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
