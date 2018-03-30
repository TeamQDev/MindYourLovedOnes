package com.mindyourlovedones.healthcare.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.PetQuery;
import com.mindyourlovedones.healthcare.model.Pet;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;

public class AddPetActivity extends AppCompatActivity {
    Context context = this;
    TextView txtTitle,txtAdd;
    TextView txtName, txtBreed, txtColor, txtChip, txtVeterian, txtCare, txtPetBirthDate, txtPetNotes;
    String name = "", breed = "", color = "", veterain = "", care = "", chip = "", bdate ="", notes ="";
    ImageView imgBack, imgDone;
    public static final int REQUEST_PET = 400;
    boolean isUpdate = false;
    RelativeLayout llAddConn;
    Preferences preferences;
    DBHelper dbHelper;

    Pet pet;

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
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
        PetQuery a = new PetQuery(context, dbHelper);

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        llAddConn= (RelativeLayout) findViewById(R.id.llAddConn);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAdd = (TextView) findViewById(R.id.txtAdd);
        txtName = (TextView) findViewById(R.id.txtName);
        txtBreed = (TextView) findViewById(R.id.txtBreed);
        txtColor = (TextView) findViewById(R.id.txtColor);
        txtChip = (TextView) findViewById(R.id.txtChip);
        txtVeterian = (TextView) findViewById(R.id.txtVeteran);
        txtCare = (TextView) findViewById(R.id.txtCare);
        txtPetBirthDate= (TextView) findViewById(R.id.txtPetBirthDate);
        txtPetNotes= (TextView) findViewById(R.id.txtPetNote);
        Intent i = getIntent();
        if (i.getExtras() != null) {
            if (i.getExtras().get("FROM").equals("Update")) {
                isUpdate = true;
                txtAdd.setText("Update Pet");
                txtTitle.setText("Update Pet");
                Pet p = (Pet) i.getExtras().getSerializable("PetObject");
                pet = p;
                if (p.getName() != null) {
                    txtName.setText(p.getName());
                }
                if (p.getBreed() != null) {
                    txtBreed.setText(p.getBreed());
                }
                if (p.getColor() != null) {
                    txtColor.setText(p.getColor());
                }
                if (p.getVeterian() != null) {
                    txtVeterian.setText(p.getVeterian());
                }
                if (p.getChip() != null) {
                    txtChip.setText(p.getChip());
                }
                if (p.getGuard() != null) {
                    txtCare.setText(p.getGuard());
                }
                if (p.getBdate() != null) {
                    txtPetBirthDate.setText(p.getBdate());
                }
                if (p.getNotes() != null) {
                    txtPetNotes.setText(p.getNotes());
                }
            } else {
                isUpdate = false;
                txtAdd.setText("Add Pet");
                txtTitle.setText("Add Pet");
            }

        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llAddConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtName.getText().toString();
                breed = txtBreed.getText().toString();
                color = txtColor.getText().toString();
                chip = txtChip.getText().toString();
                veterain = txtVeterian.getText().toString();
                care = txtCare.getText().toString();
                bdate= txtPetBirthDate.getText().toString();
                notes=txtPetNotes.getText().toString();
                if (isUpdate == false) {
                    Boolean flag = PetQuery.insertPetData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, breed, color, chip, veterain, care, bdate, notes);
                    if (flag == true) {
                        Toast.makeText(context, "Pet Added Succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                } else if (isUpdate == true) {
                    Boolean flag = PetQuery.updatePetData(pet.getId(), name, breed, color, chip, veterain, care, bdate, notes);
                    if (flag == true) {
                        Toast.makeText(context, "Pet updated Succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }

                }

                Intent intentAllergy = new Intent();
                           /* intentAllergy.putExtra("Value", value);
                            intentAllergy.putExtra("Reaction", reaction);
                            intentAllergy.putExtra("Treatment", treatment);*/
                setResult(AddInfoActivity.RESULT_ALLERGY, intentAllergy);
                finish();

            }
        });
    }
}
