package com.teinproductions.tein.integerfactorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;

public class CustomParticles extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Particle[] particles = new Particle[3];
        particles[0] = new Particle("One", "1", 1.0, null);
        particles[1] = new Particle("Two", "2", null, 2.2);
        particles[2] = new Particle("Three", null, 3.0, 3.3);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomParticlesListAdapter(this, particles));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    private static class CustomParticlesListAdapter extends ArrayAdapter<String> {

        Particle[] particles;

        public CustomParticlesListAdapter(Context context, Particle[] particles) {
            super(context, R.layout.list_item_custom_particles);
            this.particles = particles;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            View theView = theInflater.inflate(R.layout.list_item_custom_particles, parent, false);

            TextView customParticleName = (TextView) theView.findViewById(R.id.particle_name_text_view);
            ImageView imgEdit = (ImageView) theView.findViewById(R.id.img_edit);
            ImageView imgGo = (ImageView) theView.findViewById(R.id.img_go);

            customParticleName.setText(particles[position].getName());
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editParticle(particles[position]);
                }
            });

            return theView;
        }

        @Override
        public int getCount() {
            return 3;
        }

        private void editParticle(Particle particle) {
            Intent intent = new Intent(getContext(), ParticleEditActivity.class);
            intent.putExtra(ParticleEditActivity.PARTICLE_TO_EDIT, (Serializable) particle);
            getContext().startActivity(intent);
        }

    }

}
