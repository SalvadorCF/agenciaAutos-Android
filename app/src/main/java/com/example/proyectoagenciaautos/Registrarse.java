package com.example.proyectoagenciaautos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectoagenciaautos.db.DbAgencia;

public class Registrarse extends AppCompatActivity {
    Button btnRegistrar;
    EditText txtCorreo,txtPassword,txtNombre,txtApellidoP,txtApellidoM,txtCodigo;
    String c="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        btnRegistrar=findViewById(R.id.btnEditar);
        txtCorreo=findViewById(R.id.txtModelo);
        txtPassword=findViewById(R.id.txtMarca);
        txtNombre=findViewById(R.id.txtAnio);
        txtApellidoP=findViewById(R.id.txtCombustible);
        txtApellidoM=findViewById(R.id.txtPrecio);
        txtCodigo=findViewById(R.id.txtCodigo);




       btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtCodigo.getText().toString().equals("VAL")) {
                    c = "ADMIN";
                } else {
                    c = "NORMAL";
                }
                final String cargo = c;

                if (!txtCorreo.getText().toString().isEmpty()&&
                !txtPassword.getText().toString().isEmpty()&&
                !txtNombre.getText().toString().isEmpty()&&
                !txtApellidoP.getText().toString().isEmpty()&&
                !txtApellidoM.getText().toString().isEmpty()) {
                    DbAgencia dbAgencia = new DbAgencia(Registrarse.this);
                    long id = dbAgencia.insertarUsuario(txtCorreo.getText().toString(), txtPassword.getText().toString(), txtNombre.getText().toString(), txtApellidoP.getText().toString(), txtApellidoM.getText().toString(), cargo);
                    if (id > 0) {
                        Toast.makeText(Registrarse.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(Registrarse.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Registrarse.this, "LLENE LOS CAMPOS", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    private  void limpiar(){
        txtCorreo.setText("");
        txtPassword.setText("");
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtCodigo.setText("");
    }
}