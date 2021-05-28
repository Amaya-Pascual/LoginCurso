package com.example.logincurso;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;

import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LoggedActivity extends AppCompatActivity {
    Button btnCerrar, btnLlamar, btnCatalogo; //boton de tipo cerrarSesion
    TextView bienvenida;
    ImageView imagenLavin;
//menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.inicio:
                Toast.makeText(this, getString(R.string.menu1inicio), Toast.LENGTH_LONG ).show();
                Intent i  = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                return true;
            case R.id.catalogo:
                Toast.makeText(this, getString(R.string.menu1catalogo), Toast.LENGTH_LONG ).show();
                i = new Intent(getApplicationContext(),CatalogoReciclerView.class);
                startActivity(i);
                return true;
            case R.id.perfil:
                Toast.makeText(this, getString(R.string.menu1perfil), Toast.LENGTH_LONG ).show();
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

               /* Toast.makeText(this, getString(R.string.tu_moneda_menu), Toast.LENGTH_LONG ).show();
                i = new Intent(getApplicationContext(),MailActivity.class);
                startActivity(i);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_activity);
        btnCerrar = findViewById(R.id.cerrarSesion);
        btnLlamar = findViewById(R.id.btnLlamar);
        btnCatalogo = findViewById(R.id.btnCatalogo);
        bienvenida=findViewById(R.id.bienvenida);
        imagenLavin = findViewById(R.id.imageView2);
        SharedPreferences preferences = getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
        String dato = preferences.getString("mail", "usuario");
        bienvenida.setText(getString(R.string.welcome) +", " + dato);
        FloatingActionButton fab;
        fab=findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enviar un mail", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
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
                                    Toast.makeText(LoggedActivity.this, R.string.errorMail, Toast.LENGTH_LONG).show();
                                }
                            }
                        }).show();
            }
        });

        imagenLavin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(LoggedActivity.this, getString(R.string.map_donde_estamos), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UbicacionTiendaMap.class);
                startActivity(intent);
                return false;
            }
        });
        //el boton cerrar borra las preferencias
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Toast.makeText(LoggedActivity.this, getString(R.string.borra_datos_sesion), Toast.LENGTH_LONG).show();
                //hemos borrado lo guardado en preferencias
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:944158010");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                try {
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e)
                {
                    Toast.makeText(LoggedActivity.this, getString(R.string.error_llamar), Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoggedActivity.this, getString(R.string.catalogo), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CatalogoReciclerView.class);
                startActivity(intent);
            }
        });


    }

}
