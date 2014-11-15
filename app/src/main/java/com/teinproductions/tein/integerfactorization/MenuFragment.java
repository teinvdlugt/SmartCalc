package com.teinproductions.tein.integerfactorization;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

public class MenuFragment extends ListFragment {

    private String[] strings;
    public static String STRINGARRAY = "Strings to fill the ListFragment with";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        strings = getArguments().getStringArray(STRINGARRAY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, strings));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallBack.onItemClicked(strings, position);
            }
        });

    }

    public static MenuFragment newInstance(String[] strings) {

        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putStringArray(STRINGARRAY, strings);
        fragment.setArguments(args);

        return fragment;

    }


    public interface onMenuItemClickListener {
        public void onItemClicked(String[] strings, int position);
    }

    onMenuItemClickListener mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallBack = (onMenuItemClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement MenuFragment.onMenuItemClickListener");
        }

    }
}
