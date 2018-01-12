package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

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
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PharmacyQuery;
import com.mindyourelders.MyHealthCareWishes.model.Pharmacy;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Specialty;
import com.mindyourelders.MyHealthCareWishes.utility.CallDialog;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentPharmacy extends Fragment implements View.OnClickListener{
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvPharmacy;
    ArrayList<Pharmacy> PharmacyList;
    RelativeLayout llAddPharmacy;
    Preferences preferences;
    RelativeLayout rlGuide;
DBHelper dbHelper;
TextView txtMsg,txtFTU;
    final String dialog_items[]={"View","Email","Fax"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_pharmacy,null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        PharmacyQuery p=new PharmacyQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (PharmacyList.size()!=0) {
            PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(getActivity(), PharmacyList);
            lvPharmacy.setAdapter(pharmacyAdapter);
            lvPharmacy.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
        }
        else{
            lvPharmacy.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        llAddPharmacy.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To <b>add</b> information click the green bar at the bottom of the screen.If the person is in your <b>Contacts</b> click the gray  bar on the top right side of your screen." +
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
        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        rlGuide=rootview.findViewById(R.id.rlGuide);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddPharmacy= (RelativeLayout) rootview.findViewById(R.id.llAddPharmacy);
        lvPharmacy= (SwipeMenuListView) rootview.findViewById(R.id.lvPharmacy);
        if (PharmacyList.size()!=0) {
            setListData();
        }
        lvPharmacy.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvPharmacy.setMenuCreator(creator);
        lvPharmacy.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Pharmacy item = PharmacyList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        callUser(item);
                        break;
                    case 1:
                        // delete
                        deletePharmacy(item);
                        break;
                }
                return false;
            }
        });
    }
    private void callUser(Pharmacy item) {
        String mobile=item.getPhone();
        String hphone="";
        String wPhone="";

        if (mobile.length()!=0||hphone.length()!=0||wPhone.length()!=0)
        {
            CallDialog c=new CallDialog();
            c.showCallDialog(getActivity(),mobile,hphone,wPhone);
        }
        else{
            Toast.makeText(getActivity(),"You have not added phone number for call",Toast.LENGTH_SHORT).show();
        }
    }
    private void deletePharmacy(Pharmacy item) {
        boolean flag= PharmacyQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        PharmacyList= PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
     /*   PharmacyList=new ArrayList<>();

        Pharmacy P1=new Pharmacy();
        P1.setName("Health Care Medico");
        P1.setNote("Emily Holms");
        P1.setImage(R.drawable.pharmacy);
        P1.setPhone("789-456-2135");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");

        Pharmacy P2=new Pharmacy();
        P2.setName("City Medico");
        P2.setNote("Emily Holms");
        P2.setImage(R.drawable.pharmacys);
        P2.setPhone("985-456-2135");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");


        PharmacyList.add(P1);
        PharmacyList.add(P2);*/
          }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddPharmacy:
                preferences.putString(PrefConstants.SOURCE,"Pharmacy");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
                break;
            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Pharmacy.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new com.mindyourelders.MyHealthCareWishes.pdfdesign.Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addusereNameChank("Pharmacies and home medical equipment");//preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);

                /*new Header().createPdfHeader(file.getAbsolutePath(),
                        "Pharmacies and home medical equipment");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                ArrayList<Pharmacy> PharmacyList= PharmacyQuery.fetchAllPharmacyRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                new Specialty(PharmacyList);
                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
String path=Environment.getExternalStorageDirectory()
        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
        + "/Pharmacy.pdf";
                        switch (itemPos) {
                            case 0: // view
                                    StringBuffer result = new StringBuffer();
                                    result.append(new MessageString().getDoctorsInfo());
                                    result.append(new MessageString().getHospitalInfo());
                                    result.append(new MessageString().getPharmacyInfo());
                                    result.append(new MessageString().getAideInfo());
                                    result.append(new MessageString().getFinanceInfo());

                                    new PDFDocumentProcess(Environment.getExternalStorageDirectory()
                                            + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                            + "/Pharmacy.pdf",
                                            getActivity(), result);

                                    System.out.println("\n" + result + "\n");
                            break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Pharmacies And Home Medical Equipment");
                                break;
                            case 2://fax
                                new FaxCustomDialog(getActivity(), path).show();
                                break;
                        }
                    }

                });
                builder.create().show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setListData();
    }
}
