/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: Alarm_t.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: create object to use as datatype

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/


package com.soft_eng.scripturealarm;

import android.net.Uri;

public class Alarm_t {
	public long id;
	public int hour;
	public int minute;
	public Boolean enabled;
	public Uri tone;

	// Repeating days not used
	// private Boolean repeatingDays[];
	
	public Alarm_t() {
		// repeatingDays = new Boolean[7];

		// repeat every day
		// Choosing days may be added later
		/*for(int i=0; i<7; i++)
			repeatingDays[i]=true;*/
	}
}
