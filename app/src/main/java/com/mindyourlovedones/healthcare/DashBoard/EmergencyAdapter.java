package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Emergency;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

import static com.mindyourlovedones.healthcare.HomeActivity.R.id.imgForword;

/**
 * Created by varsha on 8/28/2017.
 */

public class EmergencyAdapter extends BaseAdapter {
    Context context;
    ArrayList<Emergency> emergencyList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    ImageLoader imageLoaderProfile,imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile,displayImageOptionsCard;


    public EmergencyAdapter(Context context, ArrayList<Emergency> emergencyList) {
        this.context=context;
        this.emergencyList=emergencyList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        preferences=new Preferences(context);
        initImageLoader();
    }

    private void initImageLoader() {

        //Profile
        displayImageOptionsProfile = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptionsProfile)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoaderProfile = ImageLoader.getInstance();

        //Card
        displayImageOptionsCard = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.busi_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptionsCard)
                .build();
        ImageLoader.getInstance().init(configs);
        imageLoaderCard = ImageLoader.getInstance();
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
            holder.imgForword= (ImageView) convertView.findViewById(imgForword);
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
       /* if(emergencyList.get(position).getRelationType().equals(""))
        {
            holder.txtType.setVisibility(View.GONE);
        }
        else
        {
            holder.txtType.setVisibility(View.VISIBLE);
        }*/
        String[] priorityType = {"","", "",""};
if (emergencyList.get(position).getIsPrimary()==4)
{
    holder.txtState.setVisibility(View.GONE);
}else {
    holder.txtState.setVisibility(View.VISIBLE);
    if (emergencyList.get(position).getIsPrimary() == 0) {
        holder.txtState.setText("Primary - Health Care Proxy Agent");
    } else if (emergencyList.get(position).getIsPrimary() == 1) {
        holder.txtState.setText("Primary - Emergency Contact");
    } else if (emergencyList.get(position).getIsPrimary() == 2) {
        holder.txtState.setText("Secondary - Health Care Proxy Agent");
    } else if (emergencyList.get(position).getIsPrimary() == 3) {
        holder.txtState.setText("Secondary - Emergency Contact");
    } else if (emergencyList.get(position).getIsPrimary() == 0) {
        holder.txtState.setText("");
    }
}
        holder.txtName.setText(emergencyList.get(position).getName());
        holder.txtOfficePhone.setText(emergencyList.get(position).getWorkPhone());
        holder.txtPhone.setText(emergencyList.get(position).getMobile());
        if (emergencyList.get(position).getRelationType().equals("")&&emergencyList.get(position).getIsPrimary()==4) {
            holder.txtType.setVisibility(View.GONE);
        }else{
            holder.txtType.setVisibility(View.VISIBLE);
            holder.txtType.setText(emergencyList.get(position).getRelationType());
        }
        holder.txtTelePhone.setText(emergencyList.get(position).getPhone());


      /*  byte[] photo=emergencyList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);*/
        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),emergencyList.get(position).getPhoto());
        if (imgFile.exists()) {
            imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgProfile,displayImageOptionsProfile);
           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);*/
        }

        if (!emergencyList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH),emergencyList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
                imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)),holder.imgForword,displayImageOptionsCard);
                /*Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForword.setImageBitmap(myBitmap);*/
            }
           /* byte[] photoCard = emergencyList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
            holder.imgForword.setImageBitmap(bmpCard);*/
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