package com.zena.sensorapp2.app;

import android.app.Activity;
import android.app.LauncherActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {
	private SensorManager mSensorManager;
	private Sensor mSensor;
	public static TextView xView;
	public static TextView yView;
	public static TextView zView;
	private ListView listView;
	private ListView bluetoothView;
	private Activity mainContext;
	private SensorController sensorController;
	private BluetoothAdapter bluetoothAdapter;
	private BluetoothServerSocket bluetoothServerSocket;
	private static BluetoothSocket bluetoothSocket;
	private static List<BluetoothDevice> bluetoothDeviceList;
	private BluetoothController bluetoothController;
	private static BluetoothListAdapter bluetoothListAdapter;
	private BroadcastReceiver receiver;
	private Set<BluetoothDevice> bluetoothBondedDevices;
	private UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	//onCreate is first method call when app is launched, sorta like main()
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    //get our Activity information
	    mainContext = this;
		bluetoothDeviceList = new ArrayList<BluetoothDevice>();
	    //get our BlueTooth adapter
	    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	    if(!bluetoothAdapter.isEnabled()) {
		    Intent getBluetoothEnable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		    startActivity(getBluetoothEnable);
	    }
	    //enable discoverable
	    Intent enableBluetoothDiscoverable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
	    startActivity(enableBluetoothDiscoverable);
		bluetoothController = new BluetoothController(mainContext, bluetoothAdapter);
	    sensorController.setBluetoothController(bluetoothController);
	    bluetoothController.discover();
	    //get all the views
		xView = (TextView)findViewById(R.id.xView);
	    yView = (TextView)findViewById(R.id.yView);
	    zView = (TextView)findViewById(R.id.zView);
        setContentView(R.layout.activity_main);
	    listView = (ListView)findViewById(R.id.listView);
	    bluetoothView = (ListView)findViewById(R.id.bluetoothListView);
	    mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	    mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	    //put everything together....
	    updateView();
	    //now that we have a list, setup a click listener
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				//get the sensor that was clicked....
				Sensor sensor = (Sensor)adapterView.getAdapter().getItem(i);
				//unreg any previous listeners
				mSensorManager.unregisterListener(sensorController);
				//reset the sensor listener to the selected one....
				mSensorManager.registerListener(sensorController, sensor, SensorManager.SENSOR_DELAY_UI);
			}//end onItemClick
		});//end setOnItemClickListener

	    bluetoothView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		    @Override
	        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
			    bluetoothController.disconnect();
			    Toast toast = Toast.makeText(mainContext, "Disconnect", Toast.LENGTH_SHORT);
			    toast.show();
		    }
	    });
    }//end onCreate()

	public void updateView()
	{
		//get our sensors
		List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
		//setup the adapter for ListView
		CustomAdapter adapter = new CustomAdapter(mainContext.getApplicationContext(), R.layout.listitem, sensorList);
		listView.setAdapter(adapter);
		//setup our sensor listener/controller class
		sensorController = new SensorController(mainContext, mSensor);
		bluetoothListAdapter = new BluetoothListAdapter(mainContext.getApplicationContext(), R.layout.listitem, bluetoothDeviceList);
		bluetoothView.setAdapter(bluetoothListAdapter);
	}//end updateView()

	//called when the 'settings'/'options' key is pressed on phone
	//this inflates the menu for the activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//end onCreateOptionsMenu


	//called when a 'click' is performed on the Options Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
	    //time to quit...
        if (id == R.id.action_quit) {
	        bluetoothAdapter.cancelDiscovery();
	        mSensorManager.unregisterListener(sensorController);
//	        bluetoothAdapter.disable();
	        if(bluetoothSocket != null)
				bluetoothController.disconnect();
//	        unregisterReceiver(receiver);
	        mainContext.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected()

	public static void addBluetoothDevice(BluetoothDevice foundDevice, BluetoothSocket inSocket) {
		bluetoothDeviceList.add(foundDevice);
		bluetoothSocket = inSocket;
		bluetoothListAdapter.notifyDataSetChanged();
	}
}//end MainActivity()
