package com.teinproductions.tein.integerfactorization;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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

    LinearLayout rootLayout;
    Integer backgroundColor;
    Integer animDuration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] names = getResources().getStringArray(R.array.BMIStateNames);
        String[] values = getResources().getStringArray(R.array.BMIStateValues);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BMIInfoAdapter(this, names, values));

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        animDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BMIActivity.BMIState state = BMIActivity.BMIState.values()[position];
                animateBackgroundColor(state);
            }
        });

    }

    private void animateBackgroundColor(BMIActivity.BMIState state) {
        final Integer colorFrom;
        if (backgroundColor == null) {
            colorFrom = getResources().getColor(android.R.color.white);
        } else {
            colorFrom = backgroundColor;
        }
        final Integer colorTo = getResources().getColor(state.getColor());

        backgroundColor = colorTo;

        final ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        animator.setDuration(animDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rootLayout.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                final ValueAnimator actionBarAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                actionBarAnimator.setDuration(animDuration);
                actionBarAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable((Integer) animation.getAnimatedValue()));
                    }
                });
                actionBarAnimator.start();
            }
        });

        animator.start();

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

        private String[] names;
        private String[] values;

        public BMIInfoAdapter(Context context, String[] names, String[] values) {
            super(context, R.layout.bmi_info_row_layout, names);

            this.names = names;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View theView = inflater.inflate(R.layout.bmi_info_row_layout, parent, false);

            TextView name = (TextView) theView.findViewById(R.id.state_name);
            TextView value = (TextView) theView.findViewById(R.id.state_value);

            name.setText(names[position]);
            value.setText(values[position]);

            return theView;
        }
    }
}
