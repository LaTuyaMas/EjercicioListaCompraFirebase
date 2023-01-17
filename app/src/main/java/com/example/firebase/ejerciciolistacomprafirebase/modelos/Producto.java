package com.example.firebase.ejerciciolistacomprafirebase.modelos;

import java.io.Serializable;
import java.text.NumberFormat;

public class Producto implements Serializable {

    private String nombre;
    private int cantidad;
    private float precio;

    private static final NumberFormat numberFormat;

    static {
        numberFormat = NumberFormat.getCurrencyInstance();
    }

    public Producto() {}

    public Producto(String nombre, int cantidad, float precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getPrecioMoneda() {
        return numberFormat.format(this.precio);
    }

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}

    public float getPrecio() {return precio;}
    public void setPrecio(float precio) {this.precio = precio;}

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", precio=" + precio +
                '}';
    }
}
