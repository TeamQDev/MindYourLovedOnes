package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Dosage;

import java.util.ArrayList;

/**
 * Created by welcome on 9/19/2017.
 */

class DosageAdapter extends BaseAdapter {
    Context context;
    ArrayList<Dosage> dosageList;
    LayoutInflater lf;
    Holder holder;

    public DosageAdapter(Context context, ArrayList dosageList) {
        this.context = context;
        this.dosageList = dosageList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dosageList.size();
    }

    @Override
    public Object getItem(int position) {
        return dosageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_dosage, parent, false);
            holder = new Holder();
            holder.txtNote = (TextView) convertView.findViewById(R.id.txtMedicine);
            holder.txtDateTime = (TextView) convertView.findViewById(R.id.txtDose);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.imgDelete= (ImageView) convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtNote.setText(dosageList.get(position).getMedicine());
        holder.txtDateTime.setText(dosageList.get(position).getDose());




        //holder.imgProfile.setImageResource(student.getImgid());
       /* holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ViewEventActivity.class);
                intent.putExtra("Dosage",dosageList.get(position).getTxtNote());
                intent.putExtra("Date",dosageList.get(position).getTxtDate());
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    private class Holder {
        TextView txtNote, txtDateTime, txtTime;
        ImageView imgForward,imgEdit,imgDelete;
    }

}
