package com.mindyourelders.MyHealthCareWishes.HomeActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.utility.DialogManager;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.mindyourelders.MyHealthCareWishes.webservice.WebService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import static com.google.android.gms.drive.metadata.CustomPropertyKey.fromJson;
import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    TextView txtSignUp, txtLogin, txtForgotPassword,txtPolicy2,txtPolicy4,txtPolicy5;
    ImageView imgBack, imgEdit,imgProfile;
    TextView txtName, txtEmail,txtAddress, txtCountry, txtPhone, txtBdate, txtPassword,txtGender,txtHomePhone;
     TextInputLayout tilName;
    String name, email, mobile, country, bdate, password,address,phone;



    MySpinner spinner;
    String[] countryList = {"Canada", "Mexico", "USA", "UK", "california", "India"};
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    String imagepath = "";//

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    Preferences preferences;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUI();
        initComponent();
        initListener();
        initImageLoader();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
        PersonalInfoQuery s=new PersonalInfoQuery(context,dbHelper);
        MyConnectionsQuery m=new MyConnectionsQuery(context,dbHelper);
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                // .showImageOnLoading(R.drawable.images)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    private void initListener() {
        txtSignUp.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        txtForgotPassword.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtBdate.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
    }

    private void initUI() {
        imgProfile= (ImageView) findViewById(R.id.imgProfile);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtForgotPassword = (TextView) findViewById(R.id.txtForgotPassword);
        txtBdate = (TextView) findViewById(R.id.txtBdate);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgEdit = (ImageView) findViewById(R.id.imgEdit);

        tilName = (TextInputLayout) findViewById(R.id.tilName);
        txtAddress= (TextView) findViewById(R.id.txtAddress);
        txtName = (TextView) findViewById(R.id.txtName);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtCountry = (TextView) findViewById(R.id.txtCountry);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        txtPolicy2= (TextView) findViewById(R.id.txtPolicy2);
        txtPolicy4= (TextView) findViewById(R.id.txtPolicy4);
        txtPolicy5= (TextView) findViewById(R.id.txtPolicy5);
        txtPolicy2.setClickable(true);
        txtPolicy2.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.americanbar.org/utility/privacy.html'> Private Policy </a>";
        txtPolicy2.setText(Html.fromHtml(text));

        txtPolicy4.setClickable(true);
        txtPolicy4.setMovementMethod(LinkMovementMethod.getInstance());
        String text1 = "<a href='http://www.myhealthcarewishes.com'> Terms of Use </a>";
        txtPolicy4.setText(Html.fromHtml(text1));

        String texts = "<b><a href='http://www.myhealthcarewishes.com'> All Information </a> </b> on this app resides on your smartphone or tablet.<b> HIPAA </b>federal privacy\n" +
                "rules <b> <a href='http://www.myhealthcarewishes.com'> do not apply </a> </b> to this app because the app is in your control.";
        txtPolicy5.setText(Html.fromHtml(texts));

        spinner = (MySpinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Country");

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
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilName.setHintEnabled(true);
                txtName.setFocusable(true);
                return false;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.txtSignUp:
                if (validate()) {
                    Boolean flags = PersonalInfoQuery.searchEmailAvailability(email);
                    if (flags == true) {
                        Toast.makeText(context, "This email is already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        byte[] photo = baos.toByteArray();
                        Boolean flag = PersonalInfoQuery.insertPersonalInfoData(name, email, address, country, mobile, bdate, password, photo,"","");
                        if (flag == true) {
                            Toast.makeText(context, "You have registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent signupIntent = new Intent(context, BaseActivity.class);
                            preferences.putString(PrefConstants.USER_EMAIL, email);

                            PersonalInfo personalInfo=PersonalInfoQuery.fetchProfile(email);
                            preferences.putInt(PrefConstants.USER_ID, personalInfo.getId());
                            preferences.putString(PrefConstants.USER_NAME, personalInfo.getName());
                            String saveThis = Base64.encodeToString( personalInfo.getPhoto(), Base64.DEFAULT);
                            preferences.putString(PrefConstants.USER_PROFILEIMAGE, saveThis);
                            preferences.setREGISTERED(true);
                            preferences.setLogin(true);
                            startActivity(signupIntent);

                            SplashNewActivity s=new SplashNewActivity();
                            s.finish();
                            saveToConnection(personalInfo.getId());
                            finish();
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                  /*  */
                    }
                }
                break;

            case R.id.txtLogin:
                Intent signinIntent = new Intent(context, LoginActivity.class);
                startActivity(signinIntent);
                finish();
                break;

            case R.id.txtForgotPassword:
               // if (validate()) {
                    /*CreateUserAsynk asynkTask = new CreateUserAsynk(name, email, password);
                    asynkTask.execute();*/
/*
                    UpdateUserAsynk asynkTask = new UpdateUserAsynk(name, email, password);
                    asynkTask.execute();*/


                    GetUserAsynk asynkTask = new GetUserAsynk();
                    asynkTask.execute();
              //  }
                break;

            case R.id.imgBack:
                finish();
                break;

            case R.id.imgEdit:
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview = lf.inflate(R.layout.dialog_gender1, null);
                final TextView textOption1 = (TextView) dialogview.findViewById(R.id.txtOption1);
                final TextView textOption2 = (TextView) dialogview.findViewById(R.id.txtOption2);
                final TextView textOption3 = (TextView) dialogview.findViewById(R.id.txtOption3);
                TextView textCancel = (TextView) dialogview.findViewById(R.id.txtCancel);
                textOption1.setText("Take Picture");
                textOption2.setText("Gallery");
                textOption3.setText("Remove Picture");
                dialog.setContentView(dialogview);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
                lp.width = width;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                textOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dispatchTakePictureIntent();
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
                textOption3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       imgProfile.setImageResource(R.drawable.ic_profile_defaults);
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
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
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



    private void saveToConnection(int id) {
        Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] photo = baos.toByteArray();
        Boolean flag= MyConnectionsQuery.insertMyConnectionsData(id,name,email,address,mobile," ","","Self",photo," ",1,2, "", null);
        if (flag==true)
        {
            Toast.makeText(context,"You have added connection Successfully",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               /* Uri photoURI = FileProvider.getUriForFile(this,
                        "com.infidigi.fotobuddies.fileprovider",
                        photoFile);*/
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile.getAbsolutePath());
                startActivityForResult(takePictureIntent, RESULT_CAMERA_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    private boolean validate() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
     //   mobile = txtPhone.getText().toString().trim();
  //      bdate = txtBdate.getText().toString().trim();
//        country=spinner.getSelectedItem().toString();
        password = txtPassword.getText().toString().trim();
      //  address=txtAddress.getText().toString().trim();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", context);
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email", context);
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email", context);
        }
      /*  else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address", context);
        }
        else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country", context);
        } else if (mobile.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone", context);
        } else if (mobile.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits", context);
        } else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date", context);
        }*/ else if (password.equals("")) {
            txtPassword.setError("Please Enter Password");
            showAlert("Please Enter Password", context);
        } else if (password.length() < 6) {
            txtPassword.setError("Password should be minimum 6 characters");
            showAlert("Password should be minimum 6 characters", context);
        } else {
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = (ImageView) findViewById(R.id.imgProfile);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
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

                imagepath = path + "/MHCWProfile_" + String.valueOf(System.currentTimeMillis())+ ".jpg";
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

    class CreateUserAsynk extends AsyncTask<Void, Void, String> {
String name;
        String email;
        String password;
        ProgressDialog pd;

        private String deviceUdId = "";

        private String deviceType = "Android";

        public CreateUserAsynk(String name, String email, String password) {
            this.name=name;
            this.email=email;
            this.password=password;
        }

        @Override
        protected void onPreExecute() {

            deviceUdId = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            pd = ProgressDialog.show(context, "", "Please Wait..");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            WebService webService = new WebService();

            Log.e("URL parameter", name + " " + "" + " " + email
                    + " " + password + " " + deviceUdId + " " + deviceType);
            String result = webService.createProfile(name, "-",
                    "-", email, password, deviceUdId, deviceType);
            // String result = webService.getProfile("35");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            // Toast.makeText(getApplicationContext(), result + "",
            // Toast.LENGTH_LONG).show();
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }

            if (!result.equals("")) {

                if (result.equals("Exception")) {
                   // ErrorDialog.errorDialog(context);
                    DialogManager.showAlert("Error",context);
                }

                else {
                    Log.e("CreateUserAsynk", result);

                    String errorCode = parseResponse(result);

                    if (errorCode.equals("0")) {
                        DialogManager.showAlert("000",context);
                    /*{

                        if (ByPrefrenceActivity_FLAG) {
                            finish();
                        } else {

                            if (PreferenceConnector.readString(SignUpForm.this,
                                    PreferenceConnector.YESNO, "NO").equals(
                                    "YES")) {
                                Intent viewDataIntent = new Intent(
                                        SignUpForm.this,
                                        MainUserListActivity.class);
                                ShearedValues.activitysplash = 1;
                                startActivity(viewDataIntent);
                                if (firstUserAdded) {
                                    startActivity(new Intent(SignUpForm.this,
                                            MainUserListActivity.class)
                                            .putExtra("GLOBAL_USER_ID", ""));
                                } else {

                                    launchIndividualActivity();
                                }

                                InformationSreen.informationActivity.finish();

                                // TermsAndConditions.termsConditionsActivity
                                // .finish();
                                finish();
                            } else {
                                InformationSreen.informationActivity.finish();

                                // TermsAndConditions.termsConditionsActivity
                                // .finish();

                                launchIndividualActivity();
                                PreferenceConnector.writeString(
                                        SignUpForm.this,
                                        PreferenceConnector.YESNO, "YES");
                                finish();
                            }
                        }
                    }*/
                    } else {
                        // Toast.makeText(context,
                        // "Registration Failed, Try again",
                        // Toast.LENGTH_LONG).show();
                    }
                }

            }
            super.onPostExecute(result);
        }

    }

    /*private void launchIndividualActivity() {
        Intent i = new Intent(context, IndividualContactActivity.class);
        // i.setFlags(Intent.)
        i.putExtra("GLOBAL_USER_ID", "");
        i.putExtra("Activity", "SignUpForm");
        i.putExtra("Name", firstName + " " + lastName);
        i.putExtra("EmailId", eMail);
        i.putExtra("States", state);
        startActivity(i);
    }*/

    public String parseResponse(String result) {

        Log.e("Response", result);
        JSONObject job = null;

        String errorCode = "";

        try {
            job = new JSONObject(result);

            JSONObject job1 = job.getJSONObject("response");

            errorCode = job1.getString("errorCode");

            String message = "";

            if (errorCode.equals("0")) {
                message = job1.getString("respMsg");
                JSONObject job2 = job1.getJSONObject("respData");

                String userId = job2.getString("user_id");

             /* PreferenceConnector.writeString(context,
                        PreferenceConnector.USERID, userId);
                PreferenceConnector.writeString(getApplicationContext(),
                        PreferenceConnector.REGISTERED, "YES");*/

                Log.e("SuccessFullRegisterd", "UserId= " + userId);

                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                return errorCode;
            }

            else {
                message = job1.getString("errorMsg");

                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                return errorCode;
            }

        } catch (JSONException e) {

            e.printStackTrace();
            return "Exception";
        }

    }




    class UpdateUserAsynk extends AsyncTask<Void, Void, String> {

        ProgressDialog pd;

        private String userId = "";

        private String deviceType = "Android";
        String name;
        String email;
        String password;

        public UpdateUserAsynk(String name, String email, String password) {
            this.name=name;
            this.email=email;
            this.password=password;
        }

        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(context, "", "Please Wait..");

         /*   SharedPreferences mPref = context.getSharedPreferences(
                    "UserDetails", Context.MODE_PRIVATE);*/

         //   userId = mPref.getString("userId", "");

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            WebService webService = new WebService();

            Log.e("URL parameter", name + " " + "-" + " " + email
                    + " " + password + " " + " " + deviceType);
            String result = webService.editProfile("4985",
                    name, "-", "-", email, password,userId);
            // String result = webService.getProfile("35");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            // Toast.makeText(getApplicationContext(), result + "",
            // Toast.LENGTH_LONG).show();
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }

            if (!result.equals("")) {

                if (result.equals("Exception")) {
                   // ErrorDialog.errorDialog(context);
                }

                else {
                    Log.e("CreateUserAsynk", result);

                    String errorCode = parseResponses(result);
                    //
                    if (errorCode.equals("0")) {

                        setResult(RESULT_OK);

                        finish();
                        // Intent i = new Intent(context,
                        // IndividualContactActivity.class);
                        // // i.setFlags(Intent.)
                        // i.putExtra("GLOBAL_USER_ID", "");
                        // startActivity(i);

                    } else {
                        Toast.makeText(context, "Updation Failed, Try again",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
            super.onPostExecute(result);
        }

    }

    public String parseResponses(String result) {

        JSONObject job = null;

        String errorCode = "";

        try {
            job = new JSONObject(result);

            JSONObject job1 = job.getJSONObject("response");

            errorCode = job1.getString("errorCode");

            String message = "";

            if (errorCode.equals("0")) {
                try {
                    message = job1.getString("errorMsg");
                    Toast.makeText(context, "" + message, Toast.LENGTH_LONG)
                            .show();
                } catch (JSONException jop) {

                }

                return errorCode;
            }

            else {

                message = job1.getString("errorMsg");

                Toast.makeText(context, "" + message, Toast.LENGTH_LONG).show();
                return errorCode;
            }

        } catch (JSONException e) {

            e.printStackTrace();
            return "Exception";
        }

    }


    class GetUserAsynk extends AsyncTask<Void, Void, String> {

        ProgressDialog pd;

        String userId = "";

        @Override
        protected void onPreExecute() {

            pd = ProgressDialog.show(context, "", "Please Wait..");

           /* userId = PreferenceConnector.readString(context,
                    PreferenceConnector.USERID, "-1");
*/
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            WebService webService = new WebService();

            String result = webService.getProfile("4985");
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
            }

            if (!result.equals("")) {

                if (result.equals("Exception")) {

                    //ErrorDialog.errorDialog(context);

                }

                else {
                    Log.e("CreateUserAsynk", result);
                    String errorCode = parseResponseg(result);

                    if (errorCode.equals("0")) {

                    } else {
                        Toast.makeText(context, "No details", Toast.LENGTH_LONG)
                                .show();
                    }
                }

            }
            super.onPostExecute(result);
        }

    }

    public String parseResponseg(String result) {

        JSONObject job = null;

        String errorCode = "";

        try {
            job = new JSONObject(result);

            JSONObject job1 = job.getJSONObject("response");

            errorCode = job1.getString("errorCode");

            // String message = "";

            if (errorCode.equals("0")) {

                // message = job1.getString("resp");

                JSONArray array2 = job1.getJSONArray("respData");

                JSONObject job2 = array2.getJSONObject(0);

                // String id = job2.getString("id");
                //
                // String status = job2.getString("status");
                //
                // String user_name = job2.getString("user_name");
                //
                // String user_type = job2.getString("user_type");

                String state = job2.getString("state");

                String email = job2.getString("email");

                String first_name = job2.getString("first_name");

                String last_name = job2.getString("last_name");

                // String password = job2.getString("password");
                //
                // String created_date = job2.getString("created_date");
                //
                // String device_type = job2.getString("device_type");
                //
                // String updated_date = job2.getString("updated_date");
                //
                // String deviceUdid = job2.getString("deviceUdid");

               // updateUI(first_name, last_name, state, email);

                fromJson(job2);

                return errorCode;

            }

            else {
                // message = job1.getString("errorMsg");

                Toast.makeText(context, "" + "No details", Toast.LENGTH_LONG)
                        .show();
                return errorCode;
            }

        } catch (JSONException e) {

            e.printStackTrace();
            return "Exception";
        }

    }
}
