package com.zena.sensorapp2.app;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Zena on 9/17/2014.
 *
 *      WHY DOESNT THE LAYOUT UPDATE??????
 *      ACCORDING TO THE RIGHT SENSORS...
 *
 */
public class SensorController extends Activity implements SensorEventListener {
	private Activity activity;
	private TextView mxView;
	private TextView myView;
	private TextView mzView;
	private TextView labelOne;
	private TextView labelTwo;
	private TextView labelThree;
	private Float x;
	private Float y;
	private Float z;
	private Float four = null;
	private Float five = null;
	private float[] coords;
	private Sensor sensor;
	private static BluetoothController bluetoothController;


	public static void setBluetoothController(BluetoothController inBluetoothController) {
		bluetoothController = inBluetoothController;
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		coords = sensorEvent.values;
		if(sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			x = coords[0];
			y = coords[1];
			z = coords[2];
		}
		else if(sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			x = coords[0];
			y = coords[1];
			z = coords[2];
			if(coords.length > 3) {
				four = coords[3];
				five = coords[4];
			}
		}
		else if(sensor.getType() == Sensor.TYPE_GRAVITY) {
			x = coords[0];
			y = coords[1];
			z = coords[2];
		}
		else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			x = coords[0];
			y = coords[1];
			z = coords[2];
		}
		else if(sensor.getType() == Sensor.TYPE_LIGHT) {
			x = coords[0];
		}
		else if(sensor.getType() == Sensor.TYPE_PROXIMITY) {
			x = coords[0];
		}
		run();
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {
		mzView.setText("!!!");
	}

	public SensorController(Activity inActivity, Sensor inSensor) {
		this.activity = inActivity;
		mxView = (TextView)activity.findViewById(R.id.xView);
		myView = (TextView)activity.findViewById(R.id.yView);
		mzView = (TextView)activity.findViewById(R.id.zView);
		labelOne = (TextView)activity.findViewById(R.id.labelOne);
		labelTwo = (TextView)activity.findViewById(R.id.labelTwo);
		labelThree = (TextView)activity.findViewById(R.id.labelThree);
		sensor = inSensor;
	}
	public void run() {
		Integer xInteger = x.intValue();
		Integer yInteger = y.intValue();
		Integer zInteger = z.intValue();
		if(this.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			int xpercent = xInteger * 10;
			int ypercent = yInteger * 10;
			int zpercent = zInteger * 10;
			ProgressBar progressBar1 = (ProgressBar) activity.findViewById(R.id.progressBar);
			ProgressBar progressLeft = (ProgressBar) activity.findViewById(R.id.progressleft);
			ProgressBar progressRight = (ProgressBar) activity.findViewById(R.id.progressright);
			progressBar1.setProgress(Math.abs(xpercent));
			if (ypercent < 0)
				progressLeft.setProgress(Math.abs(ypercent));
			if (ypercent > 0)
				progressRight.setProgress(ypercent);
			mxView.setText("Z: " + xInteger.toString());
			myView.setText("Y: " + yInteger.toString());
			mzView.setText("Z: " + zInteger.toString());
			labelOne.setText("X:");
			labelTwo.setText("Y:");
			labelThree.setText("Z:");
			mxView.setText(x.toString());
			myView.setText(y.toString());
			mzView.setText(z.toString());
			byte[] byteString = {x.byteValue()};
			if(bluetoothController!=null)
				bluetoothController.write(byteString);
		}
		else if(sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
			labelOne.setText("X:");
			labelTwo.setText("Y:");
			labelThree.setText("Z:");
			mxView.setText(x.toString());
			myView.setText(y.toString());
			mzView.setText(z.toString());

		}
		else if(sensor.getType() == Sensor.TYPE_GRAVITY) {
			labelOne.setText("X:");
			labelTwo.setText("Y:");
			labelThree.setText("Z:");
			mxView.setText(x.toString());
			myView.setText(y.toString());
			mzView.setText(z.toString());
		}
		else if(sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
			labelOne.setText("X:");
			labelTwo.setText("Y:");
			labelThree.setText("Z:");
			mxView.setText(x.toString());
			myView.setText(y.toString());
			mzView.setText(z.toString());
		}
		else if(sensor.getType() == Sensor.TYPE_LIGHT) {
			labelOne.setText("Light:");
			mxView.setText(x.toString());
			labelTwo.setText("Units: SI lux");
			labelThree.setText("");
		}
		else if(sensor.getType() == Sensor.TYPE_PROXIMITY) {
			labelOne.setText("Prox:");
			mxView.setText(x.toString());
			labelTwo.setText("in cm");
			labelThree.setText("");
		}
	}
}
