package com.example.proyectoagenciaautos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.proyectoagenciaautos.db.DbAgencia;
import com.example.proyectoagenciaautos.entidades.Autos;

import java.io.File;
import java.io.IOException;

public class Modificar extends AppCompatActivity {
    EditText txtModelo,txtMarca,txtAnio,txtCombustible,txtPrecio;
    Button btnEditar,btnActualizar,btnEliminar;
    ImageButton btnFoto;
    Autos auto;
    int id=0;
    boolean correcto=false;

    String Currentpath="",ruta;
    private static final int REQUEST_PERMISSION_CAMARA = 100;
    private static final int REQUEST_IMAGE_CAMARA = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);

        txtModelo=findViewById(R.id.txtModelo);
        txtMarca=findViewById(R.id.txtMarca);
        txtAnio=findViewById(R.id.txtAnio);
        txtCombustible=findViewById(R.id.txtCombustible);
        txtPrecio=findViewById(R.id.txtPrecio);
        btnEditar=findViewById(R.id.btnEditar);
        btnEliminar=findViewById(R.id.btn_Eliminar);
        btnActualizar=findViewById(R.id.btnActualizar);
        btnFoto=findViewById(R.id.btnFoto);

        btnActualizar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null){
            Bundle extras= getIntent().getExtras();
            if (extras==null){
                id= Integer.parseInt(null);
            }else{
                id= extras.getInt("ID");
            }
        }else{
            id = (int) savedInstanceState.getSerializable("ID");
        }

        DbAgencia dbAgencia=new DbAgencia(Modificar.this);
        auto=dbAgencia.verAuto(id);
        if (auto!=null){
            txtModelo.setText(auto.getModelo());
            txtMarca.setText(auto.getMarca());
            txtAnio.setText(String.valueOf(auto.getAnio()));
            txtCombustible.setText(auto.getCombustible());
            txtPrecio.setText(String.valueOf(auto.getPrecio()));
            btnFoto.setImageURI(Uri.parse(String.valueOf(auto.getRuta())));
            ruta=String.valueOf(auto.getRuta());

            txtModelo.setTextColor(Color.BLACK);
            txtMarca.setTextColor(Color.BLACK);
            txtAnio.setTextColor(Color.BLACK);
            txtCombustible.setTextColor(Color.BLACK);
            txtPrecio.setTextColor(Color.BLACK);

            txtModelo.setEnabled(false);
            txtMarca.setEnabled(false);
            txtAnio.setEnabled(false);
            txtCombustible.setEnabled(false);
            txtPrecio.setEnabled(false);
            btnFoto.setEnabled(false);
        }

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auto!=null){
                    btnActualizar.setVisibility(View.VISIBLE);
                    txtModelo.setEnabled(true);
                    txtMarca.setEnabled(true);
                    txtAnio.setEnabled(true);
                    txtCombustible.setEnabled(true);
                    txtPrecio.setEnabled(true);
                    btnFoto.setEnabled(true);
                }

            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Modificar.this);
                builder.setMessage("Desea Eliminar este Auto?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (dbAgencia.eliminarAuto(id)){
                            verAuto();
                            Toast.makeText(Modificar.this, "Auto Eliminado", Toast.LENGTH_LONG).show();

                        }

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

            }
        });
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.parseInt(txtAnio.getText().toString());
                float p=Float.parseFloat(txtPrecio.getText().toString());
                if(Currentpath.equals("")) Currentpath=ruta;
                if(!txtModelo.getText().toString().equals("")&&!txtMarca.getText().toString().equals("")&&!txtAnio.getText().toString().equals("")&&!txtCombustible.getText().toString().equals("")&&!txtPrecio.getText().toString().equals("")){
                    correcto=dbAgencia.editarAuto(id,txtModelo.getText().toString(),txtMarca.getText().toString(),a,txtCombustible.getText().toString(),p,Currentpath);

                    if (correcto){
                        Toast.makeText(Modificar.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verAuto();
                    }else{
                        Toast.makeText(Modificar.this, "ERROR AL MODIFICAR", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(Modificar.this, "DEBE DE LLENAR TODOS LOS CAMPOS", Toast.LENGTH_LONG).show();
                }

            }
        });
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(Modificar.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        AbrirCamara();

                    } else {
                        ActivityCompat.requestPermissions(Modificar.this, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMARA);
                    }
                } else {
                    AbrirCamara();
                }
            }
        });


    }
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
    public void verAuto(){
        Intent intent= new Intent (Modificar.this, Ver_Autos.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent (Modificar.this, Ver_Autos
                .class);
        startActivity(intent);
    }
}