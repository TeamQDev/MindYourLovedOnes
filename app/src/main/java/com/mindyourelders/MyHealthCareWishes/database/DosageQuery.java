package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Dosage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/29/2017.
 */

public class DosageQuery {
    Context context;
    static DBHelper dbHelper;
    //ListView lvNote;


    public static final String TABLE_NAME = "Dosage";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_PREID = "PreId";
    public static final String COL_MEDICINE= "MedicineName";
    public static final String COL_DOSE = "Dose";

    public DosageQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createDosageTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY,"+ COL_USERID + " INTEGER,"+ COL_MEDICINE + " VARCHAR(20)," + COL_DOSE+ " VARCHAR(10),"+
                COL_PREID + " Integer);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }
    public static Boolean insertDosageData(int userid, String medicine ,String dose) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_MEDICINE, medicine);
        cv.put(COL_DOSE, dose);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<Dosage> fetchAllDosageRecord(int userid, int id) {
        ArrayList<Dosage> noteList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
       Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid+"' and "+COL_PREID+"='"+id+"';",null);
      //  Cursor c=db.rawQuery("select * from "+TABLE_NAME + " where " + COL_USERID + "='" + userid+"';",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Dosage notes = new Dosage();
                    notes.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setUserid(c.getInt(c.getColumnIndex(COL_USERID)));
                    notes.setPreid(c.getInt(c.getColumnIndex(COL_PREID)));
                    notes.setMedicine(c.getString(c.getColumnIndex(COL_MEDICINE)));
                    notes.setDose(c.getString(c.getColumnIndex(COL_DOSE)));
                    noteList.add(notes);
                } while (c.moveToNext());
            }
        }

        return noteList;
    }

    public static Boolean insertDosageData(int userid, ArrayList<Dosage> dosageList,int id) {
        boolean flag=false;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i=0;i<dosageList.size();i++)
        {
            ContentValues cv = new ContentValues();
            cv.put(COL_USERID, userid);
            cv.put(COL_MEDICINE, dosageList.get(i).getMedicine());
            cv.put(COL_DOSE, dosageList.get(i).getDose());
            cv.put(COL_PREID, id);
            long rowid = db.insert(TABLE_NAME, null, cv);

            if (rowid == -1) {
                flag = false;
            } else {
                flag = true;
            }
    }
        return flag;
    }

    public static void deleteRecord(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_PREID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                db.execSQL("delete from " + TABLE_NAME + " where " + COL_PREID + "='" + id+"';");
            } while (c.moveToNext());
        }

    }
}
