package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

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

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.DashBoard.AddFormActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
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

public class SpecialistAdapter extends BaseAdapter {
    Context context;
    ArrayList<Specialist> specialistList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    ImageLoader imageLoaderProfile,imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile,displayImageOptionsCard;

    public SpecialistAdapter(Context context, ArrayList<Specialist> specialistList) {
        preferences = new Preferences(context);
        this.context = context;
        this.specialistList = specialistList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
        return specialistList.size();
    }

    @Override
    public Object getItem(int position) {
        return specialistList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_doctor, parent, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtOfficePhone= (TextView) convertView.findViewById(R.id.txtOfficePhone);
            holder.txtTelePhone= (TextView) convertView.findViewById(R.id.txtTelePhone);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
          //  holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            holder.imgForword = (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(specialistList.get(position).getOfficePhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }
        if(specialistList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else
        {
            holder.txtAddress.setVisibility(View.VISIBLE);
        }
        if(specialistList.get(position).getType().equals(""))
        {
            holder.txtType.setVisibility(View.GONE);
        }
        else
        {
            holder.txtType.setVisibility(View.VISIBLE);
        }

        holder.txtName.setText(specialistList.get(position).getName());
        holder.txtOfficePhone.setText(specialistList.get(position).getOtherPhone());
        holder.txtPhone.setText(specialistList.get(position).getOfficePhone());
        if (specialistList.get(position).getType().equals("Other"))
        {
            holder.txtType.setText(specialistList.get(position).getType()+" - "+specialistList.get(position).getOtherType());
        }
        else {
            holder.txtType.setText(specialistList.get(position).getType());
        }
       // holder.txtType.setText(specialistList.get(position).getType());
        holder.txtTelePhone.setText(specialistList.get(position).getOtherPhone());
        holder.txtAddress.setText(specialistList.get(position).getAddress());
      //  holder.imgProfile.setImageResource(specialistList.get(position).getImage());
        File imgFile = new File(specialistList.get(position).getPhoto());
        if (imgFile.exists()) {
          //  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
          //  holder.imgProfile.setImageBitmap(myBitmap);
            imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgProfile,displayImageOptionsProfile);
        }


       /* byte[] photo=specialistList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);*/

        if (!specialistList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(specialistList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForword.setImageBitmap(myBitmap);*/
                imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)),holder.imgForword,displayImageOptionsCard);

            }
          /*  byte[] photoCard = specialistList.get(position).getPhotoCard();
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
                i.putExtra("Image",specialistList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                Specialist insurance = specialistList.get(position);
                if (insurance.getIsPhysician()==1) {
                    preferences.putString(PrefConstants.SOURCE, "PhysicianData");
                }else if (insurance.getIsPhysician()==2) {
                    preferences.putString(PrefConstants.SOURCE, "SpecialistData");
                }
               /* i.putExtra("Name", insurance.getName());
                i.putExtra("Type", insurance.getType());
                i.putExtra("Address", insurance.getAddress());
                i.putExtra("Phone", insurance.getOfficePhone());
                i.putExtra("Photo", insurance.getImage());*/
                i.putExtra("SpecialistObject",insurance);
                i.putExtra("IsPhysician",insurance.getIsPhysician());
                context.startActivity(i);
            }
        });

        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                Specialist insurance = specialistList.get(position);
                if (insurance.getIsPhysician()==1) {
                    preferences.putString(PrefConstants.SOURCE, "PhysicianViewData");
                }else if (insurance.getIsPhysician()==2) {
                    preferences.putString(PrefConstants.SOURCE, "SpecialistViewData");
                }
                i.putExtra("SpecialistObject",insurance);
                i.putExtra("IsPhysician",insurance.getIsPhysician());
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType,txtTelePhone,txtOfficePhone;
        ImageView imgProfile, imgForword,imgEdit,imgNext;
        RelativeLayout rlMain;
        SwipeRevealLayout swipeLayout;
    }
}