package com.teinproductions.tein.integerfactorization;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CalculateFragment extends Fragment {

    private EditText molEditText, gramEditText, particlesEditText;
    private TextView molarMassTextView;
    private Button calculateButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View theView = inflater.inflate(R.layout.calculate_fragment, container, false);

        molEditText = (EditText) theView.findViewById(R.id.mol_edit_text);
        gramEditText = (EditText) theView.findViewById(R.id.gram_edit_text);
        particlesEditText = (EditText) theView.findViewById(R.id.particles_edit_text);
        molarMassTextView = (TextView) theView.findViewById(R.id.mass_amount_text_view);
        calculateButton = (Button) theView.findViewById(R.id.calculate_button);

        molEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                return actionId == EditorInfo.IME_ACTION_GO && calculateWithMol();
            }
        });

        gramEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                return actionId == EditorInfo.IME_ACTION_GO && calculateWithGram();
            }
        });

        particlesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                return actionId == EditorInfo.IME_ACTION_GO && calculateWithParticles();
            }
        });

        setUpButton();

        return theView;

    }

    private void setUpButton() {

        if (calculateButton != null) {
            calculateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickCalculate();
                }
            });
        }
    }

    private void onClickCalculate() {

        // If a value is given in molEditText:
        if (!String.valueOf(molEditText.getText()).equals("")) {
            calculateWithMol();
        }

        // If a value is given in gramEditText:
        else if (!String.valueOf(gramEditText.getText()).equals("")) {
            calculateWithGram();
        }

        // If a value is given in particlesEditText:
        else if (!String.valueOf(particlesEditText.getText()).equals("")) {
            calculateWithParticles();
        }
    }


    private boolean calculateWithMol() {

        try {
            Element passedElement = onCalculateClickListener.onRequestElement();

            molarMassTextView.setText((new DecimalFormat().format(passedElement.getMass())));

            Double givenMol = Double.parseDouble(String.valueOf(molEditText.getText()));

            Double calculatedGram = passedElement.calculateGramWhenMolGiven(givenMol);
            Double calculatedParticles = passedElement.calculateParticlesWhenMolGiven(givenMol);

            gramEditText.setText(format(calculatedGram));
            particlesEditText.setText(format(calculatedParticles));

            return true;
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getActivity().getFragmentManager());

            return false;
        } catch (NullPointerException e){
            return false;
        }
    }

    private boolean calculateWithGram() {

        try {
            Element passedElement = onCalculateClickListener.onRequestElement();

            molarMassTextView.setText((new DecimalFormat().format(passedElement.getMass())));

            Double givenGram = Double.parseDouble(String.valueOf(gramEditText.getText()));

            Double calculatedMol = passedElement.calculateMolWhenGramGiven(givenGram);
            Double calculatedParticles = passedElement.calculateParticlesWhenMolGiven(calculatedMol);

            molEditText.setText(format(calculatedMol));
            particlesEditText.setText(format(calculatedParticles));

            return true;
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getActivity().getFragmentManager());

            return false;
        } catch (NullPointerException e){
            return false;
        }

    }

    private boolean calculateWithParticles() {

        try {
            Element passedElement = onCalculateClickListener.onRequestElement();

            molarMassTextView.setText((new DecimalFormat().format(passedElement.getMass())));

            Integer givenParticles = Integer.parseInt(String.valueOf(particlesEditText.getText()));

            Double calculatedMol = passedElement.calculateMolWhenParticlesGiven(givenParticles);
            Double calculatedGram = passedElement.calculateGramWhenMolGiven(calculatedMol);

            molEditText.setText(format(calculatedMol));
            gramEditText.setText(format(calculatedGram));

            return true;
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getActivity().getFragmentManager());

            return false;
        } catch (NullPointerException e){
            return false;
        }

    }


    public static String format(Double d){

        DecimalFormat formatterBig = new DecimalFormat("0.#####E0");
        DecimalFormat formatterSmall = new DecimalFormat("0.#####");
        DecimalFormat formatterInteger = new DecimalFormat("0");

        if (d == 0){
            return "0";
        } else if (d > 99999 || d < 0.00001) {
            return formatterBig.format(d);
        } else {
            return formatterSmall.format(d);
        }

    }


    private OnCalculateClickListener onCalculateClickListener;

    public interface OnCalculateClickListener {

        public Element onRequestElement();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onCalculateClickListener = (OnCalculateClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCalculateClickListener");
        }
    }
}