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
import com.mindyourlovedones.healthcare.Connections.ConnectionAdapter;
import com.mindyourlovedones.healthcare.Connections.GrabConnectionActivity;
import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.MyConnectionsQuery;
import com.mindyourlovedones.healthcare.model.Emergency;
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

public class FragmentEmergency extends Fragment implements View.OnClickListener{
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvEmergency;
    ArrayList<Emergency> emergencyList;
    TextView txtAdd,txtMsg,txtFTU;
    RelativeLayout llAddConn;
    TextView txtTitle;
    ImageView imgNoti;
    DBHelper dbHelper;
    ConnectionAdapter connectionAdapter;
    Preferences preferences;
    EmergencyAdapter emergencyAdapter;
    RelativeLayout rlGuide;
     String finalText="";
    final CharSequence[] dialog_items = {"View","Email","Fax"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_emergency, null);
        initComponent();
        getData();
        initUI();
        initListener();
        return rootview;
    }

    private void initComponent() {
        preferences=new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity(),preferences.getString(PrefConstants.CONNECTED_USERDB));
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
    }

    private void setListData() {
     /*   emergencyAdapter = new EmergencyAdapter(getActivity(), emergencyList);
        lvEmergency.setAdapter(emergencyAdapter);*/
        if (emergencyList.size()!=0) {
            emergencyAdapter = new EmergencyAdapter(getActivity(), emergencyList);
            lvEmergency.setAdapter(emergencyAdapter);
            rlGuide.setVisibility(View.GONE);
            lvEmergency.setVisibility(View.VISIBLE);
        }
        else{
            rlGuide.setVisibility(View.VISIBLE);
            lvEmergency.setVisibility(View.GONE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddConn.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>add</b> information click the green bar at the bottom of the screen. If the person is in your <b>Contacts</b> click the gray  bar on the top right side of your screen." +
                "<br><br>" +
                "To <b>save</b> information click the green bar at the bottom of the screen." +
                "<br><br>" +
                "To <b>edit</b> information click the picture of the <b>pencil.</b> To save your edits click the <b>green bar</b> at the bottom of the screen." +
                "<br><br>" +
                "To <b>make an automated telephone call</b> or <b>delete</b> the entry <b>swipe right to left</b> the arrow symbol on the right side." +
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
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("EMERGENCY CONTACTS &\nHEALTH CARE PROXY AGENT");
        rlGuide=rootview.findViewById(R.id.rlGuide);
        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        /*imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);*/
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddConn = (RelativeLayout) rootview.findViewById(R.id.llAddConn);
        lvEmergency = (SwipeMenuListView) rootview.findViewById(R.id.lvEmergency);
        if (emergencyList.size()!=0||emergencyList!=null)
        {
            setListData();
        }
        lvEmergency.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvEmergency.setMenuCreator(creator);
        lvEmergency.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Emergency item = emergencyList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        callUser(item);
                        break;
                    case 1:
                        // delete
                            deleteEmergency(item);
                        break;
                }
                return false;
            }
        });
    }

    private void callUser(Emergency item) {
        String mobile=item.getMobile();
        String hphone=item.getPhone();
        String wPhone=item.getWorkPhone();

        if (mobile.length()!=0||hphone.length()!=0||wPhone.length()!=0)
        {
            CallDialog c=new CallDialog();
            c.showCallDialog(getActivity(),mobile,hphone,wPhone);
        }
        else{
            Toast.makeText(getActivity(),"You have not added phone number for call",Toast.LENGTH_SHORT).show();
        }
    }
   /* private void showCallDialog(final Context context, String mobile, String hphone, String wphone) {
        //   String text=mobile;
            if (mobile.contains("-")) {
                mobile = mobile.replaceAll("-", "");
            }
            if (hphone.contains("-")) {
                hphone = hphone.replaceAll("-", "");
           }
            if (wphone.contains("-")) {
                wphone = wphone.replaceAll("-", "");
            }
           // System.out.println("" + text);
            try {
                Double.parseDouble(mobile);
                Double.parseDouble(hphone);
                Double.parseDouble(wphone);
            } catch (NumberFormatException ex) {
                System.out.println("Some Mistake");
            }
         String[] num={mobile,hphone,wphone};
         final ArrayList<String> a=new ArrayList();
        *//*final String finalMobile = mobile;
        final String finalHphone = hphone;
        final String finalWphone = wphone;*//*
        for (int i=0;i<num.length;i++)
        {
            if (num[i].length()!=0)
            {
                a.add(num[i]);
            }

        }
     if (a.size()==1)
     {
         String value=a.get(0);
         new AlertDialog.Builder(context)
                 .setTitle("Calling Alert")
                 .setMessage("Do you want to call this number? ")
                 .setPositiveButton(a.get(0),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(0));
                             }
                         })
                 .setCancelable(true).show();
     }
     else if(a.size()==2)
     {
         new AlertDialog.Builder(context)
                 .setTitle("Calling Alert")
                 .setMessage("Do you want to call this number? ")
                 .setPositiveButton(a.get(0),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(0));
                             }
                         })

                 .setNegativeButton(a.get(1),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(1));
                             }
                         }).setCancelable(true).show();
     }
     else if (a.size()==3) {
         new AlertDialog.Builder(context)
                 .setTitle("Calling Alert")
                 .setMessage("Do you want to call this number? ")
                 .setPositiveButton(a.get(0),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(0));
                             }
                         })
                 .setNeutralButton(a.get(1), new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         onCall(a.get(1));
                     }
                 })
                 .setNegativeButton(a.get(2),
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface arg0, int arg1) {
                                 onCall(a.get(2));
                             }
                         }).setCancelable(true).show();
     }

    }*/

    private void deleteEmergency(final Emergency item) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Delete");
        alert.setMessage("Do you want to Delete this record?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean flag= MyConnectionsQuery.deleteRecord(item.getId());
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
        emergencyList =  MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
      /* //emergencyList = MyConnectionsQuery.fetchAllRecord();
emergencyList=new ArrayList<>();
        Emergency P1 = new Emergency();
        P1.setName("Clark Smith");
        P1.setRelationType("Self");
        P1.setImage(R.drawable.father);
        P1.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P1.setPhone("789566236");

        Emergency P2 = new Emergency();
        P2.setName("Cherry Smith");
        P2.setRelationType("Wife");
        P2.setImage(R.drawable.mother);
        P2.setAddress("33 West 60th St., 6th Floor New York, Ny 10023 USA.");
        P2.setPhone("789566236");



        emergencyList.add(P1);
        emergencyList.add(P2);
*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddConn:
                preferences.putString(PrefConstants.SOURCE,"Emergency");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);

                break;

            case R.id.imgRight:

                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylopdf/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Emergency.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                preferences.copyFile("ic_launcher.png",getActivity());
                Header.addImage( "/sdcard/MYLO/images/"+"ic_launcher.png");
                Header.addEmptyLine(1);
                Header.addusereNameChank("Emergency Contacts & \nHealth Care Proxy Agent");//preferences.getString(PrefConstants.CONNECTED_NAME));
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
/*
                new Header().createPdfHeader(file.getAbsolutePath(),
                        "Emergency Contacts");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/


                ArrayList<Emergency> emergencyList=MyConnectionsQuery.fetchAllEmergencyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
                new Individual("Emergency",emergencyList);
                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                              String path=Environment.getExternalStorageDirectory()
                                      + "/mylopdf/"
                                      + "/Emergency.pdf";
                        switch (itemPos) {
                            case 0: //View
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getEmergencyInfo());

                                    new PDFDocumentProcess(path,
                                            getActivity(), result);

                                    System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Emergency Contact");
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

   /* private void emailAttachement(File f) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                new String[] { "" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "MIND YOUR ELDERS"); // subject
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""); // Body

        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));

        emailIntent.setType("application/email");

        getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }*/

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }


    /*private void onCall(String finalMobile) {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                   getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    123);
        } else {
            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:" + finalMobile)));
        }
    }*/
}
