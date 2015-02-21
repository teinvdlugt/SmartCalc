package com.teinproductions.tein.smartcalc.chemistry.molu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.R;

import java.text.DecimalFormat;

public class ParticleEditActivity extends ActionBarActivity
        implements DiscardChangesConfirmDialog.SaveOrDiscard {

    public static final String PARTICLE_ID = "com.teinproductions.PARTICLE_ID";
    public static final String CHANGED = "com.teinproductions.CHANGED";

    private EditText nameET, abbrET, massET, densityET;

    private DatabaseManager dbManager;
    private int id;
    private CustomParticle particle;

    private boolean typedInAnEditText = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle_edit);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameET = (EditText) findViewById(R.id.name_edit_text);
        abbrET = (EditText) findViewById(R.id.abbr_edit_text);
        massET = (EditText) findViewById(R.id.mass_edit_text);
        densityET = (EditText) findViewById(R.id.density_edit_text);

        dbManager = new DatabaseManager(this);
        id = getIntent().getExtras().getInt(PARTICLE_ID);
        particle = dbManager.getParticleWithId(id);

        if (particle != null) {
            setText();
        }

        densityET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onClickSave(v);
                return true;
            }
        });

        setTextWatchers();
    }

    private void setText() {
        if (particle.getName() != null) {
            nameET.setText(particle.getName());
        }
        if (particle.getAbbreviation() != null) {
            abbrET.setText(particle.getAbbreviation());
        }
        if (particle.getMass() != null) {
            massET.setText(new DecimalFormat().format(particle.getMass()));
        }
        if (particle.getDensity() != null) {
            densityET.setText(new DecimalFormat().format(particle.getDensity()));
        }
    }

    private void setTextWatchers() {
        for (final EditText e : new EditText[]{nameET, abbrET, massET, densityET}) {
            e.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    typedInAnEditText = true;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.particle_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                deleteParticle();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (typedInAnEditText) {
            new DiscardChangesConfirmDialog().show(getSupportFragmentManager(), "discard or save changes");
        } else {
            finish(false);
        }
    }

    @Override
    public void saveOrDiscard(boolean save) {
        if (save) {
            onClickSave(densityET); // Just a view
        }

        finish(false);
    }

    public void onClickSave(View view) {
        // Get the values given by the user
        String name = nameET.getText().toString(),
                abbr = abbrET.getText().toString();
        if (name.equals("")) {
            if (view != null) {
                CustomDialog
                        .newInstance(R.string.no_name, R.string.no_name_dialog_message)
                        .show(getSupportFragmentManager(), "noNameForParticleDialog");
            }
            return;
        }

        Double mass = null, density = null;
        if (EditTextActivity.hasValidDecimalInput(massET)) {
            mass = Double.parseDouble(massET.getText().toString());
        }
        if (EditTextActivity.hasValidDecimalInput(densityET)) {
            density = Double.parseDouble(densityET.getText().toString());
        }

        particle = new CustomParticle(name, abbr, mass, density);

        // Save and finish activity
        saveParticle();
    }

    private void saveParticle() {
        if (id == -1) {
            dbManager.addParticle(particle);
        } else {
            dbManager.updateParticle(id, particle);
        }

        finish(true);
    }

    private void deleteParticle() {
        if (id != -1) {
            dbManager.deleteParticle(id);
        }

        finish(true);
    }

    private void finish(boolean changed) {
        Intent intent = new Intent();
        intent.putExtra(CHANGED, changed);
        setResult(RESULT_OK, intent);
        finish();
    }
}