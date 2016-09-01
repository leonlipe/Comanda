package redleon.net.comanda.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.MakersDetailActivity;
import redleon.net.comanda.model.Dish;
import redleon.net.comanda.model.Extra;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

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
        final LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.activity_makers_detail_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }


        Button btnDelete = (Button) itemView.findViewById(R.id.btn_delete_dish);
        final Dish dish = mEntries.get(position);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = Network.makeAuthParams(itemView.getContext());
                final ProgressDialog progressBar;
                progressBar = new ProgressDialog(getmContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Consultado información...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setIndeterminate(true);
                progressBar.show();
                HttpClient.post("/diners/remove_dish_places/" + dish.getOrder_dishes_id().toString(), params, new JsonHttpResponseHandler() {
                    //@Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        progressBar.dismiss();
                        // Pull out the first event on the public timeline

                        try {
                            String sResponse = response.getString("status");
                            // Do something with the response
                            if (sResponse.equals("ok")) {
                                Toast.makeText(itemView.getContext(),
                                        "El platillo se quitó de la orden",
                                        Toast.LENGTH_SHORT).show();
                                // refresh
                                ((MakersDetailActivity) mContext).onRefresh();

                            } else {
                                Toast.makeText(itemView.getContext(),
                                        "Ocurrió un error: " + response.getString("reason"),
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                   // @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                        progressBar.dismiss();
                        Toast.makeText(itemView.getContext(), "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

                    }


                }, itemView.getContext());

            }
        });

        ImageView image_icon = (ImageView) itemView.findViewById(R.id.imageIcon);

        TextView dish_size_desc = (TextView) itemView.findViewById(R.id.dish_size);
        TextView dish_desc_text = (TextView) itemView.findViewById(R.id.dish_desc);
        TextView dish_name_text = (TextView) itemView.findViewById(R.id.dish_name);
        TextView dish_notes_text = (TextView) itemView.findViewById(R.id.dish_notes);
        TextView dish_extras_text = (TextView) itemView.findViewById(R.id.dish_extras);
        TextView dish_tiime_text = (TextView) itemView.findViewById(R.id.dish_tiime);
        String dish_desc = mEntries.get(position).getStatus_desc().toString();
        String dish_name = mEntries.get(position).getName();
        String dish_size = mEntries.get(position).getSize_desc();
        String dish_extra = "";
        Integer dish_time = mEntries.get(position).getDishtiime();
        Extra[] extras = mEntries.get(position).getExtras();
        for(int x = 0; x < extras.length; x++){
            dish_extra = dish_extra.concat(extras[x].getDescription());
            if (x > 0){
                dish_extra = dish_extra.concat(",");
            }

        }
        if (extras.length <= 0){
            dish_extra = "Sin ingredientes extas.";
        }
        btnDelete.setEnabled(false);
        if (mEntries.get(position).getStatus_desc().contains("Preparado")){
            image_icon.setImageResource(R.drawable.dish_ready);
        }else {
            image_icon.setImageResource(R.drawable.dish_not_ready);
            if (mEntries.get(position).getCancel_request() == 1){
                dish_desc = mEntries.get(position).getStatus_desc().toString()+" Se solicita cancelacion";
                btnDelete.setEnabled(true);
            }
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
        dish_size_desc.setText(dish_size);
        dish_tiime_text.setText(dish_time.toString());
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
