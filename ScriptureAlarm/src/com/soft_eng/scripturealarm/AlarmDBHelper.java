/*-----------------------------------------
Student name: Chris Loewer
Course: COSC 3403 - Software Engineering I
Assignment: Implementation
File name: AlarmDBHelper.java
Program's Purpose: Android alarm clock that requires
user to complete a scripture verse to turn off

Class Purpose: creates SQLite database and CRUD methods

Development Computer: Intel 4790k chipset
Operating System: Windows 8.1
Integrated Development Environment (IDE): Eclipse
Compiler: jdk 8.0_31
Program's Operational Status: Working
-----------------------------------------*/

package com.soft_eng.scripturealarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.soft_eng.scripturealarm.AlarmContract.Alarm;
import com.soft_eng.scripturealarm.ScriptureContract.Scripture;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AlarmDBHelper extends SQLiteOpenHelper {

    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarm.db";

    // Set up SQL commands using abstract class from AlarmContract
    private static final String SQL_CREATE_ALARM = "CREATE TABLE " + Alarm.TABLE_NAME + " (" +
            Alarm._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Alarm.COLUMN_NAME_HOUR + " INTEGER," +
            Alarm.COLUMN_NAME_MINUTE + " INTEGER," +
            Alarm.COLUMN_NAME_ENABLED + " BOOLEAN" +
            " )";

    private static final String SQL_CREATE_SCRIPTURE = "CREATE TABLE " + Scripture.TABLE_NAME + " (" +
            Scripture._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Scripture.COLUMN_NAME_REFERENCE + " TEXT," +
            Scripture.COLUMN_NAME_TEXT + " TEXT" +
            " )";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXISTS " + Alarm.TABLE_NAME;

    // Constructor
    public AlarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_SCRIPTURE);
        db.execSQL(SQL_CREATE_ALARM);

        db.execSQL("INSERT INTO "
                + Scripture.TABLE_NAME
                + " (" + Scripture._ID + ", " + Scripture.COLUMN_NAME_REFERENCE + ", "
                + Scripture.COLUMN_NAME_TEXT + ") VALUES (1, 'James 1 5' ,'If any of you lacks wisdom you should ask God who gives generously to all without finding fault and it will be given to you' );");

        db.execSQL("INSERT INTO "
                + Scripture.TABLE_NAME
                + " (" + Scripture._ID + ", " + Scripture.COLUMN_NAME_REFERENCE + ", "
                + Scripture.COLUMN_NAME_TEXT + ") VALUES (2, 'John 3 16' ,'For God so loved the world that he gave his one and only Son, that whoever believes in him shall not perish but have eternal life' );");

        db.execSQL("INSERT INTO "
                + Scripture.TABLE_NAME
                + " (" + Scripture._ID + ", " + Scripture.COLUMN_NAME_REFERENCE + ", "
                + Scripture.COLUMN_NAME_TEXT + ") VALUES (3, 'Genesis 1 1' ,'In the beginning God created the heavens and the earth' );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
    }

    private Alarm_t populateAlarm(Cursor c) {
        Alarm_t alarmModel = new Alarm_t();
        alarmModel.id = c.getLong(c.getColumnIndex(Alarm._ID));
        alarmModel.hour = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_HOUR));
        alarmModel.minute = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_MINUTE));
        alarmModel.enabled = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ENABLED)) == 0 ? false : true;

        return alarmModel;
    }

    private ContentValues populateContent(Alarm_t alarmModel) {
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_HOUR, alarmModel.hour);
        values.put(Alarm.COLUMN_NAME_MINUTE, alarmModel.minute);
        values.put(Alarm.COLUMN_NAME_ENABLED, alarmModel.enabled);

        return values;
    }

    // Public methods for setting/updating alarms

    public long createAlarm(Alarm_t alarmModel) {

        Log.d("myTag", "Alarm should be created in db");

        ContentValues values = populateContent(alarmModel);
        return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);

    }

    public Alarm_t getAlarm(long id) {
        SQLiteDatabase db = getWritableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE " + Alarm._ID + " = " + id;
        Cursor c = db.rawQuery(select, null);
        if (c.moveToNext())
            return populateAlarm(c);

        return null;
    }

    // Update not actually used
    public long updateAlarm(Alarm_t alarmModel) {
        ContentValues values = populateContent(alarmModel);
        return getWritableDatabase().update(Alarm.TABLE_NAME, values, Alarm._ID + " = ?", new String[]{String.valueOf(alarmModel.id)});
    }

    public int deleteAlarm(long id) {
        return getWritableDatabase().delete(Alarm.TABLE_NAME, Alarm._ID + " = ?", new String[]{String.valueOf(id)});
    }

    public List<Alarm_t> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + Alarm.TABLE_NAME;
        Cursor c = db.rawQuery(select, null);

        List<Alarm_t> alarmList = new ArrayList<Alarm_t>();

        while (c.moveToNext())
            alarmList.add(populateAlarm(c));

        if (!alarmList.isEmpty())
            return alarmList;

        return null;
    }

    public Scripture_t getRandomVerse() {
        Scripture_t scripture = new Scripture_t();
        SQLiteDatabase db = this.getReadableDatabase();
        Random rand = new Random();
        int randomNum = rand.nextInt(3) + 1;

        Cursor c = db.rawQuery("SELECT * FROM " + Scripture.TABLE_NAME + " WHERE "
                + Scripture._ID + " = " + randomNum, null);
        c.moveToFirst();

        scripture.id = c.getInt(c.getColumnIndex(Scripture._ID));
        scripture.reference = c.getString(c.getColumnIndex(Scripture.COLUMN_NAME_REFERENCE));
        scripture.text = c.getString(c.getColumnIndex(Scripture.COLUMN_NAME_TEXT));

        return scripture;
    }
}
