package com.teinproductions.tein.integerfactorization;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import java.util.Arrays;

public class MenuActivity extends ActionBarActivity implements MenuFragment.onMenuItemClickListener {

    private String[] mainList;
    private String[] mathsList;
    private String[] physicsList;
        private String[] specialRelativityList;
    private String[] chemistryList;
    private String[] biologyList;
    private String[] converterList;

    private FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragManager = getSupportFragmentManager();
        setContentView(R.layout.activity_menu);

        mainList = new String[]{getString(R.string.maths), getString(R.string.physics), getString(R.string.chemistry), getString(R.string.biology), getString(R.string.converter)};
        mathsList = new String[]{getString(R.string.integer_factorization), getString(R.string.greatest_common_factor), getString(R.string.least_common_multiple),
                getString(R.string.complex_numbers), getString(R.string.x_or_encoder)};
        physicsList = new String[]{getString(R.string.special_relativity), getString(R.string.schwarzchild_radius)};
            specialRelativityList = new String[]{getString(R.string.adding_velocities), getString(R.string.time_dilation), getString(R.string.length_contraction)};
        chemistryList = new String[]{getString(R.string.elements)};
        biologyList = new String[]{getString(R.string.bmi_title)};
        converterList = new String[]{getString(R.string.length), getString(R.string.time), getString(R.string.temperature), getString(R.string.velocity)};

        if (savedInstanceState == null) {
            MenuFragment mainListFragment = MenuFragment.newInstance(mainList);

            fragManager
                    .beginTransaction()
                    .add(R.id.menu_fragment_container, mainListFragment)
                    .commit();
        }

    }

    private void showNewMenu(String[] listOfStrings) {

        MenuFragment mathsListFragment = MenuFragment.newInstance(listOfStrings);

        fragManager
                .beginTransaction()
                .replace(R.id.menu_fragment_container, mathsListFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .commit();

    }

    private void makeIntent(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(String[] strings, int position) {
        if (Arrays.equals(strings, mainList)) {
            switch (position) {
                case 0:
                    showNewMenu(mathsList);
                    break;
                case 1:
                    showNewMenu(physicsList);
                    break;
                case 2:
                    showNewMenu(chemistryList);
                    break;
                case 3:
                    showNewMenu(biologyList);
                    break;
                case 4:
                    showNewMenu(converterList);
                default:
                    break;
            }
        } else if (Arrays.equals(strings, mathsList)) {
            switch (position) {
                case 0:
                    makeIntent(FactorizationActivity.class);
                    break;
                case 1:
                    makeIntent(GCFActivity.class);
                    break;
                case 2:
                    makeIntent(LCMActivity.class);
                    break;
                case 3:
                    makeIntent(ComplexActivity.class);
                    break;
                case 4:
                    makeIntent(XOrEncoderActivity.class);
                    break;
                default:
                    break;
            }
        } else if (Arrays.equals(strings, physicsList)) {
            switch (position) {
                case 0:
                    showNewMenu(specialRelativityList);
                    break;
                case 1:
                    makeIntent(SchwarzschildRadiusActivity.class);
                default:
                    break;
            }
        } else if (Arrays.equals(strings, specialRelativityList)) {
            switch (position) {
                case 0:
                    makeIntent(VelocityAdding.class);
                    break;
                case 1:
                    makeIntent(TimeDilationActivity.class);
                    break;
                case 2:
                    makeIntent(LengthContractionActivity.class);
                default:
                    break;
            }
        } else if (Arrays.equals(strings, chemistryList)) {
            switch (position) {
                case 0:
                    makeIntent(ParticlePagerActivity.class);
                    break;
                default:
                    break;
            }
        } else if (Arrays.equals(strings, biologyList)) {
            switch (position) {
                case 0:
                    makeIntent(BMIActivity.class);
                    break;
                default:
                    break;
            }
        } else if (Arrays.equals(strings, converterList)) {
            switch (position) {
                case 0:
                    makeIntent(LengthConvertActivity.class);
                    break;
                case 1:
                    makeIntent(TimeConvertActivity.class);
                    break;
                case 2:
                    makeIntent(TemperatureConvertActivity.class);
                    break;
                case 3:
                    makeIntent(VelocityConvertActivity.class);
                default:
                    break;
            }
        }
    }
}