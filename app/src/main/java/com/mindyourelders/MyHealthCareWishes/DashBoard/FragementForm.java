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
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.FormQuery;
import com.mindyourelders.MyHealthCareWishes.database.InsuranceQuery;
import com.mindyourelders.MyHealthCareWishes.model.Document;
import com.mindyourelders.MyHealthCareWishes.model.Form;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

/**
 * Created by welcome on 10/30/2017.
 */

public class FragementForm extends Fragment implements View.OnClickListener {
    View rootview;
    SwipeMenuListView lvDoc;
    ArrayList<Form> documentList;
    ArrayList<Document> documentListOld;
    ImageView imgBack;
    TextView txtTitle;
    String From;
    final CharSequence[] dialog_items = { "Email", "Bluetooth", "View", "Print", "Fax" };
    RelativeLayout llAddDoc;
    Preferences preferences;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_insurance_form, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
        FormQuery i = new FormQuery(getActivity(), dbHelper);
    }

    private void setListData() {
       if (documentList.size() != 0) {
            DocumentsAdapter insuranceAdapter = new DocumentsAdapter(getActivity(), documentList);
            lvDoc.setAdapter(insuranceAdapter);
            lvDoc.setVisibility(View.VISIBLE);
        } else {
            lvDoc.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddDoc.setOnClickListener(this);
    }

    private void initUI() {

        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddDoc = (RelativeLayout) rootview.findViewById(R.id.llAddDoc);
        lvDoc = (SwipeMenuListView) rootview.findViewById(R.id.lvDoc);
        lvDoc.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createSingleMenu(getActivity());
        lvDoc.setMenuCreator(creator);
        lvDoc.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Form item = documentList.get(position);
                switch (index) {
                    case 0:
                        // delete
                        deleteDocument(item);
                        break;
                }
                return true;
            }
        });
        setListData();
    }

    private void deleteDocument(Form item) {
        boolean flag = FormQuery.deleteRecord(item.getId());
        if (flag == true) {
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void deleteInsurance(Insurance item) {
        boolean flag= InsuranceQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        documentList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    //  documentList=new ArrayList<>();
 /*
        Form f=new Form();
        f.setName("sdfds");
        f.setImage(R.id.imgPdf);*/
/*
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



        documentList.add(P1);
        documentList.add(P2);
        documentList.add(P3);
        documentList.add(P4);
        documentList.add(P5);

*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddDoc:
                //preferences.putString(PrefConstants.SOURCE, "InsuranceForm");
                Intent i = new Intent(getActivity(), AddInsuranceFormActivity.class);
                i.putExtra("GoTo","Add");
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
