package com.soft_eng.scripturealarm;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class AlarmAdapter extends ArrayAdapter<Alarm_t>{
	
	private final Context context;
	private List<Alarm_t> alarms;

	public AlarmAdapter(Context context, int resource, List<Alarm_t> alarms) {
		super(context, R.layout.alarm_list_item, alarms);
		this.context = context;
		this.alarms = alarms;
	}
	
	public void setAlarms(List<Alarm_t> alarms){
		this.alarms = alarms;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View liView = inflater.inflate(R.layout.alarm_list_item, parent, false);
		// TODO implement view holder for smoother scrolling
		
		TextView timeTV = (TextView) liView.findViewById(R.id.time_tv);
		Button deleteButton = (Button)liView.findViewById(R.id.delete_button);
		
		// Set times of the alarms in the list
		Alarm_t alarm = alarms.get(position);
		String timeToShow = String.format("%02d : %02d", alarm.hour, alarm.minute);
		timeTV.setText(timeToShow);
		
		// Button should delete alarm
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("myTag","Delete Button clicked");
				Alarm_t tempAlarm = alarms.get(position); 
				long id = tempAlarm.id;
				Log.d("myTag", "long id :" + id);
				((MainActivity) context).deleteAlarm(id);
			}
		});
		
		return liView;
	}
}