package com.soft_eng.scripturealarm;

import java.util.ArrayList;
import java.util.List;

import com.soft_eng.scripturealarm.AlarmContract.Alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AlarmDBHelper extends SQLiteOpenHelper{

	public static int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "alarm.db";
	
	// Set up SQL commands using abstract class from AlarmContract
	private static final String SQL_CREATE_ALARM = "CREATE TABLE " + Alarm.TABLE_NAME + " (" +
			Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			Alarm.COLUMN_NAME_HOUR + " INTEGER," +
			Alarm.COLUMN_NAME_MINUTE + " INTEGER," +
			Alarm.COLUMN_NAME_ENABLED + " BOOLEAN" +
	    " )";
	
	private static final String SQL_DELETE_ALARM =
			"DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;
	
	// Constructor
	public AlarmDBHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ALARM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ALARM);
		onCreate(db);
	}
	
	private Alarm_t populateAlarm(Cursor c){
		Alarm_t alarmModel = new Alarm_t();
		alarmModel.id = c.getLong(c.getColumnIndex(Alarm._ID));
		alarmModel.hour = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_HOUR));
		alarmModel.minute = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_MINUTE));
		alarmModel.enabled = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ENABLED)) == 0 ? false : true;
		
		return alarmModel;
	}
	
	private ContentValues populateContent(Alarm_t alarmModel){
		ContentValues values = new ContentValues();
		values.put(Alarm.COLUMN_NAME_HOUR, alarmModel.hour);
		values.put(Alarm.COLUMN_NAME_MINUTE, alarmModel.minute);
		values.put(Alarm.COLUMN_NAME_ENABLED, alarmModel.enabled);
		
		return values;
	}
	
	// Public methods for setting/updating alarms
	
	public long createAlarm(Alarm_t alarmModel){
		
		Log.d("myTag", "Alarm should be created in db");

		ContentValues values = populateContent(alarmModel);		
		return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);
		
	}
	
	public Alarm_t getAlarm(long id){
		SQLiteDatabase db = getWritableDatabase();
		
		String select = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE " + Alarm._ID + " = " + id;
		Cursor c = db.rawQuery(select, null);
		if (c.moveToNext())
			return populateAlarm(c);
		
		return null;
	}
	
	public long updateAlarm(Alarm_t alarmModel){
		ContentValues values = populateContent(alarmModel);
		return getWritableDatabase().update(Alarm.TABLE_NAME, values, Alarm._ID + " = ?", new String[] { String.valueOf(alarmModel.id) });
	}
	
	public int deleteAlarm(long id) {
		return getWritableDatabase().delete(Alarm.TABLE_NAME, Alarm._ID + " = ?", new String[] { String.valueOf(id) });
	}
	
	public List<Alarm_t> getAlarms(){
		SQLiteDatabase db = this.getReadableDatabase();
		String select = "SELECT * FROM " + Alarm.TABLE_NAME;
		Cursor c = db.rawQuery(select, null);
		
		List<Alarm_t> alarmList = new ArrayList<Alarm_t>();
		
		while(c.moveToNext())
			alarmList.add(populateAlarm(c));
		
		if(!alarmList.isEmpty())
			return alarmList;
		
		return null;
	}
}
