package com.teinproductions.tein.integerfactorization;

import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public abstract class EditTextActivity extends ActionBarActivity {

    protected EditText editText1, editText2;
    protected Spinner spinner1, spinner2, resultSpinner;
    protected TextView resultTextView, resultDeclaration;
    protected ProgressBar progressBar;
    protected Button button;

    protected Integer animDuration;
    protected Boolean saveResultTextViewText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_edit_texts);

        editText1 = (EditText) findViewById(R.id.edit_text1);                           // Needs Hint and InputType
        editText2 = (EditText) findViewById(R.id.edit_text2);                           // Needs Hint and InputType
        spinner1 = (Spinner) findViewById(R.id.spinner1);                               // Needs Adapter
        spinner2 = (Spinner) findViewById(R.id.spinner2);                               // Needs Adapter
        resultSpinner = (Spinner) findViewById(R.id.result_spinner);                    // Needs Adapter
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        resultDeclaration = (TextView) findViewById(R.id.result_declaration_text_view); // Needs text
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        button = (Button) findViewById(R.id.calculate_button);                          // Standard text is "Calculate"

        animDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        doYourStuff();

    }

    public void clickButtonWhenFilledEditText(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickButton(null);
                    return true;
                } return false;
            }
        });
    }

    protected abstract void doYourStuff();

    public void fadeOut(View view, AnimatorListenerAdapter listener) {
        view.animate()
                .alpha(0f)
                .setDuration(animDuration)
                .setListener(listener);
    }

    public void fadeIn(View view, AnimatorListenerAdapter listener) {
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        view.animate()
                .alpha(1f)
                .setDuration(animDuration)
                .setListener(listener);
    }


    public void onClickCalculate(View view) {
        onClickButton(view);
    }

    protected abstract void onClickButton(View view);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (saveResultTextViewText) {
            outState.putString("RESULT", resultTextView.getText().toString());
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            resultTextView.setText(savedInstanceState.getString("RESULT"));
        } catch (NullPointerException ignored) {}
    }
}
