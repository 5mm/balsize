package com.devroo.donttouchme.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.devroo.donttouchme.Class.CSetting;

import java.util.ArrayList;
import java.util.List;

public class DBSettingHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "5MM";

    // Contacts table name
    private static final String TABLE = "TB_SETTING";

    // Contacts Table Columns names
    private static final String _SN = "SN";
    private static final String _ALARM = "ALARM";
    private static final String _ALARM_URI = "ALARM_URI";
    private static final String _APP_OUT_YN = "APP_OUT_YN";
    private static final String _SUBJECT_DAYS = "SUBJECT_DAYS";
    private static final String _TIME_START = "TIME_START";
    private static final String _TIME_LECTURE = "TIME_LECTURE";
    private static final String _TIME_REST = "TIME_REST";
    private static final String _REG_ID = "REG_ID";
    private static final String _REG_DTM = "REG_DTM";
    private static final String _MOD_ID = "MOD_ID";
    private static final String _MOD_DTM = "MOD_DTM";

    public DBSettingHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE + "("
                + _SN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + _ALARM + " TEXT,"
                + _ALARM_URI + " TEXT,"
                + _APP_OUT_YN + " TEXT,"
                + _SUBJECT_DAYS + " TEXT,"
                + _TIME_START + " TEXT,"
                + _TIME_LECTURE + " TEXT,"
                + _TIME_REST + " TEXT,"
                + _REG_ID + " TEXT,"
                + _REG_DTM + " TEXT,"
                + _MOD_ID + " TEXT,"
                + _MOD_DTM + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public void addAlarmRow(CSetting cSetting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ALARM, cSetting.getAlarm());
        values.put(_ALARM_URI, cSetting.getAlarmUri());

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    public int updateRow(CSetting cSetting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_ALARM, cSetting.getAlarm());
        values.put(_ALARM_URI, cSetting.getAlarmUri());
        values.put(_APP_OUT_YN, cSetting.getAppOutYn());
        values.put(_SUBJECT_DAYS, cSetting.getSubjectDays());
        values.put(_TIME_START, cSetting.getTimeStart());
        values.put(_TIME_LECTURE, cSetting.getTimeLecture());
        values.put(_TIME_REST, cSetting.getTimeRest());

        // updating row
        return db.update(TABLE, values, _SN + " = ?",
                new String[] { String.valueOf(cSetting.getSN()) });
    }

    public void addEmptyRow(CSetting cSetting) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_SN, 1);

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public CSetting getRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { _SN,
                        _ALARM, _ALARM_URI, _APP_OUT_YN, _SUBJECT_DAYS,
                        _TIME_START, _TIME_LECTURE, _TIME_REST }, _SN + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CSetting cSetting = new CSetting(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));

        // return contact
        return cSetting;
    }

    // Getting All Contacts
    public List<CSetting> getAllRows() {
        List<CSetting> settingList = new ArrayList<CSetting>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CSetting cSetting = new CSetting();
                cSetting.setSN(Integer.parseInt(cursor.getString(0)));
                cSetting.setAlarm(cursor.getString(1));
                cSetting.setAlarmUri(cursor.getString(2));
                cSetting.setAppOutYn(cursor.getString(3));
                cSetting.setSubjectDays(cursor.getString(4));
                cSetting.setTimeStart(cursor.getString(5));
                cSetting.setTimeLecture(cursor.getString(6));
                cSetting.setTimeRest(cursor.getString(7));
                // Adding cSetting to list
                settingList.add(cSetting);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return settingList;
    }

    // Getting contacts Count
    public int getRowCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }
/*

    // Getting single contact
    CDate getRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { _SN,
                        _S_TIME, _E_TIME, _V_Date }, _SN + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CDate cDate = new CDate(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        // return contact
        return cDate;
    }

    // Getting All Contacts
    public List<CDate> getAllRows() {
        List<CDate> dateList = new ArrayList<CDate>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CDate cDate = new CDate();
                cDate.setSN(Integer.parseInt(cursor.getString(0)));
                cDate.setSTime(cursor.getString(1));
                cDate.setETime(cursor.getString(2));
                cDate.setVDate(cursor.getString(3));
                // Adding contact to list
                dateList.add(cDate);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dateList;
    }

    // Updating single contact
    public int updateRow(CDate cDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_S_TIME, cDate.getSTime());
        values.put(_E_TIME, cDate.getETime());
        values.put(_V_Date, cDate.getVDate());

        // updating row
        return db.update(TABLE, values, _SN + " = ?",
                new String[] { String.valueOf(cDate.getSN()) });
    }

    // Deleting single contact
    public void deleteRow(CDate cDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, _SN + " = ?",
                new String[] { String.valueOf(cDate.getSN()) });
        db.close();
    }

    // Getting contacts Count
    public int getRowCount() {
        String countQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    */
}
