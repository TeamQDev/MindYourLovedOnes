package com.mindyourelders.MyHealthCareWishes.DashBoard;

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

import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class EmergencyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Emergency> emergencyList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public EmergencyAdapter(Context context, ArrayList<Emergency> emergencyList) {
        this.context=context;
        this.emergencyList=emergencyList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        preferences=new Preferences(context);
    }

    @Override
    public int getCount() {
        return emergencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return emergencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView=lf.inflate(R.layout.row_specialist,parent,false);
            holder=new ViewHolder();
            holder.txtName= (TextView) convertView.findViewById(R.id.txtName);
            holder.txtOfficePhone= (TextView) convertView.findViewById(R.id.txtOfficePhone);
            holder.txtPhone= (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtState= (TextView) convertView.findViewById(R.id.txtState);
            holder.txtTelePhone= (TextView) convertView.findViewById(R.id.txtTelePhone);
            holder.txtType= (TextView) convertView.findViewById(R.id.txtType);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);
            holder.rlMain= (RelativeLayout) convertView.findViewById(R.id.rlMain);
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);
//            holder.swipeLayout= (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        if(emergencyList.get(position).getMobile().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }
        if(emergencyList.get(position).getPhone().equals(""))
        {
            holder.txtTelePhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtTelePhone.setVisibility(View.VISIBLE);
        }
        if(emergencyList.get(position).getRelationType().equals(""))
        {
            holder.txtType.setVisibility(View.GONE);
        }
        else
        {
            holder.txtType.setVisibility(View.VISIBLE);
        }
        if (emergencyList.get(position).getIsPrimary()==0)
        {
            holder.txtState.setText("Primary");
        }else if (emergencyList.get(position).getIsPrimary()==1)
        {
            holder.txtState.setText("Secondary");
        }

        holder.txtName.setText(emergencyList.get(position).getName());
        holder.txtOfficePhone.setText(emergencyList.get(position).getWorkPhone());
        holder.txtPhone.setText(emergencyList.get(position).getMobile());
        holder.txtType.setText(emergencyList.get(position).getRelationType());
        holder.txtTelePhone.setText(emergencyList.get(position).getPhone());


        byte[] photo=emergencyList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        if (emergencyList.get(position).getPhotoCard()!=null) {
            byte[] photoCard = emergencyList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
            holder.imgForword.setImageBitmap(bmpCard);
            holder.imgForword.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgForword.setVisibility(View.GONE);
        }

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE,"EmergencyUpdate");
                Intent i=new Intent(context,GrabConnectionActivity.class);
                i.putExtra("EmergencyObject",emergencyList.get(position));
                context.startActivity(i);
            }
        });
        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE,"EmergencyView");
                Intent i=new Intent(context,GrabConnectionActivity.class);
                i.putExtra("EmergencyObject",emergencyList.get(position));
                context.startActivity(i);
            }
        });

        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AddFormActivity.class);
                i.putExtra("Image",emergencyList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtAddress, txtPhone, txtType,txtTelePhone,txtOfficePhone,txtState;
        ImageView imgProfile,imgEdit,imgForword,imgNext;
        RelativeLayout rlMain;
       // SwipeRevealLayout swipeLayout;
    }
}