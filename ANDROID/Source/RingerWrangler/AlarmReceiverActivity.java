package com.zena.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


/**
 * Created by Zena on 4/2/14.
 */
public class AlarmReceiverActivity extends Activity implements AudioManager.OnAudioFocusChangeListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//open manager object
		final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		//save the current state of ringer
		super.onCreate(savedInstanceState);
		//get view
		setContentView(R.layout.alarm);
		//add a button
		Button stopAlarm = (Button) findViewById(R.id.stopAlarm);
		//add a listener to the button
		stopAlarm.setOnTouchListener(new View.OnTouchListener()
		{
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				//set ringer back to where it was
				//audioManager.setRingerMode(oldRingerMode);
				audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
				finish();
				return false;
			}
		});//end listener attach
	}

	@Override
	public void onAudioFocusChange(int i)
	{
		//put the phone on silent
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}
}