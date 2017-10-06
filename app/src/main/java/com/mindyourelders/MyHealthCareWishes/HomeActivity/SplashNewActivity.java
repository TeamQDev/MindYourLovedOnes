package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

public class SplashNewActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    TextView txtNew,txtRegistered;
    Preferences preferences;
    ImageView img1,img2,img3,img4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splash);
        variableInitialization();
        initUI();
        initListener();
        init();

    }



    private void init() {

    }
    private void variableInitialization() {
        preferences = new Preferences(this);
        //for dashboard webservice call
        preferences.setFirstTimeCall(true);
    }

    private void initListener() {
        txtNew.setOnClickListener(this);
        txtRegistered.setOnClickListener(this);
    }

    private void initUI() {
        txtNew= (TextView) findViewById(R.id.txtNew);
        txtRegistered= (TextView) findViewById(R.id.txtRegistered);
        img1= (ImageView) findViewById(R.id.img1);
        img2= (ImageView) findViewById(R.id.img2);
        img3= (ImageView) findViewById(R.id.img3);
        img4= (ImageView) findViewById(R.id.img4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtNew:
                Intent intent=new Intent(context,SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.txtRegistered:
                if (preferences == null) {
                    preferences = new Preferences(SplashNewActivity.this);
                }

                if (preferences.getREGISTERED()) {
                    startActivity(new Intent(SplashNewActivity.this, BaseActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashNewActivity.this, LoginActivity.class));
                    finish();
                }

                break;
        }
    }
 /*   public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        resizeImage(img1);
        resizeImage(img2);
        resizeImage(img3);
        resizeImage(img4);
        // Abstracting out the process where you get the image from the internet

    }

    private void resizeImage(ImageView img) {
        Bitmap loadedImage = ((BitmapDrawable) img.getDrawable()).getBitmap();

        // Gets the width you want it to be
        int intendedWidth = img.getWidth();

        // Gets the downloaded image dimensions
        int originalWidth = loadedImage.getWidth();
        int originalHeight = loadedImage.getHeight();

        // Calculates the new dimensions
        float scale = (float) intendedWidth / originalWidth;
        int newHeight = (int) Math.round(originalHeight * scale);

        // Resizes mImageView. Change "FrameLayout" to whatever layout mImageView is located in.
        img.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        img.getLayoutParams().width = intendedWidth;
        img.getLayoutParams().height = newHeight;
    }*/
}
