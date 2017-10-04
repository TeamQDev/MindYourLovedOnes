package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
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
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(pharmacyList.get(position).getName());
        holder.txtPhone.setText(pharmacyList.get(position).getPhone());

     // holder.txtAddress.setText(pharmacyList.get(position).getAddress());
        holder.imgProfile.setImageResource(pharmacyList.get(position).getImage());

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtAddress, txtPhone, txtGroup;
        ImageView imgProfile,imgForword,imgEdit;
        SwipeRevealLayout swipeLayout;
    }
}
