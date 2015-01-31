package com.teinproductions.tein.integerfactorization;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class NumeralSystem {

    private String name;
    private int nameID;
    private char[] chars;
    private final boolean editable;
    private boolean visible = true;

    public static final String SYSTEMS = "systems";
    public static final String NAME = "name";
    public static final String NAME_ID = "nameID";
    public static final String CHARS = "chars";
    public static final String EDITABLE = "editable";
    public static final String VISIBLE = "visible";

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


    public int convertToDec(String number) {
        int result = 0;
        char[] charsToConvert = number.toCharArray();

        for (int i = 0; i < charsToConvert.length; i++) {
            result += (index(charsToConvert[i])) * Math.pow(chars.length, number.length() - i - 1);
        }

        return result;
    }

    @SuppressWarnings("ConstantConditions")
    public String convertFromDec(int dec) {
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

            //sb.append(Integer.toString((int) Math.pow(chars.length, maxPow) * maxMult));
            sb.append(chars[maxMult]);
            dec -= Math.pow(chars.length, maxPow) * maxMult;

            maxPow--;
        }

        return sb.toString();
    }

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
            if (!containsChar(chars, charToCheck)) {
                return false;
            }
        }

        return true;
    }

    public String removeInvalidCharacters(String stringToCheck) {
        char[] charArray = stringToCheck.toCharArray();
        for (char character : charArray) {
            if (!containsChar(chars, character)) {
                stringToCheck = stringToCheck.replace(Character.toString(character), "");
            }
        }
        return stringToCheck;
    }

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
            if (containsChar(chars, charArray[i])) continue;

            final char otherCase = toOtherCase(charArray[i]);
            if (containsChar(chars, otherCase)) charArray[i] = otherCase;
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
            if (occursNotOrOnce(chars, character)) continue;
            return false;
        }
        return true;
    }


    public static NumeralSystem fromJSON(JSONObject jObject) {

        try {
            char[] chars = getCharsFromJSON(jObject.getJSONArray(CHARS));
            boolean editable = jObject.getBoolean(EDITABLE);
            boolean visible = jObject.getBoolean(VISIBLE);

            if (!jObject.isNull(NAME_ID)) {
                return new NumeralSystem(jObject.getInt(NAME_ID), chars, editable, visible);
            } else if (!jObject.isNull(NAME)) {
                return new NumeralSystem(jObject.getString(NAME), chars, editable, visible);
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static char[] getCharsFromJSON(JSONArray jArray) throws JSONException {
        char[] chars = new char[jArray.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = ((String) jArray.get(i)).charAt(0);
        }
        return chars;
    }

    public static NumeralSystem[] arrayFromJSON(JSONArray jArray) {
        NumeralSystem[] array = new NumeralSystem[jArray.length()];

        for (int i = 0; i < array.length; i++) {
            try {
                array[i] = fromJSON(jArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return array;
    }

    public String toJSON() {
        final String NAMEQuotes;
        final String CHARSQuotes = "\"" + CHARS + "\"";
        final String EDITABLEQuotes = "\"" + EDITABLE + "\"";
        final String VISIBLEQuotes = "\"" + VISIBLE + "\"";

        final String nameJSON;
        if (name != null) {
            nameJSON = "\"" + name + "\"";
            NAMEQuotes = "\"" + NAME + "\"";
        } else {
            nameJSON = Integer.toString(nameID);
            NAMEQuotes = "\"" + NAME_ID + "\"";
        }

        final String charsJSON = charsToJSON();
        final String editableJSON = editable ? "true" : "false";
        final String visibleJSON = visible ? "true" : "false";

        return "{" + NAMEQuotes + ":" + nameJSON + "," + CHARSQuotes + ":" + charsJSON + "," +
                EDITABLEQuotes + ":" + editableJSON + "," + VISIBLEQuotes + ":" + visibleJSON + "}";
    }

    public String charsToJSON() {
        StringBuilder sb = new StringBuilder("");
        sb.append("[");
        for (int i = 0; i < chars.length; i++) {
            sb.append("'").append(chars[i]).append("'");
            if (i != chars.length - 1) { // If it isn't the last character
                sb.append(",");
            }
        }
        return sb.append("]").toString();
    }

    public static String arrayToJSON(NumeralSystem[] array) {
        StringBuilder sb = new StringBuilder("{\"" + SYSTEMS + "\":[");
        for (NumeralSystem system : array) {
            sb.append(system.toJSON());
        }
        return sb.append("]}").toString().replace("}{", "},{");
    }
}