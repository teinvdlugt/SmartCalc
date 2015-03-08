package com.teinproductions.tein.smartcalc.maths;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

import java.util.ArrayList;

public class RSAEncryptionActivity extends ActionBarActivity {

    EditText primeNumber1, primeNumber2;
    Button calculatePrimesButton;
    TextView eTextView;

    private long p, q, totient, e, d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        primeNumber1 = (EditText) findViewById(R.id.large_prime_number1);
        primeNumber2 = (EditText) findViewById(R.id.large_prime_number2);
        calculatePrimesButton = (Button) findViewById(R.id.calculate_button);
        eTextView = (TextView) findViewById(R.id.e_textView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    public void onClickCalcPrimeNumbers(View view) {
        Long number1 = Long.parseLong(primeNumber1.getText().toString()),
                number2 = Long.parseLong(primeNumber2.getText().toString());

        new FindNextPrimeNumbers().execute(number1, number2);
    }

    private void calculateTotient() {
        totient = (p - 1) * (q - 1);

        // new FindE().execute(totient);
        new FindNextE().execute(totient);
    }

    private void calculateD() {
        /* e*d mod totient = 1
        * What is d?
        * For that we use the extended Euclidean Algorithm
        *
        * Example:
        *  Step one:
        *    7*d mod 40 = 1
        *    40x + 7y = 1
        *    40 = 5(7) + 5
        *    7 = 1(5) + 2
        *    5 = 2(2) + 1
        *  Step two: 'Back substitution'
        *    1 = 5 - 2(2)        -- substitute the 2 with step one
        *    1 = 5 - 2(7 - 1(5)) -- simplify
        *    1 = 3(5) - 2(7)     -- substitute the 5
        *    1 = 3(40 - 5(7)) - 2(7)
        *    1 = 3(40) - 17(7)   -- we are interested in the -17
        *    If -17 were positive, -17 would be d
        *    If not, d = totient - 17
        */


        // STEP ONE

        // 40 = 5(7) + 5
        // We call the 5 the floorDiv, the 7 the
        // betweenBracket and the 2nd 5 the modulo.
        ArrayList<Long> floorDivs = new ArrayList<>();
        ArrayList<Long> betweenBrackets = new ArrayList<>();
        ArrayList<Long> modulos = new ArrayList<>();

        Long floorDiv = floorDiv(totient, e);
        Long betweenBracket = this.e;
        Long modulo = totient - floorDiv * betweenBracket;

        floorDivs.add(floorDiv);
        betweenBrackets.add(betweenBracket);
        modulos.add(modulo);

        while (modulo != 1) {
            floorDiv = floorDiv(betweenBracket, modulo);
            betweenBracket = modulo;
            modulo = betweenBrackets.get(betweenBrackets.size() - 1) - floorDiv * betweenBracket;

            floorDivs.add(floorDiv);
            betweenBrackets.add(betweenBracket);
            modulos.add(modulo);
        }

        StringBuilder sb = new StringBuilder("");
        sb.append(totient + " = " + floorDivs.get(0) + "(" + betweenBrackets.get(0) + ")" + " + " + modulos.get(0));
        for (int i = 1; i < floorDivs.size(); i++) {
            sb.append("\n" + betweenBrackets.get(i - 1) + " = " + floorDivs.get(i) + "(" + betweenBrackets.get(i) + ")" + " + " + modulos.get(i));
        }

        ((TextView) findViewById(R.id.e)).setText(sb.toString());

        /* Use this piece of code if you want to display the calculations made in step one
        *
        * StringBuilder sb = new StringBuilder("");
        * sb.append(totient + " = " + floorDivs.get(0) + "(" + betweenBrackets.get(0) + ")" + " + " + modulos.get(0));
        * for (int i = 1; i < floorDivs.size(); i++) {
        *   sb.append("\n" + betweenBrackets.get(i - 1) + " = " + floorDivs.get(i) + "(" + betweenBrackets.get(i) + ")" + " + " + modulos.get(i));
        * }
        *
        * ((TextView) findViewById(R.id.e)).setText(sb.toString());
        */


        // STEP TWO

    }

    private static long floorDiv(long totient, long e) {
        return (long) Math.floor(totient / e);
    }

    public void onClickNextE(View view) {
        new FindNextE().execute(totient, e);
    }


    class FindNextPrimeNumbers extends AsyncTask<Long, Void, Long[]> {
        @Override
        protected void onPreExecute() {
            calculatePrimesButton.setText(R.string.calculating);
        }

        @Override
        protected Long[] doInBackground(Long... numbers) {
            Long[] result = new Long[2];

            if (PrimeCalculator.isPrimeNumber(numbers[0], this)) {
                if (isCancelled()) return null;
                result[0] = numbers[0];
            } else {
                result[0] = PrimeCalculator.findNextPrimeNumber(numbers[0], this);
                if (isCancelled()) return null;
            }

            if (PrimeCalculator.isPrimeNumber(numbers[1], this)) {
                if (isCancelled()) return null;
                result[1] = numbers[1];
            } else {
                result[1] = PrimeCalculator.findNextPrimeNumber(numbers[1], this);
                if (isCancelled()) return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Long[] primes) {
            calculatePrimesButton.setText(R.string.calculate_prime_numbers);

            primeNumber1.setText(primes[0].toString());
            primeNumber2.setText(primes[1].toString());

            p = primes[0];
            q = primes[1];

            calculateTotient();
        }
    }

    class FindNextE extends AsyncTask<Long, Void, Long> {
        @Override
        protected Long doInBackground(Long... longs) {
            // longs[0] must be the totient,
            // longs[1] must be the number to start from (the previous e)
            long previousE = longs.length == 1 ? 2 : longs[1];

            for (long i = previousE + 1; i < longs[0]; i++) {
                if (PrimeCalculator.areRelativePrimes(longs[0], i, this)) {
                    return i;
                }
                if (isCancelled()) return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Long e) {
            RSAEncryptionActivity.this.e = e;
            eTextView.setText(e.toString());
            calculateD();
        }
    }
}