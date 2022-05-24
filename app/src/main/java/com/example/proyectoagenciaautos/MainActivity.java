package com.example.proyectoagenciaautos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoagenciaautos.db.DbAgencia;
import com.example.proyectoagenciaautos.entidades.Sesion;

public class MainActivity extends AppCompatActivity {

    Button btnIngresar,btnRegistrarse;
    EditText txtUsuario,txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIngresar=findViewById(R.id.btnAgregar);
        btnRegistrarse=findViewById(R.id.btnRegistrarse);
        txtUsuario=findViewById(R.id.txtUsuario);
        txtPassword=findViewById(R.id.txtMarca);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtUsuario.getText().toString().isEmpty()&&
                        !txtPassword.getText().toString().isEmpty()) {
                    DbAgencia dbAgencia = new DbAgencia(MainActivity.this);
                    String login = dbAgencia.ingresar(txtUsuario.getText().toString(), txtPassword.getText().toString());

                    if(login.equals("ADMIN")) {
                        Sesion.setUsuario(txtUsuario.getText().toString());
                        Sesion.setPassword(txtPassword.getText().toString());
                        Sesion.setTipo("ADMIN");
                        Intent intent= new Intent (MainActivity.this, Menu_Administrador.class);
                        startActivity(intent);

                    } if (login.equals("NORMAL")) {
                        Sesion.setUsuario(txtUsuario.getText().toString());
                        Sesion.setPassword(txtPassword.getText().toString());
                        Sesion.setTipo("NORMAL");
                        Intent intent= new Intent (MainActivity.this, Menu_Usuario.class);
                        startActivity(intent);

                    }if (login.equals("DENEGADO")) {
                        Toast.makeText(MainActivity.this, "Error en los datos", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "LLENE LOS CAMPOS", Toast.LENGTH_LONG).show();
                }
             }
        });

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent (MainActivity.this, Registrarse.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}