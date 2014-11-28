package com.teinproductions.tein.integerfactorization;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ElementListFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String[] elementNames = new String[Element.values().length];
        for(int i = 0; i < elementNames.length; i++){
            elementNames[i] = Element.values()[i].getName(getActivity());
        }

        setListAdapter(new ElementListAdapter(getActivity(), elementNames));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.onElementClick(position);
            }
        });
    }

    public interface onElementClickListener{
        public void onElementClick(int position);
    }

    onElementClickListener mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (onElementClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ElementListFragment.onElementClickListener");
        }

    }

    public class ElementListAdapter extends ArrayAdapter {


        public ElementListAdapter(Context context, String[] elementNames){

            super(context, R.layout.element_info_list_layout, elementNames);

        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            LayoutInflater theInflater = LayoutInflater.from(getContext());

            View theView = theInflater.inflate(R.layout.element_info_list_layout, parent, false);

            TextView elementNameTextView = (TextView) theView.findViewById(R.id.element_name_text_view);
            TextView atomicNumberTextView = (TextView) theView.findViewById(R.id.atomic_number_text_view);
            TextView atomicMassTextView = (TextView) theView.findViewById(R.id.atomic_mass_text_view);
            TextView abbreviationTextView = (TextView) theView.findViewById(R.id.abbreviation_text_view);

            elementNameTextView.setText(Element.values()[position].getName(getContext()));
            atomicNumberTextView.setText(Element.values()[position].getAtomicNumber().toString());
            atomicMassTextView.setText(Element.values()[position].getMass().toString());
            abbreviationTextView.setText(Element.values()[position].getAbbreviation());

            return theView;
        }
    }
}
