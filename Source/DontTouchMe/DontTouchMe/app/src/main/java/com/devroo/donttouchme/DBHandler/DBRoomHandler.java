package com.devroo.donttouchme.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devroo.donttouchme.Class.CDate;
import com.devroo.donttouchme.Class.CRoom;

import java.util.ArrayList;
import java.util.List;

public class DBRoomHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "5MM";

    // Contacts table name
    private static final String TABLE = "TB_SETTING_LOCATION";

    // Contacts Table Columns names
    private static final String _SN = "SN";
    private static final String _STATUS = "STATUS";
    private static final String _LOCATION_CD = "LOCATION_CD";
    private static final String _LOCATION_NM = "LOCATION_NM";
    private static final String _LATITUDE = "LATITUDE";
    private static final String _LONGITUDE = "LONGITUDE";
    private static final String _REMARK = "REMARK";
    private static final String _REG_ID = "REG_ID";
    private static final String _REG_DTM = "REG_DTM";
    private static final String _MOD_ID = "MOD_ID";
    private static final String _MOD_DTM = "MOD_DTM";

    public DBRoomHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE + "("
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
    public void addRow(CRoom cRoom) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_LOCATION_CD, cRoom.getLocationCd());
        values.put(_LOCATION_NM, cRoom.getLocationNm());
        values.put(_LATITUDE, cRoom.getLatitude());
        values.put(_LONGITUDE, cRoom.getLongitude());

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
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
    */
    // Getting All Contacts
    public List<CRoom> getAllRows() {
        List<CRoom> roomList = new ArrayList<CRoom>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CRoom cRoom = new CRoom();
                cRoom.setSN(Integer.parseInt(cursor.getString(0)));
                cRoom.setLocationCd(cursor.getString(1));
                cRoom.setLocationNm(cursor.getString(2));
                cRoom.setLatitude(cursor.getString(3));
                cRoom.setLongitude(cursor.getString(4));
                // Adding contact to list
                roomList.add(cRoom);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return roomList;
    }

    // Deleting single contact
    public void deleteRow(CRoom cRoom) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, _SN + " = ?",
                new String[] { String.valueOf(cRoom.getSN()) });
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
