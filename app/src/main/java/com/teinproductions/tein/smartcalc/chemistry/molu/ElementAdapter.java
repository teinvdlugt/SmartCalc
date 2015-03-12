package com.teinproductions.tein.smartcalc.chemistry.molu;


import android.content.Context;

public class ElementAdapter implements Particle {

    private transient Context context;
    // transient because a context is not Serializable (http://goo.gl/0OoIaS)
    private final Element element;

    public ElementAdapter(Context context, Element element) {
        this.context = context;
        this.element = element;
    }

    @Override
    public String getName() {
        return element.getName(context);
    }

    @Override
    public String getAbbreviation() {
        return element.getAbbreviation();
    }

    @Override
    public Double getMass() {
        return element.getMass();
    }

    @Override
    public Double getDensity() {
        return element.getDensity();
    }

    @Override
    public Integer getYearOfDiscovery() {
        return element.getYearOfDiscovery();
    }

    @Override
    public String getYearOfDiscoveryString() {
        return element.getYearOfDiscoveryString(context);
    }

    @Override
    public Integer getAtomicNumber() {
        return element.getAtomicNumber();
    }

    @Override
    public ParticleFragment toFragment() {
        return element.toFragment(this);
    }

    public static ElementAdapter[] values(Context context) {
        ElementAdapter[] result = new ElementAdapter[Element.values().length];
        for (int i = 0; i < Element.values().length; i++) {
            result[i] = new ElementAdapter(context, Element.values()[i]);
        }
        return result;
    }
}
