package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Appoint;

import java.util.HashMap;
import java.util.List;

import static com.mindyourlovedones.healthcare.HomeActivity.R.id.txtDate;
import static com.mindyourlovedones.healthcare.HomeActivity.R.id.txtDateTime;

/**
 * Created by welcome on 11/18/2017.
 */

class ExpandableAdapter extends BaseExpandableListAdapter {
    Context context;
    List<Appoint> listDataHeader;
    HashMap<String, List<DateClass>> listDataChild;
    LayoutInflater inflater;
    public ExpandableAdapter(Context context, List<Appoint> listDataHeader, HashMap<String, List<DateClass>> listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;


        Appoint appoint= (Appoint) getGroup(groupPosition);

        if (convertView == null) {
            convertView = inflater
                    .inflate(R.layout.row_appoint, null);
            holder=new ViewHolder();

            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        holder.txtDoctor = (TextView) convertView.findViewById(R.id.txtDoctor);
        holder.txtDateTime = (TextView) convertView.findViewById(txtDateTime);
        holder.txtFrequency = (TextView) convertView.findViewById(R.id.txtFrequency);
        holder.txtType = (TextView) convertView.findViewById(R.id.txtType);
        holder.txtDate= (TextView) convertView.findViewById(txtDate);
        holder.imgForward= (ImageView) convertView.findViewById(R.id.imgForword);
        holder.imgEdit= (ImageView) convertView.findViewById(R.id.imgEdit);
        holder.txtDoctor.setText(appoint.getDoctor());
        holder.txtDateTime.setText(appoint.getDate());
        //    holder.txtFrequency.setText(noteList.get(position).getFrequency());
        if (appoint.getType().equals("Other"))
        {
            holder.txtType.setText(appoint.getOtherDoctor());
        }
        else{
            holder.txtType.setText(appoint.getType());
        }
        if (appoint.getFrequency().equals("Other"))
        {
            holder.txtFrequency.setText(appoint.getOtherFrequency());
        }
        else{
            holder.txtFrequency.setText(appoint.getFrequency());
        }
  /*      if ( mListDataHeader.get(groupPosition).toString()=="Live Scores") {*/
        holder.txtDoctor.setCompoundDrawablesWithIntrinsicBounds(0, 0, isExpanded ? R.drawable.uparrow :R.drawable.downarrow , 0);
/*
        }
        else
        {
            holder.headerLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }*/
        // headerLabel.setTypeface(null, Typeface.BOLD);

       /* if(groupPosition==0) {*/
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
      /*  if(mListDataHeader.get(groupPosition)=="Live Scores")
        {*/
        final DateClass childText = (DateClass) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater)context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.date_row, null);
            holder=new ViewHolder();
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        TextView datetime= (TextView) convertView.findViewById(R.id.txtDateTime);
        datetime.setText("Completion Date:  "+ childText.getDate());
        // }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class ViewHolder
    {
        TextView txtDoctor, txtDateTime, txtFrequency,txtType,txtDate;
        RelativeLayout rlMain;
        LinearLayout llDate;
        ImageView imgForward,imgEdit;
    }
}
