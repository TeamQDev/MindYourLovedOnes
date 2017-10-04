package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Allergy;

import java.util.ArrayList;

/**
 * Created by welcome on 9/25/2017.
 */

public class AllergyQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "AllergyInfo";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_ALLERGY = "Allergy";
    public static final String COL_REACTION = "Reaction";
    public static final String COL_TREATMENT = "Treatment";

    public AllergyQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createAllergyTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_ALLERGY + " VARCHAR(100)," + COL_REACTION + " VARCHAR(100)," +
                COL_TREATMENT + " VARCHAR(100)" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertAllergyData(int userid, String value, String reaction, String treatment) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_ALLERGY, value);
        cv.put(COL_TREATMENT, treatment);
        cv.put(COL_REACTION, reaction);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<Allergy> fetchAllRecord(int userid) {
        ArrayList<Allergy> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Allergy allergy = new Allergy();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setAllergy(c.getString(c.getColumnIndex(COL_ALLERGY)));
                    allergy.setTreatment(c.getString(c.getColumnIndex(COL_TREATMENT)));
                    allergy.setReaction(c.getString(c.getColumnIndex(COL_REACTION)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }
}
