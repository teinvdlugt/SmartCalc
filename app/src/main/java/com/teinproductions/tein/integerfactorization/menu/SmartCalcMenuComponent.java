package com.teinproductions.tein.integerfactorization.menu;


import java.io.Serializable;

public interface SmartCalcMenuComponent extends Serializable {

    public String getName();
    public SmartCalcMenuComponent[] getChildren();
    public SmartCalcMenuComponent getParent();
    public void setParent(SmartCalcMenuComponent parent);
    public void onClick(MenuActivity activity);
    public SmartCalcMenuComponent add(SmartCalcMenuComponent component);

}
