package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Implant;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */

public class ImplantQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "ImplantsInfo";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_DATE = "Date";


    public ImplantQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createVaccineTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_NAME + " VARCHAR(100)," + COL_DATE + " VARCHAR(100)" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertImplantData(int userid, String value, String date) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_NAME, value);
        cv.put(COL_DATE, date);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<Implant> fetchAllRecord(int userid) {
        ArrayList<Implant> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Implant allergy = new Implant();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    allergy.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }

    public static Boolean updateImplantData(int id, String value, String date) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_NAME, value);
        cv.put(COL_DATE, date);
        int rowid=db.update(TABLE_NAME,cv,COL_ID+"="+id,null);

        if (rowid==0)
        {
            flag=false;
        }
        else
        {
            flag=true;
        }

        return flag;
    }

    public static boolean deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id+"';");
            } while (c.moveToNext());
        }

        return true;
    }

}
