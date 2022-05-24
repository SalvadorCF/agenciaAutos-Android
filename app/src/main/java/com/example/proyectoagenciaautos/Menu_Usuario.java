package com.example.proyectoagenciaautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Usuario extends AppCompatActivity {
    Button btnAgregar,btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        btnAgregar=findViewById(R.id.btnAgregar);
        btnModificar=findViewById(R.id.btnModificar);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (Menu_Usuario.this, Agregar.class);
                startActivity(intent);
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (Menu_Usuario.this, Ver_Autos.class);
                startActivity(intent);

            }
        });

    }
    @Override
    public void onBackPressed() {

    }
}