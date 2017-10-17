package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;


public class SpecialistsActivity extends AppCompatActivity {
Context context=this;
    String[] specialist;
    int[] profile;
    ListView listSpeciallist;
    ImageView imgBack;
    TextView txtTitle;
    String from;
    boolean isEmergency,isInsurance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialists);
        initComponent();
        initUi();

        initListener();


    }

    private void initComponent() {
        Intent i=getIntent();
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        if (i.getExtras()!=null)
        {
            from=i.getExtras().getString("FROM");
            if (from.equals("Speciality"))
            {
                txtTitle.setText("SPECIALISTS CONTACTS");
                profile=new int[]{R.drawable.physician,R.drawable.pharmacies,R.drawable.aides,R.drawable.finances};
                specialist= new String[]{"DOCTORS", "PHARMACIES", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
                isEmergency=false;
                isInsurance=false;
            }
            else if (from.equals("Emergency"))
            {

                txtTitle.setText("PERSONAL & MEDICAL INFORMATION");
                isEmergency=true;
                isInsurance=false;
                profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician,R.drawable.proxys};
                specialist= new String[] { "PERSONAL INFORMATION", "MEDICAL INFORMATION", "EMERGENCY CONTACTS", "PRIMARY PHYSICIAN", "HEALTH CARE PROXY AGENT" };

            }
            else if (from.equals("Insurance"))
            {

                txtTitle.setText("INSURANCE");
                profile=new int[]{R.drawable.finances,R.drawable.insurancess};
                specialist= new String[]{"INSURANCE INFORMATION", "INSURANCE CARDS"};
                isEmergency=false;
                isInsurance=true;
            }
            else if (from.equals("Event"))
            {

                txtTitle.setText("EVENT NOTES & APPOINTMENT TRACKER");
                profile=new int[]{R.drawable.finances,R.drawable.insurancess};
                specialist= new String[]{"EVENT NOTES", "APPOINTMENT TRACKER"};
                isEmergency=false;
                isInsurance=false;
            }
        }
    }

    private void initListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initUi() {

        imgBack = (ImageView) findViewById(R.id.imgBack);
        listSpeciallist= (ListView) findViewById(R.id.listSpecialist);
        SpecialistContactAdapter adapter=new SpecialistContactAdapter(context,specialist,profile,isEmergency,isInsurance);
        listSpeciallist.setAdapter(adapter);
    }
}
