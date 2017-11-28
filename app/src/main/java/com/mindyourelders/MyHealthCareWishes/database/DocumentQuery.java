package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Document;

import java.util.ArrayList;

/**
 * Created by welcome on 10/7/2017.
 */

public class DocumentQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "Documents";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_TYPE = "DocumentType";
    public static final String COL_DATE = "DateSigned";
    public static final String COL_HOLDER = "HolderName";
    public static final String COL_LOCATION= "Location";
    public static final String COL_DOCUMENT= "Document";
    public static final String COL_CATEGORY= "Category";
    public static final String COL_PERSON= "Person";
    public static final String COL_PRINCIPLE= "Principle";
    public static final String COL_HOSP= "Hospital";
    public static final String COL_OTHER= "Other_Category";
    public static final String COL_FROM= "Froms";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_ID = "Id";


    public DocumentQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }


    public static String createDocumentTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " +
                COL_USER_ID + " INTEGER, " + COL_NAME + " VARCHAR(50)," + COL_DATE + " VARCHAR(50),"
                +COL_OTHER + " VARCHAR(100)," + COL_HOSP + " VARCHAR(70),"+ COL_TYPE + " VARCHAR(100)," + COL_HOLDER + " VARCHAR(50),"+COL_LOCATION + " VARCHAR(50),"+
                COL_CATEGORY +" VARCHAR(50),"+COL_FROM + " VARCHAR(50)," +COL_PERSON +" VARCHAR(50),"+COL_PRINCIPLE + " VARCHAR(50)," +
                COL_DOCUMENT + " VARCHAR(100),"+
                COL_PHOTO + " INTEGER);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static ArrayList<Document> fetchAllDocumentRecord(int userid,String from) {
        ArrayList<Document> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "' and "+COL_FROM + "='" + from + "';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Document notes = new Document();

                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                    notes.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    notes.setType(c.getString(c.getColumnIndex(COL_TYPE)));
                    notes.setDate(c.getString(c.getColumnIndex(COL_DATE)));
                    notes.setHolder(c.getString(c.getColumnIndex(COL_HOLDER)));
                    notes.setLocation(c.getString(c.getColumnIndex(COL_LOCATION)));
                    notes.setDocument(c.getString(c.getColumnIndex(COL_DOCUMENT)));
                    notes.setCategory(c.getString(c.getColumnIndex(COL_CATEGORY)));
                    notes.setImage(c.getInt(c.getColumnIndex(COL_PHOTO)));
                    notes.setFrom(c.getString(c.getColumnIndex(COL_FROM)));
                    notes.setPrinciple(c.getString(c.getColumnIndex(COL_PRINCIPLE)));
                    notes.setPerson(c.getString(c.getColumnIndex(COL_PERSON)));
                    notes.setOtherCategory(c.getString(c.getColumnIndex(COL_OTHER)));
                    notes.setHospital(c.getString(c.getColumnIndex(COL_HOSP)));

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }
        return noteList;
    }
    public static Boolean insertDocumentData(int userid, String name, String category, String date, String loacation, String holder, int photo, String document, String type, String from, String person, String principle, String otherCategory, String hosp) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userid);
        cv.put(COL_NAME,name);
        cv.put(COL_DATE,date);
        cv.put(COL_HOLDER,holder);
        cv.put(COL_TYPE,type);
        cv.put(COL_LOCATION,loacation);
        cv.put(COL_DOCUMENT,document);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_CATEGORY,category);
        cv.put(COL_FROM,from);
        cv.put(COL_PRINCIPLE,principle);
        cv.put(COL_PERSON,person);
        cv.put(COL_HOSP,hosp);
        cv.put(COL_OTHER,otherCategory);

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
