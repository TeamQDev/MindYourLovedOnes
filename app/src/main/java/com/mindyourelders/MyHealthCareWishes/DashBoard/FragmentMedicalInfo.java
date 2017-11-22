package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import com.mindyourelders.MyHealthCareWishes.database.MedicalConditionQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalImplantsQuery;
import com.mindyourelders.MyHealthCareWishes.model.Allergy;
import com.mindyourelders.MyHealthCareWishes.model.History;
import com.mindyourelders.MyHealthCareWishes.model.MedInfo;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentMedicalInfo extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private static final int REQUEST_CONDITION = 500;
    View rootview;
    ImageView imgBack, imgDone;
    TextView txtTitle,imgAddFlueShot;
    EditText etPreNote,etMouthNote,etVisionNote,etAideNote,etFunctionalNote,etDietNote;
    TextView imgAddPneumonia,imgAddHPV,imgAddRubella,imgAddVaricella,imgAddShingles,imgAddTetanus,imgAddHepatitis,imgAddFlue,imgAddFlueNH,imgAddPneumococcal;
    TextView txtFlueShotDate,txtPneumoniaDate,txtHPVDate,txtRubellaDate,txtVaricellaDate,txtShinglesDate,txtTetanusDate,txtHepatitisDate,txtFlueDate,txtFlueNHDate,txtPneumococcalDate;
    EditText etFt, etInch, etWeight, etAdditional, etPet;
    ToggleButton tbGlass, tbLense, tbFalse, tbImplants, tbHearingAid,tbMouth,tbColor,tbSpeech,tbFeed,tbToilet,tbMedicate;
    RadioButton rbYes, rbNo;
    String glass = "No", lense = "No", falses = "No", implants = "No", aid = "No", donor = "No",mouth="No",blind="No",speech="No",feed="No",toilet="No",medicate="No";
    String ft, inch, weight, color, lang1, lang2, blood, pet;
    RadioGroup rgDonor;
    TextView txtName;
    Spinner spinnerEyes, spinnerBlood, spinnerLang;
    ImageView imgAddAllergy, imgAddImplants, imgAddHospital, imgAddHistory,imgAddCondition;
    ListView ListHistory, ListAllergy, ListImplants, ListHospital,ListCondition;
    public static final int REQUEST_ALLERGY = 100;
    public static final int REQUEST_HISTORY = 200;
    public static final int REQUEST_IMPLANTS = 300;
    public static final int REQUEST_HOSPITAL = 400;
    String note="";
    String mouthnote="";
    String visionnote="";
    String Aidenote="";
    String functionnote="";
    String dietnote="";
    ArrayList historList = new ArrayList();
    ArrayList hospitalList = new ArrayList();

    String[] LangList = {"English","French","German","Greek","Italian","Japanese","Russian","Spanish"};
    String[] EyesList = {"Blue", "Brown", "Green", "Hazel"};
    String[] BloodList = {"","A-negative","A-positive","AB-negative","AB-positive","B-negative","B-positive","O-negative","O-positive","I dont know"};

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
        MedicalConditionQuery f=new MedicalConditionQuery(getActivity(),dbHelper);
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
        imgAddCondition.setOnClickListener(this);


        imgAddPneumonia.setOnClickListener(this);
        imgAddFlueShot.setOnClickListener(this);
        imgAddHPV.setOnClickListener(this);
        imgAddRubella.setOnClickListener(this);
        imgAddVaricella.setOnClickListener(this);
        imgAddShingles.setOnClickListener(this);
        imgAddTetanus.setOnClickListener(this);
        imgAddHepatitis.setOnClickListener(this);
        imgAddFlue.setOnClickListener(this);
        imgAddFlueNH.setOnClickListener(this);
        imgAddPneumococcal.setOnClickListener(this);

        tbGlass.setOnCheckedChangeListener(this);
        tbMouth.setOnCheckedChangeListener(this);
        tbLense.setOnCheckedChangeListener(this);
        tbFalse.setOnCheckedChangeListener(this);
        tbImplants.setOnCheckedChangeListener(this);
        tbHearingAid.setOnCheckedChangeListener(this);

        tbSpeech.setOnCheckedChangeListener(this);
        tbColor.setOnCheckedChangeListener(this);
        tbMedicate.setOnCheckedChangeListener(this);
        tbToilet.setOnCheckedChangeListener(this);
        tbFeed.setOnCheckedChangeListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("MEDICAL PROFILE");

        txtFlueShotDate = (TextView) rootview.findViewById(R.id.txtFlueShotDate);
        txtPneumoniaDate = (TextView) rootview.findViewById(R.id.txtPneumoniaDate);
        txtHPVDate = (TextView) rootview.findViewById(R.id.txtHPVDate);
        txtRubellaDate = (TextView) rootview.findViewById(R.id.txtRubellaDate);
        txtVaricellaDate = (TextView) rootview.findViewById(R.id.txtVaricellaDate);
        txtShinglesDate = (TextView) rootview.findViewById(R.id.txtShinglesDate);
        txtTetanusDate = (TextView) rootview.findViewById(R.id.txtTetanusDate);
        txtHepatitisDate = (TextView) rootview.findViewById(R.id.txtHepatitisDate);
        txtFlueDate = (TextView) rootview.findViewById(R.id.txtFlueDate);
        txtFlueNHDate = (TextView) rootview.findViewById(R.id.txtFlueNHDate);
        txtPneumococcalDate = (TextView) rootview.findViewById(R.id.txtPneumococcalDate);


        imgAddPneumonia = (TextView)rootview.findViewById(R.id.imgAddPneumonia);
        imgAddFlueShot = (TextView) rootview.findViewById(R.id.imgAddFlueShot);
        imgAddHPV = (TextView) rootview.findViewById(R.id.imgAddHPV);
        imgAddRubella = (TextView) rootview.findViewById(R.id.imgAddRubella);
        imgAddVaricella = (TextView)rootview.findViewById(R.id.imgAddVaricella);
        imgAddShingles = (TextView)rootview.findViewById(R.id.imgAddShingles);
        imgAddTetanus = (TextView) rootview.findViewById(R.id.imgAddTetanus);
        imgAddHepatitis = (TextView) rootview.findViewById(R.id.imgAddHepatitis);
        imgAddFlue = (TextView) rootview.findViewById(R.id.imgAddFlue);
        imgAddFlueNH = (TextView) rootview.findViewById(R.id.imgAddFlueNH);
        imgAddPneumococcal = (TextView) rootview.findViewById(R.id.imgAddPneumococcal);

        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);
        etPreNote= (EditText) rootview.findViewById(R.id.etNote);
        etMouthNote= (EditText) rootview.findViewById(R.id.etMouthNote);
        etVisionNote= (EditText) rootview.findViewById(R.id.etVisionNote);
        etAideNote= (EditText) rootview.findViewById(R.id.etAideNote);
        etFunctionalNote= (EditText) rootview.findViewById(R.id.etFunctionalNote);
        etDietNote= (EditText) rootview.findViewById(R.id.etDietNote);
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));

        spinnerBlood = (Spinner) rootview.findViewById(R.id.spinnerBlood);
        spinnerEyes = (Spinner) rootview.findViewById(R.id.spinnerEyes);
        spinnerLang = (Spinner) rootview.findViewById(R.id.spinnerPrimary);
        imgAddAllergy = (ImageView) rootview.findViewById(R.id.imgAddAllergy);
        imgAddImplants = (ImageView) rootview.findViewById(R.id.imgAddImplants);
        imgAddCondition = (ImageView) rootview.findViewById(R.id.imgAddCondition);
        imgAddHospital = (ImageView) rootview.findViewById(R.id.imgAddHospital);
        imgAddHistory = (ImageView) rootview.findViewById(R.id.imgAddHistory);
        ListHistory = (ListView) rootview.findViewById(R.id.ListHistory);
        ListAllergy = (ListView) rootview.findViewById(R.id.ListAllergy);
        ListImplants = (ListView) rootview.findViewById(R.id.ListImplants);
        ListHospital = (ListView) rootview.findViewById(R.id.ListHospital);
        ListCondition = (ListView) rootview.findViewById(R.id.ListCondtion);

        etFt = (EditText) rootview.findViewById(R.id.etFt);
        etInch = (EditText) rootview.findViewById(R.id.etInch);
        etWeight = (EditText) rootview.findViewById(R.id.etWeight);
        etAdditional = (EditText) rootview.findViewById(R.id.etAdditional);
        etPet = (EditText) rootview.findViewById(R.id.etPet);

        tbGlass = (ToggleButton) rootview.findViewById(R.id.tbGlass);
        tbMouth = (ToggleButton) rootview.findViewById(R.id.tbMouth);
        tbLense = (ToggleButton) rootview.findViewById(R.id.tbLense);
        tbFalse = (ToggleButton) rootview.findViewById(R.id.tbFalse);
        tbImplants = (ToggleButton) rootview.findViewById(R.id.tbImplants);
        tbHearingAid = (ToggleButton) rootview.findViewById(R.id.tbHearingAid);

        tbColor = (ToggleButton) rootview.findViewById(R.id.tbBlind);
        tbSpeech = (ToggleButton) rootview.findViewById(R.id.tbSpeech);
        tbFeed = (ToggleButton) rootview.findViewById(R.id.tbfeed);
        tbToilet = (ToggleButton) rootview.findViewById(R.id.tbToileting);
        tbMedicate = (ToggleButton) rootview.findViewById(R.id.tbMedicate);

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
        setConditionData();

        setHistoryData();
        setHospitalData();

    }

    private void setConditionData() {
        final ArrayList<String> implantsList = MedicalConditionQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (implantsList.size() != 0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, implantsList);
            ListCondition.setAdapter(adapter);
            ListCondition.setVisibility(View.VISIBLE);
            ListCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
                    ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
                    imgEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String value=implantsList.get(position);
                            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                            allergyIntent.putExtra("IsAllergy", false);
                            allergyIntent.putExtra("IsHistory", false);

                            allergyIntent.putExtra("ADD", "ConditionUpdate");
                            allergyIntent.putExtra("Title", "Update Medical Condition");
                            allergyIntent.putExtra("Name", "Add Medical Condition");
                            allergyIntent.putExtra("ConditionObject", value);
                            startActivityForResult(allergyIntent, REQUEST_CONDITION);
                        }
                    });

                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean flag = MedicalConditionQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),implantsList.get(position));
                            if (flag == true) {
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                setConditionData();
                                ListCondition.requestFocus();
                            }
                        }
                    });
                }
            });
        } else {
            ListCondition.setVisibility(View.GONE);
        }
    }

    private void setMedInfo() {
        MedInfo medInfo = MedInfoQuery.fetchOneRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (medInfo != null) {
            etFt.setText(medInfo.getFeet());
            etInch.setText(medInfo.getInch());
            etWeight.setText(medInfo.getWeight());
            etAdditional.setText(medInfo.getLang2());
            etPet.setText(medInfo.getPet());
            etPreNote.setText(medInfo.getNote());
            etMouthNote.setText(medInfo.getMouthnote());

            etDietNote.setText(medInfo.getDietNote());
            etVisionNote.setText(medInfo.getVisionNote());
            etFunctionalNote.setText(medInfo.getFunctionnote());
            etAideNote.setText(medInfo.getAideNote());
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

            if (medInfo.getMedicate().equals("Yes")) {
                tbMedicate.setChecked(true);
                medicate="Yes";
            } else if (medInfo.getMedicate().equals("No")) {
                tbMedicate.setChecked(false);
                medicate="No";
            }

            if (medInfo.getToilet().equals("Yes")) {
                tbToilet.setChecked(true);
                toilet="Yes";
            } else if (medInfo.getToilet().equals("No")) {
                tbToilet.setChecked(false);
                toilet="No";
            }

            if (medInfo.getFeed().equals("Yes")) {
                tbFeed.setChecked(true);
                feed="Yes";
            } else if (medInfo.getFeed().equals("No")) {
                tbFeed.setChecked(false);
                feed="No";
            }

            if (medInfo.getSpeech().equals("Yes")) {
                tbSpeech.setChecked(true);
                speech="Yes";
            } else if (medInfo.getSpeech().equals("No")) {
                tbSpeech.setChecked(false);
                speech="No";
            }
            if (medInfo.getBlind().equals("Yes")) {
                tbColor.setChecked(true);
                blind="Yes";
            } else if (medInfo.getBlind().equals("No")) {
                tbColor.setChecked(false);
                blind="No";
            }
            if (medInfo.getGlass().equals("Yes")) {
                tbGlass.setChecked(true);
                glass="Yes";
            } else if (medInfo.getGlass().equals("No")) {
                tbGlass.setChecked(false);
                glass="No";
            }

            if (medInfo.getMouth().equals("Yes")) {
                tbMouth.setChecked(true);
                mouth="Yes";
            } else if (medInfo.getMouth().equals("No")) {
                tbMouth.setChecked(false);
                mouth="No";
            }

            if (medInfo.getLense().equals("Yes")) {
                tbLense.setChecked(true);
                lense="Yes";
            } else if (medInfo.getLense().equals("No")) {
                tbLense.setChecked(false);
                lense="No";
            }

            if (medInfo.getFalses().equals("Yes")) {
                tbFalse.setChecked(true);
                falses="Yes";
            } else if (medInfo.getFalses().equals("No")) {
                tbFalse.setChecked(false);
                falses="No";
            }

            if (medInfo.getImplants().equals("Yes")) {
                tbImplants.setChecked(true);
                implants="Yes";
            } else if (medInfo.getImplants().equals("No")) {
                tbImplants.setChecked(false);
                implants="No";
            }

            if (medInfo.getAid().equals("Yes")) {
                tbHearingAid.setChecked(true);
                aid="Yes";
            } else if (medInfo.getAid().equals("No")) {
                tbHearingAid.setChecked(false);
                aid="No";
            }

            if (medInfo.getDonor().equals("Yes")) {
                rbYes.setChecked(true);
                donor="Yes";
            } else if (medInfo.getDonor().equals("No")) {
                rbNo.setChecked(true);
                donor="No";
            }
        }
    }

    private void setImplantData() {
        final ArrayList<String> implantsList = MedicalImplantsQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (implantsList.size() != 0) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, implantsList);
            ListImplants.setAdapter(adapter);
            ListImplants.setVisibility(View.VISIBLE);
            ListImplants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
                    ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
                    imgEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           String value=implantsList.get(position);
                            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                            allergyIntent.putExtra("IsAllergy", false);
                            allergyIntent.putExtra("IsHistory", false);

                            allergyIntent.putExtra("ADD", "ImplantUpdate");
                            allergyIntent.putExtra("Title", "Update Implant");
                            allergyIntent.putExtra("Name", "Add Implant");
                            allergyIntent.putExtra("ImplantObject", value);
                            startActivityForResult(allergyIntent, REQUEST_IMPLANTS);
                        }
                    });

                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean flag = MedicalImplantsQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),implantsList.get(position));
                            if (flag == true) {
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                setImplantData();
                                ListImplants.requestFocus();
                            }
                        }
                    });
                }
            });
        } else {
            ListImplants.setVisibility(View.GONE);
        }
    }

    private void setHistoryData() {
        final ArrayList allergyList = new ArrayList();
        final ArrayList<History> HistoryLists = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (HistoryLists.size() != 0) {
            ListHistory.setVisibility(View.VISIBLE);
            for (int i = 0; i < HistoryLists.size(); i++) {
                History a = HistoryLists.get(i);
                String allergy = "History: " + a.getName() + "\nDate: " + a.getDate() + "\nDoctor: " + a.getDoctor() + "\nDone At: " + a.getDone();
                allergyList.add(allergy);
            }

           // final ArrayList<History> historList = HistoryQuery.fetchHistoryRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
            if (allergyList.size() != 0) {
                ArrayAdapter<History> adapter = new ArrayAdapter<History>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, allergyList);
                ListHistory.setAdapter(adapter);
                ListHistory.setVisibility(View.VISIBLE);
                ListHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
                        ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
                        imgEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                History value = HistoryLists.get(position);
                                Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                                allergyIntent.putExtra("IsAllergy", false);
                                allergyIntent.putExtra("IsHistory", true);
                                allergyIntent.putExtra("ADD", "HistoryUpdate");
                                allergyIntent.putExtra("Title", "Update Medical History");
                                allergyIntent.putExtra("Name", "Add Medical History");
                                allergyIntent.putExtra("HistoryObject", value);
                                startActivityForResult(allergyIntent, REQUEST_HISTORY);
                            }
                        });

                        imgDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean flag = HistoryQuery.deleteRecord(HistoryLists.get(position).getId());
                                if (flag == true) {
                                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                    setHistoryData();
                                    ListHistory.requestFocus();
                                }
                            }
                        });
                    }
                });
            } else {
                ListHistory.setVisibility(View.GONE);
            }
        }
    }

    private void setHospitalData() {
        final ArrayList<String> hospitalList = HospitalQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (hospitalList.size() != 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, hospitalList);
            ListHospital.setAdapter(adapter);
            ListHospital.setVisibility(View.VISIBLE);
            ListHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    ImageView imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
                    ImageView imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
                    imgEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String value=hospitalList.get(position);
                            Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                            allergyIntent.putExtra("IsAllergy", false);
                            allergyIntent.putExtra("IsHistory", false);
                            allergyIntent.putExtra("ADD", "HospitalUpdate");
                            allergyIntent.putExtra("Title", "Update Hospital");
                            allergyIntent.putExtra("Name", "Add Hospital");
                            allergyIntent.putExtra("HospitalObject", value);
                            startActivityForResult(allergyIntent, REQUEST_HOSPITAL);
                        }
                    });

                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            boolean flag = HospitalQuery.deleteRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),hospitalList.get(position));
                            if (flag == true) {
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                                setHospitalData();
                                ListHospital.requestFocus();
                            }
                        }
                    });
                }
            });
        } else {
            ListHospital.setVisibility(View.GONE);
        }
    }

    private void setAllergyData() {
        final ArrayList allergyList = new ArrayList();
        final ArrayList<Allergy> AllargyLists = AllergyQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (AllargyLists.size() != 0) {
            ListAllergy.setVisibility(View.VISIBLE);
            for (int i = 0; i < AllargyLists.size(); i++) {
                Allergy a = AllargyLists.get(i);
                String allergy = "Allergy: " + a.getAllergy() + "\nReaction: " + a.getReaction() + "\nTreatment: " + a.getTreatment();
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, allergyList);
                ListAllergy.setAdapter(adapter);
                ListAllergy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        ImageView imgEdit= (ImageView) view.findViewById(R.id.imgEdit);
                        ImageView imgDelete= (ImageView) view.findViewById(R.id.imgDelete);
                        imgEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Allergy a=AllargyLists.get(position);
                                Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                                allergyIntent.putExtra("IsAllergy", true);
                                allergyIntent.putExtra("IsHistory", false);
                                allergyIntent.putExtra("ADD", "AllergyUpdate");
                                allergyIntent.putExtra("Title", "Update Allergy");
                                allergyIntent.putExtra("Name", "Add Allergy");
                                allergyIntent.putExtra("AllergyObject",a);
                                startActivityForResult(allergyIntent, REQUEST_ALLERGY);
                            }
                        });

                        imgDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Allergy a=AllargyLists.get(position);
                                boolean flag= AllergyQuery.deleteRecord(a.getId());
                                if(flag==true)
                                {
                                    Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                                    setAllergyData();
                                    ListAllergy.requestFocus();
                                }
                            }
                        });
                    }
                });
            }
        }
        else{
            ListAllergy.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //  String ft,inch,weight,color,lang1,lang2,blood,pet;

            case R.id.imgAddPneumonia:
                getDate(getActivity(),imgAddPneumonia);
                break;

            case R.id.imgAddFlueShot:
                getDate(getActivity(),imgAddFlueShot);
                break;

            case R.id.imgAddHPV:
                getDate(getActivity(),imgAddHPV);
                break;

            case R.id.imgAddRubella:
                getDate(getActivity(),imgAddRubella);
                break;

            case R.id.imgAddVaricella:
                getDate(getActivity(),imgAddVaricella);
                break;

            case R.id.imgAddShingles:
                getDate(getActivity(),imgAddShingles);
                break;

            case R.id.imgAddTetanus:
                getDate(getActivity(),imgAddTetanus);
                break;

            case R.id.imgAddHepatitis:
                getDate(getActivity(),imgAddHepatitis);
                break;

            case R.id.imgAddFlue:
                getDate(getActivity(),imgAddFlue);
                break;

            case R.id.imgAddFlueNH:
                getDate(getActivity(),imgAddFlueNH);
                break;

            case R.id.imgAddPneumococcal:
                getDate(getActivity(),imgAddPneumococcal);
                break;


            case R.id.imgDone:
                ft = etFt.getText().toString().trim();
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
                }

                break;
            case R.id.imgBack:
                getActivity().finish();
                break;
            case R.id.imgAddAllergy:
                Intent allergyIntent = new Intent(getActivity(), AddInfoActivity.class);
                allergyIntent.putExtra("IsAllergy", true);
                allergyIntent.putExtra("IsHistory", false);
                allergyIntent.putExtra("ADD", "Allergy");
                allergyIntent.putExtra("Title", "Add Allergy");
                allergyIntent.putExtra("Name", "Add Allergy");
                startActivityForResult(allergyIntent, REQUEST_ALLERGY);
                break;
            case R.id.imgAddImplants:
                Intent implantsIntent = new Intent(getActivity(), AddInfoActivity.class);
                implantsIntent.putExtra("IsAllergy", false);
                implantsIntent.putExtra("IsHistory", false);
                implantsIntent.putExtra("ADD", "Implants");
                implantsIntent.putExtra("Title", "Add Implants");
                implantsIntent.putExtra("Name", "Add Implantaion you have done");
                startActivityForResult(implantsIntent, REQUEST_IMPLANTS);
                break;
            case R.id.imgAddCondition:
                Intent implantsIntents = new Intent(getActivity(), AddInfoActivity.class);
                implantsIntents.putExtra("IsAllergy", false);
                implantsIntents.putExtra("IsHistory", false);
                implantsIntents.putExtra("ADD", "Condition");
                implantsIntents.putExtra("Title", "Add Medical Condition");
                implantsIntents.putExtra("Name", "Add Pre existing Medical Condtion");
                startActivityForResult(implantsIntents, REQUEST_CONDITION);
                break;
            case R.id.imgAddHospital:
                Intent hospIntent = new Intent(getActivity(), AddInfoActivity.class);
                hospIntent.putExtra("IsAllergy", false);
                hospIntent.putExtra("IsHistory", false);
                hospIntent.putExtra("ADD", "Hospital");
                hospIntent.putExtra("Title", "Add Hospital");
                hospIntent.putExtra("Name", "Add hospital preferences");
                startActivityForResult(hospIntent, REQUEST_HOSPITAL);
                break;
            case R.id.imgAddHistory:
                Intent historyIntent = new Intent(getActivity(), AddInfoActivity.class);
                historyIntent.putExtra("IsAllergy", false);
                historyIntent.putExtra("IsHistory", true);
                historyIntent.putExtra("ADD", "History");
                historyIntent.putExtra("Title", "Add Medical History");
                historyIntent.putExtra("Name", "Add Medical History");
                startActivityForResult(historyIntent, REQUEST_HISTORY);
                break;
        }
    }

    private void getDate(Context context, final TextView txtview) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                long selectedMilli = newDate.getTimeInMillis();

                Date datePickerDate = new Date(selectedMilli);
                String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                DateClass d=new DateClass();
                d.setDate(reportDate);
                txtview.setText(reportDate);
                txtview.setVisibility(View.VISIBLE);
            }
        }, year, month, day);
        dpd.show();
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
        } else if (REQUEST_CONDITION == requestCode) {
            /*String value=data.getExtras().getString("Value");
            implantsList.add(value);*/
            setConditionData();
            ListCondition.requestFocus();
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

            case R.id.tbBlind:
                if (isChecked == true)
                    blind = "Yes";
                else
                    blind = "No";
                break;

            case R.id.tbfeed:
                if (isChecked == true)
                    feed = "Yes";
                else
                    feed = "No";
                break;

            case R.id.tbToileting:
                if (isChecked == true)
                    toilet = "Yes";
                else
                    toilet = "No";
                break;
            case R.id.tbMedicate:
                if (isChecked == true)
                    medicate = "Yes";
                else
                    medicate = "No";
                break;
            case R.id.tbSpeech:
                if (isChecked == true)
                    speech = "Yes";
                else
                    speech = "No";
                break;

            case R.id.tbMouth:
                if (isChecked == true)
                    mouth = "Yes";
                else
                    mouth = "No";
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
