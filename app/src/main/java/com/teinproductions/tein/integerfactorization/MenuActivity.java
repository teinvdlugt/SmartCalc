package com.teinproductions.tein.integerfactorization;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public class MenuActivity extends ActionBarActivity {

    private SmartCalcMenu currentMenu;
    private SmartCalcMenu root;

    public static final String CURRENT_MENU = "com.teinproductions.tein.integerfactorization.CURRENT_MENU";
    public static final String ROOT = "com.teinproductions.tein.integerfactorization.ROOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        root = (SmartCalcMenu) new SmartCalcMenu()
                .add(new SmartCalcMenu(getString(R.string.maths))
                        .add(new SmartCalcMenuItem(getString(R.string.integer_factorization), FactorizationActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.greatest_common_factor), GCFActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.least_common_multiple), LCMActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.complex_numbers), ComplexActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.x_or_encoder), XOrEncoderActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.numeral_systems), NumeralSystemConvertActivity.class)))
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
                        .add(new SmartCalcMenuItem(getString(R.string.velocity), VelocityConvertActivity.class))
                        .add(new SmartCalcMenuItem(getString(R.string.numeral_systems), NumeralSystemConvertActivity.class)));

        if (savedInstanceState == null) {
            MenuFragment mainListFragment = MenuFragment.newInstance(root);
            currentMenu = root;

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.menu_fragment_container, mainListFragment)
                    .commit();
        }
    }

    public void setCurrentMenu(SmartCalcMenu currentMenu) {
        this.currentMenu = currentMenu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (currentMenu != root) {
        	currentMenu = (SmartCalcMenu) currentMenu.getParent();
        	if (currentMenu == root) {
        	    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        	    setTitle(R.string.app_name);
        	} else {
        		setTitle(currentMenu.getName());
        	}
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CURRENT_MENU, currentMenu);
        outState.putSerializable(ROOT, root);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentMenu = (SmartCalcMenu) savedInstanceState.getSerializable(CURRENT_MENU);
        root = (SmartCalcMenu) savedInstanceState.getSerializable(ROOT);

        if (currentMenu != null && currentMenu != root) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle(currentMenu.getName());
        }
    }
}
