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
import com.mindyourelders.MyHealthCareWishes.database.PharmacyQuery;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentPharmacy extends Fragment implements View.OnClickListener{
    View rootview;
    ListView lvPharmacy;
    ArrayList<Pharmacy> PharmacyList;
    RelativeLayout llAddPharmacy;
    Preferences preferences;
DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_pharmacy,null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        PharmacyQuery p=new PharmacyQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (PharmacyList.size()!=0) {
            PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(getActivity(), PharmacyList);
            lvPharmacy.setAdapter(pharmacyAdapter);
        }
    }

    private void initListener() {
        llAddPharmacy.setOnClickListener(this);
    }

    private void initUI() {

        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddPharmacy= (RelativeLayout) rootview.findViewById(R.id.llAddPharmacy);
        lvPharmacy= (ListView) rootview.findViewById(R.id.lvPharmacy);
        setListData();
    }

    private void getData() {
        PharmacyList= PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
     /*   PharmacyList=new ArrayList<>();

        Pharmacy P1=new Pharmacy();
        P1.setName("Health Care Medico");
        P1.setNote("Emily Holms");
        P1.setImage(R.drawable.pharmacy);
        P1.setPhone("789-456-2135");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");

        Pharmacy P2=new Pharmacy();
        P2.setName("City Medico");
        P2.setNote("Emily Holms");
        P2.setImage(R.drawable.pharmacys);
        P2.setPhone("985-456-2135");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");


        PharmacyList.add(P1);
        PharmacyList.add(P2);*/
          }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddPharmacy:
                preferences.putString(PrefConstants.SOURCE,"Pharmacy");
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
