package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

public class ViewEventActivity extends AppCompatActivity implements View.OnClickListener {
 ImageView imgBack;
    EditText etNote;
    TextView txtDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

      //  getData();
        initUI();
        initListener();
        initComponent();
    }

    private void initComponent() {
        Intent intent=getIntent();
        if (intent.getExtras()!=null) {
            String notes = intent.getExtras().getString("Note");
            String dates= intent.getExtras().getString("Date");
            etNote.setText(notes);
            txtDate.setText(dates);
        }
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        etNote= (EditText) findViewById(R.id.etNote);
        txtDate= (TextView) findViewById(R.id.txtDate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBack:
                finish();
                break;
        }
    }
}
