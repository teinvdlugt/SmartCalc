package com.teinproductions.tein.smartcalc.maths;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.teinproductions.tein.smartcalc.R;

public class RSAEncryptionActivityViewPager extends ActionBarActivity
        implements RSAFragment1.OnClickListener, RSAFragmentPrimeNumbers.DisableButtons {

    Button next, previous;

    long inputP = -1, inputQ = -1;

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
            next.setClickable(false);
            next.setTextColor(getResources().getColor(R.color.button_bar_button_inactive));
        }
    }

    private void slide(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClickCreateKeys() {
        final RSAFragmentPrimeNumbers fragment = RSAFragmentPrimeNumbers.newInstance(inputP, inputQ);
        slide(fragment);
        previous.setClickable(true);
        previous.setTextColor(getResources().getColor(R.color.button_bar_button_active));

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long[] inputs = fragment.onClickPrevious();
                inputP = inputs[0];
                inputQ = inputs[1];
                getSupportFragmentManager().popBackStack();
                disableNextButton();
                disablePreviousButton();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.onClickNext();
            }
        });
    }

    @Override
    public void onClickEncrypt() {

    }

    @Override
    public void onClickDecrypt() {

    }

    @Override
    public void disableNextButton() {
        next.setClickable(false);
        next.setTextColor(getResources().getColor(R.color.button_bar_button_inactive));
    }

    public void disablePreviousButton() {
        previous.setClickable(false);
        previous.setTextColor(getResources().getColor(R.color.button_bar_button_inactive));
    }

    @Override
    public void enableNextButton() {
        next.setClickable(true);
        next.setTextColor(getResources().getColor(R.color.button_bar_button_active));
    }
}
