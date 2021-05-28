package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

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

    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sin_registrar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inicio:
                Toast.makeText(this, getString(R.string.menu1inicio), Toast.LENGTH_LONG ).show();
                //Intent i  = new Intent(getApplicationContext(),MainActivity.class);
                //startActivity(i);
                return true;
            case R.id.registro:
                Toast.makeText(this, getString(R.string.menu1catalogo), Toast.LENGTH_LONG ).show();
                Intent i = new Intent(getApplicationContext(),Registro.class);
                startActivity(i);
                return true;
            case R.id.tumoneda:
                //para que el snackbar salga arriba FrameLayout.LayoutParams
                String enviaFoto=getString(R.string.instruccionesMailFoto);
                //creo el snack, con el texto deseado
                Snackbar snack = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), enviaFoto, Snackbar.LENGTH_INDEFINITE);
                //obtengo el textView del snack
                TextView snckBarTxt = snack.getView().findViewById(com.google.android.material.R.id.snackbar_text); //si cambia la version gradle, hay que revisar esto
                //para que ocupe el espacio que necesite
                snckBarTxt.setSingleLine(false);
                //Obtengo la vista
                View view = snack.getView();
                //parametros de la vista del snack
                FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
                //arriba
                params.gravity = Gravity.TOP;
                //params.height=340; //altura, pero si se pone setSingleLine no hace falta pq se ajusta a las lineas del texto
                view.setLayoutParams(params);
                snack
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(getApplicationContext(), MailActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String email = null, contras = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password=findViewById(R.id.password);
        user=findViewById(R.id.user);
        btnlogin=findViewById(R.id.buttonLogin);
        btnRegistro=findViewById(R.id.buttonRegistrar);
        //imagen en la primera pantalla
        imageView= findViewById(R.id.imageView);

        //una vez registrado presenta los datos de login si viene de login o de edit
        Bundle extra = getIntent().getExtras();
        if (extra !=null){
            email = extra.getString("correo");
            contras = extra.getString("contras");

        // configuras los valores
        user.setText(email);
        password.setText(contras);}
        else{
            //revupera preferencias si no viene de login o de editarperfil
            recuperarPreferencias();
        }
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
                //finish();
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
                   // finish();

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
                Map<String, String> parametros = new HashMap<>();
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