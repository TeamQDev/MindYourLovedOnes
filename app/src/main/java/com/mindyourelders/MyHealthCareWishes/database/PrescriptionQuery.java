package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Dosage;
import com.mindyourelders.MyHealthCareWishes.model.PrescribeImage;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */

public class PrescriptionQuery {
    static Context context;
    static DBHelper dbHelper;
    //ListView lvNote;


    public static final String TABLE_NAME = "Prescription";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_DOCTOR= "Doctor";
    public static final String COL_UNIQUE= "UniqueNumber";
    public static final String COL_PURPOSE = "Purpose";
    public static final String COL_NOTE = "Note";
    public static final String COL_DATE_TIME = "DateTime";

    public PrescriptionQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
        DosageQuery d=new DosageQuery(context,dbHelper);
        PrescribeImageQuery p=new PrescribeImageQuery(context,dbHelper);
    }

    public static String createPrescriptionTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER,"+ COL_UNIQUE + " INTEGER," + COL_NOTE + " VARCHAR(20)," + COL_DOCTOR + " VARCHAR(20)," + COL_PURPOSE + " VARCHAR(20)," + COL_DATE_TIME + " VARCHAR(10));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertPrescriptionData(int userid, String doctor, String purpose, String notes, String dt, ArrayList<Dosage> dosageList, ArrayList<PrescribeImage> imageList,int unique) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_NOTE, notes);
        cv.put(COL_DATE_TIME, dt);
        cv.put(COL_DOCTOR, doctor);
        cv.put(COL_PURPOSE, purpose);
        cv.put(COL_UNIQUE, unique);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;

/*
            Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "' and " + COL_UNIQUE + "='" + unique + "';", null);
            if (c != null && c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {*/
                        Boolean flags = DosageQuery.insertDosageData(userid, dosageList, unique);
                        Boolean flag3 = PrescribeImageQuery.insertImageData(userid, imageList,unique);
                   /* } while (c.moveToNext());
                }
            }*/
        }
       /* Boolean flags = DosageQuery.insertDosageData(userid,dosageList);
        Boolean flag3 = PrescribeImageQuery.insertImageData(userid,imageList);*/


        return flag;
    }

    public static ArrayList<Prescription> fetchAllPrescrptionRecord(int userid) {
        ArrayList<Prescription> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid + "';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Prescription notes = new Prescription();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setDates(c.getString(c.getColumnIndex(COL_DATE_TIME)));
                    notes.setDoctor(c.getString(c.getColumnIndex(COL_DOCTOR)));
                    notes.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    notes.setPurpose(c.getString(c.getColumnIndex(COL_PURPOSE)));
                    notes.setUnique(c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    ArrayList<Dosage> Dosagelist = DosageQuery.fetchAllDosageRecord(c.getInt(c.getColumnIndex(COL_USERID)),c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    if (Dosagelist.size()!=0)
                    {
                        notes.setDosageList(Dosagelist);
                    }
                    ArrayList<PrescribeImage> imageList =PrescribeImageQuery.fetchAllImageRecord(c.getInt(c.getColumnIndex(COL_USERID)),c.getInt(c.getColumnIndex(COL_UNIQUE)));
                    if (imageList.size()!=0)
                    {
                        notes.setPrescriptionImageList(imageList);
                    }

                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static boolean deleteRecord(int unique) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_UNIQUE + "='" + unique + "';", null);

            if (c.moveToFirst()) {
                do {
                    db.execSQL("delete from " + TABLE_NAME + " where " + COL_UNIQUE + "='" + unique+"';");
                } while (c.moveToNext());
            }
            DosageQuery.deleteRecord(unique);
            PrescribeImageQuery.deleteRecord(unique);
            return true;
    }

    public static Boolean updatePrescriptionData(int colid, int id, String doctor, String purpose, String note, String date, ArrayList<Dosage> dosageList, ArrayList<PrescribeImage> imageList, int userid) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_NOTE, doctor);
        cv.put(COL_DATE_TIME, date);
        cv.put(COL_DOCTOR, doctor);
        cv.put(COL_NOTE, note);
        cv.put(COL_PURPOSE, purpose);
        int rowid=db.update(TABLE_NAME,cv,COL_ID+"="+colid,null);

        if (rowid==0)
        {
            flag=false;
        }
        else
        {
            flag=true;
            ArrayList<Dosage> d=DosageQuery.fetchAllDosageRecord(userid,id);
            ArrayList<PrescribeImage> i=PrescribeImageQuery.fetchAllImageRecord(userid,id);
        Boolean flags = DosageQuery.updateDosageData(dosageList,id,d);
        Boolean flag3 = PrescribeImageQuery.updateImageData(imageList,id,i);
        }
        return flag;
    }
}
