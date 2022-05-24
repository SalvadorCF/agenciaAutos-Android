package com.example.proyectoagenciaautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Administrador extends AppCompatActivity {
    Button btnAgregar,btnModificar,btnReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        btnAgregar=findViewById(R.id.btnAgregar);
        btnModificar=findViewById(R.id.btnModificar);
        btnReportes=findViewById(R.id.btnReportes);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (Menu_Administrador.this, Agregar.class);
                startActivity(intent);
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (Menu_Administrador.this, Ver_Autos.class);
                startActivity(intent);

            }
        });
        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (Menu_Administrador.this, Reportes
                        .class);
                startActivity(intent);

            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}