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
import com.mindyourlovedones.healthcare.model.Finance;
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

public class FinanceAdapter extends BaseAdapter {
    Context context;
    ArrayList<Finance> FinanceList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    ImageLoader imageLoaderProfile,imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile,displayImageOptionsCard;

    public FinanceAdapter(Context context, ArrayList<Finance> FinanceList) {
        preferences=new Preferences(context);
        this.context = context;
        this.FinanceList = FinanceList;
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
        return FinanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return FinanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_finance, parent, false);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtAddress = (TextView) convertView.findViewById(R.id.txtAddress);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.txtCategory = (TextView) convertView.findViewById(R.id.txtCategory);
            holder.imgProfile = (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgForward = (ImageView) convertView.findViewById(R.id.imgForword);
            holder.imgEdit = (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgNext= (ImageView) convertView.findViewById(R.id.imgNext);


//            holder.swipeLayout = (SwipeRevealLayout) convertView.findViewById(R.id.swipe_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(FinanceList.get(position).getOfficePhone().equals(""))
        {
            holder.txtPhone.setVisibility(View.GONE);
        }
        else
        {
            holder.txtPhone.setVisibility(View.VISIBLE);
        }

        if(FinanceList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else
        {
            holder.txtAddress.setVisibility(View.VISIBLE);
        }
       /* if(FinanceList.get(position).getCategory().equals(""))
        {
            holder.txtCategory.setVisibility(View.GONE);
        }
        else
        {
            holder.txtCategory.setVisibility(View.VISIBLE);
        }*/
        holder.txtName.setText(FinanceList.get(position).getName());
        holder.txtAddress.setText(FinanceList.get(position).getAddress());
        holder.txtPhone.setText(FinanceList.get(position).getOfficePhone());
      /*  if (FinanceList.get(position).getCategory().equals("Other"))
        {
            holder.txtCategory.setText(FinanceList.get(position).getCategory()+"-"+FinanceList.get(position).getOtherCategory());
        }
        else {
            holder.txtCategory.setText(FinanceList.get(position).getCategory());
        }*/
        holder.txtCategory.setText(FinanceList.get(position).getOtherCategory());
        //holder.imgProfile.setImageResource(FinanceList.get(position).getImage());
        File imgFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),FinanceList.get(position).getPhoto());
        if (imgFile.exists()) {
           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);*/
            imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgProfile,displayImageOptionsProfile);

        }

        /*byte[] photo=FinanceList.get(position).getPhoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgProfile.setImageBitmap(bmp);
*/
        if (!FinanceList.get(position).getPhotoCard().equals("")) {
            File imgFile1 = new File(preferences.getString(PrefConstants.CONNECTED_PATH),FinanceList.get(position).getPhotoCard());
            if (imgFile1.exists()) {
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                holder.imgForward.setImageBitmap(myBitmap);*/
                imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)),holder.imgForward,displayImageOptionsCard);
            }
           /* byte[] photoCard = FinanceList.get(position).getPhotoCard();
            Bitmap bmpCard = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);
            holder.imgForward.setImageBitmap(bmpCard);*/
            holder.imgForward.setVisibility(View.VISIBLE);
        }
        else{
            holder.imgForward.setVisibility(View.GONE);
        }


        holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, AddFormActivity.class);
                i.putExtra("Image",FinanceList.get(position).getPhotoCard());
                context.startActivity(i);
            }
        });



        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "FinanceData");
                Finance finance = FinanceList.get(position);
                i.putExtra("FinanceObject",finance);
                context.startActivity(i);
            }
        });
        holder.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, GrabConnectionActivity.class);
                preferences.putString(PrefConstants.SOURCE, "FinanceViewData");
                Finance finance = FinanceList.get(position);
                i.putExtra("FinanceObject",finance);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView txtName, txtAddress, txtPhone, txtType, txtCategory;
        ImageView imgProfile, imgForward,imgEdit;
        ImageView imgNext;
        SwipeRevealLayout swipeLayout;
    }
}