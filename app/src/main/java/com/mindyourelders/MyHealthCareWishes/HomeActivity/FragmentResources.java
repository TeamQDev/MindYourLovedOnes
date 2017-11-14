package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.model.Links;

import java.util.ArrayList;

/**
 * Created by welcome on 11/14/2017.
 */

public class FragmentResources extends Fragment {

View rootview;
    ArrayList<String> Datalist;
    ArrayList<Links> UrlList;
    ListView list;
    TextView txtTitle,txtName;
    ImageView imgNoti,imgProfile,imgLogo,imgPdf;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_resources, null);

        initUI();
        initListener();
        getData();
        setData();
        return rootview;
    }

    private void setData() {
        ArrayAdapter adapter=new ArrayAdapter(getActivity(),R.layout.row_resources,R.id.txtName,Datalist);
        list.setAdapter(adapter);
    }

    private void getData() {
        UrlList=new ArrayList<>();
        Links l1=new Links();
        l1.setName("ABA - Elder Law");
        l1.setUrl("https://www.americanbar.org/groups/senior_lawyers/elder_law.html");
        UrlList.add(l1);

        //Fol show
        Datalist=new ArrayList<>();
        Datalist.add("ABA - Elder Law");
    }

    private void initListener() {

    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("RESOURCES");
        imgPdf= (ImageView) getActivity().findViewById(R.id.imgPdf);
        imgPdf.setVisibility(View.GONE);
        imgProfile= (ImageView) getActivity().findViewById(R.id.imgProfile);
        txtName= (TextView) getActivity().findViewById(R.id.txtName);
        txtName.setVisibility(View.GONE);
        imgProfile.setVisibility(View.GONE);
        imgNoti = (ImageView) getActivity().findViewById(R.id.imgNoti);
        imgNoti.setVisibility(View.GONE);
        imgLogo = (ImageView) getActivity().findViewById(R.id.imgLogo);
        imgLogo.setVisibility(View.GONE);



        list= (ListView) rootview.findViewById(R.id.list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       if (Datalist.get(position).equals(UrlList.get(position).getName()))
                       {
                           Intent intent = new Intent();
                           intent.setAction(Intent.ACTION_VIEW);
                           intent.addCategory(Intent.CATEGORY_BROWSABLE);
                           intent.setData(Uri.parse(UrlList.get(position).getUrl()));
                           startActivity(intent);
                       }
            }
        });
    }


}
