package com.teinproductions.tein.integerfactorization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ParticleFragment extends Fragment {

    public static final String PARTICLE = "com.teinproductions.integerfactorization.PARTICLE";

    TextView atomicMassTextView, abbreviationTextView, densityTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_element, container, false);

        atomicMassTextView = (TextView) theView.findViewById(R.id.fragment_element_atomic_mass);
        abbreviationTextView = (TextView) theView.findViewById(R.id.fragment_element_abbreviation);
        densityTextView = (TextView) theView.findViewById(R.id.fragment_element_density);

        theView.findViewById(R.id.yr_of_disc_layout).setVisibility(View.GONE);
        theView.findViewById(R.id.fragment_element_atomic_number).setVisibility(View.GONE);

        if (getArguments() == null) {
            return theView;
        }

        Particle particle = (Particle) getArguments().getSerializable("element");

        // Abbreviation
        abbreviationTextView.setText(particle.getAbbreviation());

        // Atomic mass
        if (particle.getMass() == null) {
            atomicMassTextView.setText(R.string.unknown);
        } else {
            atomicMassTextView.setText(new DecimalFormat().format(particle.getMass()) + " u");
        }

        // Density
        if (particle.getDensity() == null) {
            densityTextView.setText(R.string.unknown);
        } else {
            densityTextView.setText(new DecimalFormat().format(particle.getDensity()) + " " + getString(R.string.gpcm3));
        }

        return theView;
    }

    public static ParticleFragment newInstance(Particle particle) {
        ParticleFragment particleFragment = new ParticleFragment();
        Bundle args = new Bundle();
        args.putSerializable("particle", particle);
        particleFragment.setArguments(args);

        return particleFragment;
    }

}