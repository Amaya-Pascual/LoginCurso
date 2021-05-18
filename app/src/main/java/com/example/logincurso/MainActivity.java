package com.example.logincurso;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    //al clicar el boton del login vaoms a la siguiente actividad
        btnlogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
               boolean hasErrors = false;
               String error="";
               if (password.length()<6)
               {
                   hasErrors = true;
                   error+="La contraseña debe tener al menos 6 caracteres";
               }
               if (!Patterns.EMAIL_ADDRESS.matcher(user.getText()).matches()){
                   hasErrors = true;
                   error+="\nEl formato del mail de usuario es incorrecto";
               }
               if (!hasErrors){
                   Intent i = new Intent(MainActivity.this, LoggedActivity.class);
                   startActivity(i);
               }
               else{
                   Toast.makeText(MainActivity.this, error, Toast.LENGTH_LONG).show();
               }

            }
        });
    }
}