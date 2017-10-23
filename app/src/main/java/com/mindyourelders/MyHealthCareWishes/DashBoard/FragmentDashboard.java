package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.DialogManager;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.rlEmergency;

/**
 * Created by varsha on 8/23/2017.
 */

public class FragmentDashboard extends Fragment implements View.OnClickListener, View.OnLongClickListener {
    FragmentOverview fragmentOverview;
    ImageView imgProfile, imgShareLocation, imgLocationFeed, imgNoti,imgLogo;
    TextView txtName,txtAddress, txtRelation;
    RelativeLayout rlEmergencyContact, rlSpecialist, rlInsuranceCard, rlEmergencyEvent, rlPrescription, rlCarePlan;
    View rootview;
    boolean flag = false;
    TextView txtTitle;
    Preferences preferences;
DBHelper dbHelper;
    PersonalInfo personalInfo;
    RelativeConnection connection;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    String[] Relationship = {"Mom", "Dad", "Wife", "Husband", "Daughter", "Son", "Sister", "Brother", "Friend", "GrandFather", "GrandMother", "GrandSon", "GrandDaughter","Aunt","Uncle","Niece","Nephew","Cousin","Mother-in-law","Father-in-law","Neighbor", "Other"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_dashboard_news, null);
        preferences=new Preferences(getActivity());
        checkRuntimePermission();
        initUI();
        initListener();
        initComponent();

        return rootview;
    }
    private boolean checkRuntimePermission() {
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
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL)))
        {
            personalInfo = PersonalInfoQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL));
            String name =personalInfo.getName();
            String address=personalInfo.getAddress();
            String relation = "Self";
            byte[]array =personalInfo.getPhoto();
           // byte[] array = Base64.decode(image, Base64.DEFAULT);
            txtName.setText(name+" - "+relation);
            txtRelation.setText(relation);
            txtAddress.setText(address);
            Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
            imgProfile.setImageBitmap(bmp);
        }
        else {
            connection = MyConnectionsQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL));
            String name =connection.getName();
            String address=connection.getAddress();
            String relation=connection.getRelationType();
            /*int index = 0;
            for (int i = 0; i < Relationship.length; i++) {
                if (connection.getRelationType().equalsIgnoreCase(Relationship[i])) {
                    index = i;
                }
            }

            String relation =Relationship[index+1];*/
            byte[]array =connection.getPhoto();
            // byte[] array = Base64.decode(image, Base64.DEFAULT);
            txtName.setText(name+" - "+relation);
            txtRelation.setText(relation);
            txtAddress.setText(address);
            Bitmap bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
            imgProfile.setImageBitmap(bmp);
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
        imgNoti.setVisibility(View.VISIBLE);
        imgLogo = (ImageView) getActivity().findViewById(R.id.imgLogo);
        imgLogo.setVisibility(View.GONE);
        imgProfile = (ImageView) getActivity().findViewById(R.id.imgProfile);
        imgProfile.setVisibility(View.VISIBLE);
        txtName = (TextView)rootview.findViewById(R.id.txtName);
        txtName.setVisibility(View.VISIBLE);
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
    }
}
