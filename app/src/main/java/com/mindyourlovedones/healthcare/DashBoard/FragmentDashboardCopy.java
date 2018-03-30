package com.mindyourlovedones.healthcare.DashBoard;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.IndexMenu.FragmentOverview;
import com.mindyourlovedones.healthcare.utility.DialogManager;

/**
 * Created by varsha on 8/23/2017.
 */

public class FragmentDashboardCopy extends Fragment implements View.OnClickListener, View.OnLongClickListener{
    FragmentOverview fragmentOverview;
    ImageView imgProfile,imgShareLocation,imgLocationFeed,imgNoti;
    TextView txtName,txtRelation;
    RelativeLayout rlOverview,rlCarePlan,rlInsurance,rlEmergency;
    View rootview;
    boolean flag=false;
    TextView txtTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_dashboard_new,null);
        initUI();
        initListener();
        return rootview;
    }

    private void initListener() {
     /*   rlOverview.setOnClickListener(this);
        rlCarePlan.setOnClickListener(this);
        rlInsurance.setOnClickListener(this);
        rlEmergency.setOnClickListener(this);
        imgShareLocation.setOnClickListener(this);
        rlEmergency.setOnLongClickListener(this);*/
    }

    private void initUI() {
       /* txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.GONE);
        txtTitle.setText("");
        imgNoti= (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.VISIBLE);

        imgProfile= (ImageView) rootview.findViewById(R.id.imgProfile);
        rlOverview= (RelativeLayout) rootview.findViewById(R.id.rlOverview);
        rlCarePlan= (RelativeLayout) rootview.findViewById(R.id.rlCarePlan);
        rlInsurance= (RelativeLayout) rootview.findViewById(R.id.rlInsurance);
        rlEmergency= (RelativeLayout) rootview.findViewById(R.id.rlEmergency);
        txtName= (TextView) rootview.findViewById(R.id.txtName);
        txtRelation= (TextView) rootview.findViewById(R.id.txtRelation);
        imgShareLocation= (ImageView) rootview.findViewById(R.id.imgShareLocation);
        imgLocationFeed = (ImageView)getActivity().findViewById(R.id.imgLocationFeed);

        Bundle bundle = this.getArguments();
        String name = bundle.getString("Name");
        String relation=bundle.getString("Relation");
        int image=bundle.getInt("Image");
        txtName.setText(name);
        txtRelation.setText(relation);
        imgProfile.setImageResource(image);*/
    }

    public void postCommonDialog()
    {
        //imgLocationFeed.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View v) {
     /*   switch (v.getId()) {
            case R.id.rlOverview:
                Intent intentOverview=new Intent(getActivity(), OverviewActivity.class);
                startActivity(intentOverview);
                break;

            case R.id.rlCarePlan:
                Intent intentCarePlan=new Intent(getActivity(), CarePlanActivity.class);
                startActivity(intentCarePlan);
                break;

            case R.id.rlInsurance:
                Intent intentInsurance=new Intent(getActivity(), InsuranceActivity.class);
                startActivity(intentInsurance);
                break;

            case R.id.rlEmergency:
                Intent intentEmergency=new Intent(getActivity(), EmergencyActivity.class);
                intentEmergency.putExtra("Flag","NoMap");
                startActivity(intentEmergency);
                break;

            case R.id.imgShareLocation:
                ShowShareLocationDialog();
                break;
        }*/
    }

    private void ShowShareLocationDialog() {
        final Dialog customDialog;

        LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
        View customView = inflater.inflate(R.layout.dialog_share_location, null);
       // Build the dialog
        customDialog = new Dialog(getActivity(), R.style.CustomDialog);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(customView);
        ImageView imgBack= (ImageView) customDialog.findViewById(R.id.imgBack);
        TextView txtCancel= (TextView) customDialog.findViewById(R.id.txtCancel);
        TextView txtShare= (TextView) customDialog.findViewById(R.id.txtShare);
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
                DialogManager dialogManager=new DialogManager(new FragmentDashboard());
                dialogManager.showCommonDialog("Share?","Do you want to share your location?",getActivity(),"SHARE_LOCATION",null);
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    @Override
    public boolean onLongClick(View v) {
       if (v.getId()==R.id.rlEmergency) {
           Intent intentEmergency = new Intent(getActivity(), EmergencyActivity.class);
           intentEmergency.putExtra("Flag","Map");
           startActivity(intentEmergency);
       }
        return true;
    }
}
