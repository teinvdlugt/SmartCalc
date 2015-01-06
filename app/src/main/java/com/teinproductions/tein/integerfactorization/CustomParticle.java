package com.teinproductions.tein.integerfactorization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public String toJSON() {
        String strName, strAbbr, strMass, strDensity, quote = "\"";
        if (this.name != null) {
            strName = quote + this.name + quote;
        } else {
            strName = "null";
        }
        if (this.abbreviation != null) {
            strAbbr = quote + this.abbreviation + quote;
        } else {
            strAbbr = "null";
        }
        if (this.mass != null) {
            strMass = this.mass.toString();
        } else {
            strMass = "null";
        }
        if (this.density != null) {
            strDensity = this.density.toString();
        } else {
            strDensity = "null";
        }
        return "{\"name\":" + strName + ",\"abbreviation\":" + strAbbr +
                ",\"mass\":" + strMass + ",\"density\":" + strDensity + "}";
    }

    public static CustomParticle fromJSON(JSONObject jObject) {
        String name = null, abbr = null;
        Double mass = null, density = null;
        try {
            if (!jObject.isNull("name")) {
                name = jObject.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            if (!jObject.isNull("abbreviation")) {
                abbr = jObject.getString("abbreviation");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            mass = jObject.getDouble("mass");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            density = jObject.getDouble("density");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new CustomParticle(name, abbr, mass, density);
    }

    public static String arrayToJSON(CustomParticle[] customParticles) {
        StringBuilder sb = new StringBuilder("{\"particles\":[");
        for (CustomParticle customParticle : customParticles) {
            sb.append(customParticle.toJSON());
        }
        sb.append("]}");
        return sb.toString().replace("}{", "},{"); // put commas between the multiple JSONObjects
    }

    public static CustomParticle[] arrayFromJSON(JSONArray jArray) {
        CustomParticle[] customParticles = new CustomParticle[jArray.length()];
        try {
            for (int i = 0; i < jArray.length(); i++) {
                customParticles[i] = fromJSON(jArray.getJSONObject(i));
            }
            return customParticles;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ParticleFragment toFragment() {
        return ParticleFragment.newInstance(this);
    }
}
