package com.zena.btcomm.app;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Zena on 10/11/2014.
 */
public class SensorController implements SensorEventListener {
	private BluetoothGattServer gattServer;
	private Activity activity;
	private ArrayList<BluetoothGattCharacteristic> characteristics;
	private BluetoothDevice device;
	private SensorManager manager;
	private Sensor sensor;

	public SensorController(Activity inActivity, BluetoothGattServer inGattServer, ArrayList<BluetoothGattCharacteristic> inCharacteristic, BluetoothDevice inDevice) {
		activity = inActivity;
		gattServer = inGattServer;
		characteristics = inCharacteristic;
		device = inDevice;
	}
	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		float[] values = sensorEvent.values;
		Integer x = (int)values[0];
		Integer y = (int)values[1];
		int z = (int)values[2];

		if(x < 0) {
			x = Math.abs(x);
			characteristics.get(0).setValue(new byte[] {x.byteValue()});
			gattServer.notifyCharacteristicChanged(device, characteristics.get(0), true);
		}
		else if (x > 0) {
			characteristics.get(1).setValue(new byte[] {x.byteValue()});
			gattServer.notifyCharacteristicChanged(device, characteristics.get(1), true);
		}

		if(y < 0 ) {
			y = Math.abs(y);
			characteristics.get(2).setValue(new byte[] {y.byteValue()});
			gattServer.notifyCharacteristicChanged(device, characteristics.get(2), true);
		}
		else if (y > 0) {
			characteristics.get(3).setValue(new byte[] {y.byteValue()});
			gattServer.notifyCharacteristicChanged(device, characteristics.get(3), true);
		}
		TextView textView = (TextView)activity.findViewById(R.id.textView1);
		textView.setText("");
		textView.append("x: "+ x + " " + "y: " + y + " " + "z: " + z);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {
	}

}
