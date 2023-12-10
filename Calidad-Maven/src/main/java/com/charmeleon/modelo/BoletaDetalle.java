/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.charmeleon.modelo;

/**
 *
 * @author PC
 */
public class BoletaDetalle {
    int Id;
    int Producto;
    String Descripcion;
    int Cantidad;
    String Moneda;
    Double Subtotal;
    Double Precio;
    Double Total;
    int fkboelta;

    public BoletaDetalle() {
    }

    public BoletaDetalle(int Id, int Producto, String Descripcion, int Cantidad, String Moneda, Double Subtotal, Double Precio, Double Total, int fkboelta) {
        this.Id = Id;
        this.Producto = Producto;
        this.Descripcion = Descripcion;
        this.Cantidad = Cantidad;
        this.Moneda = Moneda;
        this.Subtotal = Subtotal;
        this.Precio = Precio;
        this.Total = Total;
        this.fkboelta = fkboelta;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getProducto() {
        return Producto;
    }

    public void setProducto(int Producto) {
        this.Producto = Producto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public String getMoneda() {
        return Moneda;
    }

    public void setMoneda(String Moneda) {
        this.Moneda = Moneda;
    }

    public Double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(Double Subtotal) {
        this.Subtotal = Subtotal;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double Precio) {
        this.Precio = Precio;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double Total) {
        this.Total = Total;
    }

    public int getFkboelta() {
        return fkboelta;
    }

    public void setFkboelta(int fkboelta) {
        this.fkboelta = fkboelta;
    }
    

   
   
    
}
