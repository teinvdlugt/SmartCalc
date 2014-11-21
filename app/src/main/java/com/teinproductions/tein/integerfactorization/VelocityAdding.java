package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class VelocityAdding extends ActionBarActivity {

    private TextView resultTextView;
    private EditText number1, number2;
    private Spinner spinner1, spinner2, spinnerResult;
    Integer animationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_velocity_adding);

        resultTextView = (TextView) findViewById(R.id.result_text_view);
        number1 = (EditText) findViewById(R.id.number_1);
        number2 = (EditText) findViewById(R.id.number_2);
        number2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickCalculate(null);
                    return true;
                }
                return false;
            }
        });
        spinner1 = (Spinner) findViewById(R.id.spinner_number1);
        spinner2 = (Spinner) findViewById(R.id.spinner_number2);
        spinnerResult = (Spinner) findViewById(R.id.result_spinner);
        spinnerSetup();

        animationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public void onClickCalculate(View view) {

        try {
            Double input1 = Double.parseDouble(number1.getText().toString());
            Double input2 = Double.parseDouble(number2.getText().toString());

            Units.Velocity velocity1 = Units.Velocity.values()[spinner1.getSelectedItemPosition()];
            Units.Velocity velocity2 = Units.Velocity.values()[spinner2.getSelectedItemPosition()];
            Units.Velocity resultVelocity = Units.Velocity.values()[spinnerResult.getSelectedItemPosition()];

            Double num1 = Units.Velocity.convert(velocity1, Units.Velocity.MPS, input1);
            Double num2 = Units.Velocity.convert(velocity2, Units.Velocity.MPS, input2);

            Double c = 299792485.0;
            final Double result = (num1 + num2) / (1 + (num1 * num2) / (c * c));
            final Double output = Units.Velocity.convert(Units.Velocity.MPS, resultVelocity, result);

            resultTextView.animate()
                    .alpha(0f)
                    .setDuration(animationDuration)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            resultTextView.setText(new DecimalFormat("0.################").format(output));
                            resultTextView.animate()
                                    .alpha(1f)
                                    .setDuration(animationDuration)
                                    .setListener(null);
                        }
                    });

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void spinnerSetup() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickCalculate(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Velocity.getWords(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerResult.setAdapter(adapter);

        spinner1.setOnItemSelectedListener(itemSelectedListener);
        spinner2.setOnItemSelectedListener(itemSelectedListener);
        spinnerResult.setOnItemSelectedListener(itemSelectedListener);

        spinner1.setSelection(0);
        spinner2.setSelection(0);
        spinnerResult.setSelection(0);

    }

}
