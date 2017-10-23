package com.mindyourelders.MyHealthCareWishes.DashBoard;

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
import com.mindyourelders.MyHealthCareWishes.Connections.ConnectionAdapter;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentEmergency extends Fragment implements View.OnClickListener{
    View rootview;
    SwipeMenuListView lvEmergency;
    ArrayList<Emergency> emergencyList;
    TextView txtAdd;
    RelativeLayout llAddConn;
    TextView txtTitle;
    ImageView imgNoti;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    EmergencyAdapter emergencyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_emergency, null);
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
        emergencyAdapter = new EmergencyAdapter(getActivity(), emergencyList);
        lvEmergency.setAdapter(emergencyAdapter);
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("EMERGENCY CONTACTS");
        /*imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);*/
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddConn = (RelativeLayout) rootview.findViewById(R.id.llAddConn);
        lvEmergency = (SwipeMenuListView) rootview.findViewById(R.id.lvEmergency);
        if (emergencyList.size()!=0||emergencyList!=null)
        {
            setListData();
        }
        lvEmergency.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvEmergency.setMenuCreator(creator);
        lvEmergency.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Emergency item = emergencyList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        break;
                    case 1:
                        // delete
                            deleteEmergency(item);
                        break;
                }
                return false;
            }
        });
    }

    private void deleteEmergency(Emergency item) {
      boolean flag= MyConnectionsQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        emergencyList =  MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
      /* //emergencyList = MyConnectionsQuery.fetchAllRecord();
emergencyList=new ArrayList<>();
        Emergency P1 = new Emergency();
        P1.setName("Clark Smith");
        P1.setRelationType("Self");
        P1.setImage(R.drawable.father);
        P1.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P1.setPhone("789566236");

        Emergency P2 = new Emergency();
        P2.setName("Cherry Smith");
        P2.setRelationType("Wife");
        P2.setImage(R.drawable.mother);
        P2.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P2.setPhone("789566236");



        emergencyList.add(P1);
        emergencyList.add(P2);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddConn:
                preferences.putString(PrefConstants.SOURCE,"Emergency");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
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
