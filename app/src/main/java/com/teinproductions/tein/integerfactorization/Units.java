package com.teinproductions.tein.integerfactorization;


import android.content.Context;

public class Units {

    public enum Velocity {
        MPS(1.0, R.string.meters_per_second, R.string.meters_per_second_abbr),
        KMP(3.6, R.string.km_per_hour, R.string.km_per_hour_abbr),
        MPH(2.23694, R.string.miles_per_hour, R.string.miles_per_hour_abbr),
        FPS(3.28084, R.string.feet_per_second, R.string.feet_per_second_abbr),
        KNOT(1.94384, R.string.knots, R.string.knots_abbr);

        Velocity(Double factor, int word, int abbreviation) {
            this.factor = factor;
            this.abbreviation = abbreviation;
            this.word = word;
        }

        private Double factor;
        private Integer word, abbreviation;

        public static Double convert(Velocity original, Velocity converted, Double value) {
            return value / original.getFactor() * converted.getFactor();
        }

        public Double getFactor() {
            return this.factor;
        }

        public String getWord(Context context) {
            return context.getResources().getString(this.word);
        }

        public String getAbbreviation(Context context) {
            return context.getResources().getString(this.abbreviation);
        }

        public static String[] getAbbreviations(Context context) {
            String[] abbreviations = new String[Velocity.values().length];
            for (int i = 0; i < Velocity.values().length; i++) {
                abbreviations[i] = Velocity.values()[i].getAbbreviation(context);
            }

            return abbreviations;
        }

        public static String[] getWords(Context context) {
            String[] words = new String[Velocity.values().length];
            for (int i = 0; i < Velocity.values().length; i++) {
                words[i] = Velocity.values()[i].getWord(context);
            }

            return words;
        }


    }

}
