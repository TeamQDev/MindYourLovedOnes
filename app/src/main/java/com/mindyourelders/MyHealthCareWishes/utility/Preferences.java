package com.mindyourelders.MyHealthCareWishes.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by varsha on 8/21/2017.
 */

public class Preferences {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context mContext;
    private String FIRST_TIME_CALL="FIRST_TIME_CALL";
    private  String REGISTERED = "REGISTERED";
    private String LOGIN="LOGIN";

    public boolean isLogin() {
        return preferences.getBoolean(LOGIN, true);
    }

    public void setLogin(boolean firstTimeCall) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.LOGIN, firstTimeCall);
        editor.commit();
    }

    public boolean isFirstTimeCall() {
        return preferences.getBoolean(FIRST_TIME_CALL, true);
    }

    public void setFirstTimeCall(boolean firstTimeCall) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.FIRST_TIME_CALL, firstTimeCall);
        editor.commit();
    }
    public  Boolean getREGISTERED() {
        return preferences.getBoolean(REGISTERED, false);
    }

    public  void setREGISTERED(Boolean REGISTERED) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(this.REGISTERED, REGISTERED);
        editor.commit();
    }
    public Preferences() {

    }

    public Preferences getInstance(Context ctx) {
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        mContext = ctx;
        return this;
    }

    public Preferences(Context context) {
        // TODO Auto-generated constructor stub\
        mContext = context;
        preferences = context.getSharedPreferences("MHCWPref", Context.MODE_PRIVATE);
        editor = preferences.edit();
    }



    public void clearPreferences() {
        editor.clear();
        editor.commit();
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, 0);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    public void putLong(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key) {
        return preferences.getLong(key, 0);
    }


    public void emailAttachement(File f, Context context, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        String name= getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                name+"-"+s); // subject


        String body="Hi, \n" +
                "\n" +
                "\n" +name+
                " shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thanks,\n" +
                "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

        Uri uri=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
              uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", f);
        } else {
              uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        emailIntent.setType("application/email");

        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

}
