package redleon.net.comanda.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.ListAdapter;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.activities.DishActivity;
import redleon.net.comanda.adapters.ExtraArrayAdapter;
import redleon.net.comanda.model.Extra;

/**
 * Created by leon on 26/05/15.
 */
public class ExtraIngredientDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private ArrayList<Extra> items;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //setmSelectedItems(new ArrayList<Extra>());  // Where we track the selected items
        System.out.println("ZZZZZ01:");

        ListAdapter adapter = new ExtraArrayAdapter(getActivity().getApplicationContext(),getItems());

        System.out.println("ZZZZZ02:");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Set the dialog title
        builder.setTitle(R.string.dialog_add_ingredient)
        .setAdapter(adapter,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,
                                int item) {
                //Toast.makeText(MyApp.this, "You selected: " + items[item], Toast.LENGTH_LONG).show();
                DishActivity callingActivity = (DishActivity) getActivity();

                Extra checkedItem = getItems().get(item);
                callingActivity.onSelectExtra(checkedItem);
                dialog.dismiss();
            }
        })
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                //.setSingleChoiceItems(getItems(), 0, null)
                        // Set the action buttons
//                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int id) {
//                        // User clicked OK, so save the mSelectedItems results somewhere
//                        // or return them to the component that opened the dialog
//                        //  String value = editQuantity.getText().toString();
//                        //  Log.d("Quantity: ", value);
//                        DishActivity callingActivity = (DishActivity) getActivity();
//                        ListView lw = ((AlertDialog) dialog).getListView();
//                        Extra checkedItem = (Extra) lw.getAdapter().getItem(lw.getCheckedItemPosition());
//                        callingActivity.onSelectExtra(checkedItem);
//                        //System.out.println(">>>>>>");
//                        //System.out.println( ((AlertDialog)dialog).getListView().getCheckedItemPosition());
//
//                        dialog.dismiss();
//                        System.out.println("Onclick");
//                    }
//                })
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


    public ArrayList<Extra> getItems() {
        return items;
    }

    public void setItems(ArrayList<Extra> items) {
        this.items = items;
    }
}