package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.crash.FirebaseCrash;
import com.mindyourelders.MyHealthCareWishes.Connections.FragmentConnectionNew;
import com.mindyourelders.MyHealthCareWishes.DashBoard.DropboxLoginActivity;
import com.mindyourelders.MyHealthCareWishes.DashBoard.FragmentDashboard;
import com.mindyourelders.MyHealthCareWishes.DashBoard.FragmentNotification;
import com.mindyourelders.MyHealthCareWishes.IndexMenu.FragmentOverview;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentDashboard fragmentDashboard = null;
    FragmentResources fragmentResources=null;
    FragmentMarketPlace fragmentMarketPlace=null;
    FragmentVideos fragmentVideos=null;
    FragmentBackup fragmentBackup=null;
    FragmentConnectionNew fragmentConnection = null;
    FragmentNotification fragmentNotification=null;
    FragmentOverview fragmentOverview = null;
    ImageView imgDrawer, imgNoti, imgLogout, imgLocationFeed,imgProfile,imgDrawerProfile,imgPdf;
    TextView txtTitle,txtName,txtDrawerName;
    TextView txtBank,txtForm,txtSenior,txtAdvance;
    DrawerLayout drawerLayout;
    RelativeLayout leftDrawer, container, footer, header;
   RelativeLayout rlLogOutt;
    Preferences preferences;
    RelativeLayout rlHome,rlSupport,rlContact,rlResources,rlMarketPlace,rlVideos,rlBackup,rlResourcesDetail,rlMarketDetail;
    boolean flagResource=false,flagMarket=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));

        //I'm also creating a log message, which we'll look at in more detail later//
        FirebaseCrash.log("MainActivity started");

        //Crashlytics.getInstance().crash(); // Force a crash
        initComponent();
        initUI();
        initListener();
        fragmentData();
       /* if (fragmentManager.findFragmentByTag("DASHBOARD") == null) {
           // imgLocationFeed.setVisibility(View.VISIBLE);
            imgNoti.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.GONE);
            imgLogout.setVisibility(View.GONE);
            header.setBackgroundColor(Color.TRANSPARENT);
            callFragment("DASHBOARD", fragmentDashboard);
        }*/
        if (fragmentManager.findFragmentByTag("CONNECTION") == null) {
            callFragment("CONNECTION", fragmentConnection);
        }

    }

    private void initComponent() {
       preferences=new Preferences(context);
    }

    private void initListener() {
        imgDrawer.setOnClickListener(this);
        imgPdf.setOnClickListener(this);
     //   imgNoti.setOnClickListener(this);
        rlLogOutt.setOnClickListener(this);
        imgLocationFeed.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlSupport.setOnClickListener(this);
        rlResources.setOnClickListener(this);
        rlMarketPlace.setOnClickListener(this);
        rlVideos.setOnClickListener(this);
        rlBackup.setOnClickListener(this);
        rlContact.setOnClickListener(this);

        txtBank.setOnClickListener(this);
        txtForm.setOnClickListener(this);
        txtSenior.setOnClickListener(this);
        txtAdvance.setOnClickListener(this);
    }

    private void initUI() {
        imgDrawer = (ImageView) findViewById(R.id.imgDrawer);
        imgNoti = (ImageView) findViewById(R.id.imgNoti);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        imgPdf = (ImageView) findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgLocationFeed = (ImageView) findViewById(R.id.imgLocationFeed);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtName = (TextView) findViewById(R.id.txtName);

        txtBank = (TextView) findViewById(R.id.txtBank);
        txtForm = (TextView) findViewById(R.id.txtForm);
        txtSenior = (TextView) findViewById(R.id.txtSenior);
        txtAdvance = (TextView) findViewById(R.id.txtAdvance);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        leftDrawer = (RelativeLayout) findViewById(R.id.leftDrawer);
        header = (RelativeLayout) findViewById(R.id.header);
        rlLogOutt= (RelativeLayout) findViewById(R.id.rlLogOutt);
        txtDrawerName = (TextView) leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = (ImageView) leftDrawer.findViewById(R.id.imgDrawerProfile);
        String image=preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        byte[] photo = Base64.decode(image, Base64.DEFAULT);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        imgDrawerProfile.setImageBitmap(bmp);

        rlHome= (RelativeLayout) leftDrawer.findViewById(R.id.rlHome);
        rlSupport= (RelativeLayout) leftDrawer.findViewById(R.id.rlSupport);
        rlResources= (RelativeLayout) leftDrawer.findViewById(R.id.rlResources);
        rlMarketPlace= (RelativeLayout) leftDrawer.findViewById(R.id.rlMarketPlace);
        rlVideos= (RelativeLayout) leftDrawer.findViewById(R.id.rlVideos);
        rlBackup= (RelativeLayout) leftDrawer.findViewById(R.id.rlBackup);
        rlMarketDetail= (RelativeLayout) leftDrawer.findViewById(R.id.rlMarketDetail);
        rlResourcesDetail= (RelativeLayout) leftDrawer.findViewById(R.id.rlResourcesDetail);
        rlContact= (RelativeLayout) leftDrawer.findViewById(R.id.rlContact);
    }

    private void fragmentData() {
        fragmentManager = getFragmentManager();
        fragmentDashboard = new FragmentDashboard();
        fragmentOverview = new FragmentOverview();
        fragmentConnection=new FragmentConnectionNew();
        fragmentNotification=new FragmentNotification();
        fragmentResources=new FragmentResources();
        fragmentMarketPlace=new FragmentMarketPlace();
        fragmentVideos=new FragmentVideos();
        fragmentBackup=new FragmentBackup();
    }

    public void callFragment(String fragName, Fragment fragment) {
      /*  switch (fragName) {
          *//*  case "OVERVIEW":
               // imgLocationFeed.setVisibility(View.GONE);
                imgNoti.setVisibility(View.GONE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Overview");
                imgLogout.setVisibility(View.VISIBLE);
                header.setBackgroundColor(getResources().getColor(R.color.colorBlue));
                break;*//*

            case "DASHBOARD":
               // imgLocationFeed.setVisibility(View.VISIBLE);
                imgNoti.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                imgLogout.setVisibility(View.GONE);
                header.setBackgroundColor(Color.TRANSPARENT);
                break;

            case "CONNECTION":
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("My Connections");
                // imgLocationFeed.setVisibility(View.VISIBLE);
               *//* imgNoti.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                imgLogout.setVisibility(View.GONE);
                header.setBackgroundColor(Color.TRANSPARENT);*//*
                break;

            case "NOTIFICATION":
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Notifications");
                // imgLocationFeed.setVisibility(View.VISIBLE);
               *//* imgNoti.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.GONE);
                imgLogout.setVisibility(View.GONE);
                header.setBackgroundColor(Color.TRANSPARENT);*//*
                break;
        }*/

        fragmentTransaction = fragmentManager.beginTransaction();
     if (fragName.equals("CONNECTION"))
         fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
      else
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName).addToBackStack(fragName);

        drawerLayout.closeDrawer(leftDrawer);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgDrawer:
               drawerLayout.openDrawer(leftDrawer);
               // copydb(context);
                break;

            case R.id.rlHome:
             //   if (fragmentManager.findFragmentByTag("CONNECTION") == null) {
                    callFragment("CONNECTION", fragmentConnection);
               // }
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlSupport:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.myhealthcarewishes.com/support.html"));
                startActivity(intent);
                break;

            case R.id.rlContact:
               /* Intent intents = new Intent();
                intents.setAction(Intent.ACTION_VIEW);
                intents.addCategory(Intent.CATEGORY_BROWSABLE);
                intents.setData(Uri.parse("http://www.myhealthcarewishes.com/support.html"));
                startActivity(intents);*/

                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { "helpdesk@mindyour-lovedones.com" });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        ""); // subject
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""); // Body
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    // uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", f);
                } else {
                    // uri = Uri.fromFile(f);
                }
                emailIntent.setType("application/email");
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                break;

            case R.id.rlResources:
                if (flagResource==false) {
                    rlResourcesDetail.setVisibility(View.VISIBLE);
                    flagResource=true;
                }
                else  if (flagResource==true){
                    rlResourcesDetail.setVisibility(View.GONE);
                    flagResource=false;
                }
              /* // if (fragmentManager.findFragmentByTag("RESOURCES") == null) {
                    callFragment("RESOURCES", fragmentResources);
              //  }
                drawerLayout.closeDrawer(leftDrawer);*/
                break;

            case R.id.rlMarketPlace:
                if (flagMarket==false) {
                    rlMarketDetail.setVisibility(View.VISIBLE);
                    flagMarket=true;
                }
                else  if (flagMarket==true){
                    rlMarketDetail.setVisibility(View.GONE);
                    flagMarket=false;
                }
              ///  rlMarketDetail.setVisibility(View.VISIBLE);
             /*//   if (fragmentManager.findFragmentByTag("MARKET") == null) {
                    callFragment("MARKET", fragmentMarketPlace);
              //  }
                drawerLayout.closeDrawer(leftDrawer);*/
                break;

            case R.id.txtForm:
               // callFragment("FORM", fragmentResources);
                CopyReadAssetss("medical_history_form.pdf");
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtAdvance:
                //   if (fragmentManager.findFragmentByTag("MARKET") == null) {
                callFragment("FORM", fragmentResources);
                //  }
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtBank:
                //   if (fragmentManager.findFragmentByTag("MARKET") == null) {
                callFragment("MARKET", fragmentMarketPlace);
                //  }
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtSenior:
                //   if (fragmentManager.findFragmentByTag("MARKET") == null) {
                callFragment("MARKET", fragmentMarketPlace);
                //  }
                drawerLayout.closeDrawer(leftDrawer);
                break;



            case R.id.rlVideos:
               // if (fragmentManager.findFragmentByTag("VIDEOS") == null) {
                    callFragment("VIDEOS", fragmentVideos);
              //  }
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlBackup:
                Intent i=new Intent(BaseActivity.this, DropboxLoginActivity.class);
                i.putExtra("FROM","Backup");
                startActivity(i);
                // if (fragmentManager.findFragmentByTag("VIDEOS") == null) {
               // callFragment("VIDEOS", fragmentBackup);
                //  }
                drawerLayout.closeDrawer(leftDrawer);
                break;



         /*   case R.id.imgPdf:
              *//*  StringBuffer result = new StringBuffer();
                result.append(new MessageString().getProfile());*//*
              *//*  result.append(new MessageString().getMedicalMsg());
                result.append(new MessageString().getInsuranceMsg());*//*
             *//*   new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                    + "/mye/" + preferences.getString(PrefConstants.CONNECTED_USERID) + "_" +  preferences.getString(PrefConstants.USER_ID)
                    + "/Mind Your Elders Summary Report.pdf",
                    BaseActivity.this, result);*//*

                new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                        + "/mye/" +  preferences.getString(PrefConstants.CONNECTED_USERID) + "_" +  preferences.getString(PrefConstants.USER_ID)
                        + "/Profile.pdf", BaseActivity.this,
                        new MessageString().getProfileData());
                break;*/

            case R.id.imgNoti:
                if (fragmentManager.findFragmentByTag("NOTIFICATION") == null) {
                    callFragment("NOTIFICATION", fragmentNotification);
                }
                break;

            case R.id.rlLogOutt:
                preferences.clearPreferences();
                finish();
                startActivity(new Intent(BaseActivity.this,LoginActivity.class));
                break;
            /*case R.id.txtNew:
                Intent signupIntent=new Intent(context,SignUpActivity.class);
                startActivity(signupIntent);
                finish();
                break;
            case R.id.txtForgotPassword:

                break;

            case R.id.imgFbSignup:

                break;

            case R.id.imgGoogleSignup:

                break;*/
        }
    }



    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try
        {
            in = assetManager.open(documentPath);
            outFile=new File(getExternalFilesDir(null),documentPath);
            out=new FileOutputStream(outFile);

            copyFiles(in,out);
            in.close();
            in=null;
            out.flush();
            out.close();
            out=null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }
        Uri uri=null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }


    }
}
