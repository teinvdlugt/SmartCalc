package com.teinproductions.tein.smartcalc.computerscience.numeralsystem;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.teinproductions.tein.smartcalc.IOHandler;
import com.teinproductions.tein.smartcalc.R;

public class NumeralSystemEditDialog extends DialogFragment {

    public static final int NEW_SYSTEM = -1;

    private static final String SYSTEMS = "SYSTEMS";
    private static final String INDEX = "INDEX";

    private NumeralSystem[] systems;
    private Integer index;

    /* I don't want the dialog to warn the user more than once
     * when he clicks on an edit text while editing an non-editable
     * NumeralSystem.
     */
    private boolean alreadyWarned = false;

    private EditText nameET, charactersET;
    private CheckBox visibleCheck;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.systems = (NumeralSystem[]) getArguments().getSerializable(SYSTEMS);
        this.index = getArguments().getInt(INDEX);

        if (index == NEW_SYSTEM) {
            expandSystems();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder
                .setView(getContentView())
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NumeralSystemEditDialog.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(R.string.save, null); // OnClickListener is handled in onStart()
        if (systems[index].isEditable()) {
            builder.setNeutralButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onClickDelete();
                    listener.reload();
                }
            });
        } else {
            blockEdits();
        }

        return builder.create();
    }

    private View getContentView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_numeral_system_edit, null);

        nameET = (EditText) layout.findViewById(R.id.name_edit_text);
        charactersET = (EditText) layout.findViewById(R.id.characters);
        visibleCheck = (CheckBox) layout.findViewById(R.id.visible_checkBox);

        String name;
        try {
            name = systems[index].getName(getActivity()) == null ? "" : systems[index].getName(getActivity());
        } catch (Resources.NotFoundException e) {
            name = "";
        }

        nameET.setText(name);
        charactersET.setText(String.valueOf(systems[index].getChars()));
        visibleCheck.setChecked(systems[index].isVisible());

        setTextWatcher();

        nameET.setSelection(nameET.length());

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog dialog = (AlertDialog) getDialog();
        if (dialog != null) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (applyChanges()) {
                        IOHandler.save(getActivity(), systems);
                        listener.reload();
                        dismiss();
                    }
                }
            });
        }
    }

    private void setTextWatcher() {
        charactersET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (charactersET.length() <= 1) return;
                if (NumeralSystem.isValidCharArray(charactersET.getText().toString().toCharArray()))
                    return;
                removeLastCharacter(charactersET);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void blockEdits() {
        nameET.setFocusable(false);
        charactersET.setFocusable(false);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alreadyWarned) {
                    Toast.makeText(getActivity(), "You can't edit this numeral system", Toast.LENGTH_SHORT).show();
                    alreadyWarned = true;
                }
            }
        };
        nameET.setOnClickListener(listener);
        charactersET.setOnClickListener(listener);
    }

    private boolean applyChanges() {
        final String name = nameET.getText().toString();
        final String charStr = charactersET.getText().toString();
        final boolean visible = visibleCheck.isChecked();

        if (name.length() < 1 || charStr.length() < 2 || !NumeralSystem.isValidCharArray(charStr.toCharArray())) {
            Toast.makeText(getActivity(), "Please enter a valid name and characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        systems[index].setName(name);
        systems[index].setChars(charStr.toCharArray());
        systems[index].setVisible(visible);
        return true;
    }

    private void onClickDelete() {
        if (!systems[index].isEditable()) {
            // This should not happen since the
            // delete button shouldn't be visible
            return;
        }

        // Delete the system from the array
        NumeralSystem[] systems1 = new NumeralSystem[systems.length - 1];
        int passed = 0; // 0 if false, 1 if true
        for (int i = 0; i < systems1.length; i++) {
            if (i == index) {
                passed = 1;
            } else {
                systems1[i - passed] = systems[i];
            }
        }
        systems = systems1;

        IOHandler.save(getActivity(), systems);
    }

    private void expandSystems() {
        NumeralSystem[] expanded = new NumeralSystem[systems.length + 1];
        System.arraycopy(systems, 0, expanded, 0, systems.length);
        // This is a new system so it is editable and visible
        expanded[systems.length] = new NumeralSystem(null, new char[]{}, true, true);

        index = systems.length;
        systems = expanded;
    }

    public static NumeralSystemEditDialog newInstance(NumeralSystem[] systems, int index) {
        NumeralSystemEditDialog fragment = new NumeralSystemEditDialog();

        Bundle args = new Bundle();
        args.putSerializable(SYSTEMS, systems);
        args.putInt(INDEX, index);
        fragment.setArguments(args);

        return fragment;
    }

    public static void show(FragmentManager fragManager, NumeralSystem[] systems, int index) {
        NumeralSystemEditDialog.newInstance(systems, index).show(fragManager, "NumeralSystemEditDialog");
    }


    public interface OnClickListener {
        public void reload();
    }

    private OnClickListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    "must implement NumeralSystemEditDialog.OnClickListener");
        }
    }


    public static void removeLastCharacter(EditText e) {
        String text = e.getText().toString();
        char[] chars = text.toCharArray();

        if (text.length() == 0) return;
        if (text.length() == 1) e.setText("");

        char[] buffer = new char[text.length() - 1];
        System.arraycopy(chars, 0, buffer, 0, buffer.length);

        e.setText(String.valueOf(buffer));
        e.setSelection(buffer.length);
    }
}
