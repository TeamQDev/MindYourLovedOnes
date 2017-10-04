package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Specialist;

import java.util.ArrayList;

/**
 * Created by welcome on 9/28/2017.
 */

public class SpecialistQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "Specialist";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_EMAIL = "Email";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_MOBILE = "Mobile";
    public static final String COL_HOME_PHONE = "HomePhone";
    public static final String COL_WORK_PHONE = "WorkPhone";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_SPECIALITY = "Speciality";
    public static final String COL_PRACTICENAME = "PracticeName";
    public static final String COL_FAXNO = "Faxno";
    public static final String COL_NETWORK = "NetworkStats";
    public static final String COL_AFFIL = "Affilitations";
    public static final String COL_ISPHISYCIAN = "IsPhysician";
    public static final String COL_NOTE = "Note";

    public SpecialistQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static ArrayList<Specialist> fetchAllPhysicianRecord(int id, int physician) {
        ArrayList<Specialist> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id + " and " + COL_ISPHISYCIAN + "=" + physician + ";";
        Cursor c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {

                    Specialist connection = new Specialist();
                    connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                    connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                    connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                    connection.setType(c.getString(c.getColumnIndex(COL_SPECIALITY)));
                    connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                    connection.setNetwork(c.getString(c.getColumnIndex(COL_NETWORK)));
                    connection.setFax(c.getString(c.getColumnIndex(COL_FAXNO)));
                    connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                    connection.setIsPhysician(c.getInt(c.getColumnIndex(COL_ISPHISYCIAN)));

                    connectionList.add(connection);

                } while (c.moveToNext());
            }


        return connectionList;
    }
    public static Boolean insertPhysicianData(int userId, String name, String email, String address, String mobile, String phone, String workphone, String speciality, byte[] photo, String fax, String practice_name, String network, String affil, String note, int i) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userId);
        cv.put(COL_NAME,name);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_MOBILE,mobile);
        cv.put(COL_HOME_PHONE,phone);
        cv.put(COL_WORK_PHONE,workphone);
        cv.put(COL_NOTE,note);
        cv.put(COL_NETWORK,network);
        cv.put(COL_ISPHISYCIAN,i);
        cv.put(COL_AFFIL,affil);
        cv.put(COL_PRACTICENAME,practice_name);
        cv.put(COL_SPECIALITY,speciality);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAXNO,fax);

        long rowid=db.insert(TABLE_NAME,null,cv);

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

    public static String createDoctorTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_EMAIL + " VARCHAR(50)," +
                COL_HOME_PHONE + " VARCHAR(20)," + COL_WORK_PHONE + " VARCHAR(20)," + COL_ADDRESS + " VARCHAR(100)," +
                COL_MOBILE + " VARCHAR(20)," + COL_SPECIALITY + " VARCHAR(50)," + COL_PRACTICENAME + " VARCHAR(30)," + COL_FAXNO +
                " VARCHAR(20)," + COL_ISPHISYCIAN + " INTEGER," +
                COL_NETWORK + " VARCHAR(50)," + COL_AFFIL + " VARCHAR(50)," + COL_NOTE + " VARCHAR(50)," +
                COL_PHOTO + " BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }
}
