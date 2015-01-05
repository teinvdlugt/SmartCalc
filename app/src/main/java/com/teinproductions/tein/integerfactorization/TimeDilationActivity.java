package com.teinproductions.tein.integerfactorization;

import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class TimeDilationActivity extends EditTextActivity {

    @Override
    protected void doYourStuff() {
        editText1.setHint(getString(R.string.velocity));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setHint(getString(R.string.proper_time));
        resultDeclaration.setText(getString(R.string.dilated_time));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        resultSpinner.setAdapter(adapter);

        spinner1.setSelection(0);
        spinner2.setSelection(3);
        resultSpinner.setSelection(3);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recalculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setOnItemSelectedListener(itemSelectedListener);
        spinner2.setOnItemSelectedListener(itemSelectedListener);
        resultSpinner.setOnItemSelectedListener(itemSelectedListener);

        clickButtonWhenFilledEditText(editText2);
        saveResultTextViewText = true;
    }

    @Override
    protected void onClickButton(View view) {
        try {
            Double input1 = Double.parseDouble(editText1.getText().toString());
            Double input2 = Double.parseDouble(editText2.getText().toString());

            Units.Velocity velocity1 = Units.Velocity.values()[(spinner1.getSelectedItemPosition())];
            Units.Time time2 = Units.Time.values()[(spinner2.getSelectedItemPosition())];
            Units.Time resultTime = Units.Time.values()[(resultSpinner.getSelectedItemPosition())];

            Double num1 = velocity1.convertTo(Units.Velocity.MPS, input1);
            Double num2 = time2.convertTo(Units.Time.SEC, input2);

            if (num1 > Units.Velocity.C) {
                CustomDialog.tooFast(getFragmentManager());
                editText1.setText("");
            }

            Double dilated = num2 / (Math.sqrt(1 - ((num1 * num1) / (Units.Velocity.C * Units.Velocity.C))));

            Double result = Units.Time.SEC.convertTo(resultTime, dilated);

            resultTextView.setText(Units.format(result));
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getFragmentManager());
        }
    }

    protected void recalculate() {
        try {
            Double input1 = Double.parseDouble(editText1.getText().toString());
            Double input2 = Double.parseDouble(editText2.getText().toString());

            Units.Velocity velocity1 = Units.Velocity.values()[(spinner1.getSelectedItemPosition())];
            Units.Time time2 = Units.Time.values()[(spinner2.getSelectedItemPosition())];
            Units.Time resultTime = Units.Time.values()[(resultSpinner.getSelectedItemPosition())];

            Double num1 = velocity1.convertTo(Units.Velocity.MPS, input1);
            Double num2 = time2.convertTo(Units.Time.SEC, input2);

            if (num1 > Units.Velocity.C) {
                throw new NumberFormatException(getString(R.string.faster_than_light));
            }

            Double dilated = num2 / (Math.sqrt(1 - ((num1 * num1) / (Units.Velocity.C * Units.Velocity.C))));

            Double result = Units.Time.SEC.convertTo(resultTime, dilated);

            resultTextView.setText(Units.format(result));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
