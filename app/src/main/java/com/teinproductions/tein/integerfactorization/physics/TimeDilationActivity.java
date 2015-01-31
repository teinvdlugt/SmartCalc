package com.teinproductions.tein.integerfactorization.physics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.teinproductions.tein.integerfactorization.CustomDialog;
import com.teinproductions.tein.integerfactorization.EditTextActivity;
import com.teinproductions.tein.integerfactorization.R;
import com.teinproductions.tein.integerfactorization.Units;
import com.teinproductions.tein.integerfactorization.chemistry.molu.CalculateFragment;

public class TimeDilationActivity extends EditTextActivity {

    @Override
    protected void doYourStuff(Bundle savedInstanceState) {
        setTextAndInputTypes();
        setAdapters();
        setOnItemSelectedListeners();
        setSelections();
        setTextWatchers();
    }

    private void setTextAndInputTypes() {
        editText1.setHint(getString(R.string.velocity));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText2.setHint(getString(R.string.proper_time));
        resultDeclaration.setText(getString(R.string.dilated_time));
    }

    private void setAdapters() {
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        final ArrayAdapter adapter2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getAbbreviations(this));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        final ArrayAdapter resultAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getWords(this));
        resultAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resultSpinner.setAdapter(resultAdapter);
    }

    private void setOnItemSelectedListeners() {
        for (Spinner sp : new Spinner[]{spinner1, spinner2, resultSpinner}) {
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    onClickButton(view);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void setSelections() {
        final int MPS = Units.Velocity.MPS.ordinal();
        final int SEC = Units.Time.SEC.ordinal();

        spinner1.setSelection(MPS, false);
        spinner2.setSelection(SEC, false);
        resultSpinner.setSelection(SEC, false);
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

    @Override
    protected void onClickButton(View view) {
        try {
            Double v = getMPS();
            Double t = getSEC();

            if (v > Units.Velocity.C) {
                if (view == button) {
                    CustomDialog.tooFast(getSupportFragmentManager());
                }
                resultTextView.setText("");
                return;
            }

            final Double dilated = t / (Math.sqrt(1 - ((v * v) / (Units.Velocity.C * Units.Velocity.C))));
            final Double result = getDesiredResult(dilated);

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setText(Units.format(result));
                    fadeIn(resultTextView, null);
                }
            });
        } catch (NumberFormatException e) {
            if (view == button) {
                CustomDialog.invalidNumber(getSupportFragmentManager());
            }
        }
    }

    private Double getMPS() {
        Double value = Double.parseDouble(editText1.getText().toString());
        Units.Velocity unit = Units.Velocity.values()[(spinner1.getSelectedItemPosition())];
        return unit.convertTo(Units.Velocity.MPS, value);
    }

    private Double getSEC() {
        Double value = Double.parseDouble(editText2.getText().toString());
        Units.Time unit = Units.Time.values()[(spinner2.getSelectedItemPosition())];
        return unit.convertTo(Units.Time.SEC, value);
    }

    private Double getDesiredResult(Double resultNotConverted) {
        Units.Time resultTime = Units.Time.values()[(resultSpinner.getSelectedItemPosition())];
        return Units.Time.SEC.convertTo(resultTime, resultNotConverted);
    }
}
