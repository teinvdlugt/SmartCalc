package com.teinproductions.tein.integerfactorization;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LCMActivity extends ActionBarActivity {

    private EditText number1, number2;
    private ProgressBar progressBar;
    private TextView resultTextView;
    private Integer num1, num2;

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

        progressBar.setVisibility(View.GONE);

    }

    public void onClickCalculate(View view) {

        try {
            num1 = Integer.parseInt(number1.getText().toString());
            num2 = Integer.parseInt(number2.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (num1 + num2 > 60000) {
            progressBar.setAlpha(0f);
            progressBar.setVisibility(View.VISIBLE);
            progressBar
                    .animate()
                    .alpha(1f)
                    .setDuration(animationDuration)
                    .setListener(null);
            resultTextView
                    .animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultTextView.setVisibility(View.GONE);
                        }
                    });
        }

        new GCFCreator().execute();

    }

    class GCFCreator extends AsyncTask<Void, Void, Void> {

        Integer result;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                result = PrimeCalculator.findLCM(num1, num2);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (num1 + num2 > 60000) {
                resultTextView.setText(result.toString());
                resultTextView.setAlpha(0f);
                resultTextView.setVisibility(View.VISIBLE);
                resultTextView
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
            } else {
                resultTextView.setAlpha(1f);
                resultTextView
                        .animate()
                        .alpha(0f)
                        .setDuration(animationDuration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                resultTextView.setText(result.toString());
                                resultTextView
                                        .animate()
                                        .alpha(1f)
                                        .setDuration(animationDuration)
                                        .setListener(null);
                            }
                        });

            }
        }
    }

}
