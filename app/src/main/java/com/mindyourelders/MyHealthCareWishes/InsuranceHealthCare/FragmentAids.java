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
import com.mindyourelders.MyHealthCareWishes.database.AideQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.model.Aides;
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

public class FragmentAids extends Fragment implements View.OnClickListener{
    ImageView imgRight;
    View rootview;
    SwipeMenuListView lvAides;
    ArrayList<Aides> AidesList;
    RelativeLayout llAddAides;
    Preferences preferences;
DBHelper dbHelper;
    final String dialog_items[]={"View","Email","Fax"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_aides,null);
        initComponent();

        getData();
        initUI();
        initListener();

        return rootview;
    }

    private void initComponent() {
        preferences = new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        AideQuery a=new AideQuery(getActivity(),dbHelper);
    }

    private void setListData() {
        if (AidesList.size()!=0) {
        AidesAdapter aidesAdapter=new AidesAdapter(getActivity(),AidesList);
        lvAides.setAdapter(aidesAdapter);
            lvAides.setVisibility(View.VISIBLE);
    }
        else{
            lvAides.setVisibility(View.GONE);
    }
    }

    private void initListener() {
        llAddAides.setOnClickListener(this);
        imgRight.setOnClickListener(this);
    }

    private void initUI() {
        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        // imgADMTick= (ImageView) rootview.findViewById(imgADMTick);
        llAddAides= (RelativeLayout) rootview.findViewById(R.id.llAddAides);
        lvAides = (SwipeMenuListView) rootview.findViewById(R.id.lvAides);
        setListData();

        lvAides.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvAides.setMenuCreator(creator);
        lvAides.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Aides item = AidesList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        callUser(item);
                        break;
                    case 1:
                        // delete
                        deleteAides(item);
                        break;
                }
                return false;
            }
        });
    }
    private void callUser(Aides item) {
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
    private void deleteAides(Aides item) {
        boolean flag= AideQuery.deleteRecord(item.getId());
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setListData();
        }
    }

    private void getData() {
        AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* AidesList=new ArrayList<>();

        Aides P1=new Aides();
        P1.setFirm("Home Instead Senior Care");
        P1.setAidName("Emily Holms");
        P1.setAddress("799 E DRAGRAM SUITE 5A,TUCSON AZ 85705, USA");
        P1.setImage(R.drawable.maids);
        P1.setPhone("589-789-5236");


        Aides P2=new Aides();
        P2.setFirm("American Senior Communities");
        P2.setAidName("Ava Watson");
        P2.setAddress("300 BOYLSTON AVE E, SEATTLE WA 98102, USA");
        P2.setImage(R.drawable.maidss);
        P2.setPhone("366-789-5236");

        Aides P3=new Aides();
        P3.setFirm("Community Health Network ");
        P3.setAidName("Ashley Sheridon");
        P3.setAddress("200 E MAIN ST, PHOENIX AZ 85123, USA");
        P3.setImage(R.drawable.maidsss);
        P3.setPhone("986-789-5236");


        AidesList.add(P1);
        AidesList.add(P2);
        AidesList.add(P3);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.llAddAides:
                preferences.putString(PrefConstants.SOURCE,"Aides");
                Intent i=new Intent(getActivity(),GrabConnectionActivity.class);
                startActivity(i);
                break;
            case R.id.imgRight:
                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "Aides.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new com.mindyourelders.MyHealthCareWishes.pdfdesign.Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addusereNameChank("Home health services");//preferences.getString(PrefConstants.CONNECTED_NAME));
                com.mindyourelders.MyHealthCareWishes.pdfdesign.Header.addEmptyLine(1);

               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Home health services");

                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/

                ArrayList<Aides> AidesList= AideQuery.fetchAllAideRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                new Specialty(AidesList,1);
                Header.document.close();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
String path=Environment.getExternalStorageDirectory()
        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
        + "/Aides.pdf";
                        switch (itemPos) {

                            case 0: // view
                                    StringBuffer result = new StringBuffer();

                                    result.append(new MessageString().getAideInfo());

                                    new PDFDocumentProcess(path,
                                            getActivity(), result);

                                    System.out.println("\n" + result + "\n");
                            break;
                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Home Health Service");
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
