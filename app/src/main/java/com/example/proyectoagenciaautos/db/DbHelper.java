package com.example.proyectoagenciaautos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOMBRE="agencia.db";
    public static final String TABLE_USUARIOS="t_usuarios";
    public static final String TABLE_AUTOS="t_autos";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_USUARIOS+"(" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT," +
                "correo TEXT NOT NULL," +
                "password TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "apellidoP TEXT NOT NULL," +
                "apellidoM TEXT NOT NULL," +
                "cargo TEXT NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_AUTOS+"(" +
                "idAuto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "modelo TEXT NOT NULL," +
                "marca TEXT NOT NULL," +
                "anio INTEGER NOT NULL," +
                "combustible TEXT NOT NULL," +
                "precio FLOAT NOT NULL," +
                "ruta TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_AUTOS);
        onCreate(sqLiteDatabase);

    }
}

