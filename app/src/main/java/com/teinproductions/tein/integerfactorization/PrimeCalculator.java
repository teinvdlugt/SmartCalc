package com.teinproductions.tein.integerfactorization;

import java.util.ArrayList;

public class PrimeCalculator {

    public static Integer[] factorize(int integer) {

        ArrayList<Integer> factors = new ArrayList<>();

        if (integer == 0 || integer == 1) {
            return null;
        }

        Double squareRoot = Math.sqrt(integer);

        int j = 2;
        while (1 + 1 == 2) {
            if (integer == 1) {
                return convertToArray(factors);
            }
            if (j > squareRoot && j != integer) {
                factors.add(integer);
                return convertToArray(factors);
            } else if (integer % j == 0) {
                factors.add(j);
                integer /= j;
                squareRoot = Math.sqrt(integer);
                continue;
            }

            j = findNextPrimeNumber(j);
        }
    }

    public static Integer[] factorize(Long integer) {

        ArrayList<Integer> factors = new ArrayList<>();

        if (integer == 0 || integer == 1) {
            return null;
        }

        Double squareRoot = Math.sqrt(integer);

        int j = 2;
        while (1 + 1 == 2) {
            if (integer == 1) {
                return convertToArray(factors);
            }
            if (j > squareRoot && j != integer) {
                factors.add(Integer.parseInt(integer.toString()));
                return convertToArray(factors);
            } else if (integer % j == 0) {
                factors.add(j);
                integer /= j;
                squareRoot = Math.sqrt(integer);
                continue;
            }

            j = findNextPrimeNumber(j);
        }
    }

    public static Integer findNextPrimeNumber(Integer integer) {

        int i = integer + 1;
        while (1 + 1 == 2) {
            if (isPrimeNumber(i)) {
                return i;
            }
            i++;
        }

    }

    public static Integer[] makePrimesUpTo(int integer) {
        ArrayList<Integer> primes = new ArrayList<>();

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
        ArrayList<Integer> arrayList = new ArrayList<>();
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

    public static Integer findGCF(Integer[] factors1, Integer[] factors2) {
        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

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

    public static Integer findLongGCF(Long num1, Long num2) {
        Integer[] factors1 = factorize(num1);
        Integer[] factors2 = factorize(num2);

        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

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

    public static Integer findLCM(Integer int1, Integer int2) {
        Integer[] factors1 = factorize(int1);
        Integer[] factors2 = factorize(int2);

        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<>();

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

        for (Integer num : numbers) {
            LCM *= num;
        }

        return LCM;

    }

    public static Integer findLCM(Integer[] factors1, Integer[] factors2) {
        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

        ArrayList<Integer> numbers = new ArrayList<>();

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

        for (Integer num : numbers) {
            LCM *= num;
        }

        return LCM;

    }

    public static Integer findLongLCM(Long num1, Long num2) {
        Integer[] factors1 = factorize(num1);
        Integer[] factors2 = factorize(num2);

        ArrayList<Integer> factorList1 = convertToArrayList(factors1);
        ArrayList<Integer> factorList2 = convertToArrayList(factors2);

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
}
