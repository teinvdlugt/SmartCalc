package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class FactorizationActivity extends ActionBarActivity {

    EditText numberEditText;
    TextView resultFactors;
    ProgressBar progressBar;
    Long input;

    public static String RESULTTEXT;

    int animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorization);

        numberEditText = (EditText) findViewById(R.id.number_edit_text);
        resultFactors = (TextView) findViewById(R.id.result_factors_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        animationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        numberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickFactorize(null);
                    return true;
                } return false;
            }
        });

        progressBar.setAlpha(0f);
        progressBar.setVisibility(View.GONE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.factorization, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onClickFactorize(View view) {

        try {
            input = Long.parseLong(numberEditText.getText().toString());

            progressBar.setAlpha(0f);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setListener(null);

            resultFactors.animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultFactors.setVisibility(View.GONE);
                            new Factorize().execute();
                        }
                    });

        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber().show(getFragmentManager(), "theDialog");

            resultFactors.animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultFactors.setText("");
                            resultFactors.animate()
                                    .alpha(1f)
                                    .setDuration(animationDuration)
                                    .setListener(null);
                        }
                    });
        }

    }

    class Factorize extends AsyncTask<Void, Void, Void> {

        String result;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Integer[] resultArray = PrimeCalculator.factorize(input);

                outputResult(resultArray);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            resultFactors.setText(result);
            resultFactors.setVisibility(View.VISIBLE);
            resultFactors.animate()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setListener(null);
            progressBar.animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
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
                    stringBuilder.append(", " + factors[i].toString());
                }
            }

            result = stringBuilder.toString();

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(RESULTTEXT, resultFactors.getText().toString());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            resultFactors.setText(savedInstanceState.getString(RESULTTEXT));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }
}
