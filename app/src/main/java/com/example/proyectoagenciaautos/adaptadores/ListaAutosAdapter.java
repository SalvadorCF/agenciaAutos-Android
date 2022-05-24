package com.example.proyectoagenciaautos.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoagenciaautos.Modificar;
import com.example.proyectoagenciaautos.R;
import com.example.proyectoagenciaautos.entidades.Autos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaAutosAdapter extends RecyclerView.Adapter<ListaAutosAdapter.AutoViewHolder> {

    ArrayList<Autos> listaAutos;
    ArrayList<Autos> listaOriginal;

    public ListaAutosAdapter(ArrayList<Autos> listaAutos){
        this.listaAutos=listaAutos;
        listaOriginal=new ArrayList<>();
        listaOriginal.addAll(listaAutos);
    }

    @NonNull
    @Override
    public AutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_auto,null,false);
        return  new AutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoViewHolder holder, int position) {

        holder.viewModelo.setText(listaAutos.get(position).getModelo());
        holder.viewMarca.setText(listaAutos.get(position).getMarca());
        holder.viewAnio.setText(String.valueOf(listaAutos.get(position).getAnio()));
        holder.viewCombustible.setText(listaAutos.get(position).getCombustible());
        holder.viewPrecio.setText("$"+String.valueOf(listaAutos.get(position).getPrecio()));
        holder.imagen.setImageURI(Uri.parse(listaAutos.get(position).getRuta()));


    }
    public void filtrado(String txtBuscar){
        int longitud=txtBuscar.length();
        if(longitud==0){
            listaAutos.clear();
            listaAutos.addAll(listaOriginal);
        }else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                List<Autos> coleccion= listaAutos.stream().filter(i -> i.getModelo().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listaAutos.clear();
                listaAutos.addAll(coleccion);

            }else{
                for(Autos c: listaOriginal){
                    if (c.getModelo().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listaAutos.add(c);
                    }
                }
            }

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return  listaAutos.size();
    }

    public class AutoViewHolder extends RecyclerView.ViewHolder {
        TextView viewModelo,viewMarca,viewAnio,viewCombustible,viewPrecio;
        ImageView imagen;
        public AutoViewHolder(@NonNull View itemView) {
            super(itemView);

            viewModelo= itemView.findViewById(R.id.viewModelo);
            viewMarca= itemView.findViewById(R.id.viewMarca);
            viewAnio= itemView.findViewById(R.id.viewAnio);
            viewCombustible= itemView.findViewById(R.id.viewCombustible);
            viewPrecio= itemView.findViewById(R.id.viewPrecio);
            imagen=itemView.findViewById(R.id.fotoCarro);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, Modificar.class);
                    intent.putExtra("ID",listaAutos.get(getAdapterPosition()).getId());
                    context.startActivity(intent);

                }
            });
        }
    }
}

