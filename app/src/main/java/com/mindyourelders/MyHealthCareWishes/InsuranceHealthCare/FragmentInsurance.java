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
import com.mindyourelders.MyHealthCareWishes.database.InsuranceQuery;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentInsurance extends Fragment implements View.OnClickListener {
    View rootview;
    ListView lvInsurance;
    ArrayList<Insurance> insuranceList;
    RelativeLayout llAddInsurance;
    Preferences preferences;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_insurance, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
        InsuranceQuery i = new InsuranceQuery(getActivity(), dbHelper);
    }

    private void setListData() {
        if (insuranceList.size() != 0) {
            InsuranceAdapter insuranceAdapter = new InsuranceAdapter(getActivity(), insuranceList);
            lvInsurance.setAdapter(insuranceAdapter);
            lvInsurance.setVisibility(View.VISIBLE);
        } else {
            lvInsurance.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddInsurance.setOnClickListener(this);
    }

    private void initUI() {

        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddInsurance = (RelativeLayout) rootview.findViewById(R.id.llAddInsurance);
        lvInsurance = (ListView) rootview.findViewById(R.id.lvInsurance);
        setListData();
    }

    private void getData() {
        insuranceList = InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* insuranceList=new ArrayList<>();

        Insurance P1=new Insurance();
        P1.setName("Symphonix Health");
        P1.setId("4489");
        P1.setGroup("Group angels");
        P1.setType("Medicare");
        P1.setImage(R.drawable.insu);
        P1.setPhone("963-789-5236");
        P1.setMember("Consultive");

      
        Insurance P2=new Insurance();
        P2.setName("Sierra Health");
        P2.setId("8965");
        P2.setGroup("Group angels");
        P2.setType("Supplemental");
        P2.setImage(R.drawable.insur);
        P2.setPhone("396-545-5236");
        P2.setMember("Consultive");

        Insurance P3=new Insurance();
        P3.setName("Humana Insurance");
        P3.setId("9685");
        P3.setGroup("Group angels");
        P3.setType("Long Term Care ");
        P3.setImage(R.drawable.insurs);
        P3.setPhone("985-985-5236");
        P3.setMember("Consultive");

        Insurance P4=new Insurance();
        P4.setName("Aetna");
        P4.setId("3698");
        P4.setGroup("Group angels");
        P4.setType("Medical");
        P4.setImage(R.drawable.insir);
        P4.setPhone("968-985-5236");
        P4.setMember("Consultive");

        Insurance P5=new Insurance();
        P5.setName("Aetna");
        P5.setId("9635");
        P5.setGroup("Group angels");
        P5.setType("Dental");
        P5.setImage(R.drawable.insis);
        P5.setPhone("365-985-5236");
        P5.setMember("Consultive");



        insuranceList.add(P1);
        insuranceList.add(P2);
        insuranceList.add(P3);
        insuranceList.add(P4);
        insuranceList.add(P5);

*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddInsurance:
                preferences.putString(PrefConstants.SOURCE, "Insurance");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
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
