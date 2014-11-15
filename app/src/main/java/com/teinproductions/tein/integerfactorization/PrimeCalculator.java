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

        if (factors.size() == 0) {
            factors.add(integer);
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
        }
        return true;
    }

    public static Integer[] convertToArray(ArrayList<Integer> integers) {

        Integer[] result = new Integer[integers.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = integers.get(i);
        }

        return result;
    }

    public static ArrayList<Integer> convertToArrayList(Integer[] integers) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Integer integer : integers) {
            arrayList.add(integer);
        }

        return arrayList;
    }

    public static Integer findGCF(Integer int1, Integer int2) {
        Integer[] factors1 = factorize(int1);
        Integer[] factors2 = factorize(int2);

        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for (int num : factorList1) {
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

    public static Integer findGCF(Integer[] factors1, Integer[] factors2) {
        ArrayList<Integer> factorList1 = new ArrayList<Integer>();
        ArrayList<Integer> factorList2 = new ArrayList<Integer>();

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for (Integer factor : factors1) {
            factorList1.add(factor);
        }
        for (Integer factor : factors2) {
            factorList2.add(factor);
        }

        for (int num : factorList1) {
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

    public static Integer findLCM(Integer int1, Integer int2) {
        Integer[] factors1 = factorize(int1);
        Integer[] factors2 = factorize(int2);

        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for (Integer num : factorList1) {
            numbers.add(num);
            if (factorList2.contains(num)) {
                factorList2.remove(num);
            }
        }

        for (Integer num : factorList2) {
            numbers.add(num);
        }

        Integer LCM = 1;

        for(Integer num : numbers) {
            LCM *= num;
        }

        return LCM;

    }

    public static Integer findLCM(Integer[] factors1, Integer[] factors2) {
        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<Integer>();

        for (Integer num : factorList1) {
            numbers.add(num);
            if (factorList2.contains(num)) {
                factorList2.remove(num);
            }
        }

        for (Integer num : factorList2) {
            numbers.add(num);
        }

        Integer LCM = 1;

        for(Integer num : numbers) {
            LCM *= num;
        }

        return LCM;

    }

}
