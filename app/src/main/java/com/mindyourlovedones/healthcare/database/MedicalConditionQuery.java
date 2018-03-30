package com.mindyourlovedones.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */

public class MedicalConditionQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "ConditionInfo";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_IMPLANTS= "Implants";

    public MedicalConditionQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createImplantsTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_IMPLANTS + " VARCHAR(100)"+
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertImplantsData(int userid, String value) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_IMPLANTS, value);


        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<String> fetchAllRecord(int userid) {
        ArrayList<String> arrayList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + ";", null);

        //Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    arrayList.add(c.getString(c.getColumnIndex(COL_IMPLANTS)));
                } while (c.moveToNext());
            }
        }

        return arrayList;
    }

    public static boolean deleteRecord(int userid,String s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_IMPLANTS + "='" + s + "' and "+COL_USERID+"='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_IMPLANTS + "='" + s+ "' and "+COL_USERID+"='" + userid + "';");
            } while (c.moveToNext());
        }

        return true;
    }

    public static Boolean updateImplantsData(int userid, String value, String name) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        //cv.put(COL_USERID,userid);
        cv.put(COL_IMPLANTS, value);

        //int rowid=db.update(TABLE_NAME,cv,COL_IMPLANTS + "='" + value + "' and "+COL_USERID+"=" + userid,null);
        int rowid=db.update(TABLE_NAME,
                cv,
                COL_USERID + " = "+userid+" AND " + COL_IMPLANTS + " = '"+name+"'",
               null);

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
}
