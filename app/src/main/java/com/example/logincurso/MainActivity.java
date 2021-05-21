package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
                Toast.makeText(MainActivity.this, "Dónde estamos", Toast.LENGTH_SHORT).show();
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
                    Intent i = new Intent(getApplicationContext(), LoggedActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "Contraseña o usuario incorrectos", Toast.LENGTH_LONG).show();
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

    //preferencias
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