package com.teinproductions.tein.smartcalc;

import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

// This class is also a container for all sorts of static methods that
// have to do with EditTexts.
public abstract class EditTextActivity extends ActionBarActivity {

    protected LinearLayout rootLayout;
    protected EditText editText1, editText2;
    protected Spinner spinner1, spinner2, resultSpinner;
    protected TextView resultTextView, resultDeclaration, resultExplanation;
    protected ProgressBar progressBar;
    protected Button button;

    protected Integer animDuration;
    protected Boolean saveResultTextViewText = true;

    /* When not null, an info icon will appear in the action
     * bar with a link to the web page with the provided url
     */
    protected String infoWebPageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_edit_texts);

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        editText1 = (EditText) findViewById(R.id.edit_text1);                           // Needs Hint and InputType
        editText2 = (EditText) findViewById(R.id.edit_text2);                           // Needs Hint and InputType
        spinner1 = (Spinner) findViewById(R.id.spinner1);                               // Needs Adapter
        spinner2 = (Spinner) findViewById(R.id.spinner2);                               // Needs Adapter
        resultSpinner = (Spinner) findViewById(R.id.result_spinner);                    // Needs Adapter
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        resultDeclaration = (TextView) findViewById(R.id.result_declaration_text_view); // Needs text
        resultExplanation = (TextView) findViewById(R.id.result_explanation);           // Text depends on result, hidden by default
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        button = (Button) findViewById(R.id.calculate_button);                          // Standard text is "Calculate"

        animDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        doYourStuff(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (infoWebPageUri != null) {
            getMenuInflater().inflate(R.menu.info_icon, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.info_icon:
                IOHandler.openWebPage(this, infoWebPageUri);
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickButtonWhenFilledEditText(final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    onClickButton(editText);
                    return true;
                }
                return false;
            }
        });
    }

    public void setTextWatcher(final EditText e) {
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onClickButton(e);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected abstract void doYourStuff(Bundle savedInstanceState);

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
            outState.putString("RESULT_EXPLANATION", resultExplanation.getText().toString());
        }

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        try {
            resultTextView.setText(savedInstanceState.getString("RESULT"));
            resultExplanation.setText(savedInstanceState.getString("RESULT_EXPLANATION"));
        } catch (NullPointerException ignored) {
        }
    }


    // Static methods not specifically used by subclasses of EditTextActivity

    public static boolean hasValidDecimalInput(EditText editText) {
        try {
            Double test = Double.parseDouble(editText.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean containsText(EditText e) {
        return e.getText().length() > 0;
    }

    public static boolean isEmpty(EditText e) {
        return e.getText().length() == 0;
    }
}
