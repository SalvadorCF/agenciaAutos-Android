package com.example.proyectoagenciaautos.entidades;

public class Sesion {

    private static String usuario;
    private static String password;
    private static String tipo;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String usuario) {
        Sesion.usuario = usuario;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Sesion.password = password;
    }

    public static String getTipo() {
        return tipo;
    }

    public static void setTipo(String tipo) {
        Sesion.tipo = tipo;
    }
}
