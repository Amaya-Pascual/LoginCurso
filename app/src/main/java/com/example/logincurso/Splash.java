package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Splash extends AppCompatActivity {
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sonido en la pantalla de entrada
        mp = MediaPlayer.create(Splash.this, R.raw.inicioapp);
        mp.start();
        Intent intent = new Intent(this, PresentacionActivity.class);
        startActivity(intent);
        finish(); // evitar regresar a esta activity
    }
    //aunque la musica es corta, con esto al salir de esta activity se apaga la musica
  /*  protected void onPause() {
        super.onPause();
        if (mp!=null){
            mp.stop();
            mp.release();
            mp=null;
        }
    }*/

}