package com.teinproductions.tein.integerfactorization;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ParticlePagerActivity extends ActionBarActivity
        implements CalculateFragment.OnCalculateClickListener,
        CalculateFragment.MassViewHider {

    public static final int CUSTOM_PARTICLES_ACTIVITY_REQUEST_CODE = 1;

    private ViewPager theViewPager;
    private SlidingTabLayout slidingTabLayout;

    private DrawerLayout drawerLayout;
    private ListView drawerListView;

    private Particle[] particles;

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
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                null,
                R.string.xs_drawer_open,
                R.string.xs_drawer_close
        );
        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        loadParticles();

        drawerListView.setAdapter(new ParticleListAdapter(this, particles));
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawer(drawerListView);
                theViewPager.setCurrentItem(position, true);
            }
        });
        drawerListView.setBackgroundColor(getResources().getColor(android.R.color.background_dark));

        setUpViewPagerAndSlidingTabLayout();
        theViewPager.setCurrentItem(0);
    }

    private void setUpViewPagerAndSlidingTabLayout() {
        theViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return particles[position].toFragment();
            }

            @Override
            public int getCount() {
                return particles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                if (particles[position].getName() == null) {
                    return "";
                }
                return particles[position].getName();
            }
        });

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

    private void loadParticles() {
        ElementAdapter[] elementAdapters = ElementAdapter.values(this);

        String jsonString = getFile();

        if (jsonString == null) {
            particles = elementAdapters;
        } else {
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("particles");
                CustomParticle[] customParticles = CustomParticle.arrayFromJSON(jsonArray);

                particles = new Particle[elementAdapters.length + customParticles.length];
                System.arraycopy(customParticles, 0, particles, 0, customParticles.length);
                System.arraycopy(elementAdapters, 0, particles, customParticles.length, elementAdapters.length);
            } catch (JSONException e) {
                e.printStackTrace();
                particles = elementAdapters;
            }
        }
    }

    private String getFile() {
        StringBuilder sb;

        try {
            // Opens a stream so we can read from our local file
            FileInputStream fis = this.openFileInput(CustomParticlesActivity.FILE_NAME);

            // Gets an input stream for reading data
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");

            // Used to read the data in small bytes to minimize system load
            BufferedReader bufferedReader = new BufferedReader(isr);

            // Read the data in bytes until nothing is left to read
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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
            case R.id.pager_activity_edit_button:
                startCustomParticlesActivity();
                return true;
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startCustomParticlesActivity() {
        Intent intent = new Intent(this, CustomParticlesActivity.class);
        startActivityForResult(intent, CUSTOM_PARTICLES_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CUSTOM_PARTICLES_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    loadParticles();
                    setUpViewPagerAndSlidingTabLayout();

                    if (data != null) {
                        theViewPager.setCurrentItem(data.getIntExtra(CustomParticlesActivity.CALCULATE_WITH_THIS_PARTICLE,
                                theViewPager.getCurrentItem()));
                    }
                }
        }
    }

    @Override
    public Particle onRequestParticle() {
        return particles[theViewPager.getCurrentItem()];
    }

    @Override
    public boolean hideMassView() {
        return true;
    }
}