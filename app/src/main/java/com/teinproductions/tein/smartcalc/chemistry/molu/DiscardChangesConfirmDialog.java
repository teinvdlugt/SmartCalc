package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.teinproductions.tein.smartcalc.R;


public class DiscardChangesConfirmDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage(R.string.discard_changes)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveOrDiscard.saveOrDiscard(true);
                    }
                })
                .setNegativeButton(R.string.discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveOrDiscard.saveOrDiscard(false);
                    }
                })
                .setNeutralButton(android.R.string.cancel, null);
        return builder.create();
    }

    private SaveOrDiscard saveOrDiscard;

    public interface SaveOrDiscard {
        public void saveOrDiscard(boolean save);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            saveOrDiscard = (SaveOrDiscard) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement DiscardChangesConfirmDialog.SaveOrDiscard");
        }
    }
}
