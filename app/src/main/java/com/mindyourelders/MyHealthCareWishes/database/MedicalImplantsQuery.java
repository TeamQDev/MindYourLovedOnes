package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */

public class MedicalImplantsQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "ImplantsInfo";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_IMPLANTS= "Implants";

    public MedicalImplantsQuery(Context context, DBHelper dbHelper) {
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
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    arrayList.add(c.getString(c.getColumnIndex(COL_IMPLANTS)));
                } while (c.moveToNext());
            }
        }

        return arrayList;
    }
}
