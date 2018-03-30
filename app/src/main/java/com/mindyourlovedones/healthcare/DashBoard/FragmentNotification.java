package com.mindyourlovedones.healthcare.DashBoard;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.NotificationSetter;

import java.util.ArrayList;

/**
 * Created by varsha on 9/8/2017.
 */

public class FragmentNotification  extends Fragment {
    View rootview;
    ListView lvNotification;
    ArrayList<NotificationSetter> notificationList;
 TextView txtTitle;
    ImageView imgNoti;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_notification,null);
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void setListData() {
        NotificationAdapter notificationAdapter=new NotificationAdapter(getActivity(),notificationList);
        lvNotification.setAdapter(notificationAdapter);


    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
  
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Notifications");
        imgNoti= (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
      
        lvNotification= (ListView) rootview.findViewById(R.id.lvNotification);
        setListData();
    }

    private void getData() {
        notificationList=new ArrayList<>();

        NotificationSetter P1=new NotificationSetter();
        P1.setName("Mary Charlo");
        P1.setMessage("In Pain... Come Help");
        P1.setTime("6m ago");
        P1.setImage(R.drawable.circular_profile);


        NotificationSetter P2=new NotificationSetter();
        P2.setName("Chuck Charlo");
        P2.setMessage("Has sent you a connection request.");
        P2.setTime("30m ago");
        P2.setImage(R.drawable.profile_circle);

        NotificationSetter P3=new NotificationSetter();
        P3.setName("Prince Charlo");
        P3.setMessage("Accepted your connection request");
        P3.setTime("30m ago");
        P3.setImage(R.drawable.profile_round);

        notificationList.add(P1);
        notificationList.add(P2);
        notificationList.add(P3);
        /*notificationList.add(P4);
        notificationList.add(P5);
        notificationList.add(P6);
        notificationList.add(P7);*/
    }

}
