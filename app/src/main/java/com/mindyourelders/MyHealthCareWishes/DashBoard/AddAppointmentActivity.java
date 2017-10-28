package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.AppointmentQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DateQuery;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class AddAppointmentActivity extends AppCompatActivity implements View.OnClickListener{
    Context context=this;
    TextView txtName,txtDate,txtOtherSpecialist,txtOtherFrequency;
    Preferences preferences;
    MySpinner spinnerType,spinnerFrequency;
    DBHelper dbHelper;
    ArrayList<DateClass> dateList=null;
    ImageView imgBack;
    RelativeLayout llAddConn;
    TextInputLayout tilName,tilOtherFrequency,tilOtherSpecialist;
    RadioGroup rgCompleted;
    RadioButton rbYes,rbNo;
    String otherFrequency="";
    String otherType="";
    String status="No";

    String[] Type = { "Dermatologist", "Dermatologist – Face", "Gynecologist", "Internist", "Ophthalmologist", "Pulmonologist", "Cardiologist","Mammogram", "Colonoscopy", "Psychiatrist", "CT Scan","Thyroid Scan",
            "Hypothyroid Blood test", "Glucose Test","Other"};

    String[] Frequency = { "Daily", "Weekly", "Monthly", "Quarterly","Semi-Annual", "Annual", "Every 5 Years","Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);
        initUi();
        initListener();
        initComponent();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        AppointmentQuery a=new AppointmentQuery(context,dbHelper);
        DateQuery d=new DateQuery(context,dbHelper);
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
        txtDate.setOnClickListener(this);
    }

    private void initUi() {
        txtName= (TextView) findViewById(R.id.txtName);
        tilName= (TextInputLayout) findViewById(R.id.tilName);
        txtDate= (TextView) findViewById(R.id.txtDate);
        txtOtherFrequency= (TextView) findViewById(R.id.txtOtherFrequency);
        txtOtherSpecialist= (TextView) findViewById(R.id.txtOtherType);
        tilOtherFrequency= (TextInputLayout) findViewById(R.id.tilOtherFrequency);
        tilOtherSpecialist= (TextInputLayout) findViewById(R.id.tilOtherType);
        spinnerType= (MySpinner) findViewById(R.id.spinnerType);
        spinnerFrequency= (MySpinner) findViewById(R.id.spinnerFrequency);
        imgBack= (ImageView) findViewById(R.id.imgBack);
        llAddConn= (RelativeLayout) findViewById(R.id.llAddConn);

        rgCompleted= (RadioGroup) findViewById(R.id.rgCompleted);
        rbYes= (RadioButton) findViewById(R.id.rbYes);
        rbNo= (RadioButton) findViewById(R.id.rbNo);

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setHint("Specialist to see OR Type of Test");

        ArrayAdapter adapter1 = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Frequency);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapter1);
        spinnerFrequency.setHint("Frequency");

        spinnerFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other"))
                {
                    tilOtherFrequency.setVisibility(View.VISIBLE);
                }
                else{
                    tilOtherFrequency.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other"))
                {
                    tilOtherSpecialist.setVisibility(View.VISIBLE);
                }
                else{
                    tilOtherSpecialist.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                return false;
            }
        });

        rgCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId==R.id.rbYes)
                {
                    txtDate.setVisibility(View.VISIBLE);
                    status="Yes";
                }else if (checkedId==R.id.rbNo)
                {
                    txtDate.setVisibility(View.GONE);
                    status="No";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;

            case R.id.txtDate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;

            case R.id.llAddConn:
                int unique=generateRandom();
                String name=txtName.getText().toString().trim();
                String date=txtDate.getText().toString().trim();
                otherType=txtOtherSpecialist.getText().toString();
                otherFrequency=txtOtherFrequency.getText().toString();
                int indexValuex = spinnerType.getSelectedItemPosition();
                String type=Type[indexValuex-1];

                int indexValue = spinnerFrequency.getSelectedItemPosition();
                String frequency=Frequency[indexValue-1];

                Boolean flag = AppointmentQuery.insertAppointmentData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,date,type,frequency,otherType,otherFrequency,dateList,unique);
                if (flag == true) {
                    Toast.makeText(context, "Appointment added succesfully", Toast.LENGTH_SHORT).show();
                   finish();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }



               /* Appoint appoint=new Appoint();
                appoint.setDoctor(name);
                appoint.setDate(date);
                appoint.setFrequency(frequency);
                appoint.setType(type);
                Intent i=new Intent();
                i.putExtra("AppointObject",appoint);

                setResult(100,i);*/
                finish();
                break;
        }
    }

    private int generateRandom() {
        Random r = new Random();
        int randomNumber = r.nextInt(500);

        return randomNumber;
    }
}
