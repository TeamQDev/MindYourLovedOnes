package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentIndividualContact extends Fragment implements View.OnClickListener{
   
    TextView txtSignUp, txtLogin, txtForgotPassword;
    ImageView imgEdit,imgProfile,imgDone;
    TextView txtTitle, txtName, txtEmail,txtAddress, txtCountry, txtPhone,txtHomePhone,txtWorkPhone, txtBdate,txtGender, txtPassword,txtRelation;

    String name, email, phone, country, bdate,address,homePhone,workPhone,gender;

    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;

    MySpinner spinner,spinnerRelation;
    String[] countryList = {"Canada", "Mexico", "USA", "UK", "california", "India"};

    String imagepath = "";//
    String relation;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    DBHelper dbHelper;
    View rootview;
    Preferences preferences;
    ImageView imgBack;
    RelativeConnection connection;
    PersonalInfo personalInfo;

    TextInputLayout tilBdate;
    String[] Relationship = {"Mother", "Father", "Wife", "Husband", "Daughter", "Son", "Sister", "Brother", "Friend", "GrandFather", "GrandMother", "GrandSon", "GrandDaughter", "Other"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_individual_contact,null);
        preferences = new Preferences(getActivity());
        initComponent();
        initUI();
        initListener();
       
        return rootview;
    }

    private void initComponent() {
        dbHelper=new DBHelper(getActivity());
        PersonalInfoQuery s=new PersonalInfoQuery(getActivity(),dbHelper);
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL)))
        {
            personalInfo = PersonalInfoQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL));
        }
        else {
            connection = MyConnectionsQuery.fetchEmailRecord(preferences.getString(PrefConstants.CONNECTED_USEREMAIL));
        }
    }


    private void initListener() {
        imgBack.setOnClickListener(this);
        txtBdate.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        txtGender.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("Individual Contact");
        imgProfile= (ImageView)rootview.findViewById(R.id.imgProfile);
        txtSignUp = (TextView) rootview.findViewById(R.id.txtSignUp);
        txtLogin = (TextView) rootview.findViewById(R.id.txtLogin);
        txtForgotPassword = (TextView) rootview.findViewById(R.id.txtForgotPassword);
        txtBdate = (TextView)rootview.findViewById(R.id.txtBdate);
        txtGender= (TextView)rootview.findViewById(R.id.txtGender);
        imgBack = (ImageView)getActivity().findViewById(R.id.imgBack);
        imgEdit = (ImageView) rootview.findViewById(R.id.imgEdit);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);
        txtRelation=(TextView) rootview.findViewById(R.id.txtRelation);
        tilBdate= (TextInputLayout) rootview.findViewById(R.id.tilBdate);
        spinnerRelation= (MySpinner) rootview.findViewById(R.id.spinnerRelation);
        txtAddress= (TextView)rootview.findViewById(R.id.txtAddress);
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtEmail = (TextView) rootview.findViewById(R.id.txtEmail);
        txtCountry = (TextView) rootview.findViewById(R.id.txtCountry);
        txtPhone = (TextView) rootview.findViewById(R.id.txtPhone);
        txtHomePhone = (TextView) rootview.findViewById(R.id.txtHomePhone);
        txtWorkPhone = (TextView) rootview.findViewById(R.id.txtWorkPhone);

        spinner = (MySpinner) rootview.findViewById(R.id.spinner);
        
        
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Country");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relation");

        txtPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtPhone.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 3 || length == 7)) {
                    editable.append("-");
                }
            }
        });


        setValues();

    }

    private void setValues() {
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
            tilBdate.setVisibility(View.VISIBLE);
           // spinner.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.GONE);
            txtWorkPhone.setVisibility(View.GONE);
            txtHomePhone.setVisibility(View.VISIBLE);
            if (personalInfo != null) {

                txtGender.setText(personalInfo.getGender());
                txtName.setText(personalInfo.getName());
                txtEmail.setText(personalInfo.getEmail());
                txtAddress.setText(personalInfo.getAddress());
                txtHomePhone.setText(personalInfo.getHomePhone());
/*
                if (personalInfo.getCountry()!=null) {
                    int index = 0;
                    for (int i = 0; i < countryList.length; i++) {
                        if (personalInfo.getCountry().equals(countryList[i])) {
                            index = i;
                        }
                    }
                    spinner.setSelection(index);
                }
                else{
                    spinner.setHint("Country");
                }*/
                txtPhone.setText(personalInfo.getPhone());
                txtBdate.setText(personalInfo.getDob());
                byte[] photo=personalInfo.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
               imgProfile.setImageBitmap(bmp);
            }
        }
        else{
            tilBdate.setVisibility(View.GONE);
           // spinner.setVisibility(View.GONE);
            txtWorkPhone.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.GONE);
            if (connection != null) {
                txtName.setText(connection.getName());
                txtEmail.setText(connection.getEmail());
                txtAddress.setText(connection.getAddress());
                txtPhone.setText(connection.getMobile());
                txtHomePhone.setText(connection.getPhone());
                txtWorkPhone.setText(connection.getWorkPhone());
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (connection.getRelationType().equalsIgnoreCase(Relationship[i])) {
                            index = i;
                        }
                    }
                    spinnerRelation.setSelection(index+1);
                byte[] photo=connection.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
                imgProfile.setImageBitmap(bmp);
            }
            }
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgDone:

                    Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] photo = baos.toByteArray();
                    if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
                        if (validateUser()) {
                            Boolean flag = PersonalInfoQuery.updatePersonalInfoData(preferences.getInt(PrefConstants.USER_ID), name, email, address, country, phone, bdate, photo,homePhone,gender);
                            if (flag == true) {
                                Toast.makeText(getActivity(), "You have updated Successfully", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                                editToConnection(photo);
                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        if (validateConnection())
                        editToConnection(photo);
                    }


                break;

            
            case R.id.imgBack:
                getActivity().finish();
                break;

            case R.id.txtGender:
                final Dialog dialogs = new Dialog(getActivity());
                dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogs.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf1 = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview1 = lf1.inflate(R.layout.dialog_gender, null);
                final TextView textOptions1 = (TextView) dialogview1.findViewById(R.id.txtOption1);
                final TextView textOptions2 = (TextView) dialogview1.findViewById(R.id.txtOption2);
                TextView textCancels = (TextView) dialogview1.findViewById(R.id.txtCancel);
                textOptions1.setText("Male");
                textOptions2.setText("Female");
                dialogs.setContentView(dialogview1);
                WindowManager.LayoutParams lps = new WindowManager.LayoutParams();
                lps.copyFrom(dialogs.getWindow().getAttributes());
                int widths = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
                lps.width = widths;
                lps.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lps.gravity = Gravity.CENTER;
                dialogs.getWindow().setAttributes(lps);
                dialogs.show();
                textOptions1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     txtGender.setText("Male");
                        dialogs.dismiss();
                    }
                });
                textOptions2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtGender.setText("Female");
                        dialogs.dismiss();
                    }
                });
                textCancels.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogs.dismiss();
                    }
                });

                break;
            case R.id.imgEdit:
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.dialog_gender, null);
                final TextView textOption1 = (TextView) dialogview.findViewById(R.id.txtOption1);
                final TextView textOption2 = (TextView) dialogview.findViewById(R.id.txtOption2);
                TextView textCancel = (TextView) dialogview.findViewById(R.id.txtCancel);
                textOption1.setText("Take Picture");
                textOption2.setText("Gallery");
                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
                lp.width = width;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                textOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       //dispatchTakePictureIntent();
                        dialog.dismiss();
                    }
                });
                textOption2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        startActivityForResult(photoPickerIntent, RESULT_SELECT_PHOTO);
                        dialog.dismiss();
                    }
                });
                textCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                break;

            case R.id.txtBdate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtBdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;


           /* case R.id.imgGoogleSignup:

                break;*/
        }
    }

    private boolean validateConnection() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        workPhone=txtWorkPhone.getText().toString().trim();
        homePhone=txtHomePhone.getText().toString().trim();

        address=txtAddress.getText().toString().trim();
        relation= spinnerRelation.getSelectedItem().toString();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        }
        else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
       else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        } else if (phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        } else {
            return true;
        }
        return false;


    }

    private boolean validateUser() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        bdate = txtBdate.getText().toString().trim();
        homePhone=txtHomePhone.getText().toString().trim();
        gender=txtGender.getText().toString().trim();
        if (spinner.getSelectedItem()!=null) {
            country = spinner.getSelectedItem().toString();
        }
        else{
            country="";
        }
        address=txtAddress.getText().toString().trim();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        }
        else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
        else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country",  getActivity());
        } else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        } else if (phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        } else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date",  getActivity());
        } else {
            return true;
        }
        return false;
    }

    private void editToConnection(byte[] photo) {
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), name, email, address, phone," "," ", "Self", photo," ", 1, 2);
            if (flag == true) {
                Toast.makeText(getActivity(), "You have edited connection Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
        }
        else{

            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, phone,homePhone,workPhone, spinnerRelation.getSelectedItem().toString(), photo,"", 1, 2);
            if (flag == true) {
                Toast.makeText(getActivity(), "You have edited connection Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validate() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        bdate = txtBdate.getText().toString().trim();
        country=spinner.getSelectedItem().toString();
        address=txtAddress.getText().toString().trim();
        relation= spinnerRelation.getSelectedItem().toString();
        
        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        }
        else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
       /* else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country",  getActivity());
        } */else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        } else if (phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        } /*else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date",  getActivity());
        } */else {
            return true;
        }
        return false;
    }
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = (ImageView) rootview.findViewById(R.id.imgProfile);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE && null != data) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            profileImage.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWProfile/");
            String path = file.getAbsolutePath();
            if (!file.exists()) {
                file.mkdirs();
            }


            if (file.isDirectory()) {
                String[] children = file.list();
                for (int i = 0; i < children.length; i++) {
                    new File(file, children[i]).delete();
                }
            }
            try {

                imagepath = path + "/MHCWProfile_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                outStream.write(byteArray);
                outStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }


        }

    }
}
