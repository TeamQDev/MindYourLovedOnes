package com.mindyourelders.MyHealthCareWishes.Connections;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.DashBoard.FragmentDashboard;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.BaseActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class ConnectionAdapter extends BaseSwipListAdapter {
    Context context;
    ArrayList<RelativeConnection> connectionList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;

    public ConnectionAdapter(Context context, ArrayList<RelativeConnection> connectionList) {
        preferences=new Preferences(context);
        this.context=context;
        this.connectionList=connectionList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return connectionList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return connectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {

            if (position!=connectionList.size()) {
                convertView = lf.inflate(R.layout.row_connections, parent, false);
            }
            else if(position==connectionList.size()){
                convertView = lf.inflate(R.layout.row_connectionsadd, parent, false);
             /*   holder.txtConName = (TextView) convertView.findViewById(R.id.txtConName);
                holder.imgConPhoto = (ImageView) convertView.findViewById(R.id.imgConPhoto);*/
            }
            holder = new ViewHolder();
            holder.txtConName = (TextView) convertView.findViewById(R.id.txtConName);
            holder.txtConRelation = (TextView) convertView.findViewById(R.id.txtConRelation);
            //   holder.txtAddress= (TextView) convertView.findViewById(R.id.txtAddress);
            holder.imgConPhoto = (ImageView) convertView.findViewById(R.id.imgConPhoto);
            //   holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (position!=connectionList.size()) {
            holder.txtConName.setText(connectionList.get(position).getName());
            holder.txtConRelation.setText(connectionList.get(position).getRelationType());

            byte[] photo = connectionList.get(position).getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            holder.imgConPhoto.setImageBitmap(bmp);
        }
/*
if (connectionList.get(position).getAddress().equals(""))
{
    holder.txtAddress.setText("#203,10 Downing Street, los Angeles, California.");
}
else {
    holder.txtAddress.setText(connectionList.get(position).getAddress());
}
*/


convertView.setOnLongClickListener(new View.OnLongClickListener() {
    @Override
    public boolean onLongClick(View v) {
        if (position==connectionList.size()) {

        }
        return true;
    }
});

      convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==connectionList.size())
                {
                    preferences.putString(PrefConstants.SOURCE,"Connection");
                    Intent i=new Intent(context,GrabConnectionActivity.class);
                    context.startActivity(i);
                }
                else {
                    FragmentDashboard ldf = new FragmentDashboard();
                    Bundle args = new Bundle();
                    args.putString("Name", connectionList.get(position).getName());
                    args.putString("Address", connectionList.get(position).getAddress());
                    args.putString("Relation", connectionList.get(position).getRelationType());
                    String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                    preferences.putString(PrefConstants.USER_IMAGE, saveThis);
                    preferences.putString(PrefConstants.CONNECTED_NAME, connectionList.get(position).getName());
                    preferences.putString(PrefConstants.CONNECTED_USEREMAIL, connectionList.get(position).getEmail());
                    preferences.putInt(PrefConstants.CONNECTED_USERID, connectionList.get(position).getId());
                    ldf.setArguments(args);

                    ((BaseActivity) context).callFragment("DASHBOARD", ldf);
                }
            }
        });

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtConName, txtConRelation,txtAddress;
        ImageView imgConPhoto,imgForword;
    }

   /* @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }*/
}


