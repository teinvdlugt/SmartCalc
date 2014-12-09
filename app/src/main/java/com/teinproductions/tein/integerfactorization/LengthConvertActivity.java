package com.teinproductions.tein.integerfactorization;


import android.widget.ArrayAdapter;

public class LengthConvertActivity extends ConvertActivity {

    @Override
    protected int contentView() {
        return R.layout.activity_convert1;
    }

    @Override
    protected void setAdapters() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                Units.Length.getAbbreviations(this));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner1.setSelection(2);
        spinner2.setSelection(6);
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

        return null;
    }
}
