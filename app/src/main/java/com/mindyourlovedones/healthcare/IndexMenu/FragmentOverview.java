package com.mindyourlovedones.healthcare.IndexMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.model.Proxy;

import java.util.ArrayList;

/**
 * Created by varsha on 8/23/2017.
 */

public class FragmentOverview extends Fragment implements View.OnClickListener{
    View rootview;
    ListView lvProxy;
    ArrayList<Proxy> proxyList;
    ImageView imgADMTick,imgDNRTick,imgPOLSTTick,imgGiftTick,imgPhoneTick;
    boolean admFlag,dnrFlag,polstFlag,giftFlag,phoneFlag;
    LinearLayout llProxy;
    LayoutInflater layoutInflater;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_overview,null);
        getData();
        initUI();
        initListener();
        setProxy();
        return rootview;
    }

    private void setProxy() {

        for (int i=0;i<proxyList.size();i++) {
            View proxyView = layoutInflater.inflate(R.layout.row_proxy, null);
            llProxy.addView(proxyView);
            TextView txtName= (TextView) proxyView.findViewById(R.id.txtName);
            TextView txtAddress= (TextView) proxyView.findViewById(R.id.txtAddress);
            TextView txtPhone= (TextView) proxyView.findViewById(R.id.txtPhone);
            TextView txtType= (TextView) proxyView.findViewById(R.id.txtType);
            ImageView imgProfile= (ImageView) proxyView.findViewById(R.id.imgProfile);

           txtName.setText(proxyList.get(i).getName());
           txtAddress.setText(proxyList.get(i).getAddress());
           txtPhone.setText(proxyList.get(i).getPhone());
           txtType.setText(proxyList.get(i).getRelationType());

        }
    }

    private void initListener() {
        imgADMTick.setOnClickListener(this);
        imgDNRTick.setOnClickListener(this);
        imgPOLSTTick.setOnClickListener(this);
        imgGiftTick.setOnClickListener(this);
        imgPhoneTick.setOnClickListener(this);
    }

    private void initUI() {
        layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        imgADMTick= (ImageView) rootview.findViewById(R.id.imgADMTick);
        imgDNRTick= (ImageView) rootview.findViewById(R.id.imgDNRTick);
        imgPOLSTTick= (ImageView) rootview.findViewById(R.id.imgPOLSTTick);
        imgGiftTick= (ImageView) rootview.findViewById(R.id.imgGiftTick);
        imgPhoneTick= (ImageView) rootview.findViewById(R.id.imgPhoneTick);
        llProxy= (LinearLayout) rootview.findViewById(R.id.llProxy);

      /*  lvProxy= (ListView) rootview.findViewById(R.id.lvProxy);

        ProxyAdapter proxyAdapter=new ProxyAdapter(getActivity(),proxyList);
        lvProxy.setAdapter(proxyAdapter);*/
    }

    private void getData() {
        proxyList=new ArrayList<>();

        Proxy P1=new Proxy();
        P1.setName("Caiete Charlo");
        P1.setRelationType("Primary Proxy");
        P1.setAddress("#203,10 Downing Street, los Angeles, California.");
        P1.setPhone("900-203-2244");

        Proxy P2=new Proxy();
        P2.setName("Chuck Charlo");
        P2.setRelationType("Successor Proxy");
        P2.setAddress("#203,10 Downing Street, los Angeles, California.");
        P2.setPhone("900-203-2244");

        proxyList.add(P1);
        proxyList.add(P2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgADMTick:
                if(admFlag==false)
                {
                    imgADMTick.setImageResource(R.drawable.ic_tick);
                    admFlag=true;
                }
               else if (admFlag==true)
               {
                   imgADMTick.setImageResource(R.drawable.ic_untick);
                   admFlag=false;
               }

                break;
            case R.id.imgDNRTick:
                if(dnrFlag==false)
                {
                    imgDNRTick.setImageResource(R.drawable.ic_tick);
                    dnrFlag=true;
                }
                else if (dnrFlag==true)
                {
                    imgDNRTick.setImageResource(R.drawable.ic_untick);
                    dnrFlag=false;
                }
                break;
            case R.id.imgPOLSTTick:
                if(polstFlag==false)
                {
                    imgPOLSTTick.setImageResource(R.drawable.ic_tick);
                    polstFlag=true;
                }
                else if (polstFlag==true)
                {
                    imgPOLSTTick.setImageResource(R.drawable.ic_untick);
                    polstFlag=false;
                }
                break;
            case R.id.imgGiftTick:
                if(giftFlag==false)
                {
                    imgGiftTick.setImageResource(R.drawable.ic_tick);
                    giftFlag=true;
                }
                else if (giftFlag==true)
                {
                    imgGiftTick.setImageResource(R.drawable.ic_untick);
                    giftFlag=false;
                }
                break;
            case R.id.imgPhoneTick:
                if(phoneFlag==false)
                {
                    imgPhoneTick.setImageResource(R.drawable.ic_tick);
                    phoneFlag=true;
                }
                else if (phoneFlag==true)
                {
                    imgPhoneTick.setImageResource(R.drawable.ic_untick);
                    phoneFlag=false;
                }
                break;

        }
    }
}
