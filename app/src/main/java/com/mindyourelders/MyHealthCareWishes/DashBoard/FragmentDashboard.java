package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.IndexMenu.FragmentOverview;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.SpecialistsActivity;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MedInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.DialogManager;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.rlEmergency;

/**
 * Created by varsha on 8/23/2017.
 */

public class FragmentDashboard extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    FragmentOverview fragmentOverview;
    ImageView imgProfile, imgShareLocation, imgLocationFeed, imgNoti,imgLogo,imgPdf,imgDrawerProfile;
    TextView txtName,txtAddress, txtRelation,txtDrawerName;
    RelativeLayout rlEmergencyContact, rlSpecialist, rlInsuranceCard, rlEmergencyEvent, rlPrescription, rlCarePlan;
    View rootview;
    boolean flag = false;
    TextView txtTitle;
    Preferences preferences;
    DBHelper dbHelper;
    PersonalInfo personalInfo;
    RelativeLayout leftDrawer;
    RelativeConnection connection;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final int REQUEST_WRITE_PERMISSION = 200;
    private static final int REQUEST_CALL_PERMISSION = 300;
    String[] Relationship = {"Aunt","Brother","Cousin","Dad","Daughter","Father-in-law","Friend","GrandDaughter","GrandFather","GrandMother","GrandSon","Husband","Mom","Mother-in-law","Neighbor","Nephew","Niece","Sister","Son","Uncle","Wife", "Other"};
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_dashboard_news, null);
        preferences=new Preferences(getActivity());
        //checkRuntimePermission();
        accessPermission();
        //requestPermission();
        initImageLoader();
        initUI();
        initListener();
        initComponent();

        return rootview;
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }


    private void accessPermission() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    &&
                    ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    ) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                }, REQUEST_CALL_PERMISSION);

            } else {
               // checkForRegistration();
            }
        }


    private boolean requestPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_PERMISSION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_PERMISSION);
            }
            return false;
        } else {
            return true;
        }
    }
    private boolean checkRuntimePermission() {
       /* requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                PERMISSIONS_REQUEST_READ_CONTACTS);
        return true;*/
       if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSIONS_REQUEST_READ_CONTACTS);
            }
            return false;
        } else {
            return true;
        }
    }
    private void initComponent() {
        dbHelper=new DBHelper(getActivity());
        PersonalInfoQuery s=new PersonalInfoQuery(getActivity(),dbHelper);
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
        MedInfoQuery mq=new MedInfoQuery(getActivity(),dbHelper);
        if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID)))
        {
            personalInfo = PersonalInfoQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            preferences.putString(PrefConstants.USER_PROFILEIMAGE, personalInfo.getPhoto());
            preferences.putString(PrefConstants.CONNECTED_NAME, personalInfo.getName());
            String name =personalInfo.getName();
            String address=personalInfo.getAddress();
            String relation = "Self";
            //byte[]array =personalInfo.getPhoto();
            if (!personalInfo.getPhoto().equals(""))
            {
            File imgFile = new File(personalInfo.getPhoto());
            if (imgFile.exists()) {
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgProfile.setImageBitmap(myBitmap);*/
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgProfile,displayImageOptions);
            }
        }else{
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        }
           // byte[] array = Base64.decode(image, Base64.DEFAULT);
            txtName.setText(name+" - "+relation);
            txtRelation.setText(relation);
            txtAddress.setText(address);
          /*  Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
            imgProfile.setImageBitmap(bmp);*/
        }
        else {
            connection = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            preferences.putString(PrefConstants.CONNECTED_NAME, connection.getName());
            String name =connection.getName();
            String address=connection.getAddress();
            String relation=connection.getRelationType();
            String otherrelation=connection.getOtherRelation();
            /*int index = 0;
            for (int i = 0; i < Relationship.length; i++) {
                if (connection.getRelationType().equalsIgnoreCase(Relationship[i])) {
                    index = i;
                }
            }

            String relation =Relationship[index+1];*/
           // byte[]array =connection.getPhoto();
            File imgFile = new File(connection.getPhoto());
            if (imgFile.exists()) {
              /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgProfile.setImageBitmap(myBitmap);*/
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgProfile,displayImageOptions);
            }else{
                imgProfile.setImageResource(R.drawable.ic_profile_defaults);
            }
            // byte[] array = Base64.decode(image, Base64.DEFAULT);

            if (relation.equals("Other"))
            {
                txtName.setText(name+" - "+otherrelation);
                txtRelation.setText(otherrelation);
            }else {
                txtName.setText(name+" - "+relation);
                txtRelation.setText(relation);
            }
            txtAddress.setText(address);
//            Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
//            imgProfile.setImageBitmap(bmp);
        }


    }
    private void initListener() {
        //rlOverview.setOnClickListener(this);
        rlCarePlan.setOnClickListener(this);
        rlEmergencyContact.setOnClickListener(this);
        rlSpecialist.setOnClickListener(this);
        rlInsuranceCard.setOnClickListener(this);
        rlEmergencyEvent.setOnClickListener(this);
        rlPrescription.setOnClickListener(this);
        imgProfile.setOnClickListener(this);
        imgPdf.setOnClickListener(this);
        //  rlInsurance.setOnClickListener(this);
        //  rlEmergency.setOnClickListener(this);
//        imgShareLocation.setOnClickListener(this);
        // rlEmergency.setOnLongClickListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.GONE);
        txtTitle.setText("");
        imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgPdf = (ImageView) getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgNoti.setVisibility(View.VISIBLE);
        imgLogo = (ImageView) getActivity().findViewById(R.id.imgLogo);
        imgLogo.setVisibility(View.GONE);
        imgProfile = (ImageView) getActivity().findViewById(R.id.imgProfile);
        imgProfile.setVisibility(View.VISIBLE);
        txtName = (TextView)rootview.findViewById(R.id.txtName);
        txtName.setVisibility(View.VISIBLE);

        leftDrawer = (RelativeLayout) getActivity().findViewById(R.id.leftDrawer);
        txtDrawerName = (TextView) leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = (ImageView) leftDrawer.findViewById(R.id.imgDrawerProfile);
        // rlOverview= (RelativeLayout) rootview.findViewById(rlOverview);
        rlCarePlan = (RelativeLayout) rootview.findViewById(R.id.rlCarePlan);
        rlEmergencyContact = (RelativeLayout) rootview.findViewById(R.id.rlEmergencyContact);
        rlSpecialist = (RelativeLayout) rootview.findViewById(R.id.rlSpecialist);
        rlInsuranceCard = (RelativeLayout) rootview.findViewById(R.id.rlInsuranceCard);
        rlEmergencyEvent = (RelativeLayout) rootview.findViewById(R.id.rlEmergencyEvent);
        rlPrescription = (RelativeLayout) rootview.findViewById(R.id.rlPrescription);
        // rlInsurance= (RelativeLayout) rootview.findViewById(rlInsurance);
        //rlEmergency= (RelativeLayout) rootview.findViewById(rlEmergency);
        txtAddress = (TextView) rootview.findViewById(R.id.txtAddress);

        txtRelation = (TextView) rootview.findViewById(R.id.txtRelation);
       // imgShareLocation = (ImageView) rootview.findViewById(R.id.imgShareLocation);
        imgLocationFeed = (ImageView) getActivity().findViewById(R.id.imgLocationFeed);


        /*Bundle bundle = this.getArguments();
        String name = bundle.getString("Name");
        String address=bundle.getString("Address");
        String relation = bundle.getString("Relation");
        String image=preferences.getString(PrefConstants.USER_IMAGE);
        byte[] array = Base64.decode(image, Base64.DEFAULT);
        txtName.setText(name+" - "+relation);
        txtRelation.setText(relation);
        txtAddress.setText(address);
        Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
        imgProfile.setImageBitmap(bmp);*/
    }

    public void postCommonDialog() {
        imgLocationFeed.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rlEmergencyContact:
                Intent intentOverview = new Intent(getActivity(), SpecialistsActivity.class);
                intentOverview.putExtra("FROM","Emergency");
                startActivity(intentOverview);
                break;

            case R.id.imgProfile:
                Intent intentProfile = new Intent(getActivity(), EmergencyInfoActivity.class);
                intentProfile.putExtra("FRAGMENT", "Individual");
                startActivity(intentProfile);
                break;

            //Emergency Event Note
            case R.id.rlEmergencyEvent:
                Intent intentContact = new Intent(getActivity(), SpecialistsActivity.class);
                intentContact.putExtra("FROM","Event");
                startActivity(intentContact);
               /* Intent intentContact = new Intent(getActivity(), EventNoteActivity.class);
                startActivity(intentContact);*/
                break;

            case R.id.rlPrescription:
                Intent intentPrescription = new Intent(getActivity(), PrescriptionActivity.class);
                startActivity(intentPrescription);
                break;

            case R.id.rlCarePlan:
                Intent intentCarePlan = new Intent(getActivity(), CarePlanActivity.class);
                startActivity(intentCarePlan);
                break;

            case R.id.rlSpecialist:
                Intent intentInsurance = new Intent(getActivity(), SpecialistsActivity.class);
                intentInsurance.putExtra("FROM","Speciality");
                startActivity(intentInsurance);
                break;

            case R.id.rlInsuranceCard:
                Intent intentInsuarnc3e = new Intent(getActivity(), SpecialistsActivity.class);
                intentInsuarnc3e.putExtra("FROM","Insurance");
                startActivity(intentInsuarnc3e);
                /*Intent intentEmergency = new Intent(getActivity(), InsuranceCardActivity.class);
                intentEmergency.putExtra("Flag", "NoMap");
                startActivity(intentEmergency);*/
                break;

            /*case R.id.imgShareLocation:
                ShowShareLocationDialog();
                break;*/
            case R.id.imgPdf:
              /*  StringBuffer result = new StringBuffer();
                result.append(new MessageString().getProfile());*/
              /*  result.append(new MessageString().getMedicalMsg());
                result.append(new MessageString().getInsuranceMsg());*/
             /*   new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                    + "/mye/" + preferences.getString(PrefConstants.CONNECTED_USERID) + "_" +  preferences.getString(PrefConstants.USER_ID)
                    + "/Mind Your Elders Summary Report.pdf",
                    BaseActivity.this, result);*/




                break;
        }
    }

    private void ShowShareLocationDialog() {
        final Dialog customDialog;

        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_share_location, null);
        // Build the dialog
        customDialog = new Dialog(getActivity(), R.style.CustomDialog);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(customView);
        ImageView imgBack = (ImageView) customDialog.findViewById(R.id.imgBack);
        TextView txtCancel = (TextView) customDialog.findViewById(R.id.txtCancel);
        TextView txtShare = (TextView) customDialog.findViewById(R.id.txtShare);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        txtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogManager dialogManager = new DialogManager(new FragmentDashboard());
                dialogManager.showCommonDialog("Share?", "Do you want to share your location?", getActivity(), "SHARE_LOCATION", null);
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == rlEmergency) {
            Intent intentEmergency = new Intent(getActivity(), EmergencyActivity.class);
            intentEmergency.putExtra("Flag", "Map");
            startActivity(intentEmergency);
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        initComponent();
        getProfile();
        String image=preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        //byte[] photo = Base64.decode(image, Base64.DEFAULT);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            File imgFile = new File(image);
            if (imgFile.exists()) {
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgDrawerProfile,displayImageOptions);
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgDrawerProfile.setImageBitmap(myBitmap);*/
            }
        }else{
            imgDrawerProfile.setImageResource(R.drawable.ic_profile_defaults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  //  checkForRegistration();

                } else {

                   accessPermission();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'switch' lines to check for other
            // permissions this app might request
        }
    }
    private void getProfile() {
        personalInfo = PersonalInfoQuery.fetchProfiles();
        preferences.putInt(PrefConstants.USER_ID, personalInfo.getId());
        preferences.putString(PrefConstants.USER_NAME,personalInfo.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE,personalInfo.getPhoto());
    }


}
