
package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

import java.io.File;


public class ViewImageActivity extends AppCompatActivity  {
    private static final int RESULT_CARD = 50;
    ImageView imgDoc,imgBack,imgDelete;
    boolean IsDelete=false;
    String photo=null;
    TextView txtTitle;

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
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgDoc= (ImageView) findViewById(R.id.imgDoc);
        imgDelete= (ImageView) findViewById(R.id.imgDelete);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("Prescription");

        Intent i=getIntent();
        if (i.getExtras()!=null) {
            photo = i.getExtras().getString("Image");
            if (!photo.equals("")) {
                File imgFile = new File(photo);
                //  if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgDoc.setImageBitmap(myBitmap);
                //imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgConPhoto,displayImageOptions);
            }
           /* Bitmap photoCard = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imgDoc.setImageBitmap(photoCard);*/

          /*  if (i.getExtras().containsKey("IsDelete")) {
                IsDelete = i.getExtras().getBoolean("IsDelete");
                if (IsDelete == true) {
                    imgDelete.setVisibility(View.VISIBLE);
                } else {
                    imgDelete.setVisibility(View.GONE);
                }
            }*/
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
                i.putExtra("Prescription","Delete");
                i.putExtra("Photo",photo);
                setResult(RESULT_CARD,i);
                finish();
            }
        });
    }
}