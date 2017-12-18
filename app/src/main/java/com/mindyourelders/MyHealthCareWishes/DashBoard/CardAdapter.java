package com.mindyourelders.MyHealthCareWishes.DashBoard;

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

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Card;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.imgCard;

/**
 * Created by welcome on 9/20/2017.
 */

class CardAdapter extends BaseAdapter{
    Context context;
    ArrayList<Card> cardList;
    LayoutInflater lf;
    Holder holder;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    public CardAdapter(Context context, ArrayList<Card> cardList) {
        this.context=context;
        this.cardList=cardList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }
    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_card, parent, false);
            holder = new Holder();
            holder.imgCard = (ImageView) convertView.findViewById(imgCard);
            holder.txtProvider= (TextView) convertView.findViewById(R.id.txtProviderValue);
            holder.txtType= (TextView) convertView.findViewById(R.id.txtTypeValue);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
       /* byte[] photo=cardList.get(position).getImgFront();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgCard.setImageBitmap(bmp);*/
        File imgFile = new File(cardList.get(position).getImgFront());
        if (imgFile.exists()) {
           /* BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),bmOptions);
            Matrix matrix = new Matrix();
            if (bitmap.getHeight()>bitmap.getWidth()) {
                matrix.postRotate(90);
            }
            else
            {
                matrix.postRotate(0);
            }
            //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
            holder.imgCard.setImageBitmap(rotatedBitmap);*/
            imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgCard,displayImageOptions);
           /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            holder.imgProfile.setImageBitmap(myBitmap);*/
        }
     // imageLoader.displayImage(String.valueOf(cardList.get(position).getImgFront()),holder.imgCard,displayImageOptions);
     //   holder.imgCard.setImageResource(cardList.get(position).getImgFront());
        holder.txtProvider.setText(cardList.get(position).getName());
        holder.txtType.setText(cardList.get(position).getType());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AddCardActivity.class);
                i.putExtra("CardObject",cardList.get(position));
                i.putExtra("IsEdit",true);
                context.startActivity(i);
            }
        });
        return convertView;
    }

    private class Holder {
        ImageView imgCard,imgCardBack,imgBack,imgNext,imgEdit;
        TextView txtProvider,txtType;
    }

}
