package com.mindyourlovedones.healthcare.Connections;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

public class GrabConnectionActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    Preferences preferences;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentNewContact fragmentNewContact = null;
    FragmentGrabContact fragmentGrabContact = null;
    TextView txtNew, txtTitle;
    ImageView imgContact, imgFb, imgGoogle, imgBack, imgRefresh;
    String source;
    LinearLayout llGrab;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    RelativeLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_connection);
        accessPermission();
        preferences = new Preferences(context);
        initUI();
        initListener();
        fragmentData();
        //  if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
        source = preferences.getString(PrefConstants.SOURCE);
        if (source.equals("PhysicianViewData") || source.equals("HospitalViewData") || source.equals("PharmacyDataView") || source.equals("ProxyUpdateView") || source.equals("EmergencyView") || source.equals("SpecialistViewData") || source.equals("FinanceViewData") || source.equals("InsuranceViewData") || source.equals("AidesViewData")) {
            llGrab.setVisibility(View.GONE);
            txtTitle.setVisibility(View.GONE);

        } else {
            llGrab.setVisibility(View.VISIBLE);
            txtTitle.setVisibility(View.VISIBLE);
        }

        switch (source) {
            case "Connection":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "Pharmacy":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "PharmacyData":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "PharmacyDataView":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "Proxy":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "ProxyUpdate":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "ProxyUpdateView":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "Emergency":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "EmergencyUpdate":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "EmergencyView":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;


            case "Speciality":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "Physician":
                header.setBackgroundColor(getResources().getColor(R.color.colorOne));
                break;

            case "PhysicianData":
                header.setBackgroundResource(R.color.colorOne);
                break;

            case "PhysicianViewData":
                header.setBackgroundResource(R.color.colorOne);
                break;
            case "SpecialistData":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "SpecialistViewData":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "Insurance":
                header.setBackgroundResource(R.color.colorFive);
                break;

            case "InsuranceData":
                header.setBackgroundResource(R.color.colorFive);
                break;

            case "InsuranceViewData":
                header.setBackgroundResource(R.color.colorFive);
                break;

            case "Aides":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "AidesData":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "AidesViewData":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "Finance":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "Hospital":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "HospitalData":
                header.setBackgroundResource(R.color.colorThree);
                break;
            case "HospitalViewData":
                header.setBackgroundResource(R.color.colorThree);
                break;


            case "FinanceData":
                header.setBackgroundResource(R.color.colorThree);
                break;

            case "FinanceViewData":
                header.setBackgroundResource(R.color.colorThree);
                break;
        }

        callFragment("NEWCONTACT", fragmentNewContact);
        // }

    }

    private void accessPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS
            }, PERMISSIONS_REQUEST_READ_CONTACTS);

        } else {

        }
    }




    public void callFragment(String fragName, Fragment fragment) {
        switch (fragName) {
            case "NEWCONTACT":
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                txtNew.setTextColor(getResources().getColor(R.color.colorGray));
                imgContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgContact.setImageResource(R.drawable.ic_person_white);
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgFb.setImageResource(R.drawable.fb);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgGoogle.setImageResource(R.drawable.g);
                imgRefresh.setVisibility(View.GONE);
                break;

            case "CONTACT":
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtNew.setTextColor(getResources().getColor(R.color.colorLightBlue));
                imgContact.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgContact.setImageResource(R.drawable.ic_person_gray);
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgFb.setImageResource(R.drawable.fb);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgGoogle.setImageResource(R.drawable.g);
                imgRefresh.setVisibility(View.VISIBLE);
                break;
        }


        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();
    }

    private void fragmentData() {
        fragmentNewContact = new FragmentNewContact();
        fragmentGrabContact = new FragmentGrabContact();
    }

    private void initListener() {
        txtNew.setOnClickListener(this);
        imgContact.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgGoogle.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        llGrab= (LinearLayout) findViewById(R.id.llGrab);
        txtNew = (TextView) findViewById(R.id.txtNew);
        imgContact = (ImageView) findViewById(R.id.imgContact);
        imgFb = (ImageView) findViewById(R.id.imgFb);
        imgGoogle = (ImageView) findViewById(R.id.imgGoogle);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgRefresh=(ImageView) findViewById(R.id.imgRefresh);
        header= (RelativeLayout) findViewById(R.id.header);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;

            case R.id.txtNew:
                if (fragmentManager.findFragmentByTag("NEWCONTACT") == null) {
                    callFragment("NEWCONTACT", fragmentNewContact);
                }

                break;

            case R.id.imgContact:
                if (fragmentManager.findFragmentByTag("CONTACT") == null) {
                    callFragment("CONTACT", fragmentGrabContact);
                }

                break;

            case R.id.imgFb:
                /*if (fragmentManager.findFragmentByTag("CONTACT") == null) {
                    callFragment("CONTACT", fragmentGrabContact);
                }*/
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtNew.setTextColor(getResources().getColor(R.color.colorLightBlue));
                imgContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgContact.setImageResource(R.drawable.ic_person_white);
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgFb.setImageResource(R.drawable.fb_gray);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgGoogle.setImageResource(R.drawable.g);
                imgRefresh.setVisibility(View.GONE);
                break;
            case R.id.imgGoogle:
               /* if (fragmentManager.findFragmentByTag("CONTACT") == null) {
                    callFragment("CONTACT", fragmentGrabContact);
                }*/
                txtNew.setBackgroundColor(getResources().getColor(R.color.colorGray));
                txtNew.setTextColor(getResources().getColor(R.color.colorLightBlue));
                imgContact.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgContact.setImageResource(R.drawable.ic_person_white);
                imgFb.setBackgroundColor(getResources().getColor(R.color.colorGray));
                imgFb.setImageResource(R.drawable.fb);
                imgGoogle.setBackgroundColor(getResources().getColor(R.color.colorLightBlue));
                imgGoogle.setImageResource(R.drawable.g_gray);
                imgRefresh.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

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