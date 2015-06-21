package com.devroo.donttouchme.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.devroo.donttouchme.Class.CCalendar;
import com.devroo.donttouchme.Class.CDate;

import java.util.ArrayList;
import java.util.List;

public class DBScheduleHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "5MM";

    // Contacts table name
    private static final String TABLE = "TB_CALENDAR";

    // Contacts Table Columns names
    private static final String _SN = "SN";
    private static final String _TYPE_CD = "TYPE_CD";
    private static final String _TYPE_NM = "TYPE_NM";
    private static final String _V_DTM = "V_DTM";
    private static final String _TITLE = "TITLE";
    private static final String _SUBJECT = "SUBJECT";
    private static final String _COMP_YN = "COMP_YN";
    private static final String _OUT_YN = "OUT_YN";
    private static final String _S_TIME = "S_TIME";
    private static final String _E_TIME = "E_TIME";
    private static final String _CONTENTS = "CONTENTS";
    private static final String _STATUS = "STATUS";
    private static final String _REMARK = "REMARK";
    private static final String _REG_ID = "REG_ID";
    private static final String _REG_DTM = "REG_DTM";
    private static final String _MOD_ID = "MOD_ID";
    private static final String _MOD_DTM = "MOD_DTM";

    public DBScheduleHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE + "("
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
    public void addRow(CCalendar cCalendar) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_TYPE_CD, cCalendar.getTypeCd());
        values.put(_TYPE_NM, cCalendar.getTypeNm());
        values.put(_V_DTM, cCalendar.getVDtm());
        values.put(_TITLE, cCalendar.getTitle());
        values.put(_SUBJECT, cCalendar.getSubject());
        values.put(_COMP_YN, cCalendar.getCompYn());
        values.put(_OUT_YN, cCalendar.getOutYn());
        values.put(_S_TIME, cCalendar.getSTime());
        values.put(_E_TIME, cCalendar.getETime());
        values.put(_CONTENTS, cCalendar.getContents());

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
    public List<CCalendar> getAllRows() {
        List<CCalendar> calList = new ArrayList<CCalendar>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CCalendar cCalendar = new CCalendar();
                cCalendar.setSN(Integer.parseInt(cursor.getString(0)));
                cCalendar.setTypeCd(cursor.getString(1));
                cCalendar.setTypeNm(cursor.getString(2));
                cCalendar.setVDtm(cursor.getString(3));
                cCalendar.setTitle(cursor.getString(4));
                cCalendar.setSubject(cursor.getString(5));
                cCalendar.setCompYn(cursor.getString(6));
                cCalendar.setOutYn(cursor.getString(7));
                cCalendar.setSTime(cursor.getString(8));
                cCalendar.setETime(cursor.getString(9));
                cCalendar.setContents(cursor.getString(10));
                // Adding contact to list
                calList.add(cCalendar);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return calList;
    }

    public List<CCalendar> getAllRows(String vDtm) {
        List<CCalendar> calList = new ArrayList<CCalendar>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE + " WHERE " + _V_DTM + " = '" + vDtm + "' ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CCalendar cCalendar = new CCalendar();
                cCalendar.setSN(Integer.parseInt(cursor.getString(0)));
                cCalendar.setTypeCd(cursor.getString(1));
                cCalendar.setTypeNm(cursor.getString(2));
                cCalendar.setVDtm(cursor.getString(3));
                cCalendar.setTitle(cursor.getString(4));
                cCalendar.setSubject(cursor.getString(5));
                cCalendar.setCompYn(cursor.getString(6));
                cCalendar.setOutYn(cursor.getString(7));
                cCalendar.setSTime(cursor.getString(8));
                cCalendar.setETime(cursor.getString(9));
                cCalendar.setContents(cursor.getString(10));
                // Adding contact to list
                calList.add(cCalendar);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return contact list
        return calList;
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
    // Deleting single contact
    public void deleteRow(CCalendar cCalendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, _SN + " = ?",
                new String[] { String.valueOf(cCalendar.getSN()) });
        db.close();
    }


    public String getTypeList(String vDtm) {
        String retVal = "";
        if (this.getRowCount(vDtm) > 0) {

            int type1 = 0, type2 = 0;
            String countQuery = "SELECT COUNT(*) FROM " + TABLE + " WHERE " + _V_DTM + " = '" + vDtm + "' AND " + _TYPE_NM + " = '과제' ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.moveToFirst();
            type1 = cursor.getInt(0);

            String countQuery2 = "SELECT COUNT(*) FROM " + TABLE + " WHERE " + _V_DTM + " = '" + vDtm + "' AND " + _TYPE_NM + " = '스케줄' ";
            SQLiteDatabase db2 = this.getWritableDatabase();
            Cursor cursor2 = db2.rawQuery(countQuery2, null);
            cursor2.moveToFirst();
            type2 = cursor2.getInt(0);

            if (type1 > 0 && type2 > 0) {
                retVal = "Both";
            }
            else if (type1 > 0 && type2 < 1) {
                retVal = "Subj";
            }
            else if (type1 < 1 && type2 > 0) {
                retVal = "Sch";
            }
            else {
                retVal = "None";
            }

            cursor.close();
            cursor2.close();

        } else {
            retVal = "None";
        }
        return retVal;
    }

    // Getting contacts Count
    public int getRowCount(String vDtm) {
        String countQuery = "SELECT COUNT(*) FROM " + TABLE + " WHERE " + _V_DTM + " = '" + vDtm + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int retVal = cursor.getInt(0);
        cursor.close();

        // return count
        return retVal;
    }
}
