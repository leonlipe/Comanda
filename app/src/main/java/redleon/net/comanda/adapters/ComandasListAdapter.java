package redleon.net.comanda.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.ServicesActivity;
import redleon.net.comanda.dialogs.TableNumberDialog;
import redleon.net.comanda.dialogs.TableNumberDialogForDiner;
import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.model.ComandasResult;
import redleon.net.comanda.model.DinersResult;
import redleon.net.comanda.model.Table;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 19/05/15.
 */
public class ComandasListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<ComandasResult> mEntries = new ArrayList<ComandasResult>();
    private ComandasFragment comandasFragment;
    private ArrayList<Table> mTables = new ArrayList<Table>();
    private Context ctx;

    public ComandasListAdapter(Context context, ComandasFragment comandasFragment) {
        setmContext(context);
        this.comandasFragment = comandasFragment;
        this.ctx=context;
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
                    R.layout.fragment_comandas_list, parent, false);

        } else {
            itemView = (LinearLayout) convertView;
        }

        HttpClient.get("/listtables.json", Network.makeAuthParams(itemView.getContext()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Pull out the first event on the public timeline

                try {
                    String sResponse = response.getString("response");
                    // Do something with the response
                    if (sResponse.equals("ok")) {
                        JSONArray extras = response.getJSONArray("data");
                        // mExtras = new Extra[extras.length()];

                        for (int x = 0; x < extras.length(); x++) {
                            mTables.add(new Table(extras.getJSONObject(x).getInt("id"), extras.getJSONObject(x).getInt("number")));
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                Toast.makeText(itemView.getContext(), "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

            }


        }, itemView.getContext());


        TextView titleText = (TextView) itemView.findViewById(R.id.comanda_title);
        TextView descriptionText = (TextView) itemView.findViewById(R.id.comanda_desc);
        TextView info = (TextView) itemView.findViewById(R.id.comanda_info);
        ImageView personIcon = (ImageView) itemView.findViewById(R.id.imageIcon);


        String title = "Persona no "+mEntries.get(position).getDiner_number().toString();
        String comandaInfo = "Pendientes: "+mEntries.get(position).getPending().toString()+" Enviadas: "+mEntries.get(position).getSended().toString()+" Terminadas: "+mEntries.get(position).getFinished().toString();
        titleText.setText(title);
        String description = "Servicio: "+ mEntries.get(position).getStatus_desc();
        if (mEntries.get(position).getStatus_desc().equals("Activo")){
            personIcon.setImageResource(R.drawable.person);
        }else {
            personIcon.setImageResource(R.drawable.person_closed);
        }

        Button btnReassign = (Button) itemView.findViewById(R.id.btn_reassign_person);
        final ComandasResult comandasResult = mEntries.get(position);

        btnReassign.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {


                DialogFragment tableNumber = new TableNumberDialogForDiner();


                ((TableNumberDialogForDiner) tableNumber).setItems(mTables);
                ((TableNumberDialogForDiner) tableNumber).setOrigin(comandasResult.getId());
                ((TableNumberDialogForDiner) tableNumber).setComandasFragment(comandasFragment);

                tableNumber.show(comandasFragment.getActivity().getSupportFragmentManager()
                        , "tableNumber");






            }
        });




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
