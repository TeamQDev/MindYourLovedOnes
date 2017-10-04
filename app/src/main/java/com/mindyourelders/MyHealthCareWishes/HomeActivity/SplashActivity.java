package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    TextView txtNew,txtRegistered;
    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    SliderPagerAdapter sliderPagerAdapter;
    ArrayList<Integer> slider_image_list;
    ArrayList<String> slider_text_list;
    private TextView[] dots;
    int page_position = 0;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        variableInitialization();
        initUI();
        initListener();
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
        }, 100, 5000);



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

        slider_image_list = new ArrayList<>();
        slider_text_list=new ArrayList<>();

//Add few items to slider_image_list ,this should contain url of images which should be displayed in slider
// here i am adding few sample image links, you can add your own

       /* slider_image_list.add("https://2eof2j3oc7is20vt9q3g7tlo5xe-wpengine.netdna-ssl.com/wp-content/uploads/2015/06/healthcloud-370x290.jpg");
        slider_image_list.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiqc6UPf2ObgoOv7_GEVqdJa0Tbwttd9j8B0ur-7QNz2IUGCzMmQ");
        slider_image_list.add("https://previews.123rf.com/images/franckito/franckito1106/franckito110600100/9800425-3D-rendering-of-a-hospital-interior-Stock-Photo-hospital-corridor.jpg");
        slider_image_list.add("http://languagesforlifeltd.com/wp-content/uploads/Medical-document_1.jpg");
*/
        slider_image_list.add(R.drawable.splash_one);
        slider_image_list.add(R.drawable.splash_two);
        slider_image_list.add(R.drawable.splash_three);
        slider_image_list.add(R.drawable.splash_four);

        slider_text_list.add("Access to Critical Information And Health Care Directives 24/7");
        slider_text_list.add("Mobile Tech for Seniors and their Families");
        slider_text_list.add("Manage and share critical documents and information from your phone or tablet");
        slider_text_list.add("Access to Critical Information And Health Care Directives 24/7");



        sliderPagerAdapter = new SliderPagerAdapter(SplashActivity.this, slider_image_list,slider_text_list);
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
    private void variableInitialization() {
        preferences = new Preferences(this);
        //for dashboard webservice call
        preferences.setFirstTimeCall(true);
    }

    private void initListener() {
        txtNew.setOnClickListener(this);
        txtRegistered.setOnClickListener(this);
    }

    private void initUI() {
        txtNew= (TextView) findViewById(R.id.txtNew);
        txtRegistered= (TextView) findViewById(R.id.txtRegistered);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.txtNew:
                Intent intent=new Intent(context,SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.txtRegistered:
                if (preferences == null) {
                    preferences = new Preferences(SplashActivity.this);
                }

                if (preferences.getREGISTERED()) {
                    startActivity(new Intent(SplashActivity.this, BaseActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

                break;
        }
    }
    void show()
    {

    }
}
