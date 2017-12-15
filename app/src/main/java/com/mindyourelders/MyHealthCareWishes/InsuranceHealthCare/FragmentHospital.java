package com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.Connections.GrabConnectionActivity;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.HospitalHealthQuery;
import com.mindyourelders.MyHealthCareWishes.model.Hospital;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Specialty;
import com.mindyourelders.MyHealthCareWishes.utility.CallDialog;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

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
        dbHelper = new DBHelper(getActivity());
        HospitalHealthQuery f = new HospitalHealthQuery(getActivity(), dbHelper);
    }

    private void setListData() {
        if (hospitalList.size() != 0) {
            HospitalAdapter hospitalAdapter = new HospitalAdapter(getActivity(), hospitalList);
            lvHospital.setAdapter(hospitalAdapter);
            lvHospital.setVisibility(View.VISIBLE);
        } else {
            lvHospital.setVisibility(View.GONE);
        }
    }


    private void initListener() {
        llAddHospital.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        imgRight = (ImageView) getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
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

    private void deleteHospital(Hospital item) {
        boolean flag = HospitalHealthQuery.deleteRecord(item.getId());
        if (flag == true) {
            Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
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
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Hospital.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new com.mindyourelders.MyHealthCareWishes.pdfdesign.Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addusereNameChank("Hospitals and other health professionals");//preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);

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
        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
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


