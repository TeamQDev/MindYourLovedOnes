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

import com.chauthai.swipereveallayout.SwipeRevealLayout;
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
            holder.txtTelePhone= (TextView) convertView.findViewById(R.id.txtTelePhone);
            holder.txtType= (TextView) convertView.findViewById(R.id.txtType);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.swipeLayout= (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(emergencyList.get(position).getName());
        holder.txtOfficePhone.setText(emergencyList.get(position).getWorkPhone());
        holder.txtPhone.setText(emergencyList.get(position).getMobile());
        holder.txtType.setText(emergencyList.get(position).getRelationType());
        holder.txtTelePhone.setText(emergencyList.get(position).getPhone());

        byte[] photo=emergencyList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.putString(PrefConstants.SOURCE,"EmergencyUpdate");
                Intent i=new Intent(context,GrabConnectionActivity.class);
                i.putExtra("EmergencyObject",emergencyList.get(position));
                context.startActivity(i);
            }
        });
       /* holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDashboard ldf = new FragmentDashboard ();
                Bundle args = new Bundle();
                args.putString("Name", emergencyList.get(position).getName());
                args.putString("Relation", emergencyList.get(position).getRelationType());
                ldf.setArguments(args);

                ((BaseActivity)context).callFragment("DASHBOARD",ldf);
            }
        });*/

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtAddress, txtPhone, txtType,txtTelePhone,txtOfficePhone;
        ImageView imgProfile,imgEdit;
        SwipeRevealLayout swipeLayout;
    }
}