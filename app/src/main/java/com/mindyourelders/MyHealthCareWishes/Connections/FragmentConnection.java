package com.mindyourelders.MyHealthCareWishes.Connections;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

/**
 * Created by varsha on 8/26/2017.
 */

public class FragmentConnection extends Fragment implements View.OnClickListener {
    View rootview;
    SwipeMenuListView lvConnection;
    ArrayList<RelativeConnection> connectionList;
    TextView txtAdd;
    RelativeLayout llAddConn;
    TextView txtTitle,txtName;
    ImageView imgNoti,imgProfile,imgPdf;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_connection, null);
        initComponent();
        getData();
        initUI();
        initListener();
        return rootview;
    }

    private void initComponent() {
        preferences=new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        connectionAdapter = new ConnectionAdapter(getActivity(), connectionList);
        lvConnection.setAdapter(connectionAdapter);
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("My Connections");
        imgProfile= (ImageView) getActivity().findViewById(R.id.imgProfile);
        imgPdf= (ImageView) getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        txtName= (TextView) getActivity().findViewById(R.id.txtName);
        txtName.setVisibility(View.GONE);
        imgProfile.setVisibility(View.GONE);
        imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddConn = (RelativeLayout) rootview.findViewById(R.id.llAddConn);
        lvConnection = (SwipeMenuListView) rootview.findViewById(R.id.lvConnection);
        if (connectionList.size()!=0||connectionList!=null)
        {
            setListData();
        }
        lvConnection.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvConnection.setMenuCreator(creator);
        lvConnection.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
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



    }

    private void deleteConnection(RelativeConnection item) {
      /*  boolean flag= MyConnectionsQuery.deleteRecord(item.getEmail());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }*/
    }

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

            case R.id.llAddConn:
                Intent i = new Intent(getActivity(), AddConnectionActivity.class);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
         getData();
         setListData();
    }
}
