package com.mindyourlovedones.healthcare.Connections;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedones.healthcare.DashBoard.DropboxLoginActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by varsha on 8/26/2017.
 */

public class FragmentConnectionNew extends Fragment implements View.OnClickListener {
    View rootview;
    GridView lvConnection;
    ArrayList<RelativeConnection> connectionList;
    TextView txtAdd,txtMsg,txtFTU;
    //RelativeLayout llAddConn;
    TextView txtTitle, txtName,txtDrawerName;
    ImageView imgNoti, imgProfile, imgLogo, imgPdf,imgDrawerProfile;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    // PersonalInfo personalInfo;
    RelativeLayout leftDrawer;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlGuide;
    final CharSequence[] delete_backup = {"Delete Profile", "Share Profile"};
    final CharSequence[] backup_profile = {"Share Profile"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_connection_new, null);
        initComponent();

        // getProfile();

        // getData();
        initUI();
        initListener();
        initImageLoader();
        return rootview;
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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(),"MASTER");
        // PersonalInfoQuery p = new PersonalInfoQuery(getActivity(), dbHelper);
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
    }

    public void setListData() {
        if (connectionList.size()!=0) {
            connectionAdapter = new ConnectionAdapter(getActivity(), connectionList);
            lvConnection.setAdapter(connectionAdapter);
            if (connectionList.size()>1) {
                rlGuide.setVisibility(View.GONE);
            }
            else{
                rlGuide.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        //llAddConn.setOnClickListener(this);
        imgLogo.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>add</b> a Profile click the <b>plus</b> box." +
                "You will be brought to the Personal Information Screen.<br>" +
                "If the person is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load information.<br><br>" +
                "Add as much or as little information as you want. The goal is to create less stress and allow you to focus on the issue(s) at hand.<br><br>" +
                "When completed click on the green bar at the bottom of the screen that says <b>Add Profile</b>.<br><br>" +
                "To <b>delete</b> a Profile <b>long press</b> on profile box.";
        txtMsg.setText(Html.fromHtml(msg));
        txtFTU=rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMsg.setVisibility(View.VISIBLE);
            }
        });
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("PROFILES");
        imgPdf = (ImageView) getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgProfile = (ImageView) getActivity().findViewById(R.id.imgProfile);
        txtName = (TextView) getActivity().findViewById(R.id.txtName);
        leftDrawer = (RelativeLayout) getActivity().findViewById(R.id.leftDrawer);
        txtDrawerName = (TextView) leftDrawer.findViewById(R.id.txtDrawerName);
        imgDrawerProfile = (ImageView) leftDrawer.findViewById(R.id.imgDrawerProfile);
        txtName.setVisibility(View.GONE);
        imgProfile.setVisibility(View.GONE);
        imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        imgLogo = (ImageView) getActivity().findViewById(R.id.imgLogo);
        rlGuide=rootview.findViewById(R.id.rlGuide);
        imgLogo.setVisibility(View.VISIBLE);
        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        //   Toast.makeText(getActivity(), deviceMan + " " + deviceName, Toast.LENGTH_LONG).show();
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        //llAddConn = (RelativeLayout) rootview.findViewById(llAddConn);
        lvConnection = (GridView) rootview.findViewById(R.id.lvConnection);
       /* if (connectionList.size()!=0||connectionList!=null)
        {
            setListData();
        }*/
        lvConnection.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //  Toast.makeText(getActivity(),"Long Pressed",Toast.LENGTH_SHORT).show();
                if (position != 0) {
                if (position != connectionList.size()) {
                        AlertDialog.Builder builders = new AlertDialog.Builder(getActivity());
                        builders.setTitle("");
                        builders.setItems(delete_backup, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int itemPos) {
                                switch (itemPos) {
                                    case 0: // delete
                                            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                            alert.setTitle("Delete");
                                            alert.setMessage("Do you want to Delete " + connectionList.get(position).getName() + "'s profile?");
                                            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String mail = connectionList.get(position).getEmail();
                                                    mail = mail.replace(".", "_");
                                                    mail = mail.replace("@", "_");
                                                    preferences.putString(PrefConstants.CONNECTED_USERDB, mail);
                                                    preferences.putString(PrefConstants.CONNECTED_PATH, Environment.getExternalStorageDirectory() + "/MYLO/" + preferences.getString(PrefConstants.CONNECTED_USERDB) + "/");
                                                    deleteConnection(connectionList.get(position).getId());
                                                    dialog.dismiss();
                                                }
                                            });
                                            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                    dialog.dismiss();
                                                }
                                            });
                                            alert.show();
                                        dialog.dismiss();
                                        break;
                                    case 1: // backup
                                        Intent i=new Intent(getActivity(), DropboxLoginActivity.class);
                                        i.putExtra("FROM","Backup");
                                        i.putExtra("ToDo","Individual");
                                        i.putExtra("ToDoWhat","Share");
                                        String mail=connectionList.get(position).getEmail();
                                        mail=mail.replace(".","_");
                                        mail=mail.replace("@","_");
                                        preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
                                        preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
                                        startActivity(i);
                                        dialog.dismiss();
                                        break;
                                }
                            }
                        });
                        builders.create().show();
                    }
                }
                else {
                    AlertDialog.Builder builders = new AlertDialog.Builder(getActivity());
                    builders.setTitle("");
                    builders.setItems(backup_profile, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int itemPos) {
                            switch (itemPos) {
                                case 0: // backup
                                    Intent i=new Intent(getActivity(), DropboxLoginActivity.class);
                                    i.putExtra("FROM","Backup");
                                    i.putExtra("ToDo","Individual");
                                    i.putExtra("ToDoWhat","Share");
                                    String mail=connectionList.get(position).getEmail();
                                    mail=mail.replace(".","_");
                                    mail=mail.replace("@","_");
                                    preferences.putString(PrefConstants.CONNECTED_USERDB,mail);
                                    preferences.putString(PrefConstants.CONNECTED_PATH,Environment.getExternalStorageDirectory()+"/MYLO/"+preferences.getString(PrefConstants.CONNECTED_USERDB)+"/");
                                    startActivity(i);
                                    dialog.dismiss();
                                    break;
                            }
                        }
                    });
                    builders.create().show();
                }


                return false;
            }
        });

        //lvConnection.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        //SwipeMenuCreation s=new SwipeMenuCreation();
        //SwipeMenuCreator creator=s.createMenu(getActivity());
        //  lvConnection.setMenuCreator(creator);
      /*  lvConnection.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                RelativeConnection item = connectionList.get(position);
                switch (index) {
                    case 0:
                        // open
                      //  open(item);
                        break;
                    case 1:
                        // delete
                        if (item.getEmail().equals(preferences.getString(PrefConstants.USER_EMAIL)))
                        {
                            Toast.makeText(getActivity(),"You can not delete self connection",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            deleteConnection(item);
                        }
                       // mAppList.remove(position);
                       // mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        // set SwipeListener
        lvConnection.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });


*/
    }

    private void getProfile() {
        dbHelper = new DBHelper(getActivity(),"MASTER");
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
        RelativeConnection connection = MyConnectionsQuery.fetchOneRecord("Self");
        preferences.putInt(PrefConstants.USER_ID, connection.getUserid());
        preferences.putString(PrefConstants.USER_NAME,connection.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE,connection.getPhoto());
    }

    /* private void deleteConnection(RelativeConnection item) {
         boolean flag= MyConnectionsQuery.deleteRecord(item.getEmail());
         if(flag==true)
         {
             Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
             getData();
             setListData();
         }
     }
 */
    public void getData() {
        DBHelper dbHelper=new DBHelper(getActivity(),"MASTER");
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
        connectionList = MyConnectionsQuery.fetchAllRecord();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLogo:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://mindyour-lovedones.com/"));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // getProfile();
        getProfile();
        getData();
        setListData();
        String image=preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        //byte[] photo = Base64.decode(image, Base64.DEFAULT);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            File imgFile = new File(Environment.getExternalStorageDirectory()+"/MYLO/Master/",image);
            if (imgFile.exists()) {
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgDrawerProfile,displayImageOptions);
            }
        }else{
            imgDrawerProfile.setImageResource(R.drawable.ic_profile_defaults);
        }
    }

    public void deleteConnection(int id) {
        boolean flag = MyConnectionsQuery.deleteRecord(id);
        if (flag == true) {
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setListData();

            File dir = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
           /* File dir = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
            if (dir.isDirectory())
            {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++)
                {
                    new File(dir, children[i]).delete();
                }
            }*/
            /*File file=new File(preferences.getString(PrefConstants.CONNECTED_PATH));
          //  File data=getActivity().getDatabasePath( preferences.getString(PrefConstants.CONNECTED_USERDB)+".db");
            if (file.exists())
            {
                file.delete();
            }*/

           /* String currentDBPath = "//data//com.mindyourlovedones.healthcare.HomeActivity"
                    + "//databases//" + preferences.getString(PrefConstants.CONNECTED_USERDB)+".db";*/
           /* DBHelper dbHelper=new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
            MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
            m.dropTable();*/
        }
    }

}
