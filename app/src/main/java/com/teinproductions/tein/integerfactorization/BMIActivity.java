package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import java.text.DecimalFormat;

public class BMIActivity extends EditTextActivity {

    private Integer backgroundColorID;
    private Integer backgroundColorActionBarID;
    private Integer backgroundColorStatusBarID;

    private static String BACKGROUND_COLOR_ID = "com.teinproductions.tein.integerfactorization.BACKGROUND_COLOR_ID";
    private static String BACKGROUND_COLOR_ACTION_BAR_ID = "com.teinproductions.tein.integerfactorization.BACKGROUND_COLOR_ACTION_BAR_ID";
    private static String BACKGROUND_COLOR_STATUS_BAR_ID = "com.teinproductions.tein.integerfactorization.BACKGROUND_COLOR_STATUS_BAR_ID";

    @Override
    protected void doYourStuff(Bundle savedInstanceState) {
        setHintsAndInputTypes();
        clickButtonWhenFilledEditText(editText2);

        setAdapters();
        spinner1.setSelection(2);
        spinner2.setSelection(5);

        resultDeclaration.setText(getString(R.string.your_bmi_is));
        resultSpinner.setVisibility(View.GONE);
        resultExplanation.setVisibility(View.VISIBLE);

        restoreColors(savedInstanceState);
    }

    private void setHintsAndInputTypes() {
        editText1.setHint(getString(R.string.enter_your_weight));
        editText2.setHint(getString(R.string.enter_your_length));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bmi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_bmi_info:
                startBMIInfoActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startBMIInfoActivity() {
        Intent intent = new Intent(this, BMIInfoActivity.class);
        intent.putExtra(BMIInfoActivity.BACKGROUND_COLOR_ID, backgroundColorID);
        intent.putExtra(BMIInfoActivity.BACKGROUND_COLOR_ACTION_BAR_ID, backgroundColorActionBarID);
        intent.putExtra(BMIInfoActivity.BACKGROUND_COLOR_STATUS_BAR_ID, backgroundColorStatusBarID);
        startActivity(intent);
    }

    public enum BMIState {
        VERY_SEVERELY_UNDERWEIGHT(R.color.very_severely_underweight, R.color.very_severely_underweight_actionBar, R.color.very_severely_underweight_statusBar),
        SEVERELY_UNDERWEIGHT(R.color.severely_underweight, R.color.severely_underweight_actionBar, R.color.severely_underweight_statusBar),
        UNDERWEIGHT(R.color.underweight, R.color.underweight_actionBar, R.color.underweight_statusBar),
        GOOD_WEIGHT(R.color.good_weight, R.color.good_weight_actionBar, R.color.good_weight_statusBar),
        OVERWEIGHT(R.color.overweight, R.color.overweight_actionBar, R.color.overweight_statusBar),
        MODERATELY_OBESE(R.color.moderately_obese, R.color.moderately_obese_actionBar, R.color.moderately_obese_statusBar),
        SEVERELY_OBESE(R.color.severely_obese, R.color.severely_obese_actionBar, R.color.severely_obese_statusBar),
        VERY_SEVERELY_OBESE(R.color.very_severely_obese, R.color.very_severely_obese_actionBar, R.color.very_severely_obese_statusBar);

        BMIState(int colorID, int colorActionBar, int colorStatusBar) {
            this.colorID = colorID;
            this.colorActionBarID = colorActionBar;
            this.colorStatusBarID = colorStatusBar;
        }

        private int colorID, colorActionBarID, colorStatusBarID;

        public String getName(Context context) {
            return context.getResources().getStringArray(R.array.BMIStateNames)[this.ordinal()];
        }

        public static String[] getNames(Context context) {
            String[] names = new String[BMIState.values().length];
            for (int i = 0; i < names.length; i++) {
                names[i] = BMIState.values()[i].getName(context);
            }
            return names;
        }

        public String getValue(Context context) {
            return context.getResources().getStringArray(R.array.BMIStateValues)[this.ordinal()];
        }

        public int getColorID() {
            return colorID;
        }

        public int getColorActionBarID() {
            return colorActionBarID;
        }

        public int getColorStatusBarID() {
            return colorStatusBarID;
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

        public static void animateColor(
                Context context,
                final Integer colorFrom,
                final Integer colorTo,
                ValueAnimator.AnimatorUpdateListener updateListener,
                AnimatorListenerAdapter endListener) {

            final ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            animator.setDuration(context.getResources().getInteger(android.R.integer.config_shortAnimTime));
            animator.addUpdateListener(updateListener);
            if (endListener != null) animator.addListener(endListener);
            animator.start();
        }


    } // END OF ENUM

    @Override
    protected void onClickButton(View view) {
        try {
            final Double weight = getWeightKG();
            final Double length = getLengthM();

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

                            animateBackgroundColors(state);

                        }
                    });
                }
            });

        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getFragmentManager());
        }
    }

    private Double getWeightKG() {
        Double value = Double.parseDouble(editText1.getText().toString());
        Units.Mass unit = Units.Mass.values()[spinner1.getSelectedItemPosition()];
        return unit.convertTo(Units.Mass.KILOGRAMS, value);
    }

    private Double getLengthM() {
        Double value = Double.parseDouble(editText2.getText().toString());
        Units.Length unit = Units.Length.values()[spinner2.getSelectedItemPosition()];
        return unit.convertTo(Units.Length.METER, value);
    }

    private void animateBackgroundColors(final BMIState state) {
        final Integer colorFrom;
        final Integer colorTo;
        final Integer colorFromActionBar;
        final Integer colorToActionBar;
        final Integer colorFromStatusBar;
        final Integer colorToStatusBar;

        colorFrom = getColorFrom();
        backgroundColorID = state.getColorID();
        colorTo = getResources().getColor(backgroundColorID);

        // Initialize colorFromActionBar and colorToActionBar
        colorFromActionBar = getColorFromActionBar();
        backgroundColorActionBarID = state.getColorActionBarID();
        colorToActionBar = getResources().getColor(backgroundColorActionBarID);

        // Initialize colorFromStatusBar and colorToStatusBar
        colorFromStatusBar = getColorFromStatusBar();
        backgroundColorStatusBarID = state.getColorStatusBarID();
        colorToStatusBar = getResources().getColor(backgroundColorStatusBarID);

        // animate the background color
        
    }

    private Integer getColorFrom() {
        if (backgroundColorID == null) {
            return getResources().getColor(android.R.color.white);
        } else {
            return getResources().getColor(backgroundColorID);
        }
    }

    private Integer getColorFromActionBar() {
        if (backgroundColorActionBarID == null) {
            return getResources().getColor(android.R.color.darker_gray);
        } else {
            return getResources().getColor(backgroundColorActionBarID);
        }
    }

    private Integer getColorFromStatusBar() {
        if (backgroundColorStatusBarID == null) {
            return getResources().getColor(android.R.color.darker_gray);
        } else {
            return getResources().getColor(backgroundColorStatusBarID);
        }
    }

    private void animateBackgroundColors(
            final Integer colorFrom, final Integer colorTo, 
            final Integer colorFromActionBar, final Integer colorToActionBar, 
            final Integer colorFromStatusBar, final Integer colorToStatusBar) {

        BMIActivity.BMIState.animateColor(
                getApplicationContext(),
                colorFrom,
                colorTo,
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        rootLayout.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                },
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // After that, animate the color of the Action bar and the Status bar
                        onBackgroundColorAnimationEnd(
                                colorFromActionBar, colorToActionBar, 
                                colorFromStatusBar, colorToStatusBar);
                    }
                });
    }

    private void onBackgroundColorAnimationEnd(
            final Integer colorFromActionBar, final Integer colorToActionBar, 
            final Integer colorFromStatusBar, final Integer colorToStatusBar) {
        
        BMIActivity.BMIState.animateColor(
                                getApplicationContext(),
                                colorFromActionBar,
                                colorToActionBar,
                                new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Integer) animation.getAnimatedValue()));
                                        button.setBackgroundColor((Integer) animation.getAnimatedValue());
                                    }
                                },
                                new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // After that, if lollipop or higher, animate the color of the status bar
                                        onActionBarColorAnimationEnd(colorFromStatusBar, colorToStatusBar)
                                    }
                                });
    }

    private void onActionBarColorAnimationEnd(final Integer colorFromStatusBar, final Integer colorToStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BMIActivity.BMIState.animateColor(
                getApplicationContext(),
                colorFromStatusBar,
                colorToStatusBar,
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                    getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
                    }
                }, null);
        }
    }

    private void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                Units.Mass.getAbbreviations(BMIActivity.this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(BMIActivity.this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the background colors
        try {
            outState.putInt(BACKGROUND_COLOR_ID, backgroundColorID);
            outState.putInt(BACKGROUND_COLOR_ACTION_BAR_ID, backgroundColorActionBarID);
            outState.putInt(BACKGROUND_COLOR_STATUS_BAR_ID, backgroundColorStatusBarID);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Set the background colors
        try {
            backgroundColorID = savedInstanceState.getInt(BACKGROUND_COLOR_ID);
            backgroundColorActionBarID = savedInstanceState.getInt(BACKGROUND_COLOR_ACTION_BAR_ID);
            backgroundColorStatusBarID = savedInstanceState.getInt(BACKGROUND_COLOR_STATUS_BAR_ID);

            rootLayout.setBackgroundColor(getResources().getColor(backgroundColorID));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(backgroundColorActionBarID)));
            getWindow().setStatusBarColor(getResources().getColor(backgroundColorStatusBarID));
        } catch (NullPointerException ignored) {
        }

    }

    @Override
    protected void onStop() {
        saveColorIDs();
        super.onStop();
    }

    private void saveColorIDs() {
        SharedPreferences sp = getPreferences(Context.MODE_PRIVATE).edit();

        sp.putInt(BACKGROUND_COLOR_ID, backgroundColorID);
        sp.putInt(BACKGROUND_COLOR_ACTION_BAR_ID, backgroundColorActionBarID);
        sp.putInt(BACKGROUND_COLOR_STATUS_BAR_ID, backgroundColorStatusBarID);

        sp.commit();
    }

    private void restoreColors(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            backgroundColorID = savedInstanceState.getInt(BACKGROUND_COLOR_ID);
            backgroundColorActionBarID = savedInstanceState.getInt(BACKGROUND_COLOR_ACTION_BAR_ID);
            backgroundColorStatusBarID = savedInstanceState.getInt(BACKGROUND_COLOR_STATUS_BAR_ID);

            rootLayout.setBackgroundColor(getResources().getColor(backgroundColorID));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(backgroundColorActionBarID)));
            getWindow().setStatusBarColor(getResources().getColor(backgroundColorStatusBarID));
        }
    }
}
