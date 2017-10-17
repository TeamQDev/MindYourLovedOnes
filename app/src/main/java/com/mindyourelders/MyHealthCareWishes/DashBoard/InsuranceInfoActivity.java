package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentInsurance;


public class InsuranceInfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentInsurance fragmentInsurance = null;
    FragementInsuarnceCard fragementInsuarnceCard=null;
ImageView imgBack;
    TextView txtTitle;

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
                case "Insurance Info":
                    txtTitle.setText("INSURANCE INFORMATION");
                    callFragment("INFO", fragmentInsurance);
                    break;
                case "INSURANCE CARD":
                    txtTitle.setText("INSURANCE CARD");
                    callFragment("CARD", fragementInsuarnceCard);
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
        fragementInsuarnceCard = new FragementInsuarnceCard();
        fragmentInsurance=new FragmentInsurance();
    }

    private void initListener() {
      imgBack.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtTitle.setText("INSURANCE");
        imgBack= (ImageView) findViewById(R.id.imgBack);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.imgBack:
                finish();
                break;
        }
    }
}
