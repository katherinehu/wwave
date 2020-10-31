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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.BreakIterator;
import java.util.ArrayList;

public class AccelActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private double maxYVal = 0;
    public float x = 0;
    public float y = 0;
    public float z = 0;

    TextView movement;
    GraphView gv_Movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accel);

        movement = findViewById(R.id.tvTotalMovement);
        gv_Movement = findViewById(R.id.gv_Movement);
        //Adjust the graph so it doesn't move around randomly
        gv_Movement.getViewport().setYAxisBoundsManual(true);
        gv_Movement.getViewport().setXAxisBoundsManual(true);
        gv_Movement.getViewport().setMaxY(maxYVal);
        gv_Movement.getViewport().setMinY(0);
        gv_Movement.getViewport().setMaxX(200);
        gv_Movement.getViewport().setMinX(0);

        //check movement
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Store movement data in a plottable object
        final LineGraphSeries<DataPoint>[] allMovements = new LineGraphSeries[]{new LineGraphSeries<>()};

        new Thread() {
            @Override
            public void run() {
                int xPrecision = 1;
                int yPrecision = 1;
                int zPrecision = 1;
                //20 hz of sampling rate
                int samplingRate = 20;
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
                int counter = 1;
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

                    //adjust range dynamically
                    if (currentAccel > maxYVal){
                        maxYVal = currentAccel;
                        gv_Movement.getViewport().setMaxY(maxYVal*1.1);
                    }

                    //add new movements to the object
                    allMovements[0].appendData(new DataPoint(counter,currentAccel),true,1000,true);
                    counter++;

                    //Need to clear it periodically or else it runs out of memory and crashes
                    if (counter % 10 == 0) {
                        gv_Movement.removeAllSeries();
                        gv_Movement.addSeries(allMovements[0]);
                    }

                    //Every so often refresh the plot so it doesn't get all compressed after longer periods
                    if (counter % 200 == 0) {
                        allMovements[0] = new LineGraphSeries<>();
                        gv_Movement.getViewport().setMaxX(counter + 200);
                        gv_Movement.getViewport().setMinX(counter);

                    }
                }
            }
        }.start();

    }

    //Use this to update the textview from inside a thread, maybe not the best possible way to do this, but it definitely works.
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
