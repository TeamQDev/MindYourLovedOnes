package com.mindyourlovedones.healthcare.DashBoard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by welcome on 1/18/2018.
 */

public class UpdateProfile extends AsyncTask<Void, Void, String> {
    private final String EDIT_PROFILE_URL = "https://03a7acf.netsolhost.com/MindYourLovedOnes/MYLO/index.php/webservices/user/editProfile";
    ProgressDialog pd;
    private String userId = "";
    private String deviceType = "Android";
    String name = "";
    String email = "";
    OnTaskCompletedListener listener;
    Context context;
    FragmentIndividualContact fragmentIndividualContact;


    public UpdateProfile(String name, String email, String userId, Context context, FragmentIndividualContact fragmentIndividualContact) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.fragmentIndividualContact=fragmentIndividualContact;
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("Please Wait..");
        pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.e("URL parameter", name + " " + "-" + " " + email
                + " " + " " + " " + " " + deviceType);
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(EDIT_PROFILE_URL);
        String result = "";
        InputStream is = null;
        try {

            Log.e("URL parameter", "id :" + userId + "First Name :" + name
                    + "\nlastName : " + "" + " \nState : " + ""
                    + " \nemail :" + email + "\npassword :" + "");

            httppost.setHeader("userId", userId);
            httppost.setHeader("firstName", name);
            httppost.setHeader("lastName", "");
            httppost.setHeader("state", "");
            httppost.setHeader("email", email.trim());
            //    if (!password.equals("")) {
            httppost.setHeader("password", "");
            //   }
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {

                is = responseEntity.getContent();
            }

        } catch (ClientProtocolException e) {
            return "exception";
        } catch (IOException e) {
            return "exception";
        }

        result = decodeResponse(is);
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
            /*if (pd.isShowing()) {
                pd.dismiss();
            }*/
        if (!result.equals("")) {
            if (result.equals("Exception")) {
            } else {
                Log.e("CreateUserAsynk", result);
                try {
                    fragmentIndividualContact.onTaskCompleted(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
  /*  public String editProfile(String id, String firstName, String lastName,
                              String state, String email, String password) {



        return result;

    }*/

    public static String decodeResponse(InputStream is) {

        String result = "";

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            is.close();
            result = sb.toString();

            Log.e("Response**** WebService", result);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            return "exception";

        }

        String res = decodeStringBase64(result);
        return res;
    }

    public static String decodeStringBase64(String result) {

        String res;
        try {
            res = new String(Base64.decode(result, Base64.DEFAULT),
                    "ISO-8859-5");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Response**** WebService", "String decode Exception");

            return "exception";
        }

        Log.e("Unsbcribe", res + "");

        return res;

    }
}