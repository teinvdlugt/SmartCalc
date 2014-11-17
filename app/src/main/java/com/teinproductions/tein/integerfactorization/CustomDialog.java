package com.teinproductions.tein.integerfactorization;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class CustomDialog extends DialogFragment {

    private int title, message;
    public static String TITLE = "TITLE", MESSAGE = "MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getArguments().getInt(TITLE);
        message = getArguments().getInt(MESSAGE);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());

        theDialog.setTitle(title);
        theDialog.setMessage(message);

        theDialog.setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return theDialog.create();

    }

    public static CustomDialog newInstance(int title, int message){
        CustomDialog theDialog = new CustomDialog();
        Bundle args = new Bundle();
        args.putInt(MESSAGE, message);
        args.putInt(TITLE, title);
        theDialog.setArguments(args);

        return theDialog;
    }

    public static CustomDialog invalidNumber(){
        return CustomDialog.newInstance(R.string.invalid_number_title, R.string.invalid_number_message);
    }


}
