package com.mindyourlovedones.healthcare.DashBoard;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
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
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.DocumentQuery;
import com.mindyourlovedones.healthcare.model.Document;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.DocumentPdf;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CarePlanActivity extends AppCompatActivity implements View.OnClickListener{
    final static String TARGET_BASE_PATH = "/sdcard/MYLO/images/";
    Context context=this;
    ListView lvDoc;
    ArrayList<Document> documentList;
    ImageView imgBack,imgRight;
    final CharSequence[] dialog_items = {"View", "Email", "Fax" };
    RelativeLayout llAddDoc;
    Preferences preferences;
    DBHelper dbHelper;

    RelativeLayout rlAD,rlHome,rlMedical,rlInsurance,rlOther,rlLegal,rlMedicalRecord;
    TextView txtOne,txtTwo,txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_plan_new);
        initComponent();
        initUI();
        initListener();
       /* StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getDocuments();

        setDocuments();*/
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d=new DocumentQuery(context,dbHelper);
    }

    private void setDocuments() {

    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        rlAD.setOnClickListener(this);
        rlMedicalRecord.setOnClickListener(this);
        rlLegal.setOnClickListener(this);
        rlHome.setOnClickListener(this);
        rlMedical.setOnClickListener(this);
        rlInsurance.setOnClickListener(this);
        rlOther.setOnClickListener(this);
        imgRight.setOnClickListener(this);

        //llAddDoc.setOnClickListener(this);
    }

    private void initUI() {
        txtUserName=(TextView) findViewById(R.id.txtUserName);
        txtUserName.setText(preferences.getString(PrefConstants.CONNECTED_NAME));
        imgBack= (ImageView) findViewById(R.id.imgBack);
        imgRight= (ImageView) findViewById(R.id.imgRight);
        rlAD= (RelativeLayout) findViewById(R.id.rlAD);
        rlMedicalRecord= (RelativeLayout) findViewById(R.id.rlMedicalRecord);
        rlLegal= (RelativeLayout) findViewById(R.id.rlLegal);
        rlHome= (RelativeLayout) findViewById(R.id.rlHome);
        rlMedical= (RelativeLayout) findViewById(R.id.rlMedical);
        rlInsurance= (RelativeLayout) findViewById(R.id.rlInsurance);
        rlOther= (RelativeLayout) findViewById(R.id.rlOther);
        txtOne= (TextView) findViewById(R.id.txtOne);
        txtTwo= (TextView) findViewById(R.id.txtTwo);
        rlOther= (RelativeLayout) findViewById(R.id.rlOther);

      //  String textone = "Click <b><a href='http://www.myhealthcarewishes.com'>here </a> </b> to learn more about them, why they are important, and how to get them completed.";
        String textone = "Click <b><a href=''>here </a> </b> to learn more about them, why they are important, and how to get them completed.";
       /* txtOne.setText(Html.fromHtml(textone));
        txtOne.setClickable(true);
        txtOne.setMovementMethod(LinkMovementMethod.getInstance());*/

        txtOne.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans = (Spannable) txtOne.getText();
        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget)
            {
          Intent i=new Intent(context,ResourceActivity.class);
          startActivity(i);
            }
        };
        spans.setSpan(clickSpan, 6, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtTwo.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spans2 = (Spannable) txtTwo.getText();
        ClickableSpan clickSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View widget)
            {
                Intent i=new Intent(context,ChecklistActivity.class);
                startActivity(i);
            }
        };
        spans2.setSpan(clickSpan2, 14, 23, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


       /* String texttwo = "Download this <b><a href=''> Checklist </a> </b> for help updating and organizing your estate planning documents.";

        // String texttwo = "Download this <b><a href='http://www.myhealthcarewishes.com'> Checklist </a> </b> for help updating and organizing your estate planning documents.";
        txtTwo.setText(Html.fromHtml(texttwo));
        txtTwo.setClickable(true);
        txtTwo.setMovementMethod(LinkMovementMethod.getInstance());*/
    /*   lvDoc= (ListView) findViewById(R.id.lvDoc);
        llAddDoc= (RelativeLayout) findViewById(R.id.llAddDoc);
        DocumentAdapter documentAdapter=new DocumentAdapter(context,documentList);
        lvDoc.setAdapter(documentAdapter);*/
    }

    private void getDocuments() {
      /*  documentList=new ArrayList<>();

        Document P1=new Document();
        P1.setName("HealthCare Proxy");
        P1.setImage(R.drawable.pdf);
        P1.setDesc("This is Helath care proxy Document");
        P1.setDocument("healthcare_proxy_nys.pdf");

        Document P2=new Document();
        P2.setName("Living Will");
        P2.setImage(R.drawable.pdf);
        P2.setDesc("This is Living will form");
        P2.setDocument("HCD_forms.pdf");

        Document P3=new Document();
        P3.setName("HIPAA Form");
        P3.setImage(R.drawable.pdf);
        P3.setDesc("This is Hippa form");
        P3.setDocument("ABA Consumer Tool Kit.pdf");

        Document P4=new Document();
        P4.setName("DNR");
        P4.setImage(R.drawable.pdf);
        P4.setDesc("This is dnr form");
        P4.setDocument("dnr.pdf");

        documentList.add(P1);
        documentList.add(P2);
        documentList.add(P3);
        documentList.add(P4 );*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;



            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Documents.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                copyFile("ic_launcher.png");
                Header.addImage(TARGET_BASE_PATH+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Documents");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);

                Header.addChank("MindYour-LovedOnes.com");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Paragraph p = new Paragraph(" ");
                LineSeparator line = new LineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                try {
                    Header.document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Header.addEmptyLine(1);

                  /* new Header().createPdfHeader(file.getAbsolutePath(),
                            "Specialty");*/

              /*  Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                ArrayList<Document> AdList= DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),"AD");
                ArrayList<Document> OtherList= DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),"Other");
                ArrayList<Document> RecordList= DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),"Record");
                new DocumentPdf(AdList);
                new DocumentPdf(OtherList,1);
                new DocumentPdf(RecordList,"Record");

                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path= Environment.getExternalStorageDirectory()
                                + "/mylopdf/"
                                + "/Documents.pdf";
                        switch (itemPos) {
                            case 0: // view
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getAdvanceDocuments());
                                result.append(new MessageString().getOtherDocuments());
                                result.append(new MessageString().getRecordDocuments());


                                new PDFDocumentProcess(path,
                                        context, result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,context,"Documents");
                                break;
                            case 2://fax
                                new FaxCustomDialog(context, path).show();
                                break;
                        }
                    }

                });
                builder.create().show();
                break;


            case R.id.rlOther:
                Intent i=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"Other");
                startActivity(i);
                break;

            case R.id.rlMedical:
                Intent i1=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"Medical");
                startActivity(i1);
                break;

            case R.id.rlInsurance:
                Intent i2=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"Insurance");
                startActivity(i2);
                break;

            case R.id.rlHome:
                Intent i3=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"Home");
                startActivity(i3);
                break;

            case R.id.rlAD:
                Intent i4=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"AD");
                startActivity(i4);
                break;
            case R.id.rlMedicalRecord:
                Intent intent=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"Record");
                startActivity(intent);
                break;

            case R.id.rlLegal:
                Intent i5=new Intent(context,CarePlanListActivity.class);
                preferences.putString(PrefConstants.FROM,"Legal");
                startActivity(i5);
                break;

          /*  case R.id.llAddDoc:
                Intent i=new Intent(context,AddDocumentActivity.class);
                startActivity(i);
                break;*/
        }
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

    public void CopyReadAssets(String fileName) {
        File dir = new File(Environment.getExternalStorageDirectory()
                + "/mhcw/");
        dir.mkdirs();

        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(Environment.getExternalStorageDirectory()
                + "/mhcw/" + fileName);
        try {
            in = assetManager.open(fileName);
            out = new BufferedOutputStream(new FileOutputStream(file));

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    public void onPDFClicked(String fileName) {
        final File item = new File(fileName);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("");

        builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int itemPos) {

                switch (itemPos) {
                    case 0: // email

                       /* emailAttachement(item);

                        ShearedValues.activityID = getApplicationContext();*/
                        break;
                    case 1: // email

                       /* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*/

                        break;
                    case 2: // view

                        String path_of_file = item.getAbsolutePath();
                        viewFile(context, path_of_file);

                       // ShearedValues.activityID = getApplicationContext();
                        break;

                }
            }
        });

        builder.create().show();
    }

    private void viewFile(Context context, String filename) {
        File targetFile = new File(filename);
        Uri targetUri = Uri.fromFile(targetFile);

                Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileprovider", targetFile);
            intent.setDataAndType(contentUri, "application/pdf");
        } else {
            intent.setDataAndType(Uri.fromFile(targetFile), "application/pdf");
        }

        if (targetFile.getName().endsWith(".pdf")) {
            intent.setPackage("com.adobe.reader");
            intent.setDataAndType(targetUri, "application/pdf");

            try {
                startActivity(intent);

            } catch (ActivityNotFoundException e) {
                // No application to view, ask to download one

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("No Application Found");
                builder.setMessage("Download Office Tool from Google Play ?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent marketIntent = new Intent(
                                        Intent.ACTION_VIEW);
                                marketIntent.setData(Uri
                                        .parse("market://details?id=com.adobe.reader"));
                                startActivity(marketIntent);
                            }
                        });
                builder.setNegativeButton("No", null);
                builder.create().show();
            }

        }
    }

    public void CopyAssets() {
        File fileBrochure = new File(Environment.getExternalStorageDirectory() + "/mhcw/MHCW_APP_Wallet_Card.pdf");
        if (!fileBrochure.exists())
        {
            CopyAssetsbrochure();
        }

        /** PDF reader code */
        File file = new File(Environment.getExternalStorageDirectory() +"/mhcw/MHCW_APP_Wallet_Card.pdf");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try
        {
            getApplicationContext().startActivity(intent);
        }
        catch (ActivityNotFoundException e)
        {
           // Toast.makeText(SecondActivity.this, "NO Pdf Viewer", Toast.LENGTH_SHORT).show();
        }
    }

    //method to write the PDFs file to sd card
    private void CopyAssetsbrochure() {
        AssetManager assetManager = getAssets();
        String[] files = null;
        try
        {
            files = assetManager.list("");
        }
        catch (IOException e)
        {
            Log.e("tag", e.getMessage());
        }
        for(int i=0; i<files.length; i++)
        {
            String fStr = files[i];
            if(fStr.equalsIgnoreCase("abc.pdf"))
            {
                InputStream in = null;
                OutputStream out = null;
                try
                {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + files[i]);
                    copyFile(in, out);
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                }
                catch(Exception e)
                {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }
}
