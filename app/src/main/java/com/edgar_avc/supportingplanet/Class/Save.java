package com.edgar_avc.supportingplanet.Class;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Save {

    private Context TheThis;
    private String NameOfFolder = "/CodigosQR";
    private String NameOfFile = "imagen";

    StorageReference storageRef;
    private ProgressDialog progressDialog;

    public void SaveImage(Context context, Bitmap ImageToSave, String nombre, Activity activity) {

        NameOfFile=nombre;
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        Log.d("SALIR",file_path);
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, NameOfFile  + ".jpg");

        storageRef = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(activity);

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();

            //PROCESO PARA SUBIR IMAGEN A LA BD

            progressDialog.setMessage("Guardando información...");
            progressDialog.show();
            Log.d("SALIDA", "ENTRO");
            Uri jas;
            jas = Uri.fromFile(new File(file_path+"/"+NameOfFile+".jpg"));
            StorageReference filep = storageRef.child("CodigosQR").child(jas.getLastPathSegment());
            filep.putFile(jas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(TheThis, "TAXISTA Y CODIGO QR GUARDADOS", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TheThis, "NO SE SUBIO", Toast.LENGTH_LONG).show();
                }
            });
            //TERMINA PROCESO

        }

        catch(FileNotFoundException e) {
            UnableToSave();
            Log.d("SALIDA","1: "+e);
        }
        catch(IOException e) {
            UnableToSave();
            Log.d("SALIDA","2: "+e);
        }

    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
                new String[] { file.toString() } , null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        Toast.makeText(TheThis, "OCURRIO UN ERROR CON EL CODIGO QR", Toast.LENGTH_LONG).show();
    }

    private void AbleToSave() {
        //Toast.makeText(TheThis, "TAXISTA Y CODIGO QR GUARDADOS", Toast.LENGTH_LONG).show();
    }
    //{TE AMO MUCHO JASMIN}
}
