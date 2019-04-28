package com.isii.systock.Modelo;

import android.content.Context;

import com.isii.systock.Interfaces.Dialogos.VolleyResponse;

public class Producto {

    public static final String TABLE = "Producto";
    // Labels Table Columns names
    public static final String KEY_COD_PROD = "codProducto";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_DESCRIPCION = "descripcion";
    public static final String KEY_STOCK = "stock";
    public static final String KEY_RUBRO = "rubro";
    public static final String KEY_CANT_MINIMA = "cantMinima";
    public static final String KEY_PRECIO = "precio";

    private int codProducto, stock, cantidadMinima;
    private String nombre, descripcion, rubro;
    private float precio;
    private Context context;
    private VolleyResponse mVolleyResponse;

    public Producto(int codProducto, int stock, String rubro, int cantidadMinima, String nombre, String descripcion, float precio) {
        this.codProducto = codProducto;
        this.stock = stock;
        this.rubro = rubro;
        this.cantidadMinima = cantidadMinima;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Producto() {
        this.codProducto = 0;
        this.stock = 0;
        this.rubro = "";
        this.cantidadMinima = 0;
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0;

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public VolleyResponse getVolleyResponse() {
        return mVolleyResponse;
    }

    public void setVolleyResponse(VolleyResponse volleyResponse) {
        mVolleyResponse = volleyResponse;
    }

    public int getCodProducto() {
        return codProducto;
    }

    public void setCodProducto(int codProducto) {
        this.codProducto = codProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


    public String getRubro() {
        return rubro;
    }

    public void setRubro(String rubro) {
        this.rubro = rubro;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void añadirProducto() {
    }
}
