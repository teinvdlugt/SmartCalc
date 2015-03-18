package com.teinproductions.tein.smartcalc.maths;

import android.os.AsyncTask;

import com.teinproductions.tein.smartcalc.ArrayChecker;

import java.util.ArrayList;

public class PrimeCalculator {

    @SuppressWarnings("ConstantConditions")
    public static Integer[] factorize(Long integer, AsyncTask asyncTask) {

        ArrayList<Integer> factors = new ArrayList<>();

        if (integer == 0 || integer == 1) {
            return null;
        }

        Double squareRoot = Math.sqrt(integer);

        int j = 2;
        while (1 + 1 == 2) {
            if (asyncTask.isCancelled()) return null;

            if (integer == 1) {
                return ArrayChecker.convertToArray(factors);
            }
            if (j > squareRoot && j != integer) {
                factors.add(Integer.parseInt(integer.toString()));
                return ArrayChecker.convertToArray(factors);
            } else if (integer % j == 0) {
                factors.add(j);
                integer /= j;
                squareRoot = Math.sqrt(integer);
                continue;
            }

            j = findNextPrimeNumber(j, asyncTask);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static Long[] factorizeLong(Long integer, AsyncTask asyncTask) {

        ArrayList<Long> factors = new ArrayList<>();

        if (integer == 0 || integer == 1) {
            return null;
        }

        Double squareRoot = Math.sqrt(integer);

        long j = 2;
        while (1 + 1 == 2) {
            if (asyncTask.isCancelled()) return null;

            if (integer == 1) {
                return ArrayChecker.convertToLongArray(factors);
            }
            if (j > squareRoot && j != integer) {
                factors.add(integer);
                return ArrayChecker.convertToLongArray(factors);
            } else if (integer % j == 0) {
                factors.add(j);
                integer /= j;
                squareRoot = Math.sqrt(integer);
                continue;
            }

            j = findNextPrimeNumber(j, asyncTask);
        }
    }

    public static Integer findGCF(Long num1, Long num2, AsyncTask asyncTask) {
        Integer[] factors1 = factorize(num1, asyncTask);
        Integer[] factors2 = factorize(num2, asyncTask);

        if (factors1 == null || factors2 == null) {
            return null;
        }

        ArrayList<Integer> factorList1 = ArrayChecker.convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = ArrayChecker.convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<>();

        for (Integer num : factorList1) {
            if (factorList2.contains(num)) {
                numbers.add(num);
                factorList2.remove(num);
            }
        }

        Integer GCF = 1;

        for (int number : numbers) {
            GCF *= number;
        }

        return GCF;
    }

    public static Integer findLCM(Long num1, Long num2, AsyncTask asyncTask) {
        Integer[] factors1 = factorize(num1, asyncTask);
        Integer[] factors2 = factorize(num2, asyncTask);

        if (factors1 == null || factors2 == null) {
            return null;
        }

        ArrayList<Integer> factorList1 = ArrayChecker.convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = ArrayChecker.convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<>();

        for (Integer num : factorList1) {
            numbers.add(num);
            if (factorList2.contains(num)) {
                factorList2.remove(factorList2.indexOf(num));
            }
        }

        for (Integer num : factorList2) {
            numbers.add(num);
        }

        Integer LCM = 1;

        for (Integer num : numbers) {
            LCM *= num;
        }

        return LCM;
    }

    @SuppressWarnings("ConstantConditions")
    public static Integer findNextPrimeNumber(Integer integer, AsyncTask asyncTask) {

        int i = integer + 1;
        while (1 + 1 == 2) {
            if (asyncTask.isCancelled()) return null;

            if (isPrimeNumber(i, asyncTask)) {
                return i;
            }
            i++;
        }
    }

    public static boolean isPrimeNumber(int integer, AsyncTask asyncTask) {
        for (int i = 2; i <= integer / 2; i++) {
            if (asyncTask.isCancelled()) return false;
            if (integer % i == 0 && integer != i) return false;
        }
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    public static Long findNextPrimeNumber(Long number, AsyncTask asyncTask) {

        long i = number + 1;
        while (1 + 1 == 2) {
            if (asyncTask.isCancelled()) return null;

            if (isPrimeNumber(i, asyncTask)) {
                return i;
            }
            i++;
        }
    }

    public static boolean isPrimeNumber(Long number, AsyncTask asyncTask) {
        for (long i = 2; i <= number / 2; i++) {
            if (asyncTask.isCancelled()) return false;
            if (number % i == 0 && number != i) return false;
        }
        return true;
    }

    public static boolean areRelativePrimes(long num1, long num2, AsyncTask asyncTask) {
        return findGCF(num1, num2, asyncTask) == 1;
    }
}