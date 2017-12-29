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
        LinkAdapter adapter=new LinkAdapter(getActivity(),UrlList);
        list.setAdapter(adapter);
    }

    private void getData() {
        UrlList=new ArrayList<>();
        Links l1=new Links();
        l1.setName("ABA - Elder Law");
        l1.setUrl("https://www.americanbar.org/groups/senior_lawyers/elder_law.html");
        l1.setImage(R.drawable.aba_market);

        Links l2=new Links();
        l2.setName("Caring Info - all 50 states ");
        l2.setUrl("http://www.caringinfo.org/i4a/pages/index.cfm?pageid=1");
        l2.setImage(R.drawable.aba_market);

        Links l3=new Links();
        l3.setName("Aging with Dignity - all 50 states");
        l3.setUrl("https://www.agingwithdignity.org/");
        l3.setImage(R.drawable.aba_market);

        Links l4=new Links();
        l4.setName("AARP links to Advance Directive Forms by State (PDF)");
        l4.setUrl("https://www.aarp.org/caregiving/financial-legal/free-printable-advance-directives/");
        l4.setImage(R.drawable.aba_market);

        Links l5=new Links();
        l5.setName("Aging with Dignity, Five Wishes");
        l5.setUrl("https://www.agingwithdignity.org/");
        l5.setImage(R.drawable.aba_market);

        Links l6=new Links();
        l6.setName("American Health Lawyers Association, Loving Conversations");
        l6.setUrl("https://www.healthlawyers.org/Pages/PageNotFoundError.aspx?requestUrl=https://www.healthlawyers.org/hlresources/PI/InfoSeries/Pages/LovingConversations.aspx");
        l6.setImage(R.drawable.aba_market);

        Links l7=new Links();
        l7.setName("American Hospital Association, Put It In Writing");
        l7.setUrl("http://www.aha.org/advocacy-issues/initiatives/piiw/index.shtml");
        l7.setImage(R.drawable.aba_market);

        Links l8=new Links();
        l8.setName("Caring Connections links to Advance Directive Forms by State (PDF)");
        l8.setUrl("http://www.caringinfo.org/i4a/pages/index.cfm?pageid=3289");
        l8.setImage(R.drawable.aba_market);

        Links l9=new Links();
        l9.setName("Center for Practical Bioethics, Caring Conversations");
        l9.setUrl("http://practicalbioethics.org/documents/caring-conversations/Caring-Conversations.pdf");
        l9.setImage(R.drawable.aba_market);

        Links l10=new Links();
        l10.setName("National Healthcare Decisions Day, Learning more about advance directives");
        l10.setUrl("https://www.nhdd.org/learn-more/");
        l10.setImage(R.drawable.aba_market);

        Links l11=new Links();
        l11.setName("The Commission on Law and Aging, ABA,Simple Health Care Power of Attorney form");
        l11.setUrl("https://www.americanbar.org/content/dam/aba/uncategorized/2011/2011_aging_hcdec_univhcpaform_4_2012_v2.authcheckdam.pdf");
        l11.setImage(R.drawable.aba_market);

        Links l12=new Links();
        l12.setName("The Commission on Law and Aging, ABA, Consumer’s Tool Kit for Advance Planning");
        l12.setUrl("https://www.americanbar.org/content/dam/aba/uncategorized/2011/2011_aging_bk_consumer_tool_kit_bk.authcheckdam.pdf");
        l12.setImage(R.drawable.aba_market);

        UrlList.add(l1);
        UrlList.add(l2);
        UrlList.add(l3);
        UrlList.add(l4);
        UrlList.add(l5);
        UrlList.add(l6);
        UrlList.add(l7);
        UrlList.add(l8);
        UrlList.add(l9);
        UrlList.add(l10);
        UrlList.add(l11);
        UrlList.add(l12);
        //Fol show
       // Datalist=new ArrayList<>();
        //Datalist.add("ABA - Elder Law");
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
                     /*  if (Datalist.get(position).equals(UrlList.get(position).getName()))
                       {*/
                           Intent intent = new Intent();
                           intent.setAction(Intent.ACTION_VIEW);
                           intent.addCategory(Intent.CATEGORY_BROWSABLE);
                           intent.setData(Uri.parse(UrlList.get(position).getUrl()));
                           startActivity(intent);
                   //    }
            }
        });
    }
}
