package com.teinproductions.tein.integerfactorization;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SchwarzschildRadiusActivity extends ActionBarActivity {

    private EditText massET, radiusET;
    private Spinner massSpinner, radiusSpinner;

    private boolean indirectTextChange = false;

    // This enum specifies what should happen when an EditText was filled incorrectly
    private enum InvalidDoubleType {
        TO_OTHER_ET, SHOW_DIALOG, CLEAR_OTHER_ET, DO_NOTHING
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schwarzchild_radius);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();

        setTextWatchers();
        setEditorActionListeners();

        setAdapters();
        setOnItemSelectedListeners();
        setSpinnerSelections();
    }

    private void initializeViews() {
        massET = (EditText) findViewById(R.id.mass_edit_text);
        radiusET = (EditText) findViewById(R.id.schwarzchildRadius_editText);
        massSpinner = (Spinner) findViewById(R.id.mass_spinner);
        radiusSpinner = (Spinner) findViewById(R.id.schwarzchildRadius_spinner);
    }

    private void setTextWatchers() {
        for (final EditText e : new EditText[]{massET, radiusET}) {
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!indirectTextChange) {
                        calculate(e, InvalidDoubleType.CLEAR_OTHER_ET);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void setEditorActionListeners() {
        for (final EditText e : new EditText[]{massET, radiusET}) {
            e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    calculate(e, InvalidDoubleType.TO_OTHER_ET);
                    return true;
                }
            });
        }
    }

    private void setAdapters() {
        final ArrayAdapter<String> massAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Mass.getAbbreviations(SchwarzschildRadiusActivity.this));
        massAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        massSpinner.setAdapter(massAdapter);

        final ArrayAdapter<String> lengthAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(SchwarzschildRadiusActivity.this));
        lengthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        radiusSpinner.setAdapter(lengthAdapter);

    }

    private void setOnItemSelectedListeners() {
        for (Spinner spinner : new Spinner[]{massSpinner, radiusSpinner}) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    calculate(view, InvalidDoubleType.DO_NOTHING);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void setSpinnerSelections() {
        final int kg = Units.Mass.KILOGRAMS.ordinal();
        final int m = Units.Length.METER.ordinal();
        massSpinner.setSelection(kg, false);
        radiusSpinner.setSelection(m, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    public void onClickCalculate(View view) {
        calculate(view, InvalidDoubleType.SHOW_DIALOG);
    }

    public void calculate(View view, InvalidDoubleType invalidDoubleType) {
        try {
            if (view == massET) {
                calculateWithMass();
            } else if (view == radiusET) {
                calculateWithRadius();
            } else if (massET.hasFocus() && CalculateFragment.hasValidDecimalInput(massET)) {
                calculateWithMass();
            } else if (radiusET.hasFocus() && CalculateFragment.hasValidDecimalInput(radiusET)) {
                calculateWithRadius();
            } else if (CalculateFragment.hasValidDecimalInput(massET)) {
                calculateWithMass();
            } else if (CalculateFragment.hasValidDecimalInput(radiusET)) {
                calculateWithRadius();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            switch (invalidDoubleType) {
                case TO_OTHER_ET:
                    focusOnOtherET(view);
                    break;
                case SHOW_DIALOG:
                    CustomDialog.invalidNumber(getFragmentManager());
                    break;
                case CLEAR_OTHER_ET:
                    clearOtherET(view);
                    break;
            }
        }
    }

    private void calculateWithMass() {
        final Double mass = getMassKG();
        final Double result = (2 * Units.G * mass) / (Units.Velocity.C * Units.Velocity.C);
        final Units.Length lengthUnit = Units.Length.values()[radiusSpinner.getSelectedItemPosition()];

        indirectTextChange = true;
        radiusET.setText(Units.format(Units.Length.METER.convertTo(lengthUnit, result)));
        indirectTextChange = false;
    }

    private void calculateWithRadius() {
        final Double radius = getRadiusM();
        final Double result = radius * (Units.Velocity.C * Units.Velocity.C) / (2 * Units.G);
        final Units.Mass massUnit = Units.Mass.values()[massSpinner.getSelectedItemPosition()];

        indirectTextChange = true;
        massET.setText(Units.format(Units.Mass.KILOGRAMS.convertTo(massUnit, result)));
        indirectTextChange = false;
    }

    private Double getMassKG() {
        Double value = Double.parseDouble(massET.getText().toString());
        Units.Mass unit = Units.Mass.values()[massSpinner.getSelectedItemPosition()];
        return unit.convertTo(Units.Mass.KILOGRAMS, value);
    }

    private Double getRadiusM() {
        Double value = Double.parseDouble(radiusET.getText().toString());
        Units.Length unit = Units.Length.values()[radiusSpinner.getSelectedItemPosition()];
        return unit.convertTo(Units.Length.METER, value);
    }

    private void focusOnOtherET(View view) {
        if (view.equals(massET)) {
            radiusET.requestFocus();
        } else if (view.equals(radiusET)) {
            massET.hasFocus();
        }
    }

    private void clearOtherET(View view) {
        if (view.equals(massET)) {
            radiusET.setText("");
        } else if (view.equals(radiusET)) {
            massET.setText("");
        }
    }
}