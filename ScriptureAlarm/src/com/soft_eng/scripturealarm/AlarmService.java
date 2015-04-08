/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmService.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: Service that is called when it is time
for alarm to go off - it launches AlarmScreen

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service{
	
	public static String TAG = AlarmService.class.getSimpleName();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		

		Log.d("myTag", "onStartCommand of AlarmService - ALARM STARTING!!!!!!!!!!");
		
		Intent alarmIntent = new Intent(getBaseContext(), AlarmScreen.class);
		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		getApplication().startActivity(alarmIntent);
		
		// AlarmManagerHelper.setAlarms(this);
		
		return super.onStartCommand(intent, flags, startId);
	}

}
