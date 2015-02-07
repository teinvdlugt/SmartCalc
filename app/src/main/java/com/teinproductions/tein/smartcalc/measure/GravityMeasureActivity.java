package com.teinproductions.tein.smartcalc.measure;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

public class GravityMeasureActivity extends ActionBarActivity
        implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;

    private TextView xTextView, yTextView, zTextView, totalTextView, averageTextView;
    private boolean paused = false;

    private double average = 0;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_gravity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

        xTextView = (TextView) findViewById(R.id.x_value);
        yTextView = (TextView) findViewById(R.id.y_value);
        zTextView = (TextView) findViewById(R.id.z_value);
        totalTextView = (TextView) findViewById(R.id.total_value);
        averageTextView = (TextView) findViewById(R.id.average_value);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        final float x = event.values[0];
        final float y = event.values[1];
        final float z = event.values[2];
        final double total = Math.sqrt((x * x) + (y * y) + (z * z));

        xTextView.setText(x + " ");
        xTextView.append(Html.fromHtml("m/s<sup>2</sup>"));
        yTextView.setText(y + " ");
        yTextView.append(Html.fromHtml("m/s<sup>2</sup>"));
        zTextView.setText(z + " ");
        zTextView.append(Html.fromHtml("m/s<sup>2</sup>"));
        totalTextView.setText(total + " ");
        totalTextView.append(Html.fromHtml("m/s<sup>2</sup>"));

        calculateAverage(total);
        averageTextView.setText(average + " ");
        averageTextView.append(Html.fromHtml("m/s<sup>2</sup>"));
    }

    private void calculateAverage(double lastMeasure) {
        double everything = average * count + lastMeasure;
        count++;
        average = everything / count;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // I use R.menu.info_icon because I don't want to create another file.
        getMenuInflater().inflate(R.menu.info_icon, menu);
        if (paused) {
            menu.findItem(R.id.info_icon).setIcon(R.drawable.ic_play_arrow_grey600_36dp);
            menu.findItem(R.id.info_icon).setTitle(R.string.play);
        } else {
            menu.findItem(R.id.info_icon).setIcon(R.drawable.ic_pause_grey600_36dp);
            menu.findItem(R.id.info_icon).setTitle(R.string.pause);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.info_icon:
                if (paused) {
                    paused = false;
                    sensorManager.registerListener(
                            this,
                            sensor, SensorManager.SENSOR_DELAY_FASTEST);
                } else {
                    paused = true;
                    sensorManager.unregisterListener(this, sensor);
                }

                invalidateOptionsMenu();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, sensor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(
                this,
                sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }
}
