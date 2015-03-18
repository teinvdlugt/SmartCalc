package com.teinproductions.tein.smartcalc.maths;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

public class RSAFragmentKeysDone extends Fragment {

    public static final String N = "n", E = "e", D = "d";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_rsa_keys_done, container, false);

        ((TextView) layout.findViewById(R.id.public_key_n)).setText("n = " + getArguments().getString(N));
        ((TextView) layout.findViewById(R.id.public_key_e)).setText("e = " + getArguments().getString(E));
        ((TextView) layout.findViewById(R.id.private_key_n)).setText("n = " + getArguments().getString(N));
        ((TextView) layout.findViewById(R.id.private_key_d)).setText("d = " + getArguments().getString(D));

        return layout;
    }

    public static RSAFragmentKeysDone newInstance(Long n, Long e, Long d) {
        RSAFragmentKeysDone fragment = new RSAFragmentKeysDone();
        Bundle args = new Bundle();
        args.putString(N, n.toString());
        args.putString(E, e.toString());
        args.putString(D, d.toString());
        fragment.setArguments(args);

        return fragment;
    }
}
