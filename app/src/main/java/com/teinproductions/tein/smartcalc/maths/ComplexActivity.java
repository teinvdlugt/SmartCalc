package com.teinproductions.tein.smartcalc.maths;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.IOHandler;
import com.teinproductions.tein.smartcalc.R;

public class ComplexActivity extends ActionBarActivity {

    EditText number1a, number1b, number2a, number2b, powerET;
    Spinner operatorSpinner;
    TextView resultTextView;
    RelativeLayout number2Container;

    Integer animDuration;

    public static String[] operators = {"+", "-", "×", "÷", "^"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        animDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        number1a = (EditText) findViewById(R.id.number1_a);
        number1b = (EditText) findViewById(R.id.number1_b);
        number2a = (EditText) findViewById(R.id.number2_a);
        number2b = (EditText) findViewById(R.id.number2_b);
        powerET = (EditText) findViewById(R.id.the_power);
        operatorSpinner = (Spinner) findViewById(R.id.operator_spinner);
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        number2Container = (RelativeLayout) findViewById(R.id.number2_container);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.my_spinner_textview,
                operators);
        adapter.setDropDownViewResource(R.layout.my_spinner_textview);
        operatorSpinner.setAdapter(adapter);
        operatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    if (number2Container.getVisibility() == View.VISIBLE) {
                        number2Container.animate()
                                .alpha(0f)
                                .setDuration(animDuration)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        number2Container.setVisibility(View.GONE);
                                        powerET.setAlpha(0f);
                                        powerET.setVisibility(View.VISIBLE);
                                        powerET.animate()
                                                .alpha(1f)
                                                .setDuration(animDuration)
                                                .setListener(null);
                                    }
                                });
                    }
                } else {
                    if (number2Container.getVisibility() == View.GONE) {
                        powerET.animate()
                                .alpha(0f)
                                .setDuration(animDuration)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        powerET.setVisibility(View.GONE);
                                        number2Container.setAlpha(0f);
                                        number2Container.setVisibility(View.VISIBLE);
                                        number2Container.animate()
                                                .alpha(1f)
                                                .setDuration(animDuration)
                                                .setListener(null);
                                    }
                                });
                    }

                    onClickCalculate(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        number2b.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickCalculate(number2b);
                    return true;
                }
                return false;
            }
        });

    }

    public void onClickCalculate(View view) {
        double number1aInput;
        double number1bInput;
        try {
            number1aInput = Double.parseDouble(number1a.getText().toString());
            number1bInput = Double.parseDouble(number1b.getText().toString());
        } catch (NumberFormatException e) {
            if (view != null) {
                CustomDialog.invalidNumber(getSupportFragmentManager());
            }
            return;
        }

        Complex number1 = new Complex(number1aInput, number1bInput);
        Complex result;

        if (operatorSpinner.getSelectedItemPosition() == 4) {
            int power;
            try {
                power = Integer.parseInt(powerET.getText().toString());
            } catch (NumberFormatException e) {
                if (view != null) {
                    CustomDialog.invalidNumber(getSupportFragmentManager());
                }
                return;
            }

            result = number1.pow(power);

        } else {
            double number2aInput;
            double number2bInput;

            try {
                number2aInput = Double.parseDouble(number2a.getText().toString());
                number2bInput = Double.parseDouble(number2b.getText().toString());
            } catch (NumberFormatException e) {
                if (view != null) {
                    CustomDialog.invalidNumber(getSupportFragmentManager());
                }
                return;
            }

            Complex number2 = new Complex(number2aInput, number2bInput);

            switch (operatorSpinner.getSelectedItemPosition()) {
                case 0:
                    result = Complex.add(number1, number2);
                    break;
                case 1:
                    result = Complex.subtract(number1, number2);
                    break;
                case 2:
                    result = Complex.multiply(number1, number2);
                    break;
                case 3:
                    result = Complex.divide(number1, number2);
                    break;
                default:
                    // Otherwise "result might not have been initialized"
                    result = new Complex(0, 0);
            }

        }

        resultTextView.setText(result.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.info_icon:
                IOHandler.openWebPage(this, "http://en.wikipedia.org/wiki/Complex_number");
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("RESULT", resultTextView.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            resultTextView.setText(savedInstanceState.getString("RESULT"));
        } catch (NullPointerException ignored) {
        }
    }
}
