package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AllergyQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.HistoryQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalImplantsQuery;
import com.mindyourelders.MyHealthCareWishes.model.Allergy;
import com.mindyourelders.MyHealthCareWishes.model.MedInfo;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentMedicalInfo extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    View rootview;
    ImageView imgBack, imgDone;
    TextView txtTitle;

    EditText etFt, etInch, etWeight, etAdditional, etPet;
    ToggleButton tbGlass, tbLense, tbFalse, tbImplants, tbHearingAid;
    RadioButton rbYes, rbNo;
    String glass = "No", lense = "No", falses = "No", implants = "No", aid = "No", donor = "No";
    String ft, inch, weight, color, lang1, lang2, blood, pet;
    RadioGroup rgDonor;
    TextView txtName;
    Spinner spinnerEyes, spinnerBlood, spinnerLang;
    ImageView imgAddAllergy, imgAddImplants, imgAddHospital, imgAddHistory;
    ListView ListHistory, ListAllergy, ListImplants, ListHospital;
    public static final int REQUEST_ALLERGY = 100;
    public static final int REQUEST_HISTORY = 200;
    public static final int REQUEST_IMPLANTS = 300;
    public static final int REQUEST_HOSPITAL = 400;

    ArrayList historList = new ArrayList();

    ArrayList hospitalList = new ArrayList();

    String[] LangList = {"English", "Spanish", "French", "Russian", "Japanese", "Greek", "German", "Italian"};
    String[] EyesList = {"Blue", "Brown", "Green", "Hazel"};
    String[] BloodList = {"O-positive", "O-negative", "A-positive", "A-negative", "B-positive", "B-negative", "AB-positive", "AB-negative", "I dont know"};

    Preferences preferences;
    DBHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_medical_info, null);
        preferences = new Preferences(getActivity());
        initComponent();
        initUI();
        initListener();
        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
        MedInfoQuery p = new MedInfoQuery(getActivity(), dbHelper);
        AllergyQuery a = new AllergyQuery(getActivity(), dbHelper);
        MedicalImplantsQuery m=new MedicalImplantsQuery(getActivity(),dbHelper);
        HospitalQuery h = new HospitalQuery(getActivity(), dbHelper);
        HistoryQuery hi=new HistoryQuery(getActivity(),dbHelper);
    }

    private void initListener() {
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgAddAllergy.setOnClickListener(this);
        imgAddHistory.setOnClickListener(this);
        imgAddHospital.setOnClickListener(this);
        imgAddImplants.setOnClickListener(this);

        tbGlass.setOnCheckedChangeListener(this);
        tbLense.setOnCheckedChangeListener(this);
        tbFalse.setOnCheckedChangeListener(this);
        tbImplants.setOnCheckedChangeListener(this);
        tbHearingAid.setOnCheckedChangeListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Medical Information");
        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);

        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));

        spinnerBlood = (Spinner) rootview.findViewById(R.id.spinnerBlood);
        spinnerEyes = (Spinner) rootview.findViewById(R.id.spinnerEyes);
        spinnerLang = (Spinner) rootview.findViewById(R.id.spinnerPrimary);
        imgAddAllergy = (ImageView) rootview.findViewById(R.id.imgAddAllergy);
        imgAddImplants = (ImageView) rootview.findViewById(R.id.imgAddImplants);
        imgAddHospital = (ImageView) rootview.findViewById(R.id.imgAddHospital);
        imgAddHistory = (ImageView) rootview.findViewById(R.id.imgAddHistory);
        ListHistory = (ListView) rootview.findViewById(R.id.ListHistory);
        ListAllergy = (ListView) rootview.findViewById(R.id.ListAllergy);
        ListImplants = (ListView) rootview.findViewById(R.id.ListImplants);
        ListHospital = (ListView) rootview.findViewById(R.id.ListHospital);

        etFt = (EditText) rootview.findViewById(R.id.etFt);
        etInch = (EditText) rootview.findViewById(R.id.etInch);
        etWeight = (EditText) rootview.findViewById(R.id.etWeight);
        etAdditional = (EditText) rootview.findViewById(R.id.etAdditional);
        etPet = (EditText) rootview.findViewById(R.id.etPet);

        tbGlass = (ToggleButton) rootview.findViewById(R.id.tbGlass);
        tbLense = (ToggleButton) rootview.findViewById(R.id.tbLense);
        tbFalse = (ToggleButton) rootview.findViewById(R.id.tbFalse);
        tbImplants = (ToggleButton) rootview.findViewById(R.id.tbImplants);
        tbHearingAid = (ToggleButton) rootview.findViewById(R.id.tbHearingAid);

        rbYes = (RadioButton) rootview.findViewById(R.id.rbYes);
        rbNo = (RadioButton) rootview.findViewById(R.id.rbNo);
        rgDonor = (RadioGroup) rootview.findViewById(R.id.rgDonor);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, EyesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEyes.setAdapter(adapter);
        spinnerEyes.setPrompt("Select Eyes Color");

        ArrayAdapter<String> langadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, LangList);
        langadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLang.setAdapter(langadapter);
        spinnerLang.setPrompt("Select Primary Language");

        ArrayAdapter<String> financeadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, BloodList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlood.setAdapter(financeadapter);
        spinnerBlood.setPrompt("Select Blood Type");


        rgDonor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYes) {
                    donor = "Yes";
                } else if (checkedId == R.id.rbNo) {
                    donor = "No";
                }
            }
        });

        setMedInfo();
        setAllergyData();
        setImplantData();
        setHistoryData();
        setHospitalData();

    }

    private void setMedInfo() {
        MedInfo medInfo = MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (medInfo != null) {
            etFt.setText(medInfo.getFeet());
            etInch.setText(medInfo.getInch());
            etWeight.setText(medInfo.getWeight());
            etAdditional.setText(medInfo.getLang2());
            etPet.setText(medInfo.getPet());
            int index = 0;
            for (int i = 0; i < EyesList.length; i++) {
                if (medInfo.getColor().equals(EyesList[i])) {
                    index = i;
                }
            }
            spinnerEyes.setSelection(index);

            int indexs = 0;
            for (int i = 0; i < LangList.length; i++) {
                if (medInfo.getLang1().equals(LangList[i])) {
                    indexs = i;
                }
            }
            spinnerLang.setSelection(indexs);

            int indexi = 0;
            for (int i = 0; i < BloodList.length; i++) {
                if (medInfo.getBloodType().equals(BloodList[i])) {
                    indexi = i;
                }
            }
            spinnerBlood.setSelection(indexi);

            etAdditional.setText(medInfo.getLang2());

            if (medInfo.getGlass().equals("Yes")) {
                tbGlass.setChecked(true);
            } else if (medInfo.getGlass().equals("No")) {
                tbGlass.setChecked(false);
            }

            if (medInfo.getLense().equals("Yes")) {
                tbLense.setChecked(true);
            } else if (medInfo.getLense().equals("No")) {
                tbLense.setChecked(false);
            }

            if (medInfo.getFalses().equals("Yes")) {
                tbFalse.setChecked(true);
            } else if (medInfo.getFalses().equals("No")) {
                tbFalse.setChecked(false);
            }

            if (medInfo.getImplants().equals("Yes")) {
                tbImplants.setChecked(true);
            } else if (medInfo.getImplants().equals("No")) {
                tbImplants.setChecked(false);
            }

            if (medInfo.getAid().equals("Yes")) {
                tbHearingAid.setChecked(true);
            } else if (medInfo.getAid().equals("No")) {
                tbHearingAid.setChecked(false);
            }

            if (medInfo.getDonor().equals("Yes")) {
                rbYes.setChecked(true);
            } else if (medInfo.getDonor().equals("No")) {
                rbNo.setChecked(true);
            }
        }
    }

    private void setImplantData() {
        ArrayList<String> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (implantsList.size() != 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, implantsList);
            ListImplants.setAdapter(adapter);
        }
    }

    private void setHistoryData() {
        ArrayList<String> historList = HistoryQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (historList.size() != 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, historList);
            ListHistory.setAdapter(adapter);
        }
    }

    private void setHospitalData() {
        ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (hospitalList.size() != 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, hospitalList);
            ListHospital.setAdapter(adapter);
        }
    }

    private void setAllergyData() {
        ArrayList allergyList = new ArrayList();
        ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (AllargyLists.size() != 0) {
            for (int i = 0; i < AllargyLists.size(); i++) {
                Allergy a = AllargyLists.get(i);
                String allergy = "Allergy: " + a.getAllergy() + "\nReaction: " + a.getReaction() + "\nTreatment: " + a.getTreatment();
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, allergyList);
                ListAllergy.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //  String ft,inch,weight,color,lang1,lang2,blood,pet;
            case R.id.imgDone:
                ft = etFt.getText().toString().trim();
                inch = etInch.getText().toString().trim();
                weight = etWeight.getText().toString().trim();
                color = spinnerEyes.getSelectedItem().toString();
                lang1 = spinnerLang.getSelectedItem().toString();
                lang2 = etAdditional.getText().toString();
                blood = spinnerBlood.getSelectedItem().toString();
                pet = etPet.getText().toString().trim();
                Boolean flag = MedInfoQuery.insertMedInfoData(preferences.getInt(PrefConstants.CONNECTED_USERID), ft, inch, weight, color, lang1, lang2, pet, blood, glass, lense, falses, implants, aid, donor);
                if (flag == true) {
                    Toast.makeText(getActivity(), "Medical Information Saved", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imgBack:
                getActivity().finish();
                break;
            case R.id.imgAddAllergy:
                Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                allergyIntent.putExtra("IsAllergy", true);
                allergyIntent.putExtra("ADD", "Allergy");
                allergyIntent.putExtra("Name", "Add Allergy");
                startActivityForResult(allergyIntent, REQUEST_ALLERGY);
                break;
            case R.id.imgAddImplants:
                Intent implantsIntent = new Intent(getActivity(), AddInfoActivity.class);
                implantsIntent.putExtra("IsAllergy", false);
                implantsIntent.putExtra("ADD", "Implants");
                implantsIntent.putExtra("Name", "Add Implantaion you have done");
                startActivityForResult(implantsIntent, REQUEST_IMPLANTS);
                break;
            case R.id.imgAddHospital:
                Intent hospIntent = new Intent(getActivity(), AddInfoActivity.class);
                hospIntent.putExtra("IsAllergy", false);
                hospIntent.putExtra("ADD", "Hospital");
                hospIntent.putExtra("Name", "Add hospital preferences");
                startActivityForResult(hospIntent, REQUEST_HOSPITAL);
                break;
            case R.id.imgAddHistory:
                Intent historyIntent = new Intent(getActivity(), AddInfoActivity.class);
                historyIntent.putExtra("IsAllergy", false);
                historyIntent.putExtra("ADD", "History");
                historyIntent.putExtra("Name", "Add Medical History");
                startActivityForResult(historyIntent, REQUEST_HISTORY);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_ALLERGY == requestCode) {
           /* String value=data.getExtras().getString("Value");
            String reaction=data.getExtras().getString("Reaction");
            String treatment=data.getExtras().getString("Treatment");
            String allergy="Allergy: "+value+"\nReaction: "+reaction+"\nTreatment: "+treatment;
            allergyList.add(allergy);*/
            setAllergyData();
            ListAllergy.requestFocus();
        } else if (REQUEST_IMPLANTS == requestCode) {
            /*String value=data.getExtras().getString("Value");
            implantsList.add(value);*/
            setImplantData();
            ListImplants.requestFocus();
        } else if (REQUEST_HOSPITAL == requestCode && data != null) {
           /* String value = data.getExtras().getString("Value");
            hospitalList.add(value);*/
            setHospitalData();
            ListHospital.requestFocus();
        } else if (REQUEST_HISTORY == requestCode && data != null) {
           /* String value = data.getExtras().getString("Value");
            historList.add(value);*/
            setHistoryData();
            ListHistory.requestFocus();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.tbGlass:
                if (isChecked == true)
                    glass = "Yes";
                else
                    glass = "No";
                break;
            case R.id.tbLense:
                if (isChecked == true)
                    lense = "Yes";
                else
                    lense = "No";
                break;
            case R.id.tbFalse:
                if (isChecked == true)
                    falses = "Yes";
                else
                    falses = "No";
                break;
            case R.id.tbImplants:
                if (isChecked == true)
                    implants = "Yes";
                else
                    implants = "No";
                break;
            case R.id.tbHearingAid:
                if (isChecked == true)
                    aid = "Yes";
                else
                    aid = "No";
                break;
        }
    }
}
