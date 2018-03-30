package com.mindyourlovedones.healthcare.webservice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;

/**
 * Created by welcome on 11/17/2017.
 */

public class CustomDialog {

    public static Dialog createCustomDialog(Context context, String title,
                                            String message) {
        final Dialog custDialog = new Dialog(context);

        custDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        custDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        custDialog.setContentView(R.layout.custom_dialog);

        if (message.equalsIgnoreCase("Information Updated")) {
            message = "Information updated successfully";
        }
        TextView titleTv = (TextView) custDialog
                .findViewById(R.id.text_Title_dialog);

        titleTv.setText(title);

        TextView messageTv = (TextView) custDialog
                .findViewById(R.id.text_meesage_dialog);

        messageTv.setText(message);

        Button okButton = (Button) custDialog.findViewById(R.id.dialogButtonOK);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                custDialog.dismiss();
            }
        });

        return custDialog;
    }

    public static Dialog createCustomDialogFinishActivity(
            final Context context, String title, String message) {
        final Dialog custDialog = new Dialog(context);

        custDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        custDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));

        custDialog.setContentView(R.layout.custom_dialog);

        if (message.equalsIgnoreCase("Information Updated")) {
            message = "Information updated successfully";
        }
        TextView titleTv = (TextView) custDialog
                .findViewById(R.id.text_Title_dialog);

        titleTv.setText(title);

        TextView messageTv = (TextView) custDialog
                .findViewById(R.id.text_meesage_dialog);

        messageTv.setText(message);

        Button okButton = (Button) custDialog.findViewById(R.id.dialogButtonOK);
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // custDialog.dismiss();
                ((Activity) context).finish();
            }
        });

        return custDialog;
    }
}
