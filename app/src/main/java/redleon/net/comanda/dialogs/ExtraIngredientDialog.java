package redleon.net.comanda.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

import redleon.net.comanda.R;
import redleon.net.comanda.model.Extra;

/**
 * Created by leon on 26/05/15.
 */
public class ExtraIngredientDialog extends DialogFragment {

    private ArrayList<Extra> mSelectedItems;
    private CharSequence[] items;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setmSelectedItems(new ArrayList<Extra>());  // Where we track the selected items
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                // Set the dialog title
        builder.setTitle(R.string.dialog_add_ingredient)
                // Specify the list array, the items to be selected by default (null for none),
                // and the listener through which to receive callbacks when items are selected
                .setMultiChoiceItems(getItems(), null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                  //  mSelectedItems.add(which);
                                } else if (getmSelectedItems().contains(which)) {
                                    // Else, if the item is already in the array, remove it
                                    //mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }
                        })
                        // Set the action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

    public ArrayList<Extra> getmSelectedItems() {
        return mSelectedItems;
    }

    public void setmSelectedItems(ArrayList<Extra> mSelectedItems) {
        this.mSelectedItems = mSelectedItems;
    }

    public CharSequence[] getItems() {
        return items;
    }

    public void setItems(CharSequence[] items) {
        this.items = items;
    }
}