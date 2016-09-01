package redleon.net.comanda.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import org.json.JSONObject;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.ServicesActivity;
import redleon.net.comanda.adapters.TableArrayAdapter;
import redleon.net.comanda.fragments.ComandasFragment;
import redleon.net.comanda.model.Table;
import redleon.net.comanda.network.HttpClient;
import redleon.net.comanda.utils.Network;

/**
 * Created by leon on 05/01/16.
 */
public class TableNumberDialogForDiner extends DialogFragment implements     DialogInterface.OnClickListener {
    private Integer origin;
    private ArrayList<Table> items;
    private ComandasFragment comandasFragment;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //setmSelectedItems(new ArrayList<Extra>());  // Where we track the selected items
        final Context ctx = getActivity().getBaseContext();
        ListAdapter adapter = new TableArrayAdapter(getActivity().getApplicationContext(),getItems());


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Set the dialog title
        builder.setTitle(R.string.dialog_set_table)
                .setAdapter(adapter,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int item) {

                        Table checkedItem = getItems().get(item);


                        RequestParams params = Network.makeAuthParams(ctx);
                        params.put("table_id", checkedItem.getId());
                        HttpClient.post("/reassigndiner/" + origin, params, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                // Pull out the first event on the public timeline

                                try {
                                    String sResponse = response.getString("status");

                                    if (sResponse.equals("ok")) {

                                    } else {
                                        Toast.makeText(ctx, response.getString("reason"), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(ctx, "La persona se reasigno correctamente.", Toast.LENGTH_SHORT).show();
                                //refresh activity
                                ServicesActivity servicesActivity = (ServicesActivity) comandasFragment.getActivity();
                                servicesActivity.updateForPersonChange();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject jsonObject) {
                                Toast.makeText(ctx, "Ocurrio un error inesperado:" + throwable.getMessage(), Toast.LENGTH_LONG).show();

                            }


                        }, ctx);

                    }
                })

                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {

    }


    public ArrayList<Table> getItems() {
        return items;
    }

    public void setItems(ArrayList<Table> items) {
        this.items = items;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public ComandasFragment getComandasFragment() {
        return comandasFragment;
    }

    public void setComandasFragment(ComandasFragment comandasFragment) {
        this.comandasFragment = comandasFragment;
    }
}