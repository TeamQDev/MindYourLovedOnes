package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.AllergyQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.HistoryQuery;
import com.mindyourelders.MyHealthCareWishes.database.HospitalQuery;
import com.mindyourelders.MyHealthCareWishes.database.MedicalImplantsQuery;
import com.mindyourelders.MyHealthCareWishes.model.Allergy;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;


public class AddInfoActivity extends AppCompatActivity  implements View.OnClickListener{
    Context context=this;
    ImageView imgBack;
    RelativeLayout llAddConn;
    TextView txtName,txtReaction,txtTreatment,txtTitle,txtAdd;
    TextInputLayout tilTitle,tilReaction,tilTreatment;
    public static final int RESULT_ALLERGY=100;
    public static final int RESULT_HISTORY=200;
    public static final int RESULT_IMPLANTS=300;
    public static final int RESULT_HOSPITAL=400;
    String from,name,title;
    Boolean isAllergy;
    Preferences preferences;
    DBHelper dbHelper;
    int id;
    String data="";

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

            isAllergy=i.getExtras().getBoolean("IsAllergy");
            if (isAllergy==true)
            {
                tilTreatment.setVisibility(View.VISIBLE);
                tilReaction.setVisibility(View.VISIBLE);
            }
            else{
                tilTreatment.setVisibility(View.GONE);
                tilReaction.setVisibility(View.GONE);
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
                    String value= i.getExtras().getString("ImplantObject");
                    txtName.setText(value);
                    data=value;
                    break;

                case "HospitalUpdate":
                    String values= i.getExtras().getString("HospitalObject");
                    txtName.setText(values);
                    data=values;
                    break;

                case "HistoryUpdate":
                    String values1= i.getExtras().getString("HistoryObject");
                    txtName.setText(values1);
                    data=values1;
                    break;
            }

        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
    }

    private void initUi() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        llAddConn= (RelativeLayout) findViewById(R.id.llAddConn);
        tilTitle= (TextInputLayout) findViewById(R.id.tilTitle);
        txtName= (TextView) findViewById(R.id.txtName);
        tilReaction= (TextInputLayout) findViewById(R.id.tilReaction);
        txtReaction= (TextView) findViewById(R.id.txtReaction);
        txtAdd= (TextView) findViewById(R.id.txtAdd);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        tilTreatment= (TextInputLayout) findViewById(R.id.tilTreatment);
        txtTreatment= (TextView) findViewById(R.id.txtTreatment);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;
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
                            Boolean flag1 = MedicalImplantsQuery.insertImplantsData(preferences.getInt(PrefConstants.CONNECTED_USERID),value);
                            if (flag1 == true) {
                                Toast.makeText(context, "Implants Added Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentImplants = new Intent();
                           // intentImplants.putExtra("Value", value);
                            setResult(RESULT_IMPLANTS, intentImplants);
                            finish();
                            break;

                        case "ImplantUpdate":
                            Boolean flag1s = MedicalImplantsQuery.updateImplantsData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,data);
                            if (flag1s == true) {
                                Toast.makeText(context, "Implants updated Succesfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }

                            Intent intentImplant = new Intent();
                            // intentImplants.putExtra("Value", value);
                            setResult(RESULT_IMPLANTS, intentImplant);
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
                            Boolean flag3= HistoryQuery.insertHistoryData(preferences.getInt(PrefConstants.CONNECTED_USERID),value);
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
                            Boolean flag3s= HistoryQuery.updateHistoryData(preferences.getInt(PrefConstants.CONNECTED_USERID),value,data);
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
}
