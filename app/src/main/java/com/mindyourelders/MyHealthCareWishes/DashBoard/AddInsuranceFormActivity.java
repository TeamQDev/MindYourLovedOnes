package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.FormQuery;
import com.mindyourelders.MyHealthCareWishes.model.Form;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;


public class AddInsuranceFormActivity extends AppCompatActivity  implements View.OnClickListener{
    private static final int RESULTCODE = 200;
    Context context = this;
    ImageView imgBack,imgDot,imgDone,imgDoc,imgAdd;
    TextView txtName;
    String From;
    Preferences preferences;
    final CharSequence[] dialog_items = {"Print", "Fax", "View" };
    Form document;
    DBHelper dbHelper;
    String name="";

    String documentPath="";
    int photo;
    String path="";
    String Goto="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_insurance_form);
        initComponent();
        initUi();
        initListener();
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
        imgDot.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        imgAdd.setOnClickListener(this);
        imgDoc.setOnClickListener(this);

    }

    private void initUi() {
        imgDot = (ImageView) findViewById(R.id.imgDot);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgDoc = (ImageView) findViewById(R.id.imgDoc);
        imgAdd = (ImageView) findViewById(R.id.imgAdd);
        txtName= (TextView) findViewById(R.id.txtName);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            Goto = i.getExtras().getString("GoTo");
            //path=i.getExtras().getString("Path");
        }

        if (Goto.equals("View")) {
            imgDot.setVisibility(View.VISIBLE);
            imgDone.setVisibility(View.GONE);
            imgAdd.setVisibility(View.GONE);

            document = (Form) i.getExtras().getSerializable("FormObject");
            txtName.setText(document.getName());
            documentPath = document.getDocument();
            imgDoc.setImageResource(document.getImage());

        } else {
            imgDot.setVisibility(View.GONE);
            imgDone.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.VISIBLE);
        }

    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        FormQuery d=new FormQuery(context,dbHelper);
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
                   /* File f=new File(documentPath);
                    uri= FileProvider.getUriForFile(context,
                            BuildConfig.APPLICATION_ID + ".provider",
                            f);*/
                    uri=Uri.parse(documentPath);

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri, "application/pdf");
                    context.startActivity(intent);
                  /*  if (path.equals("No"))
                    {
                       *//* uri =  Uri.parse("android.resource://"+getPackageName()+"/raw/"+documentPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);*//*
                        CopyReadAssetss(documentPath);
                       *//* uri= Uri.fromFile(getFileStreamPath("file:///android_asset/"+documentPath));
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        context.startActivity(intent);*//*
                    }
                    else{*/
                    // CopyReadAssetss(documentPath);
                       /* uri= Uri.parse(documentPath);
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(uri, "application/pdf");
                        context.startActivity(intent);*/
                    //  }



                }

                break;

            case R.id.imgDone:
                if (validate()) {
                    Boolean flag = FormQuery.insertDocumentData(preferences.getInt(PrefConstants.CONNECTED_USERID), name,photo,documentPath);
                    if (flag == true) {
                        Toast.makeText(context, "You have added form successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.imgAdd:
                Intent i=new Intent(context,DocumentSdCardList.class);
                startActivityForResult(i,RESULTCODE);
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
                              /*  if (path.equals("No"))
                                {
                                    CopyReadAssetss(documentPath);
                                }
                                else{
*/                                    uri= Uri.parse(documentPath);
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/pdf");
                                    context.startActivity(intent);
                               // }

                                break;

                        }
                    }
                });

                builder.create().show();
                // ((CarePlanActivity)context).CopyAssets();
                break;

        }
        }

    private boolean validate() {
        photo=R.drawable.pdf;
        name=txtName.getText().toString();
        if (name.length()==0)
        {
            Toast.makeText(context,"Add Name of document",Toast.LENGTH_SHORT).show();
        }
        else{
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
        }
    }

}
