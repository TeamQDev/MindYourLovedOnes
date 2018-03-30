package com.mindyourlovedones.healthcare.InsuranceHealthCare;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.Connections.RelationAdapter;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.utility.DialogManager;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.riontech.staggeredtextgridview.StaggeredTextGridView;


public class ViewActivity extends AppCompatActivity  implements View.OnClickListener {

    //TextView btnShowMore,btnShowLess,btnSon;
    TextView txtName, txtEmail, txtMobile, txtAdd, txtInsuaranceName, txtInsuarancePhone, txtId, txtGroup, txtMember, txtAddress;

    TextView txtPracticeName, txtFax, txtNetwork, txtAffiliation;

    TextView txtAids, txtSchedule, txtOther,txtFName;
    ;
    ImageView imgEdit, imgProfile;
    View rootview;
    RelativeLayout rlRelation, rlConnection, rlDoctor, rlInsurance, rlCommon, rlAids, rlFinance, rlTop;
    Preferences preferences;
    String source;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;

    String name, email, mobile, speciality;
    TextView spinner, spinnerInsuarance, spinnerFinance;
    TextInputLayout tilName,tilFName;

    StaggeredTextGridView gridRelation;
    RelationAdapter relationAdapter;
    DialogManager dialogManager;
    String imagepath = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    @Override
    public void onClick(View v) {

    }
}
