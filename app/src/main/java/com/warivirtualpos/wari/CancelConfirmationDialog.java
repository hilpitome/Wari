package com.warivirtualpos.wari;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


/**
 * Created by hilary on 12/14/17.
 */

public class CancelConfirmationDialog extends DialogFragment {

    public static CancelConfirmationDialog newInstance() {
        CancelConfirmationDialog frag = new CancelConfirmationDialog();
        return frag;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setMessage("Discard changes?")
                .setPositiveButton(R.string.alert_dialog_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }
                )
                .setNegativeButton(R.string.alert_dialog_cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                              // do nothing but dismiss the dialogue
                            }
                        }
                )
                .create();
    }


}
