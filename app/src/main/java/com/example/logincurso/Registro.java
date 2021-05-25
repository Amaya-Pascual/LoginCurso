package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.util.Hashtable;
import java.util.Map;

public class Registro extends AppCompatActivity {

    String URL_SERVIDOR = "http://194.30.35.183/subasta/registrarUsuario.php";

    EditText etUsuario, etContrasena, etNombre, etApellido;
    Button btnRegistrar, btnVolverLogin;

    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sin_registrar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inicio:
                Toast.makeText(this, "Inicio", Toast.LENGTH_LONG ).show();
                Intent i  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                return true;
            case R.id.registro:
                Toast.makeText(this, "Catálogo", Toast.LENGTH_LONG ).show();
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
                            Toast.makeText(Registro.this, "Se deben de llenar todos los campos.", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            Toast.makeText(Registro.this, "Fallo el registro.", Toast.LENGTH_SHORT).show();
                        } else if(response.equals("MENSAJE")) {
                            Toast.makeText(Registro.this, "Registrado correctamente.", Toast.LENGTH_LONG).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(Registro.this, "ERROR CON LA CONEXION", Toast.LENGTH_LONG).show();
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
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Registro.this);
        requestQueue.add(stringRequest);
    }
}