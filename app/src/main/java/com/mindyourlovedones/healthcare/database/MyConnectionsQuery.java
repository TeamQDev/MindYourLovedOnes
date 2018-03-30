package com.mindyourlovedones.healthcare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mindyourlovedones.healthcare.model.Emergency;
import com.mindyourlovedones.healthcare.model.Proxy;
import com.mindyourlovedones.healthcare.model.RelativeConnection;

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
    public static final String COL_OTHER_RELATION= "OtherRelation";
    public static final String COL_NOTE= "Notes";
    public static final String COL_FLAG= "Flag";
    public static final String COL_ISPRIMARY= "IsPrimary";

    public static final String COL_HEIGHT = "height";
    public static final String COL_WEIGHT = "weight";
    public static final String COL_PROFESSION = "profession";
    public static final String COL_EMPLOYED= "employed";
    public static final String COL_RELIGION= "religion";
    public static final String COL_EYES= "eyes";
    public static final String COL_LANG= "language";
    public static final String COL_MARITAL= "marital_status";
    public static final String COL_VETERAN= "veteran";
    public static final String COL_PET= "pet";
    public static final String COL_IDNUMBER= "IDNUmber";
    public static final String COL_MANGER_PHONE= "Manager_Phone";
    public static final String COL_PHOTOCARD= "PhotoCard";
    public static final String COL_ENGLISH= "English";

    public static final String COL_CHILD= "child";
    public static final String COL_SIBLING= "sibling";
    public static final String COL_FRIEND= "friend";
    public static final String COL_GRAND= "grandparent";
    public static final String COL_PARENT= "parent";
    public static final String COL_SPOUSE= "spouse";
    public static final String COL_OTHER_SIGN= "significant_other";
    public static final String COL_OTHER= "other";
    public static final String COL_LIVE= "Living";
    public static final String COL_OTHER_LANG= "other_language";
    public static final String COL_GENDER= "Gender";
    public static final String COL_DOB= "DOB";

    public MyConnectionsQuery(Context context, DBHelper dbHelper) {
        this.context=context;
        this.dbHelper=dbHelper;
    }

    public static String createMyConnectionsTable() {
        String createTableQuery="create table  If Not Exists "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_USER_ID+" INTEGER, "+COL_NAME+" VARCHAR(50),"+COL_EMAIL+" VARCHAR(50),"+COL_HOME_PHONE+" VARCHAR(20),"+
                COL_GENDER+" VARCHAR(20),"+COL_DOB+" VARCHAR(20),"+COL_WORK_PHONE+" VARCHAR(20),"+COL_ADDRESS+" VARCHAR(100),"+COL_MOBILE+" VARCHAR(20),"+COL_RELATION+" VARCHAR(50),"+COL_OTHER_RELATION+" VARCHAR(50),"+COL_NOTE+" VARCHAR(100),"+
                COL_FLAG+" INTEGER,"+COL_ISPRIMARY+" INTEGER,"+
                COL_HEIGHT+" VARCHAR(10),"+COL_WEIGHT+" VARCHAR(10),"+COL_PROFESSION+" VARCHAR(10),"+COL_EMPLOYED+" VARCHAR(10),"+COL_RELIGION+" VARCHAR(10),"+
                COL_EYES+" VARCHAR(10),"+COL_LANG+" VARCHAR(10),"+COL_MARITAL+" VARCHAR(10),"+COL_VETERAN+" VARCHAR(10),"+COL_PET+" VARCHAR(10),"+
                COL_MANGER_PHONE+" VARCHAR(20),"+COL_IDNUMBER+" VARCHAR(10),"+COL_PHOTOCARD+" VARCHAR(50),"+
                COL_CHILD+" VARCHAR(10),"+ COL_SIBLING+" VARCHAR(10),"+COL_FRIEND+" VARCHAR(10),"+COL_GRAND+" VARCHAR(10),"+COL_PARENT+" VARCHAR(10),"+COL_SPOUSE+" VARCHAR(10),"+
                COL_OTHER_SIGN+" VARCHAR(20),"+COL_OTHER_LANG+" VARCHAR(20),"+COL_OTHER+" VARCHAR(20),"+COL_LIVE+" VARCHAR(20),"+
                COL_PHOTO+" VARCHAR(50),"+COL_ENGLISH+" VARCHAR(10));";
        return createTableQuery;
    }

    public static String dropTable() {
        String dropTableQuery="DROP TABLE IF EXISTS "+TABLE_NAME;
        return dropTableQuery;
    }

    public static Boolean insertMyConnectionsData(int id, String name, String email, String address, String mobile, String phone, String workphone, String relation, String photo, String note, int connectionflag, int isPrimary, String otherRelation, String photoCard) {
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
        cv.put(COL_PHOTOCARD,photoCard);
        cv.put(COL_OTHER_RELATION,otherRelation);
        cv.put(COL_LANG,"");

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
                    connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                    connection.setDob(c.getString(c.getColumnIndex(COL_DOB)));
                    connection.setGender(c.getString(c.getColumnIndex(COL_GENDER)));

                    connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                    connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                    connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                    connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                    connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                    connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                    connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                    connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                    connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                    connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                    connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                    connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                    connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                    connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                    connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                    connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                    connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                    connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                    connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                    connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                    connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                    connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                    connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));

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

    public static RelativeConnection fetchEmailRecord(int email) {
        RelativeConnection connection=new RelativeConnection();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + email + "';", null);

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
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                connection.setDob(c.getString(c.getColumnIndex(COL_DOB)));
                connection.setGender(c.getString(c.getColumnIndex(COL_GENDER)));


                connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));
            } while (c.moveToNext());
        }

        return connection;
    }

    public static Boolean fetchEmailRecord(String email) {
       Boolean flag=false;
        RelativeConnection connection=new RelativeConnection();
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

    public static Boolean updateMyConnectionsData(int id, String name, String email, String address, String mobile, String homephone, String wotrkPhone, String relation, String photo, String note, int connectionflag, int isPrimary, String otherRelation, String height, String weight, String eyes, String profession, String employed, String language, String marital_status, String religion, String veteran, String idnumber, String pet, String manager_phone, String photoCard, String english, String child, String friend, String grandParent, String parent, String spouse, String other, String liveOther, String live, String otherLang, String bdate, String gender, String sibling) {
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
        cv.put(COL_OTHER_RELATION,otherRelation);

        cv.put(COL_HEIGHT,height);
        cv.put(COL_WEIGHT,weight);
        cv.put(COL_PROFESSION,profession);
        cv.put(COL_EMPLOYED,employed);
        cv.put(COL_RELIGION,religion);
        cv.put(COL_EYES,eyes);
        cv.put(COL_LANG,language);
        cv.put(COL_MARITAL,marital_status);
        cv.put(COL_VETERAN,veteran);
        cv.put(COL_PET,pet);
        cv.put(COL_IDNUMBER,idnumber);
        cv.put(COL_MANGER_PHONE,manager_phone);
        cv.put(COL_PHOTOCARD,photoCard);
        cv.put(COL_ENGLISH,english);

        cv.put(COL_CHILD,child);
        cv.put(COL_SIBLING,sibling);
        cv.put(COL_FRIEND,friend);
        cv.put(COL_GRAND,grandParent);
        cv.put(COL_PARENT,parent);
        cv.put(COL_SPOUSE,spouse);
        cv.put(COL_OTHER_SIGN,other);
        cv.put(COL_OTHER,liveOther);
        cv.put(COL_LIVE,live);
        cv.put(COL_OTHER_LANG,otherLang);
        cv.put(COL_DOB,bdate);
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




    public static ArrayList<Emergency> fetchAllEmergencyRecord(int preferencesInt, int anInt) {
        ArrayList<Emergency> connectionList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_FLAG + "=" + anInt + ";",null);

     //   Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_USER_ID + "=" + preferencesInt+" and "+ COL_FLAG + "=" + anInt + ";",null);
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
                    connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));
                    connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));

                    connectionList.add(connection);


                } while (c.moveToNext());
            }
        }

        return connectionList;
    }

    public static ArrayList<Proxy> fetchAllProxyRecord(int id, int prox) {
        ArrayList<Proxy> connectionList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_FLAG + "=" + prox + ";",null);

      //  Cursor c=db.rawQuery("select * from "+TABLE_NAME+ " where " + COL_USER_ID + "=" + id+" and "+ COL_FLAG + "=" + prox + ";",null);
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
                    connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));
                    connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));

                    connectionList.add(connection);


                } while (c.moveToNext());
            }
        }

        return connectionList;
    }

    public static RelativeConnection fetchConnectionRecord(int userid) {
        RelativeConnection connection=new RelativeConnection();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USER_ID + "='" + userid + "';", null);

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
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));
            } while (c.moveToNext());
        }

        return connection;
    }

    public static Boolean insertSingleConnectionsData(int connectionId, int id, String name, String email, String address, String mobile, String phone, String workphone, String relation, String photo, String note, int connectionflag, int isPrimary, String otherRelation, String photoCard) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_ID,connectionId);
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
        cv.put(COL_PHOTOCARD,photoCard);
        cv.put(COL_OTHER_RELATION,otherRelation);

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

    public static RelativeConnection fetchOneRecord(String self) {
        RelativeConnection connection=new RelativeConnection();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_RELATION + "='" + self + "';", null);

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
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                connection.setDob(c.getString(c.getColumnIndex(COL_DOB)));
                connection.setGender(c.getString(c.getColumnIndex(COL_GENDER)));


                connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));
                connection.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
            } while (c.moveToNext());
        }

        return connection;
    }

    public static RelativeConnection fetchConnectionRecord(int userid, String email) {
        RelativeConnection connection=new RelativeConnection();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_USER_ID + "='" + userid +"' AND "+ COL_EMAIL + "='" + email +"';", null);

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
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));
            } while (c.moveToNext());
        }

        return connection;
    }

    public static Boolean updatePhoto(int id, String imagepath, String cardPath) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(COL_PHOTO,imagepath);
        cv.put(COL_PHOTOCARD,cardPath);

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

    public static RelativeConnection fetchConnectionRecordforImport(int id) {
        RelativeConnection connection=new RelativeConnection();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_ID + "='" + id + "';", null);

        if (c.moveToFirst()) {
            do {
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setConnectionFlag(c.getInt(c.getColumnIndex(COL_FLAG)));
                connection.setIsPrimary(c.getInt(c.getColumnIndex(COL_ISPRIMARY)));
                connection.setRelationType(c.getString(c.getColumnIndex(COL_RELATION)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));
            } while (c.moveToNext());
        }

        return connection;
    }

    public static Boolean updateImportMyConnectionsData(int userid, int connectionUserid) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_USER_ID,userid);
        int rowid=db.update(TABLE_NAME,cv,COL_USER_ID+"="+connectionUserid,null);

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

    public static Boolean insertImportMyConnectionsData(int id,int uid, String name, String email, String address, String mobile, String phone, String workphone, String relation, String photo, String note, int connectionflag, int isPrimary, String otherRelation, String photoCard) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(COL_ID,id);
        cv.put(COL_USER_ID,uid);
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
        cv.put(COL_PHOTOCARD,photoCard);
        cv.put(COL_OTHER_RELATION,otherRelation);
        cv.put(COL_LANG,"");

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

    public static ArrayList<RelativeConnection> fetchAllRecord() {
        ArrayList<RelativeConnection> connectionList=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+TABLE_NAME+ ";",null);
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
                    connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                    connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                    connection.setDob(c.getString(c.getColumnIndex(COL_DOB)));
                    connection.setGender(c.getString(c.getColumnIndex(COL_GENDER)));

                    connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                    connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                    connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                    connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                    connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                    connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                    connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                    connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                    connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                    connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                    connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                    connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                    connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                    connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                    connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                    connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                    connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                    connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                    connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                    connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                    connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                    connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                    connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                    connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));

                    connectionList.add(connection);
                } while (c.moveToNext());
            }
        }

        return connectionList;
    }

    public static Boolean updateMyConnectionsRelationData(int id, String relation, String otherRelation) {
        boolean flag;
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(COL_RELATION,relation);
        cv.put(COL_OTHER_RELATION,otherRelation);

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

    public static RelativeConnection fetchConnectionRecordforImport(String email) {
        RelativeConnection connection=null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("Select * from " + TABLE_NAME + " where " + COL_EMAIL + "='" + email + "';", null);

        if (c.moveToFirst()) {
            do {
                connection=new RelativeConnection();
                connection.setName(c.getString(c.getColumnIndex(COL_NAME)));
                connection.setId(c.getInt(c.getColumnIndex(COL_ID)));
                connection.setUserid(c.getInt(c.getColumnIndex(COL_USER_ID)));
                connection.setAddress(c.getString(c.getColumnIndex(COL_ADDRESS)));
                connection.setEmail(c.getString(c.getColumnIndex(COL_EMAIL)));
                connection.setMobile(c.getString(c.getColumnIndex(COL_MOBILE)));
                connection.setPhone(c.getString(c.getColumnIndex(COL_HOME_PHONE)));
                connection.setWorkPhone(c.getString(c.getColumnIndex(COL_WORK_PHONE)));
                connection.setNote(c.getString(c.getColumnIndex(COL_NOTE)));
                connection.setConnectionFlag(c.getInt(c.getColumnIndex(COL_FLAG)));
                connection.setIsPrimary(c.getInt(c.getColumnIndex(COL_ISPRIMARY)));
                connection.setRelationType(c.getString(c.getColumnIndex(COL_RELATION)));
                connection.setPhoto(c.getString(c.getColumnIndex(COL_PHOTO)));
                connection.setOtherRelation(c.getString(c.getColumnIndex(COL_OTHER_RELATION)));

                connection.setHeight(c.getString(c.getColumnIndex(COL_HEIGHT)));
                connection.setWeight(c.getString(c.getColumnIndex(COL_WEIGHT)));
                connection.setProfession(c.getString(c.getColumnIndex(COL_PROFESSION)));
                connection.setEmployed(c.getString(c.getColumnIndex(COL_EMPLOYED)));
                connection.setReligion(c.getString(c.getColumnIndex(COL_RELIGION)));

                connection.setEyes(c.getString(c.getColumnIndex(COL_EYES)));
                connection.setLanguage(c.getString(c.getColumnIndex(COL_LANG)));
                connection.setMarital_status(c.getString(c.getColumnIndex(COL_MARITAL)));
                connection.setVeteran(c.getString(c.getColumnIndex(COL_VETERAN)));
                connection.setPet(c.getString(c.getColumnIndex(COL_PET)));
                connection.setIdnumber(c.getString(c.getColumnIndex(COL_IDNUMBER)));
                connection.setManager_phone(c.getString(c.getColumnIndex(COL_MANGER_PHONE)));
                connection.setPhotoCard(c.getString(c.getColumnIndex(COL_PHOTOCARD)));
                connection.setEnglish(c.getString(c.getColumnIndex(COL_ENGLISH)));

                connection.setChildren(c.getString(c.getColumnIndex(COL_CHILD)));
                connection.setSibling(c.getString(c.getColumnIndex(COL_SIBLING)));
                connection.setFriend(c.getString(c.getColumnIndex(COL_FRIEND)));

                connection.setGrand(c.getString(c.getColumnIndex(COL_GRAND)));
                connection.setParents(c.getString(c.getColumnIndex(COL_PARENT)));
                connection.setSpouse(c.getString(c.getColumnIndex(COL_SPOUSE)));
                connection.setSign_other(c.getString(c.getColumnIndex(COL_OTHER_SIGN)));
                connection.setOther_person(c.getString(c.getColumnIndex(COL_OTHER)));
                connection.setLive(c.getString(c.getColumnIndex(COL_LIVE)));
                connection.setOtherLang(c.getString(c.getColumnIndex(COL_OTHER_LANG)));
            } while (c.moveToNext());
        }

        return connection;
    }


    public static Boolean updateImportedMyConnectionsData(String name, String email, String address, String mobile, String homephone, String wotrkPhone, String relation, String photo, String note, int connectionflag, int isPrimary, String otherRelation, String photoCard) {
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
        cv.put(COL_OTHER_RELATION,otherRelation);
        cv.put(COL_PHOTOCARD,photoCard);

        int rowid=db.update(TABLE_NAME,cv,COL_EMAIL + "='" + email + "';",null);

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

    public static Boolean updateImportedMyConnectionsData(String name, String email, String address, String mobile, String homephone, String wotrkPhone, String photo, String note, int connectionflag, int isPrimary, String otherRelation, String photoCard) {
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
        cv.put(COL_PHOTO,photo);
        cv.put(COL_OTHER_RELATION,otherRelation);
        cv.put(COL_PHOTOCARD,photoCard);

        int rowid=db.update(TABLE_NAME,cv,COL_EMAIL + "='" + email + "';",null);

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
