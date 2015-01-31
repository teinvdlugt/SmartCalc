package com.teinproductions.tein.integerfactorization.conversion.numeralsystem;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.teinproductions.tein.integerfactorization.R;

public class NumeralSystemEditText extends EditText {

    private final NumeralSystem system;
    private final NumeralSystemConvertActivity activity;
    private boolean indirectTextChange;

    public NumeralSystemEditText(NumeralSystemConvertActivity activity, NumeralSystem system) {
        super(activity);

        this.system = system;
        this.activity = activity;

        this.setHint(system.getName(activity));

        setTextWatcher();
        setProperInputType();
        setEditIcon();
    }

    private void setTextWatcher() {
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!indirectTextChange) {
                    correctCases();
                    String text = NumeralSystemEditText.this.getText().toString();
                    if (text.equals("")) {
                        activity.clearAllEditTexts();
                    } else if (system.isValidNumber(text)) {
                        activity.convert(NumeralSystemEditText.this.convertToDec());

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
        } else if (onlyContainsUpperCaseLetters(system.getChars())) {
            this.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS |
                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        } else {
            this.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        }
    }

    private void setEditIcon() {
        this.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_edit_white_24dp), null);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (NumeralSystemEditText.this.getRight()
                            - NumeralSystemEditText.this.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        activity.edit(system);
                        return true;
                    }
                }
                return false;
            }
        });
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

    public void setSafeText(String text) {
        indirectTextChange = true;
        super.setText(text);
        indirectTextChange = false;
    }

    private void correctCases() {
        setSafeText(system.correctCases(getText().toString()));
        setSelection(length());
    }

    public static boolean onlyContainsNumbers(char[] chars) {
        for (char character : chars) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }

    public static boolean onlyContainsUpperCaseLetters(char[] chars) {
        for (char character : chars) {
            if (Character.isLowerCase(character)) {
                return false;
            }
        }
        return true;
    }

    public static NumeralSystemEditText[] getArrayFromSystems(
            NumeralSystemConvertActivity activity,
            NumeralSystem[] array) {
        NumeralSystemEditText[] es = new NumeralSystemEditText[array.length];
        for (int i = 0; i < es.length; i++) {
            es[i] = new NumeralSystemEditText(activity, array[i]);
        }

        return es;
    }
}
