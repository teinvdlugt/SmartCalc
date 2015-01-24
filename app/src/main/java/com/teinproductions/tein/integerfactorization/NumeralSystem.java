package com.teinproductions.tein.integerfactorization;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NumeralSystem {

    private char[] chars;
    private String name;

    public static final String NAME = "name";
    public static final String CHARS = "chars";
    public static final String CHAR_ARRAY = "char_array";

    public NumeralSystem(String name, char[] chars) {
        this.name = name;
        this.chars = chars;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static boolean isValidCharArray(char[] chars) {
        if (chars.length < 1) {
            return false;
        }
        for (char character : chars) {
            if (occursNotOrOnce(chars, character)) {
                return false;
            }
        }
        return true;
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

    public static NumeralSystem fromJSON(JSONObject jObject) {
        String name;
        char[] chars;

        try {
            name = jObject.getString(NAME);
            chars = getCharsFromJSON(jObject.getJSONArray(CHARS));
            return new NumeralSystem(name, chars);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static char[] getCharsFromJSON(JSONArray jArray) throws JSONException {
        char[] chars = new char[jArray.length()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) jArray.get(i);
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
}