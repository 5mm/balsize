package com.devroo.donttouchme.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.Class.CTime;

import java.util.ArrayList;
import java.util.List;

public class DBTimeHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "5MM";

    // Contacts table name
    private static final String TABLE = "TB_TIME";

    // Contacts Table Columns names
    private static final String _SN = "SN";
    private static final String _DAYS = "DAYS";
    private static final String _CLASS = "CLASS";
    private static final String _LECTURE = "LECTURE";
    private static final String _COLOR = "COLOR";
    private static final String _TITLE = "TITLE";
    private static final String _PROFESSOR = "PROFESSOR";
    private static final String _ROOM = "ROOM";
    private static final String _LOCATION_CD = "LOCATION_CD";
    private static final String _LOCATION_NM = "LOCATION_NM";
    private static final String _STATUS = "STATUS";
    private static final String _REMARK = "REMARK";
    private static final String _REG_ID = "REG_ID";
    private static final String _REG_DTM = "REG_DTM";
    private static final String _MOD_ID = "MOD_ID";
    private static final String _MOD_DTM = "MOD_DTM";

    public DBTimeHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE + "("
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

    // Adding new contact
    public void addRow(CTime cTime) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_DAYS, cTime.getDays());
        values.put(_CLASS, cTime.getClasses());
        values.put(_LECTURE, cTime.getLecture());
        values.put(_COLOR, cTime.getColor());
        values.put(_TITLE, cTime.getTitle());
        values.put(_PROFESSOR, cTime.getProfessor());
        values.put(_ROOM, cTime.getRoom());
        values.put(_LOCATION_CD, cTime.getLocationCd());
        values.put(_LOCATION_NM, cTime.getLocationNm());

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public CTime getRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { _SN,
                        _DAYS, _CLASS, _LECTURE, _COLOR, _TITLE, _PROFESSOR, _ROOM, _LOCATION_CD, _LOCATION_NM }, _SN + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CTime cTime = new CTime(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)
                , cursor.getString(7), cursor.getString(8), cursor.getString(9));

        // return contact
        return cTime;
    }

    // Getting All Contacts
    public List<CTime> getAllRows() {
        List<CTime> timeList = new ArrayList<CTime>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CTime cTime = new CTime();
                cTime.setSN(Integer.parseInt(cursor.getString(0)));
                cTime.setDays(cursor.getString(1));
                cTime.setClasses(cursor.getString(2));
                cTime.setLectrue(cursor.getString(3));
                cTime.setColor(cursor.getString(4));
                cTime.setTitle(cursor.getString(5));
                cTime.setProfessor(cursor.getString(6));
                cTime.setRoom(cursor.getString(7));
                cTime.setLocationCd(cursor.getString(8));
                cTime.setLocationNm(cursor.getString(9));
                // Adding contact to list
                timeList.add(cTime);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return timeList;
    }

    // Deleting single contact
    public void deleteRow(CTime cTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, _SN + " = ?",
                new String[] { String.valueOf(cTime.getSN()) });
        db.close();
    }

/*
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
*/

}
