package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;


public class DocumentSdCardList extends AppCompatActivity {
    Context context=this;
    ArrayAdapter adapter;
    int clickCounter=0;
    ListView lvDoc;
    ArrayList listItems=new ArrayList();
    private File[] imagelist;
    String[] pdflist;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    ImageView imgBack;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_sd_card_list);

      //  checkRuntimePermission();
        lvDoc= (ListView) findViewById(R.id.lvDoc);
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       showFile();
    }

    private void showFile() {
        File images = Environment.getExternalStorageDirectory();
        imagelist = images.listFiles(new FilenameFilter(){
            public boolean accept(File dir, String name)
            {
                return ((name.endsWith(".pdf")));
            }
        });

        if (imagelist!=null) {
            pdflist = new String[imagelist.length];
            for (int i = 0; i < imagelist.length; i++) {
                pdflist[i] = imagelist[i].getName();
            }
            PdfAdapter adapter=new PdfAdapter(context,pdflist,imagelist);
            lvDoc.setAdapter(adapter);
        }
        else{
            Toast.makeText(context,"No Files Available in Sdcard",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
            return false;
        } else {
            return true;
        }
       /* if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(DocumentSdCardList.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(DocumentSdCardList.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(DocumentSdCardList.this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);

            }
            return false;
        } else {
            return true;
        }*/
    }

    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0 && imagelist[(int) id].isFile()) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(imagelist[(int) id].getAbsoluteFile());
            intent.setDataAndType(uri, "application/pdf");
            startActivity(intent);
        }*/

   // }

    public void getData(String name, String uri) {
        Intent i=new Intent();
        i.putExtra("Name",name);
        i.putExtra("URI",uri);

        setResult(100,i);
    }
}