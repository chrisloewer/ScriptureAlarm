/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmContract.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: creates basis for database

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

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
