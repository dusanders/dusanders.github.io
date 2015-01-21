package com.zena.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


/**
 * Created by Zena on 4/4/14.
 */
public class AlarmOffActivity extends Activity implements AudioManager.OnAudioFocusChangeListener
{
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(500);
			//open manager object
			final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			//save the current state of ringer
			super.onCreate(savedInstanceState);
			//get view
			setContentView(R.layout.alarm_off);
			//add a button
			Button stopAlarm = (Button) findViewById(R.id.alarm_off_button);
			//add a listener to the button
			stopAlarm.setOnTouchListener(new View.OnTouchListener()
			{
				public boolean onTouch(View arg0, MotionEvent arg1)
				{
					//set ringer back to where it was
					audioManager.adjustVolume(AudioManager.ADJUST_RAISE, 12);
					finish();
					return false;
				}
			});//end listener attach

			//check for control, go to onchange method when we get it
			int result = audioManager.requestAudioFocus(this, AudioManager.RINGER_MODE_SILENT, AudioManager.AUDIOFOCUS_GAIN);
		}

		@Override
		public void onAudioFocusChange(int i)
		{
			//put the phone on silent
			AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		}
	}

