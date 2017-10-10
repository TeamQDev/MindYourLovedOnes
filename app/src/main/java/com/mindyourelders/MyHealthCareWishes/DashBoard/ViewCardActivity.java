package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.SliderPagerAdapter;
import com.mindyourelders.MyHealthCareWishes.model.Card;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewCardActivity extends AppCompatActivity {
    Context context=this;
    TextView txtNew,txtRegistered,txtProviderValue,txtTypeValue;
    private ViewPager vp_slider;
    ImageView imageBack;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<byte[]> slider_image_list;
    ArrayList<String> slider_text_list=new ArrayList<>();
    private TextView[] dots;
    int page_position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_card);
        initUI();
        initListener();
        initComponent();
        init();

// method for adding indicators
        addBottomDots(0);

        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 2000);

    }

    private void initComponent() {
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            Card card= (Card) i.getSerializableExtra("Card");
            byte[] front=card.getImgFront();
            byte[] back=card.getImgBack();
           String provider=card.getName();
            String type=card.getType();

            txtProviderValue.setText(provider);
            txtTypeValue.setText(type);
            slider_image_list = new ArrayList<>();
           slider_image_list.add(front);
            slider_image_list.add(back);

        }
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#F0F0F0"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#0DA8D8"));
    }


    private void init() {
        vp_slider = (ViewPager) findViewById(R.id.vp_slider);
        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);
   getImages();
               sliderPagerAdapter = new SliderPagerAdapter(ViewCardActivity.this, slider_image_list, slider_text_list);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getImages() {

    }

    private void initListener() {

    }

    private void initUI() {
imageBack= (ImageView) findViewById(R.id.imgBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtTypeValue= (TextView) findViewById(R.id.txtTypeValue);
        txtProviderValue= (TextView) findViewById(R.id.txtProviderValue);
    }



}
