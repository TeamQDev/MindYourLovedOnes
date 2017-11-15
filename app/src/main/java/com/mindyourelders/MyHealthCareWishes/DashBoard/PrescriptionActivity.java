package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PrescriptionQuery;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PrescriptionPdf;
import com.mindyourelders.MyHealthCareWishes.utility.Header;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.io.File;
import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    SwipeMenuListView lvPrescription;
   // ListView lvPrescription;
    ImageView imgBack,imgRight;
    ArrayList<Prescription> PrescriptionList;
    RelativeLayout llAddPrescription;
    TextView txtView;
   public static final int REQUEST_PRES=100;
    Preferences preferences;
    DBHelper dbHelper;
    boolean isEdit;
    TextView txtName;

    // final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    final CharSequence[] dialog_items = {"View","Email","Share","Fax" };
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
        txtName=(TextView) findViewById(R.id.txtName);
        txtName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        PrescriptionList=new ArrayList<>();
        imgBack= (ImageView)findViewById(R.id.imgBack);
        llAddPrescription= (RelativeLayout) findViewById(R.id.llAddPrescription);
        lvPrescription= (SwipeMenuListView) findViewById(R.id.lvPrescription);
       // lvPrescription= (ListView)findViewById(R.id.lvPrescription);
        txtView= (TextView) findViewById(R.id.txtView);
        imgRight= (ImageView)findViewById(R.id.imgRight);

        lvPrescription.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(context);
        lvPrescription.setMenuCreator(creator);
        lvPrescription.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Prescription item = PrescriptionList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        break;
                    case 1:
                        // delete
                        deletePrescription(item);
                        break;
                }
                return false;
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Prescription.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        "Prescription");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);

                ArrayList<Prescription> prescriptionList= PrescriptionQuery.fetchAllPrescrptionRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
           //     ArrayList<Dosage> DosageList= DosageQuery.fetchAllDosageRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),);


                new PrescriptionPdf(prescriptionList);

                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                        /*    case 0: // email

                       *//* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*//*
                                break;
                            case 1: // email

                       *//* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*//*

                                break;*/
                            case 0: // view


                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getInsuranceInfo());


                                new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                        + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                        + "/Prescription.pdf",
                                        context, result);

                                System.out.println("\n" + result + "\n");

                                break;

                            case 1: // email
                                File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Prescription.pdf");
                                    emailAttachement(f);

                                break;
                            case 2: // email
                                File fil =new File(Environment.getExternalStorageDirectory()
                                        + "/mye/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                        + "/Prescription.pdf");
                                shareAttachement(fil);

                                break;

                        }


                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
            }
        });

    }

    private void shareAttachement(File fil) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,  "MIND YOUR ELDERS"); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fil));
        emailIntent.setType("application/text");
                startActivity(Intent.createChooser(emailIntent, "Send file..."));
    }

    private void emailAttachement(File f) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "MIND YOUR ELDERS"); // subject
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    private void deletePrescription(Prescription item) {
        boolean flag= PrescriptionQuery.deleteRecord(item.getUnique());
        if(flag==true)
        {
            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setPrescriptionData();
        }
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
                i.putExtra("IsEdit",false);
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
