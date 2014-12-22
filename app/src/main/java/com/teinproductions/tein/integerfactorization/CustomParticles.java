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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CustomParticles extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Particle[] particles = new Particle[3];
        particles[0] = new Particle("John", "J", 5.6, null);
        particles[1] = new Particle("Maria", "M", null, 6.5);
        particles[2] = new Particle(null, "Citroen", 4.5, 0.0000007);
        // String jsonString = "{\"particles\":[{\"name\":\"One\",\"abbreviation\":\"1\",\"mass\":1.11,\"density\":null}, " +
        //        "{\"name\":null,\"abbreviation\":null,\"mass\":2.22,\"density\":2.34}, " +
        //        "{\"name\":\"Three\",\"abbreviation\":\"3\",\"mass\":3.333,\"density\":3.456}]}";
        String jsonString = Particle.arrayToJSON(particles, this);

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("particles");
            Particle[] particles2 = Particle.arrayFromJSON(jsonArray);
            ListView listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(new CustomParticlesListAdapter(this, particles2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

            if (particles[position].getName() != null) {
                customParticleName.setText(particles[position].getName());
            }

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
