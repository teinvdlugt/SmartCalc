package com.teinproductions.tein.smartcalc.chemistry.molu;

import java.io.Serializable;

public class CustomParticle implements Particle, Serializable {

    private String name, abbreviation;
    private Double mass, density;

    public CustomParticle(String name, String abbreviation, Double mass, Double density) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.mass = mass;
        this.density = density;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Double getMass() {
        return mass;
    }

    public void setMass(Double mass) {
        this.mass = mass;
    }

    public Double getDensity() {
        return density;
    }

    @Override
    public Integer getYearOfDiscovery() {
        // This method is not applicable to this class
        throw new UnsupportedOperationException("Particle.getYearOfDiscovery is not supported by CustomParticle");
    }

    @Override
    public String getYearOfDiscoveryString() {
        throw new UnsupportedOperationException("Particle.getYearOfDiscoveryString is not supported by CustomParticle");
    }

    @Override
    public Integer getAtomicNumber() {
        // This method is not applicable to this class
        throw new UnsupportedOperationException("Particle.getAtomicNumber is not supported by CustomParticle");
    }

    public void setDensity(Double density) {
        this.density = density;
    }



    public ParticleFragment toFragment() {
        return ParticleFragment.newInstance(this);
    }
}
