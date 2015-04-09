package com.soft_eng.scripturealarm;

import android.provider.BaseColumns;

// Base for setting up database
public class ScriptureContract {

	public ScriptureContract() {}
	
	public static abstract class Scripture implements BaseColumns{
		public static final String TABLE_NAME = "scripture";
		public static final String COLUMN_NAME_REFERENCE = "reference";
		public static final String COLUMN_NAME_TEXT = "text";
	}

}
