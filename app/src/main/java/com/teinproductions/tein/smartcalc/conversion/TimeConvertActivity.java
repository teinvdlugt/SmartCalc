package com.teinproductions.tein.smartcalc.conversion;


import android.widget.ArrayAdapter;

import com.teinproductions.tein.smartcalc.R;
import com.teinproductions.tein.smartcalc.Units;

public class TimeConvertActivity extends ConvertActivity {


    @Override
    protected int contentView() {
        return R.layout.activity_convert1;
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner1.setSelection(3, false);
        spinner2.setSelection(4, false);
    }

    @Override
    protected Double convert() {
        Units.Time time1 = Units.Time.values()[spinner1.getSelectedItemPosition()];
        Units.Time time2 = Units.Time.values()[spinner2.getSelectedItemPosition()];
        if (input1 != null && input2 == null) {
            return time1.convertTo(time2, input1);
        } else if (input1 == null && input2 != null) {
            return time2.convertTo(time1, input2);
        }

        return 0.0;
    }
}
