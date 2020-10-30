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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.BreakIterator;
import java.util.ArrayList;

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

        ArrayList<Double> allMovements = new ArrayList<>();

        new Thread() {
            @Override
            public void run() {
                int xPrecision = 1;
                int yPrecision = 1;
                int zPrecision = 1;
                //50 hz of sampling rate
                int samplingRate = 50;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double totalMovement = 0;
                double prevX = Round(x,xPrecision);
                double prevY = Round(y,yPrecision);
                double prevZ = Round(z,zPrecision);
                double prevAccel = Math.sqrt((prevX * prevX) + (prevY * prevY) + (prevZ * prevZ));
                long sleepTime = (long)(1000/(double)samplingRate);
                while(true) {
                    double currentX = Round(x,xPrecision);
                    double currentY = Round(y,yPrecision);
                    double currentZ = Round(z,zPrecision);
                    double currentAccel = Math.sqrt((currentX * currentX) + (currentY * currentY) + (currentZ * currentZ));
                    totalMovement += Math.abs(currentAccel - prevAccel);
                    prevAccel = currentAccel;
                    String display = "Total Movement: " + Round((float) totalMovement,Math.max(zPrecision,Math.max(xPrecision,yPrecision)));
                    new update().execute(display);
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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

    public double Round(float input,int scale) {
        BigDecimal bd = BigDecimal.valueOf(input);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
