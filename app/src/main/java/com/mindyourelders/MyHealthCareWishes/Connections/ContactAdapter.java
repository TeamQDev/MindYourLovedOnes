package com.mindyourelders.MyHealthCareWishes.Connections;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Contact;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class ContactAdapter extends BaseAdapter implements Filterable {
    private ArrayList<Contact> mOriginalValues; // Original Values

    Context context;
    ArrayList<Contact> contactList;
    LayoutInflater lf;
    ViewHolder holder;

    public ContactAdapter(Context context, ArrayList<Contact> contactList) {
        mOriginalValues=contactList;
        this.context=context;
        this.contactList=contactList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
           convertView=lf.inflate(R.layout.row_contact,parent,false);
           holder=new ViewHolder();
            holder.txtConName= (TextView) convertView.findViewById(R.id.txtConName);
            holder.txtPhone= (TextView) convertView.findViewById(R.id.txtPhone);
            holder.txtHPhone= (TextView) convertView.findViewById(R.id.txtHPhone);
            holder.txtWPhone= (TextView) convertView.findViewById(R.id.txtWPhone);
            holder.txtEmail= (TextView) convertView.findViewById(R.id.txtEmail);
            holder.txtAddress= (TextView) convertView.findViewById(R.id.txtAddress);

            holder.imgConPhoto= (ImageView) convertView.findViewById(R.id.imgConPhoto);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        Contact c=contactList.get(position);
        holder.txtConName.setText(contactList.get(position).getName());

        if (!contactList.get(position).getPhone().equals("")) {
            holder.txtPhone.setText(c.getPhone());
            holder.txtPhone.setVisibility(View.VISIBLE);
        }
        else{
            holder.txtPhone.setVisibility(View.GONE);
        }

        if (!contactList.get(position).getHomePhone().equals("")) {
            holder.txtHPhone.setText(c.getHomePhone());
            holder.txtHPhone.setVisibility(View.VISIBLE);
        }
        else{
            holder.txtHPhone.setVisibility(View.GONE);
        }

        if (!contactList.get(position).getWorkPhone().equals("")) {
            holder.txtWPhone.setText(c.getWorkPhone());
            holder.txtWPhone.setVisibility(View.VISIBLE);
        }
        else{
            holder.txtWPhone.setVisibility(View.GONE);
        }

        if(contactList.get(position).getEmail().equals(""))
        {
            holder.txtEmail.setVisibility(View.GONE);
        }
        else {
            holder.txtEmail.setVisibility(View.VISIBLE);
            holder.txtEmail.setText(contactList.get(position).getEmail());
        }

        if(contactList.get(position).getAddress().equals(""))
        {
            holder.txtAddress.setVisibility(View.GONE);
        }
        else {
            holder.txtAddress.setVisibility(View.VISIBLE);
            holder.txtAddress.setText(contactList.get(position).getAddress());
        }

        if (contactList.get(position).getImage()!=null) {
            Bitmap photo = getImagedata(contactList.get(position).getImage());
            holder.imgConPhoto.setImageBitmap(photo);
        }
        else{
            holder.imgConPhoto.setImageResource(R.drawable.profile_photo);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentNewContact fragmentNewContact = new FragmentNewContact();
                Bundle args = new Bundle();
                args.putString("Name", contactList.get(position).getName());
                args.putString("Email", contactList.get(position).getEmail());
                args.putString("Phone", contactList.get(position).getPhone());
                args.putString("Address", contactList.get(position).getAddress());
                args.putString("HPhone", contactList.get(position).getHomePhone());
                args.putString("WPhone", contactList.get(position).getWorkPhone());
                args.putByteArray("Photo",contactList.get(position).getImage());
                fragmentNewContact.setArguments(args);
                ((GrabConnectionActivity)context).callFragment("NEWCONTACT",fragmentNewContact);
            }
        });
       // holder.imgConPhoto.setImageResource(contactList.get(position).getImage());

      /*holder.imgForword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDashboard ldf = new FragmentDashboard ();
                Bundle args = new Bundle();
                args.putString("Name", contactList.get(position).getName());
                args.putString("Relation", contactList.get(position).getRelationType());
                ldf.setArguments(args);

                ((BaseActivity)context).callFragment("DASHBOARD",ldf);
            }
        });*/

        return convertView;
    }

    public class ViewHolder
    {
        TextView txtConName, txtPhone,txtEmail,txtAddress,txtHPhone,txtWPhone;
        ImageView imgConPhoto,imgForword;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                contactList = (ArrayList<Contact>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Contact> FilteredArrList = new ArrayList<Contact>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Contact>(contactList); // saves the original data in mOriginalValues
                }

                /********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********/
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getName();
                        if (data.toLowerCase().contains(constraint.toString())) {

                            FilteredArrList.add(new Contact(mOriginalValues.get(i).getName(),mOriginalValues.get(i).getEmail(),mOriginalValues.get(i).getPhone(),mOriginalValues.get(i).getImage(),mOriginalValues.get(i).getAddress(),mOriginalValues.get(i).getHomePhone(),mOriginalValues.get(i).getWorkPhone()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
    // convert from byte array to bitmap
    public static Bitmap getImagedata(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

