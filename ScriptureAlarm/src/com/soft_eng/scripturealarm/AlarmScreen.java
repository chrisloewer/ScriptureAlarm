package com.soft_eng.scripturealarm;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class AlarmScreen extends Activity{
	
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
