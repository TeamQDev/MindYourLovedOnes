package com.mindyourlovedones.healthcare.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.DashBoard.EmergencyInfoActivity;
import com.mindyourlovedones.healthcare.DashBoard.EventNoteActivity;
import com.mindyourlovedones.healthcare.DashBoard.InsuranceActivity;
import com.mindyourlovedones.healthcare.DashBoard.InsuranceInfoActivity;
import com.mindyourlovedones.healthcare.DashBoard.LivingActivity;
import com.mindyourlovedones.healthcare.DashBoard.MedicalAppointActivity;
import com.mindyourlovedones.healthcare.DashBoard.ProfileActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;

/**
 * Created by welcome on 9/13/2017.
 */

public class SpecialistContactAdapter extends BaseAdapter {
    Context context;
    String[] specialist;
    LayoutInflater lf;
    boolean isEmergency,isInsurance;
    int[] profile;
    String from;

    public SpecialistContactAdapter(Context context, String[] specialist, int[] profile, boolean isEmergency, boolean isInsurance, String from) {
        this.context = context;
        this.specialist = specialist;
        this.isEmergency = isEmergency;
        this.isInsurance=isInsurance;
        this.profile=profile;
        this.from=from;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return specialist.length;
    }

    @Override
    public Object getItem(int position) {
        return specialist[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_specialistsnew, parent, false);
        }
        RelativeLayout rlmain3= (RelativeLayout) convertView.findViewById(R.id.rlMain);
        if (position%2==0)
        {
            rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorTwoBar));
        }
        else{
            rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorOneBar));
        }
        /*switch(from)
        {

            case "Speciality":
                RelativeLayout rlmain3= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain3.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }
                break;
            case "Emergency":
                RelativeLayout rlmain1= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain1.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain1.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }
                break;
            case "Insurance":
                RelativeLayout rlmain5= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain5.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain5.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }

                break;
            case "Event":
                RelativeLayout rlmain4= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                if (position%2==0)
                {
                    rlmain4.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                }
                else{
                    rlmain4.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
                }
                break;
        }
*/
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        ImageView imgLogo= (ImageView) convertView.findViewById(R.id.imgLogo);
        imgLogo.setImageResource(profile[position]);
        txtName.setText(specialist[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fragment = null;
                switch (specialist[position]) {
                    case "INSURANCE":
                        fragment = "Insurance";
                        break;

                    case "PHARMACIES & HOME\nMEDICAL EQUIPMENT":
                        fragment="Pharmacies";
                        break;

                    case "HOSPITALS & REHABILITATION CENTERS":
                        fragment="Hospitals";
                        break;

                    case "ACTIVITIES OF DAILY LIVING":
                        fragment="Functional";
                        break;

                    case "DOCTORS & OTHER HEALTH\nPROFESSIONALS":
                        fragment = "Doctors";
                        break;
                    case "HOME HEALTH SERVICES":
                        fragment = "Home Health Services";
                        break;
                    case "FINANCE, INSURANCE, LEGAL":
                        fragment = "Finance,Insurance and Legal";
                        break;

                    case "PERSONAL PROFILE":
                        fragment = "Individual";
                        break;
                    case "MEDICAL PROFILE":
                        fragment = "Information";
                        break;
                    case "EMERGENCY CONTACTS AND HEALTH CARE PROXY AGENT":
                        fragment = "Emergency";
                        break;
                    case "PRIMARY PHYSICIAN":
                        fragment = "Physician";
                        break;
                    case "HEALTH CARE PROXY AGENT":
                        fragment = "Proxy";
                        break;

                    case "INSURANCE INFORMATION":
                        fragment = "Insurance Info";
                        break;
                    case "INSURANCE FORMS":
                        fragment = "Insurance Form";
                        break;

                    case "INSURANCE CARDS":
                        fragment = "INSURANCE CARD";
                        break;

                    case "EVENT NOTES":
                        fragment = "Event Notes";
                        break;

                    case "APPOINTMENT CHECKLIST":
                        fragment = "Appointment Tracker";
                        break;
                }
                if (isEmergency == false&&isInsurance==false) {
                    if (fragment.equals("Event Notes")||fragment.equals("Appointment Tracker")||fragment.equals("Functional"))
                    {
                        if (fragment.equals("Event Notes"))
                        {
                            Intent i = new Intent(context, EventNoteActivity.class);
                            i.putExtra("FRAGMENT", fragment);
                            context.startActivity(i);
                        } else if (fragment.equals("Appointment Tracker")) {
                           Intent i = new Intent(context, MedicalAppointActivity.class);
                            i.putExtra("FRAGMENT", fragment);
                            context.startActivity(i);
                        }else if(fragment.equals("Functional"))
                        {
                            Intent i = new Intent(context, LivingActivity.class);
                            i.putExtra("FRAGMENT", fragment);
                            context.startActivity(i);
                        }

                    }
                    else {
                        Intent i = new Intent(context, InsuranceActivity.class);
                        i.putExtra("FRAGMENT", fragment);
                        context.startActivity(i);
                    }
                } else if (isEmergency == true&&isInsurance==false){
                    if (fragment.equals("Individual"))
                    {
                        Intent i = new Intent(context, ProfileActivity.class);
                        i.putExtra("FRAGMENT", fragment);
                        context.startActivity(i);
                    }else{
                        Intent i = new Intent(context, EmergencyInfoActivity.class);
                        i.putExtra("FRAGMENT", fragment);
                        context.startActivity(i);
                    }

                }
                else if (isEmergency == false&&isInsurance==true){
                    Intent i = new Intent(context, InsuranceInfoActivity.class);
                    i.putExtra("FRAGMENT", fragment);
                    context.startActivity(i);
                }
            }
        });

        return convertView;
    }
}
