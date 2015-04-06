package com.soft_eng.scripturealarm;

import android.provider.BaseColumns;

// Base for setting up database
public class AlarmContract{
	
	public AlarmContract() {}
	
	public static abstract class Alarm implements BaseColumns{
		public static final String TABLE_NAME = "alarm";
		public static final String COLUMN_NAME_HOUR = "hour";
		public static final String COLUMN_NAME_MINUTE = "minute";
		public static final String COLUMN_NAME_ENABLED = "enabled";
	}

}
