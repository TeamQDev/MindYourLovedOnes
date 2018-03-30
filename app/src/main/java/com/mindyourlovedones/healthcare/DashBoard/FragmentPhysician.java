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
import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.SpecialistAdapter;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.DoctorQuery;
import com.mindyourlovedones.healthcare.database.SpecialistQuery;
import com.mindyourlovedones.healthcare.model.Specialist;
import com.mindyourlovedones.healthcare.pdfCreation.MessageString;
import com.mindyourlovedones.healthcare.pdfCreation.PDFDocumentProcess;
import com.mindyourlovedones.healthcare.pdfdesign.Header;
import com.mindyourlovedones.healthcare.pdfdesign.Individual;
import com.mindyourlovedones.healthcare.utility.CallDialog;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.mindyourlovedones.healthcare.utility.SwipeMenuCreation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentPhysician extends Fragment implements View.OnClickListener{
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvSpecialist;
    ArrayList<Specialist> specialistList;
    RelativeLayout llAddSpecialist;
    Preferences preferences;
    TextView txtTitle;
    TextView txtAdd,txtMsg,txtFTU;
    DBHelper dbHelper;
    SpecialistAdapter specialistAdapter;
    RelativeLayout rlGuide;
    final CharSequence[] dialog_items = {"View","Email","Fax"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_physician,null);
       initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
        DoctorQuery d=new DoctorQuery(getActivity(),dbHelper);
        SpecialistQuery s=new SpecialistQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (specialistList.size()!=0) {
            specialistAdapter = new SpecialistAdapter(getActivity(), specialistList);
            lvSpecialist.setAdapter(specialistAdapter);
            lvSpecialist.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
        }
        else{
            lvSpecialist.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddSpecialist.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>add</b> information click the green bar at the bottom of the screen.If the person is in your <b>Contacts</b> click the gray bar on the top right side of your screen" +
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
        txtTitle= (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("PRIMARY PHYSICIAN");
        rlGuide=rootview.findViewById(R.id.rlGuide);
       imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddSpecialist= (RelativeLayout) rootview.findViewById(R.id.llAddSpecialist);
        lvSpecialist = (SwipeMenuListView) rootview.findViewById(R.id.lvSpecialist);
        if (specialistList.size()!=0||specialistList!=null)
        {
            setListData();
        }
        lvSpecialist.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvSpecialist.setMenuCreator(creator);
        lvSpecialist.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Specialist item = specialistList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        callUser(item);
                        break;
                    case 1:
                        // delete
                        deleteSpecialist(item);
                        break;
                }
                return false;
            }
        });

    }
    private void callUser(Specialist item) {
        String mobile=item.getOfficePhone();
        String hphone=item.getHourPhone();
        String wPhone=item.getOtherPhone();

        if (mobile.length()!=0||hphone.length()!=0||wPhone.length()!=0)
        {
            CallDialog c=new CallDialog();
            c.showCallDialog(getActivity(),mobile,hphone,wPhone);
        }
        else{
            Toast.makeText(getActivity(),"You have not added phone number for call",Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteSpecialist(final Specialist item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag= SpecialistQuery.deleteRecord(item.getId(), 1);
                if(flag==true)
                {
                    Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
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
       specialistList=SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),1);

       /*specialistList=new ArrayList<>();

        Specialist P1=new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setPhone("789-789-5236");


        Specialist P2=new Specialist();
        P2.setName("Dr. James");
        P2.setType("Neuro Surgeon");
        P2.setAddress("#204,10 top Street, los Angeles, California.");
        P2.setImage(R.drawable.docto);
        P2.setPhone("987-789-5236");

        Specialist P3=new Specialist();
        P3.setName("Dr. Smith");
        P3.setType("Neuro Surgeon");
        P3.setAddress("#205,10 Left Street, los Angeles, California.");
        P3.setImage(R.drawable.doctors);
        P3.setPhone("789-789-5236");

        specialistList.add(P1);
       specialistList.add(P2);
        specialistList.add(P3);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddSpecialist:
               // hideSoftKeyboard();
                preferences.putString(PrefConstants.SOURCE,"Physician");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
                // DialogManager dialogManager=new DialogManager(new FragmentSpecialist());
                // dialogManager.showCommonDialog("Add?","Do you want to add new specialist?",getActivity(),"ADD_SPECIALIST",null);
                break;
            case R.id.imgRight:


                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Physician.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png",getActivity());
                Header.addImage( "/sdcard/MYLO/images/"+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Primary Physician");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
                        "Primary Physician");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/


                ArrayList<Specialist> specialistsList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),1);
                new Individual(specialistsList,"Physician");
                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                            String path=Environment.getExternalStorageDirectory()
                                    + "/mylopdf/"
                                    + "/Physician.pdf";
                        switch (itemPos) {
                            case 0: //View
                                if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getPhysicianInfo());

                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                            + "/mylopdf/"
                                            + "/Physician.pdf",
                                            getActivity(), result);

                                    System.out.println("\n" + result + "\n");
                                }else{
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getPhysicianInfo());

                                    new PDFDocumentProcess(path,
                                            getActivity(), result);

                                    System.out.println("\n" + result + "\n");
                                }
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Primary Physician");
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

    public void postCommonDialog() {
        //preferences.putString(PrefConstants.SOURCE,"Speciality");
        Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
        startActivity(i);
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
