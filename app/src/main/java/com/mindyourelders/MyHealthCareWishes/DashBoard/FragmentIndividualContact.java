package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.FaxCustomDialog;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.MyConnectionsQuery;
import com.mindyourelders.MyHealthCareWishes.database.PersonalInfoQuery;
import com.mindyourelders.MyHealthCareWishes.database.PetQuery;
import com.mindyourelders.MyHealthCareWishes.model.PersonalInfo;
import com.mindyourelders.MyHealthCareWishes.model.Pet;
import com.mindyourelders.MyHealthCareWishes.model.RelativeConnection;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.MessageString;
import com.mindyourelders.MyHealthCareWishes.pdfCreation.PDFDocumentProcess;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Header;
import com.mindyourelders.MyHealthCareWishes.pdfdesign.Individual;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.mindyourelders.MyHealthCareWishes.utility.DialogManager.showAlert;

/**
 * Created by welcome on 9/14/2017.
 */

public class FragmentIndividualContact extends Fragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private static final int REQUEST_CARD = 50;
    ContentValues values;
    Uri imageUriProfile=null,imageUriCard=null;

   // byte[] photoCard=null;
    ImageView imgRight,imgInfo;
    RelativeLayout llIndividual;
    TextView txtSignUp, txtLogin, txtForgotPassword,txtOther,txtOtherLanguage,txtMsg;
    ImageView imgEdit,imgProfile,imgDone,imgAddpet,imgEditCard,imgCard;
    TextView txtHeight,txtWeight,txtProfession,txttelephone,txtEmployed,txtReligion,txtIdNumber,txtOtherRelation,txtTitle, txtName, txtEmail,txtAddress, txtCountry, txtPhone,txtHomePhone,txtWorkPhone, txtBdate,txtGender, txtPassword,txtRelation;
    TextInputLayout tilOtherRelation,tilId,tilOther,tilOtherLanguage;

    RelativeLayout rlPet,rlLive;
    String name="", email="", phone="",manager_phone="", country="", bdate="",address="",homePhone="",workPhone="",gender="";
    String height="",weight="",profession="",employed="",religion="",idnumber="";
    String pet="No",veteran="No",english="No",live="No";
    String eyes,language,marital_status;
    String otherRelation;
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    private static int RESULT_CAMERA_IMAGE_CARD = 3;
    private static int RESULT_SELECT_PHOTO_CARD = 4;
    RadioGroup rgPet,rgVeteran,rgUnderstand,rgLive;
    RadioButton rbYes,rbNo,rbYesPet,rbNoPet,rbYess,rbNoo,rbYesLive,rbNoLive;
    public static final int REQUEST_PET= 400;
    CheckBox chkOther,chkChild,chkFriend,chkGrandParent,chkParent,chkSpouse;
    String child="No",friend="No",grandParent="No",parent="No",spouse="No",other="No";
    String liveOther="";
    
    ListView ListPet;
    MySpinner spinner,spinnerRelation,spinnerEyes,spinnerLanguage,spinnerMarital;
    String[] countryList = {"Canada", "Mexico", "USA", "UK", "California", "India"};

    String imagepath = "",cardpath="";//
    String relation;
    String OtherLang="";
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;
    RelativeLayout rlCard;
    TextView txtCard;
    final CharSequence[] dialog_items = {"View","Email","Fax"};
    DBHelper dbHelper;
    View rootview;
    Preferences preferences;
    ImageView imgBack;
    RelativeConnection connection;
    PersonalInfo personalInfo;

    TextInputLayout tilBdate,tilName,tilWorkPhone;
    String[] Relationship = {"Aunt","Brother","Cousin","Dad","Daughter","Father-in-law","Friend","GrandDaughter","GrandFather","GrandMother","GrandSon","Husband","Mom","Mother-in-law","Neighbor","Nephew","Niece","Sister","Son","Uncle","Wife", "Other"};
    String[] EyesList = {"Blue", "Brown", "Green", "Hazel"};
    String[] MaritalList = {"Divorced","Domestic Partner","Married","Separated","Single","Widowed"};
    String[] LangList = {"Arabic","Chinese","English","French","German","Greek","Hebrew","Hindi","Italian","Japanese","Korean","Russian","Spanish","Other"};
    ImageLoader imageLoaderProfile,imageLoaderCard;
    DisplayImageOptions displayImageOptionsProfile,displayImageOptionsCard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview=inflater.inflate(R.layout.fragment_individual_contact,null);
        preferences = new Preferences(getActivity());
        initComponent();
        initImageLoader();
        initUI();
        initListener();
        return rootview;
    }

    private void initImageLoader() {

        //Profile
        displayImageOptionsProfile = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new RoundedBitmapDisplayer(150)) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptionsProfile)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoaderProfile = ImageLoader.getInstance();

        //Card
        displayImageOptionsCard = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.busi_card)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(displayImageOptionsCard)
                .build();
        ImageLoader.getInstance().init(configs);
        imageLoaderCard = ImageLoader.getInstance();
    }

    private void initComponent() {
        dbHelper=new DBHelper(getActivity());
        PersonalInfoQuery s=new PersonalInfoQuery(getActivity(),dbHelper);
        MyConnectionsQuery m=new MyConnectionsQuery(getActivity(),dbHelper);
        PetQuery p=new PetQuery(getActivity(),dbHelper);
        if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID)))
        {
            personalInfo = PersonalInfoQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        }
        else {
            connection = MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        }
    }


    private void initListener() {
        imgBack.setOnClickListener(this);
        txtBdate.setOnClickListener(this);
        imgEdit.setOnClickListener(this);
        imgEditCard.setOnClickListener(this);
        imgCard.setOnClickListener(this);
        txtCard.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        txtGender.setOnClickListener(this);
        imgAddpet.setOnClickListener(this);
        imgRight.setOnClickListener(this);
        chkChild.setOnCheckedChangeListener(this);
        chkFriend.setOnCheckedChangeListener(this);
        chkGrandParent.setOnCheckedChangeListener(this);
        chkParent.setOnCheckedChangeListener(this);
        chkSpouse.setOnCheckedChangeListener(this);
    }

    private void initUI() {
        imgInfo=rootview.findViewById(R.id.imgInfo);
        imgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog customDialog;
                customDialog = new Dialog(getActivity());
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.dialog_living);
                customDialog.setCancelable(false);
                TextView txtNotes= (TextView) customDialog.findViewById(R.id.txtNotes);
                String msg="<b>First Time User:</b><br>" +
                        "To add information type responses.<br>" +
                        "To save information click the check mark" +
                        " on the upper right side of the screen.<br><br>" +
                        "To edit or delete information simply work on the screen and then save your edits by clicking on the check mark on the upper right side of the screen." +
                        "<br><br>" +
                        "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the upper right side of the screen.";

                txtNotes.setText(Html.fromHtml(msg));
                TextView txtNoteHeader= (TextView) customDialog.findViewById(R.id.txtNoteHeader);
                txtNoteHeader.setText("Help");
                TextView btnYes= (TextView) customDialog.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
            }
        });
        /*txtMsg=rootview.findViewById(R.id.txtMsg);

        txtMsg.setText(Html.fromHtml(msg));*/
        llIndividual= (RelativeLayout) rootview.findViewById(R.id.llIndividual);
        rlCard= (RelativeLayout) rootview.findViewById(R.id.rlCard);
        rlLive= (RelativeLayout) rootview.findViewById(R.id.rlLive);
        txtCard= (TextView) rootview.findViewById(R.id.txtCard);
        txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        txtTitle.setVisibility(View.VISIBLE);
        txtTitle.setText("PERSONAL PROFILE");
        imgRight= (ImageView) getActivity().findViewById(R.id.imgRight);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
            }
        });
        chkOther= (CheckBox) rootview.findViewById(R.id.chkOther);

        txtOtherLanguage = (TextView) rootview.findViewById(R.id.txtOtherLanguage);
        tilOtherLanguage = (TextInputLayout) rootview.findViewById(R.id.tilOtherLanguage);
        tilOtherLanguage.setHint("Other Language");

        chkChild= (CheckBox) rootview.findViewById(R.id.chkChild);
        chkFriend= (CheckBox) rootview.findViewById(R.id.chkFriend);
        chkGrandParent= (CheckBox) rootview.findViewById(R.id.chkGrandParent);
        chkParent= (CheckBox) rootview.findViewById(R.id.chkParent);
        chkSpouse= (CheckBox) rootview.findViewById(R.id.chkSpouse);

        ListPet= (ListView) rootview.findViewById(R.id.ListPet);
        imgProfile = (ImageView) rootview.findViewById(R.id.imgProfile);
        imgCard= (ImageView) rootview.findViewById(R.id.imgCard);
        imgEditCard = (ImageView) rootview.findViewById(R.id.imgEditCard);
        imgAddpet = (ImageView) rootview.findViewById(R.id.imgAddPet);
        txtSignUp = (TextView) rootview.findViewById(R.id.txtSignUp);
        tilName = (TextInputLayout) rootview.findViewById(R.id.tilName);
        tilOtherRelation = (TextInputLayout) rootview.findViewById(R.id.tilOtherRelation);
        tilWorkPhone = (TextInputLayout) rootview.findViewById(R.id.tilWorkPhone);
        tilId = (TextInputLayout) rootview.findViewById(R.id.tilId);
        rlPet= (RelativeLayout) rootview.findViewById(R.id.rlPet);
        txtOtherRelation = (TextView) rootview.findViewById(R.id.txtOtherRelation);
        txtLogin = (TextView) rootview.findViewById(R.id.txtLogin);
        txtForgotPassword = (TextView) rootview.findViewById(R.id.txtForgotPassword);
        txtBdate = (TextView) rootview.findViewById(R.id.txtBdate);
        txtGender = (TextView) rootview.findViewById(R.id.txtGender);
        imgBack = (ImageView) getActivity().findViewById(R.id.imgBack);
        imgEdit = (ImageView) rootview.findViewById(R.id.imgEdit);
        imgDone = (ImageView) getActivity().findViewById(R.id.imgDone);
        imgDone.setVisibility(View.VISIBLE);
        txtRelation = (TextView) rootview.findViewById(R.id.txtRelation);
        tilBdate = (TextInputLayout) rootview.findViewById(R.id.tilBdate);
        spinnerRelation = (MySpinner) rootview.findViewById(R.id.spinnerRelation);
        txtAddress = (TextView) rootview.findViewById(R.id.txtAddress);
        txtName = (TextView) rootview.findViewById(R.id.txtName);
        txtEmail = (TextView) rootview.findViewById(R.id.txtEmail);
        txtCountry = (TextView) rootview.findViewById(R.id.txtCountry);
        txtPhone = (TextView) rootview.findViewById(R.id.txtPhone);
        txtHomePhone = (TextView) rootview.findViewById(R.id.txtHomePhone);
        txtWorkPhone = (TextView) rootview.findViewById(R.id.txtWorkPhone);

        txtHeight = (TextView) rootview.findViewById(R.id.txtHeight);
        txtWeight = (TextView) rootview.findViewById(R.id.txtWeight);
        txtProfession = (TextView) rootview.findViewById(R.id.txtProfession);
        txtEmployed = (TextView) rootview.findViewById(R.id.txtEmployedBy);
        txttelephone= (TextView) rootview.findViewById(R.id.txttelephone);
        txtReligion = (TextView) rootview.findViewById(R.id.txtReligion);
        txtIdNumber = (TextView) rootview.findViewById(R.id.txtId);
        txtOther = (TextView) rootview.findViewById(R.id.txtOther);
        tilOther= (TextInputLayout) rootview.findViewById(R.id.tilOther);

        rbYes = (RadioButton) rootview.findViewById(R.id.rbYes);
        rbNo = (RadioButton) rootview.findViewById(R.id.rbNo);
        rbYesPet = (RadioButton) rootview.findViewById(R.id.rbYesPet);
        rbNoPet = (RadioButton) rootview.findViewById(R.id.rbNoPet);

        rgLive= (RadioGroup) rootview.findViewById(R.id.rgLive);
        rbYesLive= (RadioButton) rootview.findViewById(R.id.rbYesLive);
        rbNoLive = (RadioButton) rootview.findViewById(R.id.rbNoLive);

        rbYess = (RadioButton) rootview.findViewById(R.id.rbYess);
        rbNoo = (RadioButton) rootview.findViewById(R.id.rbNoo);

        rgPet= (RadioGroup) rootview.findViewById(R.id.rgPet);
        rgVeteran= (RadioGroup) rootview.findViewById(R.id.rgVeteran);
        rgUnderstand= (RadioGroup) rootview.findViewById(R.id.rgUnderstand);

        spinner = (MySpinner) rootview.findViewById(R.id.spinner);
        spinnerEyes = (MySpinner) rootview.findViewById(R.id.spinnerEyes);
        spinnerLanguage= (MySpinner) rootview.findViewById(R.id.spinnerLanguage);
        spinnerMarital= (MySpinner) rootview.findViewById(R.id.spinnerMarital);

        ArrayAdapter<String> adapterm = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, MaritalList);
        adapterm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMarital.setAdapter(adapterm);
        spinnerMarital.setHint("Marital Status");


        ArrayAdapter<String> adapters = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, EyesList);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEyes.setAdapter(adapters);
        spinnerEyes.setHint("Eyes Color");

        ArrayAdapter<String> adapterl = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, LangList);
        adapterl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapterl);
        spinnerLanguage.setHint("Language Spoken");

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, countryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Country");

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, Relationship);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter1);
        spinnerRelation.setHint("Relationship");

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherLanguage.setVisibility(View.VISIBLE);
                    //txtOtherLanguage.requestFocus();
                } else {
                    tilOtherLanguage.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chkOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    tilOther.setVisibility(View.VISIBLE);
                    other="Yes";

                } else if (isChecked==false) {
                    tilOther.setVisibility(View.GONE);
                    other="No";
                }
            }
        });

        rgPet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYesPet) {
                    pet = "Yes";
                    rlPet.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.rbNoPet) {
                    pet = "No";
                    rlPet.setVisibility(View.GONE);
                    boolean flag= PetQuery.deleteRecords(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    if(flag==true)
                    {
                        //  Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                        setPetData();
                        // ListPet.requestFocus();
                    }
                }
            }
        });

        rgVeteran.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYes) {
                    veteran = "Yes";
                    tilId.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.rbNo) {
                    veteran = "No";
                    tilId.setVisibility(View.GONE);
                    idnumber="";
                    txtIdNumber.setText(idnumber);
                }
            }
        });

        rgLive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYesLive) {
                    live = "Yes";
                    rlLive.setVisibility(View.GONE);
                    child="No";friend="No";grandParent="No";parent="No";spouse="No";other="No";
                    liveOther="";
                    chkChild.setChecked(false);
                    chkFriend.setChecked(false);
                    chkGrandParent.setChecked(false);
                    chkParent.setChecked(false);
                    chkSpouse.setChecked(false);
                    chkOther.setChecked(false);
                } else if (checkedId == R.id.rbNoLive) {
                    live = "No";
                    rlLive.setVisibility(View.VISIBLE);
                }
            }
        });
        rgUnderstand.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYess) {
                    english = "Yes";
                    //tilId.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.rbNoo) {
                    english = "No";
                    //tilId.setVisibility(View.GONE);
                }
            }
        });
        llIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().getCurrentFocus() != null) {
                    InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
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

        txttelephone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txttelephone.getText().toString().length();
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
        txtHomePhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHomePhone.getText().toString().length();
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

        txtWorkPhone.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtWorkPhone.getText().toString().length();
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



        txtHeight.addTextChangedListener(new TextWatcher() {
            int prevL = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                prevL = txtHeight.getText().toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.length();
                if ((prevL < length) && (length == 1)) {
                    editable.append("-");
                }
            }
        });
        setValues();

        spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Other")) {
                    tilOtherRelation.setVisibility(View.VISIBLE);
                } else {
                    tilOtherRelation.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        setPetData();
    }

    private void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void setValues() {
        if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
            tilBdate.setVisibility(View.VISIBLE);
           // spinner.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.GONE);
            txtWorkPhone.setVisibility(View.GONE);
            tilWorkPhone.setVisibility(View.GONE);
            txtHomePhone.setVisibility(View.VISIBLE);
            if (personalInfo != null) {
                txtGender.setText(personalInfo.getGender());
                txtName.setText(personalInfo.getName());
                txtEmail.setText(personalInfo.getEmail());
                txtAddress.setText(personalInfo.getAddress());
                txtHomePhone.setText(personalInfo.getHomePhone());
                txtOther.setText(personalInfo.getOther_person());
             /*   if (personalInfo.getLive() != null) {
                    if (personalInfo.getLive().equals("Yes")) {
                        rbYes.setChecked(true);

                    } else if (personalInfo.getLive().equals("No")) {
                        rbNo.setChecked(true);
                        live = "No";
                        rlLive.setVisibility(View.VISIBLE);
                    }
                }*/
                if (personalInfo.getLive()!=null) {
                    if (personalInfo.getLive().equals("Yes")) {
                        rbYesLive.setChecked(true);
                        rbNoLive.setChecked(false);
                        live = "Yes";
                        rlLive.setVisibility(View.GONE);
                    } else {
                        rbYesLive.setChecked(false);
                        rbNoLive.setChecked(true);
                        live = "No";
                        rlLive.setVisibility(View.VISIBLE);
                    }
                }
                if (personalInfo.getChildren() != null) {
                    if (personalInfo.getChildren().equals("Yes")) {
                        chkChild.setChecked(true);
                        child = "Yes";
                    } else if (personalInfo.getChildren().equals("No")) {
                        chkChild.setChecked(false);
                        child = "No";
                    }
                }
                if (personalInfo.getFriend() != null) {
                    if (personalInfo.getFriend().equals("Yes")) {
                        chkFriend.setChecked(true);
                        friend = "Yes";
                    } else if (personalInfo.getFriend().equals("No")) {
                        chkFriend.setChecked(false);
                        friend = "No";
                    }
                }

                if (personalInfo.getGrand() != null) {
                    if (personalInfo.getGrand().equals("Yes")) {
                        chkGrandParent.setChecked(true);
                        grandParent = "Yes";
                    } else if (personalInfo.getGrand().equals("No")) {
                        chkGrandParent.setChecked(false);
                        grandParent = "No";
                    }
                }
                if (personalInfo.getParents() != null) {
                    if (personalInfo.getParents().equals("Yes")) {
                        chkParent.setChecked(true);
                        parent = "Yes";
                    } else if (personalInfo.getParents().equals("No")) {
                        chkParent.setChecked(false);
                        parent = "No";
                    }
                }
                if (personalInfo.getSpouse() != null) {
                    if (personalInfo.getSpouse().equals("Yes")) {
                        chkSpouse.setChecked(true);
                        spouse = "Yes";
                    } else if (personalInfo.getSpouse().equals("No")) {
                        chkSpouse.setChecked(false);
                        spouse = "No";
                    }
                }
                if (personalInfo.getSign_other() != null) {
                    if (personalInfo.getSign_other().equals("Yes")) {
                        chkOther.setChecked(true);
                        other = "Yes";
                        tilOther.setVisibility(View.VISIBLE);
                    } else if (personalInfo.getSign_other().equals("No")) {
                        chkOther.setChecked(false);
                        other = "No";
                        tilOther.setVisibility(View.GONE);
                    }
                }

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

                txtHeight.setText(personalInfo.getHeight());
                txtWeight.setText(personalInfo.getWeight());
                txtProfession.setText(personalInfo.getProfession());
                txtEmployed.setText(personalInfo.getEmployed());
                txttelephone.setText(personalInfo.getManager_phone());
                txtReligion.setText(personalInfo.getReligion());
                txtIdNumber.setText(personalInfo.getIdnumber());

                if (personalInfo.getEyes()!=null) {
                    int index = 0;
                    for (int i = 0; i < EyesList.length; i++) {
                        if (personalInfo.getEyes().equalsIgnoreCase(EyesList[i])) {
                            index = i;
                        }
                    }
                    spinnerEyes.setSelection(index + 1);
                }

                if (personalInfo.getLanguage()!=null) {
                    int indexs = 0;
                    for (int i = 0; i < LangList.length; i++) {
                        if (personalInfo.getLanguage().equalsIgnoreCase(LangList[i])) {
                            indexs = i;
                            if (personalInfo.getLanguage().equals("Other"))
                            {
                                tilOtherLanguage.setVisibility(View.VISIBLE);
                                txtOtherLanguage.setText(personalInfo.getOtherLang());
                            }
                            else
                            {
                                tilOtherLanguage.setVisibility(View.GONE);
                                txtOtherLanguage.setText("");
                            }
                        }
                    }
                    spinnerLanguage.setSelection(indexs + 1);
                }
                if (personalInfo.getMarital_status()!=null) {
                    int indexss = 0;
                    for (int i = 0; i < MaritalList.length; i++) {
                        if (personalInfo.getMarital_status().equalsIgnoreCase(MaritalList[i])) {
                            indexss = i;
                        }
                    }
                    spinnerMarital.setSelection(indexss + 1);
                }
                if (personalInfo.getVeteran()!=null) {
                    if (personalInfo.getVeteran().equals("Yes")) {
                        rbYes.setChecked(true);
                        rbNo.setChecked(false);
                    } else {
                        rbYes.setChecked(false);
                        rbNo.setChecked(true);
                    }
                }
                if (personalInfo.getEnglish()!=null) {
                    if (personalInfo.getEnglish().equals("Yes")) {
                        rbYess.setChecked(true);
                        rbNoo.setChecked(false);
                    } else {
                        rbYess.setChecked(false);
                        rbNoo.setChecked(true);
                    }
                }
                if (personalInfo.getPet()!=null) {
                    if (personalInfo.getPet().equals("Yes")) {
                        rbYesPet.setChecked(true);
                        rbNoPet.setChecked(false);
                    } else {
                        rbYesPet.setChecked(false);
                        rbNoPet.setChecked(true);
                    }
                }
                imagepath=personalInfo.getPhoto();
                if (!imagepath.equals("")) {
                    File imgFile = new File(imagepath);
                    if (imgFile.exists()) {
                      /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*/
                        imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgProfile,displayImageOptionsProfile);
                    }
                }
                else{
                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                }
              /*  byte[] photo=personalInfo.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
               imgProfile.setImageBitmap(bmp);*/
                cardpath=personalInfo.getPhotoCard();
                if (!personalInfo.getPhotoCard().equals("")) {
                    File imgFile1 = new File(personalInfo.getPhotoCard());
                    if (imgFile1.exists()) {
                       /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                        imgCard.setImageBitmap(myBitmap);*/
                        imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)),imgCard,displayImageOptionsCard);
                    }
                   /* byte[] photoCard = personalInfo.getPhotoCard();
                    Bitmap bmps = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);*/
                   // imgCard.setImageBitmap(bmps);
                    imgCard.setVisibility(View.VISIBLE);
                    rlCard.setVisibility(View.VISIBLE);
                    txtCard.setVisibility(View.GONE);
                }
                else{
                    imgCard.setVisibility(View.GONE);
                    rlCard.setVisibility(View.GONE);
                    txtCard.setVisibility(View.VISIBLE);
                }
            }
        }
        else{
            tilBdate.setVisibility(View.GONE);
           // spinner.setVisibility(View.GONE);
            txtWorkPhone.setVisibility(View.VISIBLE);
            tilWorkPhone.setVisibility(View.VISIBLE);
            spinnerRelation.setVisibility(View.VISIBLE);
            txtGender.setVisibility(View.GONE);
            if (connection != null) {
                txtName.setText(connection.getName());
                txtEmail.setText(connection.getEmail());
                txtAddress.setText(connection.getAddress());
                txtPhone.setText(connection.getMobile());
                txtOtherRelation.setText(connection.getOtherRelation());
                txtHomePhone.setText(connection.getPhone());
                txtWorkPhone.setText(connection.getWorkPhone());
                    int index = 0;
                    for (int i = 0; i < Relationship.length; i++) {
                        if (connection.getRelationType().equalsIgnoreCase(Relationship[i])) {
                            index = i;
                        }
                    }
                    spinnerRelation.setSelection(index+1);

                txtOther.setText(connection.getOther_person());
             /*   if (personalInfo.getLive() != null) {
                    if (personalInfo.getLive().equals("Yes")) {
                        rbYes.setChecked(true);

                    } else if (personalInfo.getLive().equals("No")) {
                        rbNo.setChecked(true);
                        live = "No";
                        rlLive.setVisibility(View.VISIBLE);
                    }
                }*/
                if (connection.getLive()!=null) {
                    if (connection.getLive().equals("Yes")) {
                        rbYesLive.setChecked(true);
                        rbNoLive.setChecked(false);
                        live = "Yes";
                        rlLive.setVisibility(View.GONE);
                    } else {
                        rbYesLive.setChecked(false);
                        rbNoLive.setChecked(true);
                        live = "No";
                        rlLive.setVisibility(View.VISIBLE);
                    }
                }
                if (connection.getChildren() != null) {
                    if (connection.getChildren().equals("Yes")) {
                        chkChild.setChecked(true);
                        child = "Yes";
                    } else if (connection.getChildren().equals("No")) {
                        chkChild.setChecked(false);
                        child = "No";
                    }
                }
                if (connection.getFriend() != null) {
                    if (connection.getFriend().equals("Yes")) {
                        chkFriend.setChecked(true);
                        friend = "Yes";
                    } else if (connection.getFriend().equals("No")) {
                        chkFriend.setChecked(false);
                        friend = "No";
                    }
                }

                if (connection.getGrand() != null) {
                    if (connection.getGrand().equals("Yes")) {
                        chkGrandParent.setChecked(true);
                        grandParent = "Yes";
                    } else if (connection.getGrand().equals("No")) {
                        chkGrandParent.setChecked(false);
                        grandParent = "No";
                    }
                }
                if (connection.getParents() != null) {
                    if (connection.getParents().equals("Yes")) {
                        chkParent.setChecked(true);
                        parent = "Yes";
                    } else if (connection.getParents().equals("No")) {
                        chkParent.setChecked(false);
                        parent = "No";
                    }
                }
                if (connection.getSpouse() != null) {
                    if (connection.getSpouse().equals("Yes")) {
                        chkSpouse.setChecked(true);
                        spouse = "Yes";
                    } else if (connection.getSpouse().equals("No")) {
                        chkSpouse.setChecked(false);
                        spouse = "No";
                    }
                }
                if (connection.getSign_other() != null) {
                    if (connection.getSign_other().equals("Yes")) {
                        chkOther.setChecked(true);
                        other = "Yes";
                        tilOther.setVisibility(View.VISIBLE);
                    } else if (connection.getSign_other().equals("No")) {
                        chkOther.setChecked(false);
                        other = "No";
                        tilOther.setVisibility(View.GONE);
                    }
                }

                imagepath=connection.getPhoto();
                if (!imagepath.equals("")) {
                    File imgFile = new File(imagepath);
                    if (imgFile.exists()) {
                       /* Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imgProfile.setImageBitmap(myBitmap);*/
                        imageLoaderProfile.displayImage(String.valueOf(Uri.fromFile(imgFile)),imgProfile,displayImageOptionsProfile);
                    }
                }
                else{
                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                }
              /*  byte[] photo=personalInfo.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
               imgProfile.setImageBitmap(bmp);*/
                cardpath=connection.getPhotoCard();
                if (!connection.getPhotoCard().equals("")) {
                    File imgFile1 = new File(connection.getPhotoCard());
                    if (imgFile1.exists()) {
                      /*  Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                        imgCard.setImageBitmap(myBitmap);*/
                        imageLoaderCard.displayImage(String.valueOf(Uri.fromFile(imgFile1)),imgCard,displayImageOptionsCard);
                    }
                   /* byte[] photoCard = personalInfo.getPhotoCard();
                    Bitmap bmps = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);*/
                    // imgCard.setImageBitmap(bmps);
                    imgCard.setVisibility(View.VISIBLE);
                    rlCard.setVisibility(View.VISIBLE);
                    txtCard.setVisibility(View.GONE);
                }
                else{
                    imgCard.setVisibility(View.GONE);
                    rlCard.setVisibility(View.GONE);
                    txtCard.setVisibility(View.VISIBLE);
                }

                txtHeight.setText(connection.getHeight());
                txtWeight.setText(connection.getWeight());
                txtProfession.setText(connection.getProfession());
                txtEmployed.setText(connection.getEmployed());
                txttelephone.setText(connection.getManager_phone());
                txtReligion.setText(connection.getReligion());
                txtIdNumber.setText(connection.getIdnumber());
                int indexd = 0;

                if (connection.getEyes()!=null) {
                    for (int i = 0; i < EyesList.length; i++) {
                        if (connection.getEyes().equalsIgnoreCase(EyesList[i])) {
                            indexd = i;
                        }
                    }
                    spinnerEyes.setSelection(indexd + 1);
                }

                if (connection.getLanguage()!=null) {
                    int indexs = 0;
                    for (int i = 0; i < LangList.length; i++) {
                        if (connection.getLanguage().equalsIgnoreCase(LangList[i])) {
                            indexs = i;
                            if (connection.getLanguage().equals("Other"))
                            {
                                tilOtherLanguage.setVisibility(View.VISIBLE);
                                txtOtherLanguage.setText(connection.getOtherLang());
                            }
                            else
                            {
                                tilOtherLanguage.setVisibility(View.GONE);
                                txtOtherLanguage.setText("");
                            }
                        }
                    }
                    spinnerLanguage.setSelection(indexs + 1);
                }

                if (connection.getMarital_status()!=null) {
                    int indexss = 0;
                    for (int i = 0; i < MaritalList.length; i++) {
                        if (connection.getMarital_status().equalsIgnoreCase(MaritalList[i])) {
                            indexss = i;
                        }
                    }
                    spinnerMarital.setSelection(indexss + 1);
                }
                if (connection.getVeteran()!=null) {
                    if (connection.getVeteran().equals("Yes")) {
                        rbYes.setChecked(true);
                        rbNo.setChecked(false);
                    } else {
                        rbYes.setChecked(false);
                        rbNo.setChecked(true);
                    }
                }
                if (connection.getEnglish()!=null) {
                    if (connection.getEnglish().equals("Yes")) {
                        rbYess.setChecked(true);
                        rbNoo.setChecked(false);
                    } else {
                        rbYess.setChecked(false);
                        rbNoo.setChecked(true);
                    }
                }
                if (connection.getPet()!=null) {
                    if (connection.getPet().equals("Yes")) {
                        rbYesPet.setChecked(true);
                        rbNoPet.setChecked(false);
                    } else {
                        rbYesPet.setChecked(false);
                        rbNoPet.setChecked(true);
                    }
                }
            }
            }
        }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgAddPet:
                Intent intent = new Intent(getActivity(), AddPetActivity.class);
                intent.putExtra("FROM","View");
                startActivityForResult(intent, REQUEST_PET);
                break;

            case R.id.imgDone:

               /* Bitmap bitmap = ((BitmapDrawable) imgProfile.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos);
                byte[] photo = baos.toByteArray();

                Bitmap bitmaps = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
                ByteArrayOutputStream baoss = new ByteArrayOutputStream();
                bitmaps.compress(Bitmap.CompressFormat.JPEG, 10, baoss);
                if (imgCard.getVisibility()==View.VISIBLE)
                {
                    photoCard = baoss.toByteArray();
                }else if(imgCard.getVisibility()==View.GONE){
                    photoCard = null;
                }*/
                 if (pet.equals("No"))
                 {
                     boolean flag= PetQuery.deleteRecords(preferences.getInt(PrefConstants.CONNECTED_USERID));
                     if(flag==true)
                     {
                         //  Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                         setPetData();
                         // ListPet.requestFocus();
                     }
                 }
                if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                        if (validateUser()) {
                            Boolean flag = PersonalInfoQuery.updatePersonalInfoData(preferences.getInt(PrefConstants.USER_ID), name, email, address, country, phone, bdate, imagepath,homePhone,gender,height,weight,eyes,profession,employed,language,marital_status,religion,veteran,idnumber,pet,manager_phone,cardpath,english,child,friend,grandParent,parent,spouse,other,liveOther,live,OtherLang);
                            if (flag == true) {
                                Toast.makeText(getActivity(), "You have updated Successfully", Toast.LENGTH_SHORT).show();
                                hideSoftKeyboard();
                                preferences.putString(PrefConstants.USER_NAME,name);
                                preferences.putString(PrefConstants.USER_PROFILEIMAGE,imagepath);
                                getActivity().finish();
                                editToConnection(imagepath,cardpath);
                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        if (validateConnection()) {
                            editToConnection(imagepath,cardpath);

                            getActivity().finish();
                        }
                    }


                break;

            
            case R.id.imgBack:
                hideSoftKeyboard();
                getActivity().finish();
                break;

            case R.id.txtGender:
                final Dialog dialogs = new Dialog(getActivity());
                dialogs.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogs.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf1 = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogview1 = lf1.inflate(R.layout.dialog_gender1, null);
                final TextView textOptions1 = (TextView) dialogview1.findViewById(R.id.txtOption1);
                final TextView textOptions2 = (TextView) dialogview1.findViewById(R.id.txtOption2);
                final TextView textOptions3 = (TextView) dialogview1.findViewById(R.id.txtOption3);
                TextView textCancels = (TextView) dialogview1.findViewById(R.id.txtCancel);
                textOptions1.setText("Male");
                textOptions2.setText("Female");
                textOptions3.setText("Trans*");
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

                textOptions3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtGender.setText("Trans*");
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

            case R.id.imgRight:

                final String RESULT = Environment.getExternalStorageDirectory()
                        + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID) + "/";
                File dirfile = new File(RESULT);
                dirfile.mkdirs();
                File file = new File(dirfile, "PersonalProfile.pdf");
                if (file.exists()) {
                    file.delete();
                }
                new Header().createPdfHeader(file.getAbsolutePath(),
                        ""+preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Header.addusereNameChank("Personal Profile");//preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(1);
                Paragraph p = new Paragraph(" ");
                LineSeparator line = new LineSeparator();
                line.setOffset(-4);
                line.setLineColor(BaseColor.LIGHT_GRAY);
                p.add(line);
                try {
                    Header.document.add(p);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                Header.addEmptyLine(1);
               /* new Header().createPdfHeader(file.getAbsolutePath(),
                        "Personal Profile");
                Header.addusereNameChank(preferences.getString(PrefConstants.CONNECTED_NAME));
                Header.addEmptyLine(2);*/
                if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                    final ArrayList<Pet> PetLists = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    final PersonalInfo personalInfoList =  PersonalInfoQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    new Individual(personalInfoList,PetLists);
                }
                else{
                    final RelativeConnection personalInfoList =  MyConnectionsQuery.fetchEmailRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));

                    final ArrayList<Pet> PetList = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
                    new Individual(personalInfoList,PetList);
                }

                Header.document.close();


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("");

                builder.setItems(dialog_items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int itemPos) {
                        String path=Environment.getExternalStorageDirectory()
                                + "/mylo/" + preferences.getInt(PrefConstants.CONNECTED_USERID) + "_" + preferences.getInt(PrefConstants.USER_ID)
                                + "/PersonalProfile.pdf";
                        switch (itemPos) {
                            case 0: //View
                                StringBuffer result = new StringBuffer();
                                if (preferences.getInt(PrefConstants.CONNECTED_USERID)==(preferences.getInt(PrefConstants.USER_ID))) {
                                    result.append(new MessageString().getProfileUser());
                                }else {
                                    result.append(new MessageString().getProfileProfile());
                                }

                                new PDFDocumentProcess(path,
                                        getActivity(), result);

                                System.out.println("\n" + result + "\n");
                                break;

                            case 1://Email
                                File f =new File(path);
                                preferences.emailAttachement(f,getActivity(),"Personal Profile");
                                break;

                            case 2://fax
                                new FaxCustomDialog(getActivity(), path).show();
                                break;

                        }
                    }

                });
                builder.create().show();
                break;

            case R.id.imgEdit:
               showCardDialog(RESULT_CAMERA_IMAGE,RESULT_SELECT_PHOTO,imgProfile,"Profile");

                break;
            case R.id.imgEditCard:
                showCardDialog(RESULT_CAMERA_IMAGE_CARD,RESULT_SELECT_PHOTO_CARD,imgCard,"Card");

                break;
            case R.id.txtCard:
                showCardDialog(RESULT_CAMERA_IMAGE_CARD,RESULT_SELECT_PHOTO_CARD,imgCard,"Card");
                break;

            case R.id.imgCard:
               /* Bitmap bitma = ((BitmapDrawable) imgCard.getDrawable()).getBitmap();
                ByteArrayOutputStream bao = new ByteArrayOutputStream();
                bitma.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                byte[] photoCard = bao.toByteArray();*/
                Intent i=new Intent(getActivity(), AddFormActivity.class);
                i.putExtra("Image",cardpath);
                i.putExtra("IsDelete",true);
                startActivityForResult(i,REQUEST_CARD);
                break;

            case R.id.txtBdate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                        DateClass d=new DateClass();
                        d.setDate(reportDate);
                        txtBdate.setText(reportDate);
                    }
                }, year, month, day);
                dpd.show();
                break;


           /* case R.id.imgGoogleSignup:

                break;*/
        }
    }

    private void showCardDialog(final int resultCameraImage, final int resultSelectPhoto, final ImageView imgProfile, final String from) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        LayoutInflater lf = (LayoutInflater) getActivity()
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
        int width = (int) (getActivity().getResources().getDisplayMetrics().widthPixels * 0.80);
        lp.width = width;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        textOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dispatchTakePictureIntent(resultCameraImage,from);
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (from.equals("Profile")) {
                    imageUriProfile = getActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    //  intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriProfile);
                } else if (from.equals("Card")) {
                    imageUriCard = getActivity().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    // intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriCard);
                }

                startActivityForResult(intent, resultCameraImage);
                dialog.dismiss();
            }
        });
        textOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, resultSelectPhoto);
                dialog.dismiss();
            }
        });
        textOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Profile"))
                {
                    imgProfile.setImageResource(R.drawable.ic_profile_defaults);
                    imagepath="";
                }
                else if(from.equals("Card"))
                {
                    imgCard.setVisibility(View.GONE);
                    rlCard.setVisibility(View.GONE);
                    txtCard.setVisibility(View.VISIBLE);
                    cardpath="";
                    //photoCard = null;
                }
                dialog.dismiss();
            }
        });
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dispatchTakePictureIntent(int resultCameraImage, String from) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile(from);
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
                startActivityForResult(takePictureIntent, resultCameraImage);
            }
        }
    }

    private File createImageFile(String from) throws IOException {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        if (from.equals("Profile")) {
            imagepath = image.getAbsolutePath();
        }
        else if (from.equals("Card"))
        {
            cardpath = image.getAbsolutePath();
        }
        return image;
    }

    private boolean validateConnection() {
        name = txtName.getText().toString().trim();
        email = txtEmail.getText().toString().trim();
        phone = txtPhone.getText().toString().trim();
        workPhone=txtWorkPhone.getText().toString().trim();
        homePhone=txtHomePhone.getText().toString().trim();
        otherRelation=txtOtherRelation.getText().toString().trim();
        address=txtAddress.getText().toString().trim();
        int i= spinnerRelation.getSelectedItemPosition();
        if (i!=0)
        relation=Relationship[i-1];

        int i1= spinnerEyes.getSelectedItemPosition();
        if (i1!=0)
        eyes=EyesList[i1-1];
        int i2= spinnerLanguage.getSelectedItemPosition();
        if (i2!=0)
        language=LangList[i2-1];
        if (language.equals("Other"))
        {
            OtherLang=txtOtherLanguage.getText().toString();
        }else{
            OtherLang="";
        }
        int i3= spinnerMarital.getSelectedItemPosition();
        if (i3!=0)
        marital_status=MaritalList[i3-1];


        liveOther=txtOther.getText().toString();
        idnumber=txtIdNumber.getText().toString();
        height=txtHeight.getText().toString();
        weight=txtWeight.getText().toString();
        profession=txtProfession.getText().toString();
        employed=txtEmployed.getText().toString();
        manager_phone=txttelephone.getText().toString();
        religion=txtReligion.getText().toString();

        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        } /*else if (height.length()!=0 && height.length()<5)
        {
            txtHeight.setError("Enter height");
            showAlert("Enter correct height",  getActivity());
        }*/
       /* else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
       else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        }*/ else if (phone.length()!=0&&phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        }
        else if (manager_phone.length()!=0 && manager_phone.length() < 10) {
            txttelephone.setError("Mobile number should be 10 digits");
            showAlert("Mobile number should be 10 digits", getActivity());
        }
      /*  else if (homePhone.equals("")) {
            txtHomePhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        }else if (homePhone.length() < 10) {
            txtHomePhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        }*/
        else {
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
        idnumber=txtIdNumber.getText().toString();
        if (spinner.getSelectedItem()!=null) {
            country = spinner.getSelectedItem().toString();
        }
        else{
            country="";
        }
        address=txtAddress.getText().toString().trim();

        height=txtHeight.getText().toString();
        weight=txtWeight.getText().toString();
        profession=txtProfession.getText().toString();
        employed=txtEmployed.getText().toString();
        manager_phone=txttelephone.getText().toString();
        religion=txtReligion.getText().toString();
        liveOther=txtOther.getText().toString();

        int indexValue = spinnerEyes.getSelectedItemPosition();
        if (indexValue!=0)
         eyes =EyesList[indexValue-1];

        int indexValuex = spinnerLanguage.getSelectedItemPosition();
        if (indexValuex!=0)
        language =LangList[indexValuex-1];
        if (language.equals("Other"))
        {
            OtherLang=txtOtherLanguage.getText().toString();
        }else{
            OtherLang="";
        }

        int indexValues = spinnerMarital.getSelectedItemPosition();
        if (indexValues!=0)
        marital_status =MaritalList[indexValues-1];


        if (name.equals("")) {
            txtName.setError("Please Enter Name");
            showAlert("Please Enter Name", getActivity());
        } else if (email.equals("")) {
            txtEmail.setError("Please Enter email");
            showAlert("Please Enter email",  getActivity());
        } else if (!email.trim().matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            txtEmail.setError("Please enter valid email");
            showAlert("Please enter valid email",  getActivity());
        }/* else if (height.length()!=0 && height.length()<5)
        {
            txtHeight.setError("Enter height");
            showAlert("Enter correct height",  getActivity());
        }*/
       /* else if (address.equals("")) {
            txtAddress.setError("Please Enter Address");
            showAlert("Please Enter Address",  getActivity());
        }
        else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country",  getActivity());
        }*/  else if (phone.length()!=0&&phone.length() < 10) {
            txtPhone.setError("Phone number should be 10 digits");
            showAlert("Phone number should be 10 digits",  getActivity());
        }
        else if (manager_phone.length()!=0 && manager_phone.length() < 10) {
            txttelephone.setError("Mobile number should be 10 digits");
            showAlert("Mobile number should be 10 digits", getActivity());
        }/*else if (bdate.equals("")) {
            txtBdate.setError("Please Enter Birth date");
            showAlert("Please Enter Birth date",  getActivity());
        }*/ else {
            return true;
        }
        return false;
    }

    private void editToConnection(String photo, String photoCard) {
        if (preferences.getString(PrefConstants.CONNECTED_USEREMAIL).equals(preferences.getString(PrefConstants.USER_EMAIL))) {
            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.USER_ID), name, email, address, phone," "," ", "Self", imagepath," ", 1, 2, otherRelation,height,weight,eyes,profession,employed,language,marital_status,religion,veteran,idnumber,pet,manager_phone, cardpath,english,child,friend,grandParent,parent,spouse,other,liveOther,live,OtherLang);
            if (flag == true) {
                Toast.makeText(getActivity(), "You have edited connection Successfully", Toast.LENGTH_SHORT).show();
                preferences.putString(PrefConstants.CONNECTED_NAME,name);
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
        }
        else{
            int indexValuex = spinnerRelation.getSelectedItemPosition();
           String relation =Relationship[indexValuex-1];
            Boolean flag = MyConnectionsQuery.updateMyConnectionsData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, email, address, phone,homePhone,workPhone,relation , imagepath,"", 1, 2, otherRelation,height,weight,eyes,profession,employed,language,marital_status,religion,veteran,idnumber,pet, manager_phone, cardpath, english,child,friend,grandParent,parent,spouse,other,liveOther,live, OtherLang);
            if (flag == true) {
                Toast.makeText(getActivity(), "You have edited connection Successfully", Toast.LENGTH_SHORT).show();
                preferences.putString(PrefConstants.CONNECTED_NAME,name);
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
        }/* else if (email.equals("")) {
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
       *//* else if (country.equals("")) {
            spinner.setError("Please Select Country");
            showAlert("Please Select Country",  getActivity());
        } *//*else if (phone.equals("")) {
            txtPhone.setError("Please Enter Phone");
            showAlert("Please Enter Phone",  getActivity());
        } */else if (phone.length()!=0&&phone.length() < 10) {
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
     ImageView profileCard = (ImageView) rootview.findViewById(R.id.imgCard);
     if (REQUEST_PET == requestCode) {
         setPetData();
         ListPet.requestFocus();
     }

     if (requestCode == RESULT_SELECT_PHOTO && data!=null  ) {
         try {
             final Uri imageUri = data.getData();
             final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
             final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            // profileImage.setImageBitmap(selectedImage);
             imageLoaderProfile.displayImage(String.valueOf(imageUri),imgProfile,displayImageOptionsProfile);
             storeImage(selectedImage,"Profile");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

        }
        if (requestCode == RESULT_CAMERA_IMAGE ) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(), imageUriProfile);
                String imageurl = getRealPathFromURI(imageUriProfile);
                Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
                imageLoaderProfile.displayImage(String.valueOf(imageUriProfile),imgProfile,displayImageOptionsProfile);
               // profileImage.setImageBitmap(bitmap);
                storeImage(bitmap,"Profile");
            } catch (Exception e) {
                e.printStackTrace();
            }
        /* Bundle extras = data.getExtras();
         Bitmap imageBitmap = (Bitmap) extras.get("data");
         imgProfile.setImageBitmap(imageBitmap);

         storeImage(imageBitmap,"Profile");*/

        }
     if (requestCode == RESULT_SELECT_PHOTO_CARD  && data!=null) {
         try {
             final Uri imageUri = data.getData();
             final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
             final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
             imageLoaderCard.displayImage(String.valueOf(imageUri),imgCard,displayImageOptionsCard);
            // profileCard.setImageBitmap(selectedImage);
             rlCard.setVisibility(View.VISIBLE);
             imgCard.setVisibility(View.VISIBLE);
             txtCard.setVisibility(View.GONE);
             storeImage(selectedImage,"Card");
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }

     }
     if (requestCode == RESULT_CAMERA_IMAGE_CARD ) {
       /*  Bundle extras = data.getExtras();
         Bitmap imageBitmap = (Bitmap) extras.get("data");
         imgCard.setImageBitmap(imageBitmap);
        // String imageurl = getRealPathFromURI(imageUriCard);
         //
         rlCard.setVisibility(View.VISIBLE);
         imgCard.setVisibility(View.VISIBLE);
         txtCard.setVisibility(View.GONE);
         FileOutputStream outStream = null;
         storeImage(imageBitmap,"Card");
*/
         try {
             Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                     getActivity().getContentResolver(), imageUriCard);

             String imageurl = getRealPathFromURI(imageUriCard);
             Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
             imageLoaderCard.displayImage(String.valueOf(imageUriCard),imgCard,displayImageOptionsCard);
           //  profileCard.setImageBitmap(bitmap);
             //
             rlCard.setVisibility(View.VISIBLE);
             imgCard.setVisibility(View.VISIBLE);
             txtCard.setVisibility(View.GONE);
             storeImage(bitmap,"Card");
         } catch (Exception e) {
             e.printStackTrace();
         }

     }
     if (requestCode == REQUEST_CARD && data!=null) {
         if (data.getExtras().getString("Card").equals("Delete")) {
             rlCard.setVisibility(View.GONE);
             imgCard.setVisibility(View.GONE);
             txtCard.setVisibility(View.VISIBLE);
             cardpath="";
         }
         //photoCard=null;
     }
 }

    private void setPetData() {
        final ArrayList allergyList = new ArrayList();
        final ArrayList<Pet> AllargyLists = PetQuery.fetchAllRecord(preferences.getInt(PrefConstants.CONNECTED_USERID));
        if (AllargyLists.size() != 0) {
            ListPet.setVisibility(View.VISIBLE);
            for (int i = 0; i < AllargyLists.size(); i++) {
                Pet a = AllargyLists.get(i);
                String allergy = "Pet Name: " + a.getName() + "\nBreed: " + a.getBreed() + "\nColor: " + a.getColor()+ "\nVeterinarian: " + a.getVeterian()+ "\nCaretaker: " + a.getGuard()+ "\nMicrochip Number: " + a.getChip()+ "\nBirthdate: " + a.getBdate()+ "\nNotes: " + a.getNotes();
                allergyList.add(allergy);
            }
            if (allergyList.size() != 0) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_medicalinfo, R.id.txtInfo, allergyList);
                ListPet.setAdapter(adapter);
                ListPet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        ImageView imgEdit= (ImageView) view.findViewById(R.id.imgEdit);
                        ImageView imgDelete= (ImageView) view.findViewById(R.id.imgDelete);
                        imgEdit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Pet a=AllargyLists.get(position);
                                Intent allergyIntent = new Intent(getActivity(), AddPetActivity.class);
                                allergyIntent.putExtra("FROM","Update");
                                allergyIntent.putExtra("PetObject",a);
                                startActivityForResult(allergyIntent, REQUEST_PET);
                            }
                        });

                        imgDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Pet a=AllargyLists.get(position);
                                boolean flag= PetQuery.deleteRecord(a.getId());
                                if(flag==true)
                                {
                                    Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                                    setPetData();
                                    ListPet.requestFocus();
                                }
                            }
                        });
                    }
                });
            }
        }
        else{
            ListPet.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.chkChild:
                if (isChecked == true)
                    child = "Yes";
                else
                    child = "No";
                break;

            case R.id.chkFriend:
                if (isChecked == true)
                    friend = "Yes";
                else
                    friend = "No";
                break;

            case R.id.chkGrandParent:
                if (isChecked == true)
                    grandParent = "Yes";
                else
                    grandParent = "No";
                break;

            case R.id.chkParent:
                if (isChecked == true)
                    parent = "Yes";
                else
                    parent = "No";
                break;
            case R.id.chkSpouse:
                if (isChecked == true)
                    spouse = "Yes";
                else
                    spouse = "No";
                break;

        }
    }
    private Bitmap imageOreintationValidator(Bitmap bitmap, String path) {

        ExifInterface ei;
        try {
            ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(bitmap, 180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotateImage(bitmap, 270);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {

        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                    matrix, true);
        } catch (OutOfMemoryError err) {
            err.printStackTrace();
        }
        return bitmap;
    }
    private String getRealPathFromURI(Uri imageUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getActivity().getContentResolver().query(imageUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    private void storeImage(Bitmap selectedImage, String profile) {

        FileOutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory(),
                "/MYLO/MYLO/");
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {

            if (profile.equals("Profile")) {
                imagepath = path + "/MYLO_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
            }
            else   if (profile.equals("Card")){
                cardpath = path + "/MYLO_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(cardpath);
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
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

    @Override
    public void onResume() {
        super.onResume();
       /* imagepath=personalInfo.getPhoto();
        if (!imagepath.equals("")) {
            File imgFile = new File(imagepath);
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgProfile.setImageBitmap(myBitmap);
            }
        }
        else{
            imgProfile.setImageResource(R.drawable.ic_profile_defaults);
        }
              *//*  byte[] photo=personalInfo.getPhoto();
                Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
               imgProfile.setImageBitmap(bmp);*//*
        cardpath=personalInfo.getPhotoCard();
        if (!personalInfo.getPhotoCard().equals("")) {
            File imgFile1 = new File(personalInfo.getPhotoCard());
            if (imgFile1.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
                imgCard.setImageBitmap(myBitmap);
            }
                   *//* byte[] photoCard = personalInfo.getPhotoCard();
                    Bitmap bmps = BitmapFactory.decodeByteArray(photoCard, 0, photoCard.length);*//*
            // imgCard.setImageBitmap(bmps);
            imgCard.setVisibility(View.VISIBLE);
            rlCard.setVisibility(View.VISIBLE);
            txtCard.setVisibility(View.GONE);
        }
        else{
            imgCard.setVisibility(View.GONE);
            rlCard.setVisibility(View.GONE);
            txtCard.setVisibility(View.VISIBLE);
        }*/
    }
}
