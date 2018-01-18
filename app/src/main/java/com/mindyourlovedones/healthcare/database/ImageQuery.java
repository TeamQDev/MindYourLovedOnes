package com.mindyourlovedones.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedones.healthcare.model.PhotoImage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/28/2017.
 */

public class ImageQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "Images";

    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_LASTSEEN = "LastSeen";
    public static final String COL_PHOTOCARD= "PhotoCard";


    public ImageQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static ArrayList<PhotoImage> fetchImageRecord() {
        ArrayList<PhotoImage> connectionList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query="select * from " + TABLE_NAME + ";";
        Cursor c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {

                    PhotoImage connection = new PhotoImage();
                    connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    connectionList.add(connection);

                } while (c.moveToNext());
            }


        return connectionList;
    }
    public static Boolean insertImageData(String photo,String photoCard) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_PHOTO,photo);
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

    public static String createTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_PHOTOCARD+" VARCHAR(50),"+
                COL_PHOTO + " VARCHAR(50));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

//    public static Boolean updatePhysicianData(int id, String name, String website, String address, String officephone, String hourphone, String otherphone, String speciality, byte[] photo, String fax, String practice_name, String network, String affil, String note, int i, String lastseen, byte[] photoCard) {
//
//        boolean flag;
//        SQLiteDatabase db=dbHelper.getWritableDatabase();
//
//        ContentValues cv=new ContentValues();
//        cv.put(COL_NAME,name);
//        cv.put(COL_WEBSITE,website);
//        cv.put(COL_LASTSEEN,lastseen);
//        cv.put(COL_ADDRESS,address);
//        cv.put(COL_OFFICE_PHONE,officephone);
//        cv.put(COL_HOUR_PHONE,hourphone);
//        cv.put(COL_OTHER_PHONE,otherphone);
//        cv.put(COL_NOTE,note);
//        cv.put(COL_NETWORK,network);
//        cv.put(COL_ISPHISYCIAN,i);
//        cv.put(COL_AFFIL,affil);
//        cv.put(COL_PRACTICENAME,practice_name);
//        cv.put(COL_SPECIALITY,speciality);
//        cv.put(COL_PHOTO,photo);
//        cv.put(COL_FAX,fax);
//        cv.put(COL_PHOTOCARD,photoCard);
//
//        int rowid=db.update(TABLE_NAME,cv,COL_ID+"="+id,null);
//
//        if (rowid==0)
//        {
//            flag=false;
//        }
//        else
//        {
//            flag=true;
//        }
//
//        return flag;
//   }


    /*public static boolean deleteRecord(int id, int i) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "' and "+COL_ISPHISYCIAN+"='" + i +"';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_ID + "='" + id + "' and "+COL_ISPHISYCIAN+"='" + i +"';");
            } while (c.moveToNext());
        }

        return true;
    }*/
}
