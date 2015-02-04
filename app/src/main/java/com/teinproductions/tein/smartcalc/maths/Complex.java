package com.teinproductions.tein.smartcalc.maths;

import java.text.DecimalFormat;

public class Complex {

    private double real, imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    // http://en.wikipedia.org/wiki/Complex_number#Addition_and_subtraction

    public static Complex add(Complex complex1, Complex complex2) {
        // (a + bi) + (c + di) = (a + c) + (b + d)i
        double a = complex1.getReal(), b = complex1.getImaginary(), c = complex2.getReal(), d = complex2.getImaginary();
        return new Complex(a + c, b + d);
    }

    public static Complex subtract(Complex complex1, Complex complex2) {
        // (a + bi) - (c + di) = (a - c) + (b - d)i
        double a = complex1.getReal(), b = complex1.getImaginary(), c = complex2.getReal(), d = complex2.getImaginary();
        return new Complex(a - c, b - d);
    }

    // http://en.wikipedia.org/wiki/Complex_number#Multiplication_and_division

    public static Complex multiply(Complex complex1, Complex complex2) {
        // (a + bi) * (c + di) = (ac - bd) + (bc + ad)i
        double a = complex1.getReal(), b = complex1.getImaginary(), c = complex2.getReal(), d = complex2.getImaginary();
        return new Complex(a * c - b * d, b * c + a * d);
    }

    public static Complex divide(Complex complex1, Complex complex2) {
        // (a + bi) / (c + di) = ( (ac + bd) / (c^2 + d^2) ) + ( (bc - ad) / (c^2 + d^2) )i
        double a = complex1.getReal(), b = complex1.getImaginary(), c = complex2.getReal(), d = complex2.getImaginary();
        return new Complex(((a * c + b * d) / (c * c + d * d)), ((b * c - a * d) / (c * c + d * d)));
    }

    public Complex pow(int exponent) {
        // http://nl.wikipedia.org/wiki/Formule_van_Euler TODO to the power of a complex number

        if (exponent == 0) return new Complex(1, 0);
        if (exponent == 1) return this;

        Complex result = this;

        for (int i = 1; i < exponent; i++) {
            result = Complex.multiply(result, this);
        }

        return result;
    }

    public Complex square() {
        return this.pow(2);
    }

    @Override
    public String toString() {
        Double real = this.getReal();
        Double imaginary = this.getImaginary();

        DecimalFormat format = new DecimalFormat();

        if (imaginary == 0) {
            return format.format(real);
        } else if (real == 0) {
            return format.format(imaginary) + "i";
        } else {
            return format.format(real) + " + " + format.format(imaginary) + "i";
        }

    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
}
