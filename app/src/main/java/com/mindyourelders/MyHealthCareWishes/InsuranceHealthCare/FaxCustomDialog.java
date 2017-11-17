package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.webservice.CustomDialog;
import com.mindyourelders.MyHealthCareWishes.webservice.WebService;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by welcome on 11/17/2017.
 */

class FaxCustomDialog extends Dialog implements
        android.view.View.OnClickListener {
    TextView textmsg;
    private TextView btn_ok;
    private String path;
    private String number;
    private EditText editnumber;
    private TextView btn_cancel;
    private EditText editto;
    private EditText editfrom;
    private EditText editsubject;
    private String to;
    private String from;
    private String subject;
    private Context context;

    /**
     *
     * @param context
     */
    public FaxCustomDialog(Context context, String path) {
        super(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        setContentView(R.layout.dialog_fax);
        this.path = path;
        btn_ok = (TextView) findViewById(R.id.btnYes);
        btn_cancel = (TextView) findViewById(R.id.btnNo);
        editnumber = (EditText) findViewById(R.id.etFaxnumber);
        editto = (EditText) findViewById(R.id.etTo);
        editfrom = (EditText) findViewById(R.id.etFrom);
        editsubject = (EditText) findViewById(R.id.etSubject);

        // textmsg.setText(""+msg);
        btn_ok.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnYes:

                if (validation()) {
                    this.dismiss();

                    new ServiceAsyncTask().execute();
                    // WebService.uploadFile(path, number, to, from, subject,
                    // context);
                    // if(editnumber.getText().length()==0){

                    // }

                    // textmsg.setTextColor(context.getResources().getColor(R.color.red_color));

                    // Toast.makeText(context, "Please enter fax number",
                    // Toast.LENGTH_LONG).show();

                }
                break;

            case R.id.btnNo:

                this.dismiss();
                break;

        }

    }

    public class ServiceAsyncTask extends AsyncTask<String, Void, String>

    {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.setMessage("Fax Sending...");
            dialog.setCancelable(false);

            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            return WebService.uploadFile(path, "001"+number, to, from, subject,
                    context);
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            if (dialog.isShowing()) {
                dialog.cancel();
            }

            try {
                JSONObject jObj = new JSONObject(responce);

                JSONObject jObj1 = jObj.getJSONObject("response");

                String errorcode = jObj1.optString("errorCode");

                if (errorcode.equalsIgnoreCase("0")) {

                    String respmsg = jObj1.optString("respMsg");
                    Log.v("RES",respmsg);
                    System.out.println("" + respmsg);
                    CustomDialog.createCustomDialog(context, "Note", respmsg)
                            .show();

                } else {

                    String errormsg = jObj1.optString("errorMsg");
                    System.out.println("" + errormsg);
                    CustomDialog.createCustomDialog(context, "Note", errormsg)
                            .show();

                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private boolean validation() {

        number = editnumber.getText().toString();
        to = editto.getText().toString();
        from = editfrom.getText().toString();
        subject = editsubject.getText().toString();

        if (number.equalsIgnoreCase("")) {
            editnumber.setError("Field cannot be left blank.");
            return false;
        } else if (number.length() < 10) {
            editnumber.setError("Please enter valid Fax number");
            return false;
        }

        else if (to.equalsIgnoreCase("")) {
            editto.setError("Field cannot be left blank.");
            return false;
        } else if (from.equalsIgnoreCase("")) {
            editfrom.setError("Field cannot be left blank.");
            return false;
        } else if (subject.equalsIgnoreCase("")) {
            editsubject.setError("Field cannot be left blank.");
            return false;
        }
        return true;

    }

}
