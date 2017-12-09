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
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class SpecialistAdapter extends BaseAdapter {
    Context context;
    ArrayList<Specialist> specialistList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public SpecialistAdapter(Context context, ArrayList<Specialist> specialistList) {
        preferences = new Preferences(context);
        this.context = context;
        this.specialistList = specialistList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return specialistList.size();
    }

    @Override
    public Object getItem(int position) {
        return specialistList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_doctor, parent, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtOfficePhone= (TextView) convertView.findViewById(R.id.txtOfficePhone);
            holder.txtTelePhone= (TextView) convertView.findViewById(R.id.txtTelePhone);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
          //  holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.imgForword = (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(specialistList.get(position).getOfficePhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }
        if(specialistList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else
        {
            holder.txtAddress.setVisibility(View.VISIBLE);
        }
        if(specialistList.get(position).getType().equals(""))
        {
            holder.txtType.setVisibility(View.GONE);
        }
        else
        {
            holder.txtType.setVisibility(View.VISIBLE);
        }

        holder.txtName.setText(specialistList.get(position).getName());
        holder.txtOfficePhone.setText(specialistList.get(position).getOtherPhone());
        holder.txtPhone.setText(specialistList.get(position).getOfficePhone());
        holder.txtType.setText(specialistList.get(position).getType());
        holder.txtTelePhone.setText(specialistList.get(position).getOtherPhone());
        holder.txtAddress.setText(specialistList.get(position).getAddress());
      //  holder.imgProfile.setImageResource(specialistList.get(position).getImage());
        File imgFile = new File(specialistList.get(position).getPhoto());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);
        }


       /* byte[] photo=specialistList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);*/

        if (!specialistList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(specialistList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForword.setImageBitmap(myBitmap);
            }
          /*  byte[] photoCard = specialistList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
            holder.imgForword.setImageBitmap(bmpCard);*/
            holder.imgForword.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgForword.setVisibility(View.GONE);
        }

        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AddFormActivity.class);
                i.putExtra("Image",specialistList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                Specialist insurance = specialistList.get(position);
                if (insurance.getIsPhysician()==1) {
                    preferences.putString(PrefConstants.SOURCE, "PhysicianData");
                }else if (insurance.getIsPhysician()==2) {
                    preferences.putString(PrefConstants.SOURCE, "SpecialistData");
                }
               /* i.putExtra("Name", insurance.getName());
                i.putExtra("Type", insurance.getType());
                i.putExtra("Address", insurance.getAddress());
                i.putExtra("Phone", insurance.getOfficePhone());
                i.putExtra("Photo", insurance.getImage());*/
                i.putExtra("SpecialistObject",insurance);
                i.putExtra("IsPhysician",insurance.getIsPhysician());
                context.startActivity(i);
            }
        });

        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                Specialist insurance = specialistList.get(position);
                if (insurance.getIsPhysician()==1) {
                    preferences.putString(PrefConstants.SOURCE, "PhysicianViewData");
                }else if (insurance.getIsPhysician()==2) {
                    preferences.putString(PrefConstants.SOURCE, "SpecialistViewData");
                }
                i.putExtra("SpecialistObject",insurance);
                i.putExtra("IsPhysician",insurance.getIsPhysician());
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType,txtTelePhone,txtOfficePhone;
        ImageView imgProfile, imgForword,imgEdit,imgNext;
        RelativeLayout rlMain;
        SwipeRevealLayout swipeLayout;
    }
}