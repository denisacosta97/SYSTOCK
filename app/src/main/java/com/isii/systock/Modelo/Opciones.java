package com.isii.systock.Modelo;

public class Opciones {

    int id;
    String nombre;
    int logo;

    public Opciones(int id, String nombre, int logo) {
        this.id = id;
        this.nombre = nombre;
        this.logo = logo;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
