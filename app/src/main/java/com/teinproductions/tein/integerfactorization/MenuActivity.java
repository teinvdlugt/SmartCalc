package com.teinproductions.tein.integerfactorization;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        SmartCalcMenu root = (SmartCalcMenu) new SmartCalcMenu()
                .add(new SmartCalcMenu(getString(R.string.maths))
                        .add(new SmartCalcMenuItem(getString(R.string.integer_factorization), FactorizationActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.greatest_common_factor), GCFActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.least_common_multiple), LCMActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.complex_numbers), ComplexActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.x_or_encoder), XOrEncoderActivity.class)))
                        // Physics
                .add(new SmartCalcMenu(getString(R.string.physics))
                        // Special Relativity
                        .add(new SmartCalcMenu(getString(R.string.special_relativity))
                                .add(new SmartCalcMenuItem(getString(R.string.adding_velocities), VelocityAdding.class))
                                .add(new SmartCalcMenuItem(getString(R.string.time_dilation), TimeDilationActivity.class))
                                .add(new SmartCalcMenuItem(getString(R.string.length_contraction), LengthContractionActivity.class)))
                        .add(new SmartCalcMenuItem(getString(R.string.schwarzchild_radius), SchwarzschildRadiusActivity.class)))
                        // Chemistry
                .add(new SmartCalcMenu(getString(R.string.chemistry))
                        .add(new SmartCalcMenuItem(getString(R.string.elements), ParticlePagerActivity.class)))
                        // Biology
                .add(new SmartCalcMenu(getString(R.string.biology))
                        .add(new SmartCalcMenuItem(getString(R.string.bmi_title), BMIActivity.class)))
                        // Converter
                .add(new SmartCalcMenu(getString(R.string.converter))
                        .add(new SmartCalcMenuItem(getString(R.string.length), LengthConvertActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.time), TimeConvertActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.temperature), TemperatureConvertActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.velocity), VelocityConvertActivity.class)));

        if (savedInstanceState == null) {
            MenuFragment mainListFragment = MenuFragment.newInstance(root);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.menu_fragment_container, mainListFragment)
                    .commit();
        }
    }
}