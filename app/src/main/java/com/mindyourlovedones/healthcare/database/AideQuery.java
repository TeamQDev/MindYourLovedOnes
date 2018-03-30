package com.mindyourlovedones.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedones.healthcare.model.Aides;

import java.util.ArrayList;

/**
 * Created by welcome on 10/6/2017.
 */

public class AideQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "Aides";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_OFFICE_PHONE = "OfficePhone";
    public static final String COL_HOUR_PHONE = "AfterHourPhone";
    public static final String COL_OTHER_PHONE = "OtherPhone";
    public static final String COL_FAX = "Faxno";
    public static final String COL_WEBSITE = "Website";
    public static final String COL_EMAIL= "Email";
    public static final String COL_NOTE = "Note";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_PHOTOCARD= "PhotoCard";


    public AideQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }


    public static String createAideTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_WEBSITE + " VARCHAR(50)," + COL_ADDRESS + " VARCHAR(50),"
                + COL_EMAIL + " VARCHAR(50)," + COL_OFFICE_PHONE + " VARCHAR(20),"+ COL_HOUR_PHONE + " VARCHAR(20)," +COL_OTHER_PHONE + " VARCHAR(20)," +COL_FAX +
                " VARCHAR(20)," + COL_NOTE + " VARCHAR(50)," +
                COL_PHOTOCARD+" VARCHAR(50),"+
                COL_PHOTO + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<Aides> fetchAllAideRecord(int userid) {
        ArrayList<Aides> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME + ";",null);

        //Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Aides notes = new Aides();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setAidName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    notes.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    notes.setOfficePhone(c.getString(c.getColumnIndex(COL_OFFICE_PHONE)));
                    notes.setHourPhone(c.getString(c.getColumnIndex(COL_HOUR_PHONE)));
                    notes.setOtherPhone(c.getString(c.getColumnIndex(COL_OTHER_PHONE)));
                    notes.setFax(c.getString(c.getColumnIndex(COL_FAX)));
                    notes.setWebsite(c.getString(c.getColumnIndex(COL_WEBSITE)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    notes.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));


                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertAidesData(int userid, String name, String website, String email, String officephone, String hourPhone, String otherphone, String photo, String fax, String note, String address, String photoCard) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userid);
        cv.put(COL_NAME,name);
        cv.put(COL_WEBSITE,website);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_OFFICE_PHONE,officephone);
        cv.put(COL_HOUR_PHONE,hourPhone);
        cv.put(COL_OTHER_PHONE,otherphone);
        cv.put(COL_NOTE,note);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAX,fax);
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

    public static Boolean updateAideData(int id, String name, String website, String email, String officephone, String hourPhone, String otherphone, String photo, String fax, String note, String address, String photoCard) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_ID,id);
        cv.put(COL_NAME,name);
        cv.put(COL_WEBSITE,website);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_OFFICE_PHONE,officephone);
        cv.put(COL_HOUR_PHONE,hourPhone);
        cv.put(COL_OTHER_PHONE,otherphone);
        cv.put(COL_NOTE,note);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAX,fax);
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
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id+"';");
            } while (c.moveToNext());
        }

        return true;
    }
    }
