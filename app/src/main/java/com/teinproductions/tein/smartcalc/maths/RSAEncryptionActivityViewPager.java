package com.teinproductions.tein.smartcalc.maths;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.teinproductions.tein.smartcalc.R;

public class RSAEncryptionActivityViewPager extends AppCompatActivity
        implements RSAFragment1.OnClickListener, RSAFragmentPrimeNumbers.Listener, RSAFragmentE.Listener {

    Button next, previous;

    long p, q, n, totient, e, d;

    RSAFragment1 fragment1;
    RSAFragmentPrimeNumbers fragmentPrimeNumbers;
    RSAFragmentE fragmentE;
    RSAFragmentKeysDone fragmentKeysDone;
    RSAFragmentEncrypt fragmentEncrypt;
    RSAFragmentDecrypt fragmentDecrypt;
    RSAFragmentCrack1 fragmentCrack;

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
        if (previous.isEnabled()) {
            previous.callOnClick();
        } else {
            finish();
        }
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
        this.n = p * q;
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
                onCalculatedEAndD();
            }
        });
    }

    private void onCalculatedEAndD() {
        fragmentKeysDone = RSAFragmentKeysDone.newInstance(n, e, d);
        slide(fragmentKeysDone);

        previous.setEnabled(false);
        next.setEnabled(true);
        next.setText(R.string.done);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideBack(new RSAFragment1());
                next.setEnabled(false);
                next.setText(R.string.next);
            }
        });
    }

    @Override
    public void onClickEncrypt() {
        if (fragmentEncrypt == null) fragmentEncrypt = new RSAFragmentEncrypt();

        slide(fragmentEncrypt);
        next.setText(R.string.encrypt);
        next.setEnabled(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentEncrypt.onClickEncrypt();
            }
        });
        previous.setEnabled(true);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentEncrypt.cancelTasks();
                slideBack(new RSAFragment1());
                previous.setEnabled(false);
                next.setEnabled(false);
                next.setText(R.string.next);
            }
        });
    }

    @Override
    public void onClickDecrypt() {
        if (fragmentDecrypt == null) fragmentDecrypt = new RSAFragmentDecrypt();

        slide(fragmentDecrypt);
        next.setText(R.string.decrypt);
        next.setEnabled(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentDecrypt.onClickDecrypt();
            }
        });
        previous.setEnabled(true);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentDecrypt.cancelTasks();
                slideBack(new RSAFragment1());
                previous.setEnabled(false);
                next.setEnabled(false);
                next.setText(R.string.next);
            }
        });
    }

    @Override
    public void onClickCrack() {
        if (fragmentCrack == null) fragmentCrack = new RSAFragmentCrack1();

        slide(fragmentCrack);
        next.setText(getString(R.string.crack) + "!");
        next.setEnabled(true);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long[] input = fragmentCrack.onClickCrack();
                /* input[0] must be the cipher,
                *  input[1] must be n,
                *  input[2] must be e
                */
                long cipher = input[0], n = input[1], e = input[2];

                final RSAFragmentCrack2 fragmentCrack2 = RSAFragmentCrack2.newInstance(cipher, n, e);
                slide(fragmentCrack2);
                previous.setEnabled(true);
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fragmentCrack2.cancelTasks();
                        slideBack(new RSAFragment1());
                        next.setText(R.string.next);
                        next.setEnabled(false);
                        previous.setEnabled(false);
                    }
                });
                next.setEnabled(false);
            }
        });
        previous.setEnabled(true);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideBack(new RSAFragment1());
                previous.setEnabled(false);
                next.setEnabled(false);
                next.setText(R.string.next);
            }
        });
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
