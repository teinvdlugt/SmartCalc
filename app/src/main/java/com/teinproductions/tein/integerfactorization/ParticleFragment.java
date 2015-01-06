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

    TextView atomicMassTextView, abbreviationTextView, atomicNumberTextView, yearOfDiscTextView, densityTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_element, container, false);

        atomicMassTextView = (TextView) theView.findViewById(R.id.fragment_element_atomic_mass);
        abbreviationTextView = (TextView) theView.findViewById(R.id.fragment_element_abbreviation);
        atomicNumberTextView = (TextView) theView.findViewById(R.id.fragment_element_atomic_number);
        yearOfDiscTextView = (TextView) theView.findViewById(R.id.fragment_element_year_of_disc);
        densityTextView = (TextView) theView.findViewById(R.id.fragment_element_density);

        if (getArguments() == null) {
            return theView;
        }

        Particle particle = (Particle) getArguments().getSerializable(PARTICLE);

        // Mass
        if (particle.getMass() == null) {
            atomicMassTextView.setText(R.string.unknown);
        } else {
            atomicMassTextView.setText(new DecimalFormat().format(particle.getMass()) + " u");
        }

        // Abbreviation
        abbreviationTextView.setText(particle.getAbbreviation());

        // Atomic Number
        try {
            atomicNumberTextView.setText(new DecimalFormat().format(particle.getAtomicNumber()));
        } catch (UnsupportedOperationException e) {
            atomicNumberTextView.setVisibility(View.GONE);
        }

        // Year of Discovery
        try {
            yearOfDiscTextView.setText(particle.getYearOfDiscoveryString());
        } catch (UnsupportedOperationException e) {
            theView.findViewById(R.id.yr_of_disc_layout).setVisibility(View.GONE);
        }

        // Density
        if (particle.getDensity() == null) {
            densityTextView.setText(R.string.unknown);
        } else {
            densityTextView.setText(new DecimalFormat().format(particle.getDensity())
                    + " " + getString(R.string.gpcm3));
        }

        return theView;
    }

    public static ParticleFragment newInstance(Particle particle) {
        ParticleFragment particleFragment = new ParticleFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARTICLE, particle);
        particleFragment.setArguments(args);

        return particleFragment;
    }
}