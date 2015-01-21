package com.zena.sensorapp2.app;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zena on 9/22/2014.
 */
public class BluetoothListAdapter extends ArrayAdapter<BluetoothDevice> {
	private List<BluetoothDevice> devices;
	private Context context;


	public BluetoothListAdapter(Context inContext, int resource, List<BluetoothDevice> objects) {
		super(inContext, resource, objects);
		context = inContext;
		devices = objects;
	}

	@Override
	public View getView(int posistion, View convertView, ViewGroup parent) {
		//inflate the 'row' layout
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//set the 'row' layout
		View rowView = inflater.inflate(R.layout.listitem, parent, false);
		//get the TextView of the 'row' layout
		TextView textView =  (TextView)rowView.findViewById(R.id.listitemTextView);
		//set what we want displayed in TextView
		if(devices.size() != 0)
		textView.setText(devices.get(posistion).getName() + "\n" + devices.get(posistion).getAddress());
		//return the 'row' view
		return rowView;
	}

	//method to return out Sensor object from a 'onClick' event applied to ListView
	@Override
	public BluetoothDevice getItem(int position) {
		return devices.get(position);
	}
}
