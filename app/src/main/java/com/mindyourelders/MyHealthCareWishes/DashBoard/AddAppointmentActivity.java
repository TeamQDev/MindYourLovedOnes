package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.model.Appoint;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.Calendar;


public class AddAppointmentActivity extends AppCompatActivity implements View.OnClickListener{
    Context context=this;
    TextView txtName,txtDate;
    Preferences preferences;
    MySpinner spinnerType,spinnerFrequency;
    DBHelper dbHelper;
    ImageView imgBack;
    RelativeLayout llAddConn;
    TextInputLayout tilName;

    String[] Type = { "Dermatologist", "Dermatologist – Face", "Gynecologist", "Internist", "Ophthalmologist", "Pulmonologist", "Cardiologist","Mammogram", "Colonoscopy", "Psychiatrist", "CT Scan","Thyroid Scan",
            "Hypothyroid Blood test", "Glucose Test"};

    String[] Frequency = { "Daily", "Weekly", "Monthly", "Quarterly","Semi-Annual", "Annual", "Every 5 Years"};

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
        spinnerType= (MySpinner) findViewById(R.id.spinnerType);
        spinnerFrequency= (MySpinner) findViewById(R.id.spinnerFrequency);
        imgBack= (ImageView) findViewById(R.id.imgBack);
        llAddConn= (RelativeLayout) findViewById(R.id.llAddConn);

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
        spinnerType.setHint("Type");

        ArrayAdapter adapter1 = new ArrayAdapter(context, android.R.layout.simple_spinner_item, Frequency);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapter1);
        spinnerFrequency.setHint("Frequency");
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                return false;
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
                String name=txtName.getText().toString().trim();
                String date=txtDate.getText().toString().trim();
                int indexValuex = spinnerType.getSelectedItemPosition();
                String type=Type[indexValuex-1];

                int indexValue = spinnerFrequency.getSelectedItemPosition();
                String frequency=Frequency[indexValue-1];


                Appoint appoint=new Appoint();
                appoint.setDoctor(name);
                appoint.setDate(date);
                appoint.setFrequency(frequency);
                appoint.setType(type);
                Intent i=new Intent();
                i.putExtra("AppointObject",appoint);

                setResult(100,i);
                finish();
                break;
        }
    }
}
