package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;

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
        resultExplanation.setVisibility(View.VISIBLE);

        saveResultTextViewText = true;

    }

    public enum BMIState {
        VERY_SEVERELY_UNDERWEIGHT(R.string.very_severely_underweight, R.color.very_severely_underweight),
        SEVERELY_UNDERWEIGHT(R.string.severely_underweight, R.color.severely_underweight),
        UNDERWEIGHT(R.string.underweight, R.color.underweight),
        GOOD_WEIGHT(R.string.excellent_good_weight, R.color.good_weight),
        OVERWEIGHT(R.string.overweight, R.color.overweight),
        MODERATELY_OBESE(R.string.moderately_obese, R.color.moderately_obese),
        SEVERELY_OBESE(R.string.severely_obese, R.color.severely_obese),
        VERY_SEVERELY_OBESE(R.string.very_severely_obese, R.color.very_severely_obese);

        BMIState(int text, int color) {
            this.text = text;
            this.color = color;
        }

        int text, color;

        public String getText(Context context) {
            return context.getString(text);
        }

        public int getColor() {
            return color;
        }

        public static BMIState getBMIState(Double BMI) {
            if (BMI < 15) {
                return BMIState.VERY_SEVERELY_UNDERWEIGHT;
            } else if (BMI < 16) {
                return BMIState.SEVERELY_UNDERWEIGHT;
            } else if (BMI < 18.5) {
                return BMIState.UNDERWEIGHT;
            } else if (BMI < 25) {
                return BMIState.GOOD_WEIGHT;
            } else if (BMI < 30) {
                return BMIState.OVERWEIGHT;
            } else if (BMI < 35) {
                return BMIState.MODERATELY_OBESE;
            } else if (BMI < 40) {
                return BMIState.SEVERELY_OBESE;
            } else {
                return BMIState.VERY_SEVERELY_OBESE;
            }
        }

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

            final Double BMI = weight / length / length;

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setText(new DecimalFormat("0.##").format(BMI));
                    fadeIn(resultTextView, null);
                    fadeOut(resultExplanation, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultExplanation.setText(BMIState.getBMIState(BMI).getText(BMIActivity.this));
                            fadeIn(resultExplanation, null);
                        }
                    });
                }
            });

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
