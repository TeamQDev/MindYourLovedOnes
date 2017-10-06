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
import com.mindyourelders.MyHealthCareWishes.model.Aides;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(AidesList.get(position).getAidName());
        holder.txtAddress.setText(AidesList.get(position).getAddress());
        holder.txtPhone.setText(AidesList.get(position).getOfficePhone());
       // holder.imgProfile.setImageResource(AidesList.get(position).getImage());
        byte[] photo=AidesList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "AidesData");
                Aides aides = AidesList.get(position);
                i.putExtra("Name", aides.getFirm());
                i.putExtra("AideName", aides.getAidName());
                i.putExtra("Address", aides.getAddress());
                i.putExtra("Phone", aides.getOfficePhone());
                i.putExtra("Photo", aides.getImage());
                context.startActivity(i);
            }
        });
        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "AidesViewData");
                Aides aides = AidesList.get(position);
                i.putExtra("Name", aides.getFirm());
                i.putExtra("AideName", aides.getAidName());
                i.putExtra("Address", aides.getAddress());
                i.putExtra("Phone", aides.getOfficePhone());
                i.putExtra("Photo", aides.getImage());
                context.startActivity(i);
            }
        });


        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType;
        ImageView imgProfile, imgForword,imgEdit;
        SwipeRevealLayout swipeLayout;
    }
}