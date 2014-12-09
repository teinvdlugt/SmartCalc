package com.teinproductions.tein.integerfactorization;


import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class ConvertActivity extends ActionBarActivity {

    protected Spinner spinner1, spinner2, spinner1b, spinner2b;
    protected EditText editText1, editText2;
    protected Button button;

    protected int selected1, selected2, selected1b, selected2b;
    protected Double result;
    protected Double input1, input2;

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
        setOnClickListeners();

        TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickConvert(view);
                    return true;
                }
                return false;
            }
        };

        editText1.setOnEditorActionListener(editorActionListener);
        editText2.setOnEditorActionListener(editorActionListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
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

    protected void setOnClickListeners() {
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickConvert(spinner1);
                // Just a random spinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setOnItemSelectedListener(listener);
        try {
            spinner1b.setOnItemSelectedListener(listener);
        } catch (NullPointerException ignored) {
        }

        listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickConvert(editText2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner2.setOnItemSelectedListener(listener);
        try {
            spinner2b.setOnItemSelectedListener(listener);
        } catch (NullPointerException ignored) {
        }
    }

    public void onClickConvert(View view) {
        selected1 = spinner1.getSelectedItemPosition();
        selected2 = spinner2.getSelectedItemPosition();
        if (spinner1b != null && spinner2b != null) {
            selected1b = spinner1b.getSelectedItemPosition();
            selected2b = spinner2b.getSelectedItemPosition();
        }

        if (view == editText1) {
            try {
                input1 = Double.parseDouble(editText1.getText().toString());
                input2 = null;
                editText2.setText(convert().toString());
                input1 = null;
            } catch (NumberFormatException e) {
                editText2.requestFocus();
                return;
            }
        } else if (view == editText2) {
            try {
                input2 = Double.parseDouble(editText2.getText().toString());
                input1 = null;
                editText1.setText(convert().toString());
                input2 = null;
            } catch (NumberFormatException e) {
                editText1.requestFocus();
                return;
            }
        } else if (view == button || view == spinner1) {
            if (getCurrentFocus() == editText1) {
                if (isValidDecimalNumberInput(editText1.getText().toString())) {
                    onClickConvert(editText1);
                } else if (isValidDecimalNumberInput(editText2.getText().toString())) {
                    onClickConvert(editText2);
                } else if (view == button) {
                    CustomDialog.invalidNumber(getFragmentManager());
                }
            } else if (getCurrentFocus() == editText2) {
                if (isValidDecimalNumberInput(editText2.getText().toString())) {
                    onClickConvert(editText2);
                } else if (isValidDecimalNumberInput(editText1.getText().toString())) {
                    onClickConvert(editText1);
                } else if (view == button) {
                    CustomDialog.invalidNumber(getFragmentManager());
                }
            } else {
                editText1.setText("");
                editText2.setText("");
            }
        }
    }

    public static boolean isValidDecimalNumberInput(String input) {
        try {
            Double someDouble = Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected abstract Double convert();

}
