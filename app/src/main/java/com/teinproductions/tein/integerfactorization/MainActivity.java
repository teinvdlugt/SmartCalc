package com.teinproductions.tein.integerfactorization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements CalculateFragment.OnCalculateClickListener{

    EditText elementEditText;

    // Dit doe ik om de context en daarmee de Resources beschikbaar te maken voor de enumeration Element:
    public static Context context;

    public static final int ELEMENT_INFO_ACTIVITY = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // De context voor beschikbaarheid van Resources buiten deze class initializen:
        context = this;

        elementEditText = (EditText) findViewById(R.id.edit_text_1);
        elementEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT){
                    Element element = Element.findElementByAbbreviationOrName(String.valueOf(elementEditText.getText()));
                    if(element == null) {
                        if(String.valueOf(elementEditText.getText()).equals("")){
                            CustomDialog.elementEmpty(getFragmentManager());
                        } else if(String.valueOf(elementEditText.getText()).equalsIgnoreCase("Exit") || String.valueOf(elementEditText.getText()).equalsIgnoreCase("I hate you")){
                            finish();
                        } else {
                            CustomDialog.noElement(getFragmentManager());
                        } return false;
                    }
                    elementEditText.setText(element.getName());
                }

                return false;
            }
        });


        CalculateFragment calculateFragment = new CalculateFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, calculateFragment)
                .commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.element_action_bar_item){
            startElementInfoActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void startElementInfoActivity(){

        Intent intent  = new Intent(this,ElementInfoActivity.class);

        startActivityForResult(intent, ELEMENT_INFO_ACTIVITY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data == null){
            return;
        }

        switch(requestCode) {
            case ELEMENT_INFO_ACTIVITY:
                int result = data.getIntExtra(ElementInfoActivity.ELEMENTRESULTINT, 0);

                Element resultElement = Element.values()[result];
                elementEditText.setText(resultElement.getName());
                break;
            default:
                break;
        }



    }

    private Element validateElement(){
        String givenElementAbbreviationOrName = String.valueOf(elementEditText.getText()).trim();

        Element element = Element.findElementByAbbreviationOrName(givenElementAbbreviationOrName);

        if(element == null){
            // If no element name of abbreviation is given:
            if(givenElementAbbreviationOrName.equals("")){
                CustomDialog.elementEmpty(getFragmentManager());

            // If so:
            } else {
                CustomDialog.noElement(getFragmentManager());

            }

            return null;

        }

        elementEditText.setText(Element.findElementByAbbreviationOrName(String.valueOf(elementEditText.getText())).getName());

        return element;

    }

    @Override
    public Element onRequestElement() {

        return validateElement();

    }

    public void openNewInterface(View view) {
        Intent intent = new Intent(this, ElementPagerActivity.class);
        startActivity(intent);
    }
}
