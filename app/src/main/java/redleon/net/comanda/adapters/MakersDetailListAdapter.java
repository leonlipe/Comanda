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
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.Extra;

/**
 * Created by leon on 22/07/15.
 */
public class MakersDetailListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Dish> mEntries = new ArrayList<Dish>();

    public MakersDetailListAdapter(Context context) {
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
                    R.layout.activity_makers_detail_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView dish_desc_text = (TextView) itemView.findViewById(R.id.dish_desc);
        TextView dish_name_text = (TextView) itemView.findViewById(R.id.dish_name);
        TextView dish_notes_text = (TextView) itemView.findViewById(R.id.dish_notes);
        TextView dish_extras_text = (TextView) itemView.findViewById(R.id.dish_extras);
        String dish_desc = mEntries.get(position).getStatus_desc().toString();
        String dish_name = mEntries.get(position).getName();
        String dish_extra = "";
        Extra[] extras = mEntries.get(position).getExtras();
        for(int x = 0; x < extras.length; x++){
            dish_extra = dish_extra.concat(extras[x].getDescription());
        }
        if (extras.length <= 0){
            dish_extra = "Sin ingredientes extas.";
        }

//        String comandaInfo = "Pendientes: "+mEntries.get(position).getPending().toString()+" Enviadas: "+mEntries.get(position).getSended().toString()+" Terminadas: "+mEntries.get(position).getFinished().toString();
//        titleText.setText(title);
//        String description = "Servicio: "+
//                mEntries.get(position).getStatus_desc();
//
//        descriptionText.setText(description);
//        info.setText(comandaInfo);
        dish_desc_text.setText(dish_desc);
        dish_name_text.setText(dish_name);
        dish_notes_text.setText(mEntries.get(position).getNotes());
        dish_extras_text.setText(dish_extra);
        return itemView;
    }

    public void upDateEntries(ArrayList<Dish> entries) {
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
