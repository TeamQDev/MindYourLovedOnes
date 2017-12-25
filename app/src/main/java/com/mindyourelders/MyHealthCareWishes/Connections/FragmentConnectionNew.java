package com.mindyourelders.MyHealthCareWishes.Connections;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/26/2017.
 */

public class FragmentConnectionNew extends Fragment implements View.OnClickListener {
    View rootview;
    GridView lvConnection;
    ArrayList<RelativeConnection> connectionList;
    TextView txtAdd;
    //RelativeLayout llAddConn;
    TextView txtTitle, txtName,txtDrawerName;
    ImageView imgNoti, imgProfile, imgLogo, imgPdf,imgDrawerProfile;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    PersonalInfo personalInfo;
    RelativeLayout leftDrawer;
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
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
        dbHelper = new DBHelper(getActivity());
        PersonalInfoQuery p = new PersonalInfoQuery(getActivity(), dbHelper);
        MyConnectionsQuery m = new MyConnectionsQuery(getActivity(), dbHelper);
    }

    private void setListData() {
        connectionAdapter = new ConnectionAdapter(getActivity(), connectionList);
        lvConnection.setAdapter(connectionAdapter);
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        //llAddConn.setOnClickListener(this);
        imgLogo.setOnClickListener(this);
    }

    private void initUI() {
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
        imgLogo.setVisibility(View.VISIBLE);
        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;
        Toast.makeText(getActivity(), deviceMan + " " + deviceName, Toast.LENGTH_LONG).show();
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
                if (position != connectionList.size()) {
                    if (position != 0) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_common);
                    /* LayoutInflater lf = (LayoutInflater) getActivity()
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogview = lf.inflate(R.layout.dialog_common, null);*/
                        TextView title = (TextView) dialog.findViewById(R.id.txtTitle);
                        title.setText("Delete");
                        TextView body = (TextView) dialog.findViewById(R.id.txtMsg);
                        body.setText("Do you want to Delete " + connectionList.get(position).getName() + " profile?");

                        TextView btnYes = (TextView) dialog
                                .findViewById(R.id.btnYes);
                        btnYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                deleteConnection(connectionList.get(position).getId());
                                dialog.dismiss();
                            }
                        });
                        TextView btnNo = (TextView) dialog
                                .findViewById(R.id.btnNo);
                        btnNo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    } else {
                        Toast.makeText(getActivity(), "You can not delete user profile", Toast.LENGTH_SHORT).show();
                    }
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
      //  personalInfo = PersonalInfoQuery.fetchProfile(preferences.getString(PrefConstants.USER_EMAIL));
        personalInfo = PersonalInfoQuery.fetchProfiles();
        preferences.putInt(PrefConstants.USER_ID, personalInfo.getId());
        preferences.putString(PrefConstants.USER_NAME,personalInfo.getName());
        preferences.putString(PrefConstants.USER_PROFILEIMAGE,personalInfo.getPhoto());
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
    private void getData() {
        connectionList = MyConnectionsQuery.fetchAllRecord(preferences.getInt(PrefConstants.USER_ID), 1);


        /*RelativeConnection P1 = new RelativeConnection();
        P1.setName("Caiete Charlo");
        P1.setRelationType("Self");
        P1.setImage(R.drawable.circular_profile);

        RelativeConnection P2 = new RelativeConnection();
        P2.setName("Chuck Charlo");
        P2.setRelationType("Husband");
        P2.setImage(R.drawable.profile_circle);

        RelativeConnection P3 = new RelativeConnection();
        P3.setName("Prince Charlo");
        P3.setRelationType("Son");
        P3.setImage(R.drawable.profile_round);

        RelativeConnection P4 = new RelativeConnection();
        P4.setName("Mary Charlo");
        P4.setRelationType("Daughter");
        P4.setImage(R.drawable.profile_rounds);

        RelativeConnection P5 = new RelativeConnection();
        P5.setName("Hilary Charlo");
        P5.setRelationType("Mother");
        P5.setImage(R.drawable.mother);


        RelativeConnection P6 = new RelativeConnection();
        P6.setName("John Charlo");
        P6.setRelationType("Father");
        P6.setImage(R.drawable.father);

        RelativeConnection P7 = new RelativeConnection();
        P7.setName("James Charlo");
        P7.setRelationType("Brother");
        P7.setImage(R.drawable.brother);


        connectionList.add(P1);
        connectionList.add(P2);
        connectionList.add(P3);
        connectionList.add(P4);
        connectionList.add(P5);
        connectionList.add(P6);
        connectionList.add(P7);*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgLogo:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.myhealthcarewishes.com/"));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
        getData();
        setListData();
        String image=preferences.getString(PrefConstants.USER_PROFILEIMAGE);
        //byte[] photo = Base64.decode(image, Base64.DEFAULT);
        txtDrawerName.setText(preferences.getString(PrefConstants.USER_NAME));
        if (!image.equals("")) {
            File imgFile = new File(image);
            if (imgFile.exists()) {
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgDrawerProfile,displayImageOptions);
               /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgDrawerProfile.setImageBitmap(myBitmap);*/
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
        }
    }
}
