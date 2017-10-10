package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.CardQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.model.Card;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.utility.SwipeMenuCreation;

import java.util.ArrayList;

import static com.mindyourelders.MyHealthCareWishes.HomeActivity.R.id.imgBack;

/**
 * Created by welcome on 9/22/2017.
 */

public class FragementInsuarnceCard extends Fragment implements View.OnClickListener{
Preferences preferences;
    View rootview;
    SwipeMenuListView lvCard;

    ArrayList<Card> CardList;
    RelativeLayout llAddCard;
    TextView txtView;
    public static final int REQUEST_PRES=100;
    DBHelper dbHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_insurance_card, null);
      initComponent();
        getData();
        initUI();
        initListener();
        setCardData();
        return rootview;
    }

    private void initComponent() {
        preferences=new Preferences(getActivity());
        dbHelper=new DBHelper(getActivity());
        CardQuery c=new CardQuery(getActivity(),dbHelper);
    }

    private void getData() {
        CardList= CardQuery.fetchAllCardRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
       /* CardList=new ArrayList<>();

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
        CardList.add(c2);*/
    }

    private void initListener() {
        llAddCard.setOnClickListener(this);

    }

    private void initUI() {
        llAddCard= (RelativeLayout) rootview.findViewById(R.id.llAddCard);
        lvCard= (SwipeMenuListView) rootview.findViewById(R.id.lvCard);
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
        if (CardList.size()!=0) {
            lvCard.setVisibility(View.VISIBLE);
            CardAdapter adapter = new CardAdapter(getActivity(), CardList);
            lvCard.setAdapter(adapter);

            lvCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    final ImageView imgFront = (ImageView) view.findViewById(R.id.imgFront);
                    final ImageView imgPre = (ImageView) view.findViewById(imgBack);
                    final ImageView imgCard = (ImageView) view.findViewById(R.id.imgCard);
                    ImageView imgForword = (ImageView) view.findViewById(R.id.imgForword);
                    imgFront.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            byte[] photo=CardList.get(position).getImgFront();
                            Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                            imgCard.setImageBitmap(bmp);
                            imgPre.setImageResource(R.drawable.white_dot);
                            imgFront.setImageResource(R.drawable.blue_dot);
                        }
                    });
                    imgPre.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            byte[] photo1=CardList.get(position).getImgBack();
                            Bitmap bmp1 = BitmapFactory.decodeByteArray(photo1, 0, photo1.length);
                            imgCard.setImageBitmap(bmp1);
                            imgPre.setImageResource(R.drawable.blue_dot);
                            imgFront.setImageResource(R.drawable.white_dot);
                        }
                    });

                    imgForword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getActivity(), ViewCardActivity.class);
                            i.putExtra("Card", CardList.get(position));
                            startActivity(i);
                        }
                    });
                }
            });
        } else{
            lvCard.setVisibility(View.GONE);
        }


        lvCard.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        SwipeMenuCreation s=new SwipeMenuCreation();
        SwipeMenuCreator creator=s.createMenu(getActivity());
        lvCard.setMenuCreator(creator);
        lvCard.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Card item = CardList.get(position);
                switch (index) {
                    case 0:
                        // open
                        //  open(item);
                        break;
                    case 1:
                        // delete
                        deleteInsuranceCard(item);
                        break;
                }
                return false;
            }
        });
    }

    private void deleteInsuranceCard(Card item) {
        boolean flag= CardQuery.deleteRecord(item.getId(), 1);
        if(flag==true)
        {
            Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
            getData();
            setCardData();
        }
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

    @Override
    public void onResume() {
        super.onResume();
        getData();
        setCardData();
    }
}
