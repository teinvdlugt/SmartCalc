package com.teinproductions.tein.integerfactorization;

import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;

public class TimeDilationActivity extends EditTextActivity {

    @Override
    protected void doYourStuff() {
        editText1.setHint(getString(R.string.velocity));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setHint(getString(R.string.proper_time));
        resultDeclaration.setText(getString(R.string.dilated_time));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        resultSpinner.setAdapter(adapter);

        spinner1.setSelection(0);
        spinner2.setSelection(3);
        resultSpinner.setSelection(3);

        clickButtonWhenFilledEditText(editText2);
    }

    @Override
    protected void onClickButton(View view) {
        try {
            Double input1 = Double.parseDouble(editText1.getText().toString());
            Double input2 = Double.parseDouble(editText2.getText().toString());

            Units.Velocity velocity1 = Units.Velocity.values()[(spinner1.getSelectedItemPosition())];
            Units.Time time2 = Units.Time.values()[(spinner2.getSelectedItemPosition())];
            Units.Time resultTime = Units.Time.values()[(resultSpinner.getSelectedItemPosition())];

            Double num1 = Units.Velocity.convert(velocity1, Units.Velocity.MPS, input1);
            Double num2 = Units.Time.convert(time2, Units.Time.SEC, input2);

            if (num1 > Units.Velocity.C) {
                CustomDialog.tooFast().show(getFragmentManager(), "theDialog");
                editText1.setText("");
            }

            Double dilated = num2 / (Math.sqrt(1 - ((num1 * num1) / (Units.Velocity.C * Units.Velocity.C))));

            Double result = Units.Time.convert(Units.Time.SEC, resultTime, dilated);

            if (result == 0) {
                resultTextView.setText("0");
            } else if (Math.abs(result) > 0 && Math.abs(result) <= 0.0001) {
                resultTextView.setText(new DecimalFormat("0.##########E0").format(result));
            } else if (Math.abs(result) < 1000000) {
                resultTextView.setText(new DecimalFormat("0.##########").format(result));
            } else if (Math.abs(result) >= 1000000) {
                resultTextView.setText(new DecimalFormat("0.##########E0").format(result));
            }
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber().show(getFragmentManager(), "theDialog");
        }
    }
}
