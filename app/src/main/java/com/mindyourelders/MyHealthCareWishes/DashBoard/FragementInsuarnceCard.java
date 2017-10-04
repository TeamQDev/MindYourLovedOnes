package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.model.Card;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.imgBack;

/**
 * Created by welcome on 9/22/2017.
 */

public class FragementInsuarnceCard extends Fragment implements View.OnClickListener{
Preferences preferences;
    View rootview;
    ListView lvCard;

    ArrayList<Card> CardList;
    RelativeLayout llAddCard;
    TextView txtView;
    public static final int REQUEST_PRES=100;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_insurance_card, null);
        preferences=new Preferences(getActivity());
        getData();
        initUI();
        initListener();
        setCardData();
        return rootview;
    }

    private void getData() {
        CardList=new ArrayList<>();

        Card c1=new Card();
        c1.setName("Jackson Montana");
        c1.setType("Supplemental Insurance");
        c1.setImgFront(R.drawable.front);
        c1.setImgBack(R.drawable.back);

        Card c2=new Card();
        c2.setName("James Johnson");
        c2.setType("Medicare");
        c2.setImgFront(R.drawable.fronts);
        c2.setImgBack(R.drawable.backs);

        CardList.add(c1);
        CardList.add(c2);
    }

    private void initListener() {
        llAddCard.setOnClickListener(this);

    }

    private void initUI() {
        llAddCard= (RelativeLayout) rootview.findViewById(R.id.llAddCard);
        lvCard= (ListView)rootview.findViewById(R.id.lvCard);
        txtView= (TextView) rootview.findViewById(R.id.txtView);
    }

    private void setCardData() {
       /* if (CardList.size()!=0)
        {
            lvCard.setVisibility(View.VISIBLE);
            txtView.setVisibility(View.GONE);
        }else{
            txtView.setVisibility(View.VISIBLE);
            lvCard.setVisibility(View.GONE);
        }*/
        lvCard.setVisibility(View.VISIBLE);
        CardAdapter adapter=new CardAdapter(getActivity(),CardList);
        lvCard.setAdapter(adapter);

        lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final ImageView imgFront= (ImageView) view.findViewById(R.id.imgFront);
                final ImageView imgPre= (ImageView) view.findViewById(imgBack);
                final ImageView imgCard= (ImageView) view.findViewById(R.id.imgCard);
                ImageView imgForword= (ImageView) view.findViewById(R.id.imgForword);
                imgFront.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgCard.setImageResource(CardList.get(position).getImgFront());
                        imgPre.setImageResource(R.drawable.white_dot);
                        imgFront.setImageResource(R.drawable.blue_dot);
                    }
                });
                imgPre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgCard.setImageResource(CardList.get(position).getImgBack());
                        imgPre.setImageResource(R.drawable.blue_dot);
                        imgFront.setImageResource(R.drawable.white_dot);
                    }
                });

                imgForword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(getActivity(),ViewCardActivity.class);
                        i.putExtra("Card",CardList.get(position));
                        startActivity(i);
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llAddCard:
                // preferences.putString(PrefConstants.SOURCE,"Card");
                Intent i=new Intent(getActivity(),AddCardActivity.class);
                startActivityForResult(i,REQUEST_PRES);
                break;
        }
    }
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PRES && data != null) {

      *//*  Card p=new Card();
        p.setDates(data.getExtras().getString("Date"));
        p.setDoctor(data.getExtras().getString("Name"));
        p.setDosageList((ArrayList<Dosage>)data.getExtras().getSerializable("Dosage"));
        p.setCardImageList((ArrayList<PrescribeImage>) data.getExtras().getSerializable("Image"));*//*

            CardList.add((Card) data.getSerializableExtra("PrObj"));
            setCardData();
        }

    }*/

}
