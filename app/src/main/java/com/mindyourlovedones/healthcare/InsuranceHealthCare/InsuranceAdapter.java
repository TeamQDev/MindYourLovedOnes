package com.mindyourlovedones.healthcare.InsuranceHealthCare;

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
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.DashBoard.AddFormActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Insurance;
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

    ImageLoader imageLoaderProfile,imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile,displayImageOptionsCard;

    public InsuranceAdapter(Context context, ArrayList<Insurance> insuranceList) {
        preferences = new Preferences(context);
        this.context=context;
        this.insuranceList=insuranceList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);


            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        String name=insuranceList.get(position).getName();
        String type=insuranceList.get(position).getType();
        if (!type.equals(""))
        {
            if (type.equals("Other"))
            {
                holder.txtName.setText(name + " - " + insuranceList.get(position).getOtherInsurance());
            }
            else {
                holder.txtName.setText(name + " - " + type);
            }
        }
        else{
            holder.txtName.setText(name);
        }

        if(insuranceList.get(position).getPhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }
       /* if(insuranceList.get(position).getType().equals(""))
        {
            holder.txtType.setVisibility(View.GONE);
        }else
        {
            holder.tx
            tType.setVisibility(View.VISIBLE);
        }*/
        holder.txtPhone.setText(insuranceList.get(position).getPhone());
      //  holder.imgProfile.setImageResource(insuranceList.get(position).getImage());

        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),insuranceList.get(position).getPhoto());
        if (imgFile.exists()) {
           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);*/
            imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgProfile,displayImageOptionsProfile);
        }
        /*byte[] photo=insuranceList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);*/

        if (!insuranceList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH),insuranceList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForword.setImageBitmap(myBitmap);*/
                imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)),holder.imgForword,displayImageOptionsCard);
            }
          /*  byte[] photoCard = insuranceList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);*/
           // holder.imgForword.setImageBitmap(bmpCard);
            holder.imgForword.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgForword.setVisibility(View.GONE);
        }


        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AddFormActivity.class);
                i.putExtra("Image",insuranceList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });


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
        holder.imgNext.setOnClickListener(new View.OnClickListener() {
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
        ImageView imgNext;
        SwipeRevealLayout swipeLayout;
    }
}