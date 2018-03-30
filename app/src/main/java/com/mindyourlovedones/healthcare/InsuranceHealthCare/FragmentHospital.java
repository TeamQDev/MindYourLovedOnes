package com.mindyourlovedones.healthcare.InsuranceHealthCare;

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
import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.HospitalHealthQuery;
import com.mindyourlovedones.healthcare.model.Hospital;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.pdfdesign.Specialty;
import com.mindyourlovedones.healthcare.utility.CallDialog;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.mindyourlovedones.healthcare.utility.SwipeMenuCreation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by V@iBh@V on 10/23/2017.
 */

public class FragmentHospital extends Fragment implements View.OnClickListener {
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvHospital;
    ArrayList<Hospital> hospitalList;
    RelativeLayout llAddHospital;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlGuide;
    TextView txtMsg,txtFTU;
    final String dialog_items[] = {"View","Email","Fax"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_hospital, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
        HospitalHealthQuery f = new HospitalHealthQuery(getActivity(), dbHelper);
    }

    private void setListData() {
        if (hospitalList.size() != 0) {
            HospitalAdapter hospitalAdapter = new HospitalAdapter(getActivity(), hospitalList);
            lvHospital.setAdapter(hospitalAdapter);
            lvHospital.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
        } else {
            lvHospital.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
        }
    }


    private void initListener() {
        llAddHospital.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>add</b> information click the green bar at the bottom of the screen. If the entity is in your <b>Contacts</b> click the gray bar on the top right side of your screen to load data." +
                "<br><br>" +
                "To <b>save</b> information click the green bar at the bottom of the screen." +
                "<br><br>" +
                "To <b>edit</b> information click the picture of the <b>pencil</b>. To <b>save</b> your edits click the <b>green bar</b> at the bottom of the screen." +
                "<br><br>" +
                "To <b>make an automated phone call</b> or <b>delete</b> the entry <b>swipe right to left</b> arrow symbol." +
                "<br><br>" +
                "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen." +
                "<br><br>" +
                "To <b>add a picture</b> click the picture of the <b>pencil</b> and" +
                "either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again.Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card";
        txtMsg.setText(Html.fromHtml(msg));
        txtFTU=rootview.findViewById(R.id.txtFTU);
        txtFTU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMsg.setVisibility(View.VISIBLE);
            }
        });
        imgRight = (ImageView) getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        rlGuide=rootview.findViewById(R.id.rlGuide);
        llAddHospital = (RelativeLayout) rootview.findViewById(R.id.llAddHospital);
        lvHospital = (SwipeMenuListView) rootview.findViewById(R.id.lvHospital);
        setListData();

        lvHospital.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s = new SwipeMenuCreation();
        SwipeMenuCreator creator = s.createMenu(getActivity());
        lvHospital.setMenuCreator(creator);
        lvHospital.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Hospital item = hospitalList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        callUser(item);
                        break;
                    case 1:
                        // delete
                        deleteHospital(item);
                        break;
                }
                return false;
            }
        });
    }

    private void callUser(Hospital item) {
        String mobile = item.getOfficePhone();
        String hphone = item.getMobile();
        String wPhone = item.getOtherPhone();

        if (mobile.length() != 0 || hphone.length() != 0 || wPhone.length() != 0) {
            CallDialog c = new CallDialog();
            c.showCallDialog(getActivity(), mobile, hphone, wPhone);
        } else {
            Toast.makeText(getActivity(), "You have not added phone number for call", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteHospital(final Hospital item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag = HospitalHealthQuery.deleteRecord(item.getId());
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

    private void getData() {
        hospitalList = HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* FinanceList=new ArrayList<>();

        Finance P1=new Finance();
        P1.setFirm("Grand Capital");
        P1.setName("James Holms");
        P1.setCategory("Accountant");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");
        P1.setImage(R.drawable.insis);
        P1.setPhone("589-789-5236");


        Finance P2=new Finance();
        P2.setFirm("Latham & Watkins");
        P2.setCategory("Attorney");
        P2.setName("Jack Watson");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");
        P2.setImage(R.drawable.insir);
        P2.setPhone("366-789-5236");

        Finance P3=new Finance();
        P3.setFirm("American Advisory Group");
        P3.setName("John Sheridon");
        P3.setCategory("Financial Planner");
        P3.setAddress("200 E MAIN ST, PHOENIX AZ 85123, USA");
        P3.setImage(R.drawable.insurs);
        P3.setPhone("986-789-5236");


        FinanceList.add(P1);
        FinanceList.add(P2);
        FinanceList.add(P3);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddHospital:
                preferences.putString(PrefConstants.SOURCE, "Hospital");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);
                break;
            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Hospital.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png",getActivity());
                Header.addImage( "/sdcard/MYLO/images/"+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Hospitals and other health professionals");//preferences.getString(PrefConstants.CONNECTED_NAME));
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

              /*  new Header().createPdfHeader(file.getAbsolutePath(),
                        "Hospitals and other health professionals");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                ArrayList<Hospital> HospitalList = HospitalHealthQuery.fetchAllHospitalhealthRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                new Specialty("Hospital", HospitalList);
                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
String path=Environment.getExternalStorageDirectory()
        + "/mylopdf/"
        + "/Hospital.pdf";
                        switch (itemPos) {

                            case 0: // view
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getHospitalInfo());

                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");

                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Hospitals And Other Health Preofessional");
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


