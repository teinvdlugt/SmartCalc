package com.teinproductions.tein.smartcalc.computerscience.unixtime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.teinproductions.tein.smartcalc.R;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class UnixTimeActivity extends AppCompatActivity {

    TextView currentTime, countDown;
    Updater updater;
    Button pickDate, pickTime;
    EditText unixTimeET;

    GregorianCalendar date;
    private static final String DATE = "DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unix_time);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentTime = (TextView) findViewById(R.id.current_unix_time);
        countDown = (TextView) findViewById(R.id.countdown);
        pickDate = (Button) findViewById(R.id.pick_date_button);
        pickTime = (Button) findViewById(R.id.pick_time_button);
        unixTimeET = (EditText) findViewById(R.id.unix_time_edit_text);

        unixTimeET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                try {
                    convertFromUnixTimeET();
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        updater = new Updater();
        updater.execute();
    }

    public static int getUnixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public void onClickPickDate(View view) {
        initDate();

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = new GregorianCalendar(
                        year, month, day,
                        date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), date.get(Calendar.SECOND));
                updateButtonTexts();
                fillUnixTimeET();
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void onClickPickTime(View view) {
        initDate();

        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                date = new GregorianCalendar(
                        date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH),
                        hourOfDay, minute);
                updateButtonTexts();
                fillUnixTimeET();
            }
        }, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true).show();
    }

    private void fillUnixTimeET() {
        unixTimeET.setText("" + (int) (date.getTimeInMillis() / 1000));
    }

    private void updateButtonTexts() {
        int year = date.get(Calendar.YEAR), month = date.get(Calendar.MONTH), day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY), minute = date.get(Calendar.MINUTE);

        pickDate.setText(day + " / " + month + " / " + year);
        String minuteStr = Integer.toString(minute).length() == 1 ? "0" + minute : "" + minute;
        pickTime.setText(hour + ":" + minuteStr);
    }

    private void convertFromUnixTimeET() throws NumberFormatException{
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(unixTimeET.getText().toString()));

        int year = c.get(Calendar.YEAR), month = c.get(Calendar.MONTH), day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY), minute = c.get(Calendar.MINUTE), second = c.get(Calendar.SECOND);

        date = new GregorianCalendar(year, month, day, hour, minute, second);

        updateButtonTexts();
    }

    private void initDate() {
        if (date == null) {
            Calendar c = Calendar.getInstance();

            date = new GregorianCalendar(
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH),
                    c.get(Calendar.HOUR_OF_DAY),
                    c.get(Calendar.MINUTE),
                    c.get(Calendar.SECOND));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(DATE, date);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        date = (GregorianCalendar) savedInstanceState.getSerializable(DATE);
        updateButtonTexts();
    }

    @Override
    protected void onDestroy() {
        updater.cancel(true);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    @SuppressWarnings("ConstantConditions")
    class Updater extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while (1 + 1 == 2) {
                if (isCancelled()) return null;

                publishProgress();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isCancelled()) return null;

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            currentTime.setText("" + getUnixTime());
            countDown.setText("" + (Integer.MAX_VALUE - getUnixTime()));
        }
    }
}