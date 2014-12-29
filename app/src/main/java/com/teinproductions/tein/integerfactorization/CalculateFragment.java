package com.teinproductions.tein.integerfactorization;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalculateFragment extends Fragment {

    private EditText molEditText, gramEditText, particlesEditText, volumeEditText;
    private TextView molarMassTextView;
    private Spinner volumeSpinner, temperatureSpinner;
    private LinearLayout massLayout, temperatureLayout;
    private Button calculateButton;

    private int animDuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View theView = inflater.inflate(R.layout.calculate_fragment, container, false);

        animDuration = getActivity().getResources().getInteger(android.R.integer.config_shortAnimTime);

        molEditText = (EditText) theView.findViewById(R.id.mol_edit_text);
        gramEditText = (EditText) theView.findViewById(R.id.gram_edit_text);
        particlesEditText = (EditText) theView.findViewById(R.id.particles_edit_text);
        molarMassTextView = (TextView) theView.findViewById(R.id.mass_amount_text_view);
        massLayout = (LinearLayout) theView.findViewById(R.id.mass_layout);
        volumeSpinner = (Spinner) theView.findViewById(R.id.volume_spinner);
        volumeEditText = (EditText) theView.findViewById(R.id.volume_edit_text);
        temperatureSpinner = (Spinner) theView.findViewById(R.id.temperature_spinner);
        temperatureLayout = (LinearLayout) theView.findViewById(R.id.temperature_layout);
        if (massViewHider.hideMassView()) massLayout.setVisibility(View.GONE);
        calculateButton = (Button) theView.findViewById(R.id.calculate_button);

        molEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                return actionId == EditorInfo.IME_ACTION_GO && calculate(false);
            }
        });

        gramEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                return actionId == EditorInfo.IME_ACTION_GO && calculate(false);
            }
        });

        particlesEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                return actionId == EditorInfo.IME_ACTION_GO && calculate(false);
            }
        });

        volumeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return actionId == EditorInfo.IME_ACTION_GO && calculate(false);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                new String[]{getString(R.string.solid_or_liquid), getString(R.string.gas)});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        volumeSpinner.setAdapter(adapter);
        volumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (temperatureLayout.getVisibility() == View.VISIBLE) {
                            temperatureLayout.animate()
                                    .alpha(0f)
                                    .setDuration(animDuration)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            temperatureLayout.setVisibility(View.GONE);
                                        }
                                    });
                        }
                        break;
                    case 1:
                        if (temperatureLayout.getVisibility() == View.GONE) {
                            temperatureLayout.setAlpha(0f);
                            temperatureLayout.setVisibility(View.VISIBLE);
                            temperatureLayout.animate()
                                    .alpha(1f)
                                    .setDuration(animDuration)
                                    .setListener(null);
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        volumeSpinner.setSelection(0);

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                new String[]{"0 °C", "25 °C"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(adapter);
        temperatureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculate(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        temperatureSpinner.setSelection(1, false);

        if (calculateButton != null) {
            calculateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calculate(true);
                }
            });
        }

        return theView;
    }

    public boolean calculate(boolean warn) {
        if (molEditText.hasFocus() && hasValidDecimalInput(molEditText)) {
            return calculateWithMol(warn);
        } else if (gramEditText.hasFocus() && hasValidDecimalInput(gramEditText)) {
            return calculateWithGram(warn);
        } else if (particlesEditText.hasFocus() && hasValidDecimalInput(particlesEditText)) {
            return calculateWithParticles(warn);
        } else if (volumeEditText.hasFocus() && hasValidDecimalInput(volumeEditText)) {
            return calculateWithVolume(warn);
        } else if (hasValidDecimalInput(molEditText)) {
            return calculateWithMol(warn);
        } else if (hasValidDecimalInput(gramEditText)) {
            return calculateWithGram(warn);
        } else if (hasValidDecimalInput(particlesEditText)) {
            return calculateWithParticles(warn);
        } else if (hasValidDecimalInput(volumeEditText)) {
            return calculateWithVolume(warn);
        } else {
            if (warn) {
                CustomDialog.invalidNumber(getActivity().getFragmentManager());
            }
            return false;
        }
    }

    private boolean calculateWithMol(boolean warn) {
        try {
            Object particleOrElement = onCalculateClickListener.onRequestElement();
            boolean notEverythingCalculated = false;

            Double mass = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getMass()
                    : ((Element) particleOrElement).getMass();
            Double density = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getDensity()
                    : ((Element) particleOrElement).getDensity();

            if (mass == null) {
                molarMassTextView.setText(R.string.unknown);
            } else {
                molarMassTextView.setText((new DecimalFormat().format(mass)));
            }

            // Mol
            Double givenMol = Double.parseDouble(String.valueOf(molEditText.getText()));

            // Particles
            Double calculatedParticles = givenMol * Units.nA;
            particlesEditText.setText(format(calculatedParticles));

            // Gram
            Double calculatedGram = null;
            if (mass == null) {
                notEverythingCalculated = true;
            } else {
                calculatedGram = givenMol * mass;
                gramEditText.setText(format(calculatedGram));
            }

            // Volume
            Double calculatedVolume;
            switch (volumeSpinner.getSelectedItemPosition()) {
                case 0:
                    // Solid/liquid
                    if (density == null) {
                        notEverythingCalculated = true;
                    } else if (calculatedGram != null) {
                        calculatedVolume = calculatedGram / density;
                        volumeEditText.setText(format(calculatedVolume));
                    }
                    break;
                case 1:
                    // Gas
                    calculatedVolume = givenMol * getVm();
                    volumeEditText.setText(format(calculatedVolume));
            }

            if (notEverythingCalculated) {
                Toast.makeText(getActivity(), R.string.shortage_of_information_toast_message, Toast.LENGTH_LONG).show();
            }

            return true;
        } catch (NumberFormatException e) {
            if (warn) {
                CustomDialog.invalidNumber(getActivity().getFragmentManager());
            }

            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean calculateWithGram(boolean warn) {
        try {
            Object particleOrElement = onCalculateClickListener.onRequestElement();
            boolean notEverythingCalculated = false;

            Double mass = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getMass()
                    : ((Element) particleOrElement).getMass();
            Double density = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getDensity()
                    : ((Element) particleOrElement).getDensity();

            if (mass == null) {
                molarMassTextView.setText(R.string.unknown);
            } else {
                molarMassTextView.setText((new DecimalFormat().format(mass)));
            }

            // Gram
            Double givenGram = Double.parseDouble(String.valueOf(gramEditText.getText()));

            // Mol and Particles
            Double calculatedMol = null;
            if (mass == null) {
                notEverythingCalculated = true;
            } else {
                calculatedMol = givenGram / mass;
                molEditText.setText(format(calculatedMol));
                Double calculatedParticles = calculatedMol * Units.nA;
                particlesEditText.setText(format(calculatedParticles));
            }

            // Volume
            Double calculatedVolume;
            switch (volumeSpinner.getSelectedItemPosition()) {
                case 0:
                    // Solid/liquid
                    if (density == null) {
                        notEverythingCalculated = true;
                    } else {
                        calculatedVolume = givenGram / density;
                        volumeEditText.setText(format(calculatedVolume));
                    }
                    break;
                case 1:
                    // Gas
                    if (calculatedMol == null) {
                        notEverythingCalculated = true;
                    } else {
                        calculatedVolume = calculatedMol * getVm();
                        volumeEditText.setText(format(calculatedVolume));
                    }
            }

            if (notEverythingCalculated) {
                Toast.makeText(getActivity(), R.string.shortage_of_information_toast_message, Toast.LENGTH_LONG).show();
            }

            return true;
        } catch (NumberFormatException e) {
            if (warn) {
                CustomDialog.invalidNumber(getActivity().getFragmentManager());
            }

            return false;
        } catch (NullPointerException e) {
            Toast.makeText(getActivity(), R.string.shortage_of_information_toast_message, Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private boolean calculateWithParticles(boolean warn) {
        try {
            Object particleOrElement = onCalculateClickListener.onRequestElement();
            boolean notEverythingCalculated = false;

            Double mass = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getMass()
                    : ((Element) particleOrElement).getMass();
            Double density = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getDensity()
                    : ((Element) particleOrElement).getDensity();

            if (mass == null) {
                molarMassTextView.setText(R.string.unknown);
            } else {
                molarMassTextView.setText((new DecimalFormat().format(mass)));
            }

            // Particles
            Integer givenParticles = Integer.parseInt(String.valueOf(particlesEditText.getText()));

            // Mol
            Double calculatedMol = calculatedMol = givenParticles / Units.nA;
            molEditText.setText(format(calculatedMol));

            // Gram
            Double calculatedGram = null;
            if (mass == null) {
                notEverythingCalculated = true;
            } else {
                calculatedGram = calculatedMol * mass;
                gramEditText.setText(format(calculatedGram));
            }

            // Volume
            Double calculatedVolume;
            switch (volumeSpinner.getSelectedItemPosition()) {
                case 0:
                    // Solid/liquid
                    if (density == null) {
                        notEverythingCalculated = true;
                    } else if (calculatedGram != null) {
                        calculatedVolume = calculatedGram / density;
                        volumeEditText.setText(format(calculatedVolume));
                    }
                    break;
                case 1:
                    calculatedVolume = calculatedMol * getVm();
                    volumeEditText.setText(format(calculatedVolume));
            }

            if (notEverythingCalculated) {
                Toast.makeText(getActivity(), R.string.shortage_of_information_toast_message, Toast.LENGTH_LONG).show();
            }

            return true;
        } catch (NumberFormatException e) {
            if (warn) {
                CustomDialog.invalidNumber(getActivity().getFragmentManager());
            }

            return false;
        } catch (NullPointerException e) {
            return false;
        }

    }

    private boolean calculateWithVolume(boolean warn) {
        try {
            Object particleOrElement = onCalculateClickListener.onRequestElement();
            boolean notEverythingCalculated = false;

            Double mass = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getMass()
                    : ((Element) particleOrElement).getMass();
            Double density = particleOrElement instanceof Particle
                    ? ((Particle) particleOrElement).getDensity()
                    : ((Element) particleOrElement).getDensity();

            if (mass == null) {
                molarMassTextView.setText(R.string.unknown);
            } else {
                molarMassTextView.setText((new DecimalFormat().format(mass)));
            }

            Double givenVolume = Double.parseDouble(volumeEditText.getText().toString());
            Double calculatedMol = null, calculatedGram = null, calculatedParticles = null;

            switch (volumeSpinner.getSelectedItemPosition()) {
                case 0:
                    // Solid / liquid

                    // Gram
                    if (density == null) {
                        notEverythingCalculated = true;
                    } else {
                        calculatedGram = givenVolume * density;
                        gramEditText.setText(format(calculatedGram));
                    }

                    // Mol
                    if (mass == null) {
                        notEverythingCalculated = true;
                    } else if (calculatedGram != null) {
                        calculatedMol = calculatedGram / mass;
                        molEditText.setText(format(calculatedMol));

                        // Particles
                        calculatedParticles = calculatedMol * Units.nA;
                        particlesEditText.setText(format(calculatedParticles));
                    }
                    break;
                case 1:
                    // Gas

                    // Mol
                    calculatedMol = givenVolume / getVm();
                    molEditText.setText(format(calculatedMol));

                    // Particles
                    calculatedParticles = calculatedMol * Units.nA;
                    particlesEditText.setText(format(calculatedParticles));

                    // Gram
                    if (mass == null) {
                        notEverythingCalculated = true;
                    } else {
                        calculatedGram = calculatedMol * mass;
                        gramEditText.setText(format(calculatedGram));
                    }
            }

            if (notEverythingCalculated) {
                Toast.makeText(getActivity(), R.string.shortage_of_information_toast_message, Toast.LENGTH_LONG).show();
            }

            return true;
        } catch (NumberFormatException e) {
            if (warn) {
                CustomDialog.invalidNumber(getActivity().getFragmentManager());
            }

            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static String format(Double d) {

        if (d == 0) {
            return "0";
        } else if (d > 99999 || d < 0.00001) {
            return new DecimalFormat("0.#####E0").format(d);
        } else {
            return new DecimalFormat("0.#####").format(d);
        }

    }

    public static boolean hasValidDecimalInput(EditText editText) {
        try {
            Double test = Double.parseDouble(editText.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Double getVm() {
        switch (temperatureSpinner.getSelectedItemPosition()) {
            case 0:
                return 22.414;
            case 1:
                return 24.465;
            default:
                // Will never happen (at least I hope so)
                return null;
        }
    }


    private OnCalculateClickListener onCalculateClickListener;
    private MassViewHider massViewHider;

    public interface OnCalculateClickListener {
        public Object onRequestElement();
    }

    public interface MassViewHider {
        public boolean hideMassView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onCalculateClickListener = (OnCalculateClickListener) activity;
            massViewHider = (MassViewHider) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCalculateClickListener and MassViewHider");
        }
    }
}