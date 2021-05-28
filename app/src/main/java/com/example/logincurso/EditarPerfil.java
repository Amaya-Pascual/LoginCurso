package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class EditarPerfil extends AppCompatActivity {
    //conexion bd
    String URL_SERVIDOR = "http://194.30.35.183/subasta/editarUsuario.php";


    EditText etContrasena, etNombre, etApellido;
    Button btnEditar, btnVolverLogin;
    TextView txtMail;

    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.inicio:
                Toast.makeText(this, "Inicio", Toast.LENGTH_LONG ).show();
                Intent i  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                return true;
            case R.id.catalogo:
                Toast.makeText(this, "Catálogo", Toast.LENGTH_LONG ).show();
                i = new Intent(getApplicationContext(),CatalogoReciclerView.class);
                startActivity(i);
                return true;
            case R.id.perfil:
                Toast.makeText(this, "Catálogo", Toast.LENGTH_LONG ).show();
                i = new Intent(getApplicationContext(),EditarPerfil.class);
                startActivity(i);
                return true;
            case R.id.tumoneda:
                //para que el snackbar salga arriba FrameLayout.LayoutParams
                String enviaFoto="Enviar una foto. \nPara enviar foto pulsa en Cámara, \nsaca la foto, acepta,y después modifica \nel Asunto y \nel Cuerpo del mensaje";
                //creo el snack, con el texto deseado
                Snackbar snack = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), enviaFoto, Snackbar.LENGTH_INDEFINITE);
                //obtengo el textView del snack
                TextView snckBarTxt = (TextView) snack.getView().findViewById(com.google.android.material.R.id.snackbar_text); //si cambia la version gradle, hay que revisar esto
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        txtMail = findViewById(R.id.txtmail);
        etContrasena = findViewById(R.id.etContrasena);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        btnEditar = findViewById(R.id.btnEditar);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);
        SharedPreferences preferences = getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
        String mail = preferences.getString("mail", "usuario");
        //toma el mail de las preferencias
        txtMail.setText(mail);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar(); //edita el valor, y lo muestra
                //ocultamos el boton porque ya está editado
                btnEditar.setVisibility(View.INVISIBLE);
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
    private void editar() {
        StringRequest stringRequest;
        stringRequest = new StringRequest(Request.Method.POST, URL_SERVIDOR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //mensajes en el php
                        if(response.equals("ERROR 1")) {
                            Toast.makeText(EditarPerfil.this, getString(R.string.error_campos_vacios), Toast.LENGTH_SHORT).show();
                        } else if(response.equals("ERROR 2")) {
                            Toast.makeText(EditarPerfil.this, getString(R.string.error_edicion), Toast.LENGTH_SHORT).show();
                        } else if(response.equals("MENSAJE")) {
                            Toast.makeText(EditarPerfil.this, getString(R.string.edicion_correcta), Toast.LENGTH_LONG).show();
                            //paso los datos de registro a la pantalla de MainActivity
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            i.putExtra("correo", txtMail.getText().toString().trim());
                            i.putExtra("contras", etContrasena.getText().toString().trim());
                            i.putExtra("nom", etNombre.getText().toString().trim());
                            i.putExtra("ape", etApellido.getText().toString().trim());
                            startActivity(i);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // En caso de tener algun error en la obtencion de los datos
                Toast.makeText(EditarPerfil.this, getString(R.string.error_conexion), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // En este metodo se hace el envio de valores de la aplicacion al servidor
                Map<String, String> parametros = new Hashtable<String, String>();
                parametros.put("mail", txtMail.getText().toString().trim());
                parametros.put("contrasena", etContrasena.getText().toString().trim());
                parametros.put("nomCliente", etNombre.getText().toString().trim());
                parametros.put("ape1Cliente", etApellido.getText().toString().trim());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EditarPerfil.this);
        requestQueue.add(stringRequest);
    }
    }

