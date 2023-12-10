
package com.charmeleon.modelo;

public class Persona {
    int id;
    String nom;
    String correo;
    String telefono;
    String clave;

    public Persona() {
    }

    public Persona(int id, String nom, String correo, String telefono,String clave) {
        this.id = id;
        this.nom = nom;
        this.correo = correo;
        this.telefono = telefono;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
}
