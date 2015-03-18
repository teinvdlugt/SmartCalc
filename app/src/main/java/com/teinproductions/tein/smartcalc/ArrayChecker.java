package com.teinproductions.tein.smartcalc;


import java.util.ArrayList;
import java.util.Collections;

public class ArrayChecker {

    public static boolean containsChar(char[] chars, char charToCheck) {
        for (char character : chars) {
            if (character == charToCheck) {
                return true;
            }
        }
        return false;
    }

    public static boolean occursNotOrOnce(char[] chars, char character) {
        boolean alreadyOccurred = false;
        for (char character2 : chars) {
            if (character2 == character) {
                if (alreadyOccurred) {
                    return false;
                } else {
                    alreadyOccurred = true;
                }
            }
        }
        return true;
    }

    public static boolean onlyContainsNumbers(char[] chars) {
        for (char character : chars) {
            if (!Character.isDigit(character)) {
                return false;
            }
        }
        return true;
    }

    public static boolean onlyContainsUpperCaseLetters(char[] chars) {
        for (char character : chars) {
            if (Character.isLowerCase(character)) {
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Integer> convertToArrayList(Integer[] integers) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, integers);

        return arrayList;
    }

    public static Integer[] convertToArray(ArrayList<Integer> integers) {
        Integer[] result = new Integer[integers.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = integers.get(i);
        }
        return result;
    }

    public static Long[] convertToLongArray(ArrayList<Long> longs) {
        Long[] result = new Long[longs.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = longs.get(i);
        }
        return result;
    }
}
