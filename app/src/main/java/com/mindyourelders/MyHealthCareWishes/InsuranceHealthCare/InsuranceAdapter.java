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
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class InsuranceAdapter extends BaseAdapter {
    Context context;
    //SwipeMenuListView lvInsurance;
    ArrayList<Insurance> insuranceList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public InsuranceAdapter(Context context, ArrayList<Insurance> insuranceList) {
        preferences = new Preferences(context);
        this.context=context;
        this.insuranceList=insuranceList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return insuranceList.size();
    }

    @Override
    public Object getItem(int position) {
        return insuranceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView=lf.inflate(R.layout.row_insurance,parent,false);
            holder=new ViewHolder();
            holder.txtName= (TextView) convertView.findViewById(R.id.txtName);

            holder.txtPhone= (TextView) convertView.findViewById(R.id.txtPhone);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
           // holder.swipeLayout= (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(insuranceList.get(position).getName()+" - "+insuranceList.get(position).getType());
        holder.txtPhone.setText(insuranceList.get(position).getPhone());
      //  holder.imgProfile.setImageResource(insuranceList.get(position).getImage());
        byte[] photo=insuranceList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE,"InsuranceData");
                Insurance insurance= insuranceList.get(position);
                i.putExtra("InsuranceObject",insurance);
                context.startActivity(i);
            }
        });
        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE,"InsuranceViewData");
                Insurance insurance= insuranceList.get(position);
                i.putExtra("InsuranceObject",insurance);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtId, txtPhone, txtGroup,txtType;
        ImageView imgProfile,imgForword,imgEdit;
        SwipeRevealLayout swipeLayout;
    }
}