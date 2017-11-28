package com.mindyourelders.MyHealthCareWishes.DashBoard;

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
import android.os.StrictMode;
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
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DocumentQuery;
import com.mindyourelders.MyHealthCareWishes.model.Document;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.drawable.pdf;


public class CarePlanListActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    SwipeMenuListView lvDoc;
    ArrayList<Document> documentList;
    ArrayList<Document> documentListOld;
    ImageView imgBack;
    TextView txtTitle;
    String From;
    final CharSequence[] dialog_items = { "Email", "Bluetooth", "View", "Print", "Fax" };
    RelativeLayout llAddDoc;
    Preferences preferences;

DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_plan);
        initComponent();
        initUI();
        initListener();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getDocuments();
        setDocuments();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        DocumentQuery d=new DocumentQuery(context,dbHelper);
    }

    private void setDocuments() {
        if (documentListOld.size()!=0) {
            DocumentAdapter documentAdapter = new DocumentAdapter(context, documentListOld);
            lvDoc.setAdapter(documentAdapter);
        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        llAddDoc.setOnClickListener(this);
    }

    private void initUI() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        lvDoc= (SwipeMenuListView) findViewById(R.id.lvDoc);
        lvDoc.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createSingleMenu(context);
        lvDoc.setMenuCreator(creator);
        lvDoc.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Document item = documentListOld.get(position);
                switch (index) {
                    case 0:
                        // delete
                        deleteDocument(item);
                        break;
                }
                return false;
            }
        });
        llAddDoc= (RelativeLayout) findViewById(R.id.llAddDoc);
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtTitle.setAllCaps(true);

        From=preferences.getString(PrefConstants.FROM);
        switch (From)
        {
            case "AD":
                txtTitle.setText("Advance Directives");
                break;
            case "Other":
                txtTitle.setText("Other Documents");
                break;
            case "Legal":
                txtTitle.setText("Legal and Financial Documents");
                break;
            case "Home":
                txtTitle.setText("Home And Health Documents");
                break;
            case "Insurance":
                txtTitle.setText("Insurance Documents");
                break;
            case "Medical":
                txtTitle.setText("Medical Documents");
                break;
        }
    }

    private void deleteDocument(Document item) {
        boolean flag = DocumentQuery.deleteRecord(item.getId());
        if (flag == true) {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
            getData();
           setDocuments();
        }
    }

   /* private void setDocumentData() {
        if (documentList.size() != 0) {
            lvDoc.setVisibility(View.VISIBLE);
            //txtView.setVisibility(View.GONE);
        } else {
           // txtView.setVisibility(View.VISIBLE);
            lvDoc.setVisibility(View.GONE);
        }
        DocumentAdapter adapter = new DocumentAdapter(context, documentList);
        lvDoc.setAdapter(adapter);
    }
*/
    private void getData() {
        documentList = DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    }

    private void getDocuments() {
        documentListOld=new ArrayList<>();
         switch (From) {
            case "AD":
                Document P1d = new Document();
                P1d.setName("HealthCare Proxy");
                P1d.setType("Health Care Proxy");
                P1d.setDate("10-Nov-2017");
                P1d.setLocation("Locker");
                P1d.setImage(pdf);
                P1d.setHolder("Husband");
                P1d.setDocument("healthcare_proxy_nys.pdf");

                Document P2d = new Document();
                P2d.setName("Living Will");
                P2d.setImage(pdf);
                P2d.setType("Living Will");
                P2d.setDate("10-Nov-2017");
                P2d.setLocation("Locker");
                P2d.setImage(pdf);
                P2d.setHolder("Husband");
                P2d.setDocument("HCD_forms.pdf");

                Document P3d = new Document();
                P3d.setName("HIPAA Form");
                P3d.setImage(pdf);
                P3d.setType("HIPAA Form");
                P3d.setDate("10-Nov-2017");
                P3d.setLocation("Locker");
                P3d.setImage(pdf);
                P3d.setHolder("Husband");
                P3d.setDocument("hipaa.pdf");

                Document P4d = new Document();
                P4d.setName("DNR");
                P4d.setImage(pdf);
                P4d.setType("Non-Hospital DNR");
                P4d.setDate("10-Nov-2017");
                P4d.setLocation("Locker");
                P4d.setImage(pdf);
                P4d.setHolder("Husband");
                P4d.setDocument("dnr.pdf");

                documentListOld.add(P1d);
                documentListOld.add(P2d);
                documentListOld.add(P3d);
                documentListOld.add(P4d);
                break;
            case "Other":
                //   txtTitle.setText("Other");
                Document P1=new Document();
                P1.setName("Aging Care - Care Plan Guide");
                P1.setImage(pdf);
                P1.setType("Aging Care - Care Plan Guide");
                P1.setDate("10/10/2017");
                P1.setLocation("Locker");
                P1.setImage(pdf);
                P1.setHolder("Husband");
                P1.setDocument("AgingCare_CarePlanGuide.pdf");

                Document P4i=new Document();
                P4i.setName("Medical Claim Form");
                P4i.setImage(pdf);
                P4i.setType("Medical Claim Form");
                P4i.setDate("10/10/2017");
                P4i.setLocation("Locker");
                P4i.setImage(pdf);
                P4i.setHolder("Husband");
                P4i.setDocument("medical_claim_form.pdf");

                Document P5i=new Document();
                P5i.setName("Dental Claim Form");
                P5i.setImage(pdf);
                P5i.setType("Dental Claim Form");
                P5i.setDate("10/10/2017");
                P5i.setLocation("Locker");
                P5i.setImage(pdf);
                P5i.setHolder("Husband");
                P5i.setDocument("dental_claim_form.pdf");

                Document P3=new Document();
                P3.setName("Clinical Flow Sheet");
                P3.setImage(pdf);
                P3.setType("Clinical Flow Sheet");
                P3.setDate("10/10/2017");
                P3.setLocation("Locker");
                P3.setImage(pdf);
                P3.setHolder("Husband");
                P3.setDocument("dm_chf.pdf");

                documentListOld.add(P4i);
                documentListOld.add(P5i);
                documentListOld.add(P1);
                documentListOld.add(P3);
                break;
        }
        documentList= DocumentQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        documentListOld.addAll(documentList);
       /* switch (From)
        {
            case "AD":
                Document P1d=new Document();
                P1d.setName("HealthCare Proxy");
                P1d.setDate("10/10/2017");
                P1d.setType("Health Care Proxy");
                P1d.setImage(R.drawable.pdf);
                P1d.setDesc("This is Helath care proxy Document");
                P1d.setDocument("healthcare_proxy_nys.pdf");

                Document P2d=new Document();
                P2d.setName("Living Will");
                P2d.setImage(R.drawable.pdf);
                P2d.setDesc("This is Living will form");
                P2d.setDocument("HCD_forms.pdf");

                Document P3d=new Document();
                P3d.setName("HIPAA Form");
                P3d.setImage(R.drawable.pdf);
                P3d.setDesc("This is Hippa form");
                P3d.setDocument("ABA Consumer Tool Kit.pdf");

                Document P4d=new Document();
                P4d.setName("DNR");
                P4d.setImage(R.drawable.pdf);
                P4d.setDesc("This is dnr form");
                P4d.setDocument("dnr.pdf");

                documentList.add(P1d);
                documentList.add(P2d);
                documentList.add(P3d);
                documentList.add(P4d);
                break;
            case "Other":
             //   txtTitle.setText("Other");
                break;
            case "Legal":
             //   txtTitle.setText("Legal and Financial Documents");
                break;
            case "Home":
                Document P1=new Document();
                P1.setName("Aging Care - Care Plan Guide");
                P1.setImage(R.drawable.pdf);
                P1.setDesc("This is Helath care proxy Document");
                P1.setDocument("AgingCare_CarePlanGuide.pdf");

                documentList.add(P1);
                break;
            case "Insurance":
                Document P4i=new Document();
                P4i.setName("Medical Claim Form");
                P4i.setImage(R.drawable.pdf);
                P4i.setDesc("This is Hippa form");
                P4i.setDocument("medical_claim_form.pdf");

                Document P5i=new Document();
                P5i.setName("Dental Claim Form");
                P5i.setImage(R.drawable.pdf);
                P5i.setDesc("This is Hippa form");
                P5i.setDocument("dental_claim_form.pdf");

                documentList.add(P4i);
                documentList.add(P5i);
                break;
            case "Medical":
                Document P=new Document();
                P.setName("Screen geri");
                P.setImage(R.drawable.pdf);
                P.setDesc("This is Helath care proxy Document");
                P.setDocument("screen_geri.pdf");

                Document P2=new Document();
                P2.setName("Summery Form");
                P2.setImage(R.drawable.word);
                P2.setDesc("This is Living will form");
                P2.setDocument("summary_form.pdf");

                Document P3=new Document();
                P3.setName("Clinical Flow Sheet");
                P3.setImage(R.drawable.pdf);
                P3.setDesc("This is Hippa form");
                P3.setDocument("dm_chf.pdf");

                Document P4=new Document();
                P4.setName("MOLST pdf");
                P4.setImage(R.drawable.pdf);
                P4.setDesc("This is Hippa form");
                P4.setDocument("WebPage.pdf");

                Document P5=new Document();
                P5.setName("POLST pdf");
                P5.setImage(R.drawable.pdf);
                P5.setDesc("This is Hippa form");
                P5.setDocument("POLST & Advance Directives.pdf");

                documentList.add(P);
                documentList.add(P2);
                documentList.add(P3);
                documentList.add(P4);
                documentList.add(P5);
                break;
        }*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;

            case R.id.llAddDoc:
                Intent i=new Intent(context,AddDocumentActivity.class);
                i.putExtra("GoTo","Add");
                startActivity(i);
                break;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileprovider", targetFile);
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        getDocuments();
        setDocuments();
    }
}
