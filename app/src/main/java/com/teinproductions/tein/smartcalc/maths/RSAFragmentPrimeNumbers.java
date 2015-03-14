package com.teinproductions.tein.smartcalc.maths;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.teinproductions.tein.smartcalc.R;

public class RSAFragmentPrimeNumbers extends Fragment {

    EditText editText1, editText2;
    boolean directTextChange = true;

    public static final String P = "P", Q = "Q";

    public static RSAFragmentPrimeNumbers newInstance(long p, long q) {
        RSAFragmentPrimeNumbers fragment = new RSAFragmentPrimeNumbers();
        Bundle args = new Bundle();
        args.putLong(P, p);
        args.putLong(Q, q);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa_prime_numbers, container, false);

        editText1 = (EditText) layout.findViewById(R.id.edit_text1);
        editText2 = (EditText) layout.findViewById(R.id.edit_text2);

        setTextWatchers();
        setTexts();

        return layout;
    }

    private void setTextWatchers() {
        for (EditText e : new EditText[]{editText1, editText2}) {
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (directTextChange) {
                        if (editText1.length() == 0 || editText2.length() == 0) {
                            listener.disableNextButton();
                        } else {
                            listener.enableNextButton();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void onClickNext() {
        new PrimeFinder().execute();
    }

    public long[] onClickPrevious() {
        long[] result = new long[2];
        try {
            result[0] = Long.parseLong(editText1.getText().toString());
        } catch (NumberFormatException e) {
            result[0] = -1;
        }

        try {
            result[1] = Long.parseLong(editText2.getText().toString());
        } catch (NumberFormatException e) {
            result[1] = -1;
        }

        return result;
    }

    public void setTexts() {
        long p = getArguments().getLong(P), q = getArguments().getLong(Q);

        if (p > -1) {
            editText1.setText("" + p);
        } else {
            editText1.setText("");
        }

        if (q > -1) {
            editText2.setText("" + q);
        } else {
            editText1.setText("");
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean validLongInput(EditText et) {
        try {
            Long.parseLong(et.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static long longValue(EditText et) {
        return Long.parseLong(et.getText().toString());
    }


    class PrimeFinder extends AsyncTask<Void, Void, Void> {

        long num1, num2;
        long prime1, prime2;

        final long EDIT_TEXT_1 = 1, EDIT_TEXT_2 = 2;

        @Override
        protected void onPreExecute() {
            directTextChange = false;

            listener.disableNextButton();
            listener.disablePreviousButton();

            num1 = longValue(editText1);
            num2 = longValue(editText2);

            editText1.setText(getString(R.string.calculating));

            editText1.setFocusable(false);
            editText2.setFocusable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            prime1 = findNextPrimeNumber(num1, EDIT_TEXT_1);
            publishProgress();
            prime2 = findNextPrimeNumber(num2, EDIT_TEXT_2);

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            editText1.setText(prime1 + "");
            editText2.setText(getString(R.string.calculating));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            editText2.setText(prime2 + "");

            listener.calculatedPrimeNumbers(prime1, prime2);

            editText1.setFocusable(false);
            editText2.setFocusable(false);

            directTextChange = true;
        }

        @SuppressWarnings("ConstantConditions")
        long findNextPrimeNumber(long num, long editText) {
            long i = num;
            while (1 + 1 == 2) {
                if (isCancelled()) return -1;

                if (PrimeCalculator.isPrimeNumber(i, this)) {
                    return i;
                }

                if (isCancelled()) return -1;
                i++;
            }
        }
    }


    private Listener listener;


    public interface Listener {
        public void disableNextButton();

        public void enableNextButton();

        public void disablePreviousButton();

        public void calculatedPrimeNumbers(long p, long q);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (Listener) activity;
    }

}