package com.devroo.donttouchme.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devroo.donttouchme.Class.CApp;
import com.devroo.donttouchme.Class.CDate;

import java.util.ArrayList;
import java.util.List;

public class DBApplicationHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "5MM";

    // Contacts table name
    private static final String TABLE = "TB_SETTING_OUT";

    // Contacts Table Columns names
    private static final String _SN = "SN";
    private static final String _APP_ID = "APP_ID";
    private static final String _APP_NM = "APP_NM";
    private static final String _REG_ID = "REG_ID";
    private static final String _REG_DTM = "REG_DTM";
    private static final String _MOD_ID = "MOD_ID";
    private static final String _MOD_DTM = "MOD_DTM";

    public DBApplicationHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE + "("
                + _SN + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + _APP_ID + " TEXT,"
                + _APP_NM + " TEXT,"
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
    public void addRow(CApp cApp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_APP_ID, cApp.getAppId());
        values.put(_APP_NM, cApp.getAppNm());

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close(); // Closing database connection
    }

    // Deleting single contact
    public void deleteRow(CApp cApp) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, _SN + " = ?",
                new String[]{String.valueOf(cApp.getSN())});
        db.close();
    }

    // Getting single contact
    public CApp getRow(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE, new String[] { _SN,
                        _APP_ID, _APP_NM}, _SN + "=?",
                        new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        CApp cApp = new CApp(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        cursor.close();
        // return contact
        return cApp;
    }

    // Getting All Contacts
    public List<CApp> getAllRows() {
        List<CApp> appList = new ArrayList<CApp>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    CApp cApp = new CApp();
                    cApp.setSN(Integer.parseInt(cursor.getString(0)));
                    cApp.setAppId(cursor.getString(1));
                    cApp.setAppNm(cursor.getString(2));
                    // Adding contact to list
                    appList.add(cApp);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
            db.close();
        }

        // return contact list
        return appList;
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
