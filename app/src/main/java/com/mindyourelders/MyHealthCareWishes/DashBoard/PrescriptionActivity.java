package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PrescriptionQuery;
import com.mindyourelders.MyHealthCareWishes.model.Prescription;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.PrescriptionPdf;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TARGET_BASE_PATH =  "/sdcard/MYLO/images/";
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
    final CharSequence[] dialog_items = {"View","Email","Fax" };
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
        SwipeMenuCreator creator=s.createSingleMenu(context);
        lvPrescription.setMenuCreator(creator);
        lvPrescription.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Prescription item = PrescriptionList.get(position);
                switch (index) {

                    case 0:
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
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Prescription.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                copyFile("ic_launcher.png");
                Header.addImage(TARGET_BASE_PATH+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Prescription");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Prescription");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

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
                                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                        + "/Prescription.pdf",
                                        context, result);

                                System.out.println("\n" + result + "\n");

                                break;

                            case 1: // email
                                File f =new File(Environment.getExternalStorageDirectory()
                                            + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Prescription.pdf");
                                    emailAttachement(f,"Prescription");

                                break;
                            case 2: // Fax
                                serverAttachement(Environment.getExternalStorageDirectory()
                                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                        + "/Prescription.pdf");
                                break;

                        }


                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
            }
        });

    }
    private void serverAttachement(String path) {
        System.out.println("Path of the file    "+path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(PrescriptionActivity.this, path).show();;
    }
    private void shareAttachement(File fil) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,  "MIND YOUR ELDERS"); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fil));
        emailIntent.setType("application/text");
                startActivity(Intent.createChooser(emailIntent, "Send file..."));
    }

    private void emailAttachement(File f, String s) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        String name= preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                name+"-"+s); // subject


        String body="Hi, \n" +
                "\n" +
                "\n" +name+
                " shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thanks,\n" +
                "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body
        Uri uri=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", f);
        } else {
            uri = Uri.fromFile(f);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

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

    private void copyFile(String filename) {
        AssetManager assetManager = this.getAssets();

        InputStream in = null;
        OutputStream out = null;
        String newFileName = null;

        try {
            File dir = new File(TARGET_BASE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Log.i("tag", "copyFile() " + filename);
            in = assetManager.open(filename);
            newFileName = TARGET_BASE_PATH + filename;
            out = new FileOutputStream(newFileName);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", "Exception in copyFile() of " + newFileName);
            Log.e("tag", "Exception in copyFile() " + e.toString());
        }

    }
}
