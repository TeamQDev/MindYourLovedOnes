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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.DashBoard.AddFormActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by welcome on 9/22/2017.
 */

class PharmacyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Pharmacy> pharmacyList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    public PharmacyAdapter(Context context, ArrayList<Pharmacy> pharmacyList) {
        this.context=context;
        this.pharmacyList=pharmacyList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        preferences=new Preferences(context);
    }

    @Override
    public int getCount() {
        return pharmacyList.size();
    }

    @Override
    public Object getItem(int position) {
        return pharmacyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView=lf.inflate(R.layout.row_pharmacy,parent,false);
            holder=new ViewHolder();
            holder.txtName= (TextView) convertView.findViewById(R.id.txtName);
            holder.txtPhone= (TextView) convertView.findViewById(R.id.txtPhone);
           // holder.txtAddress= (TextView) convertView.findViewById(R.id.txtAddress);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.rlMain= (RelativeLayout) convertView.findViewById(R.id.rlMain);
                        convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        if(pharmacyList.get(position).getPhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }

        holder.txtName.setText(pharmacyList.get(position).getName());
        holder.txtPhone.setText(pharmacyList.get(position).getPhone());
        byte[] photo=pharmacyList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);
     // holder.txtAddress.setText(pharmacyList.get(position).getAddress());
       // holder.imgProfile.setImageResource(pharmacyList.get(position).getImage());

        if (pharmacyList.get(position).getPhotoCard()!=null) {
            byte[] photoCard = pharmacyList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
            holder.imgForword.setImageBitmap(bmpCard);
            holder.imgForword.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgForword.setVisibility(View.GONE);
        }


        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AddFormActivity.class);
                i.putExtra("Image",pharmacyList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "PharmacyData");
                Pharmacy insurance = pharmacyList.get(position);
               /* i.putExtra("Name", insurance.getName());
                i.putExtra("Type", insurance.getType());
                i.putExtra("Address", insurance.getAddress());
                i.putExtra("Phone", insurance.getOfficePhone());
                i.putExtra("Photo", insurance.getImage());*/
                i.putExtra("PharmacyObject",insurance);
                context.startActivity(i);
            }
        });
        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "PharmacyDataView");
                Pharmacy insurance = pharmacyList.get(position);
                i.putExtra("PharmacyObject",insurance);
                context.startActivity(i);
            }
        });
        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtAddress, txtPhone, txtGroup;
        ImageView imgProfile,imgForword,imgEdit;
        RelativeLayout rlMain;
        SwipeRevealLayout swipeLayout;
    }
}
