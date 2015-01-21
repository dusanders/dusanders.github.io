package com.zena.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by Zena on 3/29/14.
 */
public class RingerTime
{
	//datamembers
	private int startHour;
	private int startMinute;
	private int endHour;
	private int endMinute;
	private String name = " ";
	private boolean state;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	private Calendar cal;

	public RingerTime()
	{
		//empty constructor;
	}

	public RingerTime(int inStartHour, int inStartMinute, int inEndHour, int inEndMinute, String inName, boolean state,
	                  boolean inMonday, boolean inTuesday, boolean inWednesday, boolean inThursday, boolean inFriday, boolean inSaturday, boolean inSunday)
	{
		this.startHour = inStartHour;
		this.startMinute = inStartMinute;
		this.endHour = inEndHour;
		this.endMinute = inEndMinute;
		this.name = inName;
		this.monday = inMonday;
		this.tuesday = inTuesday;
		this.wednesday = inWednesday;
		this.thursday = inThursday;
		this.friday = inFriday;
		this.saturday = inSaturday;
		this.sunday = inSunday;
	}//end constructor

	public Calendar getCal()
	{
		return this.cal;
	}

	public int getStartHour()
	{
		return this.startHour;
	}

	public int getStartMinute()
	{
		return this.startMinute;
	}

	public int getEndHour()
	{
		return this.endHour;
	}

	public int getEndMinute()
	{
		return this.endMinute;
	}

	public String getName()
	{
		return this.name;
	}

	public boolean getMonday(RingerTime inRingerTime)
	{
		return this.monday;
	}

	public boolean getTuesday(RingerTime inRingerTime)
	{
		return this.tuesday;
	}

	public boolean getWednesday(RingerTime inRingerTime)
	{
		return this.wednesday;
	}

	public boolean getThursday(RingerTime inRingerTime)
	{
		return this.thursday;
	}

	public boolean getFriday(RingerTime inRingerTime)
	{
		return this.friday;
	}

	public boolean getSaturday(RingerTime inRingerTime)
	{
		return this.saturday;
	}

	public boolean getSunday(RingerTime inRingerTime)
	{
		return this.sunday;
	}

	public void setStartHour(int inStartHour)
	{
		this.startHour = inStartHour;
	}

	public void setStartMinute(int inStartMinute)
	{
		this.startMinute = inStartMinute;
	}

	public void setEndHour(int inEndHour)
	{
		this.endHour = inEndHour;
	}

	public void setEndMinute(int inEndMinute)
	{
		this.endMinute = inEndMinute;
	}

	public void setState(boolean inState)
	{
		this.state = inState;
	}

	public boolean getState(RingerTime inRingerTime)
	{
		return this.state;
	}

	public String getDays()
	{
		//String result = this.name+": ";
		String result = "";

		if(this.monday)
			result += "Mon ";
		if(this.tuesday)
			result += "Tue ";
		if(this.wednesday)
			result += "Wed ";
		if(this.thursday)
			result += "Thu ";
		if(this.friday)
			result += "Fri ";
		if(this.saturday)
			result += "Sat ";
		if(this.sunday)
			result += "Sun ";

		return result;
	}

	//method to return a string containing information on this object.
	public String getString()
	{
		String result = "";
		String displayStartMinute;
		String displayEndMinute;
		String displayStartHour;
		String displayEndHour;
		String startampm = "AM";
		String endampm = "AM";
		//normalize the time
		if(this.startHour > 12) {
			displayStartHour = "" + (this.startHour - 12);
			startampm = "PM";
		}
		else if(this.startHour == 0) {
			displayStartHour = "" + (this.startHour + 12);
			startampm = "AM";
		}
		else
			displayStartHour = ""+this.startHour;

		if(this.endHour > 12) {
			displayEndHour = "" + (this.endHour - 12);
			endampm = "PM";
		}
		else if(this.endHour == 0) {
			displayEndHour = "" + (this.endHour + 12);
			endampm = "AM";
		}
		else
			displayEndHour = ""+this.endHour;

		if(this.startMinute < 10)
			displayStartMinute = "0"+this.startMinute;
		else
			displayStartMinute = ""+this.startMinute;

		if(this.endMinute < 10)
			displayEndMinute = "0"+this.endMinute;
		else
			displayEndMinute = ""+this.endMinute;
		result += "From: " + displayStartHour+":"+displayStartMinute+ startampm+" to "+displayEndHour+":"+displayEndMinute+endampm;
		return result;
	}
}//end class

