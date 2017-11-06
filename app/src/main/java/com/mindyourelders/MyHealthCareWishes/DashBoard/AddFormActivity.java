
package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.model.Document;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class AddFormActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    ImageView imgBack,imgDot,imgDone,imgDoc,imgAdd;
    TextView txtName;
    String From;
    Preferences preferences;
    final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    Document document;
    DBHelper dbHelper;
    String name="";

    String documentPath="";
    int photo;
    String path="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        initComponent();
        initUi();
        initListener();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
       // DocumentQuery d=new DocumentQuery(context,dbHelper);
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
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgDoc:
                if (!documentPath.equals(""))
                {
                    Uri uri=null;
                    if (path.equals("No"))
                    {
                       /* uri =  Uri.parse("android.resource://"+getPackageName()+"/raw/"+documentPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);*/
                       CopyReadAssetss(documentPath);
                       /* uri= Uri.fromFile(getFileStreamPath("file:///android_asset/"+documentPath));
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        context.startActivity(intent);*/
                    }
                    else{
                        uri= Uri.parse(documentPath);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        context.startActivity(intent);
                    }



                }
                break;

            case R.id.imgDone:
                /*if (validate()) {
                    Boolean flag = DocumentQuery.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name,photo,documentPath);
                    if (flag == true) {
                        Toast.makeText(context, "You have added proxy contact successfully", Toast.LENGTH_SHORT).show();
                       finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
*/
                break;

            case R.id.imgAdd:
                Intent i=new Intent(context,DocumentSdCardList.class);
                startActivityForResult(i,100);
                break;

            case R.id.imgDot:


                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                            case 0: // email

                       /* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*/
                                break;
                            case 1: // email

                       /* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*/

                                break;
                            case 2: // view
                                Uri uri=null;
                                if (path.equals("No"))
                                {
                                    CopyReadAssetss(documentPath);
                                }
                                else{
                                    uri= Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/pdf");
                                    context.startActivity(intent);
                                }

                                break;

                        }
                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
                break;

        }
    }

    private void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try
        {
            in = assetManager.open(documentPath);
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }
        Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
      /*  Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() +"/"+documentPath),
                "application/pdf");

        startActivity(intent);*/
    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }



    public void onPDFClicked(String fileName) {
        final File item = new File(fileName);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("");

        builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int itemPos) {

                switch (itemPos) {
                    case 0: // email

                       /* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*/
                        break;
                    case 1: // email

                       /* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*/

                        break;
                    case 2: // view

                        if (!documentPath.equals(""))
                        {
                            Uri uri= Uri.parse(documentPath);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/pdf");
                            context.startActivity(intent);

                        }
                        break;

                }
            }
        });

        builder.create().show();
    }
    private void viewFile(Context context, String filename) {
        File targetFile = new File(filename);
        Uri targetUri = Uri.fromFile(targetFile);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileprovider", targetFile);
            intent.setDataAndType(contentUri, "application/pdf");
        } else {
            intent.setDataAndType(Uri.fromFile(targetFile), "application/pdf");
        }

        if (targetFile.getName().endsWith(".pdf")) {
            intent.setPackage("com.adobe.reader");
            intent.setDataAndType(targetUri, "application/pdf");

            try {
                startActivity(intent);

            } catch (ActivityNotFoundException e) {
                // No application to view, ask to download one

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Application Found");
                builder.setMessage("Download Office Tool from Google Play ?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent marketIntent = new Intent(
                                        Intent.ACTION_VIEW);
                                marketIntent.setData(Uri
                                        .parse("market://details?id=com.adobe.reader"));
                                startActivity(marketIntent);
                            }
                        });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }

        }
    }

    public void CopyReadAssets(String fileName) {
        File dir = new File(Environment.getExternalStorageDirectory()
                + "/mhcw/");
        dir.mkdirs();

        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory()
                + "/mhcw/" + fileName);
        try {
            in = assetManager.open(fileName);
            out = new BufferedOutputStream(new FileOutputStream(file));

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void copyFile(InputStream in, OutputStream out)throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && data!=null)
        {
            name=data.getExtras().getString("Name");
            documentPath=data.getExtras().getString("URI");
            txtName.setText(name);
        }
    }
}