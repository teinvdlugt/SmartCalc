package com.teinproductions.tein.integerfactorization;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class XOrEncoderActivity extends ActionBarActivity {

    private EditText plainText, keyText, ciphertext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_or_encoder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        plainText = (EditText) findViewById(R.id.plain_text);
        keyText = (EditText) findViewById(R.id.key);
        ciphertext = (EditText) findViewById(R.id.ciphertext);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.x_or_encoder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case android.R.id.copy:
                copyToClipboard();
            default:
                return false;
        }
    }

    public void onClickEncode(View view) {
        String message = plainText.getText().toString();
        String key = keyText.getText().toString();

        if (message.equals("")) {
            CustomDialog.newInstance(
                    R.string.no_plain_text_given_dialog_title,
                    R.string.no_plain_text_given_dialog_message)
                    .show(getFragmentManager(), "NO_PLAIN_TEXT_GIVEN_DIALOG");
            return;
        } else if (key.equals("")) {
            CustomDialog.newInstance(
                    R.string.no_key_given_dialog_title,
                    R.string.no_key_given_dialog_message)
                    .show(getFragmentManager(), "NO_KEY_GIVEN_DIALOG");
            return;
        }

        try {
            ciphertext.setText(encode(message, key));
        } catch (Exception e) {
            e.printStackTrace();
            CustomDialog.newInstance(
                    R.string.error,
                    R.string.encoding_error_dialog_message)
                    .show(getFragmentManager(), "ENCODING_ERROR_DIALOG");
        }
    }

    public void onClickDecode(View view) {
        String cipher = ciphertext.getText().toString();
        String key = keyText.getText().toString();

        if (cipher.equals("")) {
            CustomDialog.newInstance(
                    R.string.no_ciphertext_given_dialog_title,
                    R.string.no_ciphertext_given_dialog_message)
                    .show(getFragmentManager(), "NO_PLAIN_TEXT_GIVEN_DIALOG");
            return;
        } else if (key.equals("")) {
            CustomDialog.newInstance(
                    R.string.no_key_given_dialog_title,
                    R.string.no_key_given_dialog_message2)
                    .show(getFragmentManager(), "NO_KEY_GIVEN_DIALOG");
            return;
        }

        try {
            plainText.setText(decode(cipher, key));
        } catch (Exception e) {
            e.printStackTrace();
            CustomDialog.newInstance(
                    R.string.error,
                    R.string.decoding_error_dialog_message)
                    .show(getFragmentManager(), "ENCODING_ERROR_DIALOG");
        }
    }

    public static String encode(String plainText, String key) {
        char[] plainChars = plainText.toCharArray();
        char[] keyChars = key.toCharArray();
        char[] cipherChars = new char[plainChars.length];

        // If the key is not long enough, repeat it until it is long enough.
        while (keyChars.length < plainChars.length) {
            ArrayList<Character> keyChars2 = new ArrayList<>();
            for (char character : keyChars) {
                keyChars2.add(character);
            }
            for (char character : key.toCharArray()) {
                keyChars2.add(character);
            }
            keyChars = arrayListToCharArray(keyChars2);
        }

        for (int i = 0; i < plainChars.length; i++) {
            cipherChars[i] = xOr(plainChars[i], keyChars[i]);
        }
        return String.valueOf(cipherChars);
    }

    public static String decode(String ciphertext, String key) {
        char[] cipherChars = ciphertext.toCharArray();
        char[] keyChars = key.toCharArray();
        char[] plainChars = new char[cipherChars.length];

        // If the key is not long enough, repeat it until it is long enough.
        while (keyChars.length < cipherChars.length) {
            ArrayList<Character> keyChars2 = new ArrayList<>();
            for (char character : keyChars) {
                keyChars2.add(character);
            }
            for (char character : key.toCharArray()) {
                keyChars2.add(character);
            }
            keyChars = arrayListToCharArray(keyChars2);
        }

        for (int i = 0; i < cipherChars.length; i++) {
            plainChars[i] = xOr(cipherChars[i], keyChars[i]);
        }
        return String.valueOf(plainChars);
    }

    public static char xOr(char arg0, char arg1) {
        return (char) (arg0 ^ arg1);
    }

    public void copyToClipboard() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Ciphertext", ciphertext.getText().toString());
        clipboard.setPrimaryClip(clip);
    }

    public static char[] arrayListToCharArray(ArrayList<Character> arrayList) {
        char[] result = new char[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            result[i] = arrayList.get(i);
        }
        return result;
    }

}