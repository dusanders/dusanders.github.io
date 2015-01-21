package com.zena.app;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.*;


public class MainActivity extends Activity
{
	//class members for methods
	public static ArrayList<Integer> startAlarmManagers = new ArrayList<Integer>();
	public static ArrayList<Integer> endAlarmManagers = new ArrayList<Integer>();
	public int alarmID = 1;
	public TimePicker startTimePicker;
	public TimePicker endTimePicker;
	public EditText inName;
	public static ListView listView;
	public static String[] nameArray;
	public static String[] dayArray;
	public static String[] hourArray;
	public static int[] startHours;
	public static int[] startMinutes;
	public static int[] endHours;
	public static int[] endMinutes;
	public Bundle bundle;
	public static ArrayAdapter<String> listAdapter;
	//save strings
	public Bundle saveBundle;
	//storage for the wrangler objects
	public static ArrayList<RingerTime> wranglers = new ArrayList();

	//method to create the array for displaying the timer objects, a seperate array is created
	//for each displayed item. The size of the array is based on how many RingerTime objects we have.
	//This will initialize the displayed values for the rowview on the main screen.
	public static void init()
	{
		//create new string array for ListView
            nameArray = new String[wranglers.size()];
            dayArray = new String[wranglers.size()];
            hourArray = new String[wranglers.size()];
            startHours = new int[wranglers.size()];
            startMinutes = new int[wranglers.size()];
            endHours = new int[wranglers.size()];
            endMinutes = new int[wranglers.size()];
		for(int i = 0; i < wranglers.size(); i++)
		{
			//get string from Ringertime objects
			nameArray[i] = wranglers.get(i).getName();
			hourArray[i] = wranglers.get(i).getString();
			dayArray[i] = wranglers.get(i).getDays();
			startHours[i] = wranglers.get(i).getStartHour();
			startMinutes[i] = wranglers.get(i).getStartMinute();
			endHours[i] = wranglers.get(i).getEndHour();
			endMinutes[i] = wranglers.get(i).getEndMinute();
		}

	}//end init()

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	    //call to superclass
	    super.onCreate(savedInstanceState);
        //save the instance state
        bundle = savedInstanceState;
        saveBundle = bundle;
        //call to superclass
	    init();
	    //set view to main activity
	    setContentView(R.layout.activity_main);
	    //new ArrayAdapter

	    //pass context, view to get, viewfield to fill, what to show
	    listAdapter = new CustomArray(this, nameArray, dayArray, hourArray, startHours, startMinutes, endHours, endMinutes);
	    //create new ListView object
	    listView = (ListView)findViewById(R.id.listView);
	    //set the adapter for the ListView
	    listView.setAdapter(listAdapter);
	    Button button = (Button) (findViewById(R.id.button));
	    button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
			    //call the method to add a timer
			    onAddClick(view);
		    }
	    });
    }

	//simple 'back' navigation, may need redesign
	@Override
	public void onBackPressed() {
		//reset the view
//		onCreate(bundle);
	}
	//method called when a new timer is being created.
	public void onAddClick(View view)
	{
		//get the view
		//class memebers for methods
		setContentView(R.layout.addfrag);
		startTimePicker = (TimePicker) (findViewById(R.id.timePicker3));
		endTimePicker = (TimePicker) (findViewById(R.id.timePicker23));
		//set the pickers to 24hour format
		//get the name for this
		inName = (EditText) (findViewById(R.id.name_Text));
		Button button = (Button) (findViewById(R.id.save_Nutton));
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//call the method that will create new timer
				onSaveClick(view);
			}//end onClick()
		});//end setOnClickListener
	}//end onAddClick()

	//method to crate a new timer object
	public void onSaveClick(View view)
	{
		//get values from view
		int startTimeHour = startTimePicker.getCurrentHour();
		int startTimeMinute = startTimePicker.getCurrentMinute();
		int endTimeHour = endTimePicker.getCurrentHour();
		int endTimeMinute = endTimePicker.getCurrentMinute();
		Editable stringName = inName.getText();
		String name = stringName.toString();


		/*commented out for possible future use. this will get the values from a list
			of days, removed support for simplicity.


		boolean monday = ((CheckBox)findViewById(R.id.checkBox)).isChecked();
		boolean tuesday = ((CheckBox)findViewById(R.id.checkBox2)).isChecked();
		boolean wednesday = ((CheckBox)findViewById(R.id.checkBox3)).isChecked();
		boolean thursday = ((CheckBox)findViewById(R.id.checkBox4)).isChecked();
		boolean friday = ((CheckBox)findViewById(R.id.checkBox5)).isChecked();
		boolean saturday = ((CheckBox)findViewById(R.id.checkBox6)).isChecked();
		boolean sunday = ((CheckBox)findViewById(R.id.checkBox7)).isChecked();
		*/
		//statically set the above commented out memebers. these values are required by the RingerTime constructor
		boolean monday = true;
		boolean tuesday = true;
		boolean wednesday = true;
		boolean thursday = true;
		boolean friday = true;
		boolean saturday = true;
		boolean sunday = true;
		//create new wrangler object
		RingerTime newWrangler = new RingerTime(startTimeHour, startTimeMinute, endTimeHour, endTimeMinute, name, true, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
		//add to the array list
		wranglers.add(newWrangler);

		//send a toast
		Context context = getApplicationContext();
		CharSequence text = "Timer " + name + " saved!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();


		//Create an offset from the current time in which the alarm will go off.
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, startTimeHour);
		cal.set(Calendar.MINUTE, startTimeMinute);
		cal.set(Calendar.SECOND, 0);

		//Create a new PendingIntent and add it to the AlarmManager

		//get a new alarmID
		alarmID++;
		//add this ID to the array of managers
		startAlarmManagers.add(alarmID);
		//setup a new intent
		Intent intent = new Intent(this, AlarmReceiverActivity.class);
		//setup a new pending intent. with above intent
		PendingIntent pendingIntent = PendingIntent.getActivity(this,alarmID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		//create a new AlarmManager object
		AlarmManager am = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
		//set the managers properties(type, time, what to do)
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
		//make it repeating per day.
		am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

		//send another intent to turn ringer back on at END
		cal.set(Calendar.HOUR_OF_DAY, endTimeHour);
		cal.set(Calendar.MINUTE, endTimeMinute);
		cal.set(Calendar.SECOND, 0);
		//create new ID
		alarmID++;
		//add the ID to the array
		endAlarmManagers.add(alarmID);
		//create new intent
		Intent offIntent = new Intent(this, AlarmOffActivity.class);
		//create new pending intent with the above intent
		PendingIntent offPendingIntent = PendingIntent.getActivity(this,alarmID, offIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		//create new alarm manager object for turning ringer back on
		AlarmManager offAlarmManager = (AlarmManager)getSystemService(Activity.ALARM_SERVICE);
		//set the properties
		offAlarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), offPendingIntent);
		//set it to repeat once per day
		offAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, offPendingIntent);



		//go back to main view.
		onCreate(bundle);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }







/*  Class to handle the array of alarmManagers and RingerTime objects within the listview on the main
	page. This object will hold the 'rows' of the main page. each row is an object that contains RingerTime
	objects and its respective alarmManagers. every row is assigned a special 'tag' for the on/off button
	and the 'delete' button; we use this tag in order to find the correct RingerTime object once an anction
	is performed within the row. This class and its created object also hold the methods to delete the row,
	create new intents, and cancel pending intents.
 */
	public class CustomArray extends ArrayAdapter<String> {
		//data members
		private final Context context;
		private final String[] values;
		private final String[] days;
	    private final String[] hours;
		private final int[] startHour;
		private final int[] startMinute;
		private final int[] endHour;
		private final int[] endMinute;
		//default constructor for new object of this class. Requires all the arrays
		public CustomArray(Context context, String[] values, String[] days, String[] hours,
							int[] inStartHour, int[] inStartMinute, int[] inEndHour, int[] inEndMinute) {
			super(context, R.layout.fragment_main, values);
			this.context = context;
			this.values = values;
			this.days = days;
			this.hours = hours;
			this.startHour = inStartHour;
			this.startMinute = inStartMinute;
			this.endHour = inEndHour;
			this.endMinute = inEndMinute;
		}//end default constructor


		//method to create the row objects views
		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{
			//inflate the view
			final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final View rowView = inflater.inflate(R.layout.fragment_main, parent, false);
			//get a textview for the name
			final TextView textView = (TextView) rowView.findViewById(R.id.name_text);
			textView.setText(values[position]);
			//get a textview for the hours
			final TextView textView1 = (TextView) rowView.findViewById(R.id.timerDays);
			textView1.setText(hours[position]);
			//get a toggle button for on/off feature
			ToggleButton toggleButton = (ToggleButton) rowView.findViewById(R.id.toggleButton);
			//set a tag so we know where it is
			toggleButton.setTag(position);
			//default it to ON
			toggleButton.setChecked(true);
			//set a listener for changes.
			toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
					//did we turn it on?
					if (isChecked) {
						//open new Calendar object
						Calendar cal = Calendar.getInstance();
						//get the starting from the object
						cal.set(Calendar.HOUR_OF_DAY, startHour[position]);
						cal.set(Calendar.MINUTE, startMinute[position]);
						cal.set(Calendar.SECOND, 00);
						//find the tag
						int position = (Integer)compoundButton.getTag();
						//get the correct alarmManager based on tag
						int startAlarmId = MainActivity.startAlarmManagers.get(position);
						int endAlarmId = MainActivity.endAlarmManagers.get(position);
						//set new intents
						Intent intent = new Intent(getContext(), AlarmReceiverActivity.class);
						PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),startAlarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager am = (AlarmManager)getContext().getSystemService(Activity.ALARM_SERVICE);
						am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
						cal.set(Calendar.HOUR_OF_DAY, endHour[position]);
						cal.set(Calendar.MINUTE, endMinute[position]);
						cal.set(Calendar.SECOND, 00);
						Intent offIntent = new Intent(getContext(), AlarmOffActivity.class);
						PendingIntent offPendingIntent = PendingIntent.getActivity(getContext(),endAlarmId, offIntent, PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager offAm = (AlarmManager)getContext().getSystemService(Activity.ALARM_SERVICE);
						offAm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), offPendingIntent);;

					}
					//else we need to cancel the checked intents
					else {
						//get the array position
						int position = (Integer)compoundButton.getTag();
						int startAlarmId = MainActivity.startAlarmManagers.get(position);
						int endAlarmId = MainActivity.endAlarmManagers.get(position);
						//delete the pending intents
						Intent intent = new Intent(getContext(), AlarmReceiverActivity.class);
						PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),startAlarmId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager am = (AlarmManager)getContext().getSystemService(Activity.ALARM_SERVICE);
						am.cancel(pendingIntent);
						Intent offIntent = new Intent(getContext(), AlarmOffActivity.class);
						PendingIntent offPendingIntent = PendingIntent.getActivity(getContext(),endAlarmId, offIntent, PendingIntent.FLAG_CANCEL_CURRENT);
						AlarmManager offAm = (AlarmManager)getContext().getSystemService(Activity.ALARM_SERVICE);
						offAm.cancel(offPendingIntent);
					}

				}
			});
			//setup a delete button
			Button button = (Button) rowView.findViewById(R.id.delete_button);
			//set the tag so we can find it
			button.setTag(position);
			//add a listener for click events
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					//get the array position
					int position = (Integer) view.getTag();
					int startAlarmId = MainActivity.startAlarmManagers.get(position);
					int endAlarmId = MainActivity.endAlarmManagers.get(position);
					//delete the pending intents
					Intent intent = new Intent(getContext(), AlarmReceiverActivity.class);
					PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), startAlarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					AlarmManager am = (AlarmManager) getContext().getSystemService(Activity.ALARM_SERVICE);
					am.cancel(pendingIntent);
					Intent offIntent = new Intent(getContext(), AlarmOffActivity.class);
					PendingIntent offPendingIntent = PendingIntent.getActivity(getContext(), endAlarmId, offIntent, PendingIntent.FLAG_UPDATE_CURRENT);
					AlarmManager offAm = (AlarmManager) getContext().getSystemService(Activity.ALARM_SERVICE);
					offAm.cancel(offPendingIntent);
					wranglers.remove(position);
					rowView.invalidate();
					MainActivity.startAlarmManagers.remove(position);
					MainActivity.endAlarmManagers.remove(position);
					onCreate(bundle);

				}
			});
			return rowView;
		}
	}


}


