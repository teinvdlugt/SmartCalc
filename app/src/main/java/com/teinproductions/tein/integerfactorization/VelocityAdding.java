package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class VelocityAdding extends EditTextActivity {

    @Override
    public void doYourStuff() {
        resultDeclaration.setVisibility(View.GONE);

        setTextAndInputTypes();
        setAdapters();
        setOnItemSelectedListeners();
        setSelections();
        setTextWatchers();
    }

    private void setTextAndInputTypes() {
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText1.setHint(getString(R.string.velocity1));
        editText2.setHint(getString(R.string.velocity2));
        button.setText(getString(R.string.add_button_text));
    }

    private void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        ArrayAdapter resultAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(resultAdapter);
    }

    private void setOnItemSelectedListeners() {
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickCalculate(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setOnItemSelectedListener(itemSelectedListener);
        spinner2.setOnItemSelectedListener(itemSelectedListener);
        resultSpinner.setOnItemSelectedListener(itemSelectedListener);
    }

    private void setSelections() {
        final int mps = Units.Velocity.MPS.ordinal();

        spinner1.setSelection(mps, false);
        spinner2.setSelection(mps, false);
        resultSpinner.setSelection(mps, false);
    }

    private void setTextWatchers() {
        for (final EditText e : new EditText[]{editText1, editText2}) {
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (CalculateFragment.hasValidDecimalInput(editText1) &&
                            CalculateFragment.hasValidDecimalInput(editText2)) {
                        onClickButton(e);
                    } else {
                        resultTextView.setText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void onClickButton(View view) {
        try {
            final Double v1 = getMPS1();
            final Double v2 = getMPS2();

            final Double C = Units.Velocity.C;

            if (v1 > C || v2 > C) {
                if (view == button) {
                    CustomDialog.tooFast(getFragmentManager());
                }
                resultTextView.setText("");
                return;
            }

            final Double result = (v1 + v2) / (1 + (v1 * v2) / (C * C));
            final Double output = getDesiredResult(result);

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

    private Double getMPS1() {
        Double value = Double.parseDouble(editText1.getText().toString());
        Units.Velocity unit = Units.Velocity.values()[spinner1.getSelectedItemPosition()];
        return unit.convertTo(Units.Velocity.MPS, value);
    }

    private Double getMPS2() {
        Double value = Double.parseDouble(editText2.getText().toString());
        Units.Velocity unit = Units.Velocity.values()[spinner2.getSelectedItemPosition()];
        return unit.convertTo(Units.Velocity.MPS, value);
    }

    private Double getDesiredResult(Double resultNotConverted) {
        Units.Velocity unit = Units.Velocity.values()[resultSpinner.getSelectedItemPosition()];
        return Units.Velocity.MPS.convertTo(unit, resultNotConverted);
    }
}
