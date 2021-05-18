package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText password, user;
    private Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password=findViewById(R.id.password);
        user=findViewById(R.id.user);
        btnlogin=findViewById(R.id.buttonLogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarUsuario("http://194.30.35.183/subasta/validarUsuario.php");
            }
        });


    //al clicar el boton del login vaoms a la siguiente actividad
        //btnlogin.setOnClickListener(new View.OnClickListener() {
        //    public void onClick(View v)
        //    {
        //       boolean hasErrors = false;
        //       String error="";
        //       if (password.length()<6)
        //       {
         //          hasErrors = true;
        //           error+="La contraseña debe tener al menos 6 caracteres";
         //      }
         //      if (!Patterns.EMAIL_ADDRESS.matcher(user.getText()).matches()){
        //           hasErrors = true;
        //           error+="\nEl formato del mail de usuario es incorrecto";
         //      }
         //      if (!hasErrors){
        //           Intent i = new Intent(MainActivity.this, LoggedActivity.class);
         //          startActivity(i);
         //      }
        //       else{
        //           Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
         //      }

         //   }
       // });
    }

    private void validarUsuario (String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Intent i = new Intent(getApplicationContext(), LoggedActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
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
                parametros.put("mail", user.toString());
                parametros.put("contrasena", password.toString());
                return parametros;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}