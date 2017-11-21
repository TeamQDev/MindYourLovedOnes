package com.mindyourelders.MyHealthCareWishes.webservice;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by welcome on 11/17/2017.
 */

public class WebService {
    private final String CREATE_PROFILE_URL = "http://www.myhealthcarewishes.com/MHCW/index.php/webservices/user/createProfile";

    private final String GET_PROFILE_URL = "http://www.myhealthcarewishes.com/MHCW/index.php/webservices/user/getProfile";

    private final String EDIT_PROFILE_URL = "http://www.myhealthcarewishes.com/MHCW/index.php/webservices/user/editProfile";

    private final String UNSSUBSCRIBE_ME_URL = "http://www.myhealthcarewishes.com/MHCW/index.php/webservices/user/unRegister";

    private final static String POST_PDF_URL = "http://www.myhealthcarewishes.com/MHCW/index.php/webservices/fax/sendFax";

    public String createProfile(String firstName, String lastName,
                                String state, String mail, String password, String deviceUdid,
                                String deviceType) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(CREATE_PROFILE_URL);
        String result = "";
        InputStream is = null;
        try {

            Log.e("URL parameter", "First Name :" + firstName + "\nlastName : "
                    + lastName + " \nState : " + state + " \nemail :" + mail
                    + "\npassword :" + password + " \nDeviceId :" + deviceUdid
                    + " \ndeviceType :" + deviceType);

            httppost.setHeader("firstName", firstName);
            httppost.setHeader("lastName", lastName);
            httppost.setHeader("state", state);
            httppost.setHeader("email", mail.trim());
            httppost.setHeader("password", password);
            httppost.setHeader("deviceUdid", deviceUdid);
            httppost.setHeader("deviceType", deviceType);
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













    public static String uploadFile(String sourceFileUri, String number, String to, String from, String subject, Context context) {
        String fileName = sourceFileUri;
        String result="";
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        try {

            // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(POST_PDF_URL);

            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("fromName", from);
            conn.setRequestProperty("subject", subject);
            conn.setRequestProperty("toName", to);

            conn.setRequestProperty("faxnumber", number);



            conn.setRequestProperty("uploadFile", fileName);

            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=uploadFile;filename=\""
                    + fileName + "\"" + lineEnd);

            dos.writeBytes(lineEnd);

            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            int serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

//			Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage
//					+ ": " + serverResponseCode);

//			if (serverResponseCode == 200) {
//				Toast.makeText(context, "Fax Submitted Sucessfully", Toast.LENGTH_LONG).show();
//				System.out.println("" + serverResponseCode);
//			}

            // close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

            InputStream response = conn.getInputStream();


            result=decodeResponse(response);
            Log.i("uploadFile", "HTTP Response is : " +result);


        } catch (MalformedURLException ex) {

            ex.printStackTrace();

            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {

            e.printStackTrace();

            Log.e("Upload file to server Exception",
                    "Exception : " + e.getMessage(), e);
        }
        return result;
    }

    public String getProfile(String userId) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(GET_PROFILE_URL);
        String result = "";
        InputStream is = null;
        try {

            Log.e("Encode String", userId);

            httppost.setHeader("userId", userId);

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

   /* public String unSubscribeMe(String userId) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(UNSSUBSCRIBE_ME_URL);
        String result = "";
        InputStream is = null;
        try {

            Log.e("Encode String", userId);

            httppost.setHeader("userId", userId);

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
*/
    public String editProfile(String id, String firstName, String lastName,
                              String state, String email, String password, String userId) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(EDIT_PROFILE_URL);
        String result = "";
        InputStream is = null;
        try {

            Log.e("URL parameter", "id :" + id + "First Name :" + firstName
                    + "\nlastName : " + lastName + " \nState : " + state
                    + " \nemail :" + email + "\npassword :" + password);

            httppost.setHeader("userId", id);
            httppost.setHeader("firstName", firstName);
            httppost.setHeader("lastName", lastName);
            httppost.setHeader("state", state);
            httppost.setHeader("email", email.trim());
            if (!password.equals("")) {
                httppost.setHeader("password", password);
            }
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
