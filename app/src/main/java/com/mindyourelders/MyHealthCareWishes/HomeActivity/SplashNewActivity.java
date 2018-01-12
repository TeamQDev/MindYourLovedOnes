package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.fabric.sdk.android.Fabric;

public class SplashNewActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL_PERMISSION =100 ;
    Context context=this;
    TextView txtNew,txtRegistered,textMessage,txtWelcome;
    Preferences preferences;
    ImageView img1,img2,img3,img4,imgForword;
    RelativeLayout llBottom;
    LinearLayout llSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_splashes_);
       // hashKey();
      //  accessPermission();
        variableInitialization();
        initUI();
        initListener();
        init();
        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build();
        Fabric.with(fabric);

    }

    private void hashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.mindyourelders.MyHealthCareWishes.HomeActivity",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("VKey:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_CALL_PERMISSION);

        } else {
            // checkForRegistration();
        }
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

      final String[] array = {getResources().getString(R.string.msgstay),getResources().getString(R.string.msg),"Manage and Share Critical Information on\n" +
              "your Smartphone or Tablet","Access to Critical Information and Advance\n" +
              "Care Directives 24/7", "The Just In Case App"};
        textMessage.post(new Runnable() {
            int i = 0;
            @Override
            public void run() {

                textMessage.setText(array[i]);
                Animation RightSwipe = AnimationUtils.loadAnimation(context, R.anim.enter);
                textMessage.startAnimation(RightSwipe);
                i++;
                if (i == 5)
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //  checkForRegistration();

                } else {

                    accessPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
}
