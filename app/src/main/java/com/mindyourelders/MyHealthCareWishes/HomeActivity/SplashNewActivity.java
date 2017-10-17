package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

public class SplashNewActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    TextView txtNew,txtRegistered,textMessage,txtWelcome;
    Preferences preferences;
    ImageView img1,img2,img3,img4,imgForword;
    RelativeLayout llBottom;
    LinearLayout llSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splashes);
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
        txtWelcome.setOnClickListener(this);
        imgForword.setOnClickListener(this);
    }

    private void initUI() {
        textMessage= (TextView) findViewById(R.id.textMessage);
        txtNew= (TextView) findViewById(R.id.txtNew);
        txtRegistered= (TextView) findViewById(R.id.txtRegistered);
        txtWelcome= (TextView) findViewById(R.id.txtWelcome);
        img1= (ImageView) findViewById(R.id.img1);
        img2= (ImageView) findViewById(R.id.img2);
        img3= (ImageView) findViewById(R.id.img3);
        img4= (ImageView) findViewById(R.id.img4);
        imgForword= (ImageView) findViewById(R.id.imgForword);
        llBottom= (RelativeLayout) findViewById(R.id.llBottom);
        llSplash= (LinearLayout) findViewById(R.id.llSplash);

      final String[] array = {"Access to Critical Information And Health Care Directives 24/7",getResources().getString(R.string.msg),"Manage and share critical documents and information from your phone or tablet","All Information on this app resides on your smartphone or tablet.  HIPAA federal privacy rules do not apply to this app because the app is in your control.","Mind Your Elders only has access to your email address"};
        textMessage.post(new Runnable() {
            int i = 0;
            @Override
            public void run() {

                textMessage.setText(array[i]);
                Animation RightSwipe = AnimationUtils.loadAnimation(context, R.anim.enter);
                textMessage.startAnimation(RightSwipe);
                i++;
                if (i ==3)
                    i = 0;
                textMessage.postDelayed(this, 5000);
            }
        });

        if (preferences == null) {
            preferences = new Preferences(SplashNewActivity.this);
        }
        if (preferences.getREGISTERED()) {

            if (preferences.isLogin()) {
                llBottom.setVisibility(View.VISIBLE);
                llSplash.setVisibility(View.GONE);
                txtWelcome.setText("Welcome Back "+ preferences.getString(PrefConstants.USER_NAME));
            } else {
                llBottom.setVisibility(View.GONE);
                llSplash.setVisibility(View.VISIBLE);
            }
        }
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

            case R.id.txtWelcome:
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
            case R.id.imgForword:
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
