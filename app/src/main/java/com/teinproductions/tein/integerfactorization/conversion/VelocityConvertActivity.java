package com.teinproductions.tein.integerfactorization.conversion;

import android.widget.ArrayAdapter;

import com.teinproductions.tein.integerfactorization.R;
import com.teinproductions.tein.integerfactorization.Units;

public class VelocityConvertActivity extends ConvertActivity {

    @Override
    protected int contentView() {
        return R.layout.activity_convert2;
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(getApplicationContext()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Units.Time.getAbbreviations(getApplicationContext()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1b.setAdapter(adapter);
        spinner2b.setAdapter(adapter);

        spinner1.setSelection(Units.Length.KILOMETER.ordinal(), false);
        spinner1b.setSelection(Units.Time.HOUR.ordinal(), false);
        spinner2.setSelection(Units.Length.MILE.ordinal(), false);
        spinner2b.setSelection(Units.Time.HOUR.ordinal(), false);
    }

    @Override
    protected Double convert() {
        Units.Length length1 = Units.Length.values()[spinner1.getSelectedItemPosition()];
        Units.Time time1 = Units.Time.values()[spinner1b.getSelectedItemPosition()];
        Units.Length length2 = Units.Length.values()[spinner2.getSelectedItemPosition()];
        Units.Time time2 = Units.Time.values()[spinner2b.getSelectedItemPosition()];

        Double factor1 = length1.getFactor() / time1.getFactor();
        Double factor2 = length2.getFactor() / time2.getFactor();

        if (input1 != null && input2 == null) {
            return input1 / factor1 * factor2;
        } else if (input1 == null && input2 != null) {
            return input2 / factor2 * factor1;
        }

        return 0.0;
    }
}
