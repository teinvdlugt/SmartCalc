package com.teinproductions.tein.smartcalc.maths;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teinproductions.tein.smartcalc.R;

public class RSAFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa1, container, false);

        Button createButton = (Button) layout.findViewById(R.id.create_keys_button);
        Button encryptButton = (Button) layout.findViewById(R.id.encrypt_button);
        Button decryptButton = (Button) layout.findViewById(R.id.decrypt_button);
        Button crackButton = (Button) layout.findViewById(R.id.crack_button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickCreateKeys();
            }
        });
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickEncrypt();
            }
        });
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDecrypt();
            }
        });
        crackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickCrack();
            }
        });

        return layout;
    }


    public interface OnClickListener {
        public void onClickCreateKeys();
        public void onClickEncrypt();
        public void onClickDecrypt();
        public void onClickCrack();
    }

    OnClickListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        listener = (OnClickListener) activity;
    }
}
