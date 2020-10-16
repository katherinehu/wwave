package com.wwave.wave;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.BreakIterator;

public class AccelActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mAccelerometer;

    public float x = 0;
    public float y = 0;
    public float z = 0;

    TextView movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);

        movement = findViewById(R.id.tvTotalMovement);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        final float[] first_x = {0};
        final float[] second_x = {0};
        final float[] first_y = {0};
        final float[] second_y = {0};
        final float[] first_z = {0};
        final float[] second_z = {0};

        first_x[0] = x;
        first_y[0] = y;
        first_z[0] = z;

        final double[] totalMovement = {0};

        new Thread() {
            @Override
            public void run() {
                boolean keepRunning = true;
                while (keepRunning) {
                    second_x[0] = x;
                    second_y[0] = y;
                    second_z[0] = z;

                    totalMovement[0] += Math.sqrt((second_x[0] - first_x[0]) * (second_x[0] - first_x[0]) +
                            (second_y[0] - first_y[0]) * (second_y[0] - first_y[0]) +
                            (second_z[0] - first_z[0]) * (second_z[0] - first_z[0]));

                    String display = "Total movement so far: " + totalMovement[0];
                    new update().execute(display);

                    first_x[0] = second_x[0];
                    first_y[0] = second_y[0];
                    first_z[0] = second_z[0];
                    SystemClock.sleep(10); }
            }
        }.start();

    }

    class update extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            movement.setText(s);
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];
        x = ax;
        y = ay;
        z = az;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
