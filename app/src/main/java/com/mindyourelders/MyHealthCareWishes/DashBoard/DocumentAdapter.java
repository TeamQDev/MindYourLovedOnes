package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Document;

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
           convertView=lf.inflate(R.layout.row_care_plan,parent,false);
           holder=new ViewHolder();
            holder.txtDocHeader= (TextView) convertView.findViewById(R.id.txtDocHeader);
          //  holder.txtDocDesc= (TextView) convertView.findViewById(R.id.txtDocDesc);
            holder.imgDocType= (ImageView) convertView.findViewById(R.id.imgDocType);


            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtDocHeader.setText(documentList.get(position).getName());
      //  holder.txtDocDesc.setText(documentList.get(position).getDesc());
        holder.imgDocType.setImageResource(documentList.get(position).getImage());

     convertView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ((CarePlanListActivity)context).CopyReadAssets(documentList.get(position).getDocument());
        ((CarePlanListActivity)context).onPDFClicked(Environment.getExternalStorageDirectory()
                + "/mhcw/"+documentList.get(position).getDocument());
       // ((CarePlanActivity)context).CopyAssets();

    }
      });
        return convertView;


    }



    public class ViewHolder
    {
        TextView txtDocHeader, txtDocDesc;
        ImageView imgDocType;
    }
}
