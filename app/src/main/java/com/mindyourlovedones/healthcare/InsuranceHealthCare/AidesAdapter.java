package com.mindyourlovedones.healthcare.InsuranceHealthCare;

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
import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.DashBoard.AddFormActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Aides;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class AidesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Aides> AidesList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public AidesAdapter(Context context, ArrayList<Aides> AidesList) {
        preferences = new Preferences(context);
        this.context = context;
        this.AidesList = AidesList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return AidesList.size();
    }

    @Override
    public Object getItem(int position) {
        return AidesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_aides, parent, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
//            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.imgForword = (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(AidesList.get(position).getOfficePhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }

        if(AidesList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else
        {
            holder.txtAddress.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(AidesList.get(position).getAidName());
        holder.txtAddress.setText(AidesList.get(position).getAddress());
        holder.txtPhone.setText(AidesList.get(position).getOfficePhone());
       // holder.imgProfile.setImageResource(AidesList.get(position).getImage());
        /*byte[] photo=AidesList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);*/

        File imgFile = new File(AidesList.get(position).getPhoto());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);
        }

        if (AidesList.get(position).getPhotoCard()!=null) {
            File imgFile1 = new File(AidesList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForword.setImageBitmap(myBitmap);
            }
            /*byte[] photoCard = AidesList.get(position).getPhotoCard();
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
                i.putExtra("Image",AidesList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });



        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "AidesData");
                Aides aides = AidesList.get(position);
                i.putExtra("AideObject",aides);
                context.startActivity(i);
            }
        });
        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "AidesViewData");
                Aides aides = AidesList.get(position);
                i.putExtra("AideObject",aides);
                context.startActivity(i);
            }
        });


        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType;
        ImageView imgProfile, imgForword,imgEdit;
        ImageView imgNext;
        SwipeRevealLayout swipeLayout;
    }
}