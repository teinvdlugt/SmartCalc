package com.teinproductions.tein.integerfactorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ElementPagerActivity extends ActionBarActivity
                    implements ElementListFragment.onElementClickListener,
                               CalculateFragment.OnCalculateClickListener{

    ViewPager theViewPager;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getSupportActionBar();

        theViewPager = new ViewPager(this);
        theViewPager.setId(0x1);

        theViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position == 0){
                    return new ElementListFragment();
                } return Element.values()[position - 1].toFragment();
            }

            @Override
            public int getCount() {
                return Element.values().length + 1;
            }
        });

        theViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                invalidateOptionsMenu();
                if(position == 0){
                    actionBar.setTitle(R.string.Element_info_activity_label);
                    actionBar.selectTab(null);
                } else {
                    actionBar.setTitle(Element.values()[position - 1].getName());
                    actionBar.selectTab(actionBar.getTabAt(position - 1));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        actionBar = actionBar;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener(){

            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                int position = tab.getPosition();
                theViewPager.setCurrentItem(position + 1, true);
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                int position = tab.getPosition();
                theViewPager.setCurrentItem(position + 1, true);
            }
        };

        for (int i = 0; i < Element.values().length; i++){
        actionBar.addTab(
                actionBar.newTab()
                .setText(Element.values()[i].getName())
                .setTabListener(tabListener));
        }

        theViewPager.setCurrentItem(0);
        actionBar.selectTab(null);

        setContentView(theViewPager);

        Intent intent = getIntent();
        if()

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.element_pager, menu);
        menu.findItem(R.id.pager_activity_show_list_action).setVisible(!(theViewPager.getCurrentItem() == 0));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.pager_activity_show_list_action){
            theViewPager.setCurrentItem(0, true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (theViewPager.getCurrentItem() == 0){
            super.onBackPressed();
        } else {
            theViewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onElementClick(int position) {
        theViewPager.setCurrentItem(position + 1, true);
    }

    @Override
    public Element onRequestElement() {
        return Element.values()[theViewPager.getCurrentItem() - 1];
    }
}