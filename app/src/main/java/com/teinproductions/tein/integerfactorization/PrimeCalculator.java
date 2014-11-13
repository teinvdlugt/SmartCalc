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

        for (int i = 2; i <= integer; i++) {
            if (isPrimeNumber(i)) {
                primes.add(i);
            }
        }

        return convertToArray(primes);
    }

    public static boolean isPrimeNumber(int integer) {

        int numberOfFactors = 0;
        // Possible error: is 1 excluded?
        for (int i = 1; i <= integer; i++) {
            if (integer % i == 0) numberOfFactors++;
        }

        return numberOfFactors == 2;
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
