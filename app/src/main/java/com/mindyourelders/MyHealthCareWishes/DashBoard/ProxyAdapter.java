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
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Proxy;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

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

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.txtName.setText(proxyList.get(position).getName());
        holder.txtOfficePhone.setText(proxyList.get(position).getWorkPhone());
        holder.txtPhone.setText(proxyList.get(position).getMobile());
        if (proxyList.get(position).getIsPrimary()==1)
        {
            holder.txtType.setText("Primary Proxy");
        }else if (proxyList.get(position).getIsPrimary()==2)
            {
                holder.txtType.setText("Secondary Proxy");
            }

        holder.txtTelePhone.setText(proxyList.get(position).getPhone());
        byte[] photo=proxyList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE,"ProxyUpdate");
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
        ImageView imgProfile,imgEdit;
     //   SwipeRevealLayout swipeLayout;
    }
}
