package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

public class AddPetActivity extends AppCompatActivity {
    Context context=this;
    TextView txtName,txtBreed,txtColor,txtChip,txtVeterian,txtCare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        initUi();
        initListener();
    }

    private void initListener() {

    }

    private void initUi() {

    }
}
