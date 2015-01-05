package com.teinproductions.tein.integerfactorization;

import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class SchwarzschildRadiusActivity extends EditTextActivity {

    @Override
    protected void doYourStuff() {
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText1.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText1.setHint(R.string.mass);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Mass.getAbbreviations(SchwarzschildRadiusActivity.this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setSelection(2, false);
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

        editText2.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);

        resultDeclaration.setText(getString(R.string.schwarzchild_radius) + ":");
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(adapter);
        resultSpinner.setSelection(1, false);
        resultSpinner.setOnItemSelectedListener(itemSelectedListener);

        saveResultTextViewText = true;
        clickButtonWhenFilledEditText(editText1);
    }

    @Override
    protected void onClickButton(View view) {
        Double mass;
        try {
            mass = Double.parseDouble(editText1.getText().toString());
        } catch (NumberFormatException e) {
            if (view == editText1 || view == button) {
                CustomDialog.invalidNumber(getFragmentManager());
            }
            editText1.setText("");
            resultTextView.setText("");
            return;
        }

        Units.Mass inputUnit = Units.Mass.values()[spinner1.getSelectedItemPosition()];
        Units.Length resultUnit = Units.Length.values()[resultSpinner.getSelectedItemPosition()];
        mass = inputUnit.convertTo(Units.Mass.KILOGRAMS, mass);

        Double result = (2 * Units.G * mass) / (Units.Velocity.C * Units.Velocity.C);
        result = Units.Length.METER.convertTo(resultUnit, result);

        resultTextView.setText(Units.format(result));
    }
}
