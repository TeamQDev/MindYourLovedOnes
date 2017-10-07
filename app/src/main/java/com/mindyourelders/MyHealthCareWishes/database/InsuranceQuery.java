package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Insurance;

import java.util.ArrayList;

/**
 * Created by welcome on 10/7/2017.
 */

public class InsuranceQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "Insurance";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "InsuranceType";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_NOTE = "Note";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_MEMBERID = "MemberId";
    public static final String COL_GROUP = "GroupId";
    public static final String COL_SUBSCRIBER = "Subscriber";


    public InsuranceQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }


    public static String createInsuranceTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_WEBSITE + " VARCHAR(50),"
                + COL_TYPE + " VARCHAR(100)," + COL_OFFICE_PHONE + " VARCHAR(20),"+COL_FAX +
                " VARCHAR(20)," + COL_NOTE + " VARCHAR(50)," +COL_MEMBERID + " VARCHAR(50),"+
                COL_SUBSCRIBER + " VARCHAR(50)," +COL_GROUP+" VARCHAR(50)," +
                COL_PHOTO + " BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }
    public static ArrayList<Insurance> fetchAllInsuranceRecord(int userid) {
        ArrayList<Insurance> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Insurance notes = new Insurance();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setPhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                    notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                    notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setMember(c.getString(c.getColumnIndex(COL_MEMBERID)));
                    notes.setGroup(c.getString(c.getColumnIndex(COL_GROUP)));
                    notes.setSubscriber(c.getString(c.getColumnIndex(COL_SUBSCRIBER)));
                    notes.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return noteList;
    }
    public static Boolean insertInsuranceData(int userid, String name, String website, String type, String phone, byte[] photo, String fax, String note,String member,String group,String subscriber) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userid);
        cv.put(COL_NAME,name);
        cv.put(COL_WEBSITE,website);
        cv.put(COL_GROUP,group);
        cv.put(COL_MEMBERID,member);
        cv.put(COL_TYPE,type);
        cv.put(COL_OFFICE_PHONE,phone);
        cv.put(COL_NOTE,note);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAX,fax);
        cv.put(COL_SUBSCRIBER,subscriber);

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

}