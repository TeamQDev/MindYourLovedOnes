package com.mindyourlovedones.healthcare.DashBoard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.android.Auth;
import com.dropbox.core.v2.users.FullAccount;
import com.mindyourlovedones.healthcare.DropBox.DropboxActivity;
import com.mindyourlovedones.healthcare.DropBox.DropboxClientFactory;
import com.mindyourlovedones.healthcare.DropBox.FilesActivity;
import com.mindyourlovedones.healthcare.DropBox.GetCurrentAccountTask;
import com.mindyourlovedones.healthcare.DropBox.UnZipDirectoryTask;
import com.mindyourlovedones.healthcare.DropBox.UnZipTask;
import com.mindyourlovedones.healthcare.DropBox.ZipListner;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedones.healthcare.model.RelativeConnection;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DropboxLoginActivity extends DropboxActivity implements ZipListner {
    private static final int RESULTCODE = 400;
    Context context = this;
    private static final String APP_KEY = "428h5i4dsj95eeh";
    private static final String APP_SECRET = "6vlowskz2x12xil";
    private static final int REQUEST_CALL_PERMISSION = 100;
    private static final int REQUESTRESULT = 200;
    Button btnLogin, btnAdd;
    Button btnFiles, btnBackup, btnRestore;
    TextView txtName, txtFile;
    ImageView imgBack, imgDot;
    static boolean isLogin = false;
    Preferences preferences;
    String from = "";
    String todo = "";
    String todoWhat = "";
    RelativeLayout rlBackup, rlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropbox);
        preferences = new Preferences(context);
        preferences.putString(PrefConstants.RESULT, "");
        preferences.putString(PrefConstants.URI, "");
        accessPermission();
        imgDot = findViewById(R.id.imgDot);
        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, InstructionActivity.class);
                i.putExtra("From", "Dropbox");
                startActivity(i);
            }
        });
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        txtName = (TextView) findViewById(R.id.txtLogin);
        txtFile = (TextView) findViewById(R.id.txtfile);
        btnBackup = (Button) findViewById(R.id.btnBackup);
        btnRestore = (Button) findViewById(R.id.btnRestore);
        rlView = (RelativeLayout) findViewById(R.id.rlView);
        rlBackup = (RelativeLayout) findViewById(R.id.rlBackup);
        btnFiles = (Button) findViewById(R.id.btnFiles);
        imgBack = (ImageView) findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            from = intent.getExtras().getString("FROM");
            todo = intent.getExtras().getString("ToDo");
            todoWhat= intent.getExtras().getString("ToDoWhat");

            if (from.equals("Document")) {
                rlBackup.setVisibility(View.GONE);
                rlView.setVisibility(View.VISIBLE);
            } else if (from.equals("Backup")) {
                rlBackup.setVisibility(View.VISIBLE);
                rlView.setVisibility(View.GONE);
               /* if (todo.equals("Individual")&&todoWhat.equals("Import"))
                {
                    btnRestore.setVisibility(View.VISIBLE);
                    btnBackup.setVisibility(View.INVISIBLE);
                }
                else if (todo.equals("Individual")&&todoWhat.equals("Share"))
                {
                    btnRestore.setVisibility(View.INVISIBLE);
                    btnBackup.setVisibility(View.VISIBLE);
                }*/
            }
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("dropbox-sample", MODE_PRIVATE);
                if (prefs.contains("access-token")) {
                    prefs.edit().remove("access-token").apply();
                    com.dropbox.core.android.AuthActivity.result = null;
                    DropboxClientFactory.revokeClient(new DropboxClientFactory.CallBack() {
                        @Override
                        public void onRevoke() {
                            Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                            // onResume();
                        }
                    });
                } else {
                    Auth.startOAuth2Authentication(DropboxLoginActivity.this, APP_KEY);
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.putExtra("Name", preferences.getString(PrefConstants.RESULT));
                i.putExtra("URI", preferences.getString(PrefConstants.URI));
                setResult(RESULTCODE, i);
                finish();
            }
        });

        btnFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.STORE, "Document");
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            }
        });

        btnBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.STORE, "Backup");
                preferences.putString(PrefConstants.TODO, todo);
                startActivity(FilesActivity.getIntent(DropboxLoginActivity.this, ""));
            }
        });

        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.STORE, "Restore");
                preferences.putString(PrefConstants.TODO, todo);
                preferences.putString(PrefConstants.TODOWHAT,todoWhat);
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

                String value = "You have Logged in with " + result.getName().getDisplayName();
                txtName.setText(value);
                btnLogin.setText("Login With Different User");
                if (from.equals("Document")) {
                    btnFiles.setVisibility(View.VISIBLE);
                    btnBackup.setVisibility(View.GONE);
                    btnRestore.setVisibility(View.GONE);
                } else if (from.equals("Backup") || from.equals("Restore")) {
                    btnBackup.setVisibility(View.VISIBLE);
                    btnFiles.setVisibility(View.GONE);
                    btnRestore.setVisibility(View.VISIBLE);
                    if (todo.equals("Individual")&&todoWhat.equals("Import"))
                    {
                        btnRestore.setVisibility(View.VISIBLE);
                        btnBackup.setVisibility(View.INVISIBLE);
                    }
                    else if (todo.equals("Individual")&&todoWhat.equals("Share"))
                    {
                        btnRestore.setVisibility(View.INVISIBLE);
                        btnBackup.setVisibility(View.VISIBLE);
                    }
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
        if (preferences.getString(PrefConstants.URI).equals("") && preferences.getString(PrefConstants.RESULT).equals("")) {
            btnAdd.setVisibility(View.GONE);
            txtFile.setVisibility(View.GONE);
        } else {
            if (preferences.getString(PrefConstants.STORE).equals("Document")) {
                btnAdd.setVisibility(View.VISIBLE);
                txtFile.setVisibility(View.VISIBLE);
                txtFile.setText("Click on Add File for Add " + preferences.getString(PrefConstants.RESULT) + " File to your documents");
            } else if (preferences.getString(PrefConstants.STORE).equals("Restore")) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Restore?");
                alert.setMessage("Do you want to unzip and  restore " + preferences.getString(PrefConstants.RESULT) + " database?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String name = preferences.getString(PrefConstants.RESULT);
                        Log.v("NAME", name);
                        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
                            String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                            //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                            String backupDBPath = "/Download/" + name;
                            String newname = name.replace(".zip", "");
                            final File folder = new File(sd, backupDBPath);
                            final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                    "/MYLO/" + newname);
                            if (!destfolder.exists()) {
                                destfolder.mkdir();
                                new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
                            } else {
                               /* AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Note");
                                alert.setMessage("Profile is already exists");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();*/
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Replace?");
                                alert.setMessage("Profile is already exists, Do you want to replace?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
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
                        } else {
                            String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                            //  File data=DropboxLoginActivity.this.getDatabasePath(DBHelper.DATABASE_NAME);
                            String backupDBPath = "/Download/" + name;
                            String newname = name.replace(".zip", "");
                            final File folder = new File(sd, backupDBPath);
                            final File destfolder = new File(Environment.getExternalStorageDirectory(),
                                    newname);
                            if (!destfolder.exists()) {
                                destfolder.mkdir();
                                   }
                            new UnZipDirectoryTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();

                                   /* else {
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Note");
                                alert.setMessage("Profile is already exists");
                                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }*/
                           /* String sd = Environment.getExternalStorageDirectory().getAbsolutePath();
                            String backupDBPath = "/Download/" + "MYLO.zip";
                            File folder = new File(sd, backupDBPath);
                            File destfolder = new File(Environment.getExternalStorageDirectory(),
                                    "/MYLO/MYLO");
                            if (!destfolder.exists()) {
                                destfolder.mkdir();
                            }
                            new UnZipTask(DropboxLoginActivity.this, folder.getAbsolutePath(), destfolder.getAbsolutePath()).execute();
                        */}

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

           /* else if (preferences.getString(PrefConstants.STORE).equals("Backup")){
                final AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("BaCKUP");
                alert.setMessage("sUCCESS");
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
            }*/
        }
    }

    private void copydb(Context context) {
        if (preferences.getString(PrefConstants.TODO).equals("Individual")) {
            String backupDBPath = preferences.getString(PrefConstants.RESULT);
            backupDBPath = backupDBPath.replace(".zip", "");
            //open new imported db
            DBHelper dbHelper = new DBHelper(context, backupDBPath);
            MyConnectionsQuery m = new MyConnectionsQuery(context, dbHelper);
            //fetch data
            RelativeConnection connection = MyConnectionsQuery.fetchConnectionRecordforImport(1);
       /* Boolean flag = MyConnectionsQuery.updateImportMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), connection.getUserid());
        if (flag == true) {*/
            DBHelper dbHelpers = new DBHelper(context, "MASTER");
            MyConnectionsQuery ms = new MyConnectionsQuery(context, dbHelpers);
            RelativeConnection connections = MyConnectionsQuery.fetchConnectionRecordforImport(connection.getEmail());
            if (connections!=null)
            {
                if (connection.getRelationType().equals("Self"))
                {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                    }
                }
                else {
                    Boolean flags = MyConnectionsQuery.updateImportedMyConnectionsData(connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                    if (flags == true) {
                        Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                        storeImage(connection.getPhoto(), "Profile", backupDBPath);
                    }
                }
            }
            else {
                Boolean flags = MyConnectionsQuery.insertMyConnectionsData(connection.getUserid(), connection.getName(), connection.getEmail(), connection.getAddress(), connection.getMobile(), connection.getPhone(), connection.getWorkPhone(), "", connection.getPhoto(), "", 1, 2, connection.getOtherRelation(), connection.getPhotoCard());
                if (flags == true) {
                    Toast.makeText(context, "Data save to master db", Toast.LENGTH_SHORT).show();
                    storeImage(connection.getPhoto(), "Profile", backupDBPath);
                }
            }
        }else{
            //Toast.makeText(context, "Need Data save to master db", Toast.LENGTH_SHORT).show();
        }
      //  }
       /* String mail=connection.getEmail();
        mail=mail.replace(".","_");
        mail=mail.replace("@","_");
        preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
        preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
            */
       /* File data = DropboxLoginActivity.this.getDatabasePath("temp.db");
        Log.e("", data.getAbsolutePath());

        File currentDB = new File(data.getAbsolutePath());
        File backupDB = new File(sd, backupDBPath);
        try {
            copy(backupDB, currentDB);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    private void storeImage(String selectedImage, String profile, String backupDBPath) {
        FileOutputStream outStream1 = null;
        File createDir = new File(Environment.getExternalStorageDirectory()+"/MYLO/MASTER/");
        if(!createDir.exists()) {
            createDir.mkdir();
        }
        File file = new File(Environment.getExternalStorageDirectory()+"/MYLO/"+backupDBPath+"/"+selectedImage);
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        try {
            if (myBitmap!=null) {
                if (profile.equals("Profile")) {
                    outStream1 = new FileOutputStream(Environment.getExternalStorageDirectory()+"/MYLO/MASTER/"+selectedImage);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                    byte[] byteArray = stream.toByteArray();
                    outStream1.write(byteArray);
                    outStream1.close();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

      /*  FileOutputStream outStream2 = null;
        File fileori = new File(Environment.getExternalStorageDirectory()+"/MYLO/"+backupDBPath);
        File files = new File(Environment.getExternalStorageDirectory()+"/MYLO/MASTER/");
        if (!files.exists()) {
            files.mkdirs();
        }

        try {
            outStream2=new FileOutputStream(fileori);
            outStream2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }*/
    }
    private void copy(File backupDB, File currentDB) throws IOException {
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
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            in.close();
        }
    }


    @Override
    public void getFile(String res) {
        if (res.equals("Yes")) {
            copydb(context);
            Toast.makeText(context, "Unzipped and restored files Successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(context, "Restoring Failed, Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}
