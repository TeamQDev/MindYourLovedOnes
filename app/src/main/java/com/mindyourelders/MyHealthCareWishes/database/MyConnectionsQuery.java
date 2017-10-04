package com.mindyourelders.MyHealthCareWishes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.model.Proxy;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;

import java.util.ArrayList;

/**
 * Created by welcome on 9/12/2017.
 */

public class MyConnectionsQuery {
    Context context;
    static DBHelper dbHelper;

    public static final String TABLE_NAME = "MyConnections";

    public static final String COL_USER_ID = "UserId";
    public static final String COL_NAME = "Name";
    public static final String COL_EMAIL = "Email";
    public static final String COL_ADDRESS = "Address";
    public static final String COL_MOBILE= "Mobile";
    public static final String COL_HOME_PHONE= "HomePhone";
    public static final String COL_WORK_PHONE= "WorkPhone";
    public static final String COL_PHOTO= "Photo";
    public static final String COL_ID= "Id";
    public static final String COL_RELATION= "Relationship";
    public static final String COL_NOTE= "Notes";
    public static final String COL_FLAG= "Flag";
    public static final String COL_ISPRIMARY= "IsPrimary";


    public MyConnectionsQuery(Context context, DBHelper dbHelper) {
        this.context=context;
        this.dbHelper=dbHelper;
    }
    public static String createMyConnectionsTable() {
        String createTableQuery="create table  If Not Exists "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY, "+COL_USER_ID+" INTEGER, "+COL_NAME+" VARCHAR(50),"+COL_EMAIL+" VARCHAR(50),"+COL_HOME_PHONE+" VARCHAR(20),"+COL_WORK_PHONE+" VARCHAR(20),"+COL_ADDRESS+" VARCHAR(100),"+COL_MOBILE+" VARCHAR(20),"+COL_RELATION+" VARCHAR(50),"+COL_NOTE+" VARCHAR(100),"+COL_FLAG+" INTEGER,"+COL_ISPRIMARY+" INTEGER,"+COL_PHOTO+" BLOB);";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery="DROP TABLE IF EXISTS "+TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertMyConnectionsData(int id, String name, String email, String address, String mobile, String phone,String workphone, String relation, byte[] photo,String note, int connectionflag, int isPrimary) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,id);
        cv.put(COL_NAME,name);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_MOBILE,mobile);
        cv.put(COL_HOME_PHONE,phone);
        cv.put(COL_WORK_PHONE,workphone);
        cv.put(COL_NOTE,note);
        cv.put(COL_FLAG,connectionflag);
        cv.put(COL_ISPRIMARY,isPrimary);
        cv.put(COL_RELATION,relation);
        cv.put(COL_PHOTO,photo);

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

    public static ArrayList<RelativeConnection> fetchAllRecord(int id, int i) {
        ArrayList<RelativeConnection> connectionList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_USER_ID + "=" + id+" and "+ COL_FLAG + "=" + i + ";",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    RelativeConnection connection = new RelativeConnection();
                    connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                    connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                    connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                    connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    connection.setConnectionFlag(c.getInt(c.getColumnIndex(COL_FLAG)));
                    connection.setIsPrimary(c.getInt(c.getColumnIndex(COL_ISPRIMARY)));
                    connection.setRelationType(c.getString(c.getColumnIndex(COL_RELATION)));
                    connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                            connectionList.add(connection);


                } while (c.moveToNext());
            }
        }

        return connectionList;
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

    public static RelativeConnection fetchEmailRecord(String email) {
        RelativeConnection connection=new RelativeConnection();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_EMAIL + "='" + email + "';", null);

        if (c.moveToFirst()) {
            do {
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setConnectionFlag(c.getInt(c.getColumnIndex(COL_FLAG)));
                connection.setIsPrimary(c.getInt(c.getColumnIndex(COL_ISPRIMARY)));
                connection.setRelationType(c.getString(c.getColumnIndex(COL_RELATION)));
                connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
            } while (c.moveToNext());
        }

        return connection;
    }


    public static Boolean updateMyConnectionsData(int id,String name, String email, String address, String mobile, String homephone,String wotrkPhone, String relation, byte[] photo,String note, int connectionflag, int isPrimary) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(COL_NAME,name);
        cv.put(COL_EMAIL,email);
        cv.put(COL_ADDRESS,address);
        cv.put(COL_MOBILE,mobile);
        cv.put(COL_HOME_PHONE,homephone);
        cv.put(COL_WORK_PHONE,wotrkPhone);
        cv.put(COL_NOTE,note);
        cv.put(COL_FLAG,connectionflag);
        cv.put(COL_ISPRIMARY,isPrimary);
        cv.put(COL_RELATION,relation);
        cv.put(COL_PHOTO,photo);

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




    public static ArrayList<Emergency> fetchAllEmergencyRecord(int preferencesInt, int anInt) {
        ArrayList<Emergency> connectionList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_USER_ID + "=" + preferencesInt+" and "+ COL_FLAG + "=" + anInt + ";",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Emergency connection = new Emergency();
                    connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                    connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                    connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                    connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    connection.setConnectionFlag(c.getInt(c.getColumnIndex(COL_FLAG)));
                    connection.setIsPrimary(c.getInt(c.getColumnIndex(COL_ISPRIMARY)));
                    connection.setRelationType(c.getString(c.getColumnIndex(COL_RELATION)));
                    connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                    connectionList.add(connection);


                } while (c.moveToNext());
            }
        }

        return connectionList;
    }

    public static ArrayList<Proxy> fetchAllProxyRecord(int id, int prox) {
        ArrayList<Proxy> connectionList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_USER_ID + "=" + id+" and "+ COL_FLAG + "=" + prox + ";",null);
        if(c!=null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                do {
                    Proxy connection = new Proxy();
                    connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                    connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                    connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                    connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                    connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                    connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                    connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                    connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                    connection.setConnectionFlag(c.getInt(c.getColumnIndex(COL_FLAG)));
                    connection.setIsPrimary(c.getInt(c.getColumnIndex(COL_ISPRIMARY)));
                    connection.setRelationType(c.getString(c.getColumnIndex(COL_RELATION)));
                    connection.setPhoto(c.getBlob(c.getColumnIndex(COL_PHOTO)));
                    connectionList.add(connection);


                } while (c.moveToNext());
            }
        }

        return connectionList;
    }

}
