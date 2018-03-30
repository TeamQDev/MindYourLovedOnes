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

public class ZipDirectoryTask extends AsyncTask<String,Void,String> {
Context con;
    String outZipPath;
    String inputFolderPath;
    ZipListner context;
   ProgressDialog dialog;
    File zipDir=null;
    public ZipDirectoryTask(FilesActivity filesActivity, String absolutePath, String path) {
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
            ZipOutputStream zos = new  ZipOutputStream(new FileOutputStream(outZipPath));
            //zos=zipDir(inputFolderPath, zos);
            //close the stream
                zipDir = new File(inputFolderPath);
                //get a listing of the directory content
                 File[] dirList = zipDir.listFiles();
              /*  byte[] readBuffer = new byte[2156];
                int bytesIn = 0;*/
                //loop through dirList, and zip the files
                for(int i=0; i<dirList.length; i++)
                {
                    byte[] readBuffer = new byte[1024];
                 //   File f = new File(dirList[i].getName());
                    if(dirList[i].isDirectory()) {
                        File[] subdirList = dirList[i].listFiles();
                        for (int j = 0; j < subdirList.length; j++) {
                           File fi = new File(subdirList[j].getName());
                            if(subdirList[j].getName().endsWith(".zip"))
                            {
                            }else{
                                FileInputStream fis = new FileInputStream(subdirList[j]);
                                ZipEntry anEntry = new ZipEntry(subdirList[j].getPath());
                                //place the zip entry in the ZipOutputStream object
                                zos.putNextEntry(anEntry);
                                //now write the content of the file to the ZipOutputStream
                                int bytesIn;
                                while((bytesIn = fis.read(readBuffer)) != -1)
                                {
                                    zos.write(readBuffer, 0, bytesIn);
                                }
                                //close the Stream
                                fis.close();
                            }

                        }
                    }

                }
            zos.close();
            return outZipPath;
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
        return null;
    }

    private ZipOutputStream zipDir(String dir2zip, ZipOutputStream zos) {
        try
        {
            zipDir = new File(dir2zip);
            //get a listing of the directory content
            String[] dirList = zipDir.list();
            byte[] readBuffer = new byte[2156];
            int bytesIn = 0;
            //loop through dirList, and zip the files
            for(int i=0; i<dirList.length; i++)
            {
                File f = new File(zipDir,dirList[i]);
                if(f.isDirectory()) {
                    String[] subdirList = f.list();

                    for (int j = 0; j < subdirList.length; j++) {
                        File fi = new File(f, subdirList[j]);
                        if(fi.getName().endsWith(".zip"))
                        {

                        }else{
                            FileInputStream fis = new FileInputStream(fi);
                            ZipEntry anEntry = new ZipEntry(fi.getPath());
                            //place the zip entry in the ZipOutputStream object
                            zos.putNextEntry(anEntry);
                            //now write the content of the file to the ZipOutputStream
                            while((bytesIn = fis.read(readBuffer)) != -1)
                            {
                                zos.write(readBuffer, 0, bytesIn);
                            }
                            //close the Stream
                            fis.close();
                        }

                    }
                }

            }
        }
        catch(Exception e)
        {
            //handle exception
        }
        return zos;
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
