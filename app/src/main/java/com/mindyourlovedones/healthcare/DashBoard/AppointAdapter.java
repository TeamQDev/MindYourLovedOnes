package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Appoint;

import java.util.ArrayList;

import static com.mindyourlovedones.healthcare.HomeActivity.R.id.txtDate;
import static com.mindyourlovedones.healthcare.HomeActivity.R.id.txtDateTime;

/**
 * Created by welcome on 10/12/2017.
 */

class AppointAdapter extends BaseAdapter {
    Context context;
    ArrayList<Appoint> noteList;
    LayoutInflater lf;
    Holder holder;
    boolean flag;

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
        Appoint a=noteList.get(position);
        final ArrayList<DateClass> dates=a.getDateList();
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_appoint, parent, false);
            holder = new Holder();

            holder.txtDoctor = (TextView) convertView.findViewById(R.id.txtDoctor);
            holder.txtDateTime = (TextView) convertView.findViewById(txtDateTime);
            holder.txtFrequency = (TextView) convertView.findViewById(R.id.txtFrequency);
            holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
            holder.txtDate= (TextView) convertView.findViewById(txtDate);
            holder.imgForward= (ImageView) convertView.findViewById(R.id.imgForword);
            holder.llDate= (LinearLayout) convertView.findViewById(R.id.llDate);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (noteList.get(position).getDoctor().equals(""))
        {
            holder.txtDoctor.setVisibility(View.GONE);
        }
        else{
            holder.txtDoctor.setVisibility(View.VISIBLE);
            holder.txtDoctor.setText(noteList.get(position).getDoctor());
        }

        /*if (noteList.get(position).getDate().equals(""))
        {
            holder.txtDateTime.setVisibility(View.GONE);
        }
        else{
            holder.txtDateTime.setVisibility(View.VISIBLE);
            holder.txtDateTime.setText(noteList.get(position).getDate());
        }*/

        //holder.txtDateTime.setText(noteList.get(position).getDate());
       // LinearLayout llDate= (LinearLayout) convertView.findViewById(R.id.llDate);
       /* for (int i=0;i<dates.size()+1;i++) {

            View helperview = lf.inflate(R.layout.date_row, null);
            holder.llDate.addView(helperview);
            TextView datetime = (TextView) helperview.findViewById(R.id.txtDateTime);

            if (i == dates.size()) {
                datetime.setText("Add +");
                datetime.setTextColor(context.getResources().getColor(R.color.colorBlue));
                datetime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MedicalAppointActivity) context).SetDate(noteList.get(position), position);
                    }
                });
            } else {
                datetime.setText("Completion Date:  " + dates.get(i).getDate());
                if (i % 2 == 0) {
                    datetime.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                } else {
                    datetime.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
                }
            }
        }*/
    //    holder.txtFrequency.setText(noteList.get(position).getFrequency());
        if (noteList.get(position).getType().equals("Other"))
        {
            holder.txtType.setText(noteList.get(position).getOtherDoctor());
        }
        else{
            holder.txtType.setText(noteList.get(position).getType());
        }

        if (noteList.get(position).getFrequency().equals(""))
        {
            holder.txtFrequency.setVisibility(View.GONE);
        }
        else if (noteList.get(position).getFrequency().equals("Other"))
        {
            holder.txtFrequency.setVisibility(View.VISIBLE);
            holder.txtFrequency.setText(noteList.get(position).getOtherFrequency());
        }
        else{
            holder.txtFrequency.setVisibility(View.VISIBLE);
            holder.txtFrequency.setText(noteList.get(position).getFrequency());
        }




      /*  ArrayList<Date> datesliST=new ArrayList<>();
        {
            for (int i=0;i<dates.size();i++) {
                String dtStart = "2010-10-15T09:27:37Z";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    Date date = (Date) format.parse(dtStart);
                    datesliST.add(date);
                    System.out.println(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        Collections.sort(datesliST, new Comparator<Date>(){
            public int compare(Date date1, Date date2){
                return date1.after(date2);
            }
        });*/

        //holder.imgProfile.setImageResource(student.getImgid());
       /* final View finalConvertView = convertView;
        holder.txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll= (LinearLayout) finalConvertView.findViewById(R.id.llDate);

              *//*  if ( ll.getVisibility() == View.GONE)
                {
                    //expandedChildList.set(arg2, true);
                    ll.setVisibility(View.VISIBLE);
                }
                else
                {
                    //expandedChildList.set(arg2, false);
                    ll.setVisibility(View.GONE);
                }*//*
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appoint a=noteList.get(position);
                Intent intent=new Intent(context,AddAppointmentActivity.class);
                intent.putExtra("FROM","View");
                intent.putExtra("AppointObject",a);
                context.startActivity(intent);
            }
        });*/
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
        RelativeLayout rlMain;
        LinearLayout llDate;
        ImageView imgForward,imgEdit;
    }

}
