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
import com.mindyourelders.MyHealthCareWishes.database.InsuranceQuery;
import com.mindyourelders.MyHealthCareWishes.model.Insurance;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.InsurancePdf;
import com.mindyourelders.MyHealthCareWishes.utility.CallDialog;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by varsha on 8/28/2017.
 */

public class FragmentInsurance extends Fragment implements View.OnClickListener {
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvInsurance;
    ArrayList<Insurance> insuranceList;
    RelativeLayout llAddInsurance;
    Preferences preferences;
    DBHelper dbHelper;
    RelativeLayout rlGuide;
    TextView txtMsg,txtFTU;
    final String dialog_items[]={"View","Email","Fax"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_insurance, null);
        initComponent();
        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper = new DBHelper(getActivity());
        InsuranceQuery i = new InsuranceQuery(getActivity(), dbHelper);
    }

    private void setListData() {
        if (insuranceList.size() != 0) {
            InsuranceAdapter insuranceAdapter = new InsuranceAdapter(getActivity(), insuranceList);
            lvInsurance.setAdapter(insuranceAdapter);
            lvInsurance.setVisibility(View.VISIBLE);
            rlGuide.setVisibility(View.GONE);
        } else {
            lvInsurance.setVisibility(View.GONE);
            rlGuide.setVisibility(View.VISIBLE);
        }
    }

    private void initListener() {
        //  imgADMTick.setOnClickListener(this);
        llAddInsurance.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        txtMsg=rootview.findViewById(R.id.txtMsg);
        String msg="To add information click the green bar at the bottom of the screen. If the person or Company is in your <b>Contacts</b> click the gray bar on the top right side of your screen." +
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
                "either <b>take a photo</b> or grab one from your <b>gallery</b>. To edit or delete the picture click the pencil again.Use the same process to add a business card. It is recommended that you hold your phone horizontal when taking a picture of the business card.";
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
        llAddInsurance = (RelativeLayout) rootview.findViewById(R.id.llAddInsurance);
        lvInsurance = (SwipeMenuListView) rootview.findViewById(R.id.lvInsurance);
        setListData();

        lvInsurance.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvInsurance.setMenuCreator(creator);
        lvInsurance.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Insurance item = insuranceList.get(position);
                switch (index) {
                    case 0:
                        callUser(item);
                        break;
                    case 1:
                        // delete
                        deleteInsurance(item);
                        break;
                }
                return false;
            }
        });
    }
    private void callUser(Insurance item) {
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
        insuranceList = InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* insuranceList=new ArrayList<>();

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



        insuranceList.add(P1);
        insuranceList.add(P2);
        insuranceList.add(P3);
        insuranceList.add(P4);
        insuranceList.add(P5);

*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddInsurance:
                preferences.putString(PrefConstants.SOURCE, "Insurance");
                Intent i = new Intent(getActivity(), GrabConnectionActivity.class);
                startActivity(i);
                break;
            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Insurance.pdf");
                if (file.exists()) {
                    file.delete();
                }

                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Header.addusereNameChank("Insurance Information");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Insurance Information");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                // Header.addEmptyLine(2);*/

                ArrayList<Insurance> insuranceList= InsuranceQuery.fetchAllInsuranceRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                new InsurancePdf(insuranceList);
                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
String path=Environment.getExternalStorageDirectory()
        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
        + "/Insurance.pdf";
                        switch (itemPos) {
                            case 0: // view
                                StringBuffer result = new StringBuffer();
                                result.append(new MessageString().getInsuranceInfo());


                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");
                                break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Insurance Information");
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
