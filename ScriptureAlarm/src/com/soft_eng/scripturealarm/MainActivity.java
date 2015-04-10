/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: MainActivity.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: The home screen of the app

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	AlarmAdapter aa;
	Context context;
	AlarmDBHelper dbHelper;
	ListView lvAlarms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // IDs
        lvAlarms = (ListView)findViewById(R.id.alarm_lv);
        Button addAlarmButton = (Button)findViewById(R.id.add_alarm_button);
        context = this;
        dbHelper = new AlarmDBHelper(this);
        
        // Start new activity when button is pressed
        addAlarmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// -1 indicates it is a new alarm
				startAlarmDetailsActivity(-1);
			}
		});

/* this block is for testing only , generates a reference toast to test DB
        Button testScriptureButton = (Button)findViewById(R.id.test_scripture_button);
        testScriptureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new AlarmDBHelper(context);
                Scripture_t scripture = new Scripture_t();
                scripture = dbHelper.getRandomVerse();

                Toast.makeText(getApplicationContext(), scripture.reference, Toast.LENGTH_SHORT).show();
            }
        });
*/
        
        // set listview populated from db
        AlarmDBHelper dbHelper = new AlarmDBHelper(this);
        
        List<Alarm_t> alarms = dbHelper.getAlarms();
        if (alarms != null){
	        aa = new AlarmAdapter(this, R.layout.alarm_list_item, alarms);
	        lvAlarms.setAdapter(aa);
        }
        
    }
    
    public void startAlarmDetailsActivity(long id){
    	Intent intent = new Intent(this, AlarmDetails.class);
    	intent.putExtra("id", id);
    	startActivityForResult(intent, 0);
    }
    
    // When child activities finish
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	// called when AlarmDetails activity returns with finish()
    	// if the list changes, AlarmDetails will return a result of RESULT_OK
    	if(resultCode == RESULT_OK){
    		Log.d("myTag", "About to update array adapter");
    		AlarmDBHelper dbHelper = new AlarmDBHelper(this);
    		List<Alarm_t> alarms = dbHelper.getAlarms();
    		
    		// if aa hasn't been initialized because it was empty
    		// TODO If the first alarm hasn't been initialized on start, this doesn't show
    		if(aa == null){
    			Log.d("myTag", "Initializing adapter because it wasn't earlier");
    			aa = new AlarmAdapter(this, R.layout.alarm_list_item, alarms);
    			lvAlarms.setAdapter(aa);
    		}
    		aa.clear();
    		aa.addAll(alarms);
    		aa.notifyDataSetChanged();
    	}
    	
    }
    
    // Called when delete button is clicked
    public void deleteAlarm(long id){
    	Log.d("myTag", "DeleteAlarm called in main activity");
    	final long alarmId = id;
    	AlarmManagerHelper.clearAlarms(context);
    	
    	dbHelper.deleteAlarm(alarmId);
    	aa.clear();
    	List<Alarm_t> alarms = dbHelper.getAlarms();
    	if(alarms != null){
	    	aa.addAll(alarms);
	    	aa.notifyDataSetChanged();
    	}
    	
    	AlarmManagerHelper.setAlarms(context);
    	
    }
}
