package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class VelocityAdding extends EditTextActivity {

    @Override
    public void doYourStuff() {
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        clickButtonWhenFilledEditText(editText2);

        resultDeclaration.setVisibility(View.GONE);

        spinnerSetup();
    }

    public void onClickButton(View view) {

        try {
            Double input1 = Double.parseDouble(editText1.getText().toString());
            Double input2 = Double.parseDouble(editText2.getText().toString());

            Units.Velocity velocity1 = Units.Velocity.values()[spinner1.getSelectedItemPosition()];
            Units.Velocity velocity2 = Units.Velocity.values()[spinner2.getSelectedItemPosition()];
            Units.Velocity resultVelocity = Units.Velocity.values()[resultSpinner.getSelectedItemPosition()];

            Double num1 = velocity1.convertTo(Units.Velocity.MPS, input1);
            Double num2 = velocity2.convertTo(Units.Velocity.MPS, input2);

            final Double C = Units.Velocity.C;

            if (num1 > C || num2 > C) {
                CustomDialog.tooFast(getFragmentManager());
                return;
            }

            final Double result = (num1 + num2) / (1 + (num1 * num2) / (C * C));
            final Double output = Units.Velocity.MPS.convertTo(resultVelocity, result);

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setText(Units.format(output));
                    fadeIn(resultTextView, null);
                }
            });

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void spinnerSetup() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickCalculate(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(itemSelectedListener);
        spinner2.setOnItemSelectedListener(itemSelectedListener);
        resultSpinner.setOnItemSelectedListener(itemSelectedListener);

        spinner1.setSelection(0);
        spinner2.setSelection(0);
        resultSpinner.setSelection(0);

    }

}
