package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.Manifest;
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


public class DropboxLoginActivity extends DropboxActivity {
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private static final String APP_SECRET = "6vlowskz2x12xil";
    private static final int REQUEST_CALL_PERMISSION = 100;
    Button btnLogin;
    Button btnFiles;
    TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        accessPermission();
        btnLogin= (Button) findViewById(R.id.btnLogin);
        txtName= (TextView) findViewById(R.id.txtLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(DropboxLoginActivity.this,APP_KEY);
            }
        });
        btnFiles = (Button)findViewById(R.id.btnFiles);
        btnFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
