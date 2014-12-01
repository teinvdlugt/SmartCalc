package com.teinproductions.tein.integerfactorization;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class BMIInfoActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] names = getResources().getStringArray(R.array.BMIStateNames);
        String[] values = getResources().getStringArray(R.array.BMIStateValues);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new BMIInfoAdapter(this, names, values));
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
