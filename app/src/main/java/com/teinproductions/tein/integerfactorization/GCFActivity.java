package com.teinproductions.tein.integerfactorization;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

public class GCFActivity extends EditTextActivity {

    Long num1, num2;

    @Override
    protected void doYourStuff() {
        editText1.setHint(getString(R.string.number_1));
        editText2.setHint(getString(R.string.number_2));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        resultSpinner.setVisibility(View.GONE);

        clickButtonWhenFilledEditText(editText2);
    }

    public void onClickButton(View view) {

        try {
            num1 = Long.parseLong(editText1.getText().toString());
            num2 = Long.parseLong(editText2.getText().toString());

            fadeIn(progressBar, null);

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setVisibility(View.GONE);
                    new GCFCreator().execute();
                }
            });

        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getFragmentManager());

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setText("");
                    fadeIn(resultTextView, null);
                }
            });
        }
    }

    class GCFCreator extends AsyncTask<Void, Void, Void> {

        Integer result;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                result = PrimeCalculator.findLongGCF(num1, num2);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            resultTextView.setText(result.toString());
            fadeIn(resultTextView, null);
            fadeOut(progressBar, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("RESULT", resultTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            resultTextView.setText(savedInstanceState.getString("RESULT"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
