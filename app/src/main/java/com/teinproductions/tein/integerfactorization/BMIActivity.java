package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class BMIActivity extends EditTextActivity {

    Integer backgroundColor;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.element_pager, menu);
        MenuItem menuItem = menu.findItem(R.id.pager_activity_show_list_action);
        menuItem.setTitle(getString(R.string.info));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pager_activity_show_list_action:
                Intent intent = new Intent(this, BMIInfoActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public enum BMIState {
        VERY_SEVERELY_UNDERWEIGHT(R.color.very_severely_underweight),
        SEVERELY_UNDERWEIGHT(R.color.severely_underweight),
        UNDERWEIGHT(R.color.underweight),
        GOOD_WEIGHT(R.color.good_weight),
        OVERWEIGHT(R.color.overweight),
        MODERATELY_OBESE(R.color.moderately_obese),
        SEVERELY_OBESE(R.color.severely_obese),
        VERY_SEVERELY_OBESE(R.color.very_severely_obese);

        BMIState(int color) {
            this.color = color;
        }

        private int color;

        public String getName(Context context) {
            return context.getResources().getStringArray(R.array.BMIStateNames)[this.ordinal()];
        }

        public String getValue(Context context) {
            return context.getResources().getStringArray(R.array.BMIStateValues)[this.ordinal()];
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
            final BMIState state = BMIState.getBMIState(BMI);

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setText(new DecimalFormat("0.##").format(BMI));
                    fadeIn(resultTextView, null);
                    fadeOut(resultExplanation, new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultExplanation.setText(state.getName(BMIActivity.this));
                            fadeIn(resultExplanation, null);

                            animateBackgroundColor(state);

                        }
                    });
                }
            });

        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getFragmentManager());
        }
    }

    private void animateBackgroundColor(BMIState state) {
        final Integer colorFrom;
        if (backgroundColor == null) {
            colorFrom = getResources().getColor(android.R.color.white);
        } else {
            colorFrom = backgroundColor;
        }
        final Integer colorTo = getResources().getColor(state.getColor());

        backgroundColor = colorTo;

        final ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.setDuration(animDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rootLayout.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                final ValueAnimator actionBarAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                actionBarAnimator.setDuration(animDuration);
                actionBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Integer) animation.getAnimatedValue()));
                    }
                });
                actionBarAnimator.start();
            }
        });

        animator.start();

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (backgroundColor == null) {
            outState.putInt("BACKGROUND_COLOR", 0);
        } else {
            outState.putInt("BACKGROUND_COLOR", backgroundColor);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            backgroundColor = savedInstanceState.getInt("BACKGROUND_COLOR");
            if (backgroundColor == 0) {
                throw new NullPointerException("background was empty");
            }

            rootLayout.setBackgroundColor(backgroundColor);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(backgroundColor));
        } catch (NullPointerException ignored) {
        }

    }
}
