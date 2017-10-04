package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.PrescribeImage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */

public class PrescribeImageQuery {
    Context context;
    static DBHelper dbHelper;
    //ListView lvNote;


    public static final String TABLE_NAME = "PrescriptionImage";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_Image= "Image";

    public PrescribeImageQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createImageTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER,"  + COL_Image + " BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertImageData(int userid,int preid,byte[] image) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_Image, image);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<PrescribeImage> fetchAllImageRecord(int userid) {
        ArrayList<PrescribeImage> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid  +"';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    PrescribeImage notes = new PrescribeImage();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setImage(c.getBlob(c.getColumnIndex(COL_Image)));
                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertImageData(int userid, ArrayList<PrescribeImage> imageList) {
        boolean flag=false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i=0;i<imageList.size();i++) {
            ContentValues cv = new ContentValues();
            cv.put(COL_USERID, userid);
            cv.put(COL_Image, imageList.get(i).getImage());

            long rowid = db.insert(TABLE_NAME, null, cv);

            if (rowid == -1) {
                flag = false;
            } else {
                flag = true;
            }
        }
        return flag;
  }
}
