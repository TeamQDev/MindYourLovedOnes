package com.mindyourelders.MyHealthCareWishes.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by varsha on 8/21/2017.
 */

public class Executer extends AsyncTask<String, Void, String>
    {
        Context context;
        OnTaskCompletedListener listener;
        String tag;
        JSONObject jsonObject;
        ProgressDialog progress;
        String dup_url = "";
        HashMap<String, String> postDataParams;

        public Executer(Context context, OnTaskCompletedListener listener, String tag, JSONObject jsonObject)
        {
            this.context=context;
            this.listener=listener;
            this.tag=tag;
            this.jsonObject=jsonObject;
        }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue() + "", "UTF-8"));
        }

        return result.toString();
    }

    private static String decodeResponse(InputStream is) {
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

        } catch (Exception EXCEPTION) {
            EXCEPTION.printStackTrace();
        }

        return result;
    }


    public String uploadData(String requestURL, HashMap<String, String> postDataParams, String tag) {
        InputStream is = null;
        URL url;
        String response = "";
        try {
            url = new URL(requestURL.replace(" ", "%20"));
            if (tag.equalsIgnoreCase("Graph_img")) {
                final MultipartUtility http = new MultipartUtility(url);
                http.addFilePartData(postDataParams);
                final byte[] bytes = http.finish();
                InputStream myInputStream = new ByteArrayInputStream(bytes);
                response = decodeResponse(myInputStream);
            } else {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (postDataParams != null) {
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();
                } else {
                    conn.setRequestMethod("GET");
                }

                Log.v("response code of" + tag, "" + conn.getResponseCode());
                int responseCode = conn.getResponseCode();
                Log.v("response code of" + tag, "" + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                } else {
                    response = "";

                }
            }

        } catch (SocketTimeoutException s) {
            s.printStackTrace();
            cancel(true);
            progress.dismiss();
            //    showAlert();

        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
            cancel(true);
            progress.dismiss();
            //  showAlert();
        } catch (IOException e) {
            e.printStackTrace();
            cancel(true);
            progress.dismiss();
            //showAlert();

        } catch (Exception e) {
            cancel(true);
            e.printStackTrace();
            progress.dismiss();
            //   showAlert();

        }
        Log.v(tag, "Response: " + response);
        return response;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

      /*  if (tag.equals("dpsRequestPxPay") || tag.equals("updateDevice") || tag.equals("getDate") || tag.equals("getCardTokenID") || tag.equals("getParkingAreaforNonEyeGate") || tag.equals("getLicencePlates") || tag.equals("dpsRequestPxPayNonTokenised") || tag.equals("RefrashDateTime")) {
            progress = new ProgressDialog(context);

        } else if (tag.equals("getOutstandingAmount")) {
            progress = new ProgressDialog(context);
        } else {*/


        progress = new ProgressDialog(context);
        progress.setMessage("Loading.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCanceledOnTouchOutside(false);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();


        //  }

    }

    @Override
    protected String doInBackground(String... url) {
        dup_url = url[0];
        return uploadData(url[0], postDataParams, tag);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
//            if (SplashActivity.dismissWSProgress) {

            if (progress.isShowing()) {

                progress.dismiss();

                //  JSONObject jsonObject = new JSONObject(result);
                listener.onTaskCompleted(result, tag);
            } else {
                listener.onTaskCompleted(result, tag);
            }
//        }

        } catch (Exception e) {
            Log.e("ReadJSONFeedTask", e.getLocalizedMessage() + "");
        }

    }

    /*public void showAlert() {

        ((Activity) listener).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                final Dialog dialog = new Dialog((Context) listener);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                LayoutInflater lf = (LayoutInflater) ((Context) listener)
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.retry_dialog, null);
                TextView title = (TextView) dialogview.findViewById(R.id.title);
                title.setText("Note");
                TextView body = (TextView) dialogview
                        .findViewById(R.id.dialogBody);
                body.setText("Network connection Problem");
                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                dialog.getWindow().setAttributes(lp);
                dialog.show();
                TextView cancel = (TextView) dialogview
                        .findViewById(R.id.dialogCancel);
                cancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        progress.dismiss();
                        dialog.dismiss();
                    }
                });

                TextView retry = (TextView) dialogview
                        .findViewById(R.id.dialogRetry);
                retry.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new WebService(context, listener, postDataParams, tag).execute(dup_url);
                        dialog.dismiss();
                    }
                });

            }

        });
    }
*/
}
