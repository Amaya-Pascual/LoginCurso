package com.example.logincurso;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class MailActivity extends Activity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private static final int REQUEST_TAKE_PHOTO = 1;
    Button send;
    Bitmap thumbnail;
    File pic;
    EditText address, subject, emailtext;
    String mail, asunto, mensaje;
    String currentPhotoPath; //donde se ha guardado la foto

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        send = (Button) findViewById(R.id.emailsendbutton);
        address = (EditText) findViewById(R.id.emailaddress);
        subject = (EditText) findViewById(R.id.emailsubject);
        emailtext = (EditText) findViewById(R.id.emailtext);

        Button camera = (Button) findViewById(R.id.btncamara);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null){
                    // Crea el fichero donde va la foto
                    File photoFile = null;
                    photoFile = createImageFile();
                    // Solo continua si el fichero se ha creado bien
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MailActivity.this,
                                "com.example.android.fileprovider", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
                    }
                }
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }
        });

        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    mail = address.getText().toString();
                    asunto = subject.getText().toString();
                    mensaje = emailtext.getText().toString();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
                    i.putExtra(Intent.EXTRA_SUBJECT, asunto);
                    i.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
                    //Log.d("URI@!@#!#!@##!", "AMAYA"+Uri.fromFile(pic).toString() + " " + pic.exists());
                    Uri imageUri = FileProvider.getUriForFile(
                            MailActivity.this,
                            "com.example.logincurso.provider", //(use your app signature + ".provider" )
                            pic);
                    i.putExtra(Intent.EXTRA_STREAM, imageUri);
                    i.setType("image/png");
                    startActivity(Intent.createChooser(i, "Compartir"));
                }catch (Throwable t){
                    Toast.makeText(getApplicationContext(), t.toString(),
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();            
            thumbnail = (Bitmap) extras.get("data");
            ImageView image = (ImageView) findViewById(R.id.imageView1);
            image.setImageBitmap(thumbnail);

            Toast.makeText(getApplicationContext(), "FOTO",
                    Toast.LENGTH_LONG).show();
            try {
                //File root = Environment.getExternalStorageDirectory();
                File root = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File imagen = File.createTempFile(
                        "pic",  /* prefix */
                        ".png",         /* suffix */
                        root      /* directory */
                );
                // Save a file: path for use with ACTION_VIEW intents
                currentPhotoPath = imagen.getAbsolutePath();

                if (root.canWrite()) {
                    pic = new File(root, "pic.png");
                    FileOutputStream out = new FileOutputStream(pic);
                    thumbnail.compress(CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    Toast.makeText(getApplicationContext(), "FOTO2",
                            Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                Log.e("BROKEN", "Could not write file " + e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        }
    }
    public File createImageFile(){
        //pendiente de hacer
       return null;
    }
}