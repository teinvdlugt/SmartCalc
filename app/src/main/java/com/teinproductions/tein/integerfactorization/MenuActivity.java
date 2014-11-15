package com.teinproductions.tein.integerfactorization;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import java.util.Arrays;

public class MenuActivity extends ActionBarActivity implements MenuFragment.onMenuItemClickListener{

    private String[] mainList = {"Maths"};
    private String[] mathsList = {"Integer factorization", "GCF", "LCM"};

    private FragmentManager fragManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragManager = getSupportFragmentManager();

        setContentView(R.layout.activity_menu);

        if (savedInstanceState == null) {
            MenuFragment mainListFragment = MenuFragment.newInstance(mainList);

            fragManager
                    .beginTransaction()
                    .add(R.id.menu_fragment_container, mainListFragment)
                    .commit();

        }

    }

    private void showNewMenu(String[] listOfStrings){

        MenuFragment mathsListFragment = MenuFragment.newInstance(listOfStrings);

        fragManager
                .beginTransaction()
                .replace(R.id.menu_fragment_container, mathsListFragment)
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }

    private void makeIntent(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(String[] strings, int position) {
        if (Arrays.equals(strings, mainList)) {
            switch(position){
                case 0:
                    showNewMenu(mathsList);
                    break;
                default:
                    break;
            }
        } else if (Arrays.equals(strings, mathsList)) {
            switch(position) {
                case 0:
                    makeIntent(FactorizationActivity.class);
                    break;
                case 1:
                    makeIntent(GCFActivity.class);
                default:
                    break;
            }
        }
    }
}
