package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class BMIInfoActivity extends ActionBarActivity {

    private LinearLayout rootLayout;

    private Integer backgroundColorID;
    private Integer backgroundColorActionBarID;
    private Integer backgroundColorStatusBarID;

    private static String BACKGROUNDCOLORID = "BACKGROUNDCOLORID";
    private static String BACKGROUNDCOLORACTIONBARID = "BACKGROUNDCOLORACTIONBARID";
    private static String BACKGROUNDCOLORSTATUSBARID = "BACKGROUNDCOLORSTATUSBARID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BMIInfoAdapter(this));

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BMIActivity.BMIState state = BMIActivity.BMIState.values()[position];
                animateBackgroundColors(state);
            }
        });

    }

    private void animateBackgroundColors(final BMIActivity.BMIState state) {
        final Integer colorFrom;
        final Integer colorTo;
        final Integer colorFromActionBar;
        final Integer colorToActionBar;
        final Integer colorFromStatusBar;
        final Integer colorToStatusBar;

        // initialize colorFrom and colorTo
        if (backgroundColorID == null) {
            colorFrom = getResources().getColor(android.R.color.white);
        } else {
            colorFrom = getResources().getColor(backgroundColorID);
        }
        backgroundColorID = state.getColorID();
        colorTo = getResources().getColor(backgroundColorID);

        // Initialize colorFromActionBar and colorToActionBar
        if (backgroundColorActionBarID == null) {
            colorFromActionBar = getResources().getColor(android.R.color.darker_gray);
        } else {
            colorFromActionBar = getResources().getColor(backgroundColorActionBarID);
        }
        backgroundColorActionBarID = state.getColorActionBarID();
        colorToActionBar = getResources().getColor(backgroundColorActionBarID);

        // Initialize colorFromStatusBar and colorToStatusBar
        if (backgroundColorStatusBarID == null) {
            colorFromStatusBar = getResources().getColor(android.R.color.darker_gray);
        } else {
            colorFromStatusBar = getResources().getColor(backgroundColorStatusBarID);
        }
        backgroundColorStatusBarID = state.getColorStatusBarID();
        colorToStatusBar = getResources().getColor(backgroundColorStatusBarID);

        // animate the background color
        BMIActivity.BMIState.animateColor(
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
                        BMIActivity.BMIState.animateColor(
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
                                            BMIActivity.BMIState.animateColor(
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
            super(context, R.layout.bmi_info_row_layout, BMIActivity.BMIState.getNames(context));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View theView = inflater.inflate(R.layout.bmi_info_row_layout, parent, false);

            TextView name = (TextView) theView.findViewById(R.id.state_name);
            TextView value = (TextView) theView.findViewById(R.id.state_value);

            name.setText(BMIActivity.BMIState.values()[position].getName(getContext()));
            value.setText(BMIActivity.BMIState.values()[position].getValue(getContext()));

            return theView;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the background colors
        try {
            outState.putInt(BACKGROUNDCOLORID, backgroundColorID);
            outState.putInt(BACKGROUNDCOLORACTIONBARID, backgroundColorActionBarID);
            outState.putInt(BACKGROUNDCOLORSTATUSBARID, backgroundColorStatusBarID);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Set the background colors
        try {
            backgroundColorID = savedInstanceState.getInt(BACKGROUNDCOLORID);
            backgroundColorActionBarID = savedInstanceState.getInt(BACKGROUNDCOLORACTIONBARID);
            backgroundColorStatusBarID = savedInstanceState.getInt(BACKGROUNDCOLORSTATUSBARID);

            rootLayout.setBackgroundColor(getResources().getColor(backgroundColorID));
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(backgroundColorActionBarID)));
            getWindow().setStatusBarColor(getResources().getColor(backgroundColorStatusBarID));
        } catch (NullPointerException ignored) {
        }

    }
}
