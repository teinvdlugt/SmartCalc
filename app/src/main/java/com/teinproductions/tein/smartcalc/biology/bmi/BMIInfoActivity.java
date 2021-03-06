package com.teinproductions.tein.smartcalc.biology.bmi;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

public class BMIInfoActivity extends AppCompatActivity {

    private LinearLayout rootLayout;

    private Integer backgroundColorID;
    private Integer backgroundColorActionBarID;
    private Integer backgroundColorStatusBarID;

    public static final String BACKGROUND_COLOR_ID = "com.teinproductions.tein.integerfactorization.BACKGROUND_COLOR_ID";
    public static final String BACKGROUND_COLOR_ACTION_BAR_ID = "com.teinproductions.tein.integerfactorization.BACKGROUND_COLOR_ACTION_BAR_ID";
    public static final String BACKGROUND_COLOR_STATUS_BAR_ID = "com.teinproductions.tein.integerfactorization.BACKGROUND_COLOR_STATUS_BAR_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BMIInfoAdapter(this));

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);

        if (getIntent().getExtras() != null) {
            Integer backgroundColorID2 = getIntent().getExtras().getInt(BACKGROUND_COLOR_ID, -1);
            Integer backgroundColorActionBarID2 = getIntent().getExtras().getInt(BACKGROUND_COLOR_ACTION_BAR_ID, -1);
            Integer backgroundColorStatusBarID2 = getIntent().getExtras().getInt(BACKGROUND_COLOR_STATUS_BAR_ID, -1);

            if (backgroundColorID2 != -1 && backgroundColorActionBarID2 != -1 && backgroundColorStatusBarID2 != -1) {
                backgroundColorID = backgroundColorID2;
                backgroundColorActionBarID = backgroundColorActionBarID2;
                backgroundColorStatusBarID = backgroundColorStatusBarID2;

                rootLayout.setBackgroundColor(getResources().getColor(backgroundColorID2));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(backgroundColorActionBarID2)));
                getWindow().setStatusBarColor(getResources().getColor(backgroundColorStatusBarID2));
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BMIState state = BMIState.values()[position];
                animateBackgroundColors(state);
            }
        });

    }

    private void animateBackgroundColors(final BMIState state) {
        final Integer colorFrom;
        final Integer colorTo;
        final Integer colorFromActionBar;
        final Integer colorToActionBar;
        final Integer colorFromStatusBar;
        final Integer colorToStatusBar;

        // initialize colorFrom and colorTo
        if (backgroundColorID == null) {
            colorFrom = getResources().getColor(R.color.windowBackground);
        } else {
            colorFrom = getResources().getColor(backgroundColorID);
        }
        backgroundColorID = state.getColorID();
        colorTo = getResources().getColor(backgroundColorID);

        // Initialize colorFromActionBar and colorToActionBar
        if (backgroundColorActionBarID == null) {
            colorFromActionBar = getResources().getColor(R.color.colorPrimary);
        } else {
            colorFromActionBar = getResources().getColor(backgroundColorActionBarID);
        }
        backgroundColorActionBarID = state.getColorActionBarID();
        colorToActionBar = getResources().getColor(backgroundColorActionBarID);

        // Initialize colorFromStatusBar and colorToStatusBar
        if (backgroundColorStatusBarID == null) {
            colorFromStatusBar = getResources().getColor(R.color.colorPrimaryDark);
        } else {
            colorFromStatusBar = getResources().getColor(backgroundColorStatusBarID);
        }
        backgroundColorStatusBarID = state.getColorStatusBarID();
        colorToStatusBar = getResources().getColor(backgroundColorStatusBarID);

        // animate the background color
        BMIState.animateColor(
                getApplicationContext(),
                colorFrom,
                colorTo,
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        rootLayout.setBackgroundColor((Integer) animation.getAnimatedValue());
                    }
                },
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // After that, animate the color of the action bar
                        BMIState.animateColor(
                                getApplicationContext(),
                                colorFromActionBar,
                                colorToActionBar,
                                new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Integer) animation.getAnimatedValue()));
                                    }
                                },
                                new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        // After that, if lollipop or higher, animate the color of the status bar
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            BMIState.animateColor(
                                                    getApplicationContext(),
                                                    colorFromStatusBar,
                                                    colorToStatusBar,
                                                    new ValueAnimator.AnimatorUpdateListener() {
                                                        @Override
                                                        public void onAnimationUpdate(ValueAnimator animation) {
                                                            getWindow().setStatusBarColor((Integer) animation.getAnimatedValue());
                                                        }
                                                    },
                                                    null);
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class BMIInfoAdapter extends ArrayAdapter<String> {

        public BMIInfoAdapter(Context context) {
            super(context, R.layout.list_item_bmi_info, BMIState.getNames(context));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View theView = inflater.inflate(R.layout.list_item_bmi_info, parent, false);

            TextView name = (TextView) theView.findViewById(R.id.state_name);
            TextView value = (TextView) theView.findViewById(R.id.state_value);

            name.setText(BMIState.values()[position].getName(getContext()));
            value.setText(BMIState.values()[position].getValue(getContext()));

            return theView;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the background colors
        try {
            outState.putInt(BACKGROUND_COLOR_ID, backgroundColorID);
            outState.putInt(BACKGROUND_COLOR_ACTION_BAR_ID, backgroundColorActionBarID);
            outState.putInt(BACKGROUND_COLOR_STATUS_BAR_ID, backgroundColorStatusBarID);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Set the background colors
        try {
            backgroundColorID = savedInstanceState.getInt(BACKGROUND_COLOR_ID);
            backgroundColorActionBarID = savedInstanceState.getInt(BACKGROUND_COLOR_ACTION_BAR_ID);
            backgroundColorStatusBarID = savedInstanceState.getInt(BACKGROUND_COLOR_STATUS_BAR_ID);

            rootLayout.setBackgroundColor(getResources().getColor(backgroundColorID));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(backgroundColorActionBarID)));
            getWindow().setStatusBarColor(getResources().getColor(backgroundColorStatusBarID));
        } catch (NullPointerException ignored) {
        }

    }
}
