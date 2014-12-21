package com.teinproductions.tein.integerfactorization;


import java.io.Serializable;

public class Particle implements Serializable {

    private String name, abbreviation;
    private Double mass, density;

    public Particle(String name, String abbreviation, Double mass, Double density) {
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

    public void setDensity(Double density) {
        this.density = density;
    }

    public String toJSON() {
        // TODO
        return null;
    }

    public static Particle fromJSON(String jsonString) {
        // TODO
        return null;
    }
}
