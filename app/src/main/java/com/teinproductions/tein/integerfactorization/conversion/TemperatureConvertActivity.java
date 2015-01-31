package com.teinproductions.tein.integerfactorization.conversion;

import android.widget.ArrayAdapter;

import com.teinproductions.tein.integerfactorization.R;
import com.teinproductions.tein.integerfactorization.Units;

public class TemperatureConvertActivity extends ConvertActivity {
    @Override
    protected int contentView() {
        return R.layout.activity_convert1;
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Temperature.getAbbreviations(TemperatureConvertActivity.this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner1.setSelection(2, false);
        spinner2.setSelection(1, false);
    }

    @Override
    protected Double convert() {
        Units.Temperature temp1 = Units.Temperature.values()[spinner1.getSelectedItemPosition()];
        Units.Temperature temp2 = Units.Temperature.values()[spinner2.getSelectedItemPosition()];
        if (input1 != null && input2 == null) {
            return temp1.convertTo(temp2, input1);
        } else if (input1 == null && input2 != null) {
            return temp2.convertTo(temp1, input2);
        }

        return 0.0;
    }
}
