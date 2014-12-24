package com.teinproductions.tein.integerfactorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.Inflater;

public class CustomParticles extends ActionBarActivity {

    public static final String FILE_NAME = "particles";
    public static final int PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE = 1;
    public static final int NEW_PARTICLE = -1;

    private ListView listView;
    private Particle[] particles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);

        /* String jsonString = "{\"particles\":[{\"name\":\"John\",\"abbreviation\":\"abbr\",\"mass\":0.9,\"density\":1.23}," +
                "{\"name\":\"Lol\",\"abbreviation\":\"i'm happy\",\"mass\":0.9,\"density\":1.23}," +
                "{\"name\":\"Erick\",\"abbreviation\":\"i.e.\",\"mass\":0.9,\"density\":1.23}]}";

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("particles");
            listView.setAdapter(new CustomParticlesListAdapter(this, Particle.arrayFromJSON(jsonArray)));
        } catch (JSONException e) {
            e.printStackTrace();
            CustomDialog.errorParsingJSON(getFragmentManager());
            listView.setAdapter(new CustomParticlesListAdapter(this, new Particle[0]));
        } */

        // Initialize particles and load the saved particles into the list view
        reloadParticles();
    }

    private void reloadParticles() {
        String jsonString = getFile();
        if (jsonString == null) {
            // File didn't exist (yet)
            particles = new Particle[0];
        } else {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("particles");
                particles = Particle.arrayFromJSON(jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
                particles = new Particle[0];
            }
        }
        listView.setAdapter(new CustomParticlesListAdapter(this, particles));
    }

    private String getFile() {
        StringBuilder sb;

        try {
            // Opens a stream so we can read from our local file
            FileInputStream fis = this.openFileInput(FILE_NAME);

            // Gets an input stream for reading data
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            // Used to read the data in small bytes to minimize system load
            BufferedReader bufferedReader = new BufferedReader(isr);

            // Read the data in bytes until nothing is left to read
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
        intent.putExtra(ParticleEditActivity.PARTICLE_ARRAY, particles);
        intent.putExtra(ParticleEditActivity.PARTICLE_POSITION, particles.length); // particles.length is one more than the last index in particles
        startActivityForResult(intent, PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            reloadParticles();
        }

    }

    private static class CustomParticlesListAdapter extends ArrayAdapter<String> {

        Particle[] particles;
        CustomParticles activity;

        public CustomParticlesListAdapter(CustomParticles activity, Particle[] particles) {
            super(activity, R.layout.list_item_custom_particles);
            this.particles = particles;
            this.activity = activity;
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
                    editParticle(position);
                }
            });

            return theView;
        }

        @Override
        public int getCount() {
            return particles.length;
        }

        private void editParticle(int position) {
            Intent intent = new Intent(getContext(), ParticleEditActivity.class);
            intent.putExtra(ParticleEditActivity.PARTICLE_ARRAY, particles);
            intent.putExtra(ParticleEditActivity.PARTICLE_POSITION, position);
            activity.startActivityForResult(intent, PARTICLE_EDIT_TEXT_ACTIVITY_REQUEST_CODE);
        }
    }
}
