package com.soft_eng.scripturealarm;

import android.net.Uri;

public class Alarm_t {
	public long id;
	public int hour;
	public int minute;
	public Boolean enabled;
	public Uri tone;

	private Boolean repeatingDays[];
	
	public Alarm_t() {
		repeatingDays = new Boolean[7];

		// repeat every day
		// Choosing days may be added later
		for(int i=0; i<7; i++)
			repeatingDays[i]=true;
	}
}
