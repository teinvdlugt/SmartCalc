package com.teinproductions.tein.integerfactorization.biology.bmi;


import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;

import com.teinproductions.tein.integerfactorization.R;

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


}
