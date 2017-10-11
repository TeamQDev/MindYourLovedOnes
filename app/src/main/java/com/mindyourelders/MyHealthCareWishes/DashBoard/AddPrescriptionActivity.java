package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.customview.MySpinner;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.database.DosageQuery;
import com.mindyourelders.MyHealthCareWishes.database.PrescribeImageQuery;
import com.mindyourelders.MyHealthCareWishes.database.PrescriptionQuery;
import com.mindyourelders.MyHealthCareWishes.model.Dosage;
import com.mindyourelders.MyHealthCareWishes.model.PrescribeImage;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;


public class AddPrescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    Context context = this;
    ImageView imgBack, imgAddDosage, imgAddPhoto,imgDone;
    ListView ListDosage, ListPhoto;
    ArrayList<Dosage> dosageList = new ArrayList<>();
    ArrayList<PrescribeImage> imageList = new ArrayList<>();
    RelativeLayout llAddPrescription;
    public static final int RESULT_PRES = 100;
    TextView txtName, txtDate,txtPurpose;
    EditText etNote;
    MySpinner spinner;
    String[] FormList = {"Tablet", "Capsule", "Pills", "Powder", "Liquid", "Syrup", "Cream", "Gel", "Lotion", "Drops", "Other"};
    private static int RESULT_CAMERA_IMAGE = 1;
    private static int RESULT_SELECT_PHOTO = 2;
    String imagepath = "";//

    Preferences preferences;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        initComponent();
        initUI();
        initListener();
    }

    private void initComponent() {
        preferences=new Preferences(context);
        dbHelper=new DBHelper(context);
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
    }

    private void initUI() {
        txtName = (TextView) findViewById(R.id.txtName);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtPurpose= (TextView) findViewById(R.id.txtPurpose);
        spinner = (MySpinner) findViewById(R.id.spinner);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgAddDosage = (ImageView) findViewById(R.id.imgAddDosage);
        imgAddPhoto = (ImageView) findViewById(R.id.imgAddPhoto);
        llAddPrescription = (RelativeLayout) findViewById(R.id.llAddPrescription);
        ListDosage = (ListView) findViewById(R.id.ListDosage);
        ListPhoto = (ListView) findViewById(R.id.ListPhoto);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        etNote= (EditText) findViewById(R.id.etNote);

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, FormList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setHint("Dosage Form");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.txtDate:
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                dpd.show();
                break;

            case R.id.imgDone:
                int unique=generateRandom();
                String doctor=txtName.getText().toString().trim();
                String purpose=txtPurpose.getText().toString().trim();
                String note=etNote.getText().toString().trim();
                String date=txtDate.getText().toString().trim();
                Boolean flag = PrescriptionQuery.insertPrescriptionData(preferences.getInt(PrefConstants.CONNECTED_USERID),doctor,purpose,note,date,dosageList,imageList,unique);
                if (flag == true) {
                    Toast.makeText(context, "Prescription Added Succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
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
                break;
            case R.id.imgAddDosage:
                showInputDialog(context);
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

    private void showInputDialog(final Context context) {
        final Dialog customDialog;
        customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_input_dose);
        customDialog.setCancelable(false);
        final EditText etMedicine = (EditText) customDialog.findViewById(R.id.etMedicine);
        final EditText etDose = (EditText) customDialog.findViewById(R.id.etDose);
        TextView txtMedicine = (TextView) customDialog.findViewById(R.id.txtMedicine);
        TextView txtDose = (TextView) customDialog.findViewById(R.id.txtDose);

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
               /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                String currentDateandTime = sdf.format(new Date());*/
                if (medicine.length() != 0) {
                    Dosage dosage = new Dosage();
                    dosage.setDose(dose);
                    dosage.setMedicine(medicine);
                    dosageList.add(dosage);
                    customDialog.dismiss();
                    setDosageData();
                } else {
                    Toast.makeText(context, "Enter Medicine", Toast.LENGTH_SHORT).show();
                }
            }
        });
        customDialog.show();
    }

    private void setDosageData() {
        DosageAdapter adapter = new DosageAdapter(context, dosageList);
        ListDosage.setAdapter(adapter);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView profileImage = (ImageView) findViewById(R.id.imgProfile);
        if (requestCode == RESULT_SELECT_PHOTO && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                PrescribeImage p = new PrescribeImage();
                p.setImage(byteArray);
                imageList.add(p);
                setImageListData();
                // profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE && null != data) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream streams = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, streams);
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

            }


        }

    }

    private void setImageListData() {
        ImageAdapter adapter = new ImageAdapter(context, imageList);
        ListPhoto.setAdapter(adapter);
    }
}
