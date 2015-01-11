package com.teinproductions.tein.integerfactorization;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

public class NumeralSystemConvertActivity extends ActionBarActivity {

    EditText decimalET, binaryET, hexET;

    boolean indirectTextChange;

    public static final char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numeral_system_convert);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        decimalET = (EditText) findViewById(R.id.decimal_number);
        binaryET = (EditText) findViewById(R.id.binary_number);
        hexET = (EditText) findViewById(R.id.hexadecimal_number);

        decimalET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!indirectTextChange) {

                    indirectTextChange = true;

                    if (decimalET.getText().toString().equals("")) {
                        clearAllEditTexts();
                    } else if (hasValidIntegerInput(decimalET)) {
                        fromDecimal(decimalET.getText().toString());
                    } else {
                        // I don't expect this to happen since the inputType for
                        // decimalET is InputType.TYPE_NUMBER_FLAG_SIGNED
                        removeLastCharacterFromEditText(decimalET);
                        decimalET.setSelection(decimalET.getText().length());
                    }

                    indirectTextChange = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binaryET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!indirectTextChange) {

                    indirectTextChange = true;

                    if (binaryET.getText().toString().equals("")) {
                        clearAllEditTexts();
                    } else if (hasValidBinaryInput(binaryET)) {
                        fromBinary(binaryET.getText().toString());
                    } else {
                        removeLastCharacterFromEditText(binaryET);
                        binaryET.setSelection(binaryET.getText().length());
                    }

                    indirectTextChange = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        hexET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!indirectTextChange) {

                    indirectTextChange = true;

                    if (hexET.getText().toString().equals("")) {
                        clearAllEditTexts();
                    } else if (hasValidHexadecimalInput(hexET)) {
                        hexET.setText(hexET.getText().toString().toUpperCase());
                        hexET.setSelection(hexET.getText().length());
                        fromHex(hexET.getText().toString());
                    } else {
                        removeLastCharacterFromEditText(hexET);
                        hexET.setSelection(hexET.getText().length());
                    }

                    indirectTextChange = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void fromDecimal(String decimal) {
        binaryET.setText(Integer.toBinaryString(Integer.parseInt(decimal)));
        hexET.setText(Integer.toHexString(Integer.parseInt(decimal)).toUpperCase());
    }

    private void fromBinary(String binary) {
        String dec = binToDec(binary);
        decimalET.setText(dec);
        hexET.setText(Integer.toHexString(Integer.parseInt(dec)).toUpperCase());
    }

    private void fromHex(String hex) {
        String dec = hexToDec(hex);
        decimalET.setText(dec);
        binaryET.setText(Integer.toBinaryString(Integer.parseInt(dec)));
    }

    private void clearAllEditTexts() {
        decimalET.setText("");
        binaryET.setText("");
        hexET.setText("");
    }

    public static String binToDec(String bin) {
        int result = 0;

        for (int i = bin.length() - 1; i >= 0; i--) { // TODO try --i
            char character = bin.charAt(i);
            if (character == '1') {
                result += Math.pow(2, bin.length() - 1 - i);
            }
        }

        return Integer.toString(result);
    }

    public static String hexToDec(String hex) {
        int result = 0;

        for (int i = hex.length() - 1; i >= 0; i--) {
            char character = hex.charAt(i);
            int index = indexOfChar(hexChars, character);

            if (index == -1) {
                throw new NumberFormatException("invalid hexadecimal string");
            }

            result += index * Math.pow(16, hex.length() - 1 - i);
        }
        return Integer.toString(result);
    }

    public static int indexOfChar(char[] chars, char character) {
        // I convert both parameters to strings, so that I can
        // easily ignore the case of the character with String.equalsIgnoreCase()

        String[] strings = new String[chars.length];
        for (int i = 0; i < chars.length; i++) {
            strings[i] = String.valueOf(chars[i]);
        }

        String string = String.valueOf(character);

        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equalsIgnoreCase(string)) return i;
        }

        return -1;
    }

    public static boolean hasValidIntegerInput(EditText editText) {
        try {
            Integer.parseInt(editText.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean hasValidBinaryInput(EditText editText) {
        String string = editText.getText().toString();
        for (int i = 0; i < string.length(); i++) {
            if (!((string.charAt(i) == '0') || string.charAt(i) == '1')) return false;
        }
        return true;
    }

    public static boolean hasValidHexadecimalInput(EditText editText) {
        String string = editText.getText().toString();
        for (int i = 0; i < string.length(); i++) {
            if (indexOfChar(hexChars, string.charAt(i)) == -1) return false;
        }
        return true;
    }

    public static void removeLastCharacterFromEditText(EditText editText) {
        if (editText.getText().toString().length() == 1 || editText.getText().toString().length() == 0) {
            editText.setText("");
            return;
        }

        editText.setText(
                String.valueOf(
                        editText.getText().toString().toCharArray(), 0, editText.getText().toString().length() - 1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return false;
    }
}
