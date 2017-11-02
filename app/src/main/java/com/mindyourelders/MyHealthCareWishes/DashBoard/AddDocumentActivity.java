package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DocumentQuery;
import com.mindyourelders.MyHealthCareWishes.model.Document;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;


public class AddDocumentActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    ImageView imgBack,imgDot,imgDone,imgDoc,imgAdd;
    MySpinner spinnerDoc,spinnerType;
    TextView txtName, txtDate, txtLocation,txtHolderName,txtDist,txtDoc;
    String From;
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

    //final CharSequence[] dialog_items = { "Email", "Bluetooth", "View", "Print", "Fax" };
    final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    String[] DocList = {"Health Care Proxy/Living Will","Health Care Proxy", "Living Will", "Non-Hospital DNR", "HIPAA Form", "Power of Attorney"," Ethical Will","Other Documents"};

    String[] ADList={"Living Will","Health Care Proxy","Living Will/Health Care Proxy","HIPAA Authorization"," Non-Hospital DNR Order"," Ethical Will"};

    String[] LegalList={"Durable Power of Attorney","Bank Durable Power of Attorney","Brokerage Firm Durable Power of Attorney","Trust","Will"};

    String[] InsurancerList={"Insurance Form – Medical","Insurance Form – Dental","Long Term Care"};

    String[] OtherList={"Legal","Financial","Home Health","Insurance","Medical"};

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
                /*for (int j = 0; j < LegalList.length; j++) {
                    if (document.getType().equals(LegalList[j])) {
                        indexs = j;
                    }
                }
                spinnerDoc.setSelection(indexs+1);*/
                  /* switch(document.getCategory())
                {
                    case "Legal":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDoc.setAdapter(adapter);
                        spinnerDoc.setHint("Document Type");
                        for (int j = 0; j < LegalList.length; j++) {
                            if (document.getType().equals(LegalList[j])) {
                                indexs = j;
                            }
                        }
                        spinnerDoc.setSelection(indexs+1);
                        break;
                    case "Financial":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDoc.setAdapter(adapter);
                        spinnerDoc.setHint("Document Type");
                        for (int j = 0; j < LegalList.length; j++) {
                            if (document.getType().equals(LegalList[j])) {
                                indexs = j;
                            }
                        }
                        spinnerDoc.setSelection(indexs+1);
                        break;
                    case "Home Health":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, DocList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDoc.setAdapter(adapter);
                        spinnerDoc.setHint("Document Type");
                        for (int j = 0; j < DocList.length; j++) {
                            if (document.getType().equals(DocList[j])) {
                                indexs = j;
                            }
                        }
                        spinnerDoc.setSelection(indexs+1);
                        break;
                    case "Insurance":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, InsurancerList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDoc.setAdapter(adapter);
                        spinnerDoc.setHint("Document Type");
                        for (int j = 0; j < InsurancerList.length; j++) {
                            if (document.getType().equals(InsurancerList[j])) {
                                indexs = j;
                            }
                        }
                        spinnerDoc.setSelection(indexs+1);
                        break;
                    case "Medical":
                        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, DocList);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDoc.setAdapter(adapter);
                        spinnerDoc.setHint("Document Type");
                        for (int j = 0; j < DocList.length; j++) {
                            if (document.getType().equals(DocList[j])) {
                                indexs = j;
                            }
                        }
                        spinnerDoc.setSelection(indexs+1);
                        break;
                }*/

          }

            imgAdd.setVisibility(View.GONE);
            tilDoc.setVisibility(View.VISIBLE);
            imgDone.setVisibility(View.GONE);
            imgDot.setVisibility(View.VISIBLE);
        }
        else{
            imgDot.setVisibility(View.GONE);
            tilDoc.setVisibility(View.GONE);
            imgDone.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
        }

       /* switch (From)
        {
            case "AD":
               adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Type");
                break;
            case "Other":
              //  adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
                break;
           *//* case "Legal":
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
                break;*//*
        }*/
        /*adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoc.setAdapter(adapter);
        spinnerDoc.setHint("Document Type");*/
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
                       /* uri =  Uri.parse("android.resource://"+getPackageName()+"/raw/"+documentPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);*/
                       CopyReadAssetss(documentPath);
                       /* uri= Uri.fromFile(getFileStreamPath("file:///android_asset/"+documentPath));
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        context.startActivity(intent);*/
                    }
                    else{
                        uri= Uri.parse(documentPath);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
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
                        txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
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
                Intent i=new Intent(context,DocumentSdCardList.class);
                startActivityForResult(i,100);
                break;

            case R.id.imgDot:


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
                                Uri uri=null;
                                if (path.equals("No"))
                                {
                                    CopyReadAssetss(documentPath);
                                }
                                else{
                                    uri= Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/pdf");
                                    context.startActivity(intent);
                                }

                                break;

                        }
                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
                break;

        }
    }

    private void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try
        {
            in = assetManager.open(documentPath);
            out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e)
        {
            Log.e("tag", e.getMessage());
        }
        Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);
      /*  Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(
                Uri.parse("file://" + getFilesDir() +"/"+documentPath),
                "application/pdf");

        startActivity(intent);*/
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
       /* switch (From)
        {
            case "AD":
                type=ADList[indexValue-1];

                break;
            case "Other":
                type=OtherList[indexValue-1];

                // adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
                break;
            case "Legal":
                type=LegalList[indexValue-1];

                // adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, LegalList);
                break;
            case "Home":
                type=DocList[indexValue-1];

                //adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, DocList);
                break;
            case "Insurance":
                type=InsurancerList[indexValue-1];

                //  adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, InsurancerList);
                break;
            case "Medical":
                type=DocList[indexValue-1];

                // adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, DocList);
                break;
        }
*/
        return false;
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

                        if (!documentPath.equals(""))
                        {
                            Uri uri= Uri.parse(documentPath);
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setDataAndType(uri, "application/pdf");
                            context.startActivity(intent);

                        }
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

    private void copyFile(InputStream in, OutputStream out)throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && data!=null)
        {
            name=data.getExtras().getString("Name");
            documentPath=data.getExtras().getString("URI");
            txtName.setText(name);
        }
    }
}