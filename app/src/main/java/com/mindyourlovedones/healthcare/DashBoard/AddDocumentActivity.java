
package com.mindyourlovedones.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.HomeActivity.SplashNewActivity;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.customview.MySpinner;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.DocumentQuery;
import com.mindyourlovedones.healthcare.model.Document;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

import java.io.File;
import java.io.FileInputStream;
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
    ImageView imgBack, imgDot, imgDone, imgDoc, imgAdd;
    MySpinner spinnerDoc, spinnerType;
    TextView txtTitle,txtOtherDocType, txtName, txtAdd, txtHosp,txtLocator, txtDate, txtLocation, txtHolderName, txtDist, txtOther, txtPName, txtFName, txtDocTYpe;
    String From;
    private static final int RESULTCODE = 200;
    Preferences preferences;
    ArrayAdapter<String> adapter, adapter1;
    TextInputLayout tilDate, tilOther,tilOtherDocType, tilDocType, tilHosp, tilName, tilPName;
    RelativeLayout rlDocType;
    Document document;
    DBHelper dbHelper;
    String name = "";
    String type = "";
    String docType = "",otherDocType="", person = "", principle = "";
    String otherCategory = "";
    String Hosp = "",locator="";
    String documentPath = "";
    String originPath = "";
    String location = "";
    String holder = "";
    int photo;
    String date = "";
    String category = "";
    String Goto = "";
    String path = "";
    int id;
    Intent i;
    final CharSequence[] alert_items = {"Phone Storage", "Dropbox"};
    //final CharSequence[] dialog_items = { "Email", "Bluetooth", "View", "Print", "Fax" };
    final CharSequence[] dialog_items = {"View", "Email", "Fax"};

    final CharSequence[] dialog_add = {"Add to Advance Directives", "Add to Other Documents", "Add to Medical Records"};

    String[] ADList = {"HIPAA Authorization", "Health Care Proxy", "Living Will", "Living Will/Health Care Proxy", "MOLST", "Non-Hospital DNR Order", "POLST","Other"};
    String[] OtherList = {"Financial", "Insurance", "Legal", "Other"};

    boolean external_flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_document);
        initComponent();
        initUi();
        initListener();
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
        DocumentQuery d = new DocumentQuery(context, dbHelper);
        if (preferences == null) {
            preferences = new Preferences(AddDocumentActivity.this);
        }

        if (preferences.getREGISTERED()&&preferences.isLogin()) {

        } else {
            Toast.makeText(getApplicationContext(),"You need to login first",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddDocumentActivity.this, SplashNewActivity.class));
            finish();
        }

        From = preferences.getString(PrefConstants.FROM);

         i = getIntent();
        Log.v("URI",i.getExtras().toString());
        final Uri audoUri = (Uri) i.getParcelableExtra(Intent.EXTRA_STREAM);
        if (audoUri != null) {
            Log.v("URI",audoUri.toString());
            AlertDialog.Builder builders = new AlertDialog.Builder(context);
            builders.setTitle("");
            builders.setCancelable(false);
            builders.setItems(dialog_add, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int itemPos) {
                    switch (itemPos) {
                        case 0: // email
                            From="AD";
                            addfile(audoUri);
                            initUi();
                            external_flag=true;
                            break;
                        case 1: // email
                            From="Other";
                            addfile(audoUri);
                            initUi();
                            external_flag=true;
                            break;
                        case 2: // Fax
                            From="Record";
                            addfile(audoUri);
                            initUi();
                            external_flag=true;
                            break;
                    }
                }
            });
            builders.create().show();
        }
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
        spinnerDoc = (MySpinner) findViewById(R.id.spinnerDoc);
        rlDocType = (RelativeLayout) findViewById(R.id.rlDocType);
        spinnerType = (MySpinner) findViewById(R.id.spinnerType);
        txtName = (TextView) findViewById(R.id.txtName);
        txtHosp = (TextView) findViewById(R.id.txtHosp);
        txtLocator= (TextView) findViewById(R.id.txtLocator);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtHolderName = (TextView) findViewById(R.id.txtHolderName);
        txtDist = (TextView) findViewById(R.id.txtDist);
        txtOther = (TextView) findViewById(R.id.txtOther);
        txtAdd = (TextView) findViewById(R.id.txtAdd);
        txtDocTYpe = (TextView) findViewById(R.id.txtDocType);
        tilOther = (TextInputLayout) findViewById(R.id.tilOther);
        tilOtherDocType = (TextInputLayout) findViewById(R.id.tilOtherDocType);
        txtOtherDocType = (TextView) findViewById(R.id.txtOtherDocType);
        tilName = (TextInputLayout) findViewById(R.id.tilName);
        tilPName = (TextInputLayout) findViewById(R.id.tilPName);
        tilHosp = (TextInputLayout) findViewById(R.id.tilHosp);
        tilDocType = (TextInputLayout) findViewById(R.id.tilDocType);
        txtPName = (TextView) findViewById(R.id.txtPName);
        txtFName = (TextView) findViewById(R.id.txtFName);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
       /* txtDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tilDoc.setVisibility(View.GONE);
                spinnerDoc.setVisibility(View.VISIBLE);
                spinnerDoc.setHint("Document Type");
                spinnerDoc.performClick();
            }
        });*/

        tilDate = (TextInputLayout) findViewById(R.id.tilDate);
        //tilDate.setHint("Date Signed");
        txtDate = (TextView) findViewById(R.id.txtDate);
       /* tilDate.setHintEnabled(false);
        txtDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilDate.setHintEnabled(true);
                return false;
            }
        });*/

        adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, OtherList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setHint("Document Category");

        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoc.setAdapter(adapter);
        spinnerDoc.setHint("Document Type");
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOther.setVisibility(View.VISIBLE);
                } else {
                    tilOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        if (From.equals("AD")) {
            spinnerDoc.setVisibility(View.VISIBLE);
            rlDocType.setVisibility(View.VISIBLE);
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerDoc.setAdapter(adapter);
            spinnerDoc.setHint("Document Type");
            spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (parent.getItemAtPosition(position).toString().equals("Other")) {
                        tilOtherDocType.setVisibility(View.VISIBLE);
                    } else {
                        tilOtherDocType.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            tilDocType.setVisibility(View.GONE);
            txtHosp.setVisibility(View.GONE);
            txtLocator.setVisibility(View.GONE);
            txtName.setVisibility(View.GONE);
            tilName.setVisibility(View.GONE);

            tilPName.setHint("Name of Person");
            tilDate.setHint("Date Signed");
            txtTitle.setText("Advance Directives");

        } else if (From.equals("Other")) {
            spinnerDoc.setVisibility(View.GONE);
            tilDocType.setVisibility(View.VISIBLE);
            txtHosp.setVisibility(View.GONE);
            txtLocator.setVisibility(View.GONE);
            txtName.setVisibility(View.GONE);
            tilName.setVisibility(View.GONE);

            tilPName.setHint("Name of Person");
            tilDate.setHint("Date Signed");
            txtTitle.setText("Other Documents");
        } else if (From.equals("Record")) {
            spinnerDoc.setVisibility(View.GONE);
            spinnerType.setVisibility(View.GONE);
            tilDocType.setVisibility(View.VISIBLE);
            /*txtHolderName.setVisibility(View.GONE);
            txtLocation.setVisibility(View.GONE);*/
            txtHosp.setVisibility(View.VISIBLE);
            txtLocator.setVisibility(View.VISIBLE);
            txtName.setVisibility(View.GONE);
            tilName.setVisibility(View.GONE);
            txtTitle.setText("Medical Records");
            tilPName.setHint("Name on Document");
            tilDate.setHint("Date of Document");
        }
        switch (From) {
            case "AD":
                spinnerType.setVisibility(View.GONE);
                break;

            case "Other":
                spinnerType.setVisibility(View.VISIBLE);
                break;
        }
//intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            //documentPath=data.getPath();
           /* if (data!=null) {
                Log.v("URI", data.toString());
                File localFile = UriHelpers.getFileForUri(context, data);
                Log.v("URI", localFile.getName() + " " + localFile.getPath());
                name = localFile.getName();
                documentPath = localFile.getPath();
                txtFName.setText(name);
                imgDoc.setClickable(false);
                String text = "You Have selected <b>" + name + "</b> Document";
                Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
                showDialogWindow(text);
                txtAdd.setText("Edit File");
                imgDoc.setImageResource(R.drawable.pdf);
            }*/
        // Figure out what to do based on the intent type
        if (i.getExtras() != null) {
            if (i.hasExtra("GoTo")) {
                Goto = i.getExtras().getString("GoTo");
            }
            if (i.hasExtra("Path")) {
                path = i.getExtras().getString("Path");
            }
        }

        if (Goto.equals("View")) {
            imgDone.setVisibility(View.GONE);
            imgDot.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.GONE);
            txtAdd.setVisibility(View.GONE);
            imgDoc.setClickable(true);
            disableView();
        } else if (Goto.equals("Edit")) {
            imgDone.setVisibility(View.VISIBLE);
            imgDot.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
            txtAdd.setVisibility(View.VISIBLE);
            txtAdd.setText("Edit File");
            imgDoc.setClickable(false);
        } else {
            imgDone.setVisibility(View.VISIBLE);
            imgDot.setVisibility(View.GONE);
            imgAdd.setVisibility(View.VISIBLE);
            txtAdd.setVisibility(View.VISIBLE);
            txtAdd.setText("Select File");
            imgDoc.setClickable(false);
        }

        if (Goto.equals("View") || Goto.equals("Edit")) {

            if (From.equals("AD")) {
                spinnerDoc.setVisibility(View.VISIBLE);
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Type");
                spinnerDoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (parent.getItemAtPosition(position).toString().equals("Other")) {
                            tilOtherDocType.setVisibility(View.VISIBLE);
                        } else {
                            tilOtherDocType.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                tilDocType.setVisibility(View.GONE);
                txtHosp.setVisibility(View.GONE);
                txtLocator.setVisibility(View.GONE);
                txtName.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);
               // txtPName.setHint("Name of Person");
               // txtDate.setHint("Date Signed");
            } else if (From.equals("Other")) {
                spinnerDoc.setVisibility(View.GONE);
                tilDocType.setVisibility(View.VISIBLE);
                txtHosp.setVisibility(View.GONE);
                txtLocator.setVisibility(View.GONE);
                txtName.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);
             //   txtPName.setHint("Name of Person");
              //  txtDate.setHint("Date Signed");
            } else if (From.equals("Record")) {
                spinnerDoc.setVisibility(View.GONE);
                spinnerType.setVisibility(View.GONE);
                tilDocType.setVisibility(View.VISIBLE);
               /* txtHolderName.setVisibility(View.GONE);
                txtLocation.setVisibility(View.GONE);*/
                txtHosp.setVisibility(View.VISIBLE);
                txtLocator.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.GONE);
                tilName.setVisibility(View.GONE);
               // txtPName.setHint("Name on Document");
              //  txtDate.setHint("Date of Document");
            }
            //  imgDot.setVisibility(View.VISIBLE);
            //imgDone.setVisibility(View.GONE);

            document = (Document) i.getExtras().getSerializable("DocumentObject");
            txtDate.setText(document.getDate());
            txtHolderName.setText(document.getHolder());
            txtLocation.setText(document.getLocation());
            txtFName.setText(document.getName());
            txtPName.setText(document.getPerson());
            txtName.setText(document.getPrinciple());
            txtOther.setText(document.getOtherCategory());
            txtHosp.setText(document.getHospital());
            txtLocator.setText(document.getLocator());
            documentPath = document.getDocument();
            imgDoc.setImageResource(document.getImage());
            id = document.getId();

            int index = 0;
            if (From.equals("AD")) {
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Type");

            } else {
                txtDocTYpe.setText(document.getType());
                if (!document.getCategory().equals("")) {
                    int indexs = 0;
                    for (int j = 0; j < OtherList.length; j++) {
                        if (document.getCategory().equals(OtherList[j])) {
                            indexs = j;
                        }
                    }
                    spinnerType.setSelection(indexs + 1);
                }
            }

            if (document.getFrom().equals("AD")) {
                int indexs = 0;
                adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, ADList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDoc.setAdapter(adapter);
                spinnerDoc.setHint("Document Type");
                for (int j = 0; j < ADList.length; j++) {
                    if (document.getType().equals(ADList[j])) {

                        indexs = j;
                        if (ADList[j].equals("Other")) {
                            tilOtherDocType.setVisibility(View.VISIBLE);
                            txtOtherDocType.setText(document.getOtherDoc());
                        } else {
                            tilOtherDocType.setVisibility(View.GONE);
                        }
                    }
                }
                spinnerDoc.setSelection(indexs + 1);
                String sel = spinnerDoc.getSelectedItem().toString();

            }


        } else {
            // imgDot.setVisibility(View.GONE);
            // tilDoc.setVisibility(View.GONE);
            //  imgDone.setVisibility(View.VISIBLE);

        }


    }

    private void addfile(Uri audoUri) {
        originPath =audoUri.toString();

        File f=new File(audoUri.getPath());
        originPath=f.getPath();
        originPath=originPath.replace("/root_path/","");
        documentPath=f.getName();
        name=f.getName();
        preferences.putInt(PrefConstants.CONNECTED_USERID,1);
        txtFName.setText(name);
        imgDoc.setClickable(false);
        String text = "You Have selected <b>" + name + "</b> Document";
        Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
        showDialogWindow(text);
        txtAdd.setText("Edit File");
        imgDoc.setImageResource(R.drawable.pdf);
    }

    private void disableView() {
        txtDate.setEnabled(false);
        txtHolderName.setEnabled(false);
        txtLocation.setEnabled(false);
        txtFName.setEnabled(false);
        txtPName.setEnabled(false);
        txtName.setEnabled(false);
        txtOther.setEnabled(false);
        txtHosp.setEnabled(false);
        txtLocator.setEnabled(false);
        txtDocTYpe.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgDoc:
                if (!documentPath.equals("")) {
                    Uri uri = null;
                    if (path.equals("No")) {

                        CopyReadAssetss(documentPath);

                    } else {
                        File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),documentPath);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                             intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
                        } else {
                            uri = Uri.fromFile(targetFile);
                        }
                       // Uri uris = Uri.parse(documentPath);
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

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate = new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                        DateClass d = new DateClass();
                        d.setDate(reportDate);
                        txtDate.setText(reportDate);
                        // txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;

            case R.id.imgDone:
                if (validate()) {

                   documentPath=copydb(originPath,name);
                    if (Goto.equals("Edit")) {
                        Boolean flag = DocumentQuery.updateDocumentData(id, name, category, date, location, holder, photo, documentPath, docType, From, person, principle, otherCategory, Hosp,otherDocType,locator);
                        if (flag == true) {
                            Toast.makeText(context, "You have updated document successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = DocumentQuery.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, category, date, location, holder, photo, documentPath, docType, From, person, principle, otherCategory, Hosp,otherDocType,locator);
                        if (flag == true) {
                            Toast.makeText(context, "You have added document successfully", Toast.LENGTH_SHORT).show();
                            if (external_flag==true) {
                                Intent i = new Intent(AddDocumentActivity.this, CarePlanListActivity.class);
                                startActivity(i);
                                preferences.putString(PrefConstants.FROM,From);
                            }
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
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
                                Intent i = new Intent(context, DocumentSdCardList.class);
                                startActivityForResult(i, RESULTCODE);
                                break;
                            case 1:
                                Intent intent = new Intent(context, DropboxLoginActivity.class);
                                intent.putExtra("FROM", "Document");
                                startActivityForResult(intent, RQUESTCODE);
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
                                Uri uri = null;
                                if (path.equals("No")) {
                                    CopyReadAssetss(documentPath);
                                } else {
                                    File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
                                    } else {
                                        uri = Uri.fromFile(targetFile);
                                    }
                                    // Uri uris = Uri.parse(documentPath);
                                    intent.setDataAndType(uri, "application/pdf");
                                    context.startActivity(intent);
                                   /* uri= Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/pdf");
                                    context.startActivity(intent);*/
                                    /*Uri uris = Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                        //  uri = FileProvider.getUriForFile(context, "com.mindyourelders.healthcare.HomeActivity.fileProvider", targetFile);
                                    } else {
                                        //  uri = Uri.fromFile(targetFile);
                                    }
                                    intent.setDataAndType(uris, "application/pdf");
                                    context.startActivity(intent);*/
                                }

                                break;
                            case 1: // email
                                if (path.equals("No")) {
                                    File file = new File(getExternalFilesDir(null), documentPath);
                                    Uri urifile = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        urifile = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", file);
                                    } else {
                                        urifile = Uri.fromFile(file);
                                    }

                                   // emailAttachement(urifile, txtFName.getText().toString());
                                } else {
                                   // Uri uris = Uri.parse(documentPath);
                                    emailAttachement(documentPath, txtFName.getText().toString());
                                }
                       /* bluetoothAttachement(new File(item.getAbsolutePath()),
                                context);
                        ShearedValues.activityID = getApplicationContext();*/

                                break;
                            case 2: // Fax
                                //File localFile = UriHelpers.getFileForUri(AddDocumentActivity.this, Uri.parse(documentPath));
                                Uri uris = Uri.parse(documentPath);
                                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + txtFName.getText().toString();
/*
                                File sourceFile = new File(path);

                                try {
                                    FileInputStream fileInputStream = new FileInputStream(sourceFile);
                                    Toast.makeText(AddDocumentActivity.this,"valid",Toast.LENGTH_SHORT).show();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                    Toast.makeText(AddDocumentActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                                }*/
                                new FaxCustomDialog(AddDocumentActivity.this, preferences.getString(PrefConstants.CONNECTED_PATH)+documentPath).show();
                                ;

                                /*if (path.equals("No"))
                                {
                                    File file=new File(getExternalFilesDir(null),documentPath);
                                    Uri urifile=null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                        urifile = FileProvider.getUriForFile(context, "com.mindyourelders.healthcare.HomeActivity.fileProvider", file);
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
                                }*/


                                break;

                        }
                    }
                });

                builders.create().show();
                // ((CarePlanActivity)context).CopyAssets();
                break;

        }
    }

    private String copydb(String originPath, String name) {

        String sd= preferences.getString(PrefConstants.CONNECTED_PATH);

        File data=new File(originPath);
        Log.e("", data.getAbsolutePath());
        String backupDBPath = "/MYLO/MYLO/";

        File file = new File(sd);
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        File currentDB = new File( data.getAbsolutePath());
        File backupDB = new File(path,name);

        if (!backupDB.exists()) {
            try {
                backupDB.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            copy(backupDB,currentDB);
            return backupDB.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void serverAttachement(Uri path) {
        String urlPath = path.getPath();
        System.out.println("Path of the file    " + path);
        //WebService.sendPDFToFax(convertFileToByteArray(file));
        new FaxCustomDialog(AddDocumentActivity.this, urlPath).show();
        ;
    }

    private void emailAttachement(String documentPath, String s) {
        Uri uri=null;
        File targetFile = new File(preferences.getString(PrefConstants.CONNECTED_PATH),documentPath);
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[]{""});
        String name = preferences.getString(PrefConstants.CONNECTED_NAME);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                name + " - " + s); // subject


        String body = "Hi, \n" +
                "\n" +
                "\n" + name +
                " shared this document with you. Please check the attachment. \n" +
                "\n" +
                "Thanks,\n" +
                name;
               // "Mind Your Loved Ones - Support";
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
        } else {
            uri = Uri.fromFile(targetFile);
        }
        emailIntent.putExtra(Intent.EXTRA_STREAM,uri);

        emailIntent.setType("application/email");

        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(getFilesDir(), documentPath);
        try {
            in = assetManager.open(documentPath);
            outFile = new File(getExternalFilesDir(null), documentPath);
            out = new FileOutputStream(outFile);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            /*out = openFileOutput(file.getName(), Context.MODE_WORLD_READABLE);

            copyFiles(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;*/
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
        Uri uri = null;
        // Uri uri= Uri.parse("file://" + getFilesDir() +"/"+documentPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", outFile);
        } else {
            uri = Uri.fromFile(outFile);
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "application/pdf");
        context.startActivity(intent);

    }

    private void copyFiles(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }


    }

    private boolean validate() {
        int indexValues = spinnerType.getSelectedItemPosition();
        int indexValue = spinnerDoc.getSelectedItemPosition();
        person = txtPName.getText().toString();

        principle = txtName.getText().toString();
        if (From.equals("AD")) {
            category = "AD";
            if (indexValue != 0) {
                docType = ADList[indexValue - 1];
            }
            if (docType.equals("Other"))
            {
                otherDocType=txtOtherDocType.getText().toString().trim();
            }
        } else {
            otherDocType="";
            docType = txtDocTYpe.getText().toString();
            if (indexValues != 0) {
                category = OtherList[indexValues - 1];
            }
            otherCategory = txtOther.getText().toString();
           /* switch(category)
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
            }*/
        }

        Hosp = txtHosp.getText().toString();
        locator=txtLocator.getText().toString();
        name = txtFName.getText().toString();
        location = txtLocation.getText().toString();
        holder = txtHolderName.getText().toString();
        date = txtDate.getText().toString();
        photo = R.drawable.pdf;
        if (name.equals("")) {
            txtFName.setError("Please Select File");
            Toast.makeText(context, "Please Select File", Toast.LENGTH_SHORT).show();
        } else if (docType.equals("")) {
            txtDocTYpe.setError("Please enter type of document");
            Toast.makeText(context, "Please enter type of document", Toast.LENGTH_SHORT).show();
        } else {
            return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULTCODE && data != null) {
            name = data.getExtras().getString("Name");
            originPath = data.getExtras().getString("URI");
            txtFName.setText(name);
            imgDoc.setClickable(false);
            String text = "You Have selected <b>" + name + "</b> Document";
            Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
            showDialogWindow(text);
            txtAdd.setText("Edit File");
            imgDoc.setImageResource(R.drawable.pdf);
        } else if (requestCode == RQUESTCODE && data != null) {
            name = data.getExtras().getString("Name");
            originPath = data.getExtras().getString("URI");
            txtFName.setText(name);
            imgDoc.setClickable(false);
            String text = "You Have selected <b>" + name + "</b> Document";
            Toast.makeText(context, Html.fromHtml(text), Toast.LENGTH_SHORT).show();
            showDialogWindow(text);
            txtAdd.setText("Edit File");
            imgDoc.setImageResource(R.drawable.pdf);
        }

    }

    private void showDialogWindow(String text) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setMessage(Html.fromHtml(text));
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
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

    private void copy(File backupDB, File currentDB)throws IOException {
        try (InputStream in = new FileInputStream(currentDB)) {
            try (OutputStream out = new FileOutputStream(backupDB)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
       /* InputStream in = new FileInputStream(backupDB);
        try {
            OutputStream out = new FileOutputStream(currentDB);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            finally {
                out.close();
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            in.close();
        }*/
    }
}