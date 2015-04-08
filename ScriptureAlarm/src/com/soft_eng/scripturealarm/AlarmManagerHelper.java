/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmManagerHelper.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: Creates Pending Intents from
list of alarms and sends them to system

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmManagerHelper extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		setAlarms(context);
	}
	
	public static void setAlarms(Context context){
		clearAlarms(context);
		
		AlarmDBHelper dbHelper = new AlarmDBHelper(context);
		List<Alarm_t> alarms = dbHelper.getAlarms();
		
		if(alarms != null){
			for( Alarm_t alarm : alarms){
				if(alarm.enabled){
					Log.d("myTag", "SetAlarm: alarm is enabled. Creating Pending Intent");
					PendingIntent pIntent = createPendingIntent(context, alarm);
        	
		        	Calendar calendar = Calendar.getInstance();
		        	calendar.setTimeInMillis(System.currentTimeMillis());
		        	calendar.set(Calendar.HOUR_OF_DAY, alarm.hour);
		        	calendar.set(Calendar.MINUTE, alarm.minute);
		        	calendar.set(Calendar.SECOND, 00);
		        	
		        	setAlarm(context, calendar, pIntent);
				}
			}
		}
	}
	
	@SuppressLint("NewApi")
	private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
		Log.d("myTag", "setAlarm of AlarmManagerHelper");
		
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		// repeat every 24 hours
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pIntent);
	}
	
	public static void clearAlarms(Context context){
		AlarmDBHelper dbHelper = new AlarmDBHelper(context);
		List<Alarm_t> alarms = dbHelper.getAlarms();
		
		if(alarms != null){
			for( Alarm_t alarm : alarms){
				if(alarm.enabled){
					Log.d("myTag", "Clear/Reset: alarm is enabled. Creating Pending Intent");
					PendingIntent pIntent = createPendingIntent(context, alarm);
        	
		        	AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		        	alarmManager.cancel(pIntent);
				}
			}
		}
	}
	
	private static PendingIntent createPendingIntent(Context context, Alarm_t alarmModel){
		Intent intent = new Intent(context, AlarmService.class);
		intent.putExtra("ID", alarmModel.id);
		intent.putExtra("HOUR", alarmModel.hour);
		intent.putExtra("MINUTE", alarmModel.minute);
		intent.putExtra("ENABLED", alarmModel.enabled);
		
		return PendingIntent.getService(context, (int) alarmModel.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

}
