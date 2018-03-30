package com.mindyourlovedones.healthcare.DashBoard;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mindyourlovedones.healthcare.HomeActivity.R;
import com.mindyourlovedones.healthcare.customview.MySpinner;
import com.mindyourlovedones.healthcare.database.DBHelper;
import com.mindyourlovedones.healthcare.database.DosageQuery;
import com.mindyourlovedones.healthcare.database.PrescribeImageQuery;
import com.mindyourlovedones.healthcare.database.PrescriptionQuery;
import com.mindyourlovedones.healthcare.model.Dosage;
import com.mindyourlovedones.healthcare.model.PrescribeImage;
import com.mindyourlovedones.healthcare.model.Prescription;
import com.mindyourlovedones.healthcare.utility.PrefConstants;
import com.mindyourlovedones.healthcare.utility.Preferences;
import com.mindyourlovedones.healthcare.utility.DialogManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;


public class AddPrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CARD =50 ;
    Context context = this;
    String currentImage=null;
    Uri imageUriProfile=null;
    ContentValues values=null;
    int userid;

    ImageView imgBack, imgAddDosage, imgAddPhoto,imgDone;
    ListView ListDosage, ListPhoto;
    ArrayList<Dosage> dosageList = new ArrayList<>();
    ArrayList<PrescribeImage> imageList = new ArrayList<>();
    ArrayList<PrescribeImage> imageListOld = new ArrayList<>();
    RelativeLayout llAddPrescription;
    String pre = "No";
    ToggleButton tbPre;
    TextInputLayout tilTitle;
    public static final int RESULT_PRES = 100;
    TextView txtName, txtDate,txtPurpose,txtNote,txtRX,txtPre,txtMedicine,txtDose,txtFrequency,txtTitle;
    EditText etNote;
    MySpinner spinner;
    RadioGroup rgCounter;
    RadioButton rbYes, rbNo;
    String[] FormList = {"Capsule","Cream","Drops","Gel","Liquid","Lotion","Pills","Powder","Syrup","Tablet", "Other"};
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    String imagepath = "";//
    String counter="No";

    Preferences preferences;
    DBHelper dbHelper;
    int unique;
    boolean isEdit;
    int id,colid,dosageid,imageid;

    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        initComponent();
        initImageLoader();
        initUI();
        initListener();
    }

    private void initImageLoader() {
        displayImageOptions = new DisplayImageOptions.Builder() // resource
                .resetViewBeforeLoading(true) // default
                .cacheInMemory(true) // default
                .cacheOnDisk(true) // default
                .showImageOnLoading(R.drawable.ic_profile_defaults)
                .considerExifParams(false) // default
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED) // default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .displayer(new SimpleBitmapDisplayer()) // default //for square SimpleBitmapDisplayer()
                .handler(new Handler()) // default
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(displayImageOptions)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context,preferences.getString(PrefConstants.CONNECTED_USERDB));
        PrescriptionQuery p=new PrescriptionQuery(context,dbHelper);
        PrescribeImageQuery i=new PrescribeImageQuery(context,dbHelper);
        DosageQuery d=new DosageQuery(context,dbHelper);

    }

    private void initListener() {
        llAddPrescription.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgAddDosage.setOnClickListener(this);
        imgAddPhoto.setOnClickListener(this);
        imgDone.setOnClickListener(this);
        txtDate.setOnClickListener(this);

        rgCounter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbYes) {
                    pre = "Yes";
                } else if (checkedId == R.id.rbNo) {
                    pre = "No";
                }
            }
        });

    }

    private void initUI() {
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        txtName = (TextView) findViewById(R.id.txtName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtPurpose= (TextView) findViewById(R.id.txtPurpose);
        txtPre = (TextView) findViewById(R.id.txtPre);
        txtRX= (TextView) findViewById(R.id.txtRX);
        txtMedicine= (TextView) findViewById(R.id.txtMedicine);
        txtDose= (TextView) findViewById(R.id.txtDose);
        txtFrequency= (TextView) findViewById(R.id.txtFrequency);

        tbPre= (ToggleButton) findViewById(R.id.tbPre);
        rgCounter = (RadioGroup) findViewById(R.id.rgCounter);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbNo = (RadioButton) findViewById(R.id.rbNo);

        spinner = (MySpinner) findViewById(R.id.spinner);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgAddDosage = (ImageView) findViewById(R.id.imgAddDosage);
        imgAddPhoto = (ImageView) findViewById(R.id.imgAddPhoto);
        llAddPrescription = (RelativeLayout) findViewById(R.id.llAddPrescription);
        ListDosage = (ListView) findViewById(R.id.ListDosage);
        ListPhoto = (ListView) findViewById(R.id.ListPhoto);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        etNote= (EditText) findViewById(R.id.etNote);
        txtNote=(TextView) findViewById(R.id.txtNote);

         ListPhoto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                         Intent i=new Intent(context,ViewImageActivity.class);
                         i.putExtra("Image",imageList.get(position).getImage());
                         currentImage=imageList.get(position).getImage();
                         startActivityForResult(i,REQUEST_CARD);
             }
         });

        tbPre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true)
                    pre = "Yes";
                else
                    pre = "No";
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, FormList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Dosage Form");

        tilTitle= (TextInputLayout) findViewById(R.id.tilTitle);
       /* tilTitle.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilTitle.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });
*/
        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            Prescription p= (Prescription) i.getExtras().getSerializable("PrescriptionObject");
            isEdit=i.getExtras().getBoolean("IsEdit");
            if (isEdit==true) {
                txtTitle.setText("Update Prescription");
                id = p.getUnique();
                userid=p.getUserid();
                colid=p.getId();
                txtName.setText(p.getDoctor());
                txtDate.setText(p.getDates());
                etNote.setText(p.getNote());
                if (p.getPre().equals("Yes")) {
                    tbPre.setChecked(true);
                    //rbYes.setChecked(true);
                } else if (p.getPre().equals("No")) {
                    //rbNo.setChecked(true);
                    tbPre.setChecked(false);
                }
                txtRX.setText(p.getRX());
                txtPurpose.setText(p.getPurpose());
                txtDose.setText(p.getDose());
                txtFrequency.setText(p.getFrequency());
                txtMedicine.setText(p.getMedicine());
                dosageList = p.getDosageList();
                imageList = PrescribeImageQuery.fetchAllImageRecord(p.getUserid(),p.getUnique());
                imageListOld=imageList;
                setDosageData();
                setImageListData();
            }
            else{
                txtTitle.setText("Add Prescription");
            }
        }

        ListDosage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                ImageView imgEdit= (ImageView) view.findViewById(R.id.imgEdit);
                ImageView imgDelete= (ImageView) view.findViewById(R.id.imgDelete);
                imgEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dosage a=dosageList.get(position);
                        showInputDialog(context,a, true);
                    }
                });

                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dosage a=dosageList.get(position);
                        boolean flag= DosageQuery.deleteRecords(a.getId());
                        if(flag==true)
                        {
                            Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                           dosageList.remove(a);
                            setDosageData();
                            ListDosage.requestFocus();
                        }
                    }
                });
            }
        });
    }

    private void getDosageData() {
        dosageList=DosageQuery.fetchAllDosageRecord(preferences.getInt(PrefConstants.CONNECTED_USERID),unique);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                DialogManager.closeKeyboard(AddPrescriptionActivity.this);
                finish();
                break;
           /* case R.id.txtDate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, month, dayOfMonth);
                        long selectedMilli = newDate.getTimeInMillis();

                        Date datePickerDate = new Date(selectedMilli);
                        String reportDate=new SimpleDateFormat("d-MMM-yyyy").format(datePickerDate);

                        DateClass d=new DateClass();
                        d.setDate(reportDate);
                        txtDate.setText(reportDate);
                    }
                }, year, month, day);
                dpd.show();
                break;*/

            case R.id.imgDone:
                unique=generateRandom();
                String doctor=txtName.getText().toString().trim();
                String purpose=txtPurpose.getText().toString().trim();
                String note=etNote.getText().toString().trim();
                String date=txtDate.getText().toString().trim();
                String rx=txtRX.getText().toString().trim();
                String dose=txtDose.getText().toString().trim();
                String frequency=txtFrequency.getText().toString().trim();
                String medicine=txtMedicine.getText().toString().trim();
                if (medicine.equals(""))
                {
                    Toast.makeText(context,"Please Enter Medicine",Toast.LENGTH_SHORT).show();
                    txtMedicine.setError("Please Enter Medicine");
                }else {
                    if (isEdit == false) {
                        Boolean flag = PrescriptionQuery.insertPrescriptionData(preferences.getInt(PrefConstants.CONNECTED_USERID), doctor, purpose, note, date, dosageList, imageList, unique, pre, rx, dose, frequency, medicine);
                        if (flag == true) {
                            Toast.makeText(context, "Prescription Added Succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddPrescriptionActivity.this);
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Boolean flag = PrescriptionQuery.updatePrescriptionData(colid, unique, doctor, purpose, note, date, dosageList, imageList, preferences.getInt(PrefConstants.CONNECTED_USERID), pre, rx, dose, frequency, medicine, imageListOld);
                        if (flag == true) {
                            Toast.makeText(context, "Prescription Updated Succesfully", Toast.LENGTH_SHORT).show();
                            DialogManager.closeKeyboard(AddPrescriptionActivity.this);
                        } else {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }

                Intent i=new Intent();
               /* i.putExtra("Name",txtName.getText().toString().trim());
                i.putExtra("Date",txtDate.getText().toString().trim());
                i.putExtra("Dosage", dosageList);
                i.putExtra("Image",imageList);*/
               /*String date=txtDate.getText().toString().trim();
                Prescription p=new Prescription();
                p.setPrescriptionImageList(imageList);
                p.setDoctor(txtName.getText().toString().trim());
                p.setDates(date);
                p.setDosageList(dosageList);
                i.putExtra("PrObj", p);
*/
                setResult(RESULT_PRES,i);
                finish();
                }
                break;
            case R.id.imgAddDosage:
                showInputDialog(context, null,false);
                break;
            case R.id.imgAddPhoto:
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                LayoutInflater lf = (LayoutInflater) context
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
                int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.80);
                lp.width = width;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                dialog.getWindow().setAttributes(lp);
                dialog.show();
                textOption1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // dispatchTakePictureIntent();
                        values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, "New Picture");
                        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        imageUriProfile = getContentResolver().insert(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        //  intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriProfile);

                        startActivityForResult(intent, RESULT_CAMERA_IMAGE);
                        dialog.dismiss();
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

        }
    }

    private int generateRandom() {
        Random r = new Random();
        int randomNumber = r.nextInt(500);

        return randomNumber;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
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

    private File createImageFile() {
        // Create an image file name
        String imageFileName = "JPEG_PROFILE";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        imagepath = image.getAbsolutePath();
        return image;
    }

    private void showInputDialog(final Context context, final Dosage a, final boolean b) {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_dose);
        customDialog.setCancelable(false);
        final EditText etMedicine = (EditText) customDialog.findViewById(R.id.etMedicine);
        final EditText etDose = (EditText) customDialog.findViewById(R.id.etDose);
        final EditText etFrequency= (EditText) customDialog.findViewById(R.id.etFrequency);
        final EditText etRX= (EditText) customDialog.findViewById(R.id.etRX);
        TextView txtMedicine = (TextView) customDialog.findViewById(R.id.txtMedicine);
        TextView txtDose = (TextView) customDialog.findViewById(R.id.txtDose);

        if (b==true) {
            if (a != null) {
                etMedicine.setText(a.getMedicine());
                etDose.setText(a.getDose());
                etFrequency.setText(a.getFrequency());
                etRX.setText(a.getRx());
            }
        }
        TextView btnAdd = (TextView) customDialog.findViewById(R.id.btnYes);
        TextView btnCancel = (TextView) customDialog.findViewById(R.id.btnNo);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dose = etDose.getText().toString();
                String medicine = etMedicine.getText().toString();
                String frequency=etFrequency.getText().toString();
                String rx=etRX.getText().toString();
               /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String currentDateandTime = sdf.format(new Date());*/
               if (b==true)
               {
                   if (medicine.length() != 0) {

                       a.setDose(dose);
                       a.setMedicine(medicine);
                       a.setFrequency(frequency);
                       a.setRx(rx);
                       //dosageList.add(dosage);
                       customDialog.dismiss();
                       setDosageData();
                   } else {
                       Toast.makeText(context, "Enter Medicine", Toast.LENGTH_SHORT).show();
                   }
               }
               else{

                if (medicine.length() != 0) {
                    Dosage dosage = new Dosage();
                    dosage.setDose(dose);
                    dosage.setMedicine(medicine);
                    dosage.setFrequency(frequency);
                    dosage.setRx(rx);
                    dosageList.add(dosage);
                    customDialog.dismiss();
                    setDosageData();
                } else {
                    Toast.makeText(context, "Enter Medicine", Toast.LENGTH_SHORT).show();
                }
               }
            }
        });
        customDialog.show();
    }

    private void setDosageData() {
        if (dosageList.size()!=0) {
            DosageAdapter adapter = new DosageAdapter(context, dosageList);
            ListDosage.setAdapter(adapter);
            ListDosage.setVisibility(View.VISIBLE);
        }
        else{
            ListDosage.setVisibility(View.GONE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // ImageView profileImage = (ImageView) findViewById(R.id.imgProfile);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
               // profileImage.setImageBitmap(selectedImage);
                storeImage(selectedImage,"Profile");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

           /* try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 10, stream);
                byte[] byteArray = stream.toByteArray();
                PrescribeImage p = new PrescribeImage();
                p.setImage(byteArray);
                imageList.add(p);
                setImageListData();
                // profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        } else if (requestCode == RESULT_CAMERA_IMAGE) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUriProfile);
                //  String imageurl = getRealPathFromURI(imageUriProfile);
                // Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
              //  imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);
             //   profileImage.setImageBitmap(thumbnail);
                storeImage(thumbnail,"Profile");



            } catch (Exception e) {
                e.printStackTrace();
            }
/*
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream streams = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 10, streams);
            byte[] byteArrays = streams.toByteArray();
            PrescribeImage p = new PrescribeImage();
            p.setImage(byteArrays);
            imageList.add(p);
            setImageListData();
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWPrescription/");
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

                imagepath = path + "/MHCWPrescription_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagepath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                outStream.write(byteArray);
                outStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }*/
        }
        else if(requestCode == REQUEST_CARD )
        {
            if (data!=null) {
                if (data.getExtras().getString("Prescription").equals("Delete")) {
                    String photo = data.getExtras().getString("Photo");
                    for (int i = 0; i < imageList.size(); i++) {
                        if (imageList.get(i).getImage().equals(photo)) {
                            boolean flag= PrescribeImageQuery.deleteImageRecord(imageList.get(i).getId());
                            if(flag==true)
                            {
                                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                                imageList.remove(imageList.get(i));
                                setImageListData();
                            }
                        }
                    }

                   // setImageListData();
                }
            }
        }
    }

    private void storeImage(Bitmap selectedImage, String profile) {

        FileOutputStream outStream = null;
        File file = new File(preferences.getString(PrefConstants.CONNECTED_PATH));
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {

            imagepath = "MYLO_" + String.valueOf(System.currentTimeMillis())
                    + ".jpg";
            // Write to SD Card
            outStream = new FileOutputStream(preferences.getString(PrefConstants.CONNECTED_PATH)+imagepath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 40, stream);
            byte[] byteArray = stream.toByteArray();
            outStream.write(byteArray);
            outStream.close();

            PrescribeImage p = new PrescribeImage();
            p.setImage(preferences.getString(PrefConstants.CONNECTED_PATH)+imagepath);
            imageList.add(p);
            setImageListData();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void setImageListData() {
        ImageAdapter adapter = new ImageAdapter(context, imageList);
        ListPhoto.setAdapter(adapter);
    }



}
