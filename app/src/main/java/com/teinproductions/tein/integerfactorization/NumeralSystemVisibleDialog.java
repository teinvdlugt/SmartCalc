package com.teinproductions.tein.integerfactorization;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.ListView;

public class NumeralSystemVisibleDialog extends DialogFragment {

    private NumeralSystem[] systems;
    private static final String SYSTEMS = "SYSTEMS";

    private boolean[] initialCheckedBoxes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.systems = (NumeralSystem[]) getArguments().getSerializable(SYSTEMS);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String[] names = getNames();
        initialCheckedBoxes = getCheckedItems();

        builder
                .setMultiChoiceItems(names, initialCheckedBoxes, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        systems[which].setVisible(isChecked);
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NumeralSystemEditDialog.save(getActivity(), systems);
                        listener.reload();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onClickCancel(dialog);
                        /* This doesn't yet work; when you select/deselect an item and
                         * click cancel and reopen the dialog, the selected/deselected item will
                         * still be selected/deselected TODO
                         */
                    }
                });

        return builder.create();
    }

    private String[] getNames() {
        String[] names = new String[systems.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = systems[i].getName(getActivity());
        }
        return names;
    }

    private boolean[] getCheckedItems() {
        boolean[] checked = new boolean[systems.length];
        for (int i = 0; i < checked.length; i++) {
            checked[i] = systems[i].isVisible();
        }
        return checked;
    }

    public static NumeralSystemVisibleDialog newInstance(NumeralSystem[] systems) {
        NumeralSystemVisibleDialog fragment = new NumeralSystemVisibleDialog();

        Bundle args = new Bundle();
        args.putSerializable(SYSTEMS, systems);
        fragment.setArguments(args);

        return fragment;
    }

    private void onClickCancel(DialogInterface dialog) {
        ListView list = ((AlertDialog) dialog).getListView();
        for (int i = 0; i < systems.length; i++) {
            list.setItemChecked(i, initialCheckedBoxes[i]);
        }

        super.onCancel(dialog);
    }

    NumeralSystemEditDialog.OnClickListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (NumeralSystemEditDialog.OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement NumeralSystemEditDialog.OnClickListener");
        }
    }
}
