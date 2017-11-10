package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.History;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */

public class HistoryQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "HistoryInfo";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_HISTORY= "History";
    public static final String COL_DATE= "Date";
    public static final String COL_DOCTOR= "Doctor";
    public static final String COL_DONE= "Done";

    public HistoryQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createHistoryTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_HISTORY + " VARCHAR(100),"+ COL_DATE + " VARCHAR(20),"+ COL_DOCTOR + " VARCHAR(50),"+ COL_DONE + " VARCHAR(50)"+
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertHistoryData(int userid, String value, String date, String doctor, String done) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_HISTORY, value);
        cv.put(COL_DATE, date);
        cv.put(COL_DOCTOR, doctor);
        cv.put(COL_DONE, done);


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
                    arrayList.add(c.getString(c.getColumnIndex(COL_HISTORY)));
                } while (c.moveToNext());
            }
        }

        return arrayList;
    }

    public static boolean deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id +"';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';");
            } while (c.moveToNext());
        }

        return true;
    }

    public static Boolean updateHistoryData(int id, String value, String date, String doctor, String done) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        //cv.put(COL_USERID,userid);
        cv.put(COL_HISTORY, value);
        cv.put(COL_DATE, date);
        cv.put(COL_DOCTOR, R.drawable.doctor);
        cv.put(COL_DONE, done);


        //int rowid=db.update(TABLE_NAME,cv,COL_IMPLANTS + "='" + value + "' and "+COL_USERID+"=" + userid,null);
        int rowid=db.update(TABLE_NAME,
                cv,
                COL_ID + " = "+id,
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

    public static ArrayList<History> fetchHistoryRecord(int userid) {
        ArrayList<History> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    History allergy = new History();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setName(c.getString(c.getColumnIndex(COL_HISTORY)));
                    allergy.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    allergy.setDoctor(c.getString(c.getColumnIndex(COL_DOCTOR)));
                    allergy.setDone(c.getString(c.getColumnIndex(COL_DONE)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }
}
