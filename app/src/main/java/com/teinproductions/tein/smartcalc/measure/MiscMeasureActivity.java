package com.teinproductions.tein.smartcalc.measure;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.teinproductions.tein.smartcalc.R;

public class MiscMeasureActivity extends ActionBarActivity implements SensorEventListener {

    private TextView ambTemp, pressure, light, humidity;

    private SensorManager sensorManager;
    private Sensor ambTempSensor, pressureSensor, lightSensor, humiditySensor;

    private boolean paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc_measure);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ambTemp = (TextView) findViewById(R.id.ambient_air_temp);
        pressure = (TextView) findViewById(R.id.ambient_air_pressure);
        light = (TextView) findViewById(R.id.ambient_light);
        humidity = (TextView) findViewById(R.id.relative_humidity);

        setUpSensors();
    }

    private void setUpSensors() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        ambTempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        pressureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }

    private void registerListeners() {
        sensorManager.registerListener(this, ambTempSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private void unregisterListeners() {
        sensorManager.unregisterListener(this, ambTempSensor);
        sensorManager.unregisterListener(this, pressureSensor);
        sensorManager.unregisterListener(this, lightSensor);
        sensorManager.unregisterListener(this, humiditySensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == ambTempSensor) {
            ambTemp.setText(event.values[0] + " °C");
        } else if (event.sensor == pressureSensor) {
            pressure.setText(event.values[0] + " mbar");
        } else if (event.sensor == lightSensor) {
            light.setText(event.values[0] + " lx");
        } else if (event.sensor == humiditySensor) {
            humidity.setText(event.values[0] + " %");
        }
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
                    registerListeners();
                } else {
                    paused = true;
                    unregisterListeners();
                }

                invalidateOptionsMenu();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerListeners();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterListeners();
    }
}
