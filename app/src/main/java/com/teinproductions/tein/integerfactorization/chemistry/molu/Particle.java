package com.teinproductions.tein.integerfactorization.chemistry.molu;


import java.io.Serializable;

public interface Particle extends Serializable {

    public String getName();

    public String getAbbreviation();

    public Double getMass();

    public Double getDensity();

    public Integer getYearOfDiscovery();

    public String getYearOfDiscoveryString();

    public Integer getAtomicNumber();

    public ParticleFragment toFragment();
}
