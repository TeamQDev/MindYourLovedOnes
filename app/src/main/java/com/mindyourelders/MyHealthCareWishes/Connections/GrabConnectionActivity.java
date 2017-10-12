package com.mindyourelders.MyHealthCareWishes.Connections;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

public class GrabConnectionActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    Preferences preferences;
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentNewContact fragmentNewContact = null;
    FragmentGrabContact fragmentGrabContact = null;
    TextView txtNew,txtTitle;
    ImageView imgContact, imgFb, imgGoogle, imgBack,imgRefresh;
    String source;
    LinearLayout llGrab;

    // FragmentConnection fragmentConnection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_connection);
        preferences = new Preferences(context);
        initUI();
        initListener();
        fragmentData();
        //  if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
        source = preferences.getString(PrefConstants.SOURCE);
            if (source.equals("PharmacyDataView")||source.equals("ProxyUpdateView")||source.equals("EmergencyView")||source.equals("SpecialistViewData")||source.equals("FinanceViewData")||source.equals("InsuranceViewData")||source.equals("AidesViewData"))
            {
                llGrab.setVisibility(View.GONE);
                txtTitle.setVisibility(View.GONE);

            }
            else{
                llGrab.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
            }

        callFragment("NEWCONTACT", fragmentNewContact);
        // }

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


}