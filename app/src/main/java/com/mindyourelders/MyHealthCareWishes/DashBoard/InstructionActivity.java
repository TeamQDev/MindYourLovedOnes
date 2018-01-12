package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;

public class InstructionActivity extends AppCompatActivity {
   RelativeLayout header;
   ImageView imgBack,imgPicture;
   TextView txtHeader,txtMsg;
   String From;
    ImageView imgDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        initUI();
    }

    private void initUI() {
        header=findViewById(R.id.header);
        imgBack=findViewById(R.id.imgBack);
        imgPicture=findViewById(R.id.imgPicture);
        txtHeader=findViewById(R.id.txtHeader);
        txtMsg=findViewById(R.id.txtMsg);
        imgDot=findViewById(R.id.imgDot);
        Intent i=getIntent();
        if (i.getExtras()!=null) {
            From =i.getExtras().getString("From");
        }
        switch(From)
        {
            case "Dropbox":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.VISIBLE);
                String msg="This is a method for personal cloud storage. It is commonly referred to as a method to backup one's files online. In addition to store the files, one can also share them with colleagues and friends, all using Dropbox. To store, retrieve and manage your files, one can use a Dropbox application." +
                        "<br><br>" +
                        "You need to login with your dropbox account. If you don't  have dropbox account, You need to have one. " +
                        "<br><br>" +
                        "<b>Guidelines:</b>"+
                        "<br><br>" +
                        "<b>1) Login: </b>"+
                        "<br>" +
                        "Click on <b>\"Login With DropBox\" </b>button, It will redirect you to dropbox site. Now you can login with your personal dropbox account."+
                        "<br>" +
                        "After logging in, To allow the access to the files and folders from your dropbox, Click the <b>'Allow'</b>  Button.So now you can Access your files." +
                        "<br><br>"+
                        "<b>2) Backup: </b>"+
                        "<br>" +
                        "If you have logged in to dropbox from application, It will show you Backup Options."+
                        "<br>" +
                        "On clicking  <b>'BACKUP'</b> button, you need to Click on <b>'Backup your Data'</b> button to store your profile." +
                        "<br><br>"+
                        "<b>3) Restore: </b>"+
                        "<br>" +
                        "If you have logged in to dropbox from application, It will show you Restore Options. It will help to restore profiles."+
                        "<br>" +
                        "On clicking  <b>'RESTORE'</b>  button, you will get list of zip files for download. On clicking any of the zip file, It will be downloaded and you will get a popup message  to unzip and restore file."+
                        "<br><br>"+
                        "<b>4) Get Files: </b>"+
                        "<br>" +
                        "If you have logged in to dropbox from application, It will show you View Files Options. It will help you to get the documents in Advance Directive section  from dropbox storage."+
                          "<br>" +
                        "On clicking <b>'VIEW FILES'</b> button, you will get list of documents files for download. On clicking any of the document  file, It will be downloaded ."+
                        "<br>" +
                        "Now you can add this document  to your application by clicking on <b>'ADD FILES'</b> button.";

                        txtMsg.setText(Html.fromHtml(msg));
                        txtHeader.setText("Dropbox");
                break;

            case "Personal":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
                String msgs="To <b>add</b> information type responses.<br>" +
                        "To <b>save</b> information click the check mark" +
                        " on the <b>top right</b> side of the screen.<br><br>" +
                        "To <b>edit</b> or <b>delete</b> information simply work on the screen and then save your edits by clicking on the <b>check mark</b> on the <b>top right</b> side of the screen." +
                        "<br><br>" +
                        "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the <b>three dots</b> on the top right side of the screen.";

                txtMsg.setText(Html.fromHtml(msgs));
                txtHeader.setText("Personal Profile Instructions");
                break;

            case "Medical":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
                String msgd="To <b>add</b> information click the <b>plus box</b> for each section " +
                        "and then click the green bar." +
                        "<br><br>" +
                        "To <b>save</b> information click the <b>check mark</b>" +
                        " on the top right side of the screen." +
                        "<br><br>" +
                        "To <b>edit</b> information in a particular section click the picture of the <b>pencil</b>. To save your edits click the green bar at the lower half of the screen." +
                        "<br><br>" +
                        "To <b>delete</b> information in a particular section click the <b>garbage can</b>. For sections without the garbage can, simply delete the data." +
                        "<br><br>" +
                        "To <b>view a report</b> or to <b>email</b> or <b>fax</b> the data in each section click the three dots on the top right side of the screen.";

                txtMsg.setText(Html.fromHtml(msgd));
                txtHeader.setText("Medical Profile Instructions");
                break;

            case "Living":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
                String msgl="To <b>save</b> information click the check mark" +
                        " on the top right side of the screen." +
                        "<br><br>" +
                        "To <b>edit</b> information simply change the data and then save your edits by clicking on the check mark on the top right side of the screen." +
                        "<br><br>" +
                        "To <b>view</b>, <b>email</b>, or <b>fax</b> the data in each section click on the three dots on the top right side of the screen.";

                txtMsg.setText(Html.fromHtml(msgl));
                txtHeader.setText("Living Activity Instructions");
                break;

            case "Card":
                imgPicture.setVisibility(View.GONE);
                imgDot.setVisibility(View.GONE);
                imgPicture.setImageResource(R.drawable.v_user);
                String msgc="You can <b>add</b> the card from both the sides i.e <b>front and back</b>." +
                        "<br><br>" +
                        "For adding front and back card, You need to click on the <b>plus</b> icon. Now you can add picture of card either from <b>Camera or Gallery</b>." +
                        "<br><br>" +
                        "For adding card through <b>camera</b>, It is <b>recommended</b> that you hold your phone <b>horizontal</b> while taking a picture of a card."+
                        "<br><br>" +
                        "To <b>view</b> the card <b>click</b> on the <b>card</b>, So you can <b>view</b> card and see by <b>zooming</b> it. To <b>share card</b> to your loved ones click the <b>share</b> icon on the top right side of the screen.";

                txtMsg.setText(Html.fromHtml(msgc));
                txtHeader.setText("Insurance Card GuideLines");
                break;

        }
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert=new AlertDialog.Builder(InstructionActivity.this);
                alert.setTitle("Email ?");
                alert.setMessage("Do you want to email dropbox instructions ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg="This is a method for personal cloud storage. It is commonly referred to as a method to backup one's files online. In addition to store the files, one can also share them with colleagues and friends, all using Dropbox. To store, retrieve and manage your files, one can use a Dropbox application." +
                                "<br><br>" +
                                "You need to login with your dropbox account. If you don't  have dropbox account, You need to have one. " +
                                "<br><br>" +
                                "<b>Guidelines:</b>"+
                                "<br><br>" +
                                "<b>1) Login: </b>"+
                                "<br>" +
                                "Click on <b>\"Login With DropBox\" </b>button, It will redirect you to dropbox site. Now you can login with your personal dropbox account."+
                                "<br>" +
                                "After logging in, To allow the access to the files and folders from your dropbox, Click the <b>'Allow'</b>  Button.So now you can Access your files." +
                                "<br><br>"+
                                "<b>2) Backup: </b>"+
                                "<br>" +
                                "If you have logged in to dropbox from application, It will show you Backup Options."+
                                "<br>" +
                                "On clicking  <b>'BACKUP'</b> button, you need to Click on <b>'Backup your Data'</b> button to store your profile." +
                                "<br><br>"+
                                "<b>3) Restore: </b>"+
                                "<br>" +
                                "If you have logged in to dropbox from application, It will show you Restore Options. It will help to restore profiles."+
                                "<br>" +
                                "On clicking  <b>'RESTORE'</b>  button, you will get list of zip files for download. On clicking any of the zip file, It will be downloaded and you will get a popup message  to unzip and restore file."+
                                "<br><br>"+
                                "<b>4) Get Files: </b>"+
                                "<br>" +
                                "If you have logged in to dropbox from application, It will show you View Files Options. It will help you to get the documents in Advance Directive section  from dropbox storage."+
                                "<br>" +
                                "On clicking <b>'VIEW FILES'</b> button, you will get list of documents files for download. On clicking any of the document  file, It will be downloaded ."+
                                "<br>" +
                                "Now you can add this document  to your application by clicking on <b>'ADD FILES'</b> button.";


                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                                new String[] { "" });
                        // String name= getString(PrefConstants.CONNECTED_NAME);
                        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                                "DropBox Instructions"); // subject
                        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(msg)); // Body
                        emailIntent.setType("application/email");
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });
    }
}
