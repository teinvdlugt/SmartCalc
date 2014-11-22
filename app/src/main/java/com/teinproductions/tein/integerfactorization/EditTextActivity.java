package com.teinproductions.tein.integerfactorization;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_with_edit_texts);

        editText1 = (EditText) findViewById(R.id.edit_text1);                           // Needs Hint
        editText2 = (EditText) findViewById(R.id.edit_text2);                           // Needs Hint
        spinner1 = (Spinner) findViewById(R.id.spinner1);                               // Needs Adapter
        spinner2 = (Spinner) findViewById(R.id.spinner2);                               // Needs Adapter
        resultSpinner = (Spinner) findViewById(R.id.result_spinner);                    // Needs Adapter
        resultTextView = (TextView) findViewById(R.id.result_text_view);
        resultDeclaration = (TextView) findViewById(R.id.result_declaration_text_view); // Needs text
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        button = (Button) findViewById(R.id.calculate_button);                          // Standard text is "Calculate"

        doYourThing();

    }

    public abstract void doYourThing();

}
