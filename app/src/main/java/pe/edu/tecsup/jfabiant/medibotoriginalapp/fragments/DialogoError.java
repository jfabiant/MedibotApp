package pe.edu.tecsup.jfabiant.medibotoriginalapp.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import pe.edu.tecsup.jfabiant.medibotoriginalapp.R;

@SuppressLint("ValidFragment")
public class DialogoError extends DialogFragment {

    private String dialogTitle, dialogMessage;

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    public String getDialogMessage() {
        return dialogMessage;
    }

    public void setDialogMessage(String dialogMessage) {
        this.dialogMessage = dialogMessage;
    }

    public DialogoError (String dialogTitle, String dialogMessage) {
        this.dialogTitle = dialogTitle;
        this.dialogMessage = dialogMessage;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle(dialogTitle)
                .setMessage(dialogMessage)
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}