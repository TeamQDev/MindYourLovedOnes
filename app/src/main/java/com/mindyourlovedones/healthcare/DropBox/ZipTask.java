package com.mindyourlovedones.healthcare.DropBox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by welcome on 12/14/2017.
 */

public class ZipTask extends AsyncTask<String,Void,String> {
Context con;
    String outZipPath;
    String inputFolderPath;
    ZipListner context;
   ProgressDialog dialog;
    public ZipTask(FilesActivity filesActivity, String absolutePath, String path) {
        inputFolderPath=absolutePath;
        outZipPath=path;
        context=filesActivity;
        con=filesActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(con);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setMessage("Zipping");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            Log.d("", "Zip directory: " + srcFile.getName());
            for (int i = 0; i < files.length; i++) {
                Log.d("", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }

            zos.close();
            return outZipPath;
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (dialog.isShowing())
        {
            dialog.dismiss();
        }
       context.getFile(s);
    }
}
