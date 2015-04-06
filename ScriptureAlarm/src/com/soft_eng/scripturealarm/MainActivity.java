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


public class MainActivity extends Activity {
	
	private static Alarm_t alarmOne;
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
        
        
        // set listview
        AlarmDBHelper dbHelper = new AlarmDBHelper(this);
        
        List<Alarm_t> alarms = dbHelper.getAlarms();
        if (alarms != null){
	        aa = new AlarmAdapter(this, R.layout.alarm_list_item, alarms);
	        lvAlarms.setAdapter(aa);
        }
        
    }
    
    public static Alarm_t getAlarm(){
    	return alarmOne;
    }
    
    public void startAlarmDetailsActivity(long id){
    	Intent intent = new Intent(this, AlarmDetails.class);
    	intent.putExtra("id", id);
    	startActivityForResult(intent, 0);
    }
    
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
