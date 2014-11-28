package com.teinproductions.tein.integerfactorization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ElementFragment extends Fragment {

    TextView atomicMassTextView, abbreviationTextView, atomicNumberTextView, yearOfDiscTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.fragment_element, container, false);

        atomicMassTextView = (TextView) theView.findViewById(R.id.fragment_element_atomic_mass);
        abbreviationTextView = (TextView) theView.findViewById(R.id.fragment_element_abbreviation);
        atomicNumberTextView = (TextView) theView.findViewById(R.id.fragment_element_atomic_number);
        yearOfDiscTextView = (TextView) theView.findViewById(R.id.fragment_element_year_of_disc);

        if(getArguments() == null){
            return theView;
        }

        Element element = (Element) getArguments().getSerializable("element");

        atomicMassTextView.setText(new DecimalFormat().format(element.getMass()) + "u");
        abbreviationTextView.setText(element.getAbbreviation());
        atomicNumberTextView.setText(new DecimalFormat().format(element.getAtomicNumber()));
        yearOfDiscTextView.setText(element.getDiscYearString());

        return theView;
    }

    public static ElementFragment newInstance(Element element){
        ElementFragment elementFragment = new ElementFragment();
        Bundle args = new Bundle();
        args.putSerializable("element", element);
        elementFragment.setArguments(args);

        return elementFragment;
    }
}
