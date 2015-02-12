package com.teinproductions.tein.smartcalc.chemistry.molu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.teinproductions.tein.smartcalc.R;

public class ElementRecyclerActivity extends ActionBarActivity
        implements RecyclerAdapter.OnRecyclerItemClickListener {

    LinearLayout root;
    Toolbar toolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpRoot();
        setUpToolbar();
        setUpRecyclerView();

        setContentView(root);
    }

    private void setUpRoot() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setWeightSum(1f);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void setUpToolbar() {
        toolbar = new Toolbar(this);
        toolbar.setBackgroundResource(R.color.molu_colorPrimary);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setElevation(4);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setSupportActionBar(toolbar);
        root.addView(toolbar);
    }

    private void setUpRecyclerView() {
        recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(Element.values(), this));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        params.weight = 1f;
        root.addView(recyclerView);
    }

    @Override
    public void onItemClick(RecyclerAdapter recyclerAdapter, int i) {
        Toast.makeText(this, "You clicked on item" + i, Toast.LENGTH_SHORT).show();
        Element clickedElement = recyclerAdapter.getElementAtPosition(i);
    }
}
