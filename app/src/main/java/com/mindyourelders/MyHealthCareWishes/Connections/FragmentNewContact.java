package com.mindyourelders.MyHealthCareWishes.Connections;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.DashBoard.AddFormActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.AideQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DoctorQuery;
import com.mindyourelders.MyHealthCareWishes.database.FinanceQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalHealthQuery;
import com.mindyourelders.MyHealthCareWishes.database.InsuranceQuery;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PharmacyQuery;
import com.mindyourelders.MyHealthCareWishes.database.SpecialistQuery;
import com.mindyourelders.MyHealthCareWishes.model.Aides;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.model.Finance;
import com.mindyourelders.MyHealthCareWishes.model.Hospital;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.model.Proxy;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.utility.AppConstants;
import com.mindyourelders.MyHealthCareWishes.utility.DialogManager;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.txtPhone;
import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentNewContact extends Fragment implements View.OnClickListener {
    byte[] photoCard;
    String Cname = "";
    String Cemail ="";
    String Cphone ="";
    //TextView btnShowMore,btnShowLess,btnSon;
    TextView txtOtherInsurance,txtOtherCategory,txtOtherRelation,txtName, txtEmail, txtMobile,txtHomePhone,txtWorkPhone, txtAdd, txtInsuaranceName, txtInsuarancePhone, txtId, txtGroup, txtMember, txtAddress;

    TextView txtAgent,txtPracticeName, txtFax, txtNetwork, txtAffiliation,txtDoctorNote,txtDoctorName,txtDoctorOfficePhone,txtDoctorHourOfficePhone,txtDoctorOtherPhone,txtDoctorFax,txtDoctorWebsite;
    TextView txtDoctorAddress,txtDoctorLastSeen,txtSubscribe,txtInsuaranceFax,txtInsuaranceEmail,txtWebsite,txtInsuaranceNote;
    TextView txtFName,txtFinanceOfficePhone,txtFinanceMobilePhone,txtFinanceOtherPhone,txtFinanceFax,txtFinanceAddress,txtFinanceWebsite,txtFinancePracticeName,txtLastSeen,txtFinanceNote;
    TextView txtAids, txtSchedule, txtOther,txtEmergencyNote;
    TextView txtPharmacyName,txtPharmacyAddress,txtPharmacyPhone,txtPharmacyFax,txtPharmacyWebsite,txtPharmacyNote;
    TextView txtAideAddress,txtAideCompName,txtAideOfficePhone,txtHourOfficePhone,txtOtherPhone,txtAideFax,txtAideEmail,txtAideWebsite,txtAideNote;
    TextView txtTitle;
    String agent="";

    ImageView imgEdit, imgProfile,imgCard,imgEditCard;
    View rootview;
    RelativeLayout rlRelation, rlConnection, rlDoctor, rlInsurance, rlCommon, rlAids, rlFinance,rlProxy, rlTop,llAddConn,rlPharmacy;
    Preferences preferences;
    String source="";
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;

    private static int RESULT_CAMERA_IMAGE_CARD = 3;
    private static int RESULT_SELECT_PHOTO_CARD = 4;



    String name="", email="", mobile="", speciality="",phone="",address="",workphone="",note="",member="",group="",subscriber="",type="";
    String network="",affil="",practice_name="",website="",lastseen="";
    String fax="";
    String relation="";
    String proxy="";
    String priority="";
    String otherRelation="";
    String otherCategory="";
    String otherInsurance="";
    int prior;
    int prox;
    int connectionFlag;
    boolean inPrimary;
    MySpinner spinner, spinnerInsuarance, spinnerFinance,spinnerProxy,spinnerRelation,spinnerPriority;
    TextInputLayout tilOtherInsurance,tilOtherCategory,tilOtherRelation,tilName,tilFName,tilEmergencyNote,tilDoctorName,tilPharmacyName,tilAideCompName,tilInsuaranceName;

    StaggeredTextGridView gridRelation;
    ArrayList<String> relationArraylist;
    RelationAdapter relationAdapter;

    DialogManager dialogManager;

    String imagepath = "";

    String[] Relationship = {"Aunt","Brother","Cousin","Dad","Daughter","Father-in-law","Friend","GrandDaughter","GrandFather","GrandMother","GrandSon","Husband","Mom","Mother-in-law","Neighbor","Nephew","Niece","Sister","Son","Uncle","Wife", "Other"};

    String[] healthSpeciality = {"CARDIOLOGIST","ENDOCRINOLOGIST","FAMILY PRACTICE PHYSICIAN","GERIATRIC PSYCHIATRIST","GERIATRICIAN","GYNECOLOGIST","INTERNIST","NEPHROLOGIST","NEUROLOGIST","ONCOLOGIST","OPHTHALMOLOGIST","ORTHOPEDIC","PHYSICAL THERAPIST","PULMONOLOGIST","RHEUMATOLOGIST","UROLOGIST"};

    String[] insuaranceType = {"Dental","Disability","Life","Long Term Care","Medicaid","Medical","Medicare Supplement (Medigap)","Medicare","Supplemental","Vision", "Other"};

    String[] financeType = {"Accountant", "Attorney", "Financial Planner", "Insurance Broker", "Stock Broker", "Trustee", "Executor", "Other"};
    String[] HospitalType = {"Hospital", "Rehabilitation Center", "Physical Therapist", "Speech Pathologist","Other"};

    String[] proxyType = {"Primary", "Successor"};
    String[] priorityType = {"Primary", "Secondary"};

    Boolean isEdit;

    DBHelper dbHelper;
    int id;
    int isPhysician;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_new_contact, null);
        preferences = new Preferences(getActivity());
        getRelationData();
        initComponent();
        initUI();
        initListener();
        initVariables();
        return rootview;
    }

    private void initComponent() {
        dbHelper=new DBHelper(getActivity());
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
        DoctorQuery d=new DoctorQuery(getActivity(),dbHelper);
        SpecialistQuery s=new SpecialistQuery(getActivity(),dbHelper);
        HospitalHealthQuery h=new HospitalHealthQuery(getActivity(),dbHelper);
    }

    private void getRelationData() {
        AppConstants.RELATION="";
        relationArraylist = new ArrayList<>();
        relationArraylist.add("Mother");
        relationArraylist.add("Father");
        relationArraylist.add("Wife");
        relationArraylist.add("Husband");
        relationArraylist.add("Daughter");
        relationArraylist.add("Son");
        relationArraylist.add("Sister");
        relationArraylist.add("Brother");
        relationArraylist.add("Friend");
        relationArraylist.add("GrandFather");
        relationArraylist.add("GrandMother");
        relationArraylist.add("GrandSon");
        relationArraylist.add("GrandDaughter");
        relationArraylist.add("Other");
    }

    private void initVariables() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
           Cname = bundle.getString("Name");
            Cemail = bundle.getString("Email");
            Cphone = bundle.getString("Phone");
            byte[] image = bundle.getByteArray("Photo");
            Bitmap photo = BitmapFactory.decodeByteArray(image, 0, image.length);
            byte[] imageCard = bundle.getByteArray("PhotoCard");
            Bitmap photoCard = BitmapFactory.decodeByteArray(imageCard, 0, imageCard.length);

            imgProfile.setImageBitmap(photo);
            imgCard.setImageBitmap(photoCard);
            source = preferences.getString(PrefConstants.SOURCE);
            switch(source)
            {
                case "Connection":
                    getContact();
                    break;
                case "Proxy":
                    getContact();
                    break;
                case "Emergency":
                    getContact();
                    break;
                case "Physician":
                    getSContact();
                    break;
                case "Speciality":
                    getSContact();
                    break;
                case "Hospital":
                    txtFName.setText(Cname);
                   // txtEmail.setText(email);
                    try {
                        String mobile = "";
                        mobile = Cphone;
                        String code = mobile.substring(0, 3);
                        mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
                        txtFinanceMobilePhone.setText(mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Pharmacy":
                    txtPharmacyName.setText(Cname);
                    // txtEmail.setText(email);
                    try {
                        String mobile = "";
                        mobile = Cphone;
                        String code = mobile.substring(0, 3);
                        mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
                        txtPharmacyPhone.setText(mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Aides":
                    txtAideCompName.setText(Cname);
                    txtAideEmail.setText(Cemail);
                    try {
                        String mobile = "";
                        mobile = Cphone;
                        String code = mobile.substring(0, 3);
                        mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
                        txtAideOfficePhone.setText(mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Finance":
                    txtFName.setText(Cname);
                    // txtEmail.setText(email);
                    try {
                        String mobile = "";
                        mobile = Cphone;
                        String code = mobile.substring(0, 3);
                        mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
                        txtFinanceMobilePhone.setText(mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "Insurance":
                    txtInsuaranceName.setText(Cname);
                    txtInsuaranceEmail.setText(Cemail);
                    try {
                        String mobile = "";
                        mobile = Cphone;
                        String code = mobile.substring(0, 3);
                        mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
                        txtInsuarancePhone.setText(mobile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
      /*  txtName.setText(name);
        txtEmail.setText(email);
        try {
            String mobile = "";
            mobile = phone;
            String code = mobile.substring(0, 3);
            mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
            txtMobile.setText(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        source = preferences.getString(PrefConstants.SOURCE);
        switch (source) {
            case "Connection":
                rlTop.setVisibility(View.GONE);
                rlCommon.setVisibility(View.VISIBLE);
                spinnerRelation.setVisibility(View.VISIBLE);
                rlConnection.setVisibility(View.VISIBLE);
                rlDoctor.setVisibility(View.GONE);
                rlInsurance.setVisibility(View.GONE);
                rlAids.setVisibility(View.GONE);
                rlProxy.setVisibility(View.GONE);
                rlFinance.setVisibility(View.GONE);
                txtAdd.setText("ADD PROFILE");
                tilName.setHint("First Name, Last Name");
                tilName.setHintEnabled(false);
                txtName.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        tilName.setHintEnabled(true);
                        txtName.setFocusable(true);

                        return false;
                    }
                });
                txtTitle.setText("Add Profile");txtTitle.setAllCaps(true);

                tilEmergencyNote.setVisibility(View.GONE);
                rlPharmacy.setVisibility(View.GONE);
                break;

            case "Pharmacy":
                visiPharmacy();
                txtAdd.setText("Add Pharmacy");
                txtTitle.setText("Add Pharmacy");
                break;

            case "PharmacyData":
                visiPharmacy();
                txtAdd.setText("Update Pharmacy");
                txtTitle.setText("Update Pharmacy");
                Intent specialistIntents = getActivity().getIntent();
                if (specialistIntents.getExtras() != null) {
                    Pharmacy specialist = (Pharmacy) specialistIntents.getExtras().getSerializable("PharmacyObject");
                    txtPharmacyName.setText(specialist.getName());
                    txtPharmacyAddress.setText(specialist.getAddress());
                    txtPharmacyWebsite.setText(specialist.getWebsite());
                    txtPharmacyFax.setText(specialist.getFax());
                    txtPharmacyPhone.setText(specialist.getPhone());
                    txtPharmacyNote.setText(specialist.getNote());
                    id = specialist.getId();
                    byte[] photo = specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);
                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                    break;

            case "PharmacyDataView":
                visiPharmacy();
                disablePharmacy();
                txtTitle.setText("Pharmacy");
                txtTitle.setVisibility(View.VISIBLE);
                Intent specialistIntents2 = getActivity().getIntent();
                if (specialistIntents2.getExtras() != null) {
                    Pharmacy specialist = (Pharmacy) specialistIntents2.getExtras().getSerializable("PharmacyObject");
                    txtPharmacyName.setText(specialist.getName());
                    txtPharmacyAddress.setText(specialist.getAddress());
                    txtPharmacyWebsite.setText(specialist.getWebsite());
                    txtPharmacyFax.setText(specialist.getFax());
                    txtPharmacyPhone.setText(specialist.getPhone());
                    txtPharmacyNote.setText(specialist.getNote());
                    id = specialist.getId();

                    byte[] photo = specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);
                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;

            case "Proxy":
                visiProxy();
                txtAdd.setText("Add Proxy AGENT & SUCCESSOR(S)");
                txtTitle.setText("Add Proxy AGENT & SUCCESSOR(S)");
                break;

            case "ProxyUpdate":
                visiProxy();
                txtAdd.setText("Update Proxy AGENT & SUCCESSOR(S)");
                txtTitle.setText("Update Proxy AGENT & SUCCESSOR(S)");
                Intent ProxyIntent = getActivity().getIntent();
                if (ProxyIntent.getExtras()!=null)
                {

                    Proxy rel= (Proxy) ProxyIntent.getExtras().getSerializable("ProxyObject");
                    txtName.setText(rel.getName());
                    txtEmail.setText(rel.getEmail());
                    txtMobile.setText(rel.getMobile());
                    txtHomePhone.setText(rel.getPhone());
                    txtWorkPhone.setText(rel.getWorkPhone());
                    txtAddress.setText(rel.getAddress());
                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());
                    id=rel.getId();
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (rel.getRelationType().equals(Relationship[i])) {
                            index = i;
                        }
                    }
                    spinnerRelation.setSelection(index+1);
                   /* prox=rel.getIsPrimary();
                    if (prox==1) {
                        spinnerProxy.setSelection(0);
                    }else if (prox==2)
                    {
                        spinnerProxy.setSelection(1);
                    }*/
                    spinnerProxy.setSelection(rel.getIsPrimary()+1);

                    byte[] photo=rel.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = rel.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;

            case "ProxyUpdateView":
                visiProxy();
                disableProxy();
                txtTitle.setText("Proxy");
                txtTitle.setVisibility(View.VISIBLE);
                Intent ProxyIntents = getActivity().getIntent();
                if (ProxyIntents.getExtras()!=null)
                {

                    Proxy rel= (Proxy) ProxyIntents.getExtras().getSerializable("ProxyObject");
                    txtName.setText(rel.getName());
                    txtEmail.setText(rel.getEmail());
                    txtMobile.setText(rel.getMobile());
                    txtHomePhone.setText(rel.getPhone());
                    txtWorkPhone.setText(rel.getWorkPhone());
                    txtAddress.setText(rel.getAddress());
                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());
                    id=rel.getId();
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (rel.getRelationType().equals(Relationship[i])) {
                            index = i;
                        }
                    }
                    spinnerRelation.setSelection(index+1);
                   /* prox=rel.getIsPrimary();
                    if (prox==1) {
                        spinnerProxy.setSelection(0);
                    }else if (prox==2)
                    {
                        spinnerProxy.setSelection(1);
                    }*/

                    spinnerProxy.setSelection(rel.getIsPrimary()+1);
                    byte[] photo=rel.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = rel.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }

                break;

            case "Emergency":
                visiEmergency();
                spinnerPriority.setVisibility(View.VISIBLE);
                txtAdd.setText("Add Emergency");
                txtTitle.setText("Add Emergency Contact");
                break;

            case "EmergencyUpdate":
                visiEmergency();
                spinnerPriority.setVisibility(View.VISIBLE);
                txtAdd.setText("Update Emergency");
                txtTitle.setText("Update Emergency Contact");
                Intent EmergencyIntent = getActivity().getIntent();
                if (EmergencyIntent.getExtras()!=null)
                {

                    Emergency rel= (Emergency) EmergencyIntent.getExtras().getSerializable("EmergencyObject");
                    txtName.setText(rel.getName());
                    txtEmail.setText(rel.getEmail());
                    txtMobile.setText(rel.getMobile());
                    txtHomePhone.setText(rel.getPhone());
                    txtWorkPhone.setText(rel.getWorkPhone());
                    txtAddress.setText(rel.getAddress());
                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());
                    id=rel.getId();
                        int index = 0;
                        for (int i = 0; i < Relationship.length; i++) {
                            if (rel.getRelationType().equals(Relationship[i])) {
                                index = i;
                            }
                        }

                    spinnerRelation.setSelection(index+1);
                    int indexs = 0;
                   /* int pr=rel.getIsPrimary();String priem="";
                    if (pr==0)
                    {
                        priem="Primary";
                    }
                    else{
                        priem="Secondary";
                    }
                    for (int i = 0; i <priorityType.length; i++) {
                        if (priem.equals(priorityType[i])) {
                            indexs = i;
                        }
                    }*/

                    spinnerPriority.setSelection(rel.getIsPrimary()+1);

                    byte[] photo=rel.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = rel.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);

                }
                break;

            case "EmergencyView":
                visiEmergency();
                disableEmergency();
                spinnerPriority.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);
                txtTitle.setText("Emergency Contact");
                Intent EmergencyIntents = getActivity().getIntent();
                if (EmergencyIntents.getExtras()!=null)
                {

                    Emergency rel= (Emergency) EmergencyIntents.getExtras().getSerializable("EmergencyObject");
                    txtName.setText(rel.getName());
                    txtEmail.setText(rel.getEmail());
                    txtMobile.setText(rel.getMobile());
                    txtHomePhone.setText(rel.getPhone());
                    txtWorkPhone.setText(rel.getWorkPhone());
                    txtAddress.setText(rel.getAddress());
                    txtEmergencyNote.setText(rel.getNote());
                    txtOtherRelation.setText(rel.getOtherRelation());
                    id=rel.getId();
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (rel.getRelationType().equals(Relationship[i])) {
                            index = i;
                        }
                    }

                    spinnerRelation.setSelection(index+1);
                    spinnerPriority.setSelection(rel.getIsPrimary()+1);
                    byte[] photo=rel.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = rel.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);

                }
                break;

            case "Speciality":
                visiSpecialist();
                txtAdd.setText("Add Doctor");
                txtTitle.setText("Add Doctor");
                break;

            case "Physician":
                visiSpecialist();
                txtAdd.setText("Add Physician");
                txtTitle.setText("Add Physician");
                break;

            case "SpecialistData":

                visiSpecialist();
                txtAdd.setText("Update Doctor");
                txtTitle.setText("Update Doctor");
                Intent specialistIntent = getActivity().getIntent();
                if (specialistIntent.getExtras() != null) {
                    Specialist specialist= (Specialist) specialistIntent.getExtras().getSerializable("SpecialistObject");
                    txtDoctorName.setText(specialist.getName());
                    txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    txtDoctorLastSeen.setText(specialist.getLastseen());
                    txtDoctorAddress.setText(specialist.getAddress());
                    txtDoctorWebsite.setText(specialist.getWebsite());
                    txtDoctorFax.setText(specialist.getFax());
                    txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    txtAffiliation.setText(specialist.getHospAffiliation());
                    txtPracticeName.setText(specialist.getPracticeName());
                    txtNetwork.setText(specialist.getNetwork());
                    txtDoctorNote.setText(specialist.getNote());
                    id=specialist.getId();
                    isPhysician=specialist.getIsPhysician();
                    int index = 0;
                    for (int i = 0; i < healthSpeciality.length; i++) {
                        if (specialist.getType().equals(healthSpeciality[i])) {
                            index = i;
                        }
                    }
                    spinner.setSelection(index+1);

                    byte[] photo=specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);

                }
                break;

            case "SpecialistViewData":
                visiSpecialist();
                disableSpecialist();
                txtTitle.setText("Doctor");
                txtTitle.setVisibility(View.VISIBLE);
                Intent specialistIntentss = getActivity().getIntent();
                if (specialistIntentss.getExtras() != null) {
                    Specialist specialist= (Specialist) specialistIntentss.getExtras().getSerializable("SpecialistObject");
                    txtDoctorName.setText(specialist.getName());
                    txtDoctorOtherPhone.setText(specialist.getOtherPhone());
                    txtDoctorLastSeen.setText(specialist.getLastseen());
                    txtDoctorAddress.setText(specialist.getAddress());
                    txtDoctorWebsite.setText(specialist.getWebsite());
                    txtDoctorFax.setText(specialist.getFax());
                    txtDoctorHourOfficePhone.setText(specialist.getHourPhone());
                    txtDoctorOfficePhone.setText(specialist.getOfficePhone());
                    txtAffiliation.setText(specialist.getHospAffiliation());
                    txtPracticeName.setText(specialist.getPracticeName());
                    txtNetwork.setText(specialist.getNetwork());
                    txtDoctorNote.setText(specialist.getNote());
                    id=specialist.getId();
                    isPhysician=specialist.getIsPhysician();
                    int index = 0;
                    for (int i = 0; i < healthSpeciality.length; i++) {
                        if (specialist.getType().equals(healthSpeciality[i])) {
                            index = i;
                        }
                    }
                    spinner.setSelection(index+1);

                    byte[] photo=specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);

                }
                break;

            case "Insurance":
                visiInsurance();
                txtAdd.setText("Add Insurance");
                txtTitle.setText("Add Insurance");
                break;

            case "InsuranceData":
                visiInsurance();
                tilInsuaranceName.setHintEnabled(true);
                txtInsuaranceName.setFocusable(true);
                txtAdd.setText("Update Insurance");
                txtTitle.setText("Update Insurance");
                Intent insuranceIntent = getActivity().getIntent();
                if (insuranceIntent.getExtras() != null) {
                    Insurance insurance= (Insurance) insuranceIntent.getExtras().getSerializable("InsuranceObject");
                    int index = 0;
                    for (int i = 0; i < insuaranceType.length; i++) {
                        if (insurance.getType().equalsIgnoreCase(insuaranceType[i])) {
                            index = i;
                        }
                    }
                    spinnerInsuarance.setSelection(index+1);
                    spinnerInsuarance.setDisabledColor(getActivity().getResources().getColor(R.color.colorBlack));
                    txtInsuarancePhone.setText(insurance.getPhone());
                    txtId.setText(insurance.getMember());
                    txtGroup.setText(insurance.getGroup());
                    txtInsuaranceFax.setText(insurance.getFax());
                    txtInsuaranceEmail.setText(insurance.getEmail());
                    txtAgent.setText(insurance.getAgent());
                    txtWebsite.setText(insurance.getWebsite());
                    txtInsuaranceNote.setText(insurance.getNote());
                    txtInsuaranceName.setText(insurance.getName());
                    txtSubscribe.setText(insurance.getSubscriber());
                    txtOtherInsurance.setText(insurance.getOtherInsurance());
                    id=insurance.getId();
                    byte[] photo=insurance.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = insurance.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;

            case "InsuranceViewData":
                visiInsurance();
              disableInsurance();
                tilInsuaranceName.setHintEnabled(true);
                txtInsuaranceName.setFocusable(true);
                txtTitle.setText("INSURANCE INFORMATION");
                txtTitle.setVisibility(View.VISIBLE);
                Intent insuranceIntent2 = getActivity().getIntent();
                if (insuranceIntent2.getExtras() != null) {
                    Insurance insurance= (Insurance) insuranceIntent2.getExtras().getSerializable("InsuranceObject");
                    int index = 0;
                    for (int i = 0; i < insuaranceType.length; i++) {
                        if (insurance.getType().equalsIgnoreCase(insuaranceType[i])) {
                            index = i;
                        }
                    }
                    spinnerInsuarance.setSelection(index+1);
                    spinnerInsuarance.setDisabledColor(getActivity().getResources().getColor(R.color.colorBlack));
                    txtInsuarancePhone.setText(insurance.getPhone());
                    txtId.setText(insurance.getMember());
                    txtGroup.setText(insurance.getGroup());
                    txtInsuaranceFax.setText(insurance.getFax());
                    txtInsuaranceEmail.setText(insurance.getEmail());
                    txtAgent.setText(insurance.getAgent());
                    txtWebsite.setText(insurance.getWebsite());
                    txtInsuaranceNote.setText(insurance.getNote());
                    txtInsuaranceName.setText(insurance.getName());
                    txtSubscribe.setText(insurance.getSubscriber());
                    txtOtherInsurance.setText(insurance.getOtherInsurance());
                    id=insurance.getId();
                    byte[] photo=insurance.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = insurance.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;

            case "Aides":
                visiAides();
                txtAdd.setText("Add Health Service");
                txtTitle.setText("Add Health Service");
                break;

            case "AidesData":
                visiAides();
                txtAdd.setText("Update Health Service");
                txtTitle.setText("Update Health Service");
                Intent aidesIntent = getActivity().getIntent();
                if (aidesIntent.getExtras() != null) {
                    Aides aides= (Aides) aidesIntent.getSerializableExtra("AideObject");
                    txtAideCompName.setText(aides.getAidName());
                    txtAideOfficePhone.setText(aides.getOfficePhone());
                    txtHourOfficePhone.setText(aides.getHourPhone());
                    txtOtherPhone.setText(aides.getOtherPhone());
                    txtAideFax.setText(aides.getFax());
                    txtAideEmail.setText(aides.getEmail());
                    txtAideAddress.setText(aides.getAddress());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideNote.setText(aides.getNote());
                    id=aides.getId();

                    byte[] photo=aides.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = aides.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }

                break;

            case "AidesViewData":
                visiAides();
                disableAides();
                txtTitle.setText("Health Service");
                txtTitle.setVisibility(View.VISIBLE);
                Intent aidesIntent2 = getActivity().getIntent();
                if (aidesIntent2.getExtras() != null) {
                    Aides aides= (Aides) aidesIntent2.getSerializableExtra("AideObject");
                    txtAideCompName.setText(aides.getAidName());
                    txtAideOfficePhone.setText(aides.getOfficePhone());
                    txtHourOfficePhone.setText(aides.getHourPhone());
                    txtOtherPhone.setText(aides.getOtherPhone());
                    txtAideFax.setText(aides.getFax());
                    txtAideEmail.setText(aides.getEmail());
                    txtAideAddress.setText(aides.getAddress());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideWebsite.setText(aides.getWebsite());
                    txtAideNote.setText(aides.getNote());
                    id=aides.getId();

                    byte[] photo=aides.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);


                    //Change Class Name
                    byte[] photoCard = aides.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }

                break;

            case "Finance":
                visiFinance();
                txtAdd.setText("Add Finance, Insurance, Legal");
                txtTitle.setText("Add Finance, Insurance, Legal");
                break;

            case "Hospital":
                visiFinance();
                txtAdd.setText("Add Hospitals and Other Health Professionals");
                txtTitle.setText("Add Hospitals and Other Health Professionals");
                break;

            case "HospitalData":
                visiFinance();
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);
                txtAdd.setText("Update Hospitals and Other Health Professionals");
                txtTitle.setText("Update Hospitals and Other Health Professionals");
                Intent hospIntent = getActivity().getIntent();
                if (hospIntent.getExtras() != null) {
                    Hospital specialist = (Hospital) hospIntent.getExtras().getSerializable("HospitalObject");

                    txtFName.setText(specialist.getName());
                    txtFinanceOtherPhone.setText(specialist.getOtherPhone());
                    txtLastSeen.setText(specialist.getLastseen());
                    txtFinanceAddress.setText(specialist.getAddress());
                    txtFinanceWebsite.setText(specialist.getWebsite());
                    txtFinanceFax.setText(specialist.getFax());
                    txtFinanceMobilePhone.setText(specialist.getHourPhone());
                    txtFinanceOfficePhone.setText(specialist.getOfficePhone());
                    txtFinancePracticeName.setText(specialist.getPracticeName());
                    txtFinanceNote.setText(specialist.getNote());
                    txtOtherCategory.setText(specialist.getOtherCategory());
                    id = specialist.getId();

                    int index = 0;
                    for (int i = 0; i < HospitalType.length; i++) {
                        if (specialist.getCategory().equals(HospitalType[i])) {
                            index = i;
                        }
                    }
                    spinnerFinance.setSelection(index + 1);

                    byte[] photo = specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;
            case "HospitalViewData":
                visiFinance();
                disableFinance();
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);
                txtTitle.setText("Hospitals and Other Health Professionals");
                txtTitle.setVisibility(View.VISIBLE);
                Intent financeIntent2 = getActivity().getIntent();
                if (financeIntent2.getExtras() != null) {
                    Hospital specialist = (Hospital) financeIntent2.getExtras().getSerializable("HospitalObject");

                    txtFName.setText(specialist.getName());
                    txtFinanceOtherPhone.setText(specialist.getOtherPhone());
                    txtLastSeen.setText(specialist.getLastseen());
                    txtFinanceAddress.setText(specialist.getAddress());
                    txtFinanceWebsite.setText(specialist.getWebsite());
                    txtFinanceFax.setText(specialist.getFax());
                    txtFinanceMobilePhone.setText(specialist.getHourPhone());
                    txtFinanceOfficePhone.setText(specialist.getOfficePhone());
                    txtFinancePracticeName.setText(specialist.getPracticeName());
                    txtFinanceNote.setText(specialist.getNote());
                    txtOtherCategory.setText(specialist.getOtherCategory());
                    id = specialist.getId();
                    int index = 0;
                    for (int i = 0; i < HospitalType.length; i++) {
                        if (specialist.getCategory().equals(HospitalType[i])) {
                            index = i;
                        }
                    }
                    spinnerFinance.setSelection(index + 1);

                    byte[] photo = specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;


            case "FinanceData":
                visiFinance();
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);
                txtAdd.setText("Update Finance and Legal");
                txtTitle.setText("Update Finance and Legal");
                Intent financeIntent = getActivity().getIntent();
                if (financeIntent.getExtras() != null) {
                    Finance specialist = (Finance) financeIntent.getExtras().getSerializable("FinanceObject");

                    txtFName.setText(specialist.getName());
                    txtFinanceOtherPhone.setText(specialist.getOtherPhone());
                    txtLastSeen.setText(specialist.getLastseen());
                    txtFinanceAddress.setText(specialist.getAddress());
                    txtFinanceWebsite.setText(specialist.getWebsite());
                    txtFinanceFax.setText(specialist.getFax());
                    txtFinanceMobilePhone.setText(specialist.getHourPhone());
                    txtFinanceOfficePhone.setText(specialist.getOfficePhone());
                    txtFinancePracticeName.setText(specialist.getPracticeName());
                    txtFinanceNote.setText(specialist.getNote());
                    txtOtherCategory.setText(specialist.getOtherCategory());
                    id = specialist.getId();
                    int index = 0;
                    for (int i = 0; i < financeType.length; i++) {
                        if (specialist.getCategory().equals(financeType[i])) {
                            index = i;
                        }
                    }
                    spinnerFinance.setSelection(index + 1);

                    byte[] photo = specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;

            case "FinanceViewData":
                visiFinance();
                disableFinance();
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);
                txtTitle.setText("Finance and Legal");
                txtTitle.setVisibility(View.VISIBLE);
                Intent financeIntentd = getActivity().getIntent();
                if (financeIntentd.getExtras() != null) {
                    Finance specialist = (Finance) financeIntentd.getExtras().getSerializable("FinanceObject");

                    txtFName.setText(specialist.getName());
                    txtFinanceOtherPhone.setText(specialist.getOtherPhone());
                    txtLastSeen.setText(specialist.getLastseen());
                    txtFinanceAddress.setText(specialist.getAddress());
                    txtFinanceWebsite.setText(specialist.getWebsite());
                    txtFinanceFax.setText(specialist.getFax());
                    txtFinanceMobilePhone.setText(specialist.getHourPhone());
                    txtFinanceOfficePhone.setText(specialist.getOfficePhone());
                    txtFinancePracticeName.setText(specialist.getPracticeName());
                    txtFinanceNote.setText(specialist.getNote());
                    txtOtherCategory.setText(specialist.getOtherCategory());
                    id = specialist.getId();
                    int index = 0;
                    for (int i = 0; i < financeType.length; i++) {
                        if (specialist.getCategory().equals(financeType[i])) {
                            index = i;
                        }
                    }
                    spinnerFinance.setSelection(index + 1);

                    byte[] photo = specialist.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

                    //Change Class Name
                    byte[] photoCard = specialist.getPhotoCard();
                    Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
                    imgCard.setImageBitmap(bmpCard);
                }
                break;


        }

    }

    private void getSContact() {
        txtDoctorName.setText(Cname);
       // txtEmail.setText(email);
        try {
            String mobile = "";
            mobile = Cphone;
            String code = mobile.substring(0, 3);
            mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
            txtDoctorOfficePhone.setText(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getContact() {
        txtName.setText(Cname);
        txtEmail.setText(Cemail);
        try {
            String mobile = "";
            mobile = Cphone;
            String code = mobile.substring(0, 3);
            mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
            txtMobile.setText(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disablePharmacy() {
        txtPharmacyName.setEnabled(false);
        txtPharmacyAddress.setEnabled(false);
        txtPharmacyWebsite.setEnabled(false);
        txtPharmacyFax.setEnabled(false);
        txtPharmacyPhone.setEnabled(false);
        txtPharmacyNote.setEnabled(false);
        imgEdit.setVisibility(View.GONE);
        llAddConn.setVisibility(View.GONE);
    }

    private void disableProxy() {
        txtName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        txtEmergencyNote.setEnabled(false);
        spinnerRelation.setClickable(false);

        imgEdit.setVisibility(View.GONE);
        llAddConn.setVisibility(View.GONE);
    }

    private void disableEmergency() {
        txtName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        txtEmergencyNote.setEnabled(false);
        spinnerRelation.setClickable(false);
        spinnerPriority.setClickable(false);
        imgEdit.setVisibility(View.GONE);
        llAddConn.setVisibility(View.GONE);
    }

    private void visiPharmacy() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        tilEmergencyNote.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.VISIBLE);

        tilPharmacyName.setHintEnabled(false);
        txtPharmacyName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilPharmacyName.setHintEnabled(true);
                txtPharmacyName.setFocusable(true);

                return false;
            }
        });
    }

    private void visiProxy() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.VISIBLE);

        rlConnection.setVisibility(View.VISIBLE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlProxy.setVisibility(View.VISIBLE);
        spinnerProxy.setVisibility(View.VISIBLE);
        rlAids.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        tilName.setHint("First Name, Last Name");
        tilName.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });
        tilEmergencyNote.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiEmergency() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.VISIBLE);
        rlConnection.setVisibility(View.VISIBLE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlFinance.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);
        tilName.setHint("First Name, Last Name");
        tilName.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });

        tilEmergencyNote.setVisibility(View.VISIBLE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void disableFinance() {
        txtFName.setEnabled(false);
        txtFinanceOtherPhone.setEnabled(false);
        txtLastSeen.setEnabled(false);
        txtFinanceAddress.setEnabled(false);
        txtFinanceWebsite.setEnabled(false);
        txtFinanceFax.setEnabled(false);
        txtFinanceMobilePhone.setEnabled(false);
        txtFinanceOfficePhone.setEnabled(false);
        txtFinancePracticeName.setEnabled(false);
        txtFinanceNote.setEnabled(false);
        spinnerFinance.setClickable(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);

    }

    private void disableAides() {
        txtAideCompName.setEnabled(false);
        txtAideOfficePhone.setEnabled(false);
        txtHourOfficePhone.setEnabled(false);
        txtOtherPhone.setEnabled(false);
        txtAideFax.setEnabled(false);
        txtAideEmail.setEnabled(false);
        txtAideAddress.setEnabled(false);
        txtAideWebsite.setEnabled(false);
        txtAideWebsite.setEnabled(false);
        txtAideNote.setEnabled(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);

    }

    private void disableInsurance() {
        txtInsuarancePhone.setEnabled(false);
        txtId.setEnabled(false);
        txtGroup.setEnabled(false);
        txtInsuaranceFax.setEnabled(false);
        txtInsuaranceEmail.setEnabled(false);
        txtAgent.setEnabled(false);
        txtWebsite.setEnabled(false);
        txtInsuaranceNote.setEnabled(false);
        txtInsuaranceName.setEnabled(false);
        txtSubscribe.setEnabled(false);
        spinnerInsuarance.setClickable(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);

    }

    private void disableSpecialist() {
        txtDoctorName.setEnabled(false);
        txtDoctorOtherPhone.setEnabled(false);
        txtDoctorLastSeen.setEnabled(false);
        txtDoctorAddress.setEnabled(false);
        txtDoctorWebsite.setEnabled(false);
        txtDoctorFax.setEnabled(false);
        txtDoctorHourOfficePhone.setEnabled(false);
        txtDoctorOfficePhone.setEnabled(false);
        txtAffiliation.setEnabled(false);
        txtPracticeName.setEnabled(false);
        txtNetwork.setEnabled(false);
        txtDoctorNote.setEnabled(false);
        spinner.setClickable(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);

    }

    private void visiFinance() {
        rlTop.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.GONE);
        rlFinance.setVisibility(View.VISIBLE);
        spinnerRelation.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlFinance.setVisibility(View.VISIBLE);
        rlProxy.setVisibility(View.GONE);

        tilFName.setHint("Name");
       tilFName.setHintEnabled(false);
        txtFName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilFName.setHintEnabled(true);
                txtFName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiAides() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.VISIBLE);
        rlFinance.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

        tilAideCompName.setHintEnabled(false);
        txtAideCompName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilAideCompName.setHintEnabled(true);
                txtAideCompName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiSpecialist() {
        rlTop.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        rlFinance.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.VISIBLE);
        rlInsurance.setVisibility(View.GONE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

        txtAdd.setText("Add Doctor");
        txtTitle.setText("Add Doctor");
        tilDoctorName.setHintEnabled(false);

        txtDoctorName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilDoctorName.setHintEnabled(true);
                txtDoctorName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void visiInsurance() {
        rlTop.setVisibility(View.GONE);
        rlCommon.setVisibility(View.GONE);
        spinnerRelation.setVisibility(View.GONE);
        rlConnection.setVisibility(View.GONE);
        rlDoctor.setVisibility(View.GONE);
        rlInsurance.setVisibility(View.VISIBLE);
        rlAids.setVisibility(View.GONE);
        rlProxy.setVisibility(View.GONE);

        tilInsuaranceName.setHintEnabled(false);
        txtInsuaranceName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilInsuaranceName.setHintEnabled(true);
                txtInsuaranceName.setFocusable(true);

                return false;
            }
        });
        rlPharmacy.setVisibility(View.GONE);
    }

    private void initListener() {
        llAddConn.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgEditCard.setOnClickListener(this);
        imgCard.setOnClickListener(this);
    }

    private void initUI() {

        //Doctor
        txtDoctorName = (TextView) rootview.findViewById(R.id.txtDoctorName);
        txtDoctorOfficePhone = (TextView) rootview.findViewById(R.id.txtDoctorOfficePhone);
        txtDoctorHourOfficePhone = (TextView) rootview.findViewById(R.id.txtDoctorHourOfficePhone);
        txtDoctorOtherPhone = (TextView) rootview.findViewById(R.id.txtDoctorOtherPhone);
        txtDoctorFax = (TextView) rootview.findViewById(R.id.txtDoctorFax);
        txtDoctorWebsite = (TextView) rootview.findViewById(R.id.txtDoctorWebsite);
        txtDoctorAddress = (TextView) rootview.findViewById(R.id.txtDoctorAddress);
        txtDoctorLastSeen = (TextView) rootview.findViewById(R.id.txtDoctorLastSeen);
        txtOtherInsurance= (TextView) rootview.findViewById(R.id.txtOtherInsurance);

        //Pharmacy
        txtPharmacyName = (TextView) rootview.findViewById(R.id.txtPharmacyName);
        txtPharmacyAddress = (TextView) rootview.findViewById(R.id.txtPharmacyAddress);
        txtPharmacyPhone = (TextView) rootview.findViewById(R.id.txtPharmacyPhone);
        txtPharmacyFax = (TextView) rootview.findViewById(R.id.txtPharmacyFax);
        txtPharmacyWebsite = (TextView) rootview.findViewById(R.id.txtPharmacyWebsite);
        txtPharmacyNote = (TextView) rootview.findViewById(R.id.txtPharmacyNote);



        //Aides
        txtAideCompName = (TextView) rootview.findViewById(R.id.txtAideCompName);
        txtAideOfficePhone = (TextView) rootview.findViewById(R.id.txtAideOfficePhone);
        txtHourOfficePhone = (TextView) rootview.findViewById(R.id.txtHourOfficePhone);
        txtOtherPhone = (TextView) rootview.findViewById(R.id.txtOtherPhone);
        txtAideFax = (TextView) rootview.findViewById(R.id.txtAideFax);
        txtAideEmail = (TextView) rootview.findViewById(R.id.txtAideEmail);
        txtAideWebsite = (TextView) rootview.findViewById(R.id.txtAideWebsite);
        txtAideNote = (TextView) rootview.findViewById(R.id.txtAideNote);
        txtAideAddress= (TextView) rootview.findViewById(R.id.txtAideAddress);

        //Finance
        txtFinanceOfficePhone = (TextView) rootview.findViewById(R.id.txtFinanceOfficePhone);
        txtFinanceMobilePhone = (TextView) rootview.findViewById(R.id.txtFinanceMobilePhone);
        txtFinanceOtherPhone = (TextView) rootview.findViewById(R.id.txtFinanceOtherPhone);
        txtFinanceFax = (TextView) rootview.findViewById(R.id.txtFinanceFax);
        txtFinanceAddress = (TextView) rootview.findViewById(R.id.txtFinanceAddress);
        txtFinanceWebsite = (TextView) rootview.findViewById(R.id.txtFinanceWebsite);
        txtFinancePracticeName = (TextView) rootview.findViewById(R.id.txtFinancePracticeName);
        txtLastSeen = (TextView) rootview.findViewById(R.id.txtLastSeen);
        txtFinanceNote= (TextView) rootview.findViewById(R.id.txtFinanceNote);

        // Insurance
        txtSubscribe = (TextView) rootview.findViewById(R.id.txtSubscribe);
        txtInsuaranceFax = (TextView) rootview.findViewById(R.id.txtInsuaranceFax);
        txtInsuaranceEmail = (TextView) rootview.findViewById(R.id.txtInsuaranceEmail);
        txtAgent= (TextView) rootview.findViewById(R.id.txtAgent);
        txtWebsite = (TextView) rootview.findViewById(R.id.txtWebsite);
        txtInsuaranceNote = (TextView) rootview.findViewById(R.id.txtInsuaranceNote);

        txtTitle= (TextView) getActivity().findViewById(R.id.txtTitle);
        llAddConn = (RelativeLayout) rootview.findViewById(R.id.llAddConn);
        rlTop = (RelativeLayout) rootview.findViewById(R.id.rlTop);

        tilOtherCategory = (TextInputLayout) rootview.findViewById(R.id.tilOtherCategory);
        tilOtherCategory.setHint("Other Category");
        txtOtherCategory = (TextView) rootview.findViewById(R.id.txtOtherCategory);
        tilOtherInsurance = (TextInputLayout) rootview.findViewById(R.id.tilOtherInsurance);

        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtEmail = (TextView) rootview.findViewById(R.id.txtEmail);
        txtMobile = (TextView) rootview.findViewById(R.id.txtMobile);
        txtEmergencyNote = (TextView) rootview.findViewById(R.id.txtEmergencyNote);
        txtMobile.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtMobile.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtHomePhone= (TextView) rootview.findViewById(txtPhone);
        txtWorkPhone= (TextView) rootview.findViewById(R.id.txtOfficePhone);
        rlRelation = (RelativeLayout) rootview.findViewById(R.id.rlRelation);
        rlProxy= (RelativeLayout) rootview.findViewById(R.id.rlProxy);
        txtAdd = (TextView) rootview.findViewById(R.id.txtAdd);
        imgEdit = (ImageView) rootview.findViewById(R.id.imgEdit);
        imgEditCard = (ImageView) rootview.findViewById(R.id.imgEditCard);
        imgProfile = (ImageView) rootview.findViewById(R.id.imgProfile);
        imgCard = (ImageView) rootview.findViewById(R.id.imgCard);
        spinner = (MySpinner) rootview.findViewById(R.id.spinner);
        spinnerInsuarance = (MySpinner) rootview.findViewById(R.id.spinnerInsuarance);
        spinnerFinance = (MySpinner) rootview.findViewById(R.id.spinnerFinance);
        spinnerProxy= (MySpinner) rootview.findViewById(R.id.spinnerProxy);
        spinnerRelation= (MySpinner) rootview.findViewById(R.id.spinnerRelation);
        spinnerPriority= (MySpinner) rootview.findViewById(R.id.spinnerPriority);
        txtOtherRelation=(TextView)rootview.findViewById(R.id.txtOtherRelation);

        txtHomePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHomePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        tilName = (TextInputLayout) rootview.findViewById(R.id.tilName);
        tilPharmacyName = (TextInputLayout) rootview.findViewById(R.id.tilPharmacyName);
        tilFName= (TextInputLayout) rootview.findViewById(R.id.tilFName);
        tilAideCompName = (TextInputLayout) rootview.findViewById(R.id.tilAideCompName);
        tilDoctorName= (TextInputLayout) rootview.findViewById(R.id.tilDoctorName);
        tilInsuaranceName= (TextInputLayout) rootview.findViewById(R.id.tilInsuaranceName);
        tilEmergencyNote= (TextInputLayout) rootview.findViewById(R.id.tilEmergencyNote);
        tilOtherRelation= (TextInputLayout) rootview.findViewById(R.id.tilOtherRelation);
        tilOtherRelation.setHint("Other Relation");
        txtWorkPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtWorkPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtAddress = (TextView) rootview.findViewById(R.id.txtAddress);
        txtPracticeName = (TextView) rootview.findViewById(R.id.txtPracticeName);
        txtFax = (TextView) rootview.findViewById(R.id.txtFax);
        txtNetwork = (TextView) rootview.findViewById(R.id.txtNetwork);
        txtAffiliation = (TextView) rootview.findViewById(R.id.txtAffiliation);
        txtDoctorNote = (TextView) rootview.findViewById(R.id.txtDoctorNote);

        txtPharmacyPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtPharmacyPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtAideOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtAideOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtHourOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHourOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtFinanceOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtFinanceMobilePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceMobilePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });

        txtFinanceOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtFinanceOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtFName = (TextView) rootview.findViewById(R.id.txtFName);
        txtAids = (TextView) rootview.findViewById(R.id.txtAids);
        txtSchedule = (TextView) rootview.findViewById(R.id.txtSchedule);
        txtOther = (TextView) rootview.findViewById(R.id.txtOther);
        txtDoctorOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtDoctorHourOfficePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorHourOfficePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        txtDoctorOtherPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtDoctorOtherPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        rlConnection = (RelativeLayout) rootview.findViewById(R.id.rlConnection);
        rlDoctor = (RelativeLayout) rootview.findViewById(R.id.rlDoctor);
        rlInsurance = (RelativeLayout) rootview.findViewById(R.id.rlInsurance);
        rlCommon = (RelativeLayout) rootview.findViewById(R.id.rlCommon);
        rlAids = (RelativeLayout) rootview.findViewById(R.id.rlAids);
        rlFinance = (RelativeLayout) rootview.findViewById(R.id.rlFinance);
        rlPharmacy = (RelativeLayout) rootview.findViewById(R.id.rlPharmacy);


        txtInsuaranceName = (TextView) rootview.findViewById(R.id.txtInsuaranceName);
        txtId = (TextView) rootview.findViewById(R.id.txtId);
        txtGroup = (TextView) rootview.findViewById(R.id.txtGroup);
        txtMember = (TextView) rootview.findViewById(R.id.txtMember);
        txtInsuarancePhone = (TextView) rootview.findViewById(R.id.txtInsuarancePhone);

          txtInsuarancePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtInsuarancePhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });
        gridRelation = (StaggeredTextGridView) rootview.findViewById(R.id.gridRelation);
        setRelationData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, healthSpeciality);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Specialty");

        String sources=preferences.getString(PrefConstants.SOURCE);
        if(sources.equals("Finance")||sources.equals("FinanceViewData")||sources.equals("FinanceData")) {
            ArrayAdapter<String> financeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, financeType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFinance.setAdapter(financeadapter);
            spinnerFinance.setHint("Category");
        }
        else{
            ArrayAdapter<String> financeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, HospitalType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerFinance.setAdapter(financeadapter);
            spinnerFinance.setHint("Category");
        }

        ArrayAdapter<String> adapterInsuarance = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, insuaranceType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInsuarance.setAdapter(adapterInsuarance);
        spinnerInsuarance.setHint("Type of Insurance");

        ArrayAdapter<String> adapterProxy = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, proxyType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProxy.setAdapter(adapterProxy);
        spinnerProxy.setHint("Proxy Agent Priority");
        txtTitle.setAllCaps(true);  txtAdd.setAllCaps(true);

        ArrayAdapter<String> adapterPriority= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, priorityType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapterPriority);
        spinnerPriority.setHint("Priority");
        txtTitle.setAllCaps(true);
        txtAdd.setAllCaps(true);

        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).toString().equals("Other"))
            {
                tilOtherRelation.setVisibility(View.VISIBLE);
            }
            else{
                tilOtherRelation.setVisibility(View.GONE);
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    });

        spinnerFinance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other"))
                {
                    tilOtherCategory.setVisibility(View.VISIBLE);
                }
                else{
                    tilOtherCategory.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        spinnerInsuarance.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other"))
                {
                    tilOtherInsurance.setVisibility(View.VISIBLE);
                }
                else{
                    tilOtherInsurance.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void setRelationData() {
        relationAdapter = new RelationAdapter(getActivity(), relationArraylist);
        gridRelation.setAdapter(relationAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddConn:
                Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_SHORT).show();
               /* switch (source)
                {
                    case "Connections":
                        if (validate("Connection")) {
                          //  dialogManager = new DialogManager(new FragmentNewContact());
                          //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Speciality":
                        break;
                }
*/
              /*  if (source.equals("Connection")) {
                    if (validate("Connection")) {
                        dialogManager = new DialogManager(new FragmentNewContact());
                        dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                    }
                } else if (source.equals("Speciality")) {
                    if (validate("Speciality")) {
                        dialogManager = new DialogManager(new FragmentNewContact());
                        dialogManager.showCommonDialog("Save?", "Do you want to save Doctor?", getActivity(), "ADD_DOCTOR", null);
                    }
                }*/

                break;
            case R.id.txtAdd:
                //Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_SHORT).show();
                switch (source)
                {
                    case "Connection":
                        if (validate("Connection")) {

                                Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] photo = baos.toByteArray();
                                Boolean flag= MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.USER_ID),name,email,address,mobile,phone,workphone,relation,photo,"",1,2,otherRelation,photoCard);
                                if (flag==true)
                                {
                                    Toast.makeText(getActivity(),"You have added connection Successfully",Toast.LENGTH_SHORT).show();
                                    /*Intent signupIntent = new Intent(context, LoginActivity.class);
                                    startActivity(signupIntent);*/
                                    getActivity().finish();
                                }
                                else{
                                    Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                                }
                  /*  */

                            Toast.makeText(getActivity(),"Succesas",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Emergency":
                    if (validate("Emergency")) {

                        Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] photo = baos.toByteArray();

                        Boolean flag= MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,email,address,mobile,phone,workphone,relation,photo,note,2,prior, otherRelation, photoCard);
                        if (flag==true)
                        {
                            Toast.makeText(getActivity(),"You have added emergency contact successfully",Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(getActivity(),"Succesas",Toast.LENGTH_SHORT).show();
                        //  dialogManager = new DialogManager(new FragmentNewContact());
                        //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                    }
                    break;
                    case "EmergencyUpdate":
                        if (validate("Emergency")) {

                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();

                            Boolean flag= MyConnectionsQuery.updateMyConnectionsData(id,name,email,address,mobile,phone,workphone,relation,photo,note,2,prior,otherRelation,"", "", "", "", "", "", "", "", "", "", "", "",photoCard, "");
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated emergency contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Succesas",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    case "Proxy":
                        if (validate("Proxy")) {

                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,email,address,mobile,phone,workphone,relation,photo,note,3,prox, otherRelation, photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added proxy contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "ProxyUpdate":
                        if (validate("Proxy")) {

                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= MyConnectionsQuery.updateMyConnectionsData(id,name,email,address,mobile,phone,workphone,relation,photo,note,3,prox,otherRelation, "", "", "", "", "", "", "", "", "", "", "", "", photoCard, "");
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated proxy contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Physician":

                       if (validate("Physician")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= SpecialistQuery.insertPhysicianData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,network,affil,note,1,lastseen,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added physician contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Speciality":

                        if (validate("Physician")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= SpecialistQuery.insertPhysicianData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,network,affil,note,2,lastseen, photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added doctor contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    case "SpecialistData":
                        if (validate("Physician")) {


                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            if (isPhysician==1) {
                                Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, photo, fax, practice_name, network, affil, note, 1, lastseen,photoCard);
                                if (flag == true) {
                                    Toast.makeText(getActivity(), "You have updated physician contact successfully", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            }
                            else if(isPhysician==2)
                            {
                                Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, photo, fax, practice_name, network, affil, note, 2, lastseen, photoCard);
                                if (flag == true) {
                                    Toast.makeText(getActivity(), "You have updated doctor successfully", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                            }
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    case "Pharmacy":

                        if (validate("Pharmacy")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= PharmacyQuery.insertPharmacyData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,phone,photo,fax,note,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added pharmacy successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    case "PharmacyData":

                        if (validate("Pharmacy")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= PharmacyQuery.updatePharmacyData(id,name,website,address,phone,photo,fax,note,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated pharmacy successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Aides":

                        if (validate("Aides")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= AideQuery.insertAidesData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,email,mobile,phone,workphone,photo,fax,note,address,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added Health Service successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    case "AidesData":

                        if (validate("Aides")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= AideQuery.updateAideData(id,name,website,email,mobile,phone,workphone,photo,fax,note,address,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated Health Service successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;

                    case "Hospital":

                        if (validate("Finance")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= HospitalHealthQuery.insertHospitalHealthData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,note,lastseen,otherCategory,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case "HospitalData":

                        if (validate("Finance")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= HospitalHealthQuery.updateHospitalHealthData(id,name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,note,lastseen,otherCategory,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Finance":

                        if (validate("Finance")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= FinanceQuery.insertFinanceData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,note,lastseen,otherCategory,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "FinanceData":

                        if (validate("Finance")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= FinanceQuery.updateFinanceData(id,name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,note,lastseen,otherCategory,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated contact successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case "Insurance":

                        if (validate("Insurance")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= InsuranceQuery.insertInsuranceData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,type,phone,photo,fax,note,member,group,subscriber,email,otherInsurance,agent,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have added insurance information successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    case "InsuranceData":

                        if (validate("Insurance")) {
                            Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] photo = baos.toByteArray();
                            Boolean flag= InsuranceQuery.updateInsuranceData(id,name,website,type,phone,photo,fax,note,member,group,subscriber,email,otherInsurance,agent,photoCard);
                            if (flag==true)
                            {
                                Toast.makeText(getActivity(),"You have updated insurance information successfully",Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                            //  dialogManager = new DialogManager(new FragmentNewContact());
                            //  dialogManager.showCommonDialog("Save?", "Do you want to save Connection?", getActivity(), "ADD_CONNECTION", null);
                        }
                        break;
                    // InsuranceObject
                }
                break;
            case R.id.imgEdit:
               ShowCameraDialog(RESULT_CAMERA_IMAGE,RESULT_SELECT_PHOTO);

                break;
            case R.id.imgEditCard:
                ShowCameraDialog(RESULT_CAMERA_IMAGE_CARD,RESULT_SELECT_PHOTO_CARD);
                break;
            case R.id.imgCard:
                Bitmap bitmaps = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
                ByteArrayOutputStream baoss = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 40, baoss);
                photoCard = baoss.toByteArray();
                Intent i=new Intent(getActivity(), AddFormActivity.class);
                i.putExtra("Image",photoCard);
                startActivity(i);
                break;


        }
    }

    private void ShowCameraDialog(final int resultCameraImageCard, final int resultSelectPhotoCard) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogview = lf.inflate(R.layout.dialog_gender, null);
        final TextView textOption1 = (TextView) dialogview.findViewById(R.id.txtOption1);
        final TextView textOption2 = (TextView) dialogview.findViewById(R.id.txtOption2);
        TextView textCancel = (TextView) dialogview.findViewById(R.id.txtCancel);
        textOption1.setText("Take Picture");
        textOption2.setText("Gallery");
        dialog.setContentView(dialogview);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(resultCameraImageCard);
                dialog.dismiss();
            }
        });
        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, resultSelectPhotoCard);
                dialog.dismiss();
            }
        });
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private boolean validate(String screen) {
        Bitmap bitmaps = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
        ByteArrayOutputStream baoss = new ByteArrayOutputStream();
        bitmaps.compress(Bitmap.CompressFormat.JPEG, 40, baoss);
        photoCard = baoss.toByteArray();
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        mobile = txtMobile.getText().toString().trim();
        phone=txtHomePhone.getText().toString().trim();
        workphone=txtWorkPhone.getText().toString().trim();
        address=txtAddress.getText().toString().trim();
        int indexValue = spinnerRelation.getSelectedItemPosition();

        if (screen.equals("Connection")) {
            if (indexValue!=0) {
                relation = Relationship[indexValue - 1];
            }
            otherRelation=txtOtherRelation.getText().toString();
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            } else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", getActivity());
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", getActivity());
            } /*else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            }*/ else if (mobile.length()!=0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            } /*else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", getActivity());
            } else if (relation.equals("")) {
               // txtAddress.setError("Please Enter Address");
                showAlert("Please select relation", getActivity());
            }*/ else return true;
        } else if (screen.equals("Speciality")) {
            if (name.equals("")) {
                txtName.setError("Please Enter Doctor Name");
                showAlert("Please Enter Name", getActivity());
            } /*else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            }*/ else if (mobile.length()!=0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());

            } else return true;
        }
        else if(screen.equals("Emergency"))
        {

            int indexs=spinnerPriority.getSelectedItemPosition();
            if(indexValue!=0) {
                relation = Relationship[indexValue - 1];
            }
            if (indexs!=0) {
                priority = priorityType[indexs - 1];
            }
            otherRelation=txtOtherRelation.getText().toString();
            note=txtEmergencyNote.getText().toString().trim();
            if (priority.equals("Primary"))
            {
                prior=0;
            }
            else
            {
                prior=1;
            }
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            } /*else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", getActivity());
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", getActivity());
            } else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            }*/ else if (mobile.length()!=0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
           /* else if (phone.equals("")) {
                txtHomePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
                txtHomePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }else if (workphone.equals("")) {
                txtWorkPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (workphone.length() < 10) {
                txtWorkPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", getActivity());
            }*/ else return true;
        }
        else if(screen.equals("Proxy"))
        {
            if (indexValue!=0) {
                relation = Relationship[indexValue - 1];
            }
            otherRelation=txtOtherRelation.getText().toString();
            int indexValues = spinnerProxy.getSelectedItemPosition();
            if (indexValues!=0) {
                proxy = proxyType[indexValues - 1];
            }
            if (proxy.equals("Primary")) {
                prox=0;
            }else
            {
                prox=1;
            }
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            }/* else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", getActivity());
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", getActivity());
            }
            else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            }*/ else if (mobile.length()!=0 && mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }else return true;
           /* else if (phone.equals("")) {
                txtHomePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
                txtHomePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (workphone.equals("")) {
                txtWorkPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (workphone.length() < 10) {
                txtWorkPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", getActivity());
            }*/
        }
        else if (screen.equals("Physician")) {
            name=txtDoctorName.getText().toString();
            mobile=txtDoctorOfficePhone.getText().toString();
            phone=txtDoctorHourOfficePhone.getText().toString();
            workphone=txtDoctorOtherPhone.getText().toString();
            fax=txtDoctorFax.getText().toString();
            address=txtDoctorAddress.getText().toString();
            website=txtDoctorWebsite.getText().toString();
            lastseen=txtDoctorLastSeen.getText().toString();
            fax=txtDoctorFax.getText().toString();
            int indexValuex = spinner.getSelectedItemPosition();
            if (indexValuex!=0) {
                speciality = healthSpeciality[indexValuex - 1];
            }
            practice_name=txtPracticeName.getText().toString();
            network=txtNetwork.getText().toString();
            affil=txtAffiliation.getText().toString();
            note=txtDoctorNote.getText().toString();

            if (name.equals("")) {
                txtDoctorName.setError("Please Doctor Enter Name");
                showAlert("Please Enter Doctor Name", getActivity());
            }
             /* if (mobile.equals("")) {
                  txtDoctorOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            }*/ else if (mobile.length()!=0 && mobile.length() < 10) {
                  txtDoctorOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }else return true;
           /* else if (phone.equals("")) {
                  txtDoctorHourOfficePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
                  txtDoctorHourOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (workphone.equals("")) {
                  txtDoctorOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (workphone.length() < 10) {
                  txtDoctorOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }*/

        }
        else if (screen.equals("Pharmacy")) {
            name=txtPharmacyName.getText().toString();
            phone=txtPharmacyPhone.getText().toString();
            fax=txtPharmacyFax.getText().toString();
            address=txtPharmacyAddress.getText().toString();
            website=txtPharmacyWebsite.getText().toString();
            note=txtPharmacyNote.getText().toString();
            if (name.equals("")) {
                txtPharmacyName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            }
           /* if (phone.equals("")) {
                txtPharmacyPhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
                txtPharmacyPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }*/
            else return true;
        }
        else if (screen.equals("Aides")) {
            name=txtAideCompName.getText().toString();
            mobile=txtAideOfficePhone.getText().toString();
            phone=txtHourOfficePhone.getText().toString();
            workphone=txtOtherPhone.getText().toString();
            website=txtAideWebsite.getText().toString();
            note=txtAideNote.getText().toString();
            email=txtAideEmail.getText().toString();
            fax=txtAideFax.getText().toString();
            address=txtAideAddress.getText().toString();
            if (name.equals("")) {
                txtAideCompName.setError("Please Enter Name Of Company");
                showAlert("Please Enter Name Of Company", getActivity());
            }
         /*   if (mobile.equals("")) {
                txtAideOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            } */else if (mobile.length()!=0 && mobile.length() < 10) {
                txtAideOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }else return true;
           /* else if (phone.equals("")) {
                txtHourOfficePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
                txtHourOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (workphone.equals("")) {
                txtOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (workphone.length() < 10) {
                txtOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }*/

        }
        else if (screen.equals("Finance")) {

            name=txtFName.getText().toString();
            mobile=txtFinanceOfficePhone.getText().toString();
            phone=txtFinanceMobilePhone.getText().toString();
            workphone=txtFinanceOtherPhone.getText().toString();
            fax=txtFinanceFax.getText().toString();
            address=txtFinanceAddress.getText().toString();
            website=txtFinanceWebsite.getText().toString();
            lastseen=txtLastSeen.getText().toString();
            otherCategory=txtOtherCategory.getText().toString();
            int indexValuex = spinnerFinance.getSelectedItemPosition();
            String sources=preferences.getString(PrefConstants.SOURCE);
            if(sources.equals("Finance")||sources.equals("FinanceViewData")||sources.equals("FinanceData")) {
                if (indexValuex!=0) {
                    speciality = financeType[indexValuex - 1];
                }
            }
            else {
                if (indexValuex != 0) {
                    speciality = HospitalType[indexValuex - 1];
                }
            }
            practice_name=txtFinancePracticeName.getText().toString();
            note=txtFinanceNote.getText().toString();
            if (name.equals("")) {
                txtFName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            }

         /*   if (mobile.equals("")) {
                txtFinanceOfficePhone.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            } */else if (mobile.length()!=0 && mobile.length() < 10) {
                txtFinanceOfficePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            } else return true;
            /*else if (phone.equals("")) {
                txtFinanceMobilePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
                txtFinanceMobilePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (workphone.equals("")) {
                txtFinanceOtherPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (workphone.length() < 10) {
                txtFinanceOtherPhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }*/



        }
        else if (screen.equals("Insurance")) {
            name = txtInsuaranceName.getText().toString();
            phone = txtInsuarancePhone.getText().toString();
            fax = txtInsuaranceFax.getText().toString();
            address = txtAddress.getText().toString();
            website = txtWebsite.getText().toString();
            note = txtInsuaranceNote.getText().toString();
            member = txtId.getText().toString();
            group = txtGroup.getText().toString();
            subscriber = txtSubscribe.getText().toString();
            int indexValuex = spinnerInsuarance.getSelectedItemPosition();
            if (indexValuex != 0) {
                type = insuaranceType[indexValuex - 1];
            }
            email = txtInsuaranceEmail.getText().toString();
            agent=txtAgent.getText().toString();
            otherInsurance = txtOtherInsurance.getText().toString();
            if (name.equals("")) {
                txtInsuaranceName.setError("Please Enter Name of Insurance Company");
                showAlert("Please Enter Name of Insurance Company", getActivity());

            }
            /*else if (phone.equals("")) {
                txtInsuarancePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            }*/ else if (phone.length()!=0 && phone.length() < 10) {
                txtInsuarancePhone.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }else return true;
        }
            /* (phone.equals("")) {
                txtInsuarancePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            } else if (phone.length() < 10) {
            txtInsuarancePhone.setError("Mobile number should be 10 digits");
            showAlert("Mobile number should be 10 digits", getActivity());
        }*/


        return false;
    }

    private void dispatchTakePictureIntent(int resultCameraImageCard) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, resultCameraImageCard);
            }

        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    public void postCommonDialog() {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = (ImageView) rootview.findViewById(R.id.imgProfile);
        ImageView profileCard = (ImageView) rootview.findViewById(R.id.imgCard);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE && null != data) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWContact/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }


            /*if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }*/
            try {

                imagepath = path + "/MHCWContact_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }

        }else
        if (requestCode == RESULT_SELECT_PHOTO_CARD && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileCard.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE_CARD && null != data) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileCard.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileCard,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWContact/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }


            /*if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }*/
            try {

                imagepath = path + "/MHCWContact_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }

        }

    }

    public void callRelation(String relationship) {
        relation=relationship;
    }
}
