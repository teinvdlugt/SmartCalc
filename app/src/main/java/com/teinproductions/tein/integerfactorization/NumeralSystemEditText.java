package com.teinproductions.tein.integerfactorization;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

public class NumeralSystemEditText extends EditText {

    private NumeralSystem system;
    private NumeralSystemConvertActivity activity;
    private boolean indirectTextChange;

    public NumeralSystemEditText(NumeralSystemConvertActivity activity, NumeralSystem system) {
        super(activity);

        this.system = system;
        this.activity = activity;

        this.setHint(system.getName());
        setTextWatcher();
        setProperInputType();
    }

    private void setTextWatcher() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!indirectTextChange) {
                    String text = NumeralSystemEditText.this.getText().toString();
                    if (text.equals("")) {
                        activity.clearAllEditTexts();
                    } else if (system.isValidNumber(text)) {
                        //int selection = NumeralSystemEditText.this.getSelectionEnd();

                        activity.convert(NumeralSystemEditText.this.convertToDec());

                        //NumeralSystemEditText.this.setSelection(selection);
                        NumeralSystemEditText.this.setSelection(NumeralSystemEditText.this.length());
                    } else {
                        NumeralSystemEditText.this.setSafeText(system.removeInvalidCharacters(text));
                        NumeralSystemEditText.this.setSelection(NumeralSystemEditText.this.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setProperInputType() {
        if (onlyContainsNumbers(system.getChars())) {
            this.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            this.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
    }

    public static boolean onlyContainsNumbers(char[] chars) {
        for (char character : chars) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }

    public int convertToDec() {
        return system.convertToDec(this.getText().toString());
    }

    public void convertFromDec(int dec) {
        String converted = system.convertFromDec(dec);
        this.setSafeText(converted);
    }

    public NumeralSystem getSystem() {
        return system;
    }

    public void setSystem(NumeralSystem system) {
        this.system = system;
    }

    public void setSafeText(String text) {
        indirectTextChange = true;
        super.setText(text);
        indirectTextChange = false;
    }
}
