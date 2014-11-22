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

    public enum Time {
        NANOSEC(1000000000.0, R.string.nanoseconds, R.string.nanoseconds_abbr),
        MICROSEC(1000000.0, R.string.microsecond, R.string.microsecond_abbr),
        MILLISEC(1000.0, R.string.millisecond, R.string.millisecond_abbr),
        SEC(1.0, R.string.second, R.string.second_abbr),
        MIN(1.0 / 60.0, R.string.minute, R.string.minute_abbr),
        HOUR(1.0 / 3600.0, R.string.hour, R.string.hour_abbr),
        DAY(1.0 / 86400.0, R.string.day, R.string.day_abbr),
        WEEK(1.0 / 604800.0, R.string.week, R.string.week_abbr),
        MONTH(1.0 / 2630000.0, R.string.month, R.string.month_abbr),
        YEAR(1.0 / 31560000.0, R.string.years, R.string.years_abbr),
        DECADE(1.0 / 315600000.0, R.string.decade, R.string.decade_abbr),
        CENTURY(1.0 / 3156000000.0, R.string.century, R.string.century_abbr);

        Time(Double factor, int word, int abbreviation) {
            this.factor = factor;
            this.word = word;
            this.abbreviation = abbreviation;
        }

        private Double factor;
        private int word, abbreviation;

        public static Double convert(Time original, Time converted, Double value) {
            return value / original.getFactor() * converted.getFactor();
        }

        public static String[] getWords(Context context) {
            String[] output = new String[Time.values().length];

            for (int i = 0; i < Time.values().length; i++) {
                output[i] = Time.values()[i].getWord(context);
            }

            return output;
        }

        public static String[] getAbbreviations(Context context) {
            String[] output = new String[Time.values().length];

            for (int i = 0; i < Time.values().length; i++) {
                output[i] = Time.values()[i].getAbbreviation(context);
            }

            return output;
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
    }

}
