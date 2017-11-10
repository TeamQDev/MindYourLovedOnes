
package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;


public class AddFormActivity extends AppCompatActivity  {
    ImageView imgDoc,imgBack;

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

        Intent i=getIntent();
        if (i.getExtras()!=null) {
            byte[] photo = i.getExtras().getByteArray("Image");
            Bitmap photoCard = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            imgDoc.setImageBitmap(photoCard);
        }


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}