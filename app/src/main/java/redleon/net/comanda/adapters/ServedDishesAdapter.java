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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import redleon.net.comanda.R;
import redleon.net.comanda.activities.ComandHistoryActivity;
import redleon.net.comanda.activities.ServerDishesActivity;
import redleon.net.comanda.model.OrderDishesData;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 26/06/15.
 */
public class ServedDishesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<OrderDishesData> mEntries = new ArrayList<OrderDishesData>();

    public ServedDishesAdapter(Context context) {
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
        final LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.activity_served_dishes_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        Button btnDelete = (Button) itemView.findViewById(R.id.btn_delete_dish);
        final OrderDishesData dish = mEntries.get(position);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressBar;
                progressBar = new ProgressDialog(getmContext());
                progressBar.setCancelable(false);
                progressBar.setMessage("Consultado información...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setIndeterminate(true);
                progressBar.show();
                HttpClient.post("/order_dishes/serve/" + dish.getId().toString(), Network.makeAuthParams(itemView.getContext()), new JsonHttpResponseHandler() {
                  //  @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        progressBar.dismiss();
                        // Pull out the first event on the public timeline

                        try {
                            String sResponse = response.getString("status");
                            // Do something with the response
                            if (sResponse.equals("ok")) {
                                Toast.makeText(itemView.getContext(),
                                        "El platillo se marcó como servidor",
                                        Toast.LENGTH_SHORT).show();
                                // refresh
                                ((ServerDishesActivity)mContext).onRefresh();

                            } else {
                                Toast.makeText(itemView.getContext(),
                                        "Ocurrió un error: " + response.getString("reason"),
                                        Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                        progressBar.dismiss();
                        Toast.makeText(itemView.getContext(), "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

                    }


                }, itemView.getContext());

            }
        });

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

        sInfoText = mEntries.get(position).getStatus() + " en " + mEntries.get(position).getPlace_name();


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
