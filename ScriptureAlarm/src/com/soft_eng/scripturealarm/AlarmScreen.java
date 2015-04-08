/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmScreen.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: Activity that sounds an alarm and 
can wake up device from lock

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmScreen extends Activity{
	
	//TODO Wakelock to wake device up from sleep for alarm
	//TODO Override onPause, onResume to handle waking
	//TODO Media Player to play the sound
	// http://www.steventrigg.com/alarm-screen-wakelock-and-mediaplayer-create-an-alarm-clock-in-android-tutorial-part-7/
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.alarm_screen);
		final Context context = this;
		
		Button stopAlarmButton = (Button)findViewById(R.id.alarm_off_button);
		stopAlarmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "Stopping Alarm", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

}
