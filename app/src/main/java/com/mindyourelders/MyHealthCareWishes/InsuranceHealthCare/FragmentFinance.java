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
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.FinanceQuery;
import com.mindyourelders.MyHealthCareWishes.model.Finance;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentFinance extends Fragment implements View.OnClickListener{
    View rootview;
    ListView lvFinance;
    ArrayList<Finance> FinanceList;
    RelativeLayout llAddFinance;
    Preferences preferences;
DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_finance,null);
       initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        FinanceQuery f=new FinanceQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (FinanceList.size()!=0) {
        FinanceAdapter financeAdapter=new FinanceAdapter(getActivity(),FinanceList);
        lvFinance.setAdapter(financeAdapter);
            lvFinance.setVisibility(View.VISIBLE);
        }
        else{
            lvFinance.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        llAddFinance.setOnClickListener(this);
    }

    private void initUI() {
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddFinance= (RelativeLayout) rootview.findViewById(R.id.llAddFinance);
        lvFinance= (ListView) rootview.findViewById(R.id.lvFinance);
        setListData();
    }

    private void getData() {
        FinanceList= FinanceQuery.fetchAllFinanceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* FinanceList=new ArrayList<>();

        Finance P1=new Finance();
        P1.setFirm("Grand Capital");
        P1.setName("James Holms");
        P1.setCategory("Accountant");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");
        P1.setImage(R.drawable.insis);
        P1.setPhone("589-789-5236");


        Finance P2=new Finance();
        P2.setFirm("Latham & Watkins");
        P2.setCategory("Attorney");
        P2.setName("Jack Watson");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");
        P2.setImage(R.drawable.insir);
        P2.setPhone("366-789-5236");

        Finance P3=new Finance();
        P3.setFirm("American Advisory Group");
        P3.setName("John Sheridon");
        P3.setCategory("Financial Planner");
        P3.setAddress("200 E MAIN ST, PHOENIX AZ 85123, USA");
        P3.setImage(R.drawable.insurs);
        P3.setPhone("986-789-5236");


        FinanceList.add(P1);
        FinanceList.add(P2);
        FinanceList.add(P3);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddFinance:
                preferences.putString(PrefConstants.SOURCE,"Finance");
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
