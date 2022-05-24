package com.example.proyectoagenciaautos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectoagenciaautos.db.DbAgencia;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Agregar extends AppCompatActivity {
    EditText txtModelo,txtMarca,txtPrecio,txtCombustible,txtAnio;
    Button btnRegistrar;
    ImageButton btnFoto;

    String Currentpath="";
    private static final int REQUEST_PERMISSION_CAMARA = 100;
    private static final int REQUEST_IMAGE_CAMARA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        txtModelo=findViewById(R.id.txtModelo);
        txtMarca=findViewById(R.id.txtMarca);
        txtAnio=findViewById(R.id.txtAnio);
        txtCombustible=findViewById(R.id.txtCombustible);
        txtPrecio=findViewById(R.id.txtPrecio);
        btnRegistrar=findViewById(R.id.btnEditar);
        btnFoto=findViewById(R.id.btnFoto);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtModelo.getText().toString().isEmpty()&&
                        !txtMarca.getText().toString().isEmpty()&&
                        !txtCombustible.getText().toString().isEmpty()&&
                        !txtPrecio.getText().toString().isEmpty()&&
                        !txtAnio.getText().toString().isEmpty()&&!Currentpath.equals("")) {
                    DbAgencia dbAgencia = new DbAgencia(Agregar.this);
                    float precio=Float.parseFloat(txtPrecio.getText().toString());
                    long id = dbAgencia.insertarAuto(txtModelo.getText().toString(), txtMarca.getText().toString(), txtAnio.getText().toString(), txtCombustible.getText().toString(), precio,Currentpath);
                    if (id > 0) {
                        Toast.makeText(Agregar.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(Agregar.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Agregar.this, "LLENE LOS CAMPOS", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(Agregar.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        AbrirCamara();

                    } else {
                        ActivityCompat.requestPermissions(Agregar.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMARA);
                    }
                } else {
                    AbrirCamara();
                }

            }
        });


    }//OnCreate
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CAMARA) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AbrirCamara();
            } else {
                Toast.makeText(this, "Necesitas permisos para usar la camara", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_IMAGE_CAMARA) {
            if (resultCode == Activity.RESULT_OK) {
               /* Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imagen.setImageBitmap(bitmap);
                Log.i("TAG", "Result=>"+bitmap);*/
                btnFoto.setImageURI(Uri.parse(Currentpath));
                
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void AbrirCamara() {
        Intent abrircamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (abrircamara.resolveActivity(getPackageManager()) != null) {
            //startActivityForResult(abrircamara,REQUEST_IMAGE_CAMARA);

            File fotofile = null;
            try {
                fotofile = createFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fotofile != null) {
                Uri fotouri = FileProvider.getUriForFile(
                        this, "com.example.proyectoagenciaautos",
                        fotofile
                );
                abrircamara.putExtra(MediaStore.EXTRA_OUTPUT, fotouri);
                startActivityForResult(abrircamara, REQUEST_IMAGE_CAMARA);

            }

        }
    }//Abrir camara

    private File createFile() throws IOException {

        String imgnombre = "foto_";
        File storage=getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen=File.createTempFile(
                imgnombre,".jpg",storage
        );
        Currentpath=imagen.getAbsolutePath();

        return  imagen;

    }//createFile
    private void limpiar(){
        txtModelo.setText("");
        txtMarca.setText("");
        txtAnio.setText("");
        txtCombustible.setText("");
        txtPrecio.setText("");
        Currentpath="";

    }
}