package com.teinproductions.tein.smartcalc.maths;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.R;

public class GCFActivity extends EditTextActivity {

    Long num1, num2;
    private GCFCreator asyncTask;

    @Override
    protected void doYourStuff(Bundle savedInstanceState) {
        editText1.setHint(getString(R.string.number_1));
        editText2.setHint(getString(R.string.number_2));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);

        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        resultSpinner.setVisibility(View.GONE);

        clickButtonWhenFilledEditText(editText2);
        saveResultTextViewText = true;

        infoWebPageUri = "http://en.wikipedia.org/wiki/Greatest_common_factor";
        asyncTask = new GCFCreator();
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
                    execute();
                }
            });

        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getSupportFragmentManager());

            fadeOut(resultTextView, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    resultTextView.setText("");
                    fadeIn(resultTextView, null);
                }
            });
        }
    }

    private void execute() {
        asyncTask.cancel(true);
        asyncTask = new GCFCreator();
        asyncTask.execute(num1, num2);
    }

    @Override
    public void onBackPressed() {
        asyncTask.cancel(true);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                asyncTask.cancel(true);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class GCFCreator extends AsyncTask<Long, Void, Void> {

        Integer result;

        @Override
        protected Void doInBackground(Long... params) {

            try {
                result = PrimeCalculator.findGCF(params[0], params[1], this);
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
}
