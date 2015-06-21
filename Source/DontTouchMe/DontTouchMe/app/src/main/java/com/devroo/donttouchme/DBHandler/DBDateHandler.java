package com.devroo.donttouchme.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devroo.donttouchme.Class.CDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBDateHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "5MM";

    // DATE COLUMN
    private static final String TABLE = "TB_DATE";

    private static final String _SN = "SN";
    private static final String _S_TIME = "S_TIME";
    private static final String _E_TIME = "E_TIME";
    private static final String _V_DATE = "V_DATE";
    private static final String _STATUS = "STATUS";
    private static final String _REMARK = "REMARK";
    private static final String _REG_ID = "REG_ID";
    private static final String _REG_DTM = "REG_DTM";
    private static final String _MOD_ID = "MOD_ID";
    private static final String _MOD_DTM = "MOD_DTM";

    // SETTING COLUMN
    private static final String TABLE2 = "TB_SETTING";

    private static final String _ALARM = "ALARM";
    private static final String _ALARM_URI = "ALARM_URI";
    private static final String _APP_OUT_YN = "APP_OUT_YN";
    private static final String _SUBJECT_DAYS = "SUBJECT_DAYS";
    private static final String _TIME_START = "TIME_START";
    private static final String _TIME_LECTURE = "TIME_LECTURE";
    private static final String _TIME_REST = "TIME_REST";

    // APPLICATION COLUMN
    private static final String TABLE3 = "TB_SETTING_OUT";

    private static final String _APP_ID = "APP_ID";
    private static final String _APP_NM = "APP_NM";

    // ROOM COLUMN
    private static final String TABLE4 = "TB_SETTING_LOCATION";

    private static final String _LOCATION_CD = "LOCATION_CD";
    private static final String _LOCATION_NM = "LOCATION_NM";
    private static final String _LATITUDE = "LATITUDE";
    private static final String _LONGITUDE = "LONGITUDE";

    // TIME COLUMN
    private static final String TABLE5 = "TB_TIME";

    private static final String _DAYS = "DAYS";
    private static final String _CLASS = "CLASS";
    private static final String _LECTURE = "LECTURE";
    private static final String _COLOR = "COLOR";
    private static final String _TITLE = "TITLE";
    private static final String _PROFESSOR = "PROFESSOR";
    private static final String _ROOM = "ROOM";

    // TIME COLUMN
    private static final String TABLE6 = "TB_CALENDAR";

    private static final String _TYPE_CD = "TYPE_CD";
    private static final String _TYPE_NM = "TYPE_NM";
    private static final String _V_DTM = "V_DTM";
    private static final String _SUBJECT = "SUBJECT";
    private static final String _COMP_YN = "COMP_YN";
    private static final String _OUT_YN = "OUT_YN";
    private static final String _CONTENTS = "CONTENTS";

    public DBDateHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE + "("
                + _SN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + _S_TIME + " TEXT,"
                + _E_TIME + " TEXT,"
                + _V_DATE + " TEXT,"
                + _STATUS + " TEXT,"
                + _REMARK + " TEXT,"
                + _REG_ID + " TEXT,"
                + _REG_DTM + " TEXT,"
                + _MOD_ID + " TEXT,"
                + _MOD_DTM + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE2 =
                "CREATE TABLE " + TABLE2 + "("
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
        db.execSQL(CREATE_TABLE2);

        String CREATE_TABLE3 =
                "CREATE TABLE " + TABLE3 + "("
                + _SN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + _APP_ID + " TEXT,"
                + _APP_NM + " TEXT,"
                + _REG_ID + " TEXT,"
                + _REG_DTM + " TEXT,"
                + _MOD_ID + " TEXT,"
                + _MOD_DTM + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE3);

        String CREATE_TABLE4 =
                "CREATE TABLE " + TABLE4 + "("
                        + _SN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                        + _LOCATION_CD + " TEXT,"
                        + _LOCATION_NM + " TEXT,"
                        + _LATITUDE + " TEXT,"
                        + _LONGITUDE + " TEXT,"
                        + _STATUS + " TEXT,"
                        + _REMARK + " TEXT,"
                        + _REG_ID + " TEXT,"
                        + _REG_DTM + " TEXT,"
                        + _MOD_ID + " TEXT,"
                        + _MOD_DTM + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE4);

        String CREATE_TABLE5 =
                "CREATE TABLE " + TABLE5 + "("
                        + _SN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                        + _DAYS + " TEXT,"
                        + _CLASS + " TEXT,"
                        + _LECTURE + " TEXT,"
                        + _COLOR + " TEXT,"
                        + _TITLE + " TEXT,"
                        + _PROFESSOR + " TEXT,"
                        + _ROOM + " TEXT,"
                        + _LOCATION_CD + " TEXT,"
                        + _LOCATION_NM + " TEXT,"
                        + _STATUS + " TEXT,"
                        + _REMARK + " TEXT,"
                        + _REG_ID + " TEXT,"
                        + _REG_DTM + " TEXT,"
                        + _MOD_ID + " TEXT,"
                        + _MOD_DTM + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE5);

        String CREATE_TABLE6 =
                "CREATE TABLE " + TABLE6 + "("
                        + _SN + " INTEGER PRIMARY KEY,"
                        + _TYPE_CD + " TEXT,"
                        + _TYPE_NM + " TEXT,"
                        + _V_DTM + " TEXT,"
                        + _TITLE + " TEXT,"
                        + _SUBJECT + " TEXT,"
                        + _COMP_YN + " TEXT,"
                        + _OUT_YN + " TEXT,"
                        + _S_TIME + " TEXT,"
                        + _E_TIME + " TEXT,"
                        + _CONTENTS + " TEXT,"
                        + _STATUS + " TEXT,"
                        + _REMARK + " TEXT,"
                        + _REG_ID + " TEXT,"
                        + _REG_DTM + " TEXT,"
                        + _MOD_ID + " TEXT,"
                        + _MOD_DTM + " TEXT"
                        + ")";
        db.execSQL(CREATE_TABLE6);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE6);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addRow(CDate cDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_S_TIME, cDate.getSTime());
        values.put(_E_TIME, cDate.getETime());
        values.put(_V_DATE, cDate.getVDate());

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public CDate getRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { _SN,
                        _S_TIME, _E_TIME, _V_DATE}, _SN + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CDate cDate = new CDate(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        cursor.close();
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
        cursor.close();

        // return contact list
        return dateList;
    }

    public List<CDate> getAllRows(Date days) {
        List<CDate> dateList = new ArrayList<CDate>();
        // Select All Query

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + _V_DATE + " = '" + sdf.format(days) + "' ";;

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
        cursor.close();

        // return contact list
        return dateList;
    }

    // Updating single contact
    public int updateRow(CDate cDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_S_TIME, cDate.getSTime());
        values.put(_E_TIME, cDate.getETime());
        values.put(_V_DATE, cDate.getVDate());

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
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }
}
