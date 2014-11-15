package com.teinproductions.tein.integerfactorization;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GCFActivity extends ActionBarActivity {

    private EditText number1,number2;
    private ProgressBar progressBar;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcf_or_lcm);

        number1 = (EditText) findViewById(R.id.number_1);
        number2 = (EditText) findViewById(R.id.number_2);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        resultTextView = (TextView) findViewById(R.id.result_gcf_or_lcm);

        progressBar.setVisibility(View.GONE);

    }

    public void onClickCalculate(View view) {

        new GCFCreator().execute();

    }

    class GCFCreator extends AsyncTask<Void, Void, Void> {

        Integer result;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                int num1 = Integer.parseInt(number1.getText().toString());
                int num2 = Integer.parseInt(number2.getText().toString());

                result = PrimeCalculator.findGCF(num1, num2);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultTextView.setText(result.toString());
        }
    }

}
