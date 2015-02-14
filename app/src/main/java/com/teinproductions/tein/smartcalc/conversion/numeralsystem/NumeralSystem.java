package com.teinproductions.tein.smartcalc.conversion.numeralsystem;

import android.content.Context;
import android.util.Log;

import com.teinproductions.tein.smartcalc.ArrayChecker;
import com.teinproductions.tein.smartcalc.R;

import java.math.BigInteger;
import java.util.Arrays;

public class NumeralSystem {

    private String name;
    private int nameID;
    private char[] chars;
    private final boolean editable;
    private boolean visible = true;

    public NumeralSystem(String name, char[] chars, boolean editable, boolean visible) {
        this.name = name;
        this.chars = chars;
        this.editable = editable;
        this.visible = visible;
    }

    public NumeralSystem(int nameID, char[] chars, boolean editable, boolean visible) {
        this.nameID = nameID;
        this.chars = chars;
        this.editable = editable;
        this.visible = visible;
    }


    public static final NumeralSystem DEC = new NumeralSystem(R.string.decimal,
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, false, true);
    public static final NumeralSystem BIN = new NumeralSystem(R.string.binary,
            new char[]{'0', '1'}, false, true);
    public static final NumeralSystem OCT = new NumeralSystem(R.string.octal,
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7'}, false, true);
    public static final NumeralSystem NOV = new NumeralSystem(R.string.nonary,
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8'}, false, true);
    public static final NumeralSystem DUODEC = new NumeralSystem(R.string.duodecimal,
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B'}, false, true);
    public static final NumeralSystem HEX = new NumeralSystem(R.string.hexadecimal,
            new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}, false, true);

    public static NumeralSystem[] preloaded() {
        return new NumeralSystem[]{DEC, BIN, OCT, NOV, DUODEC, HEX};
    }


    public String getName(Context context) {
        if (name != null) {
            return name;
        } else {
            return context.getString(nameID);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public int getNameID() {
        return nameID;
    }


    public long convertToDec(String number) {
        long result = 0;
        char[] charsToConvert = number.toCharArray();

        for (int i = 0; i < charsToConvert.length; i++) {
            result += (index(charsToConvert[i])) * Math.pow(chars.length, number.length() - i - 1);
        }

        return result;
    }

    /*public BigInteger convertToBigInt(String number) { TODO
        BigInteger result = new BigInteger("0");
        char[] charsToConvert = number.toCharArray();

        for (int i = 0; i < charsToConvert.length; i++) {
            double toAdd = index(charsToConvert[i]) * Math.pow(chars.length, number.length() - i - 1);
            int floor = (int) Math.floor(toAdd);
            BigInteger floorBigInt = new BigInteger(Integer.toString(floor));
            result = result.add(floorBigInt);
        }

        return result;
    }*/

    @SuppressWarnings("ConstantConditions")
    public String convertFromDec(long dec) {
        StringBuilder sb = new StringBuilder("");

        if (dec == 0) {
            return Character.toString(chars[0]);
        }

        int maxPow = 0;
        while (1 + 1 == 2) {
            maxPow++;
            if (Math.pow(chars.length, maxPow) > dec) {
                maxPow--;
                break;
            }
        }

        while (maxPow >= 0) {
            int maxMult = 0;
            while (1 + 1 == 2) {
                maxMult++;
                if (Math.pow(chars.length, maxPow) * maxMult > dec) {
                    maxMult--;
                    break;
                }
            }

            sb.append(chars[maxMult]);
            dec -= Math.pow(chars.length, maxPow) * maxMult;

            maxPow--;
        }

        return sb.toString();
    }

    /*@SuppressWarnings("ConstantConditions") TODO
    public String convertFromBigInt(BigInteger bigInt) {
        StringBuilder sb = new StringBuilder("");

        if (bigInt.toString().equals("0")) {
            return Character.toString(chars[0]);
        }

        int maxPow = 0;
        while (1 + 1 == 2) {
            maxPow++;

            // if chars.length ^ maxPow > bigInt
            if (new BigInteger(Integer.toString(chars.length)).pow(maxPow).subtract(bigInt).doubleValue() > 0) {
                maxPow--;
                break;
            }
        }
        Log.d("DEBUG", "maxPow: " + maxPow);

        while (maxPow >= 0) {
            int maxMult = 0;
            while (1 + 1 == 2) {
                maxMult++;

                // if maxMult * chars.length ^ maxPow > bigInt
                if (new BigInteger(
                        Integer.toString(chars.length))
                        .pow(maxPow)
                        .multiply(new BigInteger(Integer.toString(maxMult)))
                        .subtract(bigInt).doubleValue()
                        > 0) {
                    maxMult--;
                    break;
                }
            }

            Log.d("DEBUG", "maxMult: " + maxMult);

            sb.append(chars[maxMult]);
            bigInt = bigInt.subtract(
                    new BigInteger(
                            Integer.toString((int) Math.floor(
                                    Math.pow(chars.length, maxPow) * maxMult))));

            maxPow--;
        }

        return sb.toString();
    }*/

    public int index(char charToCheck) {
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == charToCheck) {
                return i;
            }
        }
        return -1;
    }

    public boolean isValidNumber(String stringToCheck) {
        if (stringToCheck.length() < 1) {
            return false;
        }
        for (char charToCheck : stringToCheck.toCharArray()) {
            if (!ArrayChecker.containsChar(chars, charToCheck)) {
                return false;
            }
        }

        return true;
    }

    public String removeInvalidCharacters(String stringToCheck) {
        char[] charArray = stringToCheck.toCharArray();
        for (char character : charArray) {
            if (!ArrayChecker.containsChar(chars, character)) {
                stringToCheck = stringToCheck.replace(Character.toString(character), "");
            }
        }
        return stringToCheck;
    }

    public static boolean contains(NumeralSystem[] systems, NumeralSystem system, Context context) {
        for (NumeralSystem sys : systems) {
            if (sys.getName(context).equals(system.getName(context)) &&
                    Arrays.equals(sys.getChars(), system.getChars())) {
                return true;
            }
        }
        return false;
    }

    public String correctCases(String s) {
        char[] charArray = s.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (ArrayChecker.containsChar(chars, charArray[i])) continue;

            final char otherCase = toOtherCase(charArray[i]);
            if (ArrayChecker.containsChar(chars, otherCase)) charArray[i] = otherCase;
        }

        return String.valueOf(charArray);
    }

    public static char toOtherCase(char c) {
        if (Character.isLowerCase(c)) return Character.toUpperCase(c);
        if (Character.isUpperCase(c)) return Character.toLowerCase(c);
        return c;
    }


    public static boolean isValidCharArray(char[] chars) {
        if (chars.length < 2) {
            return false;
        }
        for (char character : chars) {
            if (ArrayChecker.occursNotOrOnce(chars, character)) continue;
            return false;
        }
        return true;
    }
}