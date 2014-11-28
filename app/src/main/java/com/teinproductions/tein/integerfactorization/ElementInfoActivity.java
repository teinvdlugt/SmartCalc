package com.teinproductions.tein.integerfactorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ElementInfoActivity extends ActionBarActivity
                    implements ElementListFragment.onElementClickListener{

    public static String ELEMENTRESULTINT = "Index of the element that must be sent back to MainActivity";

    ElementListFragment elementListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        elementListFragment = new ElementListFragment();

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, elementListFragment)
                    .commit();
        }

    }

    private void finishWithResult(int position) {

        Intent goingBack = new Intent();
        goingBack.putExtra(ELEMENTRESULTINT, position);

        setResult(RESULT_OK, goingBack);

        finish();

    }


    @Override
    public void onElementClick(int position) {
        finishWithResult(position);
    }
}
