package redleon.net.comanda.adapters;

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

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.ServicesActivity;
import redleon.net.comanda.fragments.DinersFragment;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 19/05/15.
 */
public class DinersListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<DinersResult> mEntries = new ArrayList<DinersResult>();
    private DinersFragment dinersFragment;

    public DinersListAdapter(Context context, DinersFragment dinersFragment) {
        setmContext(context);
        this.dinersFragment = dinersFragment;
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
    public View getView(int position, final View convertView,
                        ViewGroup parent) {
        final LinearLayout itemView;
        if (convertView == null) {
            itemView = (LinearLayout) mLayoutInflater.inflate(
                    R.layout.fragment_diners_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        TextView titleText = (TextView) itemView.findViewById(R.id.diner_title);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.diner_desc);
        TextView dinerStatusText = (TextView) itemView.findViewById(R.id.diner_status);
        ImageView personIcon = (ImageView) itemView.findViewById(R.id.imageIcon);

        String title = mEntries.get(position).getDiner_number().toString();
        titleText.setText(title);
        String description =
                mEntries.get(position).getDiner_desc();

        descriptionText.setText(description);
        dinerStatusText.setText(mEntries.get(position).getStatus_desc());

        if (mEntries.get(position).getStatus_desc().equals("Activo")){
            personIcon.setImageResource(R.drawable.person);
        }else {
            personIcon.setImageResource(R.drawable.person_closed);
        }

        Button btnDelete = (Button) itemView.findViewById(R.id.btn_pay);
        final DinersResult dinersResult = mEntries.get(position);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpClient.post("/diners/remove/"+ dinersResult.getId().toString(), Network.makeAuthParams(itemView.getContext()), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // Pull out the first event on the public timeline

                        try {
                            String sResponse = response.getString("status");
                            // Do something with the response
                            if (sResponse.equals("ok")) {
                                Toast.makeText(itemView.getContext(),
                                        "La persona se eliminó del servicio",
                                        Toast.LENGTH_SHORT).show();
                                ServicesActivity servicesActivity = (ServicesActivity) dinersFragment.getActivity();
                                servicesActivity.updateForPersonChange();

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
                        Toast.makeText(itemView.getContext(), "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

                    }


                },itemView.getContext());




            }
        });

        return itemView;
    }

    public void upDateEntries(ArrayList<DinersResult> entries) {
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
