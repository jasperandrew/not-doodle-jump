package me.jasperandrew.notdoodlejump;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static android.R.attr.orientation;
import static android.R.attr.rotation;

/**
 * Created by Jasper on 2/11/2017.
 **/

public class OrientationSensor implements SensorEventListener {
    private SensorManager manager;
    private Sensor gravSensor;
    private Sensor magSensor;

    private float[] gravVals;
    private float[] magVals;

    private float[] rotationMatrix;

    public OrientationSensor(Context context) {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gravSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gravVals = new float[3];
        magVals = new float[3];
        rotationMatrix = new float[9];
    }

    public float[] getOrientation() {
        manager.getRotationMatrix(rotationMatrix, null, gravVals, magVals);
        float[] orientation = new float[3];
        manager.getOrientation(rotationMatrix, orientation);
        return orientation;
    }

    public void printOrientation() {
        float[] orientation = getOrientation();
        System.out.println("Azimuth:" + orientation[0] + " Pitch:" + orientation[1] + " Roll:" + orientation[2]);
    }

    public void register() {
        manager.registerListener(this, gravSensor, SensorManager.SENSOR_DELAY_GAME);
        manager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_GAME);

    }

    public void unregister() {
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            for(int i = 0; i < 3; i++)
                gravVals[i] = event.values[i];
        }else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            for(int i = 0; i < 3; i++)
                magVals[i] = event.values[i];
        }
        Const.ROLL = getOrientation()[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
