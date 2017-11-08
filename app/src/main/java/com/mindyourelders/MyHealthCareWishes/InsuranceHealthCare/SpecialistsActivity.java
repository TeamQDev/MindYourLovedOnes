package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AideQuery;
import com.mindyourelders.MyHealthCareWishes.database.AllergyQuery;
import com.mindyourelders.MyHealthCareWishes.database.AppointmentQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DateQuery;
import com.mindyourelders.MyHealthCareWishes.database.FinanceQuery;
import com.mindyourelders.MyHealthCareWishes.database.HistoryQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalHealthQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalQuery;
import com.mindyourelders.MyHealthCareWishes.database.InsuranceQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalImplantsQuery;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.PharmacyQuery;
import com.mindyourelders.MyHealthCareWishes.database.SpecialistQuery;
import com.mindyourelders.MyHealthCareWishes.model.Aides;
import com.mindyourelders.MyHealthCareWishes.model.Allergy;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.model.Finance;
import com.mindyourelders.MyHealthCareWishes.model.Hospital;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.model.Proxy;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.EventPdf;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.Individual;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.InsurancePdf;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.Specialty;
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
    final CharSequence[] dialog_items = {"View","Email","Fax" };
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
        HospitalHealthQuery hhh=new HospitalHealthQuery(context,dbHelper);
        PharmacyQuery ph=new PharmacyQuery(context,dbHelper);
        AideQuery ad=new AideQuery(context,dbHelper);
        FinanceQuery f=new FinanceQuery(context,dbHelper);
        AppointmentQuery df=new AppointmentQuery(context,dbHelper);
        DateQuery da=new DateQuery(context,dbHelper);
        InsuranceQuery i=new InsuranceQuery(context,dbHelper);
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
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Specialty.pdf");
                    if (file.exists()) {
                        file.delete();
                    }

                    new Header().createPdfHeader(file.getAbsolutePath(),
                            "Specialty");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(2);

                    ArrayList<Specialist> specialistsList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
                    ArrayList<Hospital> HospitalList= HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Pharmacy> PharmacyList= PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Aides> AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Finance> financeList= FinanceQuery.fetchAllFinanceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));


                    new Specialty("Hospital",HospitalList);
                    new Specialty(specialistsList,"Doctors");
                    new Specialty(PharmacyList);
                    new Specialty(AidesList,1);
                    new Specialty(1,financeList);

                    Header.document.close();

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
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Insurance.pdf");
                    if (file.exists()) {
                        file.delete();
                    }

                    new Header().createPdfHeader(file.getAbsolutePath(),
                            "Insurance");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                   // Header.addEmptyLine(2);

                    ArrayList<Insurance> insuranceList= InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    new InsurancePdf(insuranceList);

                    Header.document.close();
                }
                else if (from.equals("Event"))
                {
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Event.pdf");
                    if (file.exists()) {
                        file.delete();
                    }

                    new Header().createPdfHeader(file.getAbsolutePath(),
                            "Appointment Checklist");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(2);

                    ArrayList<Appoint> AppointList= AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    new EventPdf(AppointList);

                    Header.document.close();
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
                                       StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getDoctorsInfo());
                                        result.append(new MessageString().getHospitalInfo());
                                        result.append(new MessageString().getPharmacyInfo());
                                        result.append(new MessageString().getAideInfo());
                                        result.append(new MessageString().getFinanceInfo());

                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                                + "/Specialty.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");

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
                                        result.append(new MessageString().getMedicalInfo());
                                        result.append(new MessageString().getEmergencyInfo());
                                        result.append(new MessageString().getPhysicianInfo());
                                        result.append(new MessageString().getProxyInfo());

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
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getInsuranceInfo());


                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Insurance.pdf",
                                            context, result);

                                    System.out.println("\n" + result + "\n");
                                }
                                else if (from.equals("Event"))
                                {
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getAppointInfo());


                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Event.pdf",
                                            context, result);

                                    System.out.println("\n" + result + "\n");
                                }
                                break;
                            
                            case 1: // email
                                if (from.equals("Speciality")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Specialty.pdf");
                                    emailAttachement(f);
                                } else if (from.equals("Emergency")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Profile.pdf");
                                    emailAttachement(f);
                                } else if (from.equals("Insurance")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Insurance.pdf");
                                    emailAttachement(f);
                                } else if (from.equals("Event")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Event.pdf");
                                    emailAttachement(f);
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
    private void emailAttachement(File f) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "MIND YOUR ELDERS"); // subject
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
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
