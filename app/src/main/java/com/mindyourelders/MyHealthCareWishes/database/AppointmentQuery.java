package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Appoint;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */

public class AppointmentQuery {
   Context context;
    static DBHelper dbHelper;
    //ListView lvNote;

    public static final String TABLE_NAME = "Appointment";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_TYPE = "Type";
    public static final String COL_DOCTORNAME = "Doctor";
    public static final String COL_FREQUENCY = "Frequency";
    public static final String COL_DATE_TIME = "DateTime";

    public AppointmentQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createAppointmentTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER,"+ COL_TYPE + " VARCHAR(50)," + COL_DOCTORNAME + " VARCHAR(50),"+ COL_FREQUENCY + " VARCHAR(50)," + COL_DATE_TIME + " VARCHAR(20));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertAppointmentData(int userid, String name, String date, String type, String frequency) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_TYPE, type);
        cv.put(COL_FREQUENCY, frequency);
        cv.put(COL_DOCTORNAME, name);
        cv.put(COL_DATE_TIME, date);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<Appoint> fetchAllAppointmentRecord(int userid) {
        ArrayList<Appoint> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid + "';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Appoint notes = new Appoint();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setDoctor(c.getString(c.getColumnIndex(COL_DOCTORNAME)));
                    notes.setFrequency(c.getString(c.getColumnIndex(COL_FREQUENCY)));
                    notes.setDate(c.getString(c.getColumnIndex(COL_DATE_TIME)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));

                    noteList.add(notes);


                } while (c.moveToNext());
            }
        }

        return noteList;
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

    public static Boolean updateDate(int id, String date) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_DATE_TIME, date);

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

   /* public static Boolean updateEvent(int id, String note, String date) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, id);
        cv.put(COL_NOTE, note);
        cv.put(COL_DATE_TIME, date);

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
    }*/
}