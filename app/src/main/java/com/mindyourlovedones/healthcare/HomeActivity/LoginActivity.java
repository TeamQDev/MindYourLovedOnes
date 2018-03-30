package com.mindyourlovedones.healthcare.HomeActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedones.healthcare.model.RelativeConnection;
import com.mindyourlovedones.healthcare.utility.DialogManager;
import com.mindyourlovedones.healthcare.utility.NetworkUtils;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.mindyourlovedones.healthcare.webservice.WebService;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL_PERMISSION =100 ;
    Context context = this;
    RelativeLayout rlLogin;
    TextView txtSignIn, txtNew, txtForgotPassword;
    ImageView imgFbSignup, imgGoogleSignup;
    TextView txtUserName, txtPassword, txtName;
    String username = "", password = "", name = "";
    Preferences preferences;
    TextInputLayout tilName;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponent();
        initUI();
        initListener();
    }

    private void initComponent() {
        try {
            File f=new File(Environment.getExternalStorageDirectory(),"/MYLO/MASTER/");
            if (!f.exists())
            {
                f.mkdirs();
            }
            else{
                try {
                    File file=new File(Environment.getExternalStorageDirectory(),"/MYLO/");
                    FileUtils.deleteDirectory(file);
                    f.mkdirs();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context,"MASTER");
        MyConnectionsQuery p = new MyConnectionsQuery(context, dbHelper);

    }

    private void initListener() {
        txtSignIn.setOnClickListener(this);
        txtNew.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        imgFbSignup.setOnClickListener(this);
        imgGoogleSignup.setOnClickListener(this);
    }

    private void initUI() {
        rlLogin = (RelativeLayout) findViewById(R.id.rlLogin);
        txtSignIn = (TextView) findViewById(R.id.txtSignIn);
        txtNew = (TextView) findViewById(R.id.txtNew);
        txtName = (TextView) findViewById(R.id.txtName);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        imgFbSignup = (ImageView) findViewById(R.id.imgFbSignup);
        imgGoogleSignup = (ImageView) findViewById(R.id.imgGoogleSignup);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        tilName = (TextInputLayout) findViewById(R.id.tilName);
        tilName.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });
        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtSignIn:
                if (validate()) {
                    accessPermission();


                   /* ArrayList<PersonalInfo> PersonList = PersonalInfoQuery.fetchOneRecord(username, password);
                    if (PersonList.size() != 0) {
                        for (int i = 0; i < PersonList.size(); i++) {
                            if (username.equals(PersonList.get(i).getEmail()) && password.equals(PersonList.get(i).getPassword())) {
                                Toast.makeText(context, "You have Logged in Successfully", Toast.LENGTH_SHORT).show();
                                preferences.putString(PrefConstants.USER_EMAIL, PersonList.get(i).getEmail());
                                preferences.putString(PrefConstants.USER_NAME, PersonList.get(i).getName());
                               // String saveThis = Base64.encodeToString(PersonList.get(i).getPhoto(), Base64.DEFAULT);
                                preferences.putString(PrefConstants.USER_PROFILEIMAGE, PersonList.get(i).getPhoto());
                                preferences.putInt(PrefConstants.USER_ID, PersonList.get(i).getId());
                                preferences.setREGISTERED(true);
                                preferences.setLogin(true);
                                Intent signinIntent = new Intent(context, BaseActivity.class);
                                startActivity(signinIntent);
                                finish();
                            }
                        }
                    } else {
                        Toast.makeText(context, "Enter correct Username or Password", Toast.LENGTH_SHORT).show();
                        txtUserName.setText("");
                        txtPassword.setText("");
                    }*/

                }

               /* Intent signinIntent = new Intent(context, BaseActivity.class);
                startActivity(signinIntent);
                finish();*/
                break;
            case R.id.txtNew:
                Intent signupIntent = new Intent(context, SignUpActivity.class);
                startActivity(signupIntent);
                finish();
                break;
            case R.id.txtForgotPassword:

                break;

            case R.id.imgFbSignup:

                break;

            case R.id.imgGoogleSignup:

                break;
        }
    }

    private boolean validate() {
        username = txtUserName.getText().toString().trim();
        name = txtName.getText().toString().trim();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            DialogManager.showAlert("Please Enter Name", context);
        } else if (username.equals("")) {
            txtUserName.setError("Please Enter email");
            DialogManager.showAlert("Please Enter email", context);
        } else if (!username.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtUserName.setError("Please enter valid email");
            DialogManager.showAlert("Please enter valid email", context);
        } else {
            return true;
        }

        return false;
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    class GetUserAsynk extends AsyncTask<Void, Void, String> {
        ProgressDialog pd;
        String name = "";
        String email = "";

        public GetUserAsynk(String name, String email) {
            this.name = name;
            this.email = email;
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
            WebService webService = new WebService();
            String result = webService.getProfile(name, email);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }
            if (!result.equals("")) {
                if (result.equals("Exception")) {
                    //ErrorDialog.errorDialog(context);
                } else {
                    Log.e("CreateUserAsynk", result);
                    String errorCode = parseResponseg(result);
                    if (errorCode.equals("0")) {
                      //  Toast.makeText(context, "You have logged in successfully", Toast.LENGTH_LONG).show();
                    } else {
                       // Toast.makeText(context, "No details", Toast.LENGTH_LONG).show();
                    }
                }
            }
            super.onPostExecute(result);
        }

    }

    public String parseResponseg(String result) {
        Log.e("Response", result);
        JSONObject job = null;
        String errorCode = "";
        try {
            job = new JSONObject(result);
            JSONObject job1 = job.getJSONObject("response");
            errorCode = job1.getString("errorCode");
            String message = "";
            if (errorCode.equals("0")) {
                message = job1.getString("respMsg");
                JSONObject job2 = job1.getJSONObject("respData");
                String userId = job2.getString("user_id");
                Log.e("SuccessFullLogin", "UserId= " + userId);
                int userid = Integer.parseInt(userId);
                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                //After Success
                Boolean flag = MyConnectionsQuery.insertMyConnectionsData(userid, name, username, "", "", "", "", "Self", "", "", 1, 2, "", "");
                if (flag == true) {
                    RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
                    String mail=connection.getEmail();
                    mail=mail.replace(".","_");
                    mail=mail.replace("@","_");
                    DBHelper dbHelper=new DBHelper(context,mail);
                    MyConnectionsQuery m=new MyConnectionsQuery(context,dbHelper);
                    Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getId(), name, username, "", "", "", "", "Self", "", "", 1, 2, "", "");
                    if (flags == true) {
                       // Toast.makeText(context, "You have created db Successfully", Toast.LENGTH_SHORT).show();
                    }
                  //  Toast.makeText(context,"You have added profile Successfully",Toast.LENGTH_SHORT).show();
                    preferences.putInt(PrefConstants.USER_ID, userid);
                    Intent signupIntent = new Intent(context, BaseActivity.class);
                    preferences.putString(PrefConstants.USER_EMAIL, username);
                    preferences.putString(PrefConstants.USER_NAME, name);
                    preferences.setREGISTERED(true);
                    preferences.setLogin(true);
                    startActivity(signupIntent);
                    finish();
                } else {
                    Toast.makeText(context, "Error to save in database", Toast.LENGTH_SHORT).show();
                }

                return errorCode;
            } else {
                message = job1.getString("errorMsg");
                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                return errorCode;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "Exception";
        }

    }
    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        } else {

            if (!NetworkUtils.getConnectivityStatusString(LoginActivity.this).equals("Not connected to Internet")) {
                GetUserAsynk asynkTask = new GetUserAsynk(name, username);
                asynkTask.execute();
            } else {
                DialogManager.showAlert("Network Error, Check your internet connection", LoginActivity.this);
            }
            try {
                File f=new File(Environment.getExternalStorageDirectory(),"/MYLO/MASTER/");
                if (!f.exists())
                {
                    f.mkdirs();
                }
                else{
                    try {
                        File file=new File(Environment.getExternalStorageDirectory(),"/MYLO/");
                        FileUtils.deleteDirectory(file);
                        f.mkdirs();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED) {

                    if (!NetworkUtils.getConnectivityStatusString(LoginActivity.this).equals("Not connected to Internet")) {
                        GetUserAsynk asynkTask = new GetUserAsynk(name, username);
                        asynkTask.execute();
                    } else {
                        DialogManager.showAlert("Network Error, Check your internet connection", LoginActivity.this);
                    }
                    try {
                        File f=new File(Environment.getExternalStorageDirectory(),"/MYLO/MASTER/");
                        if (!f.exists())
                        {
                            f.mkdirs();
                        }
                        else{
                            try {
                                File file=new File(Environment.getExternalStorageDirectory(),"/MYLO/");
                                FileUtils.deleteDirectory(file);
                                f.mkdirs();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                } else {

                    accessPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}
