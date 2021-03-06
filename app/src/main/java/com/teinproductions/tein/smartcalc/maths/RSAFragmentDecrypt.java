package com.teinproductions.tein.smartcalc.maths;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.R;

import java.math.BigInteger;

public class RSAFragmentDecrypt extends Fragment {

    private EditText nET, dET, cipherET;

    private Decrypter decrypter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa_decrypt, container, false);

        nET = (EditText) layout.findViewById(R.id.n_editText);
        dET = (EditText) layout.findViewById(R.id.d_editText);
        cipherET = (EditText) layout.findViewById(R.id.cipher_editText);

        return layout;
    }

    public void onClickDecrypt() {
        if (nET.length() == 0 || dET.length() == 0 || cipherET.length() == 0) {
            CustomDialog.newInstance("", getString(R.string.fill_all_fields))
                    .show(getActivity().getSupportFragmentManager(), "fill_all_fields_dialog");
        } else {
            decrypter = new Decrypter();
            decrypter.execute();
        }
    }

    public void cancelTasks() {
        if (decrypter != null) {
            decrypter.cancel(true);
        }
    }


    class Decrypter extends AsyncTask<Void, Void, BigInteger> {

        @Override
        protected BigInteger doInBackground(Void... voids) {
            BigInteger cipher = new BigInteger(cipherET.getText().toString());
            int d = Integer.parseInt(dET.getText().toString());
            BigInteger n = new BigInteger(nET.getText().toString());
            if (isCancelled()) return null;

            return cipher.pow(d).mod(n);
        }

        @Override
        protected void onPostExecute(BigInteger message) {
            if (message != null) {
                CustomDialog.newInstance("", getString(R.string.the_message_is) + message.toString())
                        .show(getActivity().getSupportFragmentManager(), "the_message_is_dialog");
            }
        }
    }
}