package com.zena.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;

/**
 * Created by Zena on 5/31/2014.
 */
public class AlarmRestarter extends BroadcastReceiver {


	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

			MainActivity.init();

			for(int i=0; i < MainActivity.wranglers.size(); i++)
			{
				//get the information from the RingerTime objects
				int startHour = MainActivity.wranglers.get(i).getStartHour();
				int startMin = MainActivity.wranglers.get(i).getStartMinute();
				int endHour = MainActivity.wranglers.get(i).getEndHour();
				int endMin = MainActivity.wranglers.get(i).getEndMinute();
				//get the alarmID from array
				int startAlarmId = MainActivity.startAlarmManagers.get(i);
				int endAlarmId = MainActivity.endAlarmManagers.get(i);
				//setup the calendar for restarting the alarm
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, startHour);
				cal.set(Calendar.MINUTE, startMin);
				cal.set(Calendar.SECOND, 00);
				//restart the intents
				context = context.getApplicationContext();
				Intent startIntent = new Intent(context, AlarmReceiverActivity.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(context,startAlarmId, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				//create a new AlarmManager object
				AlarmManager am = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
				//set the managers properties(type, time, what to do)
				am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
				//make it repeating per day.
				am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
				//setup calendar for ending intents
				cal.set(Calendar.HOUR_OF_DAY, endHour);
				cal.set(Calendar.MINUTE, endMin);
				cal.set(Calendar.SECOND, 00);
				//restart the ending intents
				Intent endIntent = new Intent(context, AlarmOffActivity.class);
				PendingIntent offpendingIntent = PendingIntent.getActivity(context, endAlarmId, endIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				AlarmManager offAm = (AlarmManager)context.getSystemService(Activity.ALARM_SERVICE);
				offAm.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), offpendingIntent);
				offAm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, offpendingIntent);
			}

		}//end if
	}//end onReceive
}//end AlarmRestarter
