package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Note;

import java.util.ArrayList;

/**
 * Created by welcome on 9/18/2017.
 */

public class NoteAdapter extends BaseAdapter {
    Context context;
    ArrayList<Note> noteList;
    LayoutInflater lf;
    Holder holder;

    public NoteAdapter(Context context, ArrayList noteList) {
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
            convertView = lf.inflate(R.layout.row_note, parent, false);
            holder = new Holder();
            holder.txtNote = (TextView) convertView.findViewById(R.id.txtNote);
            holder.txtDateTime = (TextView) convertView.findViewById(R.id.txtDateTime);
            holder.imgForward= (ImageView) convertView.findViewById(R.id.imgForword);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.txtNote.setText(noteList.get(position).getTxtNote());
        holder.txtDateTime.setText(noteList.get(position).getTxtDate());
        //holder.imgProfile.setImageResource(student.getImgid());

        return convertView;
    }

    private class Holder {
        TextView txtNote, txtDateTime, txtTime;
        ImageView imgForward;
    }
}