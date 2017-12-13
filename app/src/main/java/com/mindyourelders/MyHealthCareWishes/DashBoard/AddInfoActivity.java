package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AllergyQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.HistoryQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalConditionQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalImplantsQuery;
import com.mindyourelders.MyHealthCareWishes.database.VaccineQuery;
import com.mindyourelders.MyHealthCareWishes.model.Allergy;
import com.mindyourelders.MyHealthCareWishes.model.History;
import com.mindyourelders.MyHealthCareWishes.model.Implant;
import com.mindyourelders.MyHealthCareWishes.model.Vaccine;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;


public class AddInfoActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final int RESULT_CONDITION =500 ;
    private static final int RESULT_VACCINE =700 ;
    Context context=this;
    ImageView imgBack,imgInfo;
    RelativeLayout llAddConn,rlInfo,rlPdf;
    TextView txtName,txtReaction,txtTreatment,txtTitle,txtAdd,txtDate,txtDoctor,txtDone;
    TextInputLayout tilTitle,tilReaction,tilTreatment,tilDate,tilDoctor,tilDone;
    public static final int RESULT_ALLERGY=100;
    public static final int RESULT_HISTORY=200;
    public static final int RESULT_IMPLANTS=300;
    public static final int RESULT_HOSPITAL=400;
    String from,name,title;
    Boolean isAllergy,isHistory,isImplant;
    Preferences preferences;
    DBHelper dbHelper;
    int id;
    String data="";
    String header="";
            String msg="";
TextView txtHeader,txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        initUi();
        initListener();
        InitComponent();
    }

    private void InitComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        AllergyQuery a=new AllergyQuery(context,dbHelper);

        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
           from=i.getExtras().getString("ADD");
            name=i.getExtras().getString("Name");
            title=i.getExtras().getString("Title");
            txtTitle.setText(title);
            txtAdd.setText(title);
            tilTitle.setHint(name);
            tilTitle.setHintEnabled(false);
            txtName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    tilTitle.setHintEnabled(true);
                    txtName.setFocusable(true);

                    return false;
                }
            });
              switch (from) {
                case "Allergy":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;

                case "AllergyUpdate":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;
                case "Implants":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;

                case "ImplantUpdate":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;
                case "Condition":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;

                case "ConditionUpdate":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;
                case "Hospital":
                    rlPdf.setVisibility(View.GONE);
                    break;

                case "HospitalUpdate":
                    rlPdf.setVisibility(View.GONE);
                    break;
                case "History":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;

                case "HistoryUpdate":
                    rlPdf.setVisibility(View.VISIBLE);
                    break;

                case "Vaccine":
                    rlPdf.setVisibility(View.GONE);
                    break;

                case "VaccineUpdate":
                    rlPdf.setVisibility(View.GONE);
                    break;
            }
            switch (from) {


                case "Allergy":
                    header = "Types of Reaction to Consider :";
                    msg =   "<br>Anaphylaxis<br>" +
                            "Difficulty Breathing<br>" +
                            "Hives<br>" +
                            "Nausea<br>" +
                            "Rash<br>" +
                            "Vomiting";
                   txtHeader.setText(header);
                   txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "AllergyUpdate":
                    header = "Types of Reaction to Consider :";
                    msg =   "<br>Anaphylaxis<br>" +
                            "Difficulty Breathing<br>" +
                            "Hives<br>" +
                            "Nausea<br>" +
                            "Rash<br>" +
                            "Vomiting";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;
                case "Implants":
                    header = "Medical Implants to Consider :";
                    msg = "<br><ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                            "<li>Artifical Limbs</li>" +
                            "<li>Artificial Heart Value</li>" +
                            "<li>Body Art</li>" +
                            "<li>Body Piercing </li>" +
                            "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                            "<li>Gastric Band</li>" +
                            "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                            "<li>Implanted Devices/Pumps/Stimulator</li>" +
                            "<li>Joint Replacements (specify)______</li>" +
                            "<li>Lens Implants</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Pacemaker</li>" +
                            "<li>Penile Implant</li>" +
                            "<li>Pins/Rods/Screws (specify)</li>" +
                            "<li>Prosthetic Eye</li>" +
                            "<li>Renal or other Stents</li>" +
                            "<li>Tracheotomy</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "ImplantUpdate":
                    header = "Medical Implants to Consider :";
                    msg = "<br><ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                            "<li>Artifical Limbs</li>" +
                            "<li>Artificial Heart Value</li>" +
                            "<li>Body Art</li>" +
                            "<li>Body Piercing </li>" +
                            "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                            "<li>Gastric Band</li>" +
                            "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                            "<li>Implanted Devices/Pumps/Stimulator</li>" +
                            "<li>Joint Replacements (specify)______</li>" +
                            "<li>Lens Implants</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Pacemaker</li>" +
                            "<li>Penile Implant</li>" +
                            "<li>Pins/Rods/Screws (specify)</li>" +
                            "<li>Prosthetic Eye</li>" +
                            "<li>Renal or other Stents</li>" +
                            "<li>Tracheotomy</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                   break;
                case "Condition":
                    header = "Recommendation:<br>Use Medical History Template";
                    msg =   "<br>" +
                            "Click on<br>" +
                            "Resources/Forms and Templates/Medical History Template" ;
                    txtHeader.setText(Html.fromHtml(header));
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "ConditionUpdate":
                    header = "Recommendation:<br>Use Medical History Template";
                    msg =   "<br>" +
                            "Click on<br>" +
                            "Resources/Forms and Templates/Medical History Template" ;
                    txtHeader.setText(Html.fromHtml(header));
                    txtInfo.setText(Html.fromHtml(msg));
                    break;
                case "Hospital":

                    break;

                case "HospitalUpdate":

                    break;
                case "History":
                    header="Surgical History to Consider :";
                    msg="<br><ul><li>Appendix</li>" +
                            "<li>Breast Biopsy/Mastectomy</li>" +
                            "<li>Cataract</li>" +
                            "<li>Colon</li>" +
                            "<li>Gallbladder</li>" +
                            "<li>Heart, Angio/Stent</li>" +
                            "<li>Heart, Bypass</li>" +
                            "<li>Heart, Valve</li>" +
                            "<li>Hernia</li>" +
                            "<li>Hip Replacement</li>" +
                            "<li>Hysterectomy</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Knee Surgery/Replacement</li>" +
                            "<li>Lasik Surgery</li>" +
                            "<li>Spine Surgery</li>" +
                            "<li>Thyroid Surgery</li>" +
                            "<li>Tonsils</li>" +
                            "<li>Vascular Surgery</li>" +
                            "<li>Wisdom Teeth</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;

                case "HistoryUpdate":
                    header="Surgical History to Consider :";
                    msg="<br><ul><li>Appendix</li>" +
                            "<li>Breast Biopsy/Mastectomy</li>" +
                            "<li>Cataract</li>" +
                            "<li>Colon</li>" +
                            "<li>Gallbladder</li>" +
                            "<li>Heart, Angio/Stent</li>" +
                            "<li>Heart, Bypass</li>" +
                            "<li>Heart, Valve</li>" +
                            "<li>Hernia</li>" +
                            "<li>Hip Replacement</li>" +
                            "<li>Hysterectomy</li>" +
                            "<li>Metal Implants</li>" +
                            "<li>Middle Ear Prosthesis</li>" +
                            "<li>Knee Surgery/Replacement</li>" +
                            "<li>Lasik Surgery</li>" +
                            "<li>Spine Surgery</li>" +
                            "<li>Thyroid Surgery</li>" +
                            "<li>Tonsils</li>" +
                            "<li>Vascular Surgery</li>" +
                            "<li>Wisdom Teeth</li></ul>";
                    txtHeader.setText(header);
                    txtInfo.setText(Html.fromHtml(msg));
                    break;
            }
          /*  switch (from) {
                case "Allergy":
                  imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "AllergyUpdate":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;
                case "Implants":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "ImplantUpdate":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;
                case "Condition":
                    imgInfo.setVisibility(View.GONE);
                    break;

                case "ConditionUpdate":
                    imgInfo.setVisibility(View.GONE);
                    break;
                case "Hospital":
                    imgInfo.setVisibility(View.GONE);
                    break;

                case "HospitalUpdate":
                    imgInfo.setVisibility(View.GONE);
                    break;
                case "History":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "HistoryUpdate":
                    imgInfo.setVisibility(View.VISIBLE);
                    break;

                case "Vaccine":
                    imgInfo.setVisibility(View.GONE);
                    break;

                case "VaccineUpdate":
                    imgInfo.setVisibility(View.GONE);
                    break;
            }*/
            isAllergy=i.getExtras().getBoolean("IsAllergy");
            isHistory=i.getExtras().getBoolean("IsHistory");
            isImplant=i.getExtras().getBoolean("IsImplant");
            if (isAllergy==true)
            {
                tilTreatment.setVisibility(View.VISIBLE);
                tilReaction.setVisibility(View.VISIBLE);
            }
            else{
                tilTreatment.setVisibility(View.GONE);
                tilReaction.setVisibility(View.GONE);
            }

           if (isHistory==true)
            {
                tilDone.setVisibility(View.VISIBLE);
                tilDoctor.setVisibility(View.VISIBLE);
               // tilDate.setVisibility(View.VISIBLE);
            }else{
                tilDone.setVisibility(View.GONE);
                tilDoctor.setVisibility(View.GONE);
                //tilDate.setVisibility(View.GONE);
            }
            if (isImplant==true||isHistory==true)
            {
                tilDate.setVisibility(View.VISIBLE);
            }else{
                tilDate.setVisibility(View.GONE);
            }

            switch (from) {
                case "AllergyUpdate":
                    Allergy allergy= (Allergy) i.getExtras().getSerializable("AllergyObject");
                    txtName.setText(allergy.getAllergy());
                    txtReaction.setText(allergy.getReaction());
                    txtTreatment.setText(allergy.getTreatment());
                    id=allergy.getId();
                    break;

                case "ImplantUpdate":
                    Implant implant= (Implant) i.getExtras().getSerializable("ImplantObject");
                    txtName.setText(implant.getName());
                    txtDate.setText(implant.getDate());
                    id=implant.getId();
                    break;

                case "VaccineUpdate":
                    Vaccine vaccine= (Vaccine) i.getExtras().getSerializable("VaccineObject");
                    txtName.setText(vaccine.getName());
                    txtDate.setText(vaccine.getDate());
                    id=vaccine.getId();
                    break;

                case "ConditionUpdate":
                    String valuef= i.getExtras().getString("ConditionObject");
                    txtName.setText(valuef);
                    data=valuef;
                    break;

                case "HospitalUpdate":
                    String values= i.getExtras().getString("HospitalObject");
                    txtName.setText(values);
                    data=values;
                    break;

                case "HistoryUpdate":
                    History history= (History) i.getExtras().getSerializable("HistoryObject");
                    txtName.setText(history.getName());
                    txtDone.setText(history.getDone());
                    txtDate.setText(history.getDate());
                    txtDoctor.setText(history.getDoctor());
                    id=history.getId();
                    break;

            }

        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgInfo.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
        txtDate.setOnClickListener(this);
    }

    private void initUi() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgInfo= (ImageView) findViewById(R.id.imgInfo);
        llAddConn= (RelativeLayout) findViewById(R.id.llAddConn);
        tilTitle= (TextInputLayout) findViewById(R.id.tilTitle);
        txtName= (TextView) findViewById(R.id.txtName);
        tilReaction= (TextInputLayout) findViewById(R.id.tilReaction);
        txtReaction= (TextView) findViewById(R.id.txtReaction);
        txtAdd= (TextView) findViewById(R.id.txtAdd);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtHeader= (TextView) findViewById(R.id.txtHeader);
        txtInfo= (TextView) findViewById(R.id.txtInfo);

        txtDate= (TextView) findViewById(R.id.txtDate);
        txtDoctor= (TextView) findViewById(R.id.txtDoctor);
        txtDone= (TextView) findViewById(R.id.txtDone);
        tilTreatment= (TextInputLayout) findViewById(R.id.tilTreatment);
        txtTreatment= (TextView) findViewById(R.id.txtTreatment);

        tilDate= (TextInputLayout) findViewById(R.id.tilDate);
        tilDoctor= (TextInputLayout) findViewById(R.id.tilDoctor);
        tilDone= (TextInputLayout) findViewById(R.id.tilDone);

        rlPdf= (RelativeLayout) findViewById(R.id.rlPdf);
        rlInfo= (RelativeLayout) findViewById(R.id.rlInfo);
        rlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              hideSoftKeyboard();
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                hideSoftKeyboard();
                finish();
                break;

            case R.id.imgInfo:
                switch (from) {
                    case "Allergy":
                         header="Allergy";
                         msg="<b>Types of Reaction to Consider :</b><br>"+
                                "Anaphylaxis<br>" +
                                "Difficulty Breathing<br>" +
                                "Hives<br>" +
                                "Nausea<br>" +
                                "Rash<br>" +
                                "Vomiting <br>";
                        showViewDialog(context,msg,header);
                        break;

                    case "AllergyUpdate":
                        header="Allergy";
                      msg="<b>Types of Reaction to Consider :</b><br>"+
                                "Anaphylaxis<br>" +
                                "Difficulty Breathing<br>" +
                                "Hives<br>" +
                                "Nausea<br>" +
                                "Rash<br>" +
                                "Vomiting <br>";
                        showViewDialog(context,msg,header);
                        break;
                    case "Implants":
                        header="Medical Implants to Consider";
                        msg="<ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                                "<li>Artifical Limbs</li>" +
                                "<li>Artificial Heart Value</li>" +
                                "<li>Body Art</li>" +
                                "<li>Body Piercing </li>" +
                                "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                                "<li>Gastric Band</li>" +
                                "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                                "<li>Implanted Devices/Pumps/Stimulator</li>" +
                                "<li>Joint Replacements (specify)______</li>" +
                                "<li>Lens Implants</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Pacemaker</li>" +
                                "<li>Penile Implant</li>" +
                                "<li>Pins/Rods/Screws (specify)</li>" +
                                "<li>Prosthetic Eye</li>" +
                                "<li>Renal or other Stents</li>" +
                                "<li>Tracheotomy</li></ul>";
                        showViewDialog(context,msg,header);
                        break;

                    case "ImplantUpdate":
                        header="Medical Implants to Consider";
                        msg="<ul><li>Aneurysm Stent or Aneurysm Clip</li>" +
                                "<li>Artifical Limbs</li>" +
                                "<li>Artificial Heart Value</li>" +
                                "<li>Body Art</li>" +
                                "<li>Body Piercing </li>" +
                                "<li>Coronary Stents (Drug Coated/Bare Methal/Unknown)</li>" +
                                "<li>Gastric Band</li>" +
                                "<li>Implanted Cardio Defibrilator (ICD)</li>" +
                                "<li>Implanted Devices/Pumps/Stimulator</li>" +
                                "<li>Joint Replacements (specify)______</li>" +
                                "<li>Lens Implants</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Pacemaker</li>" +
                                "<li>Penile Implant</li>" +
                                "<li>Pins/Rods/Screws (specify)</li>" +
                                "<li>Prosthetic Eye</li>" +
                                "<li>Renal or other Stents</li>" +
                                "<li>Tracheotomy</li></ul>";
                        showViewDialog(context,msg,header);
                        break;
                    case "Condition":

                        break;

                    case "ConditionUpdate":

                        break;
                    case "Hospital":

                        break;

                    case "HospitalUpdate":

                        break;
                    case "History":
                        header="Surgical History to Consider";
                        msg="<ul><li>Appendix</li>" +
                                "<li>Breast Biopsy/Mastectomy</li>" +
                                "<li>Cataract</li>" +
                                "<li>Colon</li>" +
                                "<li>Gallbladder</li>" +
                                "<li>Heart, Angio/Stent</li>" +
                                "<li>Heart, Bypass</li>" +
                                "<li>Heart, Valve</li>" +
                                "<li>Hernia</li>" +
                                "<li>Hip Replacement</li>" +
                                "<li>Hysterectomy</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Knee Surgery/Replacement</li>" +
                                "<li>Lasik Surgery</li>" +
                                "<li>Spine Surgery</li>" +
                                "<li>Thyroid Surgery</li>" +
                                "<li>Tonsils</li>" +
                                "<li>Vascular Surgery</li>" +
                                "<li>Wisdom Teeth</li></ul>";
                        showViewDialog(context,msg,header);
                        break;

                    case "HistoryUpdate":
                        header="Surgical History to Consider";
                        msg="<ul><li>Appendix</li>" +
                                "<li>Breast Biopsy/Mastectomy</li>" +
                                "<li>Cataract</li>" +
                                "<li>Colon</li>" +
                                "<li>Gallbladder</li>" +
                                "<li>Heart, Angio/Stent</li>" +
                                "<li>Heart, Bypass</li>" +
                                "<li>Heart, Valve</li>" +
                                "<li>Hernia</li>" +
                                "<li>Hip Replacement</li>" +
                                "<li>Hysterectomy</li>" +
                                "<li>Metal Implants</li>" +
                                "<li>Middle Ear Prosthesis</li>" +
                                "<li>Knee Surgery/Replacement</li>" +
                                "<li>Lasik Surgery</li>" +
                                "<li>Spine Surgery</li>" +
                                "<li>Thyroid Surgery</li>" +
                                "<li>Tonsils</li>" +
                                "<li>Vascular Surgery</li>" +
                                "<li>Wisdom Teeth</li></ul>";
                        showViewDialog(context,msg,header);
                        break;
                }

                break;
           /* case R.id.txtDate:
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
                        txtDate.setText(reportDate);
                    }
                }, year, month, day);
                dpd.show();
                break;*/
            case R.id.llAddConn:
                String value=txtName.getText().toString().trim();
                if (value.length()!=0) {
                    switch (from) {
                        case "Allergy":
                            String reaction=txtReaction.getText().toString();
                            String treatment=txtTreatment.getText().toString();
                            Boolean flag = AllergyQuery.insertAllergyData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,reaction,treatment);
                            if (flag == true) {
                                Toast.makeText(context, "Allergy Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentAllergy = new Intent();
                           /* intentAllergy.putExtra("Value", value);
                            intentAllergy.putExtra("Reaction", reaction);
                            intentAllergy.putExtra("Treatment", treatment);*/
                            setResult(RESULT_ALLERGY, intentAllergy);
                            finish();
                            break;

                        case "AllergyUpdate":
                            String reactions=txtReaction.getText().toString();
                            String treatments=txtTreatment.getText().toString();
                            Boolean flags = AllergyQuery.updateAllergyData(id,value,reactions,treatments);
                            if (flags == true) {
                                Toast.makeText(context, "Allergy updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentAllergys = new Intent();
                           /* intentAllergy.putExtra("Value", value);
                            intentAllergy.putExtra("Reaction", reaction);
                            intentAllergy.putExtra("Treatment", treatment);*/
                            setResult(RESULT_ALLERGY, intentAllergys);
                            finish();
                            break;
                        case "Implants":
                            String dater=txtDate.getText().toString();
                            Boolean flage = MedicalImplantsQuery.insertImplantData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,dater);
                            if (flage == true) {
                                Toast.makeText(context, "Implant Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVaccines = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_IMPLANTS, intentVaccines);
                            finish();
                            break;

                        case "Vaccine":
                            String datev=txtDate.getText().toString();
                            Boolean flagr = VaccineQuery.insertVaccineData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,datev);
                            if (flagr == true) {
                                Toast.makeText(context, "Vaccine Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVaccine = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_VACCINE, intentVaccine);
                            finish();
                            break;

                        case "VaccineUpdate":
                            String dates=txtDate.getText().toString();
                            Boolean flagf = VaccineQuery.updateVaccineData(id,value,dates);
                            if (flagf == true) {
                                Toast.makeText(context, "Vaccine Updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVaccinef = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_VACCINE, intentVaccinef);
                            finish();
                            break;

                        case "ImplantUpdate":
                            String datee=txtDate.getText().toString();
                            Boolean flagw= MedicalImplantsQuery.updateImplantData(id,value,datee);
                            if (flagw == true) {
                                Toast.makeText(context, "Implant Updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentVacciney = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_IMPLANTS, intentVacciney);
                            finish();
                            break;
                        case "Condition":
                            Boolean flagj = MedicalConditionQuery.insertImplantsData(preferences.getInt(PrefConstants.CONNECTED_USERID),value);
                            if (flagj == true) {
                                Toast.makeText(context, "Medical Condition Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentImplantss = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_CONDITION, intentImplantss);
                            finish();
                            break;

                        case "ConditionUpdate":
                            Boolean flag1ss = MedicalConditionQuery.updateImplantsData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,data);
                            if (flag1ss == true) {
                                Toast.makeText(context, "Medical Condition updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentImplanto = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_CONDITION,intentImplanto);
                            finish();
                            break;
                        case "Hospital":
                            Boolean flag2 = HospitalQuery.insertHospitalData(preferences.getInt(PrefConstants.CONNECTED_USERID),value);
                            if (flag2 == true) {
                                Toast.makeText(context, "Hospital Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHospital = new Intent();
                          //  intentHospital.putExtra("Value", value);
                            setResult(RESULT_HOSPITAL, intentHospital);
                            finish();
                            break;

                        case "HospitalUpdate":
                            Boolean flag2s = HospitalQuery.updateHospitalData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,data);
                            if (flag2s == true) {
                                Toast.makeText(context, "Hospital updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHospitals = new Intent();
                            //  intentHospital.putExtra("Value", value);
                            setResult(RESULT_HOSPITAL, intentHospitals);
                            finish();
                            break;
                        case "History":
                            String date=txtDate.getText().toString();
                            String doctor=txtDoctor.getText().toString();
                            String done=txtDone.getText().toString();
                            Boolean flag3= HistoryQuery.insertHistoryData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,date,doctor,done);
                            if (flag3 == true) {
                                Toast.makeText(context, "History Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHistory = new Intent();
                            // intentHistory.putExtra("Value", value);
                            setResult(RESULT_HISTORY, intentHistory);
                            finish();
                            break;

                        case "HistoryUpdate":
                            String dateu=txtDate.getText().toString();
                            String doctors=txtDoctor.getText().toString();
                            String dones=txtDone.getText().toString();
                            Boolean flag3s= HistoryQuery.updateHistoryData(id,value,dateu,doctors,dones);
                            if (flag3s == true) {
                                Toast.makeText(context, "History updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentHistorys = new Intent();
                            // intentHistory.putExtra("Value", value);
                            setResult(RESULT_HISTORY, intentHistorys);
                            finish();
                            break;
                    }
                    break;
                }
                else{
                    Toast.makeText(context,"Value should not be empty",Toast.LENGTH_SHORT).show();
                }
        }
    }
    private void showViewDialog(Context context,String Message,String title) {
        final Dialog customDialog;

        // LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
        //  View customView = inflater.inflate(R.layout.dialog_input, null);
        // Build the dialog
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_living);
        customDialog.setCancelable(false);
        TextView txtNotes= (TextView) customDialog.findViewById(R.id.txtNotes);
        txtNotes.setText(Html.fromHtml(Message));
        TextView txtNoteHeader= (TextView) customDialog.findViewById(R.id.txtNoteHeader);
        txtNoteHeader.setText(title);
        TextView btnYes= (TextView) customDialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

public void hideSoftKeyboard() {
    if (getCurrentFocus() != null) {
        InputMethodManager inm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
}
