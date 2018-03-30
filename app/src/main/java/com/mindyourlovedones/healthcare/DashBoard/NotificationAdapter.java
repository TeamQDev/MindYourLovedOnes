package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.NotificationSetter;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class NotificationAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotificationSetter> notificationList;
    LayoutInflater lf;
    ViewHolder holder;

    public NotificationAdapter(Context context, ArrayList<NotificationSetter> notificationList) {
        this.context=context;
        this.notificationList=notificationList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
           convertView=lf.inflate(R.layout.row_notification,parent,false);
           holder=new ViewHolder();
            holder.txtName= (TextView) convertView.findViewById(R.id.txtName);
            holder.txtTime= (TextView) convertView.findViewById(R.id.txtTime);
            holder.txtMessage= (TextView) convertView.findViewById(R.id.txtMessage);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(notificationList.get(position).getName());
        holder.txtMessage.setText(notificationList.get(position).getMessage());
        holder.txtTime.setText(notificationList.get(position).getTime());
        holder.imgProfile.setImageResource(notificationList.get(position).getImage());
        if (position==0)
        {
            holder.txtMessage.setTextColor(context.getResources().getColor(R.color.colorMaroon));
        }

      holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentDashboard ldf = new FragmentDashboard ();
                Bundle args = new Bundle();
                args.putString("Name", notificationList.get(position).getName());
                args.putString("Relation", notificationList.get(position).getRelationType());
                args.putInt("Image", notificationList.get(position).getImage());
                ldf.setArguments(args);

                ((BaseActivity)context).callFragment("DASHBOARD",ldf);*/
            }
        });

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtTime,txtMessage;
        ImageView imgProfile,imgForword;
    }
}
