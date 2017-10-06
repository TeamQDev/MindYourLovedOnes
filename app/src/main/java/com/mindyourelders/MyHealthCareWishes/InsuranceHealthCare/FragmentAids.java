package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AideQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.model.Aides;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentAids extends Fragment implements View.OnClickListener{
    View rootview;
    ListView lvAides;
    ArrayList<Aides> AidesList;
    RelativeLayout llAddAides;
    Preferences preferences;
DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_aides,null);
        initComponent();

        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        AideQuery a=new AideQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (AidesList.size()!=0) {
        AidesAdapter aidesAdapter=new AidesAdapter(getActivity(),AidesList);
        lvAides.setAdapter(aidesAdapter);
            lvAides.setVisibility(View.VISIBLE);
    }
        else{
            lvAides.setVisibility(View.GONE);
    }
    }

    private void initListener() {
        llAddAides.setOnClickListener(this);
    }

    private void initUI() {

        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddAides= (RelativeLayout) rootview.findViewById(R.id.llAddAides);
        lvAides= (ListView) rootview.findViewById(R.id.lvAides);
        setListData();
    }

    private void getData() {
        AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* AidesList=new ArrayList<>();

        Aides P1=new Aides();
        P1.setFirm("Home Instead Senior Care");
        P1.setAidName("Emily Holms");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");
        P1.setImage(R.drawable.maids);
        P1.setPhone("589-789-5236");


        Aides P2=new Aides();
        P2.setFirm("American Senior Communities");
        P2.setAidName("Ava Watson");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");
        P2.setImage(R.drawable.maidss);
        P2.setPhone("366-789-5236");

        Aides P3=new Aides();
        P3.setFirm("Community Health Network ");
        P3.setAidName("Ashley Sheridon");
        P3.setAddress("200 E MAIN ST, PHOENIX AZ 85123, USA");
        P3.setImage(R.drawable.maidsss);
        P3.setPhone("986-789-5236");


        AidesList.add(P1);
        AidesList.add(P2);
        AidesList.add(P3);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddAides:
                preferences.putString(PrefConstants.SOURCE,"Aides");
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
