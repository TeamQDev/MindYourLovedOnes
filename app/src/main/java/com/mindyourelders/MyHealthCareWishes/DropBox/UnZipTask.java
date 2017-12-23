package com.mindyourelders.MyHealthCareWishes.DropBox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mindyourelders.MyHealthCareWishes.DashBoard.DropboxLoginActivity;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by welcome on 12/14/2017.
 */

public class UnZipTask extends AsyncTask<String,Void,String> {
Context con;
    String outZipPath;
    String inputFolderPath;
    ZipListner context;
   ProgressDialog dialog;
    public UnZipTask(DropboxLoginActivity filesActivity, String absolutePath, String path) {
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
        dialog.setMessage("Unzipping");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        InputStream is;
        ZipInputStream zis;
        String file="";
        try
        {
            is = new FileInputStream(inputFolderPath);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;

            while((ze = zis.getNextEntry()) != null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int count;

                String filename = ze.getName();
                FileOutputStream fout = new FileOutputStream(outZipPath);

                // reading and writing
                while((count = zis.read(buffer)) != -1)
                {
                    baos.write(buffer, 0, count);
                    byte[] bytes = baos.toByteArray();
                    fout.write(bytes);
                    baos.reset();
                }
                file=fout.toString();
                fout.close();
                zis.closeEntry();
            }

            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return file;
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
