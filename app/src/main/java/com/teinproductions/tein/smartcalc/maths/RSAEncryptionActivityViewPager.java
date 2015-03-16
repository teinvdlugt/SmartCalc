package com.teinproductions.tein.smartcalc.maths;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.teinproductions.tein.smartcalc.R;

public class RSAEncryptionActivityViewPager extends ActionBarActivity
        implements RSAFragment1.OnClickListener, RSAFragmentPrimeNumbers.Listener, RSAFragmentE.Listener {

    Button next, previous;

    long p, q, n, totient, e, d;

    RSAFragment1 fragment1;
    RSAFragmentPrimeNumbers fragmentPrimeNumbers;
    RSAFragmentE fragmentE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa_viewpager);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        next = (Button) findViewById(R.id.next_button);
        previous = (Button) findViewById(R.id.previous_button);

        if (fragment1 == null) fragment1 = new RSAFragment1();

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment1)
                    .commit();
            disablePreviousButton();
            disableNextButton();
        }
    }

    private void slide(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                .replace(R.id.fragment_container, newFragment)
                .commit();
    }

    private void slideBack(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit, R.anim.enter, R.anim.exit)
                .replace(R.id.fragment_container, newFragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onClickCreateKeys() {
        if (fragmentPrimeNumbers == null) {
            fragmentPrimeNumbers = RSAFragmentPrimeNumbers.newInstance();
        }

        slide(fragmentPrimeNumbers);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentPrimeNumbers.cancelTasks();
                slideBack(fragment1);
                disableNextButton();
                disablePreviousButton();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentPrimeNumbers.onClickNext();
            }
        });
    }

    @Override
    public void calculatedPrimeNumbers(long p, long q) {
        this.p = p;
        this.q = q;
        this.n = p*q;
        this.totient = (p - 1) * (q - 1);

        fragmentE = RSAFragmentE.newInstance(totient);
        slide(fragmentE);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentE.cancelTasks();
                slideBack(fragmentPrimeNumbers);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentPrimeNumbers.onClickNext();
                    }
                });
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        slideBack(fragment1);
                        fragmentPrimeNumbers.cancelTasks();
                        slideBack(fragment1);
                        disableNextButton();
                        disablePreviousButton();
                    }
                });
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long[] eAndD = fragmentE.onClickNext();
                e = eAndD[0];
                d = eAndD[1];

                Toast.makeText(RSAEncryptionActivityViewPager.this, e + " and " + d, Toast.LENGTH_SHORT).show();
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
    public void enableNextButton() {
        next.setEnabled(true);
    }

    @Override
    public void enablePreviousButton() {
        previous.setEnabled(true);
    }

    @Override
    public void disableNextButton() {
        next.setEnabled(false);
    }

    public void disablePreviousButton() {
        previous.setEnabled(false);
    }
}
