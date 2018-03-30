package com.mindyourlovedones.healthcare.InsuranceHealthCare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.database.AideQuery;
import com.mindyourlovedones.healthcare.database.AllergyQuery;
import com.mindyourlovedones.healthcare.database.AppointmentQuery;
import com.mindyourlovedones.healthcare.database.CardQuery;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.DateQuery;
import com.mindyourlovedones.healthcare.database.EventNoteQuery;
import com.mindyourlovedones.healthcare.database.FinanceQuery;
import com.mindyourlovedones.healthcare.database.FormQuery;
import com.mindyourlovedones.healthcare.database.HistoryQuery;
import com.mindyourlovedones.healthcare.database.HospitalHealthQuery;
import com.mindyourlovedones.healthcare.database.HospitalQuery;
import com.mindyourlovedones.healthcare.database.InsuranceQuery;
import com.mindyourlovedones.healthcare.database.LivingQuery;
import com.mindyourlovedones.healthcare.database.MedInfoQuery;
import com.mindyourlovedones.healthcare.database.MedicalConditionQuery;
import com.mindyourlovedones.healthcare.database.MedicalImplantsQuery;
import com.mindyourlovedones.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedones.healthcare.database.PetQuery;
import com.mindyourlovedones.healthcare.database.PharmacyQuery;
import com.mindyourlovedones.healthcare.database.SpecialistQuery;
import com.mindyourlovedones.healthcare.database.VaccineQuery;
import com.mindyourlovedones.healthcare.model.Allergy;
import com.mindyourlovedones.healthcare.model.Appoint;
import com.mindyourlovedones.healthcare.model.Card;
import com.mindyourlovedones.healthcare.model.Emergency;
import com.mindyourlovedones.healthcare.model.Finance;
import com.mindyourlovedones.healthcare.model.Form;
import com.mindyourlovedones.healthcare.model.History;
import com.mindyourlovedones.healthcare.model.Hospital;
import com.mindyourlovedones.healthcare.model.Implant;
import com.mindyourlovedones.healthcare.model.Insurance;
import com.mindyourlovedones.healthcare.model.Living;
import com.mindyourlovedones.healthcare.model.Note;
import com.mindyourlovedones.healthcare.model.Pet;
import com.mindyourlovedones.healthcare.model.Pharmacy;
import com.mindyourlovedones.healthcare.model.Specialist;
import com.mindyourlovedones.healthcare.model.Vaccine;
import com.mindyourlovedones.healthcare.pdfCreation.EventPdf;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.pdfdesign.Individual;
import com.mindyourlovedones.healthcare.pdfdesign.InsurancePdf;
import com.mindyourlovedones.healthcare.pdfdesign.Specialty;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
    TextView txtName;
   // final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    final CharSequence[] dialog_items = {"View","Email","Fax" };
    Preferences preferences;
    DBHelper dbHelper;
    final static String TARGET_BASE_PATH = "/sdcard/MYLO/images/";

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
        txtName=(TextView) findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
     //   imgRight= (ImageView) findViewById(R.id.imgRight);
        if (i.getExtras()!=null)
        {
            from=i.getExtras().getString("FROM");
            if (from.equals("Speciality"))
            {
                txtTitle.setText("SPECIALITY CONTACTS");

              //  imgRight.setVisibility(View.VISIBLE);
                header.setBackgroundResource(R.color.colorThree);
                profile=new int[]{R.drawable.physician,R.drawable.hosp_icon,R.drawable.pharmacies,R.drawable.finances};
             //   specialist= new String[]{"DOCTORS","HOSPITALS AND \nOTHER HEALTH PROFESSIONALS", "PHARMACIES AND \nHOME MEDICAL EQUIPMENT", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
               // specialist= new String[]{"DOCTORS & OTHER HEALTH\nPROFESSIONALS","HOSPITALS & REHABILITATION CENTERS", "PHARMACIES & HOME\nMEDICAL EQUIPMENT", "HOME HEALTH SERVICES", "FINANCE, INSURANCE, LEGAL"};
                specialist= new String[]{"DOCTORS & OTHER HEALTH\nPROFESSIONALS","HOSPITALS & REHABILITATION CENTERS", "PHARMACIES & HOME\nMEDICAL EQUIPMENT", "FINANCE, INSURANCE, LEGAL"};

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
                profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician};
                specialist= new String[] { "PERSONAL PROFILE", "MEDICAL PROFILE", "EMERGENCY CONTACTS AND HEALTH CARE PROXY AGENT", "PRIMARY PHYSICIAN" };

               /* profile=new int[]{R.drawable.contacts,R.drawable.medicalinfos,R.drawable.emer_contacts,R.drawable.physician,R.drawable.proxys};
                specialist= new String[] { "PERSONAL PROFILE", "MEDICAL PROFILE", "EMERGENCY CONTACTS AND \nHEALTH CARE PROXY AGENT", "PRIMARY PHYSICIAN", "HEALTH CARE PROXY AGENT" };
*/
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
                profile=new int[]{R.drawable.finances,R.drawable.insurancess,R.drawable.medicalinfos};
                specialist= new String[]{"EVENT NOTES", "APPOINTMENT CHECKLIST","ACTIVITIES OF DAILY LIVING"};
                isEmergency=false;
                isInsurance=false;
            }
        }
    }

    private void databases() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));

        //PersonalInfoQuery p=new PersonalInfoQuery(context,dbHelper);
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
        PetQuery pet=new PetQuery(context,dbHelper);
        EventNoteQuery e=new EventNoteQuery(context,dbHelper);
        LivingQuery l=new LivingQuery(context,dbHelper);
        CardQuery c=new CardQuery(context,dbHelper);
        MedicalConditionQuery mu=new MedicalConditionQuery(context,dbHelper);
        VaccineQuery v=new VaccineQuery(context,dbHelper);
        FormQuery fo=new FormQuery(context,dbHelper);
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
                            + "/mylopdf/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Specialty.pdf");
                    if (file.exists()) {
                        file.delete();
                    }

                   new Header().createPdfHeader(file.getAbsolutePath(),
                            ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                    copyFile("ic_launcher.png");
                    Header.addImage(TARGET_BASE_PATH+"ic_launcher.png");
                    Header.addEmptyLine(1);
                    Header.addusereNameChank("Specialty Contacts");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(1);

                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Paragraph p = new Paragraph(" ");
                    LineSeparator line = new LineSeparator();
                    line.setOffset(-4);
                    line.setLineColor(BaseColor.LIGHT_GRAY);
                    p.add(line);
                    try {
                        Header.document.add(p);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    Header.addEmptyLine(1);

                  /* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Specialty");*/

                   // Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                  //  Header.addEmptyLine(2);

                    ArrayList<Specialist> specialistsList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
                    ArrayList<Hospital> HospitalList= HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Pharmacy> PharmacyList= PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                   // ArrayList<Aides> AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Finance> financeList= FinanceQuery.fetchAllFinanceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    new Specialty(specialistsList,"Doctors");
                    new Specialty("Hospital",HospitalList);
                    new Specialty(PharmacyList);
                 //   new Specialty(AidesList,1);
                    new Specialty(1,financeList);

                    Header.document.close();

                }
                else if (from.equals("Emergency"))
                {
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mylopdf/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Profile.pdf");
                    if (file.exists()) {
                        file.delete();
                    }
                    new Header().createPdfHeader(file.getAbsolutePath(),
                            ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                    copyFile("ic_launcher.png");
                    Header.addImage(TARGET_BASE_PATH+"ic_launcher.png");
                    Header.addEmptyLine(1);
                    Header.addusereNameChank("Personal & Medical Profile");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(1);
                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Paragraph p = new Paragraph(" ");
                    LineSeparator line = new LineSeparator();
                    line.setOffset(-4);
                    line.setLineColor(BaseColor.LIGHT_GRAY);
                    p.add(line);
                    try {
                        Header.document.add(p);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    Header.addEmptyLine(1);
/*
                    new Header().createPdfHeader(file.getAbsolutePath(),
                            "Personal & Medical Profile");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(2);*/

                  /*  if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                        final ArrayList<Pet> PetLists = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                        new Individual((PersonalInfoQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID))),PetLists);
                    }
                    else{*/
                        final ArrayList<Pet> PetList = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                        new Individual((MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID))),PetList);
                   // }
                    // new MessageString().getProfileProfile(connection);
                    final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<Implant> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<String> conditionList= MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final ArrayList<Vaccine> vaccineList= VaccineQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));


                    new Individual(MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID)),AllargyLists,implantsList,historList,hospitalList,conditionList,vaccineList);



                    ArrayList<Emergency> emergencyList=MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
                    ArrayList<Specialist> specialistsList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),1);
                   // ArrayList<Proxy> proxyList=MyConnectionsQuery.fetchAllProxyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),3);
                    new Individual("Emergency",emergencyList);
                    new Individual(specialistsList,"Physician");
                 //   new Individual(proxyList);



                    Header.document.close();

                }
                else if (from.equals("Insurance"))
                {
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mylopdf/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Insurance.pdf");
                    if (file.exists()) {
                        file.delete();
                    }
                    new Header().createPdfHeader(file.getAbsolutePath(),
                            ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                    copyFile("ic_launcher.png");
                    Header.addImage(TARGET_BASE_PATH+"ic_launcher.png");
                    Header.addEmptyLine(1);
                    Header.addusereNameChank("Insurance");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(1);

                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Paragraph p = new Paragraph(" ");
                    LineSeparator line = new LineSeparator();
                    line.setOffset(-4);
                    line.setLineColor(BaseColor.LIGHT_GRAY);
                    p.add(line);
                    try {
                        Header.document.add(p);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    Header.addEmptyLine(1);

                    /* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Insurance");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));*/
                   // Header.addEmptyLine(2);

                    ArrayList<Insurance> insuranceList= InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Card> CardList= CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Form> formList= FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    new InsurancePdf(insuranceList);
                    new InsurancePdf(CardList,1);
                    new InsurancePdf(formList,"form");

                    Header.document.close();
                }
                else if (from.equals("Event"))
                {
                    final String RESULT = Environment.getExternalStorageDirectory()
                            + "/mylopdf/";
                    File dirfile = new File(RESULT);
                    dirfile.mkdirs();
                    File file = new File(dirfile, "Event.pdf");
                    if (file.exists()) {
                        file.delete();
                    }
                    new Header().createPdfHeader(file.getAbsolutePath(),
                            ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                    copyFile("ic_launcher.png");
                    Header.addImage(TARGET_BASE_PATH+"ic_launcher.png");
                    Header.addEmptyLine(1);
                    Header.addusereNameChank("Event And Appointment Checklist");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(1);

                    Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
                    Paragraph p = new Paragraph(" ");
                    LineSeparator line = new LineSeparator();
                    line.setOffset(-4);
                    line.setLineColor(BaseColor.LIGHT_GRAY);
                    p.add(line);
                    try {
                        Header.document.add(p);
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    Header.addEmptyLine(1);

                   /* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Event And Appointment Checklist");

                    Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                    Header.addEmptyLine(2);*/

                    ArrayList<Appoint> AppointList= AppointmentQuery.fetchAllAppointmentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Note> NoteList= EventNoteQuery.fetchAllNoteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    new EventPdf(NoteList,1);
                    new EventPdf(AppointList);
                    Living Live=LivingQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    ArrayList<Living> LivingList=new ArrayList<Living>();
                    LivingList.add(Live);
                    new EventPdf(1,LivingList,1);


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
                                       // result.append(new MessageString().getAideInfo());
                                        result.append(new MessageString().getFinanceInfo());

                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mylopdf/"
                                                + "/Specialty.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");

                                }
                                else if (from.equals("Emergency"))
                                {
                                    /*if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getProfileUser());
                                        result.append(new MessageString().getMedicalInfo());
                                        result.append(new MessageString().getEmergencyInfo());
                                        result.append(new MessageString().getPhysicianInfo());
                                     //   result.append(new MessageString().getProxyInfo());


                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mylopdf/" 
                                                + "/Profile.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                                    }else{*/
                                        StringBuffer result = new StringBuffer();
                                        result.append(new MessageString().getProfileProfile());
                                        result.append(new MessageString().getMedicalInfo());
                                        result.append(new MessageString().getLivingInfo());
                                        result.append(new MessageString().getEmergencyInfo());
                                        result.append(new MessageString().getPhysicianInfo());
                                        result.append(new MessageString().getProxyInfo());

                                        new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                                + "/mylopdf/" 
                                                + "/Profile.pdf",
                                                context, result);

                                        System.out.println("\n" + result + "\n");
                 /* new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                            + "/mylopdf/" 
                            + "/Profile.pdf", getActivity(),
                            new MessageString().getProfileProfile(connection));*/
                                  //  }
                                }
                                else if (from.equals("Insurance"))
                                {
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getInsuranceInfo());
                                    result.append(new MessageString().getInsuranceCard());
                                    result.append(new MessageString().getFormInfo());

                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/"
                                            + "/Insurance.pdf",
                                            context, result);

                                    System.out.println("\n" + result + "\n");
                                }
                                else if (from.equals("Event"))
                                {
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getEventInfo());
                                    result.append(new MessageString().getAppointInfo());
                                    result.append(new MessageString().getLivingInfo());



                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" + "/Event.pdf",
                                            context, result);

                                    System.out.println("\n" + result + "\n");
                                }
                                break;
                            
                            case 1: // email
                                if (from.equals("Speciality")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/"   + "/Specialty.pdf");
                                    emailAttachement(f,"Speciality");
                                } else if (from.equals("Emergency")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/"  + "/Profile.pdf");
                                    emailAttachement(f, "Profile");
                                } else if (from.equals("Insurance")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Insurance.pdf");
                                    emailAttachement(f, "Insurance");
                                } else if (from.equals("Event")) {
                                    File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Event.pdf");
                                    emailAttachement(f, "Event");
                                }
                                break;

                            case 2://Fax
                                if (from.equals("Speciality")) {
                                    serverAttachement(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Specialty.pdf");
                                  /*  File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Specialty.pdf");
                                    emailAttachement(f);*/
                                } else if (from.equals("Emergency")) {
                                    serverAttachement(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Profile.pdf");
                                  /*  File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Profile.pdf");
                                    emailAttachement(f);*/
                                } else if (from.equals("Insurance")) {
                                    serverAttachement(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Insurance.pdf");
                                    /*File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Insurance.pdf");*/
                                   // emailAttachement(f);
                                } else if (from.equals("Event")) {
                                    serverAttachement(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Event.pdf");
                                  /*  File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/" 
                                            + "/Event.pdf");
                                    emailAttachement(f);*/
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

    private void serverAttachement(String path) {
        System.out.println("Path of the file    "+path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(SpecialistsActivity.this, path).show();;
    }

    private void emailAttachement(File f, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        String name= preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                name+" - "+s); // subject


        String body="Hi, \n" +
                "\n" +
                "\n" +name+
                " shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thanks,\n" +
                name;
               // "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", f);
        } else {
            uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }
    private void initUi() {
        //header= (RelativeLayout) findViewById(R.id.header);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgRight=(ImageView) findViewById(R.id.imgRight);
        txtName= (TextView) findViewById(R.id.txtName);
        listSpeciallist= (ListView) findViewById(R.id.listSpecialist);
        SpecialistContactAdapter adapter=new SpecialistContactAdapter(context,specialist,profile,isEmergency,isInsurance,from);
        listSpeciallist.setAdapter(adapter);
    }
    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;

        try {
            File dir = new File(TARGET_BASE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.i("tag", "copyFile() " + filename);
            in = assetManager.open(filename);
            newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName);
            Log.e("tag", "Exception in copyFile() " + e.toString());
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
    }
}
