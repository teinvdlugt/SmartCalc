package com.teinproductions.tein.smartcalc.maths;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.teinproductions.tein.smartcalc.R;

public class RSAEncryptionActivityViewPager extends ActionBarActivity implements RSAFragment1.OnClickListener {

    Button next, previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_viewpager);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        next = (Button) findViewById(R.id.next_button);
        previous = (Button) findViewById(R.id.previous_button);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new RSAFragment1())
                    .commit();
            previous.setClickable(false);
            previous.setTextColor(getResources().getColor(R.color.button_bar_button_inactive));
        }
    }

    @Override
    public void onClickCreateKeys() {

    }

    @Override
    public void onClickEncrypt() {

    }

    @Override
    public void onClickDecrypt() {

    }
}
