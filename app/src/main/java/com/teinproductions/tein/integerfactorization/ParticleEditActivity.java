package com.teinproductions.tein.integerfactorization;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;

public class ParticleEditActivity extends ActionBarActivity {

    public static final String PARTICLE_ARRAY = "com.teinproductions.PARTICLE_ARRAY";
    public static final String PARTICLE_POSITION = "com.teinproductions.PARTICLE_POSITION";

    private EditText nameET, abbrET, massET, densityET;
    private Particle[] particles;
    private int position;
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

        particles = (Particle[]) getIntent().getExtras().getSerializable(PARTICLE_ARRAY);
        position = getIntent().getExtras().getInt(PARTICLE_POSITION);
        particle = particles[position];

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
        try {
            String name = nameET.getText().toString(), abbr = abbrET.getText().toString();
            Double mass = Double.parseDouble(massET.getText().toString()), density = Double.parseDouble(densityET.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }
}
