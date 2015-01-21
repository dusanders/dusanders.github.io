package com.zena.sensorapp2.app;

import android.content.Context;
import android.hardware.Sensor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Zena on 9/20/2014.
 */
public class CustomAdapter extends ArrayAdapter<Sensor> {
	Context adapterContext;
	List<Sensor> sensorList;
	public CustomAdapter(Context context, int resource) {
		super(context, resource);
	}

	//constructor for the adapter, pass the List that needs to be organized
	public CustomAdapter(Context context, int resource, List objects) {
		//call out superclass
		super(context, resource, objects);
		adapterContext = context;
		sensorList = objects;
	}

	//method called by ListView object to getView for 'row' of list
	@Override
	public View getView(int posistion, View convertView, ViewGroup parent) {
		//inflate the 'row' layout
		LayoutInflater inflater = (LayoutInflater) adapterContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//set the 'row' layout
		View rowView = inflater.inflate(R.layout.listitem, parent, false);
		//get the TextView of the 'row' layout
		TextView textView =  (TextView)rowView.findViewById(R.id.listitemTextView);
		//set what we want displayed in TextView
		textView.setText(sensorList.get(posistion).getName());
		//return the 'row' view
		return rowView;
	}

	//method to return out Sensor object from a 'onClick' event applied to ListView
	@Override
	public Sensor getItem(int position) {
		return sensorList.get(position);
	}
}
