/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmDetails.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: Activity to create/set alarms

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmDetails extends Activity{
	
	// Variables
	Alarm_t alarm;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.alarm_details);
		
		Toast.makeText(getApplicationContext(), "Activity 2 created", Toast.LENGTH_SHORT).show();
		
		// Initialization
		context = this;
		alarm = new Alarm_t();
		alarm.id = getIntent().getExtras().getLong("id");
		
		// Back Button
		Button back_button = (Button)findViewById(R.id.back_button);
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Clicked to close", Toast.LENGTH_SHORT).show();
				
                finish();
			}
		});
		
		// Save Button
		Button save_button = (Button)findViewById(R.id.save_button);
		save_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Clicked to Save", Toast.LENGTH_SHORT).show();
				updateAlarmDataInfo();
				
				AlarmManagerHelper.clearAlarms(context);
				
				AlarmDBHelper dbHelper = new AlarmDBHelper(context);
				if(alarm.id < 0){
					Log.d("myTag", "Calling helper to make alarm");
					dbHelper.createAlarm(alarm);
				}
				else{
					Log.d("myTag", "Calling helper to update alarm");
					dbHelper.updateAlarm(alarm);
				}
				
				AlarmManagerHelper.setAlarms(context);
				
				setResult(RESULT_OK);
                finish();
			}
		});
	}
	
	
	protected void updateAlarmDataInfo(){
		TimePicker timepicker = (TimePicker) findViewById(R.id.alarm_details_time_picker);
		alarm.hour = timepicker.getCurrentHour().intValue();
		alarm.minute = timepicker.getCurrentMinute().intValue();
		alarm.enabled = true;
	}
	
}
