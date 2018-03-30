package com.mindyourlovedones.healthcare.DashBoard;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.FormQuery;
import com.mindyourlovedones.healthcare.database.InsuranceQuery;
import com.mindyourlovedones.healthcare.model.Document;
import com.mindyourlovedones.healthcare.model.Form;
import com.mindyourlovedones.healthcare.model.Insurance;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.pdfdesign.InsurancePdf;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.mindyourlovedones.healthcare.utility.SwipeMenuCreation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 10/30/2017.
 */

public class FragementForm extends Fragment implements View.OnClickListener {
    View rootview;
    ImageView imgRight;
    SwipeMenuListView lvDoc;
    ArrayList<Form> documentList;
    ArrayList<Document> documentListOld;
    ImageView imgBack;
    TextView txtTitle,txtMsg,txtFTU;
    String From;
    final String dialog_items[]={"View","Email","Fax"};
    RelativeLayout llAddDoc;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlGuide;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_insurance_form, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
        FormQuery i = new FormQuery(getActivity(), dbHelper);
    }

    private void setListData() {
       if (documentList.size() != 0) {
            DocumentsAdapter insuranceAdapter = new DocumentsAdapter(getActivity(), documentList);
            lvDoc.setAdapter(insuranceAdapter);
            lvDoc.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
        } else {
            lvDoc.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddDoc.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>add</b> information click the green bar at the bottom of the screen. Click the plus sign to Select the File." +
                "<br><br>" +
                "The file is either sitting on your phone or in your Dropbox . Choose the location and click Add." +
                "<br><br>" +
                "To <b>save</b> information click the <b>check mark</b> on the top top right side of the screen" +
                "<br><br>" +
                "To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>check mark</b> again." +
                "<br><br>" +
                "To <b>delete</b> delete the entry <b>swipe right to left</b> the arrow symbol on the right side of the screen." +
                "<br><br>" +
                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen.";

        txtMsg.setText(Html.fromHtml(msg));
        txtFTU=rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMsg.setVisibility(View.VISIBLE);
            }
        });
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddDoc = (RelativeLayout) rootview.findViewById(R.id.llAddDoc);
        rlGuide=rootview.findViewById(R.id.rlGuide);
        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        lvDoc = (SwipeMenuListView) rootview.findViewById(R.id.lvDoc);
        lvDoc.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createSingleMenu(getActivity());
        lvDoc.setMenuCreator(creator);
        lvDoc.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Form item = documentList.get(position);
                switch (index) {
                    case 0:
                        // delete
                        deleteDocument(item);
                        break;
                }
                return true;
            }
        });
        setListData();
    }

    private void deleteDocument(final Form item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = FormQuery.deleteRecord(item.getId());
                if (flag == true) {
                    Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    getData();
                    setListData();
                }
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

    }

    private void deleteInsurance(Insurance item) {
        boolean flag= InsuranceQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        documentList = FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
    //  documentList=new ArrayList<>();
 /*
        Form f=new Form();
        f.setName("sdfds");
        f.setImage(R.id.imgPdf);*/
/*
        Insurance P1=new Insurance();
        P1.setName("Symphonix Health");
        P1.setId("4489");
        P1.setGroup("Group angels");
        P1.setType("Medicare");
        P1.setImage(R.drawable.insu);
        P1.setPhone("963-789-5236");
        P1.setMember("Consultive");


        Insurance P2=new Insurance();
        P2.setName("Sierra Health");
        P2.setId("8965");
        P2.setGroup("Group angels");
        P2.setType("Supplemental");
        P2.setImage(R.drawable.insur);
        P2.setPhone("396-545-5236");
        P2.setMember("Consultive");

        Insurance P3=new Insurance();
        P3.setName("Humana Insurance");
        P3.setId("9685");
        P3.setGroup("Group angels");
        P3.setType("Long Term Care ");
        P3.setImage(R.drawable.insurs);
        P3.setPhone("985-985-5236");
        P3.setMember("Consultive");

        Insurance P4=new Insurance();
        P4.setName("Aetna");
        P4.setId("3698");
        P4.setGroup("Group angels");
        P4.setType("Medical");
        P4.setImage(R.drawable.insir);
        P4.setPhone("968-985-5236");
        P4.setMember("Consultive");

        Insurance P5=new Insurance();
        P5.setName("Aetna");
        P5.setId("9635");
        P5.setGroup("Group angels");
        P5.setType("Dental");
        P5.setImage(R.drawable.insis);
        P5.setPhone("365-985-5236");
        P5.setMember("Consultive");



        documentList.add(P1);
        documentList.add(P2);
        documentList.add(P3);
        documentList.add(P4);
        documentList.add(P5);

*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddDoc:
                //preferences.putString(PrefConstants.SOURCE, "InsuranceForm");
                Intent i = new Intent(getActivity(), AddInsuranceFormActivity.class);
                i.putExtra("GoTo","Add");
                startActivity(i);
                break;

            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "InsuranceForm.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png",getActivity());
                Header.addImage( "/sdcard/MYLO/images/"+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Insurance Form");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
                        "Insurance Information");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                // Header.addEmptyLine(2);*/

                ArrayList<Form> formList= FormQuery.fetchAllDocumentRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                new InsurancePdf(formList,"form");
                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path= Environment.getExternalStorageDirectory()
                                + "/mylopdf/" 
                                + "/InsuranceForm.pdf";
                        switch (itemPos) {
                            case 0: // view
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getFormInfo());


                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Insurance Form");
                                break;
                            case 2://fax
                                new FaxCustomDialog(getActivity(), path).show();
                                break;
                        }
                    }

                });
                builder.create().show();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
