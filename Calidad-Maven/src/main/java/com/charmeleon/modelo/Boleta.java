/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.charmeleon.modelo;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Boleta {
    int NBoleta;
    String Cliente;
    int  Dni;
    Date FechaE;
    String Hora;

    public Boleta() {
    }

    public Boleta(int NBoleta, String Cliente, int Dni, Date FechaE, String Hora) {
        this.NBoleta = NBoleta;
        this.Cliente = Cliente;
        this.Dni = Dni;
        this.FechaE = FechaE;
        this.Hora = Hora;
    }

    public int getNBoleta() {
        return NBoleta;
    }

    public void setNBoleta(int NBoleta) {
        this.NBoleta = NBoleta;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String Cliente) {
        this.Cliente = Cliente;
    }

    public int getDni() {
        return Dni;
    }

    public void setDni(int Dni) {
        this.Dni = Dni;
    }

    public Date getFechaE() {
        return FechaE;
    }

    public void setFechaE(Date FechaE) {
        this.FechaE = FechaE;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    
    
}
