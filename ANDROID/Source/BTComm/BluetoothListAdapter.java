package com.zena.btcomm.app;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by Zena on 10/11/2014.
 */
public class BluetoothListAdapter extends ArrayAdapter<BluetoothDevice> {
	private List<BluetoothDevice> aDeviceList;
	private Context aContext;

	public BluetoothListAdapter(Context context, int resource, List<BluetoothDevice> objects) {
		super(context, resource, objects);
		aDeviceList = objects;
		aContext = context;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)aContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View listItem = inflater.inflate(R.layout.list_item, parent, false);
		TextView bluetoothDeviceName = (TextView)listItem.findViewById(R.id.bluetoothDeviceName);
		Button connectButton = (Button)listItem.findViewById(R.id.connectButton);
		if(!aDeviceList.isEmpty()) {
			bluetoothDeviceName.setText(aDeviceList.get(position).getName() + "\n" + aDeviceList.get(position).getAddress());

			connectButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					MainActivity.startGattServer(aDeviceList.get(position));
				}
			});
		}
		return listItem;
	}
	@Override
	public BluetoothDevice getItem(int position){
		return aDeviceList.get(position);
	}
}
