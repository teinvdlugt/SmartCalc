package com.teinproductions.tein.integerfactorization;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;
import java.text.DecimalFormat;

public class ParticleEditActivity extends ActionBarActivity {

    public static final String PARTICLE_TO_EDIT = "com.teinproductions.PARTICLE_TO_EDIT";

    private EditText nameET, abbrET, massET, densityET;
    private Particle particle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particle_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameET = (EditText) findViewById(R.id.name_edit_text);
        abbrET = (EditText) findViewById(R.id.abbr_edit_text);
        massET = (EditText) findViewById(R.id.mass_edit_text);
        densityET = (EditText) findViewById(R.id.density_edit_text);

        particle = (Particle) getIntent().getExtras().getSerializable(PARTICLE_TO_EDIT);

        setText();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    public void onClickSave(View view) {
        // TODO
    }
}
