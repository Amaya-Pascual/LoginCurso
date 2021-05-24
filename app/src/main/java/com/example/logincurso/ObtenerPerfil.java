package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ObtenerPerfil extends AppCompatActivity {
    String URL_SERVIDOR = "http://194.30.35.183/subasta/obtenerUsuario.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_perfil);

    }
}