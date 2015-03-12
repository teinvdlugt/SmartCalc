package com.teinproductions.tein.smartcalc;


import android.content.Context;

import java.text.DecimalFormat;

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

        private final Double factor;
        private final Integer word, abbreviation;

        public static final Double C = 299792485.0;

        public Double convertTo(Velocity converted, Double value) {
            return value / this.getFactor() * converted.getFactor();
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

        private final Double factor;
        private final int word, abbreviation;

        public Double convertTo(Time converted, Double value) {
            return value / this.getFactor() * converted.getFactor();
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

    public enum Length {
        PLANCKLENGTH(1873559000000000000000000000000000.0, R.string.planck_length, R.string.planck_length_abbr),
        NANOMETER(1000000000.0, R.string.nanometer, R.string.nanometer_abbr),
        MICROMETER(1000000.0, R.string.micrometer, R.string.micrometer_abbr),
        MILLIMETER(1000.0, R.string.millimeter, R.string.millimeter_abbr),
        CENTIMETER(100.0, R.string.centimeter, R.string.centimeter_abbr),
        METER(1.0, R.string.meter, R.string.meter_abbr),
        KILOMETER(0.001, R.string.kilometer, R.string.kilometer_abbr),
        YARD(1.09361, R.string.yard, R.string.yard_abbr),
        MILE(0.000621371, R.string.mile, R.string.mile_abbr),
        FOOT(3.28084, R.string.foot, R.string.foot_abbr),
        INCH(39.3701, R.string.inch, R.string.inch_abbr);

        Length(Double factor, int word, int abbreviation) {
            this.factor = factor;
            this.word = word;
            this.abbreviation = abbreviation;
        }

        private final Double factor;
        private final int word, abbreviation;

        public Double convertTo(Length converted, Double value) {
            return value / this.getFactor() * converted.getFactor();
        }

        public static String[] getWords(Context context) {
            String[] output = new String[Length.values().length];

            for (int i = 0; i < Length.values().length; i++) {
                output[i] = Length.values()[i].getWord(context);
            }

            return output;
        }

        public static String[] getAbbreviations(Context context) {
            String[] output = new String[Length.values().length];

            for (int i = 0; i < Length.values().length; i++) {
                output[i] = Length.values()[i].getAbbreviation(context);
            }

            return output;
        }

        public Double getFactor() {
            return this.factor;
        }

        public String getWord(Context context) {
            return context.getString(this.word);
        }

        public String getAbbreviation(Context context) {
            return context.getString(this.abbreviation);
        }
    }

    public enum Mass {
        MILLIGRAM(1000000.0, R.string.milligram, R.string.milligram_abbr),
        GRAM(1000.0, R.string.gram, R.string.gram_abbr),
        KILOGRAMS(1.0, R.string.kilogram, R.string.kilogram_abbr),
        TONNE(0.001, R.string.tonne, R.string.tonne_abbr),
        LONG_TON(0.000984207, R.string.long_ton, R.string.long_ton_abbr),
        SHORT_TON(0.00110231, R.string.short_ton, R.string.short_ton_abbr),
        STONE(0.157473, R.string.stone, R.string.stone_abbr),
        POUND(2.20462, R.string.pound, R.string.pound_abbr),
        OUNCE(35.274, R.string.ounce, R.string.ounce_abbr);

        final Double factor;
        final int word, abbreviation;

        Mass(Double factor, int word, int abbreviation) {
            this.factor = factor;
            this.word = word;
            this.abbreviation = abbreviation;
        }

        public Double convertTo(Mass converted, Double value) {
            return value / this.factor * converted.getFactor();
        }

        public static String[] getWords(Context context) {
            String[] output = new String[Mass.values().length];

            for (int i = 0; i < Mass.values().length; i++) {
                output[i] = Mass.values()[i].getWord(context);
            }

            return output;
        }

        public static String[] getAbbreviations(Context context) {
            String[] output = new String[Mass.values().length];

            for (int i = 0; i < Mass.values().length; i++) {
                output[i] = Mass.values()[i].getAbbreviation(context);
            }

            return output;
        }

        public Double getFactor() {
            return factor;
        }

        public String getWord(Context context) {
            return context.getString(word);
        }

        public String getAbbreviation(Context context) {
            return context.getString(abbreviation);
        }

    }

    public enum Temperature {
        KELVIN(R.string.kelvin, R.string.kelvin_abbr),
        CELSIUS(R.string.celsius, R.string.celsius_abbr),
        FAHRENHEIT(R.string.fahrenheit, R.string.fahrenheit_abbr);

        private final int word, abbreviation;

        Temperature(int word, int abbreviation) {
            this.word = word;
            this.abbreviation = abbreviation;
        }

        public Double convertTo(Temperature converted, Double value) {
            // Formula for conversion between Celsius and Fahrenheit:
            // F = C * 9/5 + 32
            // C = (F - 32) * 5/9
            // http://www.mathatube.com/fahrenheit-to-celsius-converter.html

            switch (this) {
                case KELVIN:
                    switch (converted) {
                        case KELVIN:
                            return value;
                        case CELSIUS:
                            return value - 273;
                        case FAHRENHEIT:
                            return CELSIUS.convertTo(FAHRENHEIT, value - 273);
                    }
                case CELSIUS:
                    switch (converted) {
                        case KELVIN:
                            return value + 273;
                        case CELSIUS:
                            return value;
                        case FAHRENHEIT:
                            return value * 9 / 5 + 32;
                    }
                case FAHRENHEIT:
                    switch (converted) {
                        case KELVIN:
                            return FAHRENHEIT.convertTo(CELSIUS, value) + 273;
                        case CELSIUS:
                            return (value - 32) * 5 / 9;
                        case FAHRENHEIT:
                            return value;
                    }
                default:
                    return null;
            }
        }

        public String getWord(Context context) {
            return context.getResources().getString(word);
        }

        public static String[] getWords(Context context) {
            String[] names = new String[values().length];
            for (int i = 0; i < names.length; i++) {
                names[i] = values()[i].getWord(context);
            }
            return names;
        }

        public String getAbbreviation(Context context) {
            return context.getResources().getString(abbreviation);
        }

        public static String[] getAbbreviations(Context context) {
            String[] abbreviations = new String[values().length];
            for (int i = 0; i < abbreviations.length; i++) {
                abbreviations[i] = values()[i].getAbbreviation(context);
            }
            return abbreviations;
        }

    }

    public static String format(Double input) {
        if (input == 0) {
            return "0";
        } else if (Math.abs(input) > 0 && Math.abs(input) <= 0.0001) {
            return new DecimalFormat("0.##########E0").format(input);
        } else if (Math.abs(input) < 1000000) {
            return new DecimalFormat("0.##########").format(input);
        } else {
            return new DecimalFormat("0.##########E0").format(input);
        }
    }

    public static final Double G = 00000000000.667384;
    public static final Double nA = 602214000000000000000000.0;

}
