package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.MedInfo;

/**
 * Created by welcome on 9/24/2017.
 */

public class MedInfoQuery {

    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "MedInfo";

    public static final String COL_ID= "Id";
    public static final String COL_USERID= "UserId";
    public static final String COL_FT ="Feet";
    public static final String COL_NOTE ="Note";
    public static final String COL_MOUTH_NOTE ="MouthNote";
    public static final String COL_INCH ="Inches";
    public static final String COL_WT="Weight";
    public static final String COL_EYE_COLOR= "EyeColor";
    public static final String COL_LANG_PRIMARY="LangPrimary";
    public static final String COL_LANG_SECONDORY="LangSecondory";
    public static final String COL_EYE_GLASSES="Glasses";
    public static final String COL_EYE_LENSE="Lense";
    public static final String COL_TEETH_FALSE= "False";
    public static final String COL_TEETH_IMPLANTS="Implants";
    public static final String COL_TEETH_MOUTH="Mouth";
    public static final String COL_HEARING_AIDES ="Aides";
    public static final String COL_BLOODTYPE="BloodType";
    public static final String COL_ORGANDONOR="OrganDonor";
    public static final String COL_PETS="Pets";


    public MedInfoQuery(Context context, DBHelper dbHelper) {
        this.context=context;
        this.dbHelper=dbHelper;
    }

    public static String createMedInfoTable() {
        String createTableQuery="create table  If Not Exists "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY, "+COL_USERID+" INTEGER, "+
                COL_FT+" VARCHAR(20),"+COL_INCH+" VARCHAR(20),"+
                COL_WT+" VARCHAR(20),"+COL_EYE_COLOR+" VARCHAR(20),"+COL_LANG_PRIMARY+" VARCHAR(20),"+
                COL_LANG_SECONDORY+" VARCHAR(20),"+COL_EYE_GLASSES+" VARCHAR(20),"+COL_EYE_LENSE+" VARCHAR(20),"+
                COL_TEETH_FALSE+" VARCHAR(20),"+COL_TEETH_IMPLANTS+" VARCHAR(20),"+COL_HEARING_AIDES+" VARCHAR(20),"+
                COL_BLOODTYPE+" VARCHAR(20),"+COL_ORGANDONOR+" VARCHAR(20),"+COL_PETS+" VARCHAR(20),"+COL_NOTE+" VARCHAR(20),"+
                COL_MOUTH_NOTE+" VARCHAR(20),"+COL_TEETH_MOUTH+" VARCHAR(20)"+
                ");";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery="DROP TABLE IF EXISTS "+TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertMedInfoData(int userid, String ft, String inch, String weight, String color, String lang1, String lang2, String pet, String blood, String glass, String lense, String falses, String implants, String aid, String donor, String note, String mouth, String mouthnote) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USERID,userid);
        cv.put(COL_FT,ft);
        cv.put(COL_NOTE,note);
        cv.put(COL_INCH,inch);
        cv.put(COL_WT,weight);
        cv.put(COL_EYE_COLOR,color);
        cv.put(COL_LANG_PRIMARY,lang1);
        cv.put(COL_LANG_SECONDORY,lang2);
        cv.put(COL_EYE_GLASSES,glass);
        cv.put(COL_EYE_LENSE,lense);
        cv.put(COL_TEETH_FALSE,falses);
        cv.put(COL_TEETH_IMPLANTS,implants);
        cv.put(COL_HEARING_AIDES,aid);
        cv.put(COL_BLOODTYPE,blood);
        cv.put(COL_ORGANDONOR,donor);
        cv.put(COL_PETS,pet);
        cv.put(COL_MOUTH_NOTE,mouthnote);
        cv.put(COL_TEETH_MOUTH,mouth);

        long rowid=db.insert(TABLE_NAME,null,cv);

        if (rowid==-1)
        {
            flag=false;
        }
        else
        {
            flag=true;
        }

        return flag;
    }

    public static MedInfo fetchOneRecord(int userid) {
        MedInfo medInfo=new MedInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USERID + "='" + userid + "';", null);

        if (c.moveToFirst()) {
            do {
                medInfo.setId(c.getInt(c.getColumnIndex(COL_ID)));
                medInfo.setUserId(c.getInt(c.getColumnIndex(COL_USERID)));
                medInfo.setFeet(c.getString(c.getColumnIndex(COL_FT)));
                medInfo.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                medInfo.setInch(c.getString(c.getColumnIndex(COL_INCH)));
                medInfo.setWeight(c.getString(c.getColumnIndex(COL_WT)));
                medInfo.setColor(c.getString(c.getColumnIndex(COL_EYE_COLOR)));
                medInfo.setLang1(c.getString(c.getColumnIndex(COL_LANG_PRIMARY)));
                medInfo.setLang2(c.getString(c.getColumnIndex(COL_LANG_SECONDORY)));
                medInfo.setGlass(c.getString(c.getColumnIndex(COL_EYE_GLASSES)));
                medInfo.setLense(c.getString(c.getColumnIndex(COL_EYE_LENSE)));
                medInfo.setFalses(c.getString(c.getColumnIndex(COL_TEETH_FALSE)));
                medInfo.setImplants(c.getString(c.getColumnIndex(COL_TEETH_IMPLANTS)));
                medInfo.setAid(c.getString(c.getColumnIndex(COL_HEARING_AIDES)));
                medInfo.setBloodType(c.getString(c.getColumnIndex(COL_BLOODTYPE)));
                medInfo.setDonor(c.getString(c.getColumnIndex(COL_ORGANDONOR)));
                medInfo.setPet(c.getString(c.getColumnIndex(COL_PETS)));
                medInfo.setMouth(c.getString(c.getColumnIndex(COL_TEETH_MOUTH)));
                medInfo.setMouthnote(c.getString(c.getColumnIndex(COL_MOUTH_NOTE)));
            } while (c.moveToNext());
        }
        
        return medInfo;
    }
}
