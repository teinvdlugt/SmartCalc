package com.teinproductions.tein.smartcalc;


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
}
