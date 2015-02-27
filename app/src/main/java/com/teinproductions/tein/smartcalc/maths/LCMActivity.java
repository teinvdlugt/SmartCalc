package com.teinproductions.tein.smartcalc.maths;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.R;

public class LCMActivity extends EditTextActivity {

    private Long num1, num2;
    private LCMCreator asyncTask;

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

        infoWebPageUri = "http://en.wikipedia.org/wiki/Least_common_multiple";
        asyncTask = new LCMCreator(this);
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
        asyncTask = new LCMCreator(this);
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

    @Override
    protected void onDestroy() {
        asyncTask.cancel(true);
        super.onDestroy();
    }

    class LCMCreator extends AsyncTask<Long, Void, Void> {

        Integer result;
        Context context;

        LCMCreator(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Long... params) {

            try {
                result = PrimeCalculator.findLCM(params[0], params[1], this);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            String resultText;

            if (result == null) {
                resultText = context.getString(R.string.none);
            } else {
                resultText = result.toString();
            }

            resultTextView.setText(resultText);
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
