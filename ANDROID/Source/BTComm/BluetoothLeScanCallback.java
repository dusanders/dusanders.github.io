package com.zena.btcomm.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * Created by Zena on 10/11/2014.
 */
public class BluetoothLeScanCallback implements BluetoothAdapter.LeScanCallback {
	private Activity lActivity;
	public BluetoothLeScanCallback (Activity activity) {
		lActivity = activity;
	}
	@Override
	public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
		lActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				MainActivity.addBluetoothDevice(bluetoothDevice);
			}
		});
	}
}
