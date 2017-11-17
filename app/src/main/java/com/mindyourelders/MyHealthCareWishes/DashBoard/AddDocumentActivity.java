
package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DocumentQuery;
import com.mindyourelders.MyHealthCareWishes.model.Document;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RQUESTCODE = 400;
    Context context = this;
    ImageView imgBack,imgDot,imgDone,imgDoc,imgAdd;
    MySpinner spinnerDoc,spinnerType;
    TextView txtName, txtDate, txtLocation,txtHolderName,txtDist,txtDoc;
    String From;
    private static final int RESULTCODE = 200;
    Preferences preferences;
    ArrayAdapter<String> adapter, adapter1;
    TextInputLayout tilDate,tilDoc;
    Document document;
    DBHelper dbHelper;
    String name="";
    String type="";
    String documentPath="";
    String location="";
    String holder="";
    int photo;
    String date="";
    String category="";
    String Goto="";
    String path="";
    final CharSequence[] alert_items = { "SD Card", "Dropbox" };
    //final CharSequence[] dialog_items = { "Email", "Bluetooth", "View", "Print", "Fax" };
    final CharSequence[] dialog_items = {"View", "Email", "Fax" };
    String[] DocList = {" Ethical Will","HIPAA Form","Health Care Proxy","Health Care Proxy/Living Will","Living Will","Non-Hospital DNR","Power of Attorney","Other Documents"};

    String[] ADList={"Ethical Will","Non-Hospital DNR Order","HIPAA Authorization","Health Care Proxy","Living Will","Living Will/Health Care Proxy"};

    String[] LegalList={"Bank Durable Power of Attorney","Brokerage Firm Durable Power of Attorney","Trust","Will","Durable Power of Attorney"};

    String[] InsurancerList={"Insurance Form – Dental","Insurance Form – Medical","Long Term Care"};

    String[] OtherList={"Financial","Home Health","Insurance","Legal","Medical"};

    String[] Others={"Other"};

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
        dbHelper=new DBHelper(context);
        DocumentQuery d=new DocumentQuery(context,dbHelper);
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgDot.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        imgDoc.setOnClickListener(this);
        txtDate.setOnClickListener(this);
    }

    private void initUi() {
        imgDot = (ImageView) findViewById(R.id.imgDot);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgDoc = (ImageView) findViewById(R.id.imgDoc);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        spinnerDoc= (MySpinner) findViewById(R.id.spinnerDoc);
        spinnerType= (MySpinner) findViewById(R.id.spinnerType);
        txtName= (TextView) findViewById(R.id.txtName);
        txtLocation= (TextView) findViewById(R.id.txtLocation);
        txtHolderName= (TextView) findViewById(R.id.txtHolderName);
        txtDist= (TextView) findViewById(R.id.txtDist);
        txtDoc= (TextView) findViewById(R.id.txtDoc);
        tilDoc= (TextInputLayout) findViewById(R.id.tilDoc);

        txtDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilDoc.setVisibility(View.GONE);
                spinnerDoc.setVisibility(View.VISIBLE);
                spinnerDoc.setHint("Document Type");
                spinnerDoc.performClick();
            }
        });

        tilDate= (TextInputLayout) findViewById(R.id.tilDate);
        txtDate= (TextView) findViewById(R.id.txtDate);
        tilDate.setHintEnabled(false);
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilDate.setHintEnabled(true);
                return false;
            }
        });

        adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setHint("Document Category");

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, Others);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoc.setAdapter(adapter);
        spinnerDoc.setHint("Document Type");


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item= parent.getItemAtPosition(position).toString();
                switch(item)
                {
                    case "Legal":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                        break;
                    case "Financial":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                        break;
                    case "Home Health":
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        From=preferences.getString(PrefConstants.FROM);
        if (From.equals("AD"))
        {
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDoc.setAdapter(adapter);
            spinnerDoc.setHint("Document Type");
            tilDoc.setVisibility(View.GONE);
        }
        switch (From) {
            case "AD":
                spinnerType.setVisibility(View.GONE);
                break;

            case "Other":
                spinnerType.setVisibility(View.VISIBLE);
                break;
        }
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            Goto=i.getExtras().getString("GoTo");
            path=i.getExtras().getString("Path");
        }

        if (Goto.equals("View"))
        {
            imgDot.setVisibility(View.VISIBLE);
            imgDone.setVisibility(View.GONE);

            document= (Document) i.getExtras().getSerializable("DocumentObject");
            txtDate.setText(document.getDate());
            txtHolderName.setText(document.getHolder());
            txtLocation.setText(document.getLocation());
            txtName.setText(document.getName());
            documentPath=document.getDocument();
            imgDoc.setImageResource(document.getImage());


            int index= 0;
            if (From.equals("AD"))
            {
                spinnerDoc.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Type");
                tilDoc.setVisibility(View.GONE);
                txtDoc.setVisibility(View.GONE);
            }
            else{
                adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapter1);
                spinnerType.setHint("Document Category");

                for (int j = 0; j < OtherList.length; j++) {
                    if (document.getCategory().equals(OtherList[j])) {
                        index = j;
                    }
                }
                spinnerType.setSelection(index+1);
            }

            if (document.getFrom().equals("AD"))
            {
                int indexs = 0;
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Type");
                tilDoc.setVisibility(View.GONE);
                txtDoc.setVisibility(View.GONE);
                for (int j = 0; j < ADList.length; j++) {
                    if (document.getType().equals(ADList[j])) {
                        indexs = j;
                    }
                }
                spinnerDoc.setSelection(indexs+1);
               String sel=spinnerDoc.getSelectedItem().toString();

           }else{
                int indexs = 0;

                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("");
                spinnerDoc.setSelection(indexs+1);
                spinnerDoc.setVisibility(View.INVISIBLE);
                txtDoc.setText(spinnerDoc.getSelectedItem().toString());
                txtDoc.setFocusable(false);

          }

            imgAdd.setVisibility(View.GONE);
            tilDoc.setVisibility(View.VISIBLE);
            imgDone.setVisibility(View.GONE);
            imgDot.setVisibility(View.VISIBLE);
            imgDoc.setClickable(true);
        }
        else{
            imgDot.setVisibility(View.GONE);
            tilDoc.setVisibility(View.GONE);
            imgDone.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
            imgDoc.setClickable(false);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgDoc:
                if (!documentPath.equals(""))
                {
                    Uri uri=null;
                    if (path.equals("No"))
                    {

                       CopyReadAssetss(documentPath);

                    }
                    else{
                       /* uri= Uri.parse(documentPath);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        context.startActivity(intent);*/
                        Uri uris= Uri.parse(documentPath);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            //  uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", targetFile);
                        } else {
                            //  uri = Uri.fromFile(targetFile);
                        }
                        intent.setDataAndType(uris, "application/pdf");
                        context.startActivity(intent);
                    }



                }
                break;

            case R.id.txtDate:
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
                       // txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;

            case R.id.imgDone:
                if (validate()) {
                    Boolean flag = DocumentQuery.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name,category,date,location,holder,photo,documentPath,type,From);
                    if (flag == true) {
                        Toast.makeText(context, "You have added proxy contact successfully", Toast.LENGTH_SHORT).show();
                       finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.imgAdd:

                AlertDialog.Builder builder = new AlertDialog.Builder(AddDocumentActivity.this);

                builder.setTitle("");
                builder.setItems(alert_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                            case 0:
                                Intent i=new Intent(context,DocumentSdCardList.class);
                                startActivityForResult(i,RESULTCODE);
                                break;
                            case 1:
                                Intent intent=new Intent(context,DropboxLoginActivity.class);
                                intent.putExtra("FROM","Document");
                                startActivityForResult(intent,RQUESTCODE);
                                break;

                        }

                    }

                });

                builder.create().show();


                break;

            case R.id.imgDot:

                AlertDialog.Builder builders = new AlertDialog.Builder(context);

                builders.setTitle("");

                builders.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {

                        switch (itemPos) {
                            case 0: // email
                                Uri uri=null;
                                if (path.equals("No"))
                                {
                                    CopyReadAssetss(documentPath);
                                }
                                else{
                                   /* uri= Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/pdf");
                                    context.startActivity(intent);*/
                                    Uri uris= Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        //  uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", targetFile);
                                    } else {
                                        //  uri = Uri.fromFile(targetFile);
                                    }
                                    intent.setDataAndType(uris, "application/pdf");
                                    context.startActivity(intent);
                                }

                                break;
                            case 1: // email
                                if (path.equals("No"))
                                {
                                    File file=new File(getExternalFilesDir(null),documentPath);
                                    Uri urifile=null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        urifile = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", file);
                                    } else {
                                        urifile = Uri.fromFile(file);
                                    }

                                    emailAttachement(urifile);
                                }
                                else {
                                    Uri uris = Uri.parse(documentPath);
                                    emailAttachement(uris);
                                }
                       /* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*/

                                break;
                            case 2: // Fax
                                if (path.equals("No"))
                                {
                                    File file=new File(getExternalFilesDir(null),documentPath);
                                    Uri urifile=null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        urifile = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", file);
                                    } else {
                                        urifile = Uri.fromFile(file);
                                    }
                                    String path=urifile.getPath();
                                    serverAttachement(urifile);
                                   // emailAttachement(urifile);
                                }
                                else {

                                    Uri uris = Uri.parse(documentPath);
                                    String path=uris.getPath();
                                    serverAttachement(uris);
                                 //  emailAttachement(uris);
                                }


                                break;

                        }
                    }
                });

                builders.create().show();
                // ((CarePlanActivity)context).CopyAssets();
                break;

        }
    }
    private void serverAttachement(Uri path) {
        String urlPath=path.getPath();
        System.out.println("Path of the file    "+path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(AddDocumentActivity.this, urlPath).show();;
    }

    private void emailAttachement(Uri f) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "MIND YOUR ELDERS"); // subject
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""); // Body
         // Uri uri=null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           // uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", f);
        } else {
             // uri = Uri.fromFile(f);
        }

        emailIntent.putExtra(Intent.EXTRA_STREAM, f);

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try
        {
            in = assetManager.open(documentPath);
            outFile=new File(getExternalFilesDir(null),documentPath);
            out=new FileOutputStream(outFile);

            copyFiles(in,out);
            in.close();
            in=null;
            out.flush();
            out.close();
            out=null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }
        Uri uri=null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourelders.MyHealthCareWishes.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }


    }

    private boolean validate() {
        int indexValues = spinnerType.getSelectedItemPosition();
        int indexValue = spinnerDoc.getSelectedItemPosition();
        if (From.equals("AD"))
        {
            category="AD";
            if (indexValue!=0) {
                type = ADList[indexValue - 1];
            }
        }else{
            if (indexValues!=0) {
                category = OtherList[indexValues - 1];
            }
            switch(category)
            {
                case "Legal":
                    if (indexValue!=0) {
                        type = LegalList[indexValue - 1];
                    }
                    break;
                case "Financial":
                    if (indexValue!=0) {
                        type = LegalList[indexValue - 1];
                    }
                    break;
                case "Home Health":
                    if (indexValue!=0) {
                        type = DocList[indexValue - 1];
                    }
                    break;
                case "Insurance":
                    if (indexValue!=0) {
                        type = InsurancerList[indexValue - 1];
                    }
                    break;
                case "Medical":
                    if (indexValue!=0) {
                        type = DocList[indexValue - 1];
                    }
                    break;
            }
        }


        name=txtName.getText().toString();
        location=txtLocation.getText().toString();
        holder=txtHolderName.getText().toString();
        date=txtDate.getText().toString();
        photo=R.drawable.pdf;
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
           Toast.makeText(context,"Enter Document Name",Toast.LENGTH_SHORT).show();
        }else{
            return true;
        }

        return false;
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RESULTCODE && data!=null)
        {
            name=data.getExtras().getString("Name");
            documentPath=data.getExtras().getString("URI");
            txtName.setText(name);
            imgDoc.setClickable(false);
        }
        else  if (requestCode==RQUESTCODE && data!=null)
        {
            name=data.getExtras().getString("Name");
            documentPath=data.getExtras().getString("URI");
            txtName.setText(name);
            imgDoc.setClickable(false);
        }

    }

 /*   public void getData(Uri uri, String ext) {
        TextView txtName= (TextView) findViewById(R.id.txtName);
        name=ext;
        documentPath=uri.toString();
        txtName.setText(ext);
        imgDoc.setClickable(false);
        *//*PackageManager manager = getPackageManager();
        List<ResolveInfo> resolveInfo = manager.queryIntentActivities(intent, 0);
        if (resolveInfo.size() > 0) {
            startActivity(intent);
        }*//*
    }
*/

}