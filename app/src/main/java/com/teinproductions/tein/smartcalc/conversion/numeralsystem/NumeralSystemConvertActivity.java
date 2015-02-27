package com.teinproductions.tein.smartcalc.conversion.numeralsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.teinproductions.tein.smartcalc.IOHandler;
import com.teinproductions.tein.smartcalc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class NumeralSystemConvertActivity extends ActionBarActivity
        implements NumeralSystemEditDialog.OnClickListener {

    private NumeralSystem[] systems;
    private NumeralSystemEditText[] editTexts;

    private ScrollView scrollView;
    private LinearLayout container;
    private LinearLayout ll;

    public static final String FILE_NAME = "saved_numeral_systems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ll.setWeightSum(1);

        Toolbar toolbar = (Toolbar) getLayoutInflater().inflate(R.layout.app_bar_dark, ll, false);
        ll.addView(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadEditTexts();

        initializeScrollView();
        initializeLinearLayout();

        loadScreenContent();
        setContentView(ll);
    }

    private void loadEditTexts() {
        String jsonString = IOHandler.getFile(this, FILE_NAME);
        if (jsonString == null) {
            // File didn't exist (yet)
            systems = NumeralSystem.preloaded();

        } else {
            try {
                JSONObject jObject = new JSONObject(jsonString);
                JSONArray jArray = jObject.getJSONArray(IOHandler.NS_SYSTEMS);

                systems = IOHandler.numeralSystemArrayFromJSON(jArray);
                checkForPreloadedSystems();
            } catch (JSONException e) {
                e.printStackTrace();

                systems = NumeralSystem.preloaded();

            }
        }

        editTexts = NumeralSystemEditText.getArrayFromSystems(this, systems);
    }

    private void initializeScrollView() {
        scrollView = new ScrollView(this);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setVerticalScrollBarEnabled(true);
        final int topBottom = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        final int leftRight = getResources().getDimensionPixelOffset(R.dimen.activity_vertical_margin);
        scrollView.setPadding(leftRight, topBottom, leftRight, topBottom);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1f;
        scrollView.setLayoutParams(params);
        ll.addView(scrollView);
    }

    private void initializeLinearLayout() {
        container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.addView(container, ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);
    }

    private void loadScreenContent() {
        container.removeAllViews();

        for (NumeralSystemEditText e : editTexts) {
            if (e.getSystem().isVisible()) {
                e.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                container.addView(e);
            }
        }
    }

    private void checkForPreloadedSystems() {
        NumeralSystem[] preloaded = NumeralSystem.preloaded();
        for (int i = 0; i < preloaded.length; i++) {
            if (NumeralSystem.contains(systems, preloaded[i], this)) {
                continue;
            } else {
                insertAtPos(preloaded[i], i);
            }
        }
    }

    private void insertAtPos(NumeralSystem system, int pos) {
        NumeralSystem[] altered = new NumeralSystem[systems.length + 1];
        int passed = 0; // 0 if false, 1 if true
        for (int i = 0; i < altered.length; i++) {
            if (i == pos) {
                altered[i] = system;
                passed = 1;
            } else {
                altered[i] = systems[i - passed];
            }
        }
    }

    public void clearAllEditTexts() {
        if (editTexts != null) {
            for (NumeralSystemEditText e : editTexts) {
                if (e != null) {
                    e.setSafeText("");
                }
            }
        }
    }

    public void convert(long dec) {
        for (NumeralSystemEditText e : editTexts) {
            if (e.getSystem().isVisible()) {
                e.convertFromDec(dec);
            }
        }
    }

    public void edit(NumeralSystem system) {
        for (int i = 0; i < systems.length; i++) {
            if (system.equals(systems[i])) {
                NumeralSystemEditDialog.show(getSupportFragmentManager(), systems, i);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.numeral_system_convert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add:
                NumeralSystemEditDialog.show(
                        getSupportFragmentManager(),
                        systems,
                        NumeralSystemEditDialog.NEW_SYSTEM);
                return true;
            case R.id.action_visible_systems:
                NumeralSystemVisibleDialog
                        .newInstance(systems)
                        .show(getSupportFragmentManager(), "NumeralSystemVisibleDialog");
            default:
                return false;
        }
    }

    @Override
    public void reload() {
        loadEditTexts();
        loadScreenContent();
    }
}
