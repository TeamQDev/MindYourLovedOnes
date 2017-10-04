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
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentSpecialist extends Fragment implements View.OnClickListener{
    View rootview;
    ListView lvSpecialist;
    ArrayList<Specialist> specialistList;
    RelativeLayout llAddSpecialist;
    Preferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_specialist,null);
        preferences = new Preferences(getActivity());
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void setListData() {
        SpecialistAdapter specialistAdapter=new SpecialistAdapter(getActivity(),specialistList);
        lvSpecialist.setAdapter(specialistAdapter);


    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddSpecialist.setOnClickListener(this);
    }

    private void initUI() {

        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddSpecialist= (RelativeLayout) rootview.findViewById(R.id.llAddSpecialist);
        lvSpecialist= (ListView) rootview.findViewById(R.id.lvSpecialist);
        setListData();
    }

    private void getData() {
        specialistList=new ArrayList<>();

        /*Specialist P1=new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setPhone("789-789-5236");


        Specialist P2=new Specialist();
        P2.setName("Dr. James");
        P2.setType("Neuro Surgeon");
        P2.setAddress("#204,10 upper Street, los Angeles, California.");
        P2.setImage(R.drawable.docto);
        P2.setPhone("987-789-5236");

        Specialist P3=new Specialist();
        P3.setName("Dr. Smith");
        P3.setType("Neuro Surgeon");
        P3.setAddress("#205,10 Left Street, los Angeles, California.");
        P3.setImage(R.drawable.doctors);
        P3.setPhone("789-789-5236");
*/
      /*  specialistList.add(P1);
        specialistList.add(P2);
        specialistList.add(P3);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddSpecialist:
                preferences.putString(PrefConstants.SOURCE,"Speciality");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
               // DialogManager dialogManager=new DialogManager(new FragmentSpecialist());
               // dialogManager.showCommonDialog("Add?","Do you want to add new specialist?",getActivity(),"ADD_SPECIALIST",null);
                break;
        }
    }

    public void postCommonDialog() {
        //preferences.putString(PrefConstants.SOURCE,"Speciality");
        Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
        startActivity(i);
    }
}
