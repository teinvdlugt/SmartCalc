package com.teinproductions.tein.integerfactorization;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GCFActivity extends ActionBarActivity {

    private EditText number1, number2;
    private ProgressBar progressBar;
    private TextView resultTextView;
    private Long num1, num2;

    int animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcf_or_lcm);

        number1 = (EditText) findViewById(R.id.number_1);
        number2 = (EditText) findViewById(R.id.number_2);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        resultTextView = (TextView) findViewById(R.id.result_gcf_or_lcm);

        animationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        number2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickCalculate(null);
                    return true;
                }
                return false;
            }
        });

        progressBar.setVisibility(View.GONE);

    }

    public void onClickCalculate(View view) {

        try {
            num1 = Long.parseLong(number1.getText().toString());
            num2 = Long.parseLong(number2.getText().toString());

            progressBar.setAlpha(0f);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.animate()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setListener(null);

            resultTextView.animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultTextView.setVisibility(View.GONE);
                            new GCFCreator().execute();
                        }
                    });

        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getFragmentManager());

            // Empty the result text view
            resultTextView.animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultTextView.setText("");
                            resultTextView.animate()
                                    .alpha(1f)
                                    .setDuration(animationDuration)
                                    .setListener(null);
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
            resultTextView.setVisibility(View.VISIBLE);
            resultTextView.animate()
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
