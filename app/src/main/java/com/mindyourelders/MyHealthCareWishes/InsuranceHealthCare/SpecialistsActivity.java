package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;


public class SpecialistsActivity extends AppCompatActivity {
Context context=this;
    String[] specialist;
    int[] profile;
    ListView listSpeciallist;
    ImageView imgBack,imgRight;
    TextView txtTitle;
    String from;
    boolean isEmergency,isInsurance;
    RelativeLayout header;
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
        header= (RelativeLayout) findViewById(R.id.header);
        imgRight= (ImageView) findViewById(R.id.imgRight);
        if (i.getExtras()!=null)
        {
            from=i.getExtras().getString("FROM");
            if (from.equals("Speciality"))
            {
                txtTitle.setText("SPECIALITY");
                imgRight.setVisibility(View.VISIBLE);
                header.setBackgroundResource(R.color.colorThree);
                profile=new int[]{R.drawable.physician,R.drawable.hosp_icon,R.drawable.pharmacies,R.drawable.aides,R.drawable.finances};
                specialist= new String[]{"DOCTORS","HOSPITALS AND \nOTHER HEALTH PROFESSIONALS", "PHARMACIES AND \nHOME MEDICAL EQUIPMENT", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
                isEmergency=false;
                isInsurance=false;
            }
            else if (from.equals("Emergency"))
            {
                imgRight.setVisibility(View.GONE);
                txtTitle.setText("PERSONAL & MEDICAL PROFILE & EMERGENCY CONTACTS");
                header.setBackgroundResource(R.color.colorOne);
                isEmergency=true;
                isInsurance=false;
                profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician,R.drawable.proxys};
                specialist= new String[] { "PERSONAL PROFILE", "MEDICAL PROFILE", "EMERGENCY CONTACTS", "PRIMARY PHYSICIAN", "HEALTH CARE PROXY AGENT" };

            }
            else if (from.equals("Insurance"))
            {
                imgRight.setVisibility(View.VISIBLE);
                txtTitle.setText("INSURANCE");
                header.setBackgroundResource(R.color.colorFive);
                profile=new int[]{R.drawable.finances,R.drawable.insurancess,R.drawable.finances};
                specialist= new String[]{"INSURANCE INFORMATION", "INSURANCE CARDS","INSURANCE FORMS"};
                isEmergency=false;
                isInsurance=true;
            }
            else if (from.equals("Event"))
            {
                imgRight.setVisibility(View.VISIBLE);
                txtTitle.setText("NOTES & APPOINTMENT CHECKLIST");
                header.setBackgroundResource(R.color.colorFour);
                profile=new int[]{R.drawable.finances,R.drawable.insurancess};
                specialist= new String[]{"EVENT NOTES", "APPOINTMENT CHECKLIST"};
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
        //header= (RelativeLayout) findViewById(R.id.header);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgRight=(ImageView) findViewById(R.id.imgRight);
        listSpeciallist= (ListView) findViewById(R.id.listSpecialist);
        SpecialistContactAdapter adapter=new SpecialistContactAdapter(context,specialist,profile,isEmergency,isInsurance,from);
        listSpeciallist.setAdapter(adapter);
    }
}
