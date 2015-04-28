package com.teinproductions.tein.smartcalc.chemistry.molu;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.IOHandler;
import com.teinproductions.tein.smartcalc.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ParticleEditActivity extends AppCompatActivity {

    public static final String PARTICLE_ARRAY = "com.teinproductions.PARTICLE_ARRAY";
    public static final String PARTICLE_POSITION = "com.teinproductions.PARTICLE_POSITION";

    private EditText nameET, abbrET, massET, densityET;
    private CustomParticle[] customParticles;
    private int position;
    private CustomParticle customParticle;

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

        customParticles = (CustomParticle[]) getIntent().getExtras().getSerializable(PARTICLE_ARRAY);
        position = getIntent().getExtras().getInt(PARTICLE_POSITION);
        if (position == customParticles.length) {
            customParticles = extendParticles(customParticles);
        }
        customParticle = customParticles[position];

        setText();

        densityET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onClickSave(v);
                return true;
            }
        });
    }

    private CustomParticle[] extendParticles(CustomParticle[] customParticles) {
        CustomParticle[] particlesExtended = new CustomParticle[customParticles.length + 1];
        System.arraycopy(customParticles, 0, particlesExtended, 0, customParticles.length);
        particlesExtended[customParticles.length] = new CustomParticle(null, null, null, null);
        return particlesExtended;
    }

    private void setText() {
        if (customParticle.getName() != null) {
            nameET.setText(customParticle.getName());
        }
        if (customParticle.getAbbreviation() != null) {
            abbrET.setText(customParticle.getAbbreviation());
        }
        if (customParticle.getMass() != null) {
            massET.setText(new DecimalFormat().format(customParticle.getMass()));
        }
        if (customParticle.getDensity() != null) {
            densityET.setText(new DecimalFormat().format(customParticle.getDensity()));
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
        onClickSave(null);
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
            } else {
                deleteParticle();
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

        customParticle.setName(name);
        customParticle.setAbbreviation(abbr);
        customParticle.setMass(mass);
        customParticle.setDensity(density);

        // Save and finish activity
        saveParticlesAndFinishActivity();
    }

    private void saveParticlesAndFinishActivity() {
        IOHandler.save(this, customParticles);

        // Finish the activity
        setResult(RESULT_OK);
        finish();
    }

    public void deleteParticle() {
        // Convert particles to ArrayList
        ArrayList<CustomParticle> customParticleArrayList = new ArrayList<>();
        Collections.addAll(customParticleArrayList, customParticles);

        customParticleArrayList.remove(position);
        // Convert back to Array
        customParticles = new CustomParticle[customParticleArrayList.size()];
        for (int i = 0; i < customParticleArrayList.size(); i++) {
            customParticles[i] = customParticleArrayList.get(i);
        }

        saveParticlesAndFinishActivity();
    }
}