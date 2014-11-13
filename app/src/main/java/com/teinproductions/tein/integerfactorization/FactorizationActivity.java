package com.teinproductions.tein.integerfactorization;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class FactorizationActivity extends ActionBarActivity {

    EditText numberEditText;
    TextView resultFactors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factorization);

        numberEditText = (EditText) findViewById(R.id.number_edit_text);
        resultFactors = (TextView) findViewById(R.id.result_factors_text_view);

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

}
