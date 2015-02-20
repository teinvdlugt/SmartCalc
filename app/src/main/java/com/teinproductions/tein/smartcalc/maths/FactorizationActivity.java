package com.teinproductions.tein.smartcalc.maths;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.R;


public class FactorizationActivity extends EditTextActivity {

    private Long input;
    private FactorizationAsyncTask asyncTask;

    @Override
    protected void doYourStuff(Bundle savedInstanceState) {
        editText1.setHint(getString(R.string.number_to_factorize_hint));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText1.setImeOptions(EditorInfo.IME_ACTION_GO);
        button.setText(getString(R.string.factorize_button));
        resultDeclaration.setText(getString(R.string.factorize_result_declaration));

        editText2.setVisibility(View.GONE);
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        resultSpinner.setVisibility(View.GONE);

        clickButtonWhenFilledEditText(editText1);
        saveResultTextViewText = true;

        infoWebPageUri = "http://en.wikipedia.org/wiki/Integer_factorization";
        asyncTask = new FactorizationAsyncTask();
    }

    public void onClickButton(View view) {
        try {
            input = Long.parseLong(editText1.getText().toString());

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
        asyncTask = new FactorizationAsyncTask();
        asyncTask.execute();
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

    class FactorizationAsyncTask extends AsyncTask<Void, Void, Void> {

        String result;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Integer[] resultArray = PrimeCalculator.factorize(input, this);

                outputResult(resultArray);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultTextView.setText(result);
            fadeIn(resultTextView, null);
            fadeOut(progressBar, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        private void outputResult(Integer[] factors) {
            StringBuilder stringBuilder = new StringBuilder("");

            for (int i = 0; i < factors.length; i++) {
                if (i == 0) {
                    stringBuilder.append(factors[i].toString());
                } else {
                    stringBuilder.append(", ").append(factors[i].toString());
                }
            }

            result = stringBuilder.toString();
        }
    }
}
