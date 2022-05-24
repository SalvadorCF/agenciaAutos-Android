package com.example.proyectoagenciaautos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.proyectoagenciaautos.entidades.Autos;

import java.util.ArrayList;

public class DbAgencia extends DbHelper {
    Context context;

    public DbAgencia(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //Agregar Usuario
    public long insertarUsuario(String correo, String password, String nombre, String apellidoP, String apellidoM,String cargo) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            SQLiteDatabase dbc = dbHelper.getWritableDatabase();

            //Comprobar duplicidad
            Cursor cursor=null;

            cursor= dbc.rawQuery("SELECT * FROM "+TABLE_USUARIOS+" WHERE correo='"+correo+"' LIMIT 1",null);

            if (!cursor.moveToFirst()){
                ContentValues values = new ContentValues();
                values.put("correo", correo);
                values.put("password", password);
                values.put("nombre", nombre);
                values.put("apellidoP", apellidoP);
                values.put("apellidoM", apellidoM);
                values.put("cargo", cargo);

                id = db.insert(TABLE_USUARIOS, null, values);
            }
            cursor.close();
            db.close();
            dbc.close();

        } catch (Exception e) {
            e.toString();
        }
        return id;

    }//Agregar Usuario

    //Ingresar
    public String ingresar(String correo,String password ){
        String aviso="DENEGADO";
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase dbc = dbHelper.getWritableDatabase();

            //Comprobar duplicidad
            Cursor cursor=null;

            cursor= dbc.rawQuery("SELECT cargo FROM "+TABLE_USUARIOS+" WHERE correo='"+correo+"' AND password='"+password+"'   LIMIT 1",null);

            if (cursor.moveToFirst()){
                aviso=cursor.getString(0).toString();

            }
            cursor.close();

            dbc.close();

        } catch (Exception e) {
            e.toString();
        }
        return aviso;

    }//Ingresar

    //Agregar Auto
    public long insertarAuto(String modelo, String marca, String anio, String combustible, float precio,String ruta) {
        long id = 0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            SQLiteDatabase dbc = dbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("modelo", modelo);
                values.put("marca", marca);
                values.put("anio", anio);
                values.put("combustible", combustible);
                values.put("precio", precio);
            values.put("ruta", ruta);

                id = db.insert(TABLE_AUTOS, null, values);

                db.close();
        } catch (Exception e) {
            e.toString();
        }
        return id;

    }//Agregar auto

    //Mostrar Autos
    public ArrayList<Autos> mostrarAutos(){
        DbHelper  dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Autos> listaAutos= new ArrayList<>();
        Autos auto= null;
        Cursor cursorAutos=null;

        cursorAutos= db.rawQuery("SELECT * FROM "+TABLE_AUTOS,null);

        if (cursorAutos.moveToFirst()){
            do{
                auto=new Autos();
                auto.setId(cursorAutos.getInt(0));
                auto.setModelo(cursorAutos.getString(1));
                auto.setMarca(cursorAutos.getString(2));
                auto.setAnio(cursorAutos.getInt(3));
                auto.setCombustible(cursorAutos.getString(4));
                auto.setPrecio(cursorAutos.getFloat(5));
                auto.setRuta(cursorAutos.getString(6));

                listaAutos.add(auto);
            }while (cursorAutos.moveToNext());

        }
        cursorAutos.close();
        db.close();
        return  listaAutos;
    }

    //Ver auto Individual
    public Autos verAuto(int id){
        DbHelper  dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Autos auto= null;
        Cursor cursorAutos=null;

        cursorAutos= db.rawQuery("SELECT * FROM "+TABLE_AUTOS+" WHERE idAuto="+id+" LIMIT 1",null);

        if (cursorAutos.moveToFirst()){

            auto=new Autos();
            auto.setId(cursorAutos.getInt(0));
            auto.setModelo(cursorAutos.getString(1));
            auto.setMarca(cursorAutos.getString(2));
            auto.setAnio(cursorAutos.getInt(3));
            auto.setCombustible(cursorAutos.getString(4));
            auto.setPrecio(cursorAutos.getFloat(5));
            auto.setRuta(cursorAutos.getString(6));

        }
        cursorAutos.close();
        return  auto;
    }

    //Editar Auto
    public boolean editarAuto(int id,String modelo,String marca,int anio,String combustible,float precio,String ruta){
        boolean correcto=false;
        DbHelper  dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE "+TABLE_AUTOS+" SET modelo='"+modelo+"',"+"marca='"+marca+"',"
                    +"anio="+anio+", combustible='"+combustible+"', precio="+precio+
                    ", ruta='"+ruta+"' WHERE idAuto='"+id+"'");
            correcto=true;

        }catch (Exception e){
            e.toString();
            correcto=false;
        }finally {
            db.close();
        }
        return correcto;

    }//Editar auto

    //Eliminar auto
    public boolean eliminarAuto(int id){
        boolean correcto=false;
        DbHelper  dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("DELETE FROM "+TABLE_AUTOS+" WHERE idAuto='"+id+"'");
            correcto=true;

        }catch (Exception e){
            e.toString();
            correcto=false;
        }finally {
            db.close();
        }
        return correcto;

    }//Eliminar contacto



}