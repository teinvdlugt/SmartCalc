package com.teinproductions.tein.integerfactorization;

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

public class ComplexActivity extends ActionBarActivity {

    EditText number1a, number1b, number2a, number2b;
    Spinner operatorSpinner;
    TextView resultTextView;

    public static String[] operators = {"+", "-", "×", "÷"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex);

        number1a = (EditText) findViewById(R.id.number1_a);
        number1b = (EditText) findViewById(R.id.number1_b);
        number2a = (EditText) findViewById(R.id.number2_a);
        number2b = (EditText) findViewById(R.id.number2_b);
        operatorSpinner = (Spinner) findViewById(R.id.operator_spinner);
        resultTextView = (TextView) findViewById(R.id.result_text_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.my_spinner_textview,
                operators);
        adapter.setDropDownViewResource(R.layout.my_spinner_textview);
        operatorSpinner.setAdapter(adapter);
        operatorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onClickCalculate(null);
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
        double number2aInput;
        double number2bInput;
        try {
            number1aInput = Double.parseDouble(number1a.getText().toString());
            number1bInput = Double.parseDouble(number1b.getText().toString());
            number2aInput = Double.parseDouble(number2a.getText().toString());
            number2bInput = Double.parseDouble(number2b.getText().toString());
        } catch (NumberFormatException e) {
            if (view != null) {
                CustomDialog.invalidNumber(getFragmentManager());
            }
            return;
        }

        Complex number1 = new Complex(number1aInput, number1bInput);
        Complex number2 = new Complex(number2aInput, number2bInput);

        Complex result;

        switch (operatorSpinner.getSelectedItemPosition()) {
            case 0:
                result = Complex.add(number1, number2);
                break;
            case 1:
                result = Complex.substract(number1, number2);
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

        resultTextView.setText(result.toString());
    }
}
