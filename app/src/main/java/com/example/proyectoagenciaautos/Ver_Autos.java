package com.example.proyectoagenciaautos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import com.example.proyectoagenciaautos.adaptadores.ListaAutosAdapter;
import com.example.proyectoagenciaautos.db.DbAgencia;
import com.example.proyectoagenciaautos.entidades.Autos;
import com.example.proyectoagenciaautos.entidades.Sesion;

import java.util.ArrayList;

public class Ver_Autos extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SearchView txtBuscar;
    RecyclerView listaAutos;
    ArrayList<Autos> listaArrayAutos;
    ListaAutosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_autos);

        listaAutos=findViewById(R.id.listaAutos);
        txtBuscar=findViewById(R.id.search);
        listaAutos.setLayoutManager(new LinearLayoutManager(Ver_Autos.this));

        DbAgencia dbAgencia= new DbAgencia(Ver_Autos.this);

        listaArrayAutos= new ArrayList<>();

         adapter= new ListaAutosAdapter(dbAgencia.mostrarAutos());
        listaAutos.setAdapter(adapter);


        txtBuscar.setOnQueryTextListener(this);
    }
    @Override
    public void onBackPressed() {
        if(Sesion.getTipo().equals("ADMIN")) {
            Intent intent = new Intent(Ver_Autos.this, Menu_Administrador
                    .class);
            startActivity(intent);
        }
        if(Sesion.getTipo().equals("NORMAL")) {
            Intent intent = new Intent(Ver_Autos.this, Menu_Usuario
                    .class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        adapter.filtrado(newText);

        return false;
    }
}