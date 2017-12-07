package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Hospital;

import java.util.ArrayList;

/**
 * Created by V@iBh@V on 10/23/2017.
 */

public class HospitalHealthQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "HospitalHealth";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_OTHER_PHONE = "OtherPhone";
    public static final String COL_CATEGORY = "Category";
    public static final String COL_OTHER_CATEGORY = "OtherCategory";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_PRACTICENAME = "PracticeName";
    public static final String COL_NOTE = "Note";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_LASTSEEN = "LastSeen";
    public static final String COL_LOCATION= "Location";
    public static final String COL_PHOTOCARD= "PhotoCard";


    public HospitalHealthQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }


    public static Boolean insertHospitalHealthData(int userId, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, byte[] photo, String fax, String practice_name, String note, String lastseen, String otherCategory, byte[] photoCard, String location) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userId);
        cv.put(COL_NAME,name);
        cv.put(COL_WEBSITE,website);
        cv.put(COL_LASTSEEN,lastseen);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_OFFICE_PHONE,officephone);
        cv.put(COL_LOCATION,location);
        cv.put(COL_OTHER_PHONE,otherphone);
        cv.put(COL_NOTE,note);
        cv.put(COL_PRACTICENAME,practice_name);
        cv.put(COL_CATEGORY,speciality);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAX,fax);
        cv.put(COL_OTHER_CATEGORY,otherCategory);
        cv.put(COL_PHOTOCARD,photoCard);


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

    public static String createHospitalHealthTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_WEBSITE + " VARCHAR(50)," + COL_LASTSEEN + " VARCHAR(50),"+
                COL_LOCATION + " VARCHAR(20)," + COL_OTHER_PHONE + " VARCHAR(20)," + COL_ADDRESS + " VARCHAR(100)," +
                COL_OFFICE_PHONE + " VARCHAR(20)," + COL_CATEGORY + " VARCHAR(50)," + COL_OTHER_CATEGORY + " VARCHAR(50)," + COL_PRACTICENAME + " VARCHAR(30)," + COL_FAX +
                " VARCHAR(20)," + COL_NOTE + " VARCHAR(50)," +
                COL_PHOTOCARD+" BLOB,"+
                COL_PHOTO + " BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean updateHospitalHealthData(int id, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, byte[] photo, String fax, String practice_name, String note, String lastseen, String otherCategory, byte[] photoCard, String location) {

        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_NAME,name);
        cv.put(COL_WEBSITE,website);
        cv.put(COL_LASTSEEN,lastseen);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_OFFICE_PHONE,officephone);
        cv.put(COL_LOCATION,location);
        cv.put(COL_OTHER_PHONE,otherphone);
        cv.put(COL_NOTE,note);
        cv.put(COL_PRACTICENAME,practice_name);
        cv.put(COL_CATEGORY,speciality);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAX,fax);
        cv.put(COL_OTHER_CATEGORY,otherCategory);
        cv.put(COL_PHOTOCARD,photoCard);


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
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id +"';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id+"';");
            } while (c.moveToNext());
        }

        return true;
    }


    public static ArrayList<Hospital> fetchAllHospitalhealthRecord(int id) {
        ArrayList<Hospital> hospitalList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query="select * from " + TABLE_NAME +" where " + COL_USER_ID + "=" + id+ ";";
        Cursor c = db.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {

                Hospital connection = new Hospital();
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                connection.setLastseen(c.getString(c.getColumnIndex(COL_LASTSEEN)));
                connection.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                connection.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                connection.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                connection.setCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
                connection.setPracticeName(c.getString(c.getColumnIndex(COL_PRACTICENAME)));
                connection.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherCategory(c.getString(c.getColumnIndex(COL_OTHER_CATEGORY)));
                connection.setPhotoCard(c.getBlob(c.getColumnIndex(COL_PHOTOCARD)));


                hospitalList.add(connection);

            } while (c.moveToNext());
        }


        return hospitalList;
    }
}
