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

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Card;

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
    public CardAdapter(Context context, ArrayList<Card> cardList) {
        this.context=context;
        this.cardList=cardList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
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
        byte[] photo=cardList.get(position).getImgFront();
        Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
        holder.imgCard.setImageBitmap(bmp);
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
