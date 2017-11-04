package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Pet;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.database.AllergyQuery.COL_ALLERGY;
import static com.mindyourelders.MyHealthCareWishes.database.AllergyQuery.COL_REACTION;
import static com.mindyourelders.MyHealthCareWishes.database.AllergyQuery.COL_TREATMENT;

/**
 * Created by welcome on 9/25/2017.
 */

public class PetQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "PetInfo";

    public static final String COL_ID = "Id";
    public static final String COL_USERID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_BREED = "Breed";
    public static final String COL_COLOR = "Color";
    public static final String COL_VETERIAN = "Veteran";
    public static final String COL_GUARD = "Guard";
    public static final String COL_CHIP= "Chip";

    public PetQuery(Context context, DBHelper dbHelper) {
        this.context = context;
        this.dbHelper = dbHelper;
    }

    public static String createPetTable() {
        String createTableQuery = "create table  If Not Exists " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY, " + COL_USERID + " INTEGER, " +
                COL_NAME+ " VARCHAR(30)," +COL_BREED + " VARCHAR(30)," + COL_COLOR + " VARCHAR(30)," +
                COL_VETERIAN + " VARCHAR(100)," +   COL_GUARD + " VARCHAR(100)," +   COL_CHIP + " VARCHAR(30)" +
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return dropTableQuery;
    }


    public static Boolean insertPetData(int userid, String name, String breed, String color, String chip, String veterain, String care) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_USERID, userid);
        cv.put(COL_BREED, breed);
        cv.put(COL_COLOR, color);
        cv.put(COL_VETERIAN, veterain);
        cv.put(COL_GUARD, care);
        cv.put(COL_CHIP, chip);
        cv.put(COL_NAME, name);

        long rowid = db.insert(TABLE_NAME, null, cv);

        if (rowid == -1) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    public static ArrayList<Pet> fetchAllRecord(int userid) {
        ArrayList<Pet> allergyList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {

                    Pet allergy = new Pet();
                    allergy.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    allergy.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                    allergy.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    allergy.setChip(c.getString(c.getColumnIndex(COL_CHIP)));
                    allergy.setColor(c.getString(c.getColumnIndex(COL_COLOR)));
                    allergy.setVeterian(c.getString(c.getColumnIndex(COL_VETERIAN)));
                    allergy.setGuard(c.getString(c.getColumnIndex(COL_GUARD)));
                    allergy.setBreed(c.getString(c.getColumnIndex(COL_BREED)));
                    allergyList.add(allergy);
                } while (c.moveToNext());
            }
        }

        return allergyList;
    }

    public static Boolean updateAllergyData(int id, String value, String reactions, String treatments) {
        boolean flag;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COL_ALLERGY, value);
        cv.put(COL_TREATMENT, treatments);
        cv.put(COL_REACTION, reactions);

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
