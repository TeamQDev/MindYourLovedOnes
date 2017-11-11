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
import com.mindyourelders.MyHealthCareWishes.model.Finance;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FinanceAdapter extends BaseAdapter {
    Context context;
    ArrayList<Finance> FinanceList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public FinanceAdapter(Context context, ArrayList<Finance> FinanceList) {
        preferences=new Preferences(context);
        this.context = context;
        this.FinanceList = FinanceList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return FinanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return FinanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_finance, parent, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgForward = (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.rlMain= (RelativeLayout) convertView.findViewById(R.id.rlMain);


//            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(FinanceList.get(position).getOfficePhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }

        if(FinanceList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else
        {
            holder.txtAddress.setVisibility(View.VISIBLE);
        }
        if(FinanceList.get(position).getCategory().equals(""))
        {
            holder.txtCategory.setVisibility(View.GONE);
        }
        else
        {
            holder.txtCategory.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText(FinanceList.get(position).getName());
        holder.txtAddress.setText(FinanceList.get(position).getAddress());
        holder.txtPhone.setText(FinanceList.get(position).getOfficePhone());
        holder.txtCategory.setText(FinanceList.get(position).getCategory());
        //holder.imgProfile.setImageResource(FinanceList.get(position).getImage());
        byte[] photo=FinanceList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        if (FinanceList.get(position).getPhotoCard()!=null) {
            byte[] photoCard = FinanceList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
            holder.imgForward.setImageBitmap(bmpCard);
            holder.imgForward.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgForward.setVisibility(View.GONE);
        }


        holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AddFormActivity.class);
                i.putExtra("Image",FinanceList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });



        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "FinanceData");
                Finance finance = FinanceList.get(position);
                i.putExtra("FinanceObject",finance);
                context.startActivity(i);
            }
        });
        holder.rlMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "FinanceViewData");
                Finance finance = FinanceList.get(position);
                i.putExtra("FinanceObject",finance);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType, txtCategory;
        ImageView imgProfile, imgForward,imgEdit;
        RelativeLayout rlMain;
        SwipeRevealLayout swipeLayout;
    }
}