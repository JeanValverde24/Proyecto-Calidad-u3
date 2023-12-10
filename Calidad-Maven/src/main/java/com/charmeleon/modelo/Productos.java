/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.charmeleon.modelo;

import javax.swing.JButton;

/**
 *
 * @author PC
 */
public class Productos {
     int id;
    String Nombre;
    String Descripcion;
    Double Precio;
    int Cantidad;
    String Categoria;
    private JButton botonModificar;
    private JButton botonEliminar;
    private JButton botonDetalles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double Precio) {
        this.Precio = Precio;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public JButton getBotonModificar() {
        return botonModificar;
    }

    public void setBotonModificar(JButton botonModificar) {
        this.botonModificar = botonModificar;
    }

    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    public void setBotonEliminar(JButton botonEliminar) {
        this.botonEliminar = botonEliminar;
    }

    public JButton getBotonDetalles() {
        return botonDetalles;
    }

    public void setBotonDetalles(JButton botonDetalles) {
        this.botonDetalles = botonDetalles;
    }

    public Productos() {
    }

    public Productos(int id, String Nombre, String Descripcion, Double Precio, int Cantidad, String Categoria, JButton botonModificar, JButton botonEliminar, JButton botonDetalles) {
        this.id = id;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
        this.Cantidad = Cantidad;
        this.Categoria = Categoria;
        this.botonModificar = botonModificar;
        this.botonEliminar = botonEliminar;
        this.botonDetalles = botonDetalles;
    }

   
    
}

