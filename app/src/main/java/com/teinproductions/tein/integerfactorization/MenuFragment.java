package com.teinproductions.tein.integerfactorization;

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

public class MenuFragment extends ListFragment {

    private SmartCalcMenu menu;
    public static final String MENU_TO_SHOW = "com.teinproductions.tein.integerfactorization.MENU_TO_SHOW";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        menu = (SmartCalcMenu) getArguments().getSerializable(MENU_TO_SHOW);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        setListAdapter(new MenuAdapter(getActivity(), menu));

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menu.getChildren()[position].onClick((MenuActivity) getActivity());
            }
        });
    }

    public static MenuFragment newInstance(SmartCalcMenu menu) {

        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putSerializable(MENU_TO_SHOW, menu);
        fragment.setArguments(args);

        return fragment;
    }

    public class MenuAdapter extends ArrayAdapter {

        private SmartCalcMenu menu;

        public MenuAdapter(Context context, SmartCalcMenu menu) {
            super(context, R.layout.list_item_menu);
            this.menu = menu;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater theInflater = LayoutInflater.from(getContext());
            View theView = theInflater.inflate(R.layout.list_item_menu, parent, false);

            TextView theTextView = (TextView) theView.findViewById(R.id.menu_text);
            theTextView.setText(menu.getChildren()[position].getName());

            if (menu.getChildren()[position] instanceof SmartCalcMenu) {
                theView.findViewById(R.id.menu_arrow).setVisibility(View.VISIBLE);
            }

            return theView;
        }

        @Override
        public int getCount() {
            return menu.getChildren().length;
        }
    }
}
