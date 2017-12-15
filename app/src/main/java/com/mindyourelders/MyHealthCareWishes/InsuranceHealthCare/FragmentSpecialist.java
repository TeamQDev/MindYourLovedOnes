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
import com.mindyourelders.MyHealthCareWishes.database.SpecialistQuery;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;
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
 * Created by varsha on 8/28/2017.
 */

public class FragmentSpecialist extends Fragment implements View.OnClickListener{
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvSpecialist;
    ArrayList<Specialist> specialistList;
    RelativeLayout llAddSpecialist;
    Preferences preferences;
    DBHelper dbHelper;
    final String dialog_items[]={"View","Email","Fax"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_specialist,null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        SpecialistQuery s=new SpecialistQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (specialistList.size()!=0) {
            SpecialistAdapter specialistAdapter = new SpecialistAdapter(getActivity(), specialistList);
            lvSpecialist.setAdapter(specialistAdapter);
            lvSpecialist.setVisibility(View.VISIBLE);
        }
        else{
            lvSpecialist.setVisibility(View.GONE);
        }

    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddSpecialist.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {

        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddSpecialist= (RelativeLayout) rootview.findViewById(R.id.llAddSpecialist);
        lvSpecialist= (SwipeMenuListView) rootview.findViewById(R.id.lvSpecialist);
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
    private void deleteSpecialist(Specialist item) {
        boolean flag= SpecialistQuery.deleteRecord(item.getId(),2);
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }
    private void getData() {
        specialistList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
     //   specialistList=new ArrayList<>();

        /*Specialist P1=new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setPhone("789-789-5236");


        Specialist P2=new Specialist();
        P2.setName("Dr. James");
        P2.setType("Neuro Surgeon");
        P2.setAddress("#204,10 upper Street, los Angeles, California.");
        P2.setImage(R.drawable.docto);
        P2.setPhone("987-789-5236");

        Specialist P3=new Specialist();
        P3.setName("Dr. Smith");
        P3.setType("Neuro Surgeon");
        P3.setAddress("#205,10 Left Street, los Angeles, California.");
        P3.setImage(R.drawable.doctors);
        P3.setPhone("789-789-5236");
*/
      /*  specialistList.add(P1);
        specialistList.add(P2);
        specialistList.add(P3);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddSpecialist:
                preferences.putString(PrefConstants.SOURCE,"Speciality");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
               // DialogManager dialogManager=new DialogManager(new FragmentSpecialist());
               // dialogManager.showCommonDialog("Add?","Do you want to add new specialist?",getActivity(),"ADD_SPECIALIST",null);
                break;
            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Doctor.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new com.mindyourelders.MyHealthCareWishes.pdfdesign.Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addusereNameChank("Doctor");//preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Doctor");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                ArrayList<Specialist> specialistsList= SpecialistQuery.fetchAllPhysicianRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),2);
                new Specialty(specialistsList,"Doctors");
                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
String path=Environment.getExternalStorageDirectory()
        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
        + "/Doctor.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getDoctorsInfo());

                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");

                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Doctors");
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
