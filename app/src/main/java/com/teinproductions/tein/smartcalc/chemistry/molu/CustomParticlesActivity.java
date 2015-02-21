package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.teinproductions.tein.smartcalc.R;

public class CustomParticlesActivity extends ActionBarActivity
        implements CustomParticleRecyclerAdapter.OnClickListener {

    public static final String FILE_NAME = "particles";
    public static final int PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE = 1;

    private RecyclerView recyclerView;
    private CustomParticle[] customParticles;
    private DatabaseManager dbManager = null;

    private boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_particles);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize particles and load the saved particles into the list view
        reloadParticles();
        recyclerView.setAdapter(new CustomParticleRecyclerAdapter(customParticles, this));
    }

    private void reloadParticles() {
        if (dbManager == null) {
            dbManager = new DatabaseManager(this);
        }

        customParticles = dbManager.getParticles();
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
            recyclerView.swapAdapter(new CustomParticleRecyclerAdapter(customParticles, this), true);
            changed = true;
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ParticlePagerActivity.RELOAD_PARTICLES, changed);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickEdit(int i) {
        Intent intent = new Intent(this, ParticleEditActivity.class);
        intent.putExtra(ParticleEditActivity.PARTICLE_ARRAY, customParticles);
        intent.putExtra(ParticleEditActivity.PARTICLE_POSITION, i);
        startActivityForResult(intent, PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onClickGoTo(int i) {
        Intent intent = new Intent();
        intent.putExtra(ParticlePagerActivity.GO_TO_PARTICLE, i);
        setResult(RESULT_OK, intent);
        finish();
    }

    /*private static class CustomParticlesListAdapter extends ArrayAdapter<String> {

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
            Intent intent = new Intent();
            intent.putExtra(ParticlePagerActivity.GO_TO_PARTICLE, position);
            activity.setResult(RESULT_OK, intent);
            activity.finish();
        }
    }*/
}
