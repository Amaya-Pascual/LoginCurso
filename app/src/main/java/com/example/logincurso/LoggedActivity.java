package com.example.logincurso;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class LoggedActivity extends AppCompatActivity {
    Button btnCerrar, btnLlamar; //boton de tipo cerrarSesion
    TextView bienvenida;
    ImageView imagenLavin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_activity);
        btnCerrar = findViewById(R.id.cerrarSesion);
        btnLlamar = findViewById(R.id.btnLlamar);
        bienvenida=findViewById(R.id.bienvenida);
        imagenLavin = findViewById(R.id.imageView2);
        SharedPreferences preferences = getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
        String dato = preferences.getString("mail", "usuario");
        bienvenida.setText("Usuario registrado: " + dato);
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
                Toast.makeText(LoggedActivity.this, "Dónde estamos", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoggedActivity.this, "Se borran sus datos de sesion", Toast.LENGTH_LONG).show();
                //hemos borrado lo guardado en preferencias
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("tel:5551234");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                try {
                    startActivity(callIntent);
                } catch (ActivityNotFoundException e)
                {
                    Toast.makeText(LoggedActivity.this, "Ha habido un error", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
