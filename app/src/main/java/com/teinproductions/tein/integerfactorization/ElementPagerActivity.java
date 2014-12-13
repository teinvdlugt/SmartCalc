package com.teinproductions.tein.integerfactorization;

import android.graphics.drawable.ColorDrawable;
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
        CalculateFragment.OnCalculateClickListener {

    private ViewPager theViewPager;
    private SlidingTabLayout slidingTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_pager);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        theViewPager = (ViewPager) findViewById(R.id.view_pager);

        theViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new ElementListFragment();
                }
                return Element.values()[position - 1].toFragment();
            }

            @Override
            public int getCount() {
                return Element.values().length + 1;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return getString(R.string.action_bar_pager_show_list);
                }
                return Element.values()[position - 1].getName(ElementPagerActivity.this);
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
        menu.findItem(R.id.pager_activity_show_list_action).setVisible(!(theViewPager.getCurrentItem() == 0));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.pager_activity_show_list_action:
                theViewPager.setCurrentItem(0, true);
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
    public void onElementClick(int position) {
        theViewPager.setCurrentItem(position + 1, true);
    }

    @Override
    public Element onRequestElement() {
        return Element.values()[theViewPager.getCurrentItem() - 1];
    }
}