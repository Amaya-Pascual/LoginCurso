package com.example.logincurso;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MailActivity extends Activity {


    private static final int CAMERA_PIC_REQUEST = 1337;
    private static final int REQUEST_TAKE_PHOTO = 1;
    Button send, salir, camera;
    Bitmap thumbnail;
    File pic;
    EditText address, subject, emailtext;
    String mail, asunto, mensaje;
    String currentPhotoPath; //donde se ha guardado la foto

    ImageButton btnInicio, btnPerfil;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mail);
        send = findViewById(R.id.emailsendbutton);
        address = findViewById(R.id.emailaddress);
        subject = findViewById(R.id.emailsubject);
        emailtext = findViewById(R.id.emailtext);
        salir=  findViewById((R.id.btnsalir));

        btnInicio=findViewById(R.id.btnInicio);
        btnPerfil=findViewById(R.id.btnPerfil);

        camera = findViewById(R.id.btncamara);

        if(validaPermisos()){
            camera.setEnabled(true);
        }else{
            camera.setEnabled(false);
        }

        btnInicio.setOnClickListener(v -> {
            Intent i = new Intent(MailActivity.this, MainActivity.class);
            startActivity(i);
        });
        btnPerfil.setOnClickListener(v -> {
            Intent i = new Intent(MailActivity.this,EditarPerfil.class);
            startActivity(i);
        });

        camera.setOnClickListener(arg0 -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(getPackageManager()) != null){
                // Crea el fichero donde va la foto
                File photoFile ;
                photoFile = createImageFile();
                // Solo continua si el fichero se ha creado bien
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(MailActivity.this,
                            "com.example.android.fileprovider", photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
                }
            }
            //si no vuelve a pedir foto
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        });

        salir.setOnClickListener(v -> {
            //si esta logueado vuelve a la loggedActivity
            SharedPreferences preferences = getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
            boolean sesion = preferences.getBoolean("sesion", false);
            if (sesion){ Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                startActivity(intent);
                finish();
            }else{
                //si no, vuelve a la pantalla de registro y login
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

        send.setOnClickListener(arg0 -> {
            try {
                // si no se ha sacado foto
                if (!(pic == null)){
              // mail = address.getText().toString();
                    mail ="numislav@gmail.com";
                asunto = subject.getText().toString();
                mensaje = emailtext.getText().toString();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});
                i.putExtra(Intent.EXTRA_SUBJECT, asunto);
                i.putExtra(Intent.EXTRA_TEXT, mensaje);
                Uri imageUri = FileProvider.getUriForFile(
                        MailActivity.this,
                        "com.example.logincurso.provider", //(use your app signature + ".provider" )
                        pic);
                i.putExtra(Intent.EXTRA_STREAM, imageUri);
                i.setType("image/png");
                startActivity(Intent.createChooser(i, "Compartir"));
               }else{//envia un mail
                String uriText =
                        "mailto:lavin@numismaticalavin.com" +
                                "?subject=" + Uri.encode("Asunto...") +
                                "&body=" + Uri.encode("Cuerpo del mensaje...");

                Uri uri = Uri.parse(uriText);
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                    try {
                        startActivity(Intent.createChooser(sendIntent, getString(R.string.enviarMail)));
                    } catch (ActivityNotFoundException e)
                    {
                        Toast.makeText(MailActivity.this, R.string.errorMail, Toast.LENGTH_LONG).show();
                    }}


            }catch (Throwable t){
                Toast.makeText(getApplicationContext(), t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED
                    && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                camera.setEnabled(true);
            }else{
                solicitarPermisosManual();
            }
        }

    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones={"si","no"};
        final AlertDialog.Builder alertOpciones=new AlertDialog.Builder(MailActivity.this);
        alertOpciones.setTitle("¿Está seguro de aceptar los permisos?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("si")){
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri=Uri.fromParts("package",getPackageName(),null);
                    intent.setData(uri);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Los permisos fueron aceptados",Toast.LENGTH_SHORT).show();
                    camera.setEnabled(true);
                    dialogInterface.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_SHORT).show();
                    cargarDialogoRecomendacion();
                    dialogInterface.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    private boolean validaPermisos() {
   /*    if(Build.VERSION.SDK_INT>23){
            return true;
        }*/
        int permisoCamara =ContextCompat.checkSelfPermission(MailActivity.this, CAMERA);
        int permisoEscritura = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permisolectura = ContextCompat.checkSelfPermission(MailActivity.this, READ_EXTERNAL_STORAGE);

        if((checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }

        if((ActivityCompat.shouldShowRequestPermissionRationale(MailActivity.this,CAMERA)) ||
                (ActivityCompat.shouldShowRequestPermissionRationale(MailActivity.this,WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }

        return false;

    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo=new AlertDialog.Builder(MailActivity.this);
        dialogo.setTitle("Permisos Desactivados");
        dialogo.setMessage("Debe aceptar los permisos para el correcto funcionamiento de la App");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
            }
        });
        dialogo.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();            
            thumbnail = (Bitmap) extras.get("data");
            ImageView image = findViewById(R.id.imageView1);
            image.setImageBitmap(thumbnail);
           /* Toast.makeText(getApplicationContext(), "FOTO",
                    Toast.LENGTH_LONG).show();*/
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
                /*    Toast.makeText(getApplicationContext(), "FOTO2",
                            Toast.LENGTH_LONG).show();*/
                }
            } catch (IOException e) {
                Log.e("BROKEN", "Could not write file " + e.getMessage());
                Toast.makeText(getApplicationContext(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }

        }
    }
    public File createImageFile(){
        //
       return null;
    }
}