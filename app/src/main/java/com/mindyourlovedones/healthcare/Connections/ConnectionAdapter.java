package com.mindyourlovedones.healthcare.Connections;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedones.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedones.healthcare.DashBoard.FragmentDashboard;
import com.mindyourlovedones.healthcare.HomeActivity.BaseActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.customview.MySpinner;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedones.healthcare.model.RelativeConnection;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class ConnectionAdapter extends BaseSwipListAdapter {
    Context context;
    ArrayList<RelativeConnection> connectionList;
    LayoutInflater lf;
    ViewHolder holder;
    Preferences preferences;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    String[] Relationship = {"Aunt","Brother","Cousin","Dad","Daughter","Father-in-law","Friend","GrandDaughter","GrandFather","GrandMother","GrandSon","Husband","Mom","Mother-in-law","Neighbor","Nephew","Niece","Sister","Son","Uncle","Wife", "Other"};

    final CharSequence[] import_new = {"Import From Dropbox", "Create New"};

    public ConnectionAdapter(Context context, ArrayList<RelativeConnection> connectionList) {
        preferences=new Preferences(context);
        this.context=context;
        this.connectionList=connectionList;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        initImageLoader();
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return connectionList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return connectionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {

            if (position!=connectionList.size()) {
                convertView = lf.inflate(R.layout.row_connections, parent, false);
            }
            else if(position==connectionList.size()){
                convertView = lf.inflate(R.layout.row_connectionsadd, parent, false);
             /*   holder.txtConName = (TextView) convertView.findViewById(R.id.txtConName);
                holder.imgConPhoto = (ImageView) convertView.findViewById(R.id.imgConPhoto);*/
            }
            holder = new ViewHolder();
            holder.txtConName = (TextView) convertView.findViewById(R.id.txtConName);
            holder.txtConRelation = (TextView) convertView.findViewById(R.id.txtConRelation);
            //   holder.txtAddress= (TextView) convertView.findViewById(R.id.txtAddress);
            holder.imgConPhoto = (ImageView) convertView.findViewById(R.id.imgConPhoto);
            //   holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);

            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (position!=connectionList.size()) {
            holder.txtConName.setText(connectionList.get(position).getName());
            if (connectionList.get(position).getRelationType().equals("Other"))
            {
                holder.txtConRelation.setText(connectionList.get(position).getOtherRelation());
            }else {
                holder.txtConRelation.setText(connectionList.get(position).getRelationType());
            }

            if (!connectionList.get(position).getPhoto().equals("")) {
                File imgFile = new File(Environment.getExternalStorageDirectory()+"/MYLO/Master/",connectionList.get(position).getPhoto());
                //  if (imgFile.exists()) {
                //   Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                // holder.imgConPhoto.setImageBitmap(myBitmap);
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),holder.imgConPhoto,displayImageOptions);
            }
            else{
                holder.imgConPhoto.setImageResource(R.drawable.ic_profile_defaults);
            }
            //  }
            /*byte[] photo = connectionList.get(position).getPhoto();
            Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);*/
            //holder.imgConPhoto.setImageBitmap(bmp);
        }
/*
if (connectionList.get(position).getAddress().equals(""))
{
    holder.txtAddress.setText("#203,10 Downing Street, los Angeles, California.");
}
else {
    holder.txtAddress.setText(connectionList.get(position).getAddress());
}
*/




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position==connectionList.size())
                {
                    AlertDialog.Builder builders = new AlertDialog.Builder(context);
                    builders.setTitle("");
                    builders.setItems(import_new, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int itemPos) {
                            switch (itemPos) {
                                case 0: // import
                                    Intent in=new Intent(context, DropboxLoginActivity.class);
                                    in.putExtra("FROM","Backup");
                                    in.putExtra("ToDo","Individual");
                                    in.putExtra("ToDoWhat","Import");
                                    context.startActivity(in);
                                    dialog.dismiss();
                                    break;
                                case 1: // new
                                    preferences.putString(PrefConstants.SOURCE,"Connection");
                                    Intent i=new Intent(context,GrabConnectionActivity.class);
                                    context.startActivity(i);
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    });
                    builders.create().show();

                }
                else {
                    if (connectionList.get(position).getRelationType().equals("")) {
                        showInputDialog(context,connectionList.get(position).getId(),connectionList.get(position).getEmail());
                    } else {
                        FragmentDashboard ldf = new FragmentDashboard();
                        Bundle args = new Bundle();
                        args.putString("Name", connectionList.get(position).getName());
                        args.putString("Address", connectionList.get(position).getAddress());
                        args.putString("Relation", connectionList.get(position).getRelationType());
                        //String saveThis = Base64.encodeToString(connectionList.get(position).getPhoto(), Base64.DEFAULT);
                        preferences.putString(PrefConstants.USER_IMAGE, connectionList.get(position).getPhoto());
                        preferences.putString(PrefConstants.CONNECTED_NAME, connectionList.get(position).getName());
                        preferences.putString(PrefConstants.CONNECTED_USEREMAIL, connectionList.get(position).getEmail());
                        preferences.putInt(PrefConstants.CONNECTED_USERID, connectionList.get(position).getId());
                        String mail = connectionList.get(position).getEmail();
                        mail = mail.replace(".", "_");
                        mail = mail.replace("@", "_");
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                        ldf.setArguments(args);

                        ((BaseActivity) context).callFragment("DASHBOARD", ldf);
                    }
                }
            }
        });

        return convertView;
    }

    private void showInputDialog(final Context context, final int id,final String mail) {
        final Dialog customDialog;

        // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        //  View customView = inflater.inflate(R.layout.dialog_input, null);
        // Build the dialog
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_relation);
        customDialog.setCancelable(false);
        TextView btnAdd = (TextView) customDialog.findViewById(R.id.btnYes);

        final TextInputLayout tilOtherRelation = customDialog.findViewById(R.id.tilOtherRelation);
        final TextView txtOtherRelation= customDialog.findViewById(R.id.txtOtherRelation);
        final MySpinner spinnerRelation=customDialog.findViewById(R.id.spinnerRelation);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherRelation.setVisibility(View.VISIBLE);
                    tilOtherRelation.setHint("Other Relation");
                } else {
                    tilOtherRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= spinnerRelation.getSelectedItemPosition();
                if (i!=0) {
                    String relation = Relationship[i - 1];
                    String otherRelation="";
                    if (relation.equals("Other"))
                    {
                        otherRelation=txtOtherRelation.getText().toString();
                    }
                    else{
                        otherRelation="";
                    }
                    DBHelper dbHelpers=new DBHelper(context,"MASTER");
                    MyConnectionsQuery ms=new MyConnectionsQuery(context,dbHelpers);
                    Boolean flag = MyConnectionsQuery.updateMyConnectionsRelationData(id,relation,otherRelation);
                    if (flag == true) {
                        String email = mail.replace(".", "_");
                        String mail = email.replace("@", "_");
                        preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                        DBHelper dbHelper=new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
                        MyConnectionsQuery m=new MyConnectionsQuery(context,dbHelper);
                        Boolean flags = MyConnectionsQuery.updateMyConnectionsRelationData(1,relation,otherRelation);
                        if (flags == true) {
                            Toast.makeText(context, "You have edited relation Successfully", Toast.LENGTH_SHORT).show();
                        }
                        Toast.makeText(context, "You have edited relation Successfully", Toast.LENGTH_SHORT).show();
                        Intent myIntent = new Intent(context, BaseActivity.class);
                        context.startActivity(myIntent);
                        ((BaseActivity)context).finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
                customDialog.dismiss();
            }
        });

        customDialog.show();
    }

    public class ViewHolder
    {
        TextView txtConName, txtConRelation,txtAddress;
        ImageView imgConPhoto,imgForword;
    }

   /* @Override
    public boolean getSwipEnableByPosition(int position) {
        if(position % 2 == 0){
            return false;
        }
        return true;
    }*/
}


