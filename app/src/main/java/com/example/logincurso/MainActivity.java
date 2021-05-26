package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText password, user;
    private Button btnlogin;
    private Button btnRegistro;
    private ImageView imageView;
    String usuario, passw;

    //Notificaciones
    //cuando te logueas te notifica que estas logueado y te lleva a la pantalla loggedActivity
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    private void setPendingIntent(){
        Intent intent = new Intent(this, LoggedActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT);
    }
    private void createNotificacionChanel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_perfilb);
        builder.setContentTitle("Bienvenido a Numismática Lavín");
        builder.setContentText("Estas logueado. Puedes navegar al catalogo");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password=findViewById(R.id.password);
        user=findViewById(R.id.user);
        btnlogin=findViewById(R.id.buttonLogin);
        btnRegistro=findViewById(R.id.buttonRegistrar);
        //imagen en la primera pantalla
        imageView= findViewById(R.id.imageView);
        recuperarPreferencias();

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(MainActivity.this, getString(R.string.map_donde_estamos), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UbicacionTiendaMap.class);
                startActivity(intent);
                return false;
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //no permite campos vacíos
                usuario = user.getText().toString();
                passw = password.getText().toString();
                //errores
                boolean hasErrors = false;
                String error="";
                if (usuario.isEmpty() && passw.isEmpty()){
                    hasErrors = true;
                    error+="\nNo se permiten campos vacíos";
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(user.getText()).matches()){
                    hasErrors = true;
                    error+="\nMail con @.";
                }
               if (password.length()<2)
                 {
                    hasErrors = true;
                    error+="\nLa contraseña debe tener al menos 2 caracteres";
                 }
                if (!hasErrors){
                    validarUsuario("http://194.30.35.183/subasta/validarUsuario.php");
                }
                  else{
                    Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
             }
            }
        });
        //boton para llevarnos a la pantalla de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validarUsuario (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    guardarPreferencias();

                    //notificacion
                    setPendingIntent();
                    createNotificacionChanel();
                    createNotification();
                    //envio a la loggedActivity
                    Intent i = new Intent(getApplicationContext(), LoggedActivity.class);
                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(MainActivity.this, getString(R.string.error_contras_usu), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: "+ error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("mail", usuario);
                parametros.put("contrasena", passw);
                return parametros;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //preferencias, guarda el mail y la contraseña para no tener que loguearse en el mismo dispositivo
    private void guardarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mail",usuario);
        editor.putString("contrasena", passw);
        editor.putBoolean("sesion", true);
        editor.commit();
    }

    //recuperar preferencias
    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
        user.setText(preferences.getString("mail", "correo@dom.es"));
        password.setText(preferences.getString("contrasena", "1234"));
    }

}