package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
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

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CARD =50 ;
    ContentValues values;
    Uri imageUriFront,imageUriBack;
    Context context = this;
    TextView txtName, txttype,txtTitle;
    TextInputLayout tilTitle;
    Bitmap bitmap1,bitmap2;
    String imagePathFront="",imagePathBack="";
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
    ImageLoader imageLoader;
    DisplayImageOptions displayImageOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
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
                .showImageOnLoading(R.drawable.ins_card)
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
        preferences = new Preferences(context);
        dbHelper = new DBHelper(context);
        CardQuery c = new CardQuery(context, dbHelper);
    }

    private void initListener() {
        imgDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        imgEdit1.setOnClickListener(this);
        imgEdit2.setOnClickListener(this);
        imgfrontCard.setOnClickListener(this);
        imgBackCard.setOnClickListener(this);
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
            String photo=card.getImgFront();
            imagePathFront=photo;
           /* Bitmap bmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
           imgfrontCard.setImageBitmap(bmp);*/
            File imgFile = new File(photo);
            if (imgFile.exists()) {
               /* BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),bmOptions);
                Matrix matrix = new Matrix();
                if (bitmap.getHeight()>bitmap.getWidth()) {
                    matrix.postRotate(90);
                }
                else
                {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
                imgfrontCard.setImageBitmap(rotatedBitmap);*/
                //bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth(),bitmap.getHeight(),true);
               imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile)), imgfrontCard, displayImageOptions);
            }
            String photo1=card.getImgBack();
            imagePathBack=photo1;
            File imgFile1 = new File(photo1);
            if (imgFile1.exists()) {
//                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                Bitmap bitmap = BitmapFactory.decodeFile(imgFile1.getAbsolutePath(),bmOptions);
//                Matrix matrix = new Matrix();
//                if (bitmap.getHeight()>bitmap.getWidth()) {
//                    matrix.postRotate(90);
//                }
//                else
//                {
//                    matrix.postRotate(0);
//                }
//                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
//                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap .getWidth(), bitmap .getHeight(), matrix, true);
//                imgBackCard.setImageBitmap(rotatedBitmap);
                imageLoader.displayImage(String.valueOf(Uri.fromFile(imgFile1)), imgBackCard, displayImageOptions);
            }
           /* Bitmap bmp1 = BitmapFactory.decodeByteArray(photo1, 0, photo1.length);
            imgBackCard.setImageBitmap(bmp1);*/
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgFrontCard:
                Intent i=new Intent(context,AddFormActivity.class);
                i.putExtra("Image",imagePathFront);
                startActivityForResult(i,REQUEST_CARD);
                break;

            case R.id.imgBackCard:
                Intent j=new Intent(context,AddFormActivity.class);
                j.putExtra("Image",imagePathBack);
                startActivityForResult(j,REQUEST_CARD);
                break;
            case R.id.imgDone:
                name = txtName.getText().toString();
                type = txttype.getText().toString();
//                Bitmap bitmap1 = ((BitmapDrawable) imgfrontCard.getDrawable()).getBitmap();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap1.compress(Bitmap.CompressFormat.JPEG, 10, baos);
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
               /* byte[] photo1 = baos.toByteArray();
               Bitmap bitmap2 = ((BitmapDrawable) imgBackCard.getDrawable()).getBitmap();
                ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 10, baos2);*/
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
             //   byte[] photo2 = baos2.toByteArray();
if (isEdit==false) {
    boolean flag = CardQuery.insertInsuranceCardData(preferences.getInt(PrefConstants.CONNECTED_USERID), name, type, imagePathFront, imagePathBack);
    if (flag) {
        Toast.makeText(context, "You have added insurance information successfully", Toast.LENGTH_SHORT).show();
        finish();
    } else {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
    }
    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
}else if (isEdit==true)
{
    boolean flag = CardQuery.updateInsuranceCardData(id, name, type, imagePathFront, imagePathBack);
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
                showDialogs(RESULT_CAMERA_IMAGE1, RESULT_SELECT_PHOTO1,"Front");

                break;
            case R.id.imgEdit2:
                showDialogs(RESULT_CAMERA_IMAGE2, RESULT_SELECT_PHOTO2,"Back");
                break;


        }
    }

    private void showDialogs(final int resultCameraImage, final int resultSelectPhoto, final String from) {
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
                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(from.equals("Front")) {
                    imageUriFront = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFront);
                }else if(from.equals("Back"))
                {
                    imageUriBack = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriBack);
                }
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
        textOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from.equals("Front")) {
                    imgfrontCard.setImageResource(R.drawable.ins_card);
                    imagePathFront="";
                }else if (from.equals("Back"))
                {
                    imagePathBack="";
                    imgBackCard.setImageResource(R.drawable.ins_card);
                }
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
                final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //  profileImage.setImageBitmap(selectedImage);

               // imageLoader.displayImage(String.valueOf(imageUri),imgfrontCard,displayImageOptions);
                Matrix matrix = new Matrix();
                if (selectedImage.getHeight()>selectedImage.getWidth()) {
                    matrix.postRotate(90);
                }
                else
                {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(selectedImage , 0, 0, selectedImage .getWidth(), selectedImage .getHeight(), matrix, true);
                imgfrontCard.setImageBitmap(rotatedBitmap);
                storeImage(rotatedBitmap,"Front");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
          /*  try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                bitmap1=selectedImage;
                imgfrontCard.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        } else if (requestCode == RESULT_CAMERA_IMAGE1) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriFront);
               // String imageurl = getRealPathFromURI(imageUriFront);
               // Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
           //     imageLoader.displayImage(String.valueOf(imageUriFront),imgfrontCard,displayImageOptions);
                Matrix matrix = new Matrix();
                if (thumbnail.getHeight()>thumbnail.getWidth()) {
                    matrix.postRotate(90);
                }
                else
                {
                    matrix.postRotate(0);
                }
              //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail , 0, 0, thumbnail .getWidth(), thumbnail .getHeight(), matrix, true);
                imgfrontCard.setImageBitmap(rotatedBitmap);
                storeImage(rotatedBitmap,"Front");
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
                final InputStream imageStream =getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                //  profileImage.setImageBitmap(selectedImage);
              //  imageLoader.displayImage(String.valueOf(imageUri),imgBackCard,displayImageOptions);
                Matrix matrix = new Matrix();
                if (selectedImage.getHeight()>selectedImage.getWidth()) {
                    matrix.postRotate(90);
                }
                else
                {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(selectedImage , 0, 0, selectedImage .getWidth(), selectedImage .getHeight(), matrix, true);
                imgBackCard.setImageBitmap(rotatedBitmap);
                storeImage(rotatedBitmap,"Back");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == RESULT_CAMERA_IMAGE2 ) {
            try {
                Bitmap thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUriBack);
               // String imageurl = getRealPathFromURI(imageUriBack);
               // Bitmap bitmap = imageOreintationValidator(thumbnail, imageurl);
               // imageLoader.displayImage(String.valueOf(imageUriBack),imgBackCard,displayImageOptions);
                Matrix matrix = new Matrix();
                if (thumbnail.getHeight()>thumbnail.getWidth()) {
                    matrix.postRotate(90);
                }
                else
                {
                    matrix.postRotate(0);
                }
                //  Bitmap scaledBitmap = Bitmap.createScaledBitmap(thumbnail,thumbnail.getWidth(),thumbnail.getHeight(),true);
                Bitmap rotatedBitmap = Bitmap.createBitmap(thumbnail , 0, 0, thumbnail .getWidth(), thumbnail .getHeight(), matrix, true);
                imgBackCard.setImageBitmap(rotatedBitmap);
                // profileImage.setImageBitmap(bitmap);
                storeImage(rotatedBitmap,"Back");
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

    private void storeImage(Bitmap selectedImage, String profile) {
        FileOutputStream outStream = null;
        File file = new File(Environment.getExternalStorageDirectory(),
                "/MYLO/");
        String path = file.getAbsolutePath();
        if (!file.exists()) {
            file.mkdirs();
        }

        try {

            if (profile.equals("Front")) {
                imagePathFront = path + "/MYLO_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagePathFront);
            }
            else if (profile.equals("Back")){
                imagePathBack = path + "/MYLO_" + String.valueOf(System.currentTimeMillis())
                        + ".jpg";
                // Write to SD Card
                outStream = new FileOutputStream(imagePathBack);
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

    private String getRealPathFromURI(Uri imageUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor =getContentResolver().query(imageUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
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

    private Bitmap rotateImage(Bitmap source, int angle) {

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
}
