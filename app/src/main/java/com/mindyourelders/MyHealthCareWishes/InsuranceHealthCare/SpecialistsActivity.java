package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AllergyQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.HistoryQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalImplantsQuery;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.SpecialistQuery;
import com.mindyourelders.MyHealthCareWishes.model.Allergy;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.model.Proxy;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.Individual;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.utility.Header;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.File;
import java.util.ArrayList;


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
   // final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    final CharSequence[] dialog_items = {"View" };
    Preferences preferences;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialists);
        initComponent();
        initUi();
        initListener();
    }

    private void initComponent() {
       databases();
        Intent i=getIntent();
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        header= (RelativeLayout) findViewById(R.id.header);
     //   imgRight= (ImageView) findViewById(R.id.imgRight);
        if (i.getExtras()!=null)
        {
            from=i.getExtras().getString("FROM");
            if (from.equals("Speciality"))
            {
                txtTitle.setText("SPECIALITY");
              //  imgRight.setVisibility(View.VISIBLE);
                header.setBackgroundResource(R.color.colorThree);
                profile=new int[]{R.drawable.physician,R.drawable.hosp_icon,R.drawable.pharmacies,R.drawable.aides,R.drawable.finances};
                specialist= new String[]{"DOCTORS","HOSPITALS AND \nOTHER HEALTH PROFESSIONALS", "PHARMACIES AND \nHOME MEDICAL EQUIPMENT", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
                isEmergency=false;
                isInsurance=false;
            }
            else if (from.equals("Emergency"))
            {
              // imgRight.setVisibility(View.GONE);
                txtTitle.setText("PERSONAL & MEDICAL PROFILE & EMERGENCY CONTACTS");
                header.setBackgroundResource(R.color.colorOne);
                isEmergency=true;
                isInsurance=false;
                profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician,R.drawable.proxys};
                specialist= new String[] { "PERSONAL PROFILE", "MEDICAL PROFILE", "EMERGENCY CONTACTS", "PRIMARY PHYSICIAN", "HEALTH CARE PROXY AGENT" };

            }
            else if (from.equals("Insurance"))
            {
              //  imgRight.setVisibility(View.VISIBLE);
                txtTitle.setText("INSURANCE");
                header.setBackgroundResource(R.color.colorFive);
                profile=new int[]{R.drawable.finances,R.drawable.insurancess,R.drawable.finances};
                specialist= new String[]{"INSURANCE INFORMATION", "INSURANCE CARDS","INSURANCE FORMS"};
                isEmergency=false;
                isInsurance=true;
            }
            else if (from.equals("Event"))
            {
              //  imgRight.setVisibility(View.VISIBLE);
                txtTitle.setText("NOTES & APPOINTMENT CHECKLIST");
                header.setBackgroundResource(R.color.colorFour);
                profile=new int[]{R.drawable.finances,R.drawable.insurancess};
                specialist= new String[]{"EVENT NOTES", "APPOINTMENT CHECKLIST"};
                isEmergency=false;
                isInsurance=false;
            }
        }
    }

    private void databases() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);

        PersonalInfoQuery p=new PersonalInfoQuery(context,dbHelper);
        MyConnectionsQuery m=new MyConnectionsQuery(context,dbHelper);
        AllergyQuery a=new AllergyQuery(context,dbHelper);
        MedicalImplantsQuery ml=new MedicalImplantsQuery(context,dbHelper);
        HistoryQuery o=new HistoryQuery(context,dbHelper);
        HospitalQuery h=new HospitalQuery(context,dbHelper);
        SpecialistQuery s=new SpecialistQuery(context,dbHelper);
    }

    private void initListener() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (from.equals("Speciality")) {

                }
                else if (from.equals("Emergency"))
                {
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Profile.pdf");
                    if (file.exists()) {
                        file.delete();
                    }

                    new Header().createPdfHeader(file.getAbsolutePath(),
                            "Personal & Medical Profile");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(2);
                    if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
                        new Individual(PersonalInfoQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL)));


                    }
                    else{
                        new Individual(MyConnectionsQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL)));
                    }
                    // new MessageString().getProfileProfile(connection);
                    final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<String> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<String> historList = HistoryQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    new Individual(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)),AllargyLists,implantsList,historList,hospitalList);

                    ArrayList<Emergency> emergencyList=MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
                    ArrayList<Specialist> specialistsList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),1);
                    ArrayList<Proxy> proxyList=MyConnectionsQuery.fetchAllProxyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),3);
                    new Individual("Emergency",emergencyList);
                    new Individual(specialistsList,"Physician");
                    new Individual(proxyList);

                    Header.document.close();

                }
                else if (from.equals("Insurance"))
                {

                }
                else if (from.equals("Event"))
                {

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                        /*    case 0: // email

                       *//* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*//*
                                break;
                            case 1: // email

                       *//* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*//*

                                break;*/
                            case 0: // view
                                if (from.equals("Speciality")) {

                                }
                                else if (from.equals("Emergency"))
                                {
                                    if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getProfileUser());
                                        result.append(new MessageString().getMedicalInfo());
                                        result.append(new MessageString().getEmergencyInfo());
                                        result.append(new MessageString().getPhysicianInfo());
                                        result.append(new MessageString().getProxyInfo());

                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                                + "/Profile.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                                    }else{
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getProfileProfile());

                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                                + "/Profile.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                 /* new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                            + "/Profile.pdf", getActivity(),
                            new MessageString().getProfileProfile(connection));*/
                                    }
                                }
                                else if (from.equals("Insurance"))
                                {

                                }
                                else if (from.equals("Event"))
                                {

                                }
                                break;

                        }
                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
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
