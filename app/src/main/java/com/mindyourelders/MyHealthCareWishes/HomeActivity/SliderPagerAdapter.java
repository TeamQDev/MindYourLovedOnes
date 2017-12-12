package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 9/12/2017.
 */

public class SliderPagerAdapter extends PagerAdapter {

    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<String> image_arraylist;
    ArrayList<String> slider_text_list;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    public SliderPagerAdapter(Activity activity, ArrayList<String> image_arraylist, ArrayList<String> slider_text_list) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
        this.slider_text_list=slider_text_list;
        initImageLoader();
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ins_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_slider, container, false);
        ImageView im_slider = (ImageView) view.findViewById(R.id.im_slider);
        TextView textview= (TextView) view.findViewById(R.id.text);
//        textview.setText(slider_text_list.get(position));
       im_slider.setAdjustViewBounds(true);
      im_slider.setScaleType(ImageView.ScaleType.FIT_XY);
        File imgFile = new File(image_arraylist.get(position));
        if (imgFile.exists()) {
            imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),im_slider,displayImageOptions);
                            /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);*/
        }else{
            im_slider.setImageResource(R.drawable.ins_card);
        }
       // imageLoader.displayImage(String.valueOf(image_arraylist.get(position)),im_slider,displayImageOptions);
       /* byte[] photo=image_arraylist.get(position);
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        im_slider.setImageBitmap(bmp);*/
      /*  Picasso.with(activity.getApplicationContext())
                .load(image_arraylist.get(position))
                .placeholder(R.drawable.im) // optional
                .error(R.drawable.hosp)         // optional
                .into(im_slider);*/


        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}

