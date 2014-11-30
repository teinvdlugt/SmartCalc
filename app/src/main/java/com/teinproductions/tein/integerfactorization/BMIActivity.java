package com.teinproductions.tein.integerfactorization;

import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;

public class BMIActivity extends EditTextActivity {


    @Override
    protected void doYourStuff() {
        editText1.setHint(getString(R.string.enter_your_weight));
        editText2.setHint(getString(R.string.enter_your_length));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        clickButtonWhenFilledEditText(editText2);

        setAdapters();
        spinner1.setSelection(2);
        spinner2.setSelection(2);

        resultDeclaration.setText(getString(R.string.your_bmi_is));
        resultSpinner.setVisibility(View.GONE);

        saveResultTextViewText = true;

    }

    @Override
    protected void onClickButton(View view) {
        try {
            Double weightNumber = Double.parseDouble(editText1.getText().toString());
            Double lengthNumber = Double.parseDouble(editText2.getText().toString());

            Units.Mass massUnit = Units.Mass.values()[spinner1.getSelectedItemPosition()];
            Units.Length lengthUnit = Units.Length.values()[spinner1.getSelectedItemPosition()];

            Double weight = massUnit.convertTo(Units.Mass.KILOGRAMS, weightNumber);
            Double length = lengthUnit.convertTo(Units.Length.METER, lengthNumber);

            Double BMI = weight / length / length;

            resultTextView.setText(Units.format(BMI));
            
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getFragmentManager());
        }
    }

    private void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Units.Mass.getAbbreviations(BMIActivity.this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(BMIActivity.this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);
    }

}
