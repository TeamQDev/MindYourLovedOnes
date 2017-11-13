package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.users.FullAccount;
import com.mindyourelders.MyHealthCareWishes.DropBox.DropboxActivity;
import com.mindyourelders.MyHealthCareWishes.DropBox.DropboxClientFactory;
import com.mindyourelders.MyHealthCareWishes.DropBox.FilesActivity;
import com.mindyourelders.MyHealthCareWishes.DropBox.GetCurrentAccountTask;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;


public class DropboxLoginActivity extends DropboxActivity {
    private static final int RESULTCODE = 400;
    Context context=this;
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private static final String APP_SECRET = "6vlowskz2x12xil";
    private static final int REQUEST_CALL_PERMISSION = 100;
    private static final int REQUESTRESULT =200 ;
    Button btnLogin,btnAdd;
    Button btnFiles;
    TextView txtName,txtFile;
    static boolean isLogin=false;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        preferences=new Preferences(context);
        preferences.putString(PrefConstants.RESULT,"");
        preferences.putString(PrefConstants.URI,"");
        accessPermission();
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        txtName= (TextView) findViewById(R.id.txtLogin);
        txtFile= (TextView) findViewById(R.id.txtfile);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token"))
                {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                          // onResume();
                        }
                    });
                }
                else{
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }



            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent();
                i.putExtra("Name",preferences.getString(PrefConstants.RESULT));
                i.putExtra("URI",preferences.getString(PrefConstants.URI));
                setResult(RESULTCODE,i);
                finish();
            }
        });
        btnFiles = (Button)findViewById(R.id.btnFiles);
        btnFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* removeToken(new DropboxClientFactory.CallBack() {
                    @Override
                    public void onRevoke() {
                        onResume();
                    }
                });*/
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            }
        });
    }
    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(DropboxLoginActivity.this.getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(DropboxLoginActivity.this.getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        } else {
            // checkForRegistration();
        }
    }


    @Override
    protected void loadData() {
        new GetCurrentAccountTask(DropboxClientFactory.getClient(), new GetCurrentAccountTask.Callback() {
            @Override
            public void onComplete(FullAccount result) {

                String value="You have Logged in with " + result.getName().getDisplayName();
                txtName.setText(value);
                btnFiles.setVisibility(View.VISIBLE);
                btnLogin.setText("Login With Different User");

              //  Toast.makeText(DropboxLoginActivity.this,,Toast.LENGTH_SHORT).show();
                /*((TextView) findViewById(R.id.email_text)).setText(result.getEmail());
                ((TextView) findViewById(R.id.name_text)).setText(result.getName().getDisplayName());
                ((TextView) findViewById(R.id.type_text)).setText(result.getAccountType().name());*/
            }
            @Override
            public void onError(Exception e) {
                Log.e(getClass().getName(), "Failed to get account details.", e);
            }
        }).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
       if (preferences.getString(PrefConstants.URI).equals("")&&preferences.getString(PrefConstants.RESULT).equals(""))
       {
           btnAdd.setVisibility(View.GONE);
           txtFile.setVisibility(View.GONE);
       }else
        {
            btnAdd.setVisibility(View.VISIBLE);
            txtFile.setVisibility(View.VISIBLE);
            txtFile.setText("Click on Add File for Add "+preferences.getString(PrefConstants.RESULT)+" File to your documents");
        }
    }
}
