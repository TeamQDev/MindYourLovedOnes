package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

/**
 * Created by welcome on 11/24/2017.
 */

public class FragmentLiving extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    View rootview;
    ImageView imgBack, imgDone;
    Preferences preferences;
    TextView txtTitle, txtName;
    DBHelper dbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_living, null);
        preferences = new Preferences(getActivity());
        initComponent();
        initUI();
        initListener();
        return rootview;
    }

    private void initListener() {
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("ACTIVITIES OF DAILY LIVING");

        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgDone:
      /*  ft = etFt.getText().toString().trim();
        inch = etInch.getText().toString().trim();
        weight = etWeight.getText().toString().trim();
        color = spinnerEyes.getSelectedItem().toString();
        lang1 = spinnerLang.getSelectedItem().toString();
        lang2 = etAdditional.getText().toString();
        blood = spinnerBlood.getSelectedItem().toString();
        pet = etPet.getText().toString().trim();
        note = etPreNote.getText().toString().trim();
        mouthnote=etMouthNote.getText().toString().trim();

        visionnote=etVisionNote.getText().toString().trim();
        Aidenote=etAideNote.getText().toString().trim();
        functionnote=etFunctionalNote.getText().toString().trim();
        dietnote=etDietNote.getText().toString().trim();

        Boolean flag = MedInfoQuery.insertMedInfoData(preferences.getInt(PrefConstants.CONNECTED_USERID), ft, inch, weight, color, lang1, lang2, pet, blood, glass, lense, falses, implants, aid, donor,note,mouth,mouthnote,visionnote,Aidenote,functionnote,dietnote,blind,speech,medicate,toilet,feed);
        if (flag == true) {
            Toast.makeText(getActivity(), "Medical Profile Saved", Toast.LENGTH_SHORT).show();
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }*/

                break;
            case R.id.imgBack:
                getActivity().finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
}
