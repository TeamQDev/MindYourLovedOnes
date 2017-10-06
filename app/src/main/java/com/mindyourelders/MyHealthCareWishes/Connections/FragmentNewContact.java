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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.AideQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DoctorQuery;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PharmacyQuery;
import com.mindyourelders.MyHealthCareWishes.database.SpecialistQuery;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
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

import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentNewContact extends Fragment implements View.OnClickListener {

    //TextView btnShowMore,btnShowLess,btnSon;
    TextView txtName, txtEmail, txtMobile,txtHomePhone,txtWorkPhone, txtAdd, txtInsuaranceName, txtInsuarancePhone, txtId, txtGroup, txtMember, txtAddress;

    TextView txtPracticeName, txtFax, txtNetwork, txtAffiliation,txtDoctorNote,txtDoctorName,txtDoctorOfficePhone,txtDoctorHourOfficePhone,txtDoctorOtherPhone,txtDoctorFax,txtDoctorWebsite;
    TextView txtDoctorAddress,txtDoctorLastSeen;

    TextView txtAids, txtSchedule, txtOther,txtFName,txtEmergencyNote;
TextView txtPharmacyName,txtPharmacyAddress,txtPharmacyPhone,txtPharmacyFax,txtPharmacyWebsite,txtPharmacyNote;
    TextView txtAideAddress,txtAideCompName,txtAideOfficePhone,txtHourOfficePhone,txtOtherPhone,txtAideFax,txtAideEmail,txtAideWebsite,txtAideNote;
    TextView txtTitle;
    ImageView imgEdit, imgProfile;
    View rootview;
    RelativeLayout rlRelation, rlConnection, rlDoctor, rlInsurance, rlCommon, rlAids, rlFinance,rlProxy, rlTop,llAddConn,rlPharmacy;
    Preferences preferences;
    String source;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;

    String name, email, mobile, speciality,phone,address,workphone,note;
    String network,affil,practice_name,website,lastseen;
    String fax="";
    String relation="";
    String proxy="";
    int prox;
    int connectionFlag;
    boolean inPrimary;
    MySpinner spinner, spinnerInsuarance, spinnerFinance,spinnerProxy,spinnerRelation;
    TextInputLayout tilName,tilFName,tilEmergencyNote;

    StaggeredTextGridView gridRelation;
    ArrayList<String> relationArraylist;
    RelationAdapter relationAdapter;

    DialogManager dialogManager;

    String imagepath = "";

    String[] Relationship = {"Mother", "Father", "Wife", "Husband", "Daughter", "Son", "Sister", "Brother", "Friend", "GrandFather", "GrandMother", "GrandSon", "GrandDaughter", "Other"};

    String[] healthSpeciality = {"FAMILY PRACTICE PHYSICIAN", "NEUROLOGIST", "ORTHOPEDICS", "AUDIOLOGIST", "CARDIOLOGIST", "ENDOCRINOLOGISTS", "GERIATRICIAN", "GERIATRIC PSYCHIATRIST", "INTERNIST", "NEPHROLOGIST", "NUTRITIONIST", "OPHTHALMOLOGIST", "PHARMACIST", "PHYSICAL THERAPISTS", "PULMONOLOGIST", "RHEUMATOLOGISTIS", "SPEECH LANGUAGE PATHOLOGIST", "UROLOGIST"};

    String[] insuaranceType = {"Dental", "Medical", "Medicare", "Medicare Supplement (Medigap)", "Medicaid", "Disability", "Long Term Care", "Supplemental", "Other"};

    String[] financeType = {"Accountant", "Attorney", "Financial Planner", "Insurance Broker", "Stock Broker", "Trustee"};

    String[] proxyType = {"Primary", "Secondary"};

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
            String name = bundle.getString("Name");
            String email = bundle.getString("Email");
            String phone = bundle.getString("Phone");
            byte[] image = bundle.getByteArray("Photo");
            Bitmap photo = BitmapFactory.decodeByteArray(image, 0, image.length);

            imgProfile.setImageBitmap(photo);
            txtName.setText(name);
            txtEmail.setText(email);

            try {
                String mobile = "";
                mobile = phone;
                String code = mobile.substring(0, 3);
                mobile = mobile.substring(3, 6) + "-" + mobile.substring(6, 9) + "-" + mobile.substring(9, mobile.length());
                txtMobile.setText(mobile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
                txtAdd.setText("Add Connection");
                tilName.setHint("First Name, Last Name");
                txtTitle.setText("Add Connection");
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
                }
                    break;

            case "Proxy":
                visiProxy();
                txtAdd.setText("Add Proxy");
                txtTitle.setText("Add Proxy");
                break;

            case "ProxyUpdate":
                visiProxy();
                txtAdd.setText("Update Proxy");
                txtTitle.setText("Update Proxy");
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
                    id=rel.getId();
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (rel.getRelationType().equals(Relationship[i])) {
                            index = i;
                        }
                    }
                    spinnerRelation.setSelection(index+1);
                    prox=rel.getIsPrimary();
                    if (prox==1) {
                        spinnerProxy.setSelection(0);
                    }else if (prox==2)
                    {
                        spinnerProxy.setSelection(1);
                    }


                    byte[] photo=rel.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);
                }

                break;

            case "Emergency":
                visiEmergency();
                txtAdd.setText("Add Emergency");
                txtTitle.setText("Add Emergency Contact");
                break;
            case "EmergencyUpdate":
                visiEmergency();
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
                    id=rel.getId();
                        int index = 0;
                        for (int i = 0; i < Relationship.length; i++) {
                            if (rel.getRelationType().equals(Relationship[i])) {
                                index = i;
                            }
                        }

                    spinnerRelation.setSelection(index+1);
                    byte[] photo=rel.getPhoto();
                    Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                    imgProfile.setImageBitmap(bmp);

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

                }
                break;

            case "SpecialistViewData":
                visiSpecialist();
                disableSpecialist();
                Intent specialistViewIntent = getActivity().getIntent();
                if (specialistViewIntent.getExtras() != null) {
                    txtName.setText(specialistViewIntent.getExtras().getString("Name"));
                    // txtEmail.setText(specialistIntent.getExtras().getString());
                    txtMobile.setText(specialistViewIntent.getExtras().getString("Phone"));
                    txtAddress.setText(specialistViewIntent.getExtras().getString("Address"));

                    int index = 0;
                    for (int i = 0; i < healthSpeciality.length; i++) {
                        if (specialistViewIntent.getExtras().getString("Type").equals(healthSpeciality[i])) {
                            index = i;
                        }
                    }
                    spinner.setSelection(index);
                }
                break;

            case "Insurance":
                visiInsurance();
                break;

            case "InsuranceData":
                visiInsurance();
                Intent insuranceIntent = getActivity().getIntent();
                if (insuranceIntent.getExtras() != null) {
                    txtInsuaranceName.setText(insuranceIntent.getExtras().getString("Name"));
                    int index = 0;
                    for (int i = 0; i < insuaranceType.length; i++) {
                        if (insuranceIntent.getExtras().getString("Type").equalsIgnoreCase(insuaranceType[i])) {
                            index = i;
                        }
                    }
                    spinnerInsuarance.setSelection(index+1);
                    spinnerInsuarance.setDisabledColor(getActivity().getResources().getColor(R.color.colorBlack));
                    txtInsuarancePhone.setText(insuranceIntent.getExtras().getString("Phone"));
                    txtId.setText(insuranceIntent.getExtras().getString("Id"));
                    txtGroup.setText(insuranceIntent.getExtras().getString("Group"));
                    txtMember.setText(insuranceIntent.getExtras().getString("Member"));
                }
                break;

            case "InsuranceViewData":
                visiInsurance();
                disableInsurance();
                Intent insuranceViewIntent = getActivity().getIntent();
                if (insuranceViewIntent.getExtras() != null) {
                    txtInsuaranceName.setText(insuranceViewIntent.getExtras().getString("Name"));
                    String insuranceName=insuranceViewIntent.getExtras().getString("Type");
                    int index = 0;
                    for (int i = 0; i < insuaranceType.length; i++) {
                        if (insuranceName.equalsIgnoreCase(insuaranceType[i])) {
                            index = i;
                        }
                    }
                    spinnerInsuarance.setSelection(index+1);
                    txtInsuarancePhone.setText(insuranceViewIntent.getExtras().getString("Phone"));
                    txtId.setText(insuranceViewIntent.getExtras().getString("Id"));
                    txtGroup.setText(insuranceViewIntent.getExtras().getString("Group"));
                    txtMember.setText(insuranceViewIntent.getExtras().getString("Member"));
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
                    txtName.setText(aidesIntent.getExtras().getString("Name"));
                    txtMobile.setText(aidesIntent.getExtras().getString("Phone"));
                    txtAddress.setText(aidesIntent.getExtras().getString("Address"));
                    txtAids.setText(aidesIntent.getExtras().getString("AideName"));
                }
                break;

            case "AidesViewData":
                visiAides();
                disableAides();
                Intent aidesViewIntent = getActivity().getIntent();
                if (aidesViewIntent.getExtras() != null) {
                    txtName.setText(aidesViewIntent.getExtras().getString("Name"));
                    txtMobile.setText(aidesViewIntent.getExtras().getString("Phone"));
                    txtAddress.setText(aidesViewIntent.getExtras().getString("Address"));
                    txtAids.setText(aidesViewIntent.getExtras().getString("AideName"));
                }
                break;

            case "Finance":
                visiFinance();
                break;

            case "FinanceData":
                visiFinance();
                Intent finaceIntent = getActivity().getIntent();
                if (finaceIntent.getExtras() != null) {
                    int index = 0;
                    for (int i = 0; i < financeType.length; i++) {
                        if (finaceIntent.getExtras().getString("Category").equals(insuaranceType[i])) {
                            index = i;
                        }
                    }
                    spinnerFinance.setSelection(index);
                    txtFName.setText(finaceIntent.getExtras().getString("Firm"));
                    txtName.setText(finaceIntent.getExtras().getString("Name"));
                    txtMobile.setText(finaceIntent.getExtras().getString("Phone"));
                    txtAddress.setText(finaceIntent.getExtras().getString("Address"));
                }
                break;

            case "FinanceViewData":
                visiFinance();
                disableFinance();
                Intent finaceViewIntent = getActivity().getIntent();
                if (finaceViewIntent.getExtras() != null) {
                    int index = 0;
                    for (int i = 0; i < financeType.length; i++) {
                        if (finaceViewIntent.getExtras().getString("Category").equals(insuaranceType[i])) {
                            index = i;
                        }
                    }
                    spinnerFinance.setSelection(index);
                    txtFName.setText(finaceViewIntent.getExtras().getString("Firm"));
                    txtName.setText(finaceViewIntent.getExtras().getString("Name"));
                    txtMobile.setText(finaceViewIntent.getExtras().getString("Phone"));
                    txtAddress.setText(finaceViewIntent.getExtras().getString("Address"));
                }
                break;


        }

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
        tilEmergencyNote.setVisibility(View.VISIBLE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void disableFinance() {
        txtName.setEnabled(false);
        txtFName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        spinnerFinance.setEnabled(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void disableAides() {
        txtName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        txtAids.setEnabled(false);
        txtSchedule.setEnabled(false);
        txtOther.setEnabled(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void disableInsurance() {
        txtInsuaranceName.setEnabled(false);
        spinnerInsuarance.setClickable(false);
        txtInsuarancePhone.setEnabled(false);
        txtId.setEnabled(false);
        txtGroup.setEnabled(false);
        txtMember.setEnabled(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.GONE);
    }

    private void disableSpecialist() {
        txtName.setEnabled(false);
        txtEmail.setEnabled(false);
        txtMobile.setEnabled(false);
        txtHomePhone.setEnabled(false);
        txtWorkPhone.setEnabled(false);
        txtAddress.setEnabled(false);
        spinner.setEnabled(false);
        txtPracticeName.setEnabled(false);
        txtFax.setEnabled(false);
        txtNetwork.setEnabled(false);
        txtAffiliation.setEnabled(false);
        llAddConn.setVisibility(View.GONE);
        imgEdit.setVisibility(View.GONE);
        rlPharmacy.setVisibility(View.GONE);
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
        txtAdd.setText("Add Finane and Legal");
        txtTitle.setText("Add Finane and Legal");
        tilFName.setHint("Name");
        tilName.setHint("Name");
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


        tilName.setHint("Name of Firm");
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

        tilName.setHint("Name of Doctor");

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
        txtAdd.setText("Add Insurance");
        txtTitle.setText("Add Insurance");
        tilName.setHint("Name of Firm");
        rlPharmacy.setVisibility(View.GONE);
    }

    private void initListener() {
        llAddConn.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
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

        txtTitle= (TextView) getActivity().findViewById(R.id.txtTitle);
        llAddConn = (RelativeLayout) rootview.findViewById(R.id.llAddConn);
        rlTop = (RelativeLayout) rootview.findViewById(R.id.rlTop);
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
        txtHomePhone= (TextView) rootview.findViewById(R.id.txtPhone);
        txtWorkPhone= (TextView) rootview.findViewById(R.id.txtOfficePhone);
        rlRelation = (RelativeLayout) rootview.findViewById(R.id.rlRelation);
        rlProxy= (RelativeLayout) rootview.findViewById(R.id.rlProxy);
        txtAdd = (TextView) rootview.findViewById(R.id.txtAdd);
        imgEdit = (ImageView) rootview.findViewById(R.id.imgEdit);
        imgProfile = (ImageView) rootview.findViewById(R.id.imgProfile);
        spinner = (MySpinner) rootview.findViewById(R.id.spinner);
        spinnerInsuarance = (MySpinner) rootview.findViewById(R.id.spinnerInsuarance);
        spinnerFinance = (MySpinner) rootview.findViewById(R.id.spinnerFinance);
        spinnerProxy= (MySpinner) rootview.findViewById(R.id.spinnerProxy);
        spinnerRelation= (MySpinner) rootview.findViewById(R.id.spinnerRelation);
        tilName = (TextInputLayout) rootview.findViewById(R.id.tilName);
        tilFName= (TextInputLayout) rootview.findViewById(R.id.tilFName);
        tilEmergencyNote= (TextInputLayout) rootview.findViewById(R.id.tilEmergencyNote);
        txtAddress = (TextView) rootview.findViewById(R.id.txtAddress);
        txtPracticeName = (TextView) rootview.findViewById(R.id.txtPracticeName);
        txtFax = (TextView) rootview.findViewById(R.id.txtFax);
        txtNetwork = (TextView) rootview.findViewById(R.id.txtNetwork);
        txtAffiliation = (TextView) rootview.findViewById(R.id.txtAffiliation);
        txtDoctorNote = (TextView) rootview.findViewById(R.id.txtDoctorNote);

        txtFName = (TextView) rootview.findViewById(R.id.txtFName);
        txtAids = (TextView) rootview.findViewById(R.id.txtAids);
        txtSchedule = (TextView) rootview.findViewById(R.id.txtSchedule);
        txtOther = (TextView) rootview.findViewById(R.id.txtOther);

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

        gridRelation = (StaggeredTextGridView) rootview.findViewById(R.id.gridRelation);
        setRelationData();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relation");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, healthSpeciality);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Specialist");

        ArrayAdapter<String> financeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, financeType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFinance.setAdapter(financeadapter);
        spinnerFinance.setHint("Category");

        ArrayAdapter<String> adapterInsuarance = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, insuaranceType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInsuarance.setAdapter(adapterInsuarance);
        spinnerInsuarance.setHint("Type of Insurance");

        ArrayAdapter<String> adapterProxy = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, proxyType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProxy.setAdapter(adapterProxy);
        spinnerProxy.setHint("Proxy Type");
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
                                Boolean flag= MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.USER_ID),name,email,address,mobile,phone,workphone,relation,photo,"",1,2);
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

                        Boolean flag= MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,email,address,mobile,phone,workphone,relation,photo,note,2,2);
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

                            Boolean flag= MyConnectionsQuery.updateMyConnectionsData(id,name,email,address,mobile,phone,workphone,relation,photo,note,2,2);
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
                            Boolean flag= MyConnectionsQuery.insertMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,email,address,mobile,phone,workphone,relation,photo,note,3,prox);
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
                            Boolean flag= MyConnectionsQuery.updateMyConnectionsData(id,name,email,address,mobile,phone,workphone,relation,photo,note,3,prox);
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
                            Boolean flag= SpecialistQuery.insertPhysicianData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,network,affil,note,1,lastseen);
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
                            Boolean flag= SpecialistQuery.insertPhysicianData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,mobile,phone,workphone,speciality,photo,fax,practice_name,network,affil,note,2,lastseen);
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
                                Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, photo, fax, practice_name, network, affil, note, 1, lastseen);
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
                                Boolean flag = SpecialistQuery.updatePhysicianData(id, name, website, address, mobile, phone, workphone, speciality, photo, fax, practice_name, network, affil, note, 2, lastseen);
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
                            Boolean flag= PharmacyQuery.insertPharmacyData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,address,phone,photo,fax,note);
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
                            Boolean flag= PharmacyQuery.updatePharmacyData(id,name,website,address,phone,photo,fax,note);
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
                            Boolean flag= AideQuery.insertAidesData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,website,email,mobile,phone,workphone,photo,fax,note,address);
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
                }
                break;
            case R.id.imgEdit:
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
                        dispatchTakePictureIntent();
                        dialog.dismiss();
                    }
                });
                textOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_SELECT_PHOTO);
                        dialog.dismiss();
                    }
                });
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;
        }
    }

    private boolean validate(String screen) {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        mobile = txtMobile.getText().toString().trim();
        phone=txtHomePhone.getText().toString().trim();
        workphone=txtWorkPhone.getText().toString().trim();
        address=txtAddress.getText().toString().trim();
        int indexValue = spinnerRelation.getSelectedItemPosition();

        if (screen.equals("Connection")) {
            relation=Relationship[indexValue-1];
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            } else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", getActivity());
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", getActivity());
            } else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            } else if (mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            } else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", getActivity());
            } else if (relation.equals("")) {
               // txtAddress.setError("Please Enter Address");
                showAlert("Please select relation", getActivity());
            } else return true;
        } else if (screen.equals("Speciality")) {
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            } else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            } else if (mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            } else return true;
        }
        else if(screen.equals("Emergency"))
        {
            relation=Relationship[indexValue-1];
            note=txtEmergencyNote.getText().toString().trim();
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            } else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", getActivity());
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", getActivity());
            } else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            } else if (mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (phone.equals("")) {
                txtHomePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (workphone.equals("")) {
                txtWorkPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", getActivity());
            } else return true;
        }
        else if(screen.equals("Proxy"))
        {
            relation=Relationship[indexValue];
            int indexValues = spinnerProxy.getSelectedItemPosition();
            proxy=proxyType[indexValues];
            if (proxy.equals("Primary")) {
                prox=1;
            }else if (proxy.equals("Secondary"))
            {
                prox=2;
            }
            if (name.equals("")) {
                txtName.setError("Please Enter Name");
                showAlert("Please Enter Name", getActivity());
            } else if (email.equals("")) {
                txtEmail.setError("Please Enter email");
                showAlert("Please Enter email", getActivity());
            } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
                txtEmail.setError("Please enter valid email");
                showAlert("Please enter valid email", getActivity());
            } else if (mobile.equals("")) {
                txtMobile.setError("Please Enter Mobile");
                showAlert("Please Enter Mobile", getActivity());
            } else if (mobile.length() < 10) {
                txtMobile.setError("Mobile number should be 10 digits");
                showAlert("Mobile number should be 10 digits", getActivity());
            }
            else if (phone.equals("")) {
                txtHomePhone.setError("Please Enter Home Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (workphone.equals("")) {
                txtWorkPhone.setError("Please Enter Work Phone");
                showAlert("Please Enter Mobile", getActivity());
            }else if (address.equals("")) {
                txtAddress.setError("Please Enter Address");
                showAlert("Please Enter Address", getActivity());
            } else return true;
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
            speciality=healthSpeciality[indexValuex-1];
            practice_name=txtPracticeName.getText().toString();
            network=txtNetwork.getText().toString();
            affil=txtAffiliation.getText().toString();
            note=txtDoctorNote.getText().toString();
             return true;
        }
        else if (screen.equals("Pharmacy")) {
            name=txtPharmacyName.getText().toString();
            phone=txtPharmacyPhone.getText().toString();
            fax=txtPharmacyFax.getText().toString();
            address=txtPharmacyAddress.getText().toString();
            website=txtPharmacyWebsite.getText().toString();
            note=txtPharmacyNote.getText().toString();

            return true;
        }
        else if (screen.equals("Aides")) {
            name=txtAideCompName.getText().toString();
            mobile=txtAideOfficePhone.getText().toString();
            phone=txtDoctorHourOfficePhone.getText().toString();
            workphone=txtOtherPhone.getText().toString();
            website=txtAideWebsite.getText().toString();
            note=txtAideNote.getText().toString();
            email=txtAideEmail.getText().toString();
            fax=txtAideFax.getText().toString();
            address=txtAideAddress.getText().toString();

            return true;
        }
        return false;
    }

    private void dispatchTakePictureIntent() {
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
                startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
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


            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }
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

        }

    }

    public void callRelation(String relationship) {
        relation=relationship;
    }
}
