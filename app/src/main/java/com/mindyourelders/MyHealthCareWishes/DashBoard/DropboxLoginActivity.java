package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import static com.mindyourelders.MyHealthCareWishes.utility.PrefConstants.URI;


public class DropboxLoginActivity extends DropboxActivity {
    private static final int RESULTCODE = 400;
    Context context=this;
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private static final String APP_SECRET = "6vlowskz2x12xil";
    private static final int REQUEST_CALL_PERMISSION = 100;
    private static final int REQUESTRESULT =200 ;
    Button btnLogin,btnAdd;
    Button btnFiles,btnBackup,btnRestore;
    TextView txtName,txtFile;
    static boolean isLogin=false;
    Preferences preferences;
    String from="";
    RelativeLayout rlBackup,rlView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        preferences=new Preferences(context);
        preferences.putString(PrefConstants.RESULT,"");
        preferences.putString(URI,"");
        accessPermission();



        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnAdd= (Button) findViewById(R.id.btnAdd);
        txtName= (TextView) findViewById(R.id.txtLogin);
        txtFile= (TextView) findViewById(R.id.txtfile);
        btnBackup= (Button) findViewById(R.id.btnBackup);
        btnRestore= (Button) findViewById(R.id.btnRestore);
        rlView= (RelativeLayout) findViewById(R.id.rlView);
        rlBackup= (RelativeLayout) findViewById(R.id.rlBackup);
        btnFiles = (Button)findViewById(R.id.btnFiles);

        Intent intent=getIntent();
        if (intent.getExtras()!=null) {
            from =intent.getExtras().getString("FROM");
            if (from.equals("Document"))
            {
                rlBackup.setVisibility(View.GONE);
                rlView.setVisibility(View.VISIBLE);
            }else if (from.equals("Backup"))
            {
                rlBackup.setVisibility(View.VISIBLE);
                rlView.setVisibility(View.GONE);
            }
        }
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
                i.putExtra("URI",preferences.getString(URI));
                setResult(RESULTCODE,i);
                finish();
            }
        });

        btnFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* removeToken(new DropboxClientFactory.CallBack() {
                    @Override
                    public void onRevoke() {
                        onResume();
                    }
                });*/
               preferences.putString(PrefConstants.STORE,"Document");
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            }
        });
        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.STORE,"Backup");

                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            }
        });
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.STORE,"Restore");

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
                btnLogin.setText("Login With Different User");
                if (from.equals("Document"))
                {
                    btnFiles.setVisibility(View.VISIBLE);
                    btnBackup.setVisibility(View.GONE);
                    btnRestore.setVisibility(View.GONE);
                }else if (from.equals("Backup")||from.equals("Restore"))
                {
                    btnBackup.setVisibility(View.VISIBLE);
                    btnFiles.setVisibility(View.GONE);
                    btnRestore.setVisibility(View.VISIBLE);
                }


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
       if (preferences.getString(URI).equals("")&&preferences.getString(PrefConstants.RESULT).equals(""))
       {
           btnAdd.setVisibility(View.GONE);
           txtFile.setVisibility(View.GONE);
       }else
        {
            if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                btnAdd.setVisibility(View.VISIBLE);
                txtFile.setVisibility(View.VISIBLE);
                txtFile.setText("Click on Add File for Add " + preferences.getString(PrefConstants.RESULT) + " File to your documents");
            }
            else if (preferences.getString(PrefConstants.STORE).equals("Restore")){
                final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Restore?");
                alert.setMessage("Do you want to restore "+preferences.getString(PrefConstants.RESULT)+" database?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        copydb(context);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });
                alert.show();
            }
        }
    }

    private void copydb(Context context) {
       // String sd = Environment.getExternalStorageDirectory().getAbsolutePath(); ;
       // Log.e("", sd);
        String sd=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

        /*Uri uris= Uri.parse(URI);
        String path=uris.getPath();*/
        File data = Environment.getDataDirectory();
        Log.e("", data.getAbsolutePath());
        FileChannel source = null;
        FileChannel destination = null;
        String currentDBPath = "//data//com.mindyourelders.MyHealthCareWishes.HomeActivity"
                + "//databases//" + "MYE.db";
      //  String backupDBPath = "/Download/" + "CONTACTS.db";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd,"MYE.db");
        try {
            copy(backupDB,currentDB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void copy(File backupDB, File currentDB)throws IOException {
        InputStream in = new FileInputStream(backupDB);
        try {
            OutputStream out = new FileOutputStream(currentDB);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }
}
