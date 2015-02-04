package com.teinproductions.tein.smartcalc.conversion;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.R;

public abstract class ConvertActivity extends ActionBarActivity {

    protected Spinner spinner1, spinner2, spinner1b, spinner2b;
    protected EditText editText1, editText2;
    protected Button button;

    protected Double result;
    protected Double input1, input2;

    private boolean indirectTextChange = false;

    private enum InvalidDoubleActionType {CLEAR_OTHER_ET, SHOW_MESSAGE, TO_NEXT_ET}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner1b = (Spinner) findViewById(R.id.spinner1b);
        spinner2b = (Spinner) findViewById(R.id.spinner2b);
        editText1 = (EditText) findViewById(R.id.edit_text1);
        editText2 = (EditText) findViewById(R.id.edit_text2);
        button = (Button) findViewById(R.id.calculate_button);

        setAdapters();
        setOnItemSelectedListeners();

        setEditorActionListeners();
        setTextWatchers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected abstract
    @LayoutRes
    int contentView();

    protected abstract void setAdapters();

    protected void setOnItemSelectedListeners() {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                convert(view, InvalidDoubleActionType.CLEAR_OTHER_ET);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setOnItemSelectedListener(listener);
        spinner2.setOnItemSelectedListener(listener);
        try {
            spinner1b.setOnItemSelectedListener(listener);
            spinner2b.setOnItemSelectedListener(listener);
        } catch (NullPointerException ignored) {
        }
    }

    private void setEditorActionListeners() {
        TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                convert(view, InvalidDoubleActionType.TO_NEXT_ET);
                return true;
            }
        };

        editText1.setOnEditorActionListener(editorActionListener);
        editText2.setOnEditorActionListener(editorActionListener);
    }

    private void setTextWatchers() {
        for (final EditText e : new EditText[]{editText1, editText2}) {
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!indirectTextChange) {
                        convert(e, InvalidDoubleActionType.CLEAR_OTHER_ET);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void onClickConvert(View view) {
        if (view == button) {
            convert(view, InvalidDoubleActionType.SHOW_MESSAGE);
        }
    }

    private void convert(View view, InvalidDoubleActionType invalidDoubleActionType) {
        indirectTextChange = true;

        try {
            if (view == editText1) {
                input1 = Double.parseDouble(editText1.getText().toString());
                input2 = null;
                editText2.setText(convert().toString());
                input1 = null;
            } else if (view == editText2) {
                input2 = Double.parseDouble(editText2.getText().toString());
                input1 = null;
                editText1.setText(convert().toString());
                input2 = null;
            } else {
                if (editText1.hasFocus() && EditTextActivity.hasValidDecimalInput(editText1)) {
                    convert(editText1, InvalidDoubleActionType.SHOW_MESSAGE);
                } else if (editText2.hasFocus() && EditTextActivity.hasValidDecimalInput(editText2)) {
                    convert(editText2, InvalidDoubleActionType.SHOW_MESSAGE);
                } else if (EditTextActivity.hasValidDecimalInput(editText1)) {
                    convert(editText1, InvalidDoubleActionType.SHOW_MESSAGE);
                } else if (EditTextActivity.hasValidDecimalInput(editText2)) {
                    convert(editText2, InvalidDoubleActionType.SHOW_MESSAGE);
                } else {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException e) {
            switch(invalidDoubleActionType) {
                case CLEAR_OTHER_ET:
                    clearOtherET();
                    break;
                case TO_NEXT_ET:
                    editText2.requestFocus();
                    break;
                case SHOW_MESSAGE:
                    CustomDialog.invalidNumber(getSupportFragmentManager());
            }
        }

        indirectTextChange = false;
    }

    protected abstract Double convert();

    private void clearOtherET() {
        if (editText1.hasFocus()) {
            editText2.setText("");
        } else if (editText2.hasFocus()) {
            editText1.setText("");
        }
    }
}
