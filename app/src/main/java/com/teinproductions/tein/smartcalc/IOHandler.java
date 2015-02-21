package com.teinproductions.tein.smartcalc;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.teinproductions.tein.smartcalc.chemistry.molu.CustomParticle;
import com.teinproductions.tein.smartcalc.chemistry.molu.CustomParticlesActivity;
import com.teinproductions.tein.smartcalc.conversion.numeralsystem.NumeralSystem;
import com.teinproductions.tein.smartcalc.conversion.numeralsystem.NumeralSystemConvertActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class IOHandler {

    public static final String NS_SYSTEMS = "systems";
    public static final String NS_NAME = "name";
    public static final String NS_NAME_ID = "nameID";
    public static final String NS_CHARS = "chars";
    public static final String NS_EDITABLE = "editable";
    public static final String NS_VISIBLE = "visible";

    public static void save(Context context, NumeralSystem[] systems) {
        String jsonString = arrayToJSON(systems);

        final String FILE_NAME = NumeralSystemConvertActivity.FILE_NAME;

        try {
            FileOutputStream outputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            outputStream.write(jsonString.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to save system", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openWebPage(Context context, String URL) {
        if (URLUtil.isValidUrl(URL)) {
            Uri webPage = Uri.parse(URL);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);

            PackageManager packageManager = context.getPackageManager();
            List activities = packageManager.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
            boolean isIntentSafe = activities.size() > 0;

            if (isIntentSafe) {
                context.startActivity(webIntent);
            }
        }
    }


    public static String getFile(Context context, String fileName) {
        StringBuilder sb;

        try {
            // Opens a stream so we can read from our local file
            FileInputStream fis = context.openFileInput(fileName);

            // Gets an input stream for reading data
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            // Used to read the data in small bytes to minimize system load
            BufferedReader bufferedReader = new BufferedReader(isr);

            // Read the data in bytes until nothing is left to read
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static NumeralSystem numeralSystemFromJSON(JSONObject jObject) {
        try {
            char[] chars = getCharsFromJSON(jObject.getJSONArray(NS_CHARS));
            boolean editable = jObject.getBoolean(NS_EDITABLE);
            boolean visible = jObject.getBoolean(NS_VISIBLE);

            if (!jObject.isNull(NS_NAME_ID)) {
                return new NumeralSystem(jObject.getInt(NS_NAME_ID), chars, editable, visible);
            } else if (!jObject.isNull(NS_NAME)) {
                return new NumeralSystem(jObject.getString(NS_NAME), chars, editable, visible);
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

    public static NumeralSystem[] numeralSystemArrayFromJSON(JSONArray jArray) {
        NumeralSystem[] array = new NumeralSystem[jArray.length()];

        for (int i = 0; i < array.length; i++) {
            try {
                array[i] = numeralSystemFromJSON(jArray.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return array;
    }

    public static String toJSON(NumeralSystem system) {
        final String NAMEQuotes;
        final String CHARSQuotes = "\"" + NS_CHARS + "\"";
        final String EDITABLEQuotes = "\"" + NS_EDITABLE + "\"";
        final String VISIBLEQuotes = "\"" + NS_VISIBLE + "\"";

        final String nameJSON;
        final String name = system.getName();
        final int nameID = system.getNameID();
        if (name != null) {
            nameJSON = "\"" + name + "\"";
            NAMEQuotes = "\"" + NS_NAME + "\"";
        } else {
            nameJSON = Integer.toString(nameID);
            NAMEQuotes = "\"" + NS_NAME_ID + "\"";
        }

        final String charsJSON = charsToJSON(system.getChars());
        final String editableJSON = system.isEditable() ? "true" : "false";
        final String visibleJSON = system.isVisible() ? "true" : "false";

        return "{" + NAMEQuotes + ":" + nameJSON + "," + CHARSQuotes + ":" + charsJSON + "," +
                EDITABLEQuotes + ":" + editableJSON + "," + VISIBLEQuotes + ":" + visibleJSON + "}";
    }

    public static String charsToJSON(char[] chars) {
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
        StringBuilder sb = new StringBuilder("{\"" + NS_SYSTEMS + "\":[");
        for (NumeralSystem system : array) {
            sb.append(toJSON(system));
        }
        return sb.append("]}").toString().replace("}{", "},{");
    }
}
