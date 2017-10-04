package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;

import java.util.ArrayList;

/**
 * Created by welcome on 9/12/2017.
 */

public class PersonalInfoQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "PersonalInfo";

    public static final String COL_NAME = "Name";
    public static final String COL_EMAIL = "Email";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_COUNTRY= "Country";
    public static final String COL_MOBILE= "Mobile";
    public static final String COL_PHONE= "Phone";
    public static final String COL_GENDER= "Gender";
    public static final String COL_DOB= "DOB";
    public static final String COL_PASS= "Password";
    public static final String COL_PHOTO= "Photo";
    public static final String COL_ID= "Id";



    public PersonalInfoQuery(Context context, DBHelper dbHelper) {
        this.context=context;
        this.dbHelper=dbHelper;
    }

    public static String createPersonalInfoTable() {
        String createTableQuery="create table  If Not Exists "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY, "+COL_NAME+" VARCHAR(50),"+COL_EMAIL+" VARCHAR(50),"+COL_MOBILE+" VARCHAR(20),"+COL_PHONE+" VARCHAR(20),"+COL_GENDER+" VARCHAR(20),"+COL_ADDRESS+" VARCHAR(100),"+COL_COUNTRY+" VARCHAR(40),"+COL_DOB+" VARCHAR(20),"+COL_PASS+" VARCHAR(10),"+COL_PHOTO+" BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery="DROP TABLE IF EXISTS "+TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertPersonalInfoData(String name, String email, String address, String country, String phone, String bdate, String password, byte[] photo, String homephone, String gender) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_NAME,name);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_COUNTRY,country);
        cv.put(COL_MOBILE,phone);
        cv.put(COL_DOB,bdate);
        cv.put(COL_PASS,password);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_PHONE,homephone);
        cv.put(COL_GENDER,gender);

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

    public static ArrayList<PersonalInfo> fetchOneRecord(String username, String password) {
        ArrayList<PersonalInfo> personList=new ArrayList<>();

        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+";",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    if (username.equals(c.getString(c.getColumnIndex(COL_EMAIL))) && password.equals(c.getString(c.getColumnIndex(COL_PASS)))) {
                        {
                            PersonalInfo Person = new PersonalInfo();
                            Person.setName(c.getString(c.getColumnIndex(COL_NAME)));
                            Person.setId(c.getInt(c.getColumnIndex(COL_ID)));
                            Person.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                            Person.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                            Person.setCountry(c.getString(c.getColumnIndex(COL_COUNTRY)));
                            Person.setPhone(c.getString(c.getColumnIndex(COL_MOBILE)));
                            Person.setPassword(c.getString(c.getColumnIndex(COL_PASS)));
                            Person.setDob(c.getString(c.getColumnIndex(COL_DOB)));
                            Person.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                            Person.setHomePhone(c.getString(c.getColumnIndex(COL_PHONE)));
                            Person.setGender(c.getString(c.getColumnIndex(COL_GENDER)));
                            personList.add(Person);
                        }
                    }
                } while (c.moveToNext());
            }
        }

               return personList;
    }


    public static PersonalInfo fetchEmailRecord(String email) {
        PersonalInfo connection=new PersonalInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_EMAIL + "='" + email + "';", null);

        if (c.moveToFirst()) {
            do {
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                connection.setPhone(c.getString(c.getColumnIndex(COL_MOBILE)));
                connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                connection.setCountry(c.getString(c.getColumnIndex(COL_COUNTRY)));
                connection.setDob(c.getString(c.getColumnIndex(COL_DOB)));
                connection.setHomePhone(c.getString(c.getColumnIndex(COL_PHONE)));
                connection.setGender(c.getString(c.getColumnIndex(COL_GENDER)));
            } while (c.moveToNext());
        }

        return connection;
    }

    public static Boolean searchEmailAvailability(String email) {
        Boolean flag;
        PersonalInfo connection=new PersonalInfo();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_EMAIL + "='" + email + "';", null);

        if (c.moveToFirst()) {
            flag=true;
        }
        else{
          flag=false;
        }

        return flag;
    }

    public static Boolean updatePersonalInfoData(int id, String name, String email, String address, String country, String phone, String bdate, byte[] photo, String homephone, String gender) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_NAME,name);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_COUNTRY,country);
        cv.put(COL_MOBILE,phone);
        cv.put(COL_DOB,bdate);
        cv.put(COL_PHOTO,photo);
        cv.put(COL_PHONE,homephone);
        cv.put(COL_GENDER,gender);

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
}
