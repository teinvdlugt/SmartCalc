package com.teinproductions.tein.smartcalc.maths;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.R;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class RSAFragmentCrack2 extends Fragment {

    public static final String CIPHER = "cipher";
    public static final String N = "n";
    public static final String E = "e";

    TextView heading, progress;

    long cipher, n, e;

    Cracker cracker;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = new LinearLayout(getActivity());
        layout.setOrientation(LinearLayout.VERTICAL);

        heading = new TextView(getActivity());
        heading.setTextAppearance(getActivity(), android.R.style.TextAppearance_Large);
        heading.setText(getString(R.string.cracking_dot_dot_dot));

        progress = new TextView(getActivity());
        progress.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
        progress.setHorizontalScrollBarEnabled(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 0);
        progress.setLayoutParams(params);

        layout.addView(heading);
        layout.addView(progress);

        cipher = getArguments().getLong(CIPHER);
        n = getArguments().getLong(N);
        e = getArguments().getLong(E);

        cracker = new Cracker();
        cracker.execute();

        return layout;
    }

    public void cancelTasks() {
        cracker.cancel(true);
    }

    public static RSAFragmentCrack2 newInstance(long cipher, long n, long e) {
        RSAFragmentCrack2 fragment = new RSAFragmentCrack2();
        Bundle args = new Bundle();
        args.putLong(CIPHER, cipher);
        args.putLong(N, n);
        args.putLong(E, e);
        fragment.setArguments(args);

        return fragment;
    }


    class Cracker extends AsyncTask<Void, String, Long> {

        @Override
        protected void onPreExecute() {
            progress.setText("Preparing...");
        }

        @Override
        protected Long doInBackground(Void... voids) {
            try {
                Thread.sleep(200);
                // Just to be cool
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (isCancelled()) return null;

            long p, q, totient;

            publishProgress("Factorizing n...");
            Long[] primeFactors = PrimeCalculator.factorizeLong(n, this);
            if (isCancelled()) return null;

            if (primeFactors.length != 2) {
                publishProgress("ERROR: incorrect n", "Failed, aborting...", "Aborted");
                return null;
            }

            p = primeFactors[0];
            q = primeFactors[1];
            totient = (p - 1) * (q - 1);

            publishProgress(
                    "p = " + p,
                    "q = " + q,
                    "e = " + e,
                    "totient = " + totient,
                    "Calculating d...");

            if (isCancelled()) return null;

            int d = 0;
            try {
                d = (int) calculateD(totient);
                if (isCancelled()) return null;
            } catch (Exception e1) {
                publishProgress("An error occured", "Failed, aborting...", "Aborted");
                return null;
            }

            publishProgress("d = " + d, "m = " + cipher + "^" + d + " mod " + n);

            BigInteger cipher = new BigInteger(Long.toString(RSAFragmentCrack2.this.cipher));
            BigInteger n = new BigInteger(Long.toString(RSAFragmentCrack2.this.n));

            BigInteger message = cipher.pow(d).mod(n);

            publishProgress("\nm = " + message);

            return message.longValue();
        }

        long calculateD(long totient) {
            // Just copied this method from RSAFragmentE and did some alterations

            // STEP ONE

            ArrayList<Long> floorDivs = new ArrayList<>();
            ArrayList<Long> betweenBrackets = new ArrayList<>();
            ArrayList<Long> modulos = new ArrayList<>();

            Long floorDiv = floorDiv(totient, e);
            Long betweenBracket = RSAFragmentCrack2.this.e;
            Long modulo = totient - floorDiv * betweenBracket;

            floorDivs.add(floorDiv);
            betweenBrackets.add(betweenBracket);
            modulos.add(modulo);

            while (modulo != 1) {
                if (isCancelled()) return -1;

                floorDiv = floorDiv(betweenBracket, modulo);
                betweenBracket = modulo;
                modulo = betweenBrackets.get(betweenBrackets.size() - 1) - floorDiv * betweenBracket;

                floorDivs.add(floorDiv);
                betweenBrackets.add(betweenBracket);
                modulos.add(modulo);
            }


            publishProgress(totient + " = " + floorDivs.get(0) + "(" + betweenBrackets.get(0) + ") + " + modulos.get(0));
            for (int i = 1; i < floorDivs.size(); i++) {
                publishProgress(betweenBrackets.get(i - 1) + " = " + floorDivs.get(i) + "(" + betweenBrackets.get(i) + ")" + " + " + modulos.get(i));
            }


            // STEP TWO

            HashMap<Long, Long> map = new HashMap<>();
            try {
                map.put(betweenBrackets.get(betweenBrackets.size() - 2), 1L);
            } catch (ArrayIndexOutOfBoundsException e1) {
                map.put(totient, 1L);
            }
            map.put(betweenBrackets.get(betweenBrackets.size() - 1), -floorDivs.get(floorDivs.size() - 1));

            publishProgress(calcToString(map));

            while (!isReady(map, totient, e)) {
                if (isCancelled()) return -1;

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

                publishProgress(calcToString(map));
            }

            Long result = map.get(e);
            if (result < 0) {
                publishProgress("d = " + totient + " - " + -result);
                return totient + result;
            } else {
                return result;
            }
        }

        private long floorDiv(long totient, long e) {
            return (long) Math.floor(totient / e);
        }

        private boolean isReady(HashMap map, long totient, long e) {
            return map.containsKey(totient) && map.containsKey(e) && map.size() == 2;
        }

        private void addValues(HashMap<Long, Long> map, Long key, Long value) {
            // If there is already a value with this key, the values are
            // added, if not, the new value is added with a new key

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

                sb.append(value).append("(").append(key).append(")");
            }

            return sb.toString();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            for (String string : values) {
                progress.append("\n" + string);
            }
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result != null) {
                CustomDialog.newInstance
                        (getString(R.string.crack_successful), getString(R.string.the_message_was) + " " + result)
                        .show(getActivity().getSupportFragmentManager(), "result_of_crack_dialog");
            }
        }
    }
}