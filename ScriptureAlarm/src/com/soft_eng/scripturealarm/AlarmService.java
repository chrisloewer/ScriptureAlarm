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
