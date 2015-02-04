package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.IOHandler;
import com.teinproductions.tein.smartcalc.R;

public class CustomParticlesActivity extends ActionBarActivity {

    public static final String FILE_NAME = "particles";
    public static final int PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE = 1;
    public static final String CALCULATE_WITH_THIS_PARTICLE = "com.teinproductions.tein.integerfactorization." +
            "CALCULATE_WITH_THIS_PARTICLE";

    private ListView listView;
    private CustomParticle[] customParticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);

        // Initialize particles and load the saved particles into the list view
        reloadParticles();
    }

    private void reloadParticles() {
        String jsonString = IOHandler.getFile(this, FILE_NAME);
        customParticles = IOHandler.getSavedParticles(jsonString);
        listView.setAdapter(new CustomParticlesListAdapter(this, customParticles));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_particle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                addParticle();
                return true;
            default:
                return false;
        }
    }

    private void addParticle() {
        Intent intent = new Intent(this, ParticleEditActivity.class);
        intent.putExtra(ParticleEditActivity.PARTICLE_ARRAY, customParticles);
        intent.putExtra(ParticleEditActivity.PARTICLE_POSITION, customParticles.length); // particles.length is one more than the last index in particles
        startActivityForResult(intent, PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            reloadParticles();
        }

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private static class CustomParticlesListAdapter extends ArrayAdapter<String> {

        CustomParticle[] customParticles;
        CustomParticlesActivity activity;

        public CustomParticlesListAdapter(CustomParticlesActivity activity, CustomParticle[] customParticles) {
            super(activity, R.layout.list_item_custom_particles);
            this.customParticles = customParticles;
            this.activity = activity;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            View theView = theInflater.inflate(R.layout.list_item_custom_particles, parent, false);

            TextView customParticleName = (TextView) theView.findViewById(R.id.particle_name_text_view);
            ImageView imgEdit = (ImageView) theView.findViewById(R.id.img_edit);
            ImageView imgGo = (ImageView) theView.findViewById(R.id.img_go);

            if (customParticles[position].getName() != null) {
                customParticleName.setText(customParticles[position].getName());
            }

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editParticle(position);
                }
            };

            imgEdit.setOnClickListener(listener);
            customParticleName.setOnClickListener(listener);

            imgGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calculateWithParticle(position);
                }
            });

            return theView;
        }

        @Override
        public int getCount() {
            return customParticles.length;
        }

        private void editParticle(int position) {
            Intent intent = new Intent(getContext(), ParticleEditActivity.class);
            intent.putExtra(ParticleEditActivity.PARTICLE_ARRAY, customParticles);
            intent.putExtra(ParticleEditActivity.PARTICLE_POSITION, position);
            activity.startActivityForResult(intent, PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE);
        }

        private void calculateWithParticle(int position) {
            Intent goingBack = new Intent();
            goingBack.putExtra(CALCULATE_WITH_THIS_PARTICLE, position);
            activity.setResult(RESULT_OK, goingBack);
            activity.finish();
        }
    }
}
