package com.mindyourlovedones.healthcare.DropBox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.mindyourlovedones.healthcare.DashBoard.DropboxLoginActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by welcome on 12/14/2017.
 */

public class UnZipDirectoryTask extends AsyncTask<String,Void,String> {
Context con;
    String outZipPath;
    String inputFolderPath;
    ZipListner context;
   ProgressDialog dialog;
    public UnZipDirectoryTask(DropboxLoginActivity filesActivity, String absolutePath, String path) {
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
        FileInputStream fis= null;
        ZipInputStream zis=null;
        try {
            fis = new FileInputStream(inputFolderPath);
            zis = new ZipInputStream(
                new BufferedInputStream(fis));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "No";
        } catch (IOException e) {
            e.printStackTrace();
            return "No";
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "No";
        }
        finally {
            try {
                zis.close();
            } catch (IOException e) {
                e.printStackTrace();
                return "No";
            }
        }
        return "Yes";
        /*InputStream is;
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
                FileOutputStream fout = new FileOutputStream(filename);

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

        return file;*/
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
