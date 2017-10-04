package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Dosage;
import com.mindyourelders.MyHealthCareWishes.model.PrescribeImage;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;

import java.util.ArrayList;

/**
 * Created by welcome on 9/18/2017.
 */

class PrescriptionAdapter extends BaseAdapter {
    Context context;
    ArrayList<Prescription> prescriptionList;
    LayoutInflater lf;
    Holder holder;

    public PrescriptionAdapter(Context context, ArrayList prescriptionList) {
        this.context = context;
        this.prescriptionList = prescriptionList;
        lf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return prescriptionList.size();
    }

    @Override
    public Object getItem(int position) {
        return prescriptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.row_prescription, parent, false);
            holder = new Holder();
            holder.txtDoctor = (TextView) convertView.findViewById(R.id.txtDoctor);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.llImg = (LinearLayout) convertView.findViewById(R.id.llImg);
            holder.llPrescription= (LinearLayout) convertView.findViewById(R.id.llPrescription);
            holder.imgForward = (ImageView) convertView.findViewById(R.id.imgForword);
            ArrayList<PrescribeImage> PrescriptionImageList;
           if (prescriptionList.get(position).getPrescriptionImageList()!=null) {
                PrescriptionImageList = prescriptionList.get(position).getPrescriptionImageList();
                for (int i=0;i<PrescriptionImageList.size();i++)
                {
                    LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = vi.inflate(R.layout.row_img, null);
                    ImageView imgView = (ImageView) v.findViewById(R.id.img);
                    byte[] bytea=PrescriptionImageList.get(i).getImage();
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytea, 0, bytea.length);
                    imgView.setImageBitmap(bmp);
                    holder.llImg.addView(v);
                }
            }
            if (prescriptionList.get(position).getDosageList()!=null)
            {
                ArrayList<Dosage> DosageList = prescriptionList.get(position).getDosageList();
                for (int i = 0; i<DosageList.size(); i++)
                {
                    LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v = vi.inflate(R.layout.row_txt, null);
                    TextView textView = (TextView) v.findViewById(R.id.txt);
                    textView.setText(""+DosageList.get(i).getMedicine()+" "+DosageList.get(i).getDose());
                    holder.llPrescription.addView(v);
                }
            }
            holder.txtDoctor.setText(prescriptionList.get(position).getDoctor());
            holder.txtDate.setText(prescriptionList.get(position).getDates());



            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        /*holder.txtNote.setText(prescriptionList.get(position).getTxtNote());
        holder.txtDateTime.setText(prescriptionList.get(position).getTxtDate());
        //holder.imgProfile.setImageResource(student.getImgid());
        holder.imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ViewEventActivity.class);
                intent.putExtra("Prescription",prescriptionList.get(position).getTxtNote());
                intent.putExtra("Date",prescriptionList.get(position).getTxtDate());
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    private class Holder {
        TextView txtDoctor, txtDate, txtTime;
        LinearLayout llImg,llPrescription;
        ImageView imgForward;
    }
}