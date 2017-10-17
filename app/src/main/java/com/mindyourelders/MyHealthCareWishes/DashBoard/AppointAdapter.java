package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.txtDate;
import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.txtDateTime;

/**
 * Created by welcome on 10/12/2017.
 */

class AppointAdapter extends BaseAdapter {
    Context context;
    ArrayList<Appoint> noteList;
    LayoutInflater lf;
    Holder holder;

    public AppointAdapter(Context context, ArrayList noteList) {
        this.context = context;
        this.noteList = noteList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_appoint, parent, false);
            holder = new Holder();
            holder.txtDoctor = (TextView) convertView.findViewById(R.id.txtDoctor);
            holder.txtDateTime = (TextView) convertView.findViewById(txtDateTime);
            holder.txtFrequency = (TextView) convertView.findViewById(R.id.txtFrequency);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.txtDate= (TextView) convertView.findViewById(txtDate);
            holder.imgForward= (ImageView) convertView.findViewById(R.id.imgForword);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtDoctor.setText(noteList.get(position).getType());
        holder.txtDateTime.setText(noteList.get(position).getDate());
        holder.txtFrequency.setText(noteList.get(position).getFrequency());
        holder.txtType.setText(noteList.get(position).getDoctor());
        //holder.imgProfile.setImageResource(student.getImgid());
        holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(context,ViewEventActivity.class);
                intent.putExtra("AppointObject",noteList.get(position));
                context.startActivity(intent);*/
            }
        });
       /* holder.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        holder.txtDateTime.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
            }
        });*/

        return convertView;
    }

    private class Holder {
        TextView txtDoctor, txtDateTime, txtFrequency,txtType,txtDate;
        ImageView imgForward;
    }

}
