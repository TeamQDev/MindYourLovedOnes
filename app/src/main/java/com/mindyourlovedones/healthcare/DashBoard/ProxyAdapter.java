package com.mindyourlovedones.healthcare.DashBoard;

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

import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Proxy;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class ProxyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Proxy> proxyList;
    LayoutInflater lf;

    ViewHolder holder;
    Preferences preferences;

    public ProxyAdapter(Context context, ArrayList<Proxy> proxyList) {
        this.context=context;
        this.proxyList=proxyList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        preferences=new Preferences(context);
    }

    @Override
    public int getCount() {
        return proxyList.size();
    }

    @Override
    public Object getItem(int position) {
        return proxyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
           convertView=lf.inflate(R.layout.row_proxy,parent,false);
            holder=new ViewHolder();
            holder.txtName= (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress= (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtOfficePhone= (TextView) convertView.findViewById(R.id.txtOfficePhone);
            holder.txtPhone= (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtTelePhone= (TextView) convertView.findViewById(R.id.txtTelePhone);
            holder.txtType= (TextView) convertView.findViewById(R.id.txtType);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);


            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        if(proxyList.get(position).getMobile().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }
        if(proxyList.get(position).getWorkPhone().equals(""))
        {
            holder.txtTelePhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtTelePhone.setVisibility(View.VISIBLE);
        }
        /*if(proxyList.get(position).getRelationType().equals(""))
        {
            holder.txtType.setVisibility(View.GONE);
        }
        else
        {
            holder.txtType.setVisibility(View.VISIBLE);
        }*/
        holder.txtName.setText(proxyList.get(position).getName());
        holder.txtOfficePhone.setText(proxyList.get(position).getWorkPhone());
        holder.txtPhone.setText(proxyList.get(position).getMobile());
        if (proxyList.get(position).getIsPrimary()==0)
        {
            holder.txtType.setText("Primary - Health Care Proxy Agent");
        }else if (proxyList.get(position).getIsPrimary()==1)
        {
            holder.txtType.setText("Successor - Health Care Proxy Agent");
        }


        holder.txtTelePhone.setText(proxyList.get(position).getWorkPhone());
        File imgFile = new File(proxyList.get(position).getPhoto());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);
        }
       /* byte[] photo=proxyList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);*/

        if (proxyList.get(position).getPhotoCard()!=null) {
            File imgFile1 = new File(proxyList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForword.setImageBitmap(myBitmap);
            }
           /* byte[] photoCard = proxyList.get(position).getPhotoCard();
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
                i.putExtra("Image",proxyList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });


        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE,"ProxyUpdate");
                Intent i=new Intent(context,GrabConnectionActivity.class);
                i.putExtra("ProxyObject",proxyList.get(position));
                context.startActivity(i);
            }
        });

        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE,"ProxyUpdateView");
                Intent i=new Intent(context,GrabConnectionActivity.class);
                i.putExtra("ProxyObject",proxyList.get(position));
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtAddress, txtPhone, txtType,txtTelePhone,txtOfficePhone;
        ImageView imgProfile,imgEdit,imgForword;
        ImageView imgNext;
     //   SwipeRevealLayout swipeLayout;
    }
}
