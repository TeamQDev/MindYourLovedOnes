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
                txtTitle.setText("Specialist Contacts");
                profile=new int[]{R.drawable.physician,R.drawable.pharmacies,R.drawable.aides,R.drawable.finances};
                specialist= new String[]{"Doctors", "Pharmacies", "Home Health Services", "Finance,Insurance and Legal"};
                isEmergency=false;
                isInsurance=false;
            }
            else if (from.equals("Emergency"))
            {

                txtTitle.setText("Contacts and Medical Information");
                isEmergency=true;
                isInsurance=false;
                profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician,R.drawable.proxys};
                specialist= new String[] { "Individual Info", "Medical Information", "Emergency Contacts", "Primary Physician", "Health Care Proxy Agents" };

            }
            else if (from.equals("Insurance"))
            {

                txtTitle.setText("Insurance");
                profile=new int[]{R.drawable.finances,R.drawable.insurancess};
                specialist= new String[]{"Insurance Info", "Insurance Card"};
                isEmergency=false;
                isInsurance=true;
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
