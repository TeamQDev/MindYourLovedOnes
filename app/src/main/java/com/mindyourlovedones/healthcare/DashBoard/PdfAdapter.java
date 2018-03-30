package com.mindyourlovedones.healthcare.DashBoard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mindyourlovedones.healthcare.HomeActivity.R;

import java.io.File;
import java.util.List;

/**
 * Created by welcome on 10/17/2017.
 */

class PdfAdapter extends BaseAdapter {
    Context context;
    String[] pdfList;
    LayoutInflater lf;
    ViewHolder holder;
    File[] imagelist;

    public PdfAdapter(Context context, String[] pdfList, File[] imagelist) {
        this.context=context;
        this.pdfList=pdfList;
        this.imagelist=imagelist;
        lf= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pdfList.length;
    }

    @Override
    public Object getItem(int position) {
        return pdfList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView==null)
        {
            convertView=lf.inflate(R.layout.row_pdf,parent,false);
            holder=new ViewHolder();
            holder.txtName= (TextView) convertView.findViewById(R.id.txtName);
         /*   holder.txtTime= (TextView) convertView.findViewById(R.id.txtTime);
            holder.txtMessage= (TextView) convertView.findViewById(R.id.txtMessage);
            holder.imgProfile= (ImageView) convertView.findViewById(R.id.imgProfile);
            holder.imgForword= (ImageView) convertView.findViewById(R.id.imgForword);*/
            convertView.setTag(holder);
        }
        else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txtName.setText(pdfList[position]);
      /*  holder.txtMessage.setText(pdfList.get(position).getMessage());
        holder.txtTime.setText(pdfList.get(position).getTime());
        holder.imgProfile.setImageResource(pdfList.get(position).getImage());
        if (position==0)
        {
            holder.txtMessage.setTextColor(context.getResources().getColor(R.color.colorMaroon));
        }
*/
      convertView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              final Dialog dialog = new Dialog(context);
              dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
              dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
              LayoutInflater lf = (LayoutInflater) context
                      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              View dialogview = lf.inflate(R.layout.dialog_gender, null);
              final TextView textOption1 = (TextView) dialogview.findViewById(R.id.txtOption1);
              final TextView textOption2 = (TextView) dialogview.findViewById(R.id.txtOption2);
              TextView textCancel = (TextView) dialogview.findViewById(R.id.txtCancel);
              textOption1.setText("Add");
              textOption2.setText("View");
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
                      PackageManager packageManager = context.getPackageManager();
                      Intent testIntent = new Intent(Intent.ACTION_VIEW);
                      testIntent.setType("application/pdf");
                      List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
                      if (list.size() > 0 && imagelist[(int) position].isFile()) {
                          ((DocumentSdCardList)context).getData(pdfList[position],String.valueOf(imagelist[(int) position].getPath()));
                         /* File targetFile = new File(String.valueOf(imagelist[(int) position].getPath()));
                          Uri uri=null;
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                             // intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                              uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
                          } else {
                              uri = Uri.fromFile(targetFile);
                          }
                          ((DocumentSdCardList)context).getData(pdfList[position], String.valueOf(uri));*/
                          dialog.dismiss();

                      }
                    /*  File targetFile = new File(String.valueOf(imagelist[(int) position].getAbsoluteFile()));
                      Uri uri=null;
                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        //  intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                          uri = FileProvider.getUriForFile(context, "com.mindyourelders.healthcare.HomeActivity.fileProvider", targetFile);
                      } else {
                          uri = Uri.fromFile(targetFile);
                      }*/
                     //uri=Uri.fromFile(imagelist[(int) position].getAbsoluteFile()).toString();
                      ((DocumentSdCardList) context).finish();
                  }
              });
              textOption2.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      PackageManager packageManager = context.getPackageManager();
                      Intent testIntent = new Intent(Intent.ACTION_VIEW);
                      testIntent.setType("application/pdf");
                      List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
                      if (list.size() > 0 && imagelist[(int) position].isFile()) {
                          Intent intent = new Intent();
                          intent.setAction(Intent.ACTION_VIEW);
                         // Uri uri = Uri.fromFile(imagelist[(int) position].getAbsoluteFile());
                          File targetFile = new File(String.valueOf(imagelist[(int) position].getPath()));
                          Uri uri=null;
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                               intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                              uri = FileProvider.getUriForFile(context, "com.mindyourlovedones.healthcare.HomeActivity.fileProvider", targetFile);
                          } else {
                              uri = Uri.fromFile(targetFile);
                          }
                          intent.setDataAndType(uri, "application/pdf");
                          context.startActivity(intent);
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


              /**/
          }
      });
        return convertView;
    }

    public class ViewHolder
    {
        TextView txtName, txtTime,txtMessage;
       // ImageView imgProfile,imgForword;
    }
}
