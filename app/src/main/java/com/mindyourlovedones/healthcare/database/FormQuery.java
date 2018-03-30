package com.mindyourlovedones.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedones.healthcare.model.Form;

import java.util.ArrayList;

/**
 * Created by welcome on 10/7/2017.
 */

public class FormQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "InsuranceForms";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";
    public static final String COL_DOCUMENT= "Document";


    public FormQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }


    public static String createDocumentTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," +COL_DOCUMENT + " VARCHAR(100),"+
                COL_PHOTO + " INTEGER);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<Form> fetchAllDocumentRecord(int userid) {
        ArrayList<Form> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
              Cursor c=db.rawQuery("select * from "+TABLE_NAME + ";",null);

        //   Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid+ "';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Form notes = new Form();

                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setDocument(c.getString(c.getColumnIndex(COL_DOCUMENT)));
                    notes.setImage(c.getInt(c.getColumnIndex(COL_PHOTO)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return noteList;
    }
    public static Boolean insertDocumentData(int userid, String name, int photo, String document) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userid);
        cv.put(COL_NAME,name);
        cv.put(COL_DOCUMENT,name);
        cv.put(COL_PHOTO,photo);

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

    public static Boolean updateDocumentData(int id, String name, int photo, String documentPath) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(COL_NAME,name);
        cv.put(COL_DOCUMENT,name);
        cv.put(COL_PHOTO,photo);
        int rowid=db.update(TABLE_NAME,cv,COL_ID+"="+id,null);

        if (rowid == 0) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

 /*   public static Boolean updateInsuranceData(int id, String name, String website, String type, String phone, byte[] photo, String fax, String note, String member, String group, String subscriber, String email) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(COL_NAME,name);
        cv.put(COL_WEBSITE,website);
        cv.put(COL_GROUP,group);
        cv.put(COL_MEMBERID,member);
        cv.put(COL_EMAIL,email);
        cv.put(COL_TYPE,type);
        cv.put(COL_OFFICE_PHONE,phone);
        cv.put(COL_NOTE,note);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_FAX,fax);
        cv.put(COL_SUBSCRIBER,subscriber);
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
