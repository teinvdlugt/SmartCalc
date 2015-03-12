package com.teinproductions.tein.smartcalc.maths;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.teinproductions.tein.smartcalc.R;

public class RSAFragmentPrimeNumbers extends Fragment {

    EditText editText1, editText2;

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
                    if (editText1.length() == 0 || editText2.length() == 0) {
                        listener.disableNextButton();
                    } else {
                        listener.enableNextButton();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    public void onClickNext() {
        Toast.makeText(getActivity(), "clicked next from fragment", Toast.LENGTH_SHORT).show();
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


    private DisableButtons listener;

    public interface DisableButtons {
        public void disableNextButton();

        public void enableNextButton();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (DisableButtons) activity;
    }

}