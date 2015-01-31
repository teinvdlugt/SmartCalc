package com.teinproductions.tein.integerfactorization.chemistry.molu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teinproductions.tein.integerfactorization.R;

import java.text.DecimalFormat;

public class ParticleListAdapter extends ArrayAdapter {

    private Particle[] particles;

    public ParticleListAdapter(Context context, Particle[] particles) {
        super(context, R.layout.element_info_list_layout);
        this.particles = particles;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View theView = LayoutInflater.from(getContext()).inflate(R.layout.element_info_list_layout, parent, false);

        TextView particleNameTextView = (TextView) theView.findViewById(R.id.particle_name_text_view);
        TextView atomicNumberTextView = (TextView) theView.findViewById(R.id.atomic_number_text_view);
        TextView atomicMassTextView = (TextView) theView.findViewById(R.id.atomic_mass_text_view);
        TextView abbreviationTextView = (TextView) theView.findViewById(R.id.abbreviation_text_view);

        try {
            particleNameTextView.setText(particles[position].getName());
        } catch (NullPointerException ignored) {
        }
        try {
            atomicMassTextView.setText(new DecimalFormat().format(particles[position].getMass()));
        } catch (NullPointerException | IllegalArgumentException ignored) {
        }
        try {
            abbreviationTextView.setText(particles[position].getAbbreviation());
        } catch (NullPointerException ignored) {
        }
        try {
            atomicNumberTextView.setText(particles[position].getAtomicNumber().toString());
        } catch (UnsupportedOperationException ignored) {
        }

        return theView;
    }

    @Override
    public int getCount() {
        return particles.length;
    }
}
