package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;


public class EmergencyInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentIndividualContact fragmentIndividualContact = null;

    FragmentProxy fragmentProxy=null;
    FragmentEmergency fragmentEmergency=null;
    FragmentMedicalInfo fragmentMedicalInfo=null;
    FragmentPhysician fragmentPhysician=null;
    FragmentLiving fragmentLiving=null;
ImageView imgBack,imgRight;
    TextView txtTitle;
RelativeLayout header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_info);
        initUI();
        initListener();
        fragmentData();
        initComponent();
    }

    private void initComponent() {
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            String fragment=i.getExtras().getString("FRAGMENT");
            switch (fragment)
            {
                case "Individual":
                    callFragment("INDIVIDUAL", fragmentIndividualContact);
                    imgRight.setVisibility(View.GONE);
                    break;
                case "Information":
                    callFragment("INFORMATION", fragmentMedicalInfo);
                    imgRight.setVisibility(View.GONE);
                    break;
                case "Emergency":
                    callFragment("EMERGENCY", fragmentEmergency);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
                case "Physician":
                    callFragment("PHYSICIAN", fragmentPhysician);
                    imgRight.setVisibility(View.VISIBLE);

                    break;
                case "Functional":
                    callFragment("FUNCTIONAL", fragmentLiving);
                    imgRight.setVisibility(View.GONE);

                    break;
                case "Proxy":
                    callFragment("PROXY", fragmentProxy);
                    imgRight.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
    private void callFragment(String fragName, Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, fragName);
        fragmentTransaction.commit();
    }

    private void fragmentData() {
        fragmentEmergency = new FragmentEmergency();
        fragmentIndividualContact=new FragmentIndividualContact();
        fragmentMedicalInfo=new FragmentMedicalInfo();
        fragmentPhysician=new FragmentPhysician();
        fragmentProxy=new FragmentProxy();
fragmentLiving=new FragmentLiving();
    }

    private void initListener() {
      imgBack.setOnClickListener(this);
    }

    private void initUI() {
        header= (RelativeLayout) findViewById(R.id.header);
        header.setBackgroundResource(R.color.colorOne);
        imgRight= (ImageView) findViewById(R.id.imgRight);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("PERSONAL AND MEDICAL PROFILE AND EMERGENCY CONTACTS");
        imgBack= (ImageView) findViewById(R.id.imgBack);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.imgBack:
               try
               {
                   InputMethodManager inm= (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                   inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
               }
               catch (Exception e)
               {
                   //Todo: handle exception
               }
                finish();
                break;
        }
    }
}
