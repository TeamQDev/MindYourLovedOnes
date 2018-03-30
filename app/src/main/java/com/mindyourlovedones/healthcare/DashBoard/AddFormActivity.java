
package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;


public class AddFormActivity extends AppCompatActivity  {
    private static final int RESULT_CARD = 50;
    ImageView imgDoc,imgBack,imgDelete,imgDot;
    boolean IsDelete=false;
    Bitmap myBitmap;
    File imgFile;
    Preferences preferences;
String From="";
TextView txtTitle;
    final String dialog_items[]={"Email"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        initUi();
        initListener();
    }

    private void initListener() {

    }

    private void initUi() {
        preferences=new Preferences(AddFormActivity.this);
        imgDot= (ImageView) findViewById(R.id.imgDot);
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgDoc= (ImageView) findViewById(R.id.imgDoc);
        imgDelete= (ImageView) findViewById(R.id.imgDelete);
        txtTitle=findViewById(R.id.txtTitle);

        
        if (IsDelete == true) {
            imgDelete.setVisibility(View.VISIBLE);
        } else {
            imgDelete.setVisibility(View.GONE);
        }

        Intent i=getIntent();
        if (i.getExtras()!=null) {
            String photo = i.getExtras().getString("Image");
            if (!photo.equals("")) {
                File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH),photo);
                imgFile= new File(photo);
                if (imgFile1.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                    if(myBitmap.getWidth() > myBitmap.getHeight())
                    {
                        // imgDoc.setRotation(180);
                    }else
                    {
                        imgDoc.setRotation(90);
                    }
                    imgDoc.setImageBitmap(myBitmap);

                    //   imgCard.setImageBitmap(myBitmap);
                }
            }
          /*  byte[] photo = i.getExtras().getByteArray("Image");
            Bitmap photoCard = BitmapFactory.decodeByteArray(photo, 0, photo.length);*/
         /*   int nh = (int) ( photoCard.getHeight() * (600.0 / photoCard.getWidth()) );
            Bitmap scaled = Bitmap.createScaledBitmap(photoCard, 600, nh, true);*/


            if (i.getExtras().containsKey("IsDelete")) {
                IsDelete = i.getExtras().getBoolean("IsDelete");
                if (IsDelete == true) {
                    imgDelete.setVisibility(View.VISIBLE);
                } else {
                    imgDelete.setVisibility(View.GONE);
                }
            }

            if (i.getExtras().containsKey("FROM")) {
                From = i.getExtras().getString("FROM");
            }
            if (From.equals("Insurance"))
            {
                txtTitle.setText("Insurance Card");
            }else{
                txtTitle.setText("Business Card");
            }
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.putExtra("Card","Delete");
                setResult(RESULT_CARD,i);
                finish();
            }
        });

        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(AddFormActivity.this);
                alert.setTitle("Email ?");
                alert.setMessage("Do you want to email card image ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File f =new File(imgFile.getAbsolutePath());
                        if (From.equals("Insurance"))
                        {
                            emailAttachement(imgFile.getAbsolutePath(), AddFormActivity.this, "Insurance Card");
                        }else {
                            emailAttachement(imgFile.getAbsolutePath(), AddFormActivity.this, "Business Card");
                        }
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
        });
    }

    public void emailAttachement(String path, Context context, String s) {
        File f = new File(preferences.getString(PrefConstants.CONNECTED_PATH),path);
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        String name=preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,s); // subject


        String body="Hi, \n" +
                "\n" +
                "\n" +name+
                " shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thanks,\n" +
                name;
        //"Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

        Uri uri=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", f);
        } else {
            uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
//emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
        emailIntent.setType("application/email");

        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
}