package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.PrescribeImage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/19/2017.
 */

class ImageAdapter  extends BaseAdapter {
    Context context;
    ArrayList<PrescribeImage> imageList;
    LayoutInflater lf;
    Holder holder;

    public ImageAdapter(Context context, ArrayList imageList) {
        this.context = context;
        this.imageList = imageList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.img_presc, parent, false);
            holder = new Holder();
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        byte[] imageData=imageList.get(position).getImage();
        Bitmap bm= BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        holder.img.setImageBitmap(bm);
        //holder.imgProfile.setImageResource(student.getImgid());
       /* holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ViewEventActivity.class);
                intent.putExtra("PrescribeImage",imageList.get(position).getTxtNote());
                intent.putExtra("Date",imageList.get(position).getTxtDate());
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    private class Holder {
        ImageView img;
    }

}
