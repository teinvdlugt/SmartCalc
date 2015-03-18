package com.teinproductions.tein.smartcalc.maths;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.R;

public class RSAFragmentCrack1 extends Fragment {

    private EditText nET, eET, cipherET;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa_crack, container, false);

        nET = (EditText) layout.findViewById(R.id.n_editText);
        eET = (EditText) layout.findViewById(R.id.e_editText);
        cipherET = (EditText) layout.findViewById(R.id.cipher_editText);

        return layout;
    }

    public long[] onClickCrack() {
        if (nET.length() == 0 || eET.length() == 0 || cipherET.length() == 0) {
            CustomDialog.newInstance("", getString(R.string.fill_all_fields))
                    .show(getActivity().getSupportFragmentManager(), "fill_all_fields_dialog");
            return null;
        } else {
            return new long[]{RSAFragmentPrimeNumbers.longValue(cipherET),
                    RSAFragmentPrimeNumbers.longValue(nET),
                    RSAFragmentPrimeNumbers.longValue(eET)};
        }
    }
}
