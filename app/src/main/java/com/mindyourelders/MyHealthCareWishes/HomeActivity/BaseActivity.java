package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.Connections.FragmentConnectionNew;
import com.mindyourelders.MyHealthCareWishes.DashBoard.FragmentDashboard;
import com.mindyourelders.MyHealthCareWishes.DashBoard.FragmentNotification;
import com.mindyourelders.MyHealthCareWishes.IndexMenu.FragmentOverview;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentDashboard fragmentDashboard = null;
    FragmentConnectionNew fragmentConnection = null;
    FragmentNotification fragmentNotification=null;
    FragmentOverview fragmentOverview = null;
    ImageView imgDrawer, imgNoti, imgLogout, imgLocationFeed,imgProfile;
    TextView txtTitle,txtName;
    DrawerLayout drawerLayout;
    RelativeLayout leftDrawer, container, footer, header;
   RelativeLayout rlLogOutt;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
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
     //   imgNoti.setOnClickListener(this);
 rlLogOutt.setOnClickListener(this);
        imgLocationFeed.setOnClickListener(this);
    }

    private void initUI() {
        imgDrawer = (ImageView) findViewById(R.id.imgDrawer);
        imgNoti = (ImageView) findViewById(R.id.imgNoti);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        imgLocationFeed = (ImageView) findViewById(R.id.imgLocationFeed);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtName = (TextView) findViewById(R.id.txtName);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        leftDrawer = (RelativeLayout) findViewById(R.id.leftDrawer);
        header = (RelativeLayout) findViewById(R.id.header);

        rlLogOutt= (RelativeLayout) findViewById(R.id.rlLogOutt);
    }

    private void fragmentData() {
        fragmentManager = getFragmentManager();
        fragmentDashboard = new FragmentDashboard();
        fragmentOverview = new FragmentOverview();
        fragmentConnection=new FragmentConnectionNew();
        fragmentNotification=new FragmentNotification();
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
                break;

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
}
