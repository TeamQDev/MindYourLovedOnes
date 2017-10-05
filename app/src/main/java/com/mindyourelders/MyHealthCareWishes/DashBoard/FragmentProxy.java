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
import com.mindyourelders.MyHealthCareWishes.model.Proxy;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentProxy extends Fragment implements View.OnClickListener{
    View rootview;
    SwipeMenuListView lvProxy;
    ArrayList<Proxy> proxyList;
    TextView txtAdd;
    RelativeLayout llAddConn;
    TextView txtTitle;
    ImageView imgNoti;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    ProxyAdapter proxyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_proxy, null);
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
        proxyAdapter = new ProxyAdapter(getActivity(), proxyList);
        lvProxy.setAdapter(proxyAdapter);
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Health Care Proxy Agents");
        /*imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);*/
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddConn = (RelativeLayout) rootview.findViewById(R.id.llAddConn);
        lvProxy = (SwipeMenuListView) rootview.findViewById(R.id.lvProxy);
        if (proxyList.size()!=0||proxyList!=null)
        {
            setListData();
        }
        lvProxy.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvProxy.setMenuCreator(creator);
        lvProxy.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Proxy item = proxyList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        break;
                    case 1:
                        // delete
                        deleteProxy(item);
                        break;
                }
                return false;
            }
        });
    }

    private void deleteProxy(Proxy item) {
       boolean flag= MyConnectionsQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        proxyList = MyConnectionsQuery.fetchAllProxyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),3);
      /*  proxyList=new ArrayList<>();
        Proxy P1 = new Proxy();
        P1.setName("Cherry Smith");
        P1.setType("Primary Proxy");
        P1.setImage(R.drawable.mother);
        P1.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P1.setPhone("789566236");

        Proxy P2 = new Proxy();
        P2.setName("John Smith");
        P2.setType("Secondary Proxy");
        P2.setImage(R.drawable.user_two);
        P2.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P2.setPhone("789566236");

        proxyList.add(P1);
        proxyList.add(P2);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddConn:
                preferences.putString(PrefConstants.SOURCE,"Proxy");
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
