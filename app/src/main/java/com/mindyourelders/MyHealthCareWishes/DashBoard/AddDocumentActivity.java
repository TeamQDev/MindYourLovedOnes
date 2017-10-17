package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;


public class AddDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    ImageView imgBack,imgDot,imgDone;
    MySpinner spinnerDoc;
    TextView txtName, txtDate, txtLocation,txtHolderName;
    String From;
    Preferences preferences;
    ArrayAdapter<String> adapter;
    TextInputLayout tilDate;
    String Goto="";
    String[] DocList = {"Health Care Proxy/Living Will","Health Care Proxy", "Living Will", "Non-Hospital DNR", "HIPAA Form", "Power of Attorney"," Ethical Will","Other Documents"};

    String[] ADList={"Living Will","Health Care Proxy","Living Will/Health Care Proxy","HIPAA Authorization"," Non-Hospital DNR Order"," Ethical Will"};

    String[] LegalList={"Durable Power of Attorney","Bank Durable Power of Attorney","Brokerage Firm Durable Power of Attorney","Trust","Will"};

    String[] InsurancerList={"Insurance Form – Medical","Insurance Form – Dental","Long Term Care"};

    String[] OtherList={"Birth Certificate","Funeral Planning Documents","Social Security Card","Veteran Card"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        initComponent();
        initUi();
        initListener();
    }

    private void initComponent() {
        preferences=new Preferences(context);
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgDot.setOnClickListener(this);
        imgDone.setOnClickListener(this);
    }

    private void initUi() {
        imgDot = (ImageView) findViewById(R.id.imgDot);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        spinnerDoc= (MySpinner) findViewById(R.id.spinnerDoc);

        tilDate= (TextInputLayout) findViewById(R.id.tilDate);
        txtDate= (TextView) findViewById(R.id.txtDate);
        tilDate.setHintEnabled(false);
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilDate.setHintEnabled(true);
                txtDate.setFocusable(true);

                return false;
            }
        });
        From=preferences.getString(PrefConstants.FROM);
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            Goto=i.getExtras().getString("Goto");
        }

        switch (From)
        {
            case "AD":
           adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                break;
            case "Other":
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
                break;
            case "Legal":
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                break;
            case "Home":
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, DocList);
                break;
            case "Insurance":
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, InsurancerList);
                break;
            case "Medical":
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, DocList);
                break;
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoc.setAdapter(adapter);
        spinnerDoc.setHint("Document Type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgDone:
                finish();
                break;
            case R.id.imgDot:
                finish();
                break;
        }
    }
}