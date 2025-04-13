package com.nicolas.canivete_suico;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;
import java.util.Locale;
import androidx.core.content.ContextCompat;


public class LevelActivity extends AppCompatActivity implements SensorEventListener {

    private ImageView imageView;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private TextView textViewGraus;
    private float[] gravity;
    private float[] geomagnetic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        textViewGraus = findViewById(R.id.textViewGraus);
        imageView = findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (accelerometer != null && magnetometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        android.util.Log.d("Level", "onSensorChanged() foi chamado!");

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
        }

        if (gravity != null && geomagnetic != null) {
            float[] L = new float[9];
            float[] I = new float[9];

            if (SensorManager.getRotationMatrix(L, I, gravity, geomagnetic)) {
                float[] orientation = new float[3];
                SensorManager.getOrientation(L, orientation);

                float azimuthInRadians = orientation[0];
                float azimuthInDegrees = (float) Math.toDegrees(azimuthInRadians);
                azimuthInDegrees = (azimuthInDegrees + 360) % 360;

                imageView.setRotation(-azimuthInDegrees);
                textViewGraus.setText(String.format(Locale.US, "%.0f°", azimuthInDegrees));

                if (isNear(azimuthInDegrees, 0) ||
                        isNear(azimuthInDegrees, 90) ||
                        isNear(azimuthInDegrees, 180) ||
                        isNear(azimuthInDegrees, 270) ||
                        isNear(azimuthInDegrees, 360)){

                    findViewById(R.id.main).setBackgroundColor(
                            ContextCompat.getColor(this, android.R.color.black)
                    );
                } else {
                    findViewById(R.id.main).setBackgroundColor(
                            ContextCompat.getColor(this, android.R.color.holo_green_dark)
                    );
                }
            }
        }
    }


    private boolean isNear(float value, float target) {
        float tolerance = 2.0f;
        return Math.abs(value - target) <= tolerance;
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
