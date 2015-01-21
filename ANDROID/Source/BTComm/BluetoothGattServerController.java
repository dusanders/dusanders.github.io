package com.zena.btcomm.app;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Zena on 10/11/2014.
 */
public class BluetoothGattServerController {
	private BluetoothManager manager;
	private BluetoothGattServer gattServer;
	private SensorManager sensorManager;
	private SensorController sensorController;
	private Sensor sensor;
	private Activity activity;
	private ArrayList<BluetoothGattCharacteristic> characteristics;


	public BluetoothGattServerController(BluetoothManager inManager, final Activity inActivity, ArrayList<BluetoothGattCharacteristic> inCharacteristics) {
		manager = inManager;
		activity = inActivity;
		characteristics = inCharacteristics;
		gattServer = manager.openGattServer(activity, new BluetoothGattServerCallback() {
			@Override
			public void onConnectionStateChange(final BluetoothDevice device, int status, final int newState) {
				super.onConnectionStateChange(device, status, newState);
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast toast = Toast.makeText(activity, device.getName() + "State Change" + newState, Toast.LENGTH_SHORT);
						toast.show();
					}
				});
			}

			@Override
			public void onServiceAdded(int status, final BluetoothGattService service) {
				super.onServiceAdded(status, service);
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast toast = Toast.makeText(activity, "Service: " + service.getUuid() + " Added!", Toast.LENGTH_SHORT);
						toast.show();
					}
				});
			}

			@Override
			public void onCharacteristicReadRequest(final BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
				super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
				if(sensorController == null) {
					sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
					sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
					sensorController = new SensorController(activity, gattServer, characteristics, device);
					sensorManager.registerListener(sensorController, sensor, 350000);
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast toast = Toast.makeText(activity, device.getName() + " Sensor Activated!", Toast.LENGTH_SHORT);
							toast.show();
						}
					});
				}
			}

			@Override
			public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
				super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
			}

			@Override
			public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
				super.onDescriptorReadRequest(device, requestId, offset, descriptor);
			}

			@Override
			public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
				super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
			}

			@Override
			public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
				super.onExecuteWrite(device, requestId, execute);
			}

			@Override
			protected Object clone() throws CloneNotSupportedException {
				return super.clone();
			}

			@Override
			public boolean equals(Object o) {
				return super.equals(o);
			}

			@Override
			protected void finalize() throws Throwable {
				super.finalize();
			}

			@Override
			public int hashCode() {
				return super.hashCode();
			}

			@Override
			public String toString() {
				return super.toString();
			}
		});
	}

	public BluetoothGattServer getGattServer() {
		return gattServer;
	}

	public void close() {
		gattServer.close();
//		sensorManager.unregisterListener(sensorController);
	}
}
