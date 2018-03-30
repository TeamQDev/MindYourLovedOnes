package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.LinkAdapter;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Links;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ChecklistActivity extends AppCompatActivity {
    Context context=this;
    ArrayList<Links> UrlList;
    ListView list;
    TextView txtTitle;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        initUI();
        initListener();
        getData();
        setData();
    }

    private void setData() {
        LinkAdapter adapter=new LinkAdapter(context,UrlList);
        list.setAdapter(adapter);
    }

    private void getData() {
        UrlList=new ArrayList<>();
        /*Links l1=new Links();
        l1.setName("ABA - Elder Law");
        l1.setUrl("https://www.americanbar.org/groups/senior_lawyers/elder_law.html");
        l1.setImage(R.drawable.aba_market);

        Links l2=new Links();
        l2.setName("Caring Info - all 50 states ");
        l2.setUrl("http://www.caringinfo.org/i4a/pages/index.cfm?pageid=1");
        l2.setImage(R.drawable.aba_market);

        Links l3=new Links();
        l3.setName("Aging with Dignity - all 50 states");
        l3.setUrl("https://www.agingwithdignity.org/");
        l3.setImage(R.drawable.aba_market);
*/
        Links l4=new Links();
        l4.setName("Medical History Form");
        l4.setUrl("medical_history_form.pdf");
        l4.setImage(R.drawable.pdf);

        Links l5=new Links();
        l5.setName("Checklist for Organizing Estate Planning Docs");
        l5.setUrl("checklist_for_organizing_estate_planning_docs.pdf");
        l5.setImage(R.drawable.pdf);
       /* Links l6=new Links();
        l6.setName("American Health Lawyers Association, Loving Conversations");
        l6.setUrl("https://www.healthlawyers.org/Pages/PageNotFoundError.aspx?requestUrl=https://www.healthlawyers.org/hlresources/PI/InfoSeries/Pages/LovingConversations.aspx");
        l6.setImage(R.drawable.aba_market);*/
/*
        Links l7=new Links();
        l7.setName("American Hospital Association, Put It In Writing");
        l7.setUrl("http://www.aha.org/advocacy-issues/initiatives/piiw/index.shtml");
        l7.setImage(R.drawable.link_three);

        Links l8=new Links();
        l8.setName("Caring Connections links to Advance Directive Forms by State (PDF)");
        l8.setUrl("http://www.caringinfo.org/i4a/pages/index.cfm?pageid=3289");
        l8.setImage(R.drawable.care);

        Links l9=new Links();
        l9.setName("Center for Practical Bioethics, Caring Conversations");
        l9.setUrl("http://practicalbioethics.org/programs/caring-conversations.html?jaredirect");
        l9.setImage(R.drawable.link_five);

        Links l10=new Links();
        l10.setName("National Healthcare Decisions Day (NHDD),  Advance Care Planning");
        l10.setUrl("https://www.nhdd.org/public-resources/#where-can-i-get-an-advance-directive");
        l10.setImage(R.drawable.link_six);

        Links l11=new Links();
        l11.setName("The Commission on Law and Aging, ABA");
        l11.setUrl("https://www.americanbar.org/groups/law_aging.html");
        l11.setImage(R.drawable.aba_market);*/

      /*  Links l12=new Links();
        l12.setName("The Commission on Law and Aging, ABA, Consumer’s Tool Kit for Advance Planning");
        l12.setUrl("https://www.americanbar.org/content/dam/aba/uncategorized/2011/2011_aging_bk_consumer_tool_kit_bk.authcheckdam.pdf");
        l12.setImage(R.drawable.aba_market);*/

        // UrlList.add(l1);
        // UrlList.add(l2);
        // UrlList.add(l3);
        UrlList.add(l4);
        UrlList.add(l5);
        //UrlList.add(l6);
       /* UrlList.add(l7);
        UrlList.add(l8);
        UrlList.add(l9);
        UrlList.add(l10);
        UrlList.add(l11);*/
        //  UrlList.add(l12);
        //Fol show
        // Datalist=new ArrayList<>();
        //Datalist.add("ABA - Elder Law");
    }

    private void initListener() {

    }

    private void initUI() {
        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Helpful forms and\ntemplates");
        imgBack= (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        list= (ListView)findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CopyReadAssetss(UrlList.get(position).getUrl());
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder alert=new AlertDialog.Builder(context);
                alert.setTitle("Email ?");
                alert.setMessage("Do you want to email pdf file ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Links link= UrlList.get(position);
                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                new String[] { "" });
                        // String name= getString(PrefConstants.CONNECTED_NAME);
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                link.getName()); // subject
                        String body="Hi, \n" +
                                "\n" +
                                " Please check the attachment. \n" +
                                "\n" +
                                "Thanks";
                        //"Mind Your Loved Ones - Support";
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body); // Body

                        AssetManager assetManager = context.getAssets();
                        File outFile = null;
                        InputStream in = null;
                        OutputStream out = null;
                        File file = new File(context.getFilesDir(), link.getUrl());
                        try
                        {
                            in = assetManager.open(link.getUrl());
                            outFile=new File(context.getExternalFilesDir(null),link.getUrl());
                            out=new FileOutputStream(outFile);

                            copyFiles(in,out);
                            in.close();
                            in=null;
                            out.flush();
                            out.close();
                            out=null;
                        } catch (Exception e)
                        {
                            Log.e("tag", e.getMessage());
                        }
                        Uri uri=null;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            emailIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", outFile);
                        } else {
                            uri = Uri.fromFile(outFile);
                        }
                        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        emailIntent.setType("application/email");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();

                return true;
            }
        });
    }

    public void CopyReadAssetss(String documentPath) {
        AssetManager assetManager = context.getAssets();
        File outFile = null;
        InputStream in = null;
        OutputStream out = null;
        File file = new File(context.getFilesDir(), documentPath);
        try
        {
            in = assetManager.open(documentPath);
            outFile=new File(context.getExternalFilesDir(null),documentPath);
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

    private void copyFiles(InputStream in, OutputStream out) throws IOException
    {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1)
        {
            out.write(buffer, 0, read);
        }


    }
}
