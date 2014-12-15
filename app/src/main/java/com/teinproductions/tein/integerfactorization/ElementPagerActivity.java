package com.teinproductions.tein.integerfactorization;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ElementPagerActivity extends ActionBarActivity
        implements CalculateFragment.OnCalculateClickListener {

    private ViewPager theViewPager;
    private SlidingTabLayout slidingTabLayout;

    private DrawerLayout drawerLayout;
    private ListView drawerListView;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_pager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        theViewPager = (ViewPager) findViewById(R.id.view_pager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListView = (ListView) findViewById(R.id.drawer_listView);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                null,
                R.string.xs_drawer_open,
                R.string.xs_drawer_close
        );
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        final String[] elementNames = new String[Element.values().length];
        for (int i = 0; i < Element.values().length; i++) {
            elementNames[i] = Element.values()[i].getName(this);
        }
        drawerListView.setAdapter(new ElementListAdapter(this, elementNames));
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(drawerListView);
                theViewPager.setCurrentItem(position, true);
            }
        });
        drawerListView.setBackgroundColor(getResources().getColor(android.R.color.background_dark));

        setUpViewPagerAndSlidingTabLayout();
    }

    private void setUpViewPagerAndSlidingTabLayout() {
        theViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return Element.values()[position].toFragment();
            }

            @Override
            public int getCount() {
                return Element.values().length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return Element.values()[position].getName(ElementPagerActivity.this);
            }
        });

        theViewPager.setCurrentItem(0);

        slidingTabLayout.setViewPager(theViewPager);
        slidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        slidingTabLayout.setBackground(new ColorDrawable(R.color.molu_colorPrimaryDark));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.element_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pager_activity_show_list_action:
                if (drawerLayout.isDrawerOpen(drawerListView)) {
                    drawerLayout.closeDrawer(drawerListView);
                } else {
                    drawerLayout.openDrawer(drawerListView);
                }
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (theViewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            theViewPager.setCurrentItem(0);
        }
    }

    @Override
    public Element onRequestElement() {
        return Element.values()[theViewPager.getCurrentItem()];
    }
}