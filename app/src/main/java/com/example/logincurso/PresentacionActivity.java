package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;


public class PresentacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences=getSharedPreferences("loginPreferencia", Context.MODE_PRIVATE);
                //comprueba si en las preferencias hay una sesion creada, y si es true va a la aplicacion
                boolean sesion = preferences.getBoolean("sesion", false);
                //recordar que si hemos cerrado sesion las preferencias se han borrado
                if (sesion){
                    //si hay sesion entra
                    Intent intent = new Intent(getApplicationContext(), LoggedActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    //si no hay sesion presenta pantalla de login
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }
}