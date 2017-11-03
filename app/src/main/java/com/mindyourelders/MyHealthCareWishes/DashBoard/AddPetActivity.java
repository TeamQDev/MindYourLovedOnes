package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.PetQuery;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import static com.mindyourelders.MyHealthCareWishes.DashBoard.AddInfoActivity.RESULT_ALLERGY;

public class AddPetActivity extends AppCompatActivity {
    Context context=this;
    TextView txtName,txtBreed,txtColor,txtChip,txtVeterian,txtCare;
  String name="",breed="",color="",veterain="",care="",chip="";
    ImageView imgBack,imgDone;
    public static final int REQUEST_PET= 400;

    Preferences preferences;
    DBHelper dbHelper;
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
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        PetQuery a=new PetQuery(context,dbHelper);

      imgBack= (ImageView) findViewById(R.id.imgBack);
        imgDone= (ImageView) findViewById(R.id.imgDone);
        txtName= (TextView) findViewById(R.id.txtName);
        txtBreed= (TextView) findViewById(R.id.txtBreed);
        txtColor= (TextView) findViewById(R.id.txtColor);
        txtChip= (TextView) findViewById(R.id.txtChip);
        txtVeterian= (TextView) findViewById(R.id.txtVeteran);
        txtCare= (TextView) findViewById(R.id.txtCare);


        imgDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=txtName.getText().toString();
                breed=txtBreed.getText().toString();
                color=txtColor.getText().toString();
                chip=txtChip.getText().toString();
                veterain=txtVeterian.getText().toString();
                care=txtCare.getText().toString();

                Boolean flag = PetQuery.insertPetData(preferences.getInt(PrefConstants.CONNECTED_USERID),name,breed,color,chip,veterain,care);
                if (flag == true) {
                    Toast.makeText(context, "Pet Added Succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }

                Intent intentAllergy = new Intent();
                           /* intentAllergy.putExtra("Value", value);
                            intentAllergy.putExtra("Reaction", reaction);
                            intentAllergy.putExtra("Treatment", treatment);*/
                setResult(RESULT_ALLERGY, intentAllergy);
                finish();

            }
        });
    }
}
