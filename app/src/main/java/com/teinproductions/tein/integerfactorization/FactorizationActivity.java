package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class FactorizationActivity extends ActionBarActivity {

    EditText numberEditText;
    TextView resultFactors;
    RelativeLayout resultContainer;
    ProgressBar progressBar;

    public static String RESULTTEXT;

    int animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorization);

        numberEditText = (EditText) findViewById(R.id.number_edit_text);
        resultFactors = (TextView) findViewById(R.id.result_factors_text_view);
        resultContainer = (RelativeLayout) findViewById(R.id.result_layout);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        animationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

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

        if (Integer.parseInt(numberEditText.getText().toString()) > 50000) {
            // Make an animation only when is lasts long enough.
            progressBar.setVisibility(View.VISIBLE);
            progressBar
                    .animate()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setListener(null);

            resultContainer
                    .animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultContainer.setVisibility(View.GONE);
                        }
                    });
        }

        new Factorize().execute();

    }

    class Factorize extends AsyncTask<Void, Void, Void> {

        String result;

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Integer[] resultArray = PrimeCalculator.factorize(Integer.parseInt(numberEditText.getText().toString()));

                outputResult(resultArray);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            resultFactors.setText(result);

            if (Integer.parseInt(numberEditText.getText().toString()) > 50000) {
                resultContainer.setAlpha(0f);
                resultContainer.setVisibility(View.VISIBLE);

                resultContainer
                        .animate()
                        .alpha(1f)
                        .setDuration(animationDuration)
                        .setListener(null);

                progressBar
                        .animate()
                        .alpha(0f)
                        .setDuration(animationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
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
