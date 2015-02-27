package com.teinproductions.tein.smartcalc.biology.bmi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.R;
import com.teinproductions.tein.smartcalc.Units;

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
            CustomDialog.invalidNumber(getSupportFragmentManager());
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
        animateBackgroundColors(
                colorFrom, colorTo,
                colorFromActionBar, colorToActionBar,
                colorFromStatusBar, colorToStatusBar);
    }

    private Integer getColorFrom() {
        if (backgroundColorID == null) {
            return getResources().getColor(R.color.windowBackground);
        } else {
            return getResources().getColor(backgroundColorID);
        }
    }

    private Integer getColorFromActionBar() {
        if (backgroundColorActionBarID == null) {
            return getResources().getColor(R.color.colorPrimary);
        } else {
            return getResources().getColor(backgroundColorActionBarID);
        }
    }

    private Integer getColorFromStatusBar() {
        if (backgroundColorStatusBarID == null) {
            return getResources().getColor(R.color.colorPrimaryDark);
        } else {
            return getResources().getColor(backgroundColorStatusBarID);
        }
    }

    private void animateBackgroundColors(
            final Integer colorFrom, final Integer colorTo,
            final Integer colorFromActionBar, final Integer colorToActionBar,
            final Integer colorFromStatusBar, final Integer colorToStatusBar) {

        BMIState.animateColor(
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

        BMIState.animateColor(
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
                        onActionBarColorAnimationEnd(colorFromStatusBar, colorToStatusBar);
                    }
                });
    }

    private void onActionBarColorAnimationEnd(final Integer colorFromStatusBar, final Integer colorToStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BMIState.animateColor(
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
            button.setBackgroundResource(backgroundColorID);
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(backgroundColorActionBarID)));
            getWindow().setStatusBarColor(getResources().getColor(backgroundColorStatusBarID));
        } catch (NullPointerException ignored) {
        }

    }
}
