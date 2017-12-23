
package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

import java.io.File;


public class AddFormActivity extends AppCompatActivity  {
    private static final int RESULT_CARD = 50;
    ImageView imgDoc,imgBack,imgDelete;
    boolean IsDelete=false;
    Bitmap myBitmap;
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
        
        if (IsDelete == true) {
            imgDelete.setVisibility(View.VISIBLE);
        } else {
            imgDelete.setVisibility(View.GONE);
        }

        Intent i=getIntent();
        if (i.getExtras()!=null) {
            String photo = i.getExtras().getString("Image");
            if (!photo.equals("")) {
                File imgFile1 = new File(photo);
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
    }
}