package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PrescriptionQuery;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    ListView lvPrescription;
    ImageView imgBack;
    ArrayList<Prescription> PrescriptionList;
    RelativeLayout llAddPrescription;
    TextView txtView;
   public static final int REQUEST_PRES=100;
    Preferences preferences;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        initComponent();
        getData();
        initUI();
        initListener();
        setPrescriptionData();
    }

    private void getData() {
        PrescriptionList=PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        PrescriptionQuery p=new PrescriptionQuery(context,dbHelper);
    }

    private void initListener() {
        llAddPrescription.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        PrescriptionList=new ArrayList<>();
        imgBack= (ImageView)findViewById(R.id.imgBack);
        llAddPrescription= (RelativeLayout) findViewById(R.id.llAddPrescription);
        lvPrescription= (ListView)findViewById(R.id.lvPrescription);
        txtView= (TextView) findViewById(R.id.txtView);
    }

    private void setPrescriptionData() {
      if (PrescriptionList.size()!=0)
        {
            lvPrescription.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.GONE);
        }else{
            txtView.setVisibility(View.VISIBLE);
            lvPrescription.setVisibility(View.GONE);
        }
        PrescriptionAdapter adapter=new PrescriptionAdapter(context,PrescriptionList);
        lvPrescription.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.llAddPrescription:
               // preferences.putString(PrefConstants.SOURCE,"Prescription");
                Intent i=new Intent(context,AddPrescriptionActivity.class);
                startActivityForResult(i,REQUEST_PRES);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode==REQUEST_PRES&& data!=null) {

      /*  Prescription p=new Prescription();
        p.setDates(data.getExtras().getString("Date"));
        p.setDoctor(data.getExtras().getString("Name"));
        p.setDosageList((ArrayList<Dosage>)data.getExtras().getSerializable("Dosage"));
        p.setPrescriptionImageList((ArrayList<PrescribeImage>) data.getExtras().getSerializable("Image"));*/

      //  PrescriptionList.add((Prescription) data.getSerializableExtra("PrObj"));
      //  setPrescriptionData();
    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        setPrescriptionData();
    }
}
