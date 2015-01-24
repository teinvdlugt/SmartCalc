package com.teinproductions.tein.integerfactorization;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class NumeralSystemConvertActivity extends ActionBarActivity {

    private EditText decET;
    private NumeralSystemEditText[] preloadedEditTexts;
    private NumeralSystemEditText[] editTexts;

    private LinearLayout ll;

    private boolean indirectTextChangeDecET = false;
    public static final String FILE_NAME = "saved_numeral_systems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeDecET();
        loadPreloadedEditTexts();
        loadEditTexts();

        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        final int topBottom = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        final int leftRight = getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin);
        ll.setPadding(leftRight, topBottom, leftRight, topBottom);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        loadScreenContent();
        setContentView(ll);
    }

    private void initializeDecET() {
        decET = new EditText(this);
        decET.setInputType(InputType.TYPE_CLASS_NUMBER);
        decET.setHint(getString(R.string.decimal));
        decET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!indirectTextChangeDecET) {
                    final String text = decET.getText().toString();
                    try {
                        Integer value = Integer.parseInt(text);
                        convert(value);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        clearAllEditTexts();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void loadPreloadedEditTexts() {
        NumeralSystem BIN = new NumeralSystem(getString(R.string.binary),
                new char[]{'0', '1'});
        NumeralSystem OCT = new NumeralSystem(getString(R.string.octal),
                new char[]{'0', '1', '2', '3', '4', '5', '6', '7'});
        NumeralSystem NOV = new NumeralSystem(getString(R.string.nonary),
                new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'});
        NumeralSystem DUODEC = new NumeralSystem(getString(R.string.duodecimal),
                new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B'});
        NumeralSystem HEX = new NumeralSystem(getString(R.string.hexadecimal),
                new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'});

        preloadedEditTexts = new NumeralSystemEditText[]{
                new NumeralSystemEditText(this, BIN),
                new NumeralSystemEditText(this, OCT),
                new NumeralSystemEditText(this, NOV),
                new NumeralSystemEditText(this, DUODEC),
                new NumeralSystemEditText(this, HEX)
        };
    }

    private void loadEditTexts() {
        String jsonString = getFile();
        if (jsonString == null) {
            // File didn't exist (yet)
            editTexts = new NumeralSystemEditText[0];
        } else {
            try {
                JSONObject jObject = new JSONObject(jsonString);
                JSONArray jArray = jObject.getJSONArray(NumeralSystem.CHAR_ARRAY);
                NumeralSystem[] systems = NumeralSystem.arrayFromJSON(jArray);
                editTexts = new NumeralSystemEditText[systems.length];

                for (int i = 0; i < systems.length; i++) {
                    editTexts[i] = new NumeralSystemEditText(this, systems[i]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                editTexts = new NumeralSystemEditText[0];
            }
        }
    }

    private void loadScreenContent() {
        decET.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll.addView(decET);
        decET.setVisibility(View.VISIBLE);

        for (NumeralSystemEditText e : preloadedEditTexts) {
            e.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(e);
        }

        for (NumeralSystemEditText e : editTexts) {
            e.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ll.addView(e);
        }
    }

    private String getFile() {
        StringBuilder sb;

        try {
            // Opens a stream so we can read from our local file
            FileInputStream fis = this.openFileInput(FILE_NAME);

            // Gets an input stream for reading data
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            // Used to read the data in small bytes to minimize system load
            BufferedReader bufferedReader = new BufferedReader(isr);

            // Read the data in bytes until nothing is left to read
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void clearAllEditTexts() {
        if (preloadedEditTexts != null) {
            for (NumeralSystemEditText e : preloadedEditTexts) {
                e.setSafeText("");
            }
        }

        if (editTexts != null) {
            for (NumeralSystemEditText e : editTexts) {
                e.setSafeText("");
            }
        }

        indirectTextChangeDecET = true;
        decET.setText("");
        indirectTextChangeDecET = false;
    }

    public void convert(int decimal) {
        indirectTextChangeDecET = true;
        decET.setText(Integer.toString(decimal));
        decET.setSelection(decET.length());
        indirectTextChangeDecET = false;


        for (NumeralSystemEditText e : preloadedEditTexts) {
            e.convertFromDec(decimal);
        }

        for (NumeralSystemEditText e : editTexts) {
            e.convertFromDec(decimal);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
