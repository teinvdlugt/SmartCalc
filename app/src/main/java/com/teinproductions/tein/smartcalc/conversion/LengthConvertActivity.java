package com.teinproductions.tein.smartcalc.conversion;


import android.widget.ArrayAdapter;

import com.teinproductions.tein.smartcalc.R;
import com.teinproductions.tein.smartcalc.Units;

public class LengthConvertActivity extends ConvertActivity {

    @Override
    protected int contentView() {
        return R.layout.activity_convert1;
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        // "false" is so that the onSelectedItemListener is not triggered
        spinner1.setSelection(5, false);
        spinner2.setSelection(9, false);
    }

    @Override
    protected Double convert() {
        Units.Length length1 = Units.Length.values()[spinner1.getSelectedItemPosition()];
        Units.Length length2 = Units.Length.values()[spinner2.getSelectedItemPosition()];
        if (input1 != null && input2 == null) {
            return length1.convertTo(length2, input1);
        } else if (input1 == null && input2 != null) {
            return length2.convertTo(length1, input2);
        }

        return 0.0;
    }
}
