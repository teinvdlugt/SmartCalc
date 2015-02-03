package com.teinproductions.tein.integerfactorization.physics;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.teinproductions.tein.integerfactorization.EditTextActivity;
import com.teinproductions.tein.integerfactorization.IOHandler;
import com.teinproductions.tein.integerfactorization.R;
import com.teinproductions.tein.integerfactorization.Units;

public class SchwarzschildRadiusActivity extends ActionBarActivity {

    private EditText massET, radiusET;
    private Spinner massSpinner, radiusSpinner;

    private boolean indirectTextChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schwarzchild_radius);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();

        setTextWatchers();
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
                        calculate(e);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

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
                    calculate(view);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.info_icon:
                IOHandler.openWebPage(this, "http://en.wikipedia.org/wiki/Schwarzschild_radius");
                return true;
        }
        return false;
    }

    public void onClickCalculate(View view) {
        calculate(view);
    }

    public void calculate(View view) {
        if (view == massET) {
            calculateWithMass(massET);
        } else if (view == radiusET) {
            calculateWithRadius(radiusET);
        } else if (massET.hasFocus() && EditTextActivity.hasValidDecimalInput(massET)) {
            calculateWithMass(null);
        } else if (radiusET.hasFocus() && EditTextActivity.hasValidDecimalInput(radiusET)) {
            calculateWithRadius(null);
        } else if (EditTextActivity.hasValidDecimalInput(massET)) {
            calculateWithMass(null);
        } else if (EditTextActivity.hasValidDecimalInput(radiusET)) {
            calculateWithRadius(null);
        }
    }

    private void calculateWithMass(View view) {
        final Double mass;
        try {
            mass = getMassKG();
        } catch (NumberFormatException e) {
            if (view == massET) {
                indirectTextChange = true;
                radiusET.setText("");
                indirectTextChange = false;
            } else if (view == null) {
                indirectTextChange = true;
                radiusET.setText("");
                massET.setText("");
                indirectTextChange = false;
            }
            return;
        }

        final Units.Length lengthUnit = Units.Length.values()[radiusSpinner.getSelectedItemPosition()];
        final Double result = (2 * Units.G * mass) / (Units.Velocity.C * Units.Velocity.C);

        indirectTextChange = true;
        radiusET.setText(Units.format(Units.Length.METER.convertTo(lengthUnit, result)));
        indirectTextChange = false;
    }

    private void calculateWithRadius(View view) {
        final Double radius;
        try {
            radius = getRadiusM();
        } catch (NumberFormatException e) {
            if (view == radiusET) {
                indirectTextChange = true;
                massET.setText("");
                indirectTextChange = false;
            } else if (view == null) {
                indirectTextChange = true;
                radiusET.setText("");
                massET.setText("");
                indirectTextChange = false;
            }
            return;
        }

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
}