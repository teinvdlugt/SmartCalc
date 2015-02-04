package com.teinproductions.tein.smartcalc.menu;


import android.content.Intent;

public class SmartCalcMenuItem implements SmartCalcMenuComponent {

    private String name;
    private SmartCalcMenuComponent parent;
    private Class classToOpen;

    public SmartCalcMenuItem(String name, Class classToOpen) {
        this.name = name;
        this.classToOpen = classToOpen;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public SmartCalcMenuComponent[] getChildren() {
        throw new UnsupportedOperationException("A SmartCalcMenuItem doesn't have children");
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
        Intent intent = new Intent(activity, classToOpen);
        activity.startActivity(intent);
    }

    @Override
    public SmartCalcMenuComponent add(SmartCalcMenuComponent component) {
        throw new UnsupportedOperationException("A SmartCalcMenuItem can't add children");
    }
}
