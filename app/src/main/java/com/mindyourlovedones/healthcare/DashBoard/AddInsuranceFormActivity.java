package com.mindyourlovedones.healthcare.DashBoard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.FormQuery;
import com.mindyourlovedones.healthcare.model.Form;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AddInsuranceFormActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RESULTCODE = 200;
    private static final int RQUESTCODE = 400;
    Context context = this;
    ImageView imgBack, imgDot, imgDone, imgDoc, imgAdd;
    TextView txtName,txtAdd;
    TextInputLayout tilName;
    String From;
    Preferences preferences;
    final CharSequence[] alert_items = {"Phone Storage", "Dropbox"};
    final CharSequence[] dialog_items = {"View", "Email", "Fax"};
    Form document;
    DBHelper dbHelper;
    String name = "";

    String documentPath = "";
    String originPath = "";
    int photo;
    String path = "";
    String Goto = "";
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_insurance_form);
        initComponent();
        initUi();
        initListener();
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgDot.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        imgDoc.setOnClickListener(this);

    }

    private void initUi() {
        imgDot = (ImageView) findViewById(R.id.imgDot);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgDoc = (ImageView) findViewById(R.id.imgDoc);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        txtName = (TextView) findViewById(R.id.txtName);
        tilName = findViewById(R.id.tilName);
        txtAdd = (TextView) findViewById(R.id.txtAdd);

        
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                return false;
            }
        });
        Intent i = getIntent();
        if (i.getExtras() != null) {
            Goto = i.getExtras().getString("GoTo");
            //path=i.getExtras().getString("Path");
        }

        if (Goto.equals("View")) {
            imgDot.setVisibility(View.VISIBLE);
            imgDone.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);

            document = (Form) i.getExtras().getSerializable("FormObject");
            txtName.setText(document.getName());
            documentPath = document.getDocument();
            imgDoc.setImageResource(document.getImage());
            txtAdd.setVisibility(View.GONE);

        } else if (Goto.endsWith("Edit")) {
            document = (Form) i.getExtras().getSerializable("FormObject");
            txtName.setText(document.getName());
            documentPath = document.getDocument();
            imgDoc.setImageResource(document.getImage());
            id = document.getId();
            imgDot.setVisibility(View.GONE);
            imgDone.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
            txtAdd.setVisibility(View.VISIBLE);
            txtAdd.setText("Edit File");
        } else {
            imgDot.setVisibility(View.GONE);
            imgDone.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
            txtAdd.setVisibility(View.VISIBLE);
            txtAdd.setText("Select File");
        }

    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
        FormQuery d = new FormQuery(context, dbHelper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgDoc:
                Uri uri=null;
                if (!documentPath.equals("")) {
                    File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),documentPath);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
                    } else {
                        uri = Uri.fromFile(targetFile);
                    }
                    // Uri uris = Uri.parse(documentPath);
                    intent.setDataAndType(uri, "application/pdf");
                    context.startActivity(intent);
                   /* Uri uris = Uri.parse(documentPath);
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //  uri = FileProvider.getUriForFile(context, "com.mindyourelders.healthcare.HomeActivity.fileProvider", targetFile);
                    } else {
                        //  uri = Uri.fromFile(targetFile);
                    }
                    intent.setDataAndType(uris, "application/pdf");
                    context.startActivity(intent);*/


                }

                break;

            case R.id.imgDone:
                if (validate()) {
                    documentPath=copydb(originPath,name);
                    if (Goto.equals("Edit")) {
                        Boolean flag = FormQuery.updateDocumentData(id, name, photo, documentPath);
                        if (flag == true) {
                            Toast.makeText(context, "You have updated document successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = FormQuery.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, photo, documentPath);
                        if (flag == true) {
                            Toast.makeText(context, "You have added form successfully", Toast.LENGTH_SHORT).show();
                            try {
                                InputMethodManager inm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
                                inm.hideSoftInputFromWindow(AddInsuranceFormActivity.this.getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                //Todo: handle exception
                            }

                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                    break;

                    case R.id.imgAdd:
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddInsuranceFormActivity.this);

                        builder.setTitle("");
                        builder.setItems(alert_items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int itemPos) {

                                switch (itemPos) {
                                    case 0:
                                        Intent i = new Intent(context, DocumentSdCardList.class);
                                        startActivityForResult(i, RESULTCODE);
                                        break;
                                    case 1:
                                        Intent intent = new Intent(context, DropboxLoginActivity.class);
                                        intent.putExtra("FROM", "Document");
                                        startActivityForResult(intent, RQUESTCODE);
                                        break;

                                }

                            }

                        });

                        builder.create().show();

                        break;

                    case R.id.imgDot:

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);

                        alert.setTitle("");

                        alert.setItems(dialog_items, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int itemPos) {

                                switch (itemPos) {
                                    case 0: //view
                                        Uri uri=null;
                                        if (!documentPath.equals("")) {
                                            File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),documentPath);
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_VIEW);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                                uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
                                            } else {
                                                uri = Uri.fromFile(targetFile);
                                            }
                                            // Uri uris = Uri.parse(documentPath);
                                            intent.setDataAndType(uri, "application/pdf");
                                            context.startActivity(intent);
                                        }
                                       /* Uri uris = Uri.parse(documentPath);
                                        Intent intent = new Intent();
                                        intent.setAction(Intent.ACTION_VIEW);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                            //  uri = FileProvider.getUriForFile(context, "com.mindyourelders.healthcare.HomeActivity.fileProvider", targetFile);
                                        } else {
                                            //  uri = Uri.fromFile(targetFile);
                                        }
                                        intent.setDataAndType(uris, "application/pdf");
                                        context.startActivity(intent);*/
                                        break;
                                    case 1: //email
                                        Uri urisd = Uri.parse(documentPath);
                                        emailAttachement(documentPath, txtName.getText().toString());
                                        break;
                                    case 2: // fax
                                        //Uri uri= Uri.parse(documentPath);
                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + txtName.getText().toString();
                                        new FaxCustomDialog(AddInsuranceFormActivity.this, preferences.getString(PrefConstants.CONNECTED_PATH)+documentPath).show();
                                        ;

                                        break;

                                }
                            }
                        });

                        alert.create().show();
                        // ((CarePlanActivity)context).CopyAssets();
                        break;

                }
        }

    private String copydb(String originPath, String name) {


        String sd= preferences.getString(PrefConstants.CONNECTED_PATH);

        File data=new File(originPath);
        Log.e("", data.getAbsolutePath());
        String backupDBPath = "/MYLO/MYLO/";

        File file = new File(sd);
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        File currentDB = new File( data.getAbsolutePath());
        File backupDB = new File(path,name);

        if (!backupDB.exists()) {
            try {
                backupDB.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            copy(backupDB,currentDB);
            return backupDB.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void copy(File backupDB, File currentDB)throws IOException {
        try (InputStream in = new FileInputStream(currentDB)) {
            try (OutputStream out = new FileOutputStream(backupDB)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }
    
    private void emailAttachement(String uris, String s) {
        Uri uri=null;
        File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),documentPath);
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        String name = preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                name + " - " + s); // subject


        String body = "Hi, \n" +
                "\n" +
                "\n" + name +
                " shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thanks,\n" +
                name;
              //  "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
        } else {
            uri = Uri.fromFile(targetFile);
        }

        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private boolean validate() {
        photo = R.drawable.pdf;
        name = txtName.getText().toString();
        if (name.length() == 0) {
            Toast.makeText(context, "Add Name of document", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTCODE && data != null) {
            name = data.getExtras().getString("Name");
            originPath = data.getExtras().getString("URI");
            txtName.setText(name);
            String text = "You Have selected <b>" + name + "</b> Document";
            Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
            imgDoc.setClickable(false);
            imgDoc.setImageResource(R.drawable.pdf);
            txtAdd.setText("Edit File");
            ShowWindowDialog(text);
        } else if (requestCode == RQUESTCODE && data != null) {
            name = data.getExtras().getString("Name");
            originPath = data.getExtras().getString("URI");
            txtName.setText(name);
            String text = "You Have selected <b>" + name + "</b> Document";
            Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
            imgDoc.setImageResource(R.drawable.pdf);
            imgDoc.setClickable(false);
            txtAdd.setText("Edit File");
            ShowWindowDialog(text);
        }
    }

    private void ShowWindowDialog(String text) {
        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setMessage(Html.fromHtml(text));
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

}
