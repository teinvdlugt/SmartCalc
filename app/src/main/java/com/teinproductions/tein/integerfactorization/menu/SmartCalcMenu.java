package com.teinproductions.tein.integerfactorization.menu;


import android.support.v4.app.FragmentTransaction;

import com.teinproductions.tein.integerfactorization.R;

import java.util.ArrayList;

public class SmartCalcMenu implements SmartCalcMenuComponent {

    private ArrayList<SmartCalcMenuComponent> children;
    private String name;
    private SmartCalcMenuComponent parent;

    public SmartCalcMenu(String name) {
        this.children = new ArrayList<>();
        this.name = name;
    }

    public SmartCalcMenu() {
        this.children = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SmartCalcMenuComponent[] getChildren() {
        SmartCalcMenuComponent[] result = new SmartCalcMenuComponent[children.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = children.get(i);
        }
        return result;
    }

    @Override
    public SmartCalcMenuComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(SmartCalcMenuComponent parent) {
        this.parent = parent;
    }

    @Override
    public void onClick(MenuActivity activity) {
        MenuFragment newFragment = MenuFragment.newInstance(this);

        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.menu_fragment_container, newFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();

        activity.setCurrentMenu(this);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(name);
    }

    @Override
    public SmartCalcMenuComponent add(SmartCalcMenuComponent component) {
        children.add(component);
        component.setParent(this);
        return this;
    }
}
