package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Document;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class DocumentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Document> documentList;
    LayoutInflater lf;

    ViewHolder holder;

    public DocumentAdapter(Context context, ArrayList<Document> documentList) {
        this.context=context;
        this.documentList=documentList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return documentList.size();
    }

    @Override
    public Object getItem(int position) {
        return documentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
           convertView=lf.inflate(R.layout.row_care_plan_new,parent,false);
            holder=new ViewHolder();
            holder.txtDocHeader= (TextView) convertView.findViewById(R.id.txtDocHeader);
            holder.txtDocTime= (TextView) convertView.findViewById(R.id.txtDocTime);
            holder.txtDocDate= (TextView) convertView.findViewById(R.id.txtDocDate);
            holder.imgDocType= (ImageView) convertView.findViewById(R.id.imgDocType);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgNext);
            holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
            holder.txtDocDate.setVisibility(View.VISIBLE);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        if (documentList.get(position).getType().equals("Other"))
        {
            holder.txtDocHeader.setText(documentList.get(position).getType()+" - "+documentList.get(position).getOtherDoc());
        }else{
            holder.txtDocHeader.setText(documentList.get(position).getType());
        }
        if (documentList.get(position).getPerson().equals("")) {
            holder.txtDocTime.setVisibility(View.GONE);
        }
        else{
            holder.txtDocTime.setVisibility(View.VISIBLE);
            holder.txtDocTime.setText(documentList.get(position).getPerson());
        }
        if (documentList.get(position).getDate().equals("")) {
            holder.txtDocDate.setVisibility(View.GONE);
        }
        else{
            holder.txtDocDate.setVisibility(View.VISIBLE);
            holder.txtDocDate.setText(documentList.get(position).getDate());
        }
        holder.imgDocType.setImageResource(documentList.get(position).getImage());
        holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AddDocumentActivity.class);
                i.putExtra("GoTo","View");
                i.putExtra("Path","Yes");
               /* if (position>3)
                {

                }
                else
                {
                    i.putExtra("Path","No");
                }*/
                i.putExtra("DocumentObject",documentList.get(position));
                context.startActivity(i);
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,AddDocumentActivity.class);
                i.putExtra("GoTo","Edit");
                i.putExtra("Path","Yes");
               /* if (position>3)
                {

                }
                else
                {
                    i.putExtra("Path","No");
                }*/
                i.putExtra("DocumentObject",documentList.get(position));
                context.startActivity(i);
            }
        });


     /*convertView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


    }
      });*/
        return convertView;


    }



    public class ViewHolder
    {
        TextView txtDocHeader, txtDocDate,txtDocTime;
        ImageView imgDocType,imgForword,imgEdit;
    }
}
