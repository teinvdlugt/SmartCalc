package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.samples.apps.iosched.ui.widget.SlidingTabLayout;
import com.teinproductions.tein.smartcalc.R;


public class ParticlePagerActivity extends ActionBarActivity
        implements CalculateFragment.OnCalculateClickListener,
        RecyclerAdapter.OnRecyclerItemClickListener {

    public static final int CUSTOM_PARTICLES_ACTIVITY_REQUEST_CODE = 1;
    public static final String RELOAD_PARTICLES = "com.teinproductions.tein.smartcalc.RELOAD_PARTICLES";
    public static final String GO_TO_PARTICLE = "com.teinproductions.tein.smartcalc.GO_TO_PARTICLE";

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private ViewPager theViewPager;
    private SlidingTabLayout slidingTabLayout;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;

    private Particle[] particles;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_pager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeViews();

        // drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        loadParticles();

        setDrawerToggle();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(particles, this));
        recyclerView.setBackgroundResource(R.color.background_material_light);

        slidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.molu_colorAccent));
        slidingTabLayout.setBackgroundColor(getResources().getColor(R.color.molu_colorPrimary));
        slidingTabLayout.setCustomTabView(R.layout.tab, R.id.text_view);

        setUpViewPagerAndSlidingTabLayout();
        theViewPager.setCurrentItem(0);
    }

    private void initializeViews() {
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        theViewPager = (ViewPager) findViewById(R.id.view_pager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        recyclerView = (RecyclerView) findViewById(R.id.drawer_recyclerView);
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
    }

    private void setDrawerToggle() {
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.xs_drawer_open,
                R.string.xs_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(toggle);
    }

    private void loadParticles() {
        ElementAdapter[] elementAdapters = ElementAdapter.values(this);

        if (dbManager == null) {
            dbManager = new DatabaseManager(this);
        }

        CustomParticle[] customParticles = dbManager.getParticles();

        particles = new Particle[elementAdapters.length + customParticles.length];
        System.arraycopy(customParticles, 0, particles, 0, customParticles.length);
        System.arraycopy(elementAdapters, 0, particles, customParticles.length, elementAdapters.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.particle_pager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.edit:
                startCustomParticlesActivity();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private void startCustomParticlesActivity() {
        Intent intent = new Intent(this, CustomParticlesActivity.class);
        startActivityForResult(intent, CUSTOM_PARTICLES_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CUSTOM_PARTICLES_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getBooleanExtra(RELOAD_PARTICLES, false)) {
                loadParticles();
                setUpViewPagerAndSlidingTabLayout();
                recyclerView.swapAdapter(new RecyclerAdapter(particles, this), true);
            }

            int goToPos = data.getIntExtra(GO_TO_PARTICLE, -1);
            if (goToPos != -1) {
                theViewPager.setCurrentItem(goToPos);
            }

            drawerLayout.closeDrawers();
        }
    }

    @Override
    public Particle onRequestParticle() {
        return particles[theViewPager.getCurrentItem()];
    }

    @Override
    public void onItemClick(RecyclerAdapter recyclerAdapter, int i) {
        drawerLayout.closeDrawer(recyclerView);
        theViewPager.setCurrentItem(i, true);
    }
}