package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL_PERMISSION =600 ;
    Context context=this;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentDashboard fragmentDashboard = null;
    FragmentResources fragmentResources=null;
    FragmentForm fragmentForm=null;
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
    TextView txtPrivacyPolicy,txtEULA;
    RelativeLayout rlHome,rlSupport,rlContact,rlResources,rlPrivacy,rlMarketPlace,rlVideos,rlBackup,rlResourcesDetail,rlMarketDetail,rlPrivacyDetail;
    boolean flagResource=false,flagMarket=false,flagPrivacy=false;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        //I'm also creating a log message, which we'll look at in more detail later//
        FirebaseCrash.log("MainActivity started");
        accessPermission();
        //Crashlytics.getInstance().crash(); // Force a crash
        initImageLoader();
        initComponent();
        initUI();
        initListener();
        fragmentData();
        if (fragmentManager.findFragmentByTag("CONNECTION") == null) {
            callFirstFragment("CONNECTION", fragmentConnection);
        }
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
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
        rlPrivacy.setOnClickListener(this);
        rlMarketPlace.setOnClickListener(this);
        rlVideos.setOnClickListener(this);
        rlBackup.setOnClickListener(this);
        rlContact.setOnClickListener(this);

        txtBank.setOnClickListener(this);
        txtForm.setOnClickListener(this);
        txtSenior.setOnClickListener(this);
        txtAdvance.setOnClickListener(this);
        rlPrivacyDetail.setOnClickListener(this);
        txtPrivacyPolicy.setOnClickListener(this);
        txtEULA.setOnClickListener(this);
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

       /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        imgDrawerProfile.setImageBitmap(bmp);*/

        rlHome= (RelativeLayout) leftDrawer.findViewById(R.id.rlHome);
        rlSupport= (RelativeLayout) leftDrawer.findViewById(R.id.rlSupport);
        rlResources= (RelativeLayout) leftDrawer.findViewById(R.id.rlResources);
        rlPrivacy= (RelativeLayout) leftDrawer.findViewById(R.id.rlPrivacy);
        rlMarketPlace= (RelativeLayout) leftDrawer.findViewById(R.id.rlMarketPlace);
        rlVideos= (RelativeLayout) leftDrawer.findViewById(R.id.rlVideos);
        rlBackup= (RelativeLayout) leftDrawer.findViewById(R.id.rlBackup);
        rlMarketDetail= (RelativeLayout) leftDrawer.findViewById(R.id.rlMarketDetail);
        rlResourcesDetail= (RelativeLayout) leftDrawer.findViewById(R.id.rlResourcesDetail);
        rlPrivacyDetail= (RelativeLayout) leftDrawer.findViewById(R.id.rlPrivacyDetail);
        rlContact= (RelativeLayout) leftDrawer.findViewById(R.id.rlContact);
        txtPrivacyPolicy = (TextView) leftDrawer.findViewById(R.id.txtPrivacyPolicy);
        txtEULA = (TextView) leftDrawer.findViewById(R.id.txtEULA);
    }

    private void fragmentData() {
        fragmentManager = getFragmentManager();
        fragmentDashboard = new FragmentDashboard();
        fragmentOverview = new FragmentOverview();
        fragmentConnection=new FragmentConnectionNew();
        fragmentNotification=new FragmentNotification();
        fragmentResources=new FragmentResources();
        fragmentForm=new FragmentForm();
        fragmentMarketPlace=new FragmentMarketPlace();
        fragmentVideos=new FragmentVideos();
        fragmentBackup=new FragmentBackup();
    }

    public void callFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        if (fragName.equals("DASHBOARD")||fragName.equals("ADVANCE"))
            fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName).addToBackStack("CONNECTION");
        else
       fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();
    }

    public void callFirstFragment(String fragName, Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgDrawer:
              // Intent intn=new Intent(context, ImageActivity.class);
               // startActivity(intn);
              drawerLayout.openDrawer(leftDrawer);
               // copydb(context);
                break;

            case R.id.rlHome:
               //if (fragmentManager.findFragmentByTag("CONNECTION") == null) {
                if (fragmentConnection.isAdded())
                {
                    drawerLayout.closeDrawer(leftDrawer);
                    return;
                }else {
                   // callFragment("CONNECTION", fragmentConnection);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, fragmentConnection, "CONNECTION").addToBackStack("CONNECTION");
                    fragmentTransaction.commit();
                }
             //  }
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.rlSupport:

                CopyReadAssetss("support_faqs.pdf");
                drawerLayout.closeDrawer(leftDrawer);
               /* Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.myhealthcarewishes.com/support.html"));
                startActivity(intent);*/
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

            case R.id.rlPrivacy:
                if (flagPrivacy==false) {
                    rlPrivacyDetail.setVisibility(View.VISIBLE);
                    flagPrivacy=true;
                }
                else  if (flagPrivacy==true){
                    rlPrivacyDetail.setVisibility(View.GONE);
                    flagPrivacy=false;
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
               //CopyReadAssetss("medical_history_form.pdf");
                //drawerLayout.closeDrawer(leftDrawer);

                   if (fragmentManager.findFragmentByTag("FORM") == null) {
                callFragment("FORM", fragmentForm);
                  }
                drawerLayout.closeDrawer(leftDrawer);

                break;

            case R.id.txtPrivacyPolicy:
                // callFragment("FORM", fragmentResources);
                CopyReadAssetss("privacy_policy.pdf");
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtEULA:
                // callFragment("FORM", fragmentResources);
                CopyReadAssetss("eula_draft.pdf");
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtAdvance:
                  if (fragmentManager.findFragmentByTag("ADVANCE") == null) {
                callFragment("ADVANCE", fragmentResources);
                  }
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtBank:
                //   if (fragmentManager.findFragmentByTag("MARKET") == null) {
              //  callFragment("MARKET", fragmentMarketPlace);
                //  }

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://wordpress.arihantwebconsultancy.com/"));
                startActivity(intent);
                drawerLayout.closeDrawer(leftDrawer);
                break;

            case R.id.txtSenior:
                //   if (fragmentManager.findFragmentByTag("MARKET") == null) {
               // callFragment("MARKET", fragmentMarketPlace);
                //  }
                Intent intents = new Intent();
                intents.setAction(Intent.ACTION_VIEW);
                intents.addCategory(Intent.CATEGORY_BROWSABLE);
                intents.setData(Uri.parse("http://wordpress.arihantwebconsultancy.com/"));
                startActivity(intents);
                drawerLayout.closeDrawer(leftDrawer);
                break;



            case R.id.rlVideos:
                if (fragmentManager.findFragmentByTag("VIDEOS") == null) {
                    callFragment("VIDEOS", fragmentVideos);
                }
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

            case R.id.rlLogOutt:
                preferences.clearPreferences();
                finish();
                startActivity(new Intent(BaseActivity.this,LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String image=preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        //byte[] photo = Base64.decode(image, Base64.DEFAULT);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            File imgFile = new File(image);
            if (imgFile.exists()) {
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgDrawerProfile,displayImageOptions);
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgDrawerProfile.setImageBitmap(myBitmap);*/
            }
        }else{
            imgDrawerProfile.setImageResource(R.drawable.ic_profile_defaults);
        }
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

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code

        } else {
            if (getFragmentManager().findFragmentByTag("CONNECTION").isResumed()) {
                finish();
            }
            else{
                getFragmentManager().popBackStack();
            }
        }

    }
}
