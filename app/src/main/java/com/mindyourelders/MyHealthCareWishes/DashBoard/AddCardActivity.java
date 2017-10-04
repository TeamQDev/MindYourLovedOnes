package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    Context context=this;
    TextView txtName,txttype;
    ImageView imgDone,imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initUI();
        initListener();
    }

    private void initListener() {
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        imgDone= (ImageView)findViewById(R.id.imgDone);
        imgBack= (ImageView) findViewById(R.id.imgBack);

        txtName= (TextView) findViewById(R.id.txtName);
        txttype= (TextView) findViewById(R.id.txtType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgDone:
                break;

            case R.id.imgBack:
                finish();
                break;


        }
    }
}
