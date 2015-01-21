package com.zena.btcomm.app;

import android.app.Activity;
import android.bluetooth.*;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class MainActivity extends Activity {
	private static Activity mActivity;
	private TextView mTextView1;
	private TextView mTextView2;
	private Button mRestartBluetooth;
	private Button mScanBluetooth;
	private ListView mBluetoothListView;

	private static BluetoothListAdapter mBluetoothListAdapter;
	private static List<BluetoothDevice> mBluetoothDeviceList;

	private static BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private static ArrayList<BluetoothGattCharacteristic> mCharacteristics;
	private static UUID[] mUUID = {UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"), UUID.fromString("00001101-0000-1000-8000-00805F90001B"),
			                       UUID.fromString("00001101-0000-1000-8000-00805F9FF01B"), UUID.fromString("00001101-0000-1000-8000-008ABD90001B"),
			                       UUID.fromString("00001101-0000-1000-8000-00805CDE001B")};
	private static BluetoothGattService mService;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActivity = this;
		mTextView1 = (TextView) findViewById(R.id.textView1);
		mTextView2 = (TextView) findViewById(R.id.textView2);
		mRestartBluetooth = (Button) findViewById(R.id.restartBluetooth);
		mScanBluetooth = (Button) findViewById(R.id.scanBluetooth);
		mBluetoothListView = (ListView) findViewById(R.id.bluetoothListView);
		mBluetoothDeviceList = new ArrayList<BluetoothDevice>();
		mBluetoothListAdapter = new BluetoothListAdapter(mActivity.getApplicationContext(), R.layout.list_item, mBluetoothDeviceList);
		mBluetoothListView.setAdapter(mBluetoothListAdapter);

		mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		mBluetoothAdapter = mBluetoothManager.getAdapter();

		mService = new BluetoothGattService(mUUID[0], BluetoothGattService.SERVICE_TYPE_PRIMARY);

		mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
		BluetoothGattCharacteristic characteristic1 = new BluetoothGattCharacteristic(mUUID[1], BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_READ);
		BluetoothGattCharacteristic characteristic2 = new BluetoothGattCharacteristic(mUUID[2], BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_READ);
		BluetoothGattCharacteristic characteristic3 = new BluetoothGattCharacteristic(mUUID[3], BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_READ);
		BluetoothGattCharacteristic characteristic4 = new BluetoothGattCharacteristic(mUUID[4], BluetoothGattCharacteristic.PROPERTY_WRITE, BluetoothGattCharacteristic.PERMISSION_READ);
		mCharacteristics.add(characteristic1);
		mCharacteristics.add(characteristic2);
		mCharacteristics.add(characteristic3);
		mCharacteristics.add(characteristic4);

		mService.addCharacteristic(mCharacteristics.get(0));
		mService.addCharacteristic(mCharacteristics.get(1));
		mService.addCharacteristic(mCharacteristics.get(2));
		mService.addCharacteristic(mCharacteristics.get(3));


		mRestartBluetooth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mBluetoothAdapter.isEnabled()) {
					mBluetoothAdapter.disable();
				} else if (!mBluetoothAdapter.isEnabled()) {
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivity(intent);
				}
			}
		});
		mScanBluetooth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final BluetoothLeScanCallback mLeScanCallback = new BluetoothLeScanCallback(mActivity);
				final long SCAN_PERIOD = 1000;
				Handler scannerHandler = new Handler();
				scannerHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						mBluetoothAdapter.stopLeScan(mLeScanCallback);
					}
				}, SCAN_PERIOD);
				mBluetoothAdapter.startLeScan(mLeScanCallback);
			}
		});

	}

	public static void addBluetoothDevice(BluetoothDevice inDevice) {
		if (mBluetoothDeviceList.isEmpty() && inDevice.getName() != null) {
			mBluetoothDeviceList.add(inDevice);
			mBluetoothListAdapter.notifyDataSetChanged();
		}
		if (!mBluetoothDeviceList.isEmpty()) {
			if (!mBluetoothDeviceList.contains(inDevice)) {
				mBluetoothDeviceList.add(inDevice);
				mBluetoothListAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	    @Override
	    public void onPause() {
		    super.onPause();
		    gattServerController.close();
		    this.finish();
	    }
    */
	public static void startGattServer(final BluetoothDevice inBluetoothDevice) {
		BluetoothGattServerController gattServerController = new BluetoothGattServerController(mBluetoothManager, mActivity, mCharacteristics);
		BluetoothGattServer mGattServer = gattServerController.getGattServer();
		mGattServer.addService(mService);
		mGattServer.connect(inBluetoothDevice, true);
	}
}

