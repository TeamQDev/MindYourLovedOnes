package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.database.CardQuery;
import com.mindyourelders.MyHealthCareWishes.database.DBHelper;
import com.mindyourelders.MyHealthCareWishes.model.Card;
import com.mindyourelders.MyHealthCareWishes.utility.PrefConstants;
import com.mindyourelders.MyHealthCareWishes.utility.Preferences;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    ContentValues values;
    Uri imageUri;
    Context context = this;
    TextView txtName, txttype,txtTitle;
    TextInputLayout tilTitle;
    Bitmap bitmap1,bitmap2;
    ImageView imgDone, imgBack, imgEdit1, imgEdit2, imgfrontCard, imgBackCard;
    private static int RESULT_CAMERA_IMAGE1 = 1;
    private static int RESULT_SELECT_PHOTO1 = 2;
    private static int RESULT_CAMERA_IMAGE2 = 3;
    private static int RESULT_SELECT_PHOTO2 = 4;
    String imagepath = "";//
    String name, type;
    Boolean isEdit=false;
    int id;

    Preferences preferences;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        initComponent();
        initUI();
        initListener();
    }

    private void initComponent() {
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context);
        CardQuery c = new CardQuery(context, dbHelper);
    }

    private void initListener() {
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgEdit1.setOnClickListener(this);
        imgEdit2.setOnClickListener(this);
    }

    private void initUI() {
        txtTitle= (TextView) findViewById(R.id.txtTitle);
        imgDone = (ImageView) findViewById(R.id.imgDone);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgEdit1 = (ImageView) findViewById(R.id.imgEdit1);
        imgEdit2 = (ImageView) findViewById(R.id.imgEdit2);
        imgfrontCard = (ImageView) findViewById(R.id.imgFrontCard);
        imgBackCard = (ImageView) findViewById(R.id.imgBackCard);

        txtName = (TextView) findViewById(R.id.txtName);
        txttype = (TextView) findViewById(R.id.txtType);
        tilTitle = (TextInputLayout) findViewById(R.id.tilTitle);
        tilTitle.setHintEnabled(false);
        txtName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tilTitle.setHintEnabled(true);
                txtName.setFocusable(true);

                return false;
            }
        });

        Intent i=getIntent();
        if (i.getExtras()!=null)
        {
            txtTitle.setText("Update Insurance Card");
            if (i.getExtras().getBoolean("IsEdit")==true)
            {
                isEdit=true;
            }
            Card card= (Card) i.getExtras().getSerializable("CardObject");
            txtName.setText(card.getName());
            txttype.setText(card.getType());
            id=card.getId();
            byte[] photo=card.getImgFront();
            Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
           imgfrontCard.setImageBitmap(bmp);

            byte[] photo1=card.getImgBack();
            Bitmap bmp1 = BitmapFactory.decodeByteArray(photo1, 0, photo1.length);
            imgBackCard.setImageBitmap(bmp1);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgDone:
                name = txtName.getText().toString();
                type = txttype.getText().toString();
                Bitmap bitmap1 = ((BitmapDrawable) imgfrontCard.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 40, baos);
               /* int newHeight = bitmap1.getHeight();
                int newWidth = bitmap1.getWidth();
                int ratio = bitmap1.getWidth() / bitmap1.getHeight();
                if (ratio==0)
                {
                    ratio=1;
                }
                if (bitmap1.getWidth() > 800) {
                    newWidth = 800;
                    newHeight = ratio * newWidth;
                }
                Bitmap.createBitmap(bitmap1,bitmap1.getWidth()-100,bitmap1.getHeight()-100, newWidth, newHeight).compress(Bitmap.CompressFormat.JPEG, 100, baos);
*/
                byte[] photo1 = baos.toByteArray();
               Bitmap bitmap2 = ((BitmapDrawable) imgBackCard.getDrawable()).getBitmap();
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 40, baos2);
               /* newHeight = bitmap2.getHeight();
                newWidth = bitmap2.getWidth();
                ratio = bitmap2.getWidth() / bitmap2.getHeight();
                if (ratio==0)
                {
                    ratio=1;
                }
                if (bitmap2.getWidth() > 800) {
                    newWidth =800;
                    newHeight = ratio * newWidth;
                }
                Bitmap.createBitmap(bitmap2,bitmap2.getWidth()+100, bitmap2.getHeight()+100, newWidth, newHeight).compress(Bitmap.CompressFormat.JPEG, 100, baos2);
*/
                byte[] photo2 = baos2.toByteArray();
if (isEdit==false) {
    boolean flag = CardQuery.insertInsuranceCardData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, type, photo1, photo2);
    if (flag) {
        Toast.makeText(context, "You have added insurance information successfully", Toast.LENGTH_SHORT).show();
        finish();
    } else {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }
    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
}else if (isEdit==true)
{
    boolean flag = CardQuery.updateInsuranceCardData(id, name, type, photo1, photo2);
    if (flag) {
        Toast.makeText(context, "You have updated insurance information successfully", Toast.LENGTH_SHORT).show();
        finish();
    } else {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }
    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
}
                break;

            case R.id.imgBack:
                finish();
                break;
            case R.id.imgEdit1:
                showDialogs(RESULT_CAMERA_IMAGE1, RESULT_SELECT_PHOTO1);

                break;
            case R.id.imgEdit2:
                showDialogs(RESULT_CAMERA_IMAGE2, RESULT_SELECT_PHOTO2);
                break;


        }
    }

    private void showDialogs(final int resultCameraImage, final int resultSelectPhoto) {
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
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, resultCameraImage);
               // dispatchTakePictureIntent(resultCameraImage);
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
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dispatchTakePictureIntent(int resultCameraImage) {
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
                startActivityForResult(takePictureIntent, resultCameraImage);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imgfrontCard = (ImageView) findViewById(R.id.imgFrontCard);
        ImageView imgBackCard = (ImageView) findViewById(R.id.imgBackCard);
        if (requestCode == RESULT_SELECT_PHOTO1 && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmap1=selectedImage;
                imgfrontCard.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE1) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                      getContentResolver(), imageUri);
                imgfrontCard.setImageBitmap(thumbnail);
                //  String imageurl = getRealPathFromURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgfrontCard.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWInsurance/");
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

                imagepath = path + "/MHCWInsurance_" + String.valueOf(System.currentTimeMillis())
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
*/

        } else if (requestCode == RESULT_SELECT_PHOTO2 && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmap2=selectedImage;
                imgBackCard.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == RESULT_CAMERA_IMAGE2 ) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
                imgBackCard.setImageBitmap(thumbnail);
                //  String imageurl = getRealPathFromURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /* Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            imgBackCard.setImageBitmap(imageBitmap);
            // imageLoader.displayImage(imageBitmap,profileImage,displayImageOptions);

            FileOutputStream outStream = null;
            File file = new File(Environment.getExternalStorageDirectory(),
                    "/MHCWInsurance/");
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

                imagepath = path + "/MHCWInsurance_" + String.valueOf(System.currentTimeMillis())
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

*/
        }

    }
}
