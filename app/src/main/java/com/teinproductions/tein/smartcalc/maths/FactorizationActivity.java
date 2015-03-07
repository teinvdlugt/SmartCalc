package com.teinproductions.tein.smartcalc.maths;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.teinproductions.tein.smartcalc.CustomDialog;
import com.teinproductions.tein.smartcalc.EditTextActivity;
import com.teinproductions.tein.smartcalc.R;

import java.util.ArrayList;


public class FactorizationActivity extends EditTextActivity {

    private Long input;
    private FactorizationAsyncTask asyncTask;

    @Override
    protected void doYourStuff(Bundle savedInstanceState) {
        editText1.setHint(getString(R.string.number_to_factorize_hint));
        editText1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        editText1.setImeOptions(EditorInfo.IME_ACTION_GO);
        button.setText(getString(R.string.factorize_button));
        resultDeclaration.setText(getString(R.string.factorize_result_declaration));

        editText2.setVisibility(View.GONE);
        spinner1.setVisibility(View.GONE);
        spinner2.setVisibility(View.GONE);
        resultSpinner.setVisibility(View.GONE);

        clickButtonWhenFilledEditText(editText1);
        saveResultTextViewText = true;

        infoWebPageUri = "http://en.wikipedia.org/wiki/Integer_factorization";
        asyncTask = new FactorizationAsyncTask(this);
    }

    public void onClickButton(View view) {
        try {
            input = Long.parseLong(editText1.getText().toString());
            execute();
        } catch (NumberFormatException e) {
            CustomDialog.invalidNumber(getSupportFragmentManager());

            resultTextView.setText("");
        }
    }

    private void execute() {
        asyncTask.cancel(true);
        asyncTask = new FactorizationAsyncTask(this);
        asyncTask.execute();
    }

    @Override
    public void onBackPressed() {
        asyncTask.cancel(true);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                asyncTask.cancel(true);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        asyncTask.cancel(true);
        super.onDestroy();
    }

    class FactorizationAsyncTask extends AsyncTask<Void, String, Void> {

        private ArrayList<Integer> result;
        private boolean lastThingStillInProgress = true;
        private Context context;

        FactorizationAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                result = new ArrayList<>();
                factorize(input);
                result.clone();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void publishProgress2() {
            String resultString = formatResult(result);
            publishProgress(resultString);
        }

        @Override
        protected void onProgressUpdate(String... text) {
            resultTextView.setText(text[0]);
        }

        private String formatResult(ArrayList<Integer> result) {
            if (result.isEmpty()) {
                return context.getString(R.string.none);
            } else {
                StringBuilder stringBuilder = new StringBuilder("");

                for (int i = 0; i < result.size(); i++) {
                    if (i == 0) {
                        stringBuilder.append(result.get(i).toString());
                    } else {
                        stringBuilder.append(", ").append(result.get(i).toString());
                    }
                }

                return stringBuilder.toString();
            }
        }

        @Override
        protected void onCancelled() {
            if (result != null && !result.isEmpty()) {
                resultTextView.setText(formatResult(result) + "...");
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            resultTextView.setText(formatResult(result));
        }

        @SuppressWarnings("ConstantConditions")
        public void factorize(Long integer) {
            if (integer == 0 || integer == 1) {
                result.clear();
                return;
            }

            Double squareRoot = Math.sqrt(integer);

            Integer i = 2;
            while (1 + 1 == 2) {
                if (isCancelled()) return;

                if (integer == 1) {
                    return;
                }
                if (i > squareRoot && (long) i != integer) {
                    if (lastThingStillInProgress) {
                        if (!result.isEmpty()) {
                            result.remove(result.size() - 1);
                        }
                        lastThingStillInProgress = false;
                    }
                    result.add(Integer.parseInt(integer.toString()));
                    return;
                } else if (integer % i == 0) {
                    if (lastThingStillInProgress) {
                        if (!result.isEmpty()) {
                            result.remove(result.size() - 1);
                        }
                        lastThingStillInProgress = false;
                    }
                    result.add(i);
                    lastThingStillInProgress = false;
                    publishProgress2();
                    integer /= i;
                    squareRoot = Math.sqrt(integer);
                    continue;
                }

                if (lastThingStillInProgress) {
                    // overwrite last item in result
                    try {
                        result.remove(result.size() - 1);
                        result.add(i);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        // if this is the first item in the result arrayList
                        result.add(i);
                    }
                } else {
                    // add item
                    result.add(i);
                    lastThingStillInProgress = true;
                }
                publishProgress2();
                i = findNextPrimeNumber(i);
            }
        }

        @SuppressWarnings("ConstantConditions")
        public Integer findNextPrimeNumber(Integer integer) {

            int i = integer + 1;
            while (1 + 1 == 2) {
                if (isCancelled()) return null;

                if (PrimeCalculator.isPrimeNumber(i, this)) {
                    return i;
                }
                i++;
            }
        }
    }
}