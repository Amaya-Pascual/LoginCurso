package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.Hashtable;
import java.util.Map;

public class Registro extends AppCompatActivity {

    String URL_SERVIDOR = "http://194.30.35.183/subasta/registrarUsuario.php";

    EditText etUsuario, etContrasena, etNombre, etApellido;
    Button btnRegistrar, btnVolverLogin;

    //Notificaciones
    //cuando te registras te notifica que estas registrado y te lleva la pantalla de login
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

    private void setPendingIntent(){
        Intent intent = new Intent(this, MainActivity.class);
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
        builder.setContentText("Estas Registrado. Ahora tienes que loguearte");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);

        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }

    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sin_registrar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inicio:
                Toast.makeText(this, getString(R.string.menu1inicio), Toast.LENGTH_LONG ).show();
                Intent i  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                return true;
            case R.id.registro:
                Toast.makeText(this, getString(R.string.menu1catalogo), Toast.LENGTH_LONG ).show();
                i = new Intent(getApplicationContext(),Registro.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasena = findViewById(R.id.etContrasena);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
                btnRegistrar.setVisibility(View.INVISIBLE);
             /*   Intent intent = new Intent(getApplicationContext(), Registro.class);
                startActivity(intent);
                finish();*/
            }
        });
        btnVolverLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void registrar() {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //mensajes en el php
                        if(response.equals("ERROR 1")) {
                            Toast.makeText(Registro.this, getString(R.string.error_campos_vacios), Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            //por ejemplo, si hay una restriccion unique en la bd
                            Toast.makeText(Registro.this, getString(R.string.error_registro), Toast.LENGTH_SHORT).show();
                        } else if(response.equals("MENSAJE")) {
                            Toast.makeText(Registro.this, getString(R.string.registro_correcto), Toast.LENGTH_LONG).show();
                            //notificacion
                            setPendingIntent();
                            createNotificacionChanel();
                            createNotification();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(Registro.this, getString(R.string.error_conexion), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("mail", etUsuario.getText().toString().trim());
                parametros.put("contrasena", etContrasena.getText().toString().trim());
                parametros.put("nomCliente", etNombre.getText().toString().trim());
                parametros.put("ape1Cliente", etApellido.getText().toString().trim());
                return parametros;            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Registro.this);
        requestQueue.add(stringRequest);
    }

}