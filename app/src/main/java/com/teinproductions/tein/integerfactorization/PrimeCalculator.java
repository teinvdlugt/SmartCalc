package com.teinproductions.tein.integerfactorization;

import java.util.ArrayList;

public class PrimeCalculator {

    public static Integer[] factorize(int integer) {
        ArrayList<Integer> factors = new ArrayList<Integer>();
        Integer[] primes = makePrimesUpTo(integer);

        for (int prime : primes) {

            while (1 + 1 == 2) {
                if (integer % prime == 0) {
                    factors.add(prime);
                    integer /= prime;
                } else {
                    break;
                }
            }

        }

        return convertToArray(factors);
    }

    public static Integer[] makePrimesUpTo(int integer) {
        ArrayList<Integer> primes = new ArrayList<Integer>();

        for (int i = 2; i <= integer / 2; i++) {
            if (isPrimeNumber(i)) {
                primes.add(i);
            }
        }

        return convertToArray(primes);
    }

    public static boolean isPrimeNumber(int integer) {

        for (int i = 2; i <= integer / 2; i++) {
            if (integer % 2 == 0 && integer != i) {
                return false;
            }
        } return true;
    }

    public static Integer[] convertToArray(ArrayList<Integer> integers) {

        Integer[] result = new Integer[integers.size()];
        for (int i = 0; i < result.length; i++)
        {
            result[i] = integers.get(i);
        }

        return result;
    }

}
