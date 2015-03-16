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
    PrimeFinder asyncTask;
    String input1, input2;

    public static RSAFragmentPrimeNumbers newInstance() {
        RSAFragmentPrimeNumbers fragment = new RSAFragmentPrimeNumbers();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa_prime_numbers, container, false);

        editText1 = (EditText) layout.findViewById(R.id.edit_text1);
        editText2 = (EditText) layout.findViewById(R.id.edit_text2);

        setTextWatchers();
        listener.enablePreviousButton();

        return layout;
    }

    private void setTextWatchers() {
        for (final EditText e : new EditText[]{editText1, editText2}) {
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (directTextChange) {
                        if (e.equals(editText1)) input1 = editText1.getText().toString();
                        else if (e.equals(editText2)) input2 = editText2.getText().toString();

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
        asyncTask = new PrimeFinder();
        asyncTask.execute();
    }

    public void cancelTasks() {
        if (asyncTask != null) asyncTask.cancel(true);

        if (!RSAFragmentPrimeNumbers.validLongInput(editText1)) editText1.setText(input1);
        if (!RSAFragmentPrimeNumbers.validLongInput(editText2)) editText2.setText(input2);
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

        long prime1, prime2;
        long input1, input2;

        final long EDIT_TEXT_1 = 1, EDIT_TEXT_2 = 2;

        @Override
        protected void onPreExecute() {
            directTextChange = false;

            listener.disableNextButton();

            input1 = longValue(editText1);
            input2 = longValue(editText2);

            editText1.setText(getString(R.string.calculating));

            editText1.setFocusable(false);
            editText2.setFocusable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            prime1 = findNextPrimeNumber(input1, EDIT_TEXT_1);
            if (isCancelled()) return null;
            publishProgress();
            prime2 = findNextPrimeNumber(input2, EDIT_TEXT_2);

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            editText1.setText(prime1 + "");
            editText2.setText(getString(R.string.calculating));
        }

        @Override
        protected void onCancelled() {
            editText1.setText(input1 + "");
            editText2.setText(input1 + "");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            editText2.setText(prime2 + "");

            editText1.setFocusable(false);
            editText2.setFocusable(false);

            directTextChange = true;

            listener.calculatedPrimeNumbers(prime1, prime2);
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

        public void enablePreviousButton();

        public void calculatedPrimeNumbers(long p, long q);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (Listener) activity;
    }
}