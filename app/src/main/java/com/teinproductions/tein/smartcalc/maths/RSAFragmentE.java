package com.teinproductions.tein.smartcalc.maths;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RSAFragmentE extends Fragment {

    public static String TOTIENT = "totient";
    long totient, e, d;

    ProgressBar progressBarE, progressBarD;
    EditText editText;
    TextView textViewE, textViewD;
    Button buttonE, buttonD;

    FindNextE asyncTaskE;
    FindD asyncTaskD;

    public static RSAFragmentE newInstance(long totient) {
        RSAFragmentE fragment = new RSAFragmentE();
        Bundle args = new Bundle();
        args.putLong(TOTIENT, totient);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        totient = getArguments().getLong(TOTIENT);

        View layout = inflater.inflate(R.layout.fragment_rsa_e, container, false);

        progressBarE = (ProgressBar) layout.findViewById(R.id.progress_bar);
        progressBarD = (ProgressBar) layout.findViewById(R.id.progress_bar2);
        editText = (EditText) layout.findViewById(R.id.edit_text1);
        textViewE = (TextView) layout.findViewById(R.id.e_textView);
        textViewD = (TextView) layout.findViewById(R.id.d_textView);
        buttonE = (Button) layout.findViewById(R.id.calculate_e_button);
        buttonD = (Button) layout.findViewById(R.id.calculate_d_button);

        buttonD.setEnabled(false);
        buttonE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    long input = RSAFragmentPrimeNumbers.longValue(editText);
                    if (input > 1 && input < totient) {
                        onClickCalculateE();
                    } else {
                        CustomDialog.newInstance(
                                getString(R.string.out_of_range_rsa_title),
                                getString(R.string.out_of_range_rsa_message) + " " + totient)
                                .show(getActivity().getSupportFragmentManager(), "out_of_range_dialog");
                    }
                } catch (NumberFormatException e1) {
                    CustomDialog.invalidNumber(getActivity().getSupportFragmentManager());
                }
            }
        });
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCalculateD();
            }
        });

        return layout;
    }

    private void onClickCalculateE() {
        listener.disableNextButton();

        textViewE.animate()
                .alpha(0f)
                .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();

        textViewD.setText("");

        progressBarE.setAlpha(0f);
        progressBarE.setVisibility(View.VISIBLE);
        progressBarE.animate()
                .alpha(1f)
                .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        asyncTaskE = new FindNextE();
                        asyncTaskE.execute(totient, RSAFragmentPrimeNumbers.longValue(editText));
                    }
                }).start();
    }

    private void onClickCalculateD() {
        listener.disableNextButton();

        textViewD.animate()
                .alpha(0f)
                .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .start();

        progressBarD.setAlpha(0f);
        progressBarD.setVisibility(View.VISIBLE);
        progressBarD.animate()
                .alpha(1f)
                .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        asyncTaskD = new FindD();
                        asyncTaskD.execute();
                    }
                }).start();
    }

    public void cancelTasks() {
        if (asyncTaskE != null) {
            asyncTaskE.cancel(true);
        }

        if (asyncTaskD != null) {
            asyncTaskD.cancel(true);
        }
    }

    public long[] onClickNext() {
        return new long[]{e, d};
    }


    class FindNextE extends AsyncTask<Long, Void, Long> {

        @Override
        protected void onPreExecute() {
            listener.disableNextButton();
            buttonE.setEnabled(false);
            buttonD.setEnabled(false);
            editText.setEnabled(false);
        }

        @Override
        protected Long doInBackground(Long... longs) {
            // longs[0] must be the totient,
            // longs[1] must be the number to start from (the previous e)
            long previousE = longs.length == 1 ? 2 : longs[1];
            long totient = longs[0];

            for (long i = previousE; i < longs[0]; i++) {
                if (PrimeCalculator.areRelativePrimes(totient, i, this)) {
                    return i;
                }
                if (isCancelled()) return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Long e) {
            RSAFragmentE.this.e = e;
            textViewE.setText("e = " + e.toString());

            progressBarE.animate()
                    .alpha(0f)
                    .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            buttonD.setEnabled(true);
                            buttonE.setEnabled(true);
                            editText.setEnabled(true);
                        }
                    }).start();
            textViewE.animate()
                    .alpha(1f)
                    .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                    .start();
        }
    }

    class FindD extends AsyncTask<Void, Void, Void> {

        // StringBuilder to keep track of the calculations
        StringBuilder sb = new StringBuilder("");

        @Override
        protected void onPreExecute() {
            listener.disableNextButton();
            editText.setEnabled(false);
            buttonE.setEnabled(false);
            buttonD.setEnabled(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            calculateD();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            textViewD.setText("d = " + d);

            progressBarD.setVisibility(View.INVISIBLE);
            textViewD.animate()
                    .alpha(1f)
                    .setDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            buttonD.setEnabled(true);
                            buttonE.setEnabled(true);
                            listener.enableNextButton();
                            editText.setEnabled(true);
                        }
                    }).start();
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
            Long betweenBracket = RSAFragmentE.this.e;
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
            sb.append(totient).append(" = ").append(floorDivs.get(0)).append("(").append(betweenBrackets.get(0)).append(")").append(" + ").append(modulos.get(0));
            for (int i = 1; i < floorDivs.size(); i++) {
                sb.append("\n").append(betweenBrackets.get(i - 1)).append(" = ").append(floorDivs.get(i)).append("(").append(betweenBrackets.get(i)).append(")").append(" + ").append(modulos.get(i));
            }


            // STEP TWO

            HashMap<Long, Long> map = new HashMap<>();
            try {
                map.put(betweenBrackets.get(betweenBrackets.size() - 2), 1L);
            } catch (ArrayIndexOutOfBoundsException e1) {
                map.put(totient, 1L);
            }
            map.put(betweenBrackets.get(betweenBrackets.size() - 1), -floorDivs.get(floorDivs.size() - 1));

            sb.append("\n").append(calcToString(map));

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

                sb.append("\n").append(calcToString(map));
            }

            Long result = map.get(e);
            if (result < 0) {
                d = totient + result;
                sb.append("\nd = ").append(totient).append(" - ").append(-result);
            } else {
                d = result;
            }

            sb.append("\nd = ").append(d);
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
    }


    private Listener listener;

    public interface Listener {
        public void disableNextButton();

        public void enableNextButton();

        public void disablePreviousButton();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (Listener) activity;
    }
}
