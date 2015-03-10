package com.teinproductions.tein.smartcalc.maths;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RSAEncryptionActivity extends ActionBarActivity {

    EditText primeNumber1, primeNumber2, messageET, cipherET;
    Button calculatePrimesButton;
    TextView eTextView, dTextView;

    private Long p, q, n, totient, e, d, message, cipher;

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
        dTextView = (TextView) findViewById(R.id.d_textView);
        messageET = (EditText) findViewById(R.id.plain_text);
        cipherET = (EditText) findViewById(R.id.ciphertext);
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
        n = p * q;
        totient = (p - 1) * (q - 1);

        // new FindE().execute(totient);
        new FindNextE().execute(totient);
    }

    public void onClickNextE(View view) {
        new FindNextE().execute(totient, e);
    }

    public void onClickEncrypt(View view) {
        new Encrypt().execute();
    }

    public void onClickDecrypt(View view) {
        new Decrypt().execute();
    }

    public static long pow(long ground, long exp) {
        if (exp == 0) return 1;
        if (exp == 1) return ground;
        if (exp % 2 == 0) return pow(ground * ground, exp / 2);
        return ground * pow(ground * ground, (exp - 1) / 2);
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
            Log.i("CRAZY CHEESE", e.toString());
            new FindD().execute();
        }
    }

    class FindD extends AsyncTask<Void, Void, Void> {

        // StringBuilder to keep track of the calculations
        StringBuilder sb = new StringBuilder("");

        @Override
        protected Void doInBackground(Void... params) {
            calculateD();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((TextView) findViewById(R.id.e)).setText(sb.toString());
            dTextView.setText(d.toString());
        }

        void calculateD() {
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
            *    If -17 were positive, d would be -17
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
            Long betweenBracket = RSAEncryptionActivity.this.e;
            Long modulo = totient - floorDiv * betweenBracket;

            floorDivs.add(floorDiv);
            betweenBrackets.add(betweenBracket);
            modulos.add(modulo);

            while (modulo != 1) {
                if (isCancelled()) return;

                floorDiv = floorDiv(betweenBracket, modulo);
                betweenBracket = modulo;
                modulo = betweenBrackets.get(betweenBrackets.size() - 1) - floorDiv * betweenBracket;

                floorDivs.add(floorDiv);
                betweenBrackets.add(betweenBracket);
                modulos.add(modulo);
            }


            // To keep track of the calculations, you can use this StringBuilder.
            sb.append(totient + " = " + floorDivs.get(0) + "(" + betweenBrackets.get(0) + ")" + " + " + modulos.get(0));
            for (int i = 1; i < floorDivs.size(); i++) {
                sb.append("\n" + betweenBrackets.get(i - 1) + " = " + floorDivs.get(i) + "(" + betweenBrackets.get(i) + ")" + " + " + modulos.get(i));
            }


            // STEP TWO

            HashMap<Long, Long> map = new HashMap<>();
            try {
                map.put(betweenBrackets.get(betweenBrackets.size() - 2), 1L);
            } catch (ArrayIndexOutOfBoundsException e1) {
                map.put(totient, 1L);
            }
            map.put(betweenBrackets.get(betweenBrackets.size() - 1), -floorDivs.get(floorDivs.size() - 1));

            sb.append("\n" + calcToString(map));

            while (!isReady(map, totient, e)) {
                if (isCancelled()) return;

                for (int i = 0; i < map.size(); i++) {
                    Long key = (Long) map.keySet().toArray()[i];
                    Long value = map.get(key);

                    if (key.equals(totient) || key.equals(e)) {
                        continue;
                    }

                    int indexInArrays = modulos.indexOf(key);
                    try {
                        addValues(map, betweenBrackets.get(indexInArrays - 1), value);
                    } catch (ArrayIndexOutOfBoundsException e1) {
                        addValues(map, totient, value);
                    }
                    addValues(map, betweenBrackets.get(indexInArrays), value * -floorDivs.get(indexInArrays));

                    map.remove(0L);
                    map.remove(key);
                }

                sb.append("\n" + calcToString(map));
            }

            Long result = map.get(e);
            if (result < 0) {
                d = totient + result;
                sb.append("\nd = " + totient + " - " + -result);
            } else {
                d = result;
            }

            sb.append("\nd = " + d);
        }

        private long floorDiv(long totient, long e) {
            return (long) Math.floor(totient / e);
        }

        private boolean isReady(HashMap map, long totient, long e) {
            return map.containsKey(totient) && map.containsKey(e) && map.size() == 2;
        }

        private void addValues(HashMap<Long, Long> map, Long key, Long value) {
            if (map.containsKey(key)) {
                Long newValue = map.get(key) + value;
                map.remove(key);
                map.put(key, newValue);
            } else {
                map.put(key, value);
            }
        }

        private String calcToString(HashMap<Long, Long> map) {
            StringBuilder sb = new StringBuilder("");

            sb.append("1 = ");
            for (int i = 0; i < map.size(); i++) {
                if (i != 0) {
                    sb.append(" + ");
                }

                final Long key = (Long) map.keySet().toArray()[i];
                final Long value = map.get(key);

                sb.append(value + "(" + key + ")");
            }

            return sb.toString();
        }
    }

    class Encrypt extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            message = Long.parseLong(messageET.getText().toString());
            cipher = new BigInteger(message.toString()).pow((int) (long) e).mod(new BigInteger(n.toString())).longValue();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cipherET.setText(cipher.toString());
        }
    }

    class Decrypt extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            cipher = Long.parseLong(cipherET.getText().toString());
            message = new BigInteger(cipher.toString()).pow((int) (long) d).mod(new BigInteger(n.toString())).longValue();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            messageET.setText(message.toString());
        }
    }
}