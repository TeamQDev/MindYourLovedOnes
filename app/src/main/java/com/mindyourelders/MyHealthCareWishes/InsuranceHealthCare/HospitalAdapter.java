package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Hospital;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by V@iBh@V on 10/23/2017.
 */

public class HospitalAdapter extends BaseAdapter{
    Context context;
    ArrayList<Hospital> hospitalList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public HospitalAdapter(Context context, ArrayList<Hospital> hospitalList) {
        preferences=new Preferences(context);
        this.context = context;
        this.hospitalList = hospitalList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hospitalList.size();
    }

    @Override
    public Object getItem(int position) {
        return hospitalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_hospital, parent, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgForward = (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
//            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(hospitalList.get(position).getPhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }

        if(hospitalList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else
        {
            holder.txtAddress.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(hospitalList.get(position).getName());
        holder.txtAddress.setText(hospitalList.get(position).getAddress());
        holder.txtPhone.setText(hospitalList.get(position).getOfficePhone());
        holder.txtType.setText(hospitalList.get(position).getName());
        holder.txtCategory.setText(hospitalList.get(position).getCategory());
        //holder.imgProfile.setImageResource(FinanceList.get(position).getImage());
        byte[] photo=hospitalList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "HospitalData");
                Hospital hospital = hospitalList.get(position);
                i.putExtra("HospitalObject",hospital);
                context.startActivity(i);
            }
        });
        holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "HospitalViewData");
                Hospital hospital = hospitalList.get(position);
                i.putExtra("HospitalObject",hospital);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType, txtCategory;
        ImageView imgProfile, imgForward,imgEdit;
        SwipeRevealLayout swipeLayout;
    }

}
