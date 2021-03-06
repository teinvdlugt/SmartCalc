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

public class RSAFragmentEncrypt extends Fragment {

    private EditText nET, eET, messageET;

    private Encrypter encrypter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa_encrypt, container, false);

        nET = (EditText) layout.findViewById(R.id.n_editText);
        eET = (EditText) layout.findViewById(R.id.e_editText);
        messageET = (EditText) layout.findViewById(R.id.message_editText);

        return layout;
    }

    public void onClickEncrypt() {
        if (nET.length() == 0 || eET.length() == 0 || messageET.length() == 0) {
            CustomDialog.newInstance("", getString(R.string.fill_all_fields))
                    .show(getActivity().getSupportFragmentManager(), "fill_all_fields_dialog");
        } else if (Long.parseLong(messageET.getText().toString()) >= Long.parseLong(nET.getText().toString())) {
            CustomDialog.newInstance(R.string.message_gt_n_title, R.string.message_gt_n_message)
                    .show(getActivity().getSupportFragmentManager(), "message_gt_n_dialog");
        } else {
            encrypter = new Encrypter();
            encrypter.execute();
        }
    }

    public void cancelTasks() {
        if (encrypter != null) {
            encrypter.cancel(true);
        }
    }


    class Encrypter extends AsyncTask<Void, Void, BigInteger> {

        @Override
        protected BigInteger doInBackground(Void... voids) {
            BigInteger message = new BigInteger(messageET.getText().toString());
            int e = Integer.parseInt(eET.getText().toString());
            BigInteger n = new BigInteger(nET.getText().toString());
            if (isCancelled()) return null;

            return message.pow(e).mod(n);
        }

        @Override
        protected void onPostExecute(BigInteger cipher) {
            if (cipher != null) {
                CustomDialog.newInstance("", getString(R.string.the_cipher_is) + cipher.toString())
                        .show(getActivity().getSupportFragmentManager(), "the_cipher_is_dialog");
            }
        }
    }
}