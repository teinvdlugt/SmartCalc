package com.teinproductions.tein.integerfactorization;

import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class LengthContractionActivity extends EditTextActivity {

    @Override
    protected void doYourStuff() {
        setInputTypesAndTexts();

        setAdapters();
        setItemSelectedListeners();

        final int mps = Units.Velocity.MPS.ordinal(), meters = Units.Length.METER.ordinal();

        spinner1.setSelection(mps, false);
        spinner2.setSelection(meters, false);
        resultSpinner.setSelection(meters, false);

        clickButtonWhenFilledEditText(editText2);
        setTextWatcher(editText1);
        setTextWatcher(editText2);
    }

    private void setInputTypesAndTexts() {
        editText1.setHint(getString(R.string.velocity));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setHint(getString(R.string.proper_length));
        resultDeclaration.setText(getString(R.string.contracted_length));
    }

    private void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        resultSpinner.setAdapter(adapter);
    }

    private void setItemSelectedListeners() {
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickButton(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setOnItemSelectedListener(itemSelectedListener);
        spinner2.setOnItemSelectedListener(itemSelectedListener);
        resultSpinner.setOnItemSelectedListener(itemSelectedListener);
    }

    @Override
    protected void onClickButton(View view) {
        try {
            final Double velocity = getConvertedVelocity();
            final Double properLength = getConvertedProperLength();

            if (velocity > Units.Velocity.C) {
                if (view == button) {
                    CustomDialog.tooFast(getFragmentManager());
                    editText1.setText("");
                    resultTextView.setText("");
                } else if (view instanceof EditText) {
                    resultTextView.setText("");
                }
                return;
            }


            final Double velocitySquared = velocity * velocity;
            final Double lightSpeedSquared = Units.Velocity.C * Units.Velocity.C;

            Double contracted = properLength * Math.sqrt(1 - (velocitySquared / (lightSpeedSquared)));

            Units.Length resultLength = Units.Length.values()[(resultSpinner.getSelectedItemPosition())];
            Double result = Units.Length.METER.convertTo(resultLength, contracted);

            resultTextView.setText(Units.format(result));
        } catch (NumberFormatException e) {
            if (view == button) {
                CustomDialog.invalidNumber(getFragmentManager());
            }
        }
    }

    private Double getConvertedVelocity() {
        Double value = Double.parseDouble(editText1.getText().toString());
        Units.Velocity unit = Units.Velocity.values()[(spinner1.getSelectedItemPosition())];
        return unit.convertTo(Units.Velocity.MPS, value);
    }

    private Double getConvertedProperLength() {
        Double value = Double.parseDouble(editText2.getText().toString());
        Units.Length unit = Units.Length.values()[(spinner2.getSelectedItemPosition())];
        return unit.convertTo(Units.Length.METER, value);
    }
}
