package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentAids;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentFinance;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentHospital;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentInsurance;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentPharmacy;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FragmentSpecialist;

public class InsuranceActivity extends AppCompatActivity implements View.OnClickListener {
    public static FragmentManager fragmentManager;
    public FragmentTransaction fragmentTransaction;
    FragmentInsurance fragmentInsurance = null;
    FragmentSpecialist fragmentSpecialist=null;
    FragmentAids fragmentAids=null;
    FragmentPharmacy fragmentPharmacy=null;
    FragmentFinance fragmentFinance=null;
    FragmentHospital fragmentHospital=null;
TextView txtTitle;
    Spinner spinner;

    ImageView imgBack;


  //  String[] specialist = { "Insurance", "DOCTORS", "HOME HEALTH SERVICES", "FINANCE, INSURANCE & LEGAL" };


    // FragmentConnection fragmentConnection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);
        initUI();

        initListener();
        fragmentData();
        initComponent();
      //  callFragment("INSURANCE", fragmentInsurance);
      //  if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
        //    callFragment("INSURANCE", fragmentInsurance);
       // }
    }

    private void initComponent() {
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            String fragment=i.getExtras().getString("FRAGMENT");
            switch (fragment)
            {
                case "Insurance":
                    txtTitle.setText("INSURANCE");
                    callFragment("INSURANCE", fragmentInsurance);
                    break;
                case "Doctors":
                    txtTitle.setText("DOCTORS");
                    callFragment("SPECIALIST", fragmentSpecialist);
                    break;

                case "Hospitals":
                    txtTitle.setText("HOSPITALS AND OTHER HEALTH PROFESSIONALS");
                    callFragment("HOSPITAL", fragmentHospital);
                    break;
                case "Pharmacies":
                    txtTitle.setText("PHARMACIES AND \nHOME MEDICAL EQUIPMENT");
                    callFragment("PHARMACY", fragmentPharmacy);
                    break;
                case "Home Health Services":
                    txtTitle.setText("HOME HEALTH SERVICES");
                    callFragment("AIDS", fragmentAids);
                    break;
                case "Finance,Insurance and Legal":
                    txtTitle.setText("FINANCE, INSURANCE, LEGAL");
                    callFragment("FINANCE", fragmentFinance);
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
        fragmentInsurance = new FragmentInsurance();
        fragmentSpecialist=new FragmentSpecialist();
        fragmentAids=new FragmentAids();
        fragmentFinance=new FragmentFinance();
        fragmentPharmacy=new FragmentPharmacy();
        fragmentHospital=new FragmentHospital();
    }

    private void initListener() {

        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
       // spinner=(Spinner) findViewById(R.id.spinner);


       /* ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,specialist);
        aa.setDropDownViewResource(android.R.layout.simple_list_item_1);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),specialist[position] ,Toast.LENGTH_LONG).show();
                switch (specialist[position] )
                {
                    case "Insurance":
                       // if (fragmentManager.findFragmentByTag("INSURANCE") == null) {
                            callFragment("INSURANCE", fragmentInsurance);
                       // }
                        break;
                    case "Doctors":
                      //  if (fragmentManager.findFragmentByTag("SPECIALIST") == null) {
                            callFragment("SPECIALIST", fragmentSpecialist);
                     //   }
                        break;
                    case "Health Aides":
                    //    if (fragmentManager.findFragmentByTag("AIDS") == null) {
                            callFragment("AIDS", fragmentAids);
                      //  }
                        break;
                    case "Finance and Legal":
                        callFragment("FINANCE", fragmentFinance);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
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
