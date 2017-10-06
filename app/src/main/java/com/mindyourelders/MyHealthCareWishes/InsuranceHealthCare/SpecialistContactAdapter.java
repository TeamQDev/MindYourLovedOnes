package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.DashBoard.EmergencyInfoActivity;
import com.mindyourelders.MyHealthCareWishes.DashBoard.InsuranceActivity;
import com.mindyourelders.MyHealthCareWishes.DashBoard.InsuranceInfoActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

/**
 * Created by welcome on 9/13/2017.
 */

class SpecialistContactAdapter extends BaseAdapter {
    Context context;
    String[] specialist;
    LayoutInflater lf;
    boolean isEmergency,isInsurance;
    int[] profile;

    public SpecialistContactAdapter(Context context, String[] specialist, int[] profile, boolean isEmergency, boolean isInsurance) {
        this.context = context;
        this.specialist = specialist;
        this.isEmergency = isEmergency;
        this.isInsurance=isInsurance;
        this.profile=profile;
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

        RelativeLayout rlmain= (RelativeLayout) convertView.findViewById(R.id.rlMain);
        if (position%2==0)
        {
            rlmain.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
        }
        else{
            rlmain.setBackgroundColor(context.getResources().getColor(R.color.colorLightGray));
        }
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        ImageView imgLogo= (ImageView) convertView.findViewById(R.id.imgLogo);
        imgLogo.setImageResource(profile[position]);
        txtName.setText(specialist[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fragment = null;
                switch (specialist[position]) {
                    case "Insurance":
                        fragment = "Insurance";
                        break;

                    case "Pharmacies":
                        fragment="Pharmacies";
                        break;

                    case "Doctors":
                        fragment = "Doctors";
                        break;
                    case "Home Health Services":
                        fragment = "Home Health Services";
                        break;
                    case "Finance,Insurance and Legal":
                        fragment = "Finance,Insurance and Legal";
                        break;


                    case "Individual Info":
                        fragment = "Individual";
                        break;
                    case "Medical Information":
                        fragment = "Information";
                        break;
                    case "Emergency Contacts":
                        fragment = "Emergency";
                        break;
                    case "Primary Physician":
                        fragment = "Physician";
                        break;
                    case "Health Care Proxy Agents":
                        fragment = "Proxy";
                        break;

                    case "Insurance Info":
                        fragment = "Insurance Info";
                        break;
                    case "Insurance Card":
                        fragment = "Insurance Card";
                        break;
                }
                if (isEmergency == false&&isInsurance==false) {
                    Intent i = new Intent(context, InsuranceActivity.class);
                    i.putExtra("FRAGMENT", fragment);
                    context.startActivity(i);
                } else if (isEmergency == true&&isInsurance==false){
                    Intent i = new Intent(context, EmergencyInfoActivity.class);
                    i.putExtra("FRAGMENT", fragment);
                    context.startActivity(i);
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
