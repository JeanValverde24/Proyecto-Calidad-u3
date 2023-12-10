package com.charmeleon.modeloDAO;

import com.charmeleon.modelo.Conexion;
import com.charmeleon.modelo.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    private Connection con;
    private Conexion conectar = new Conexion();

    public PersonaDAO(Conexion conectar) {
        this.conectar = conectar;
    }

    public List<Persona> listar() {
        List<Persona> datos = new ArrayList<>();
        String sql = "SELECT Id, Nombres, Correo, Telefono, '***' AS Clave FROM tbempleado";

        try (Connection con = conectar.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("Id"));
                p.setNom(rs.getString("Nombres"));
                p.setCorreo(rs.getString("Correo"));
                p.setTelefono(rs.getString("Telefono"));
                p.setClave(rs.getString("Clave"));
                datos.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datos;
    }

    public int agregar(Persona per) {
        int r = 0;
        String sqlSelect = "SELECT COUNT(*) FROM tbempleado WHERE Id = ?";
        String sqlInsert = "INSERT INTO tbempleado (Id, Nombres, Correo, Telefono, Clave) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = conectar.getConnection();
                PreparedStatement psSelect = con.prepareStatement(sqlSelect);
                PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            psSelect.setInt(1, per.getId());
            ResultSet rs = psSelect.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                if (count > 0) {
                    return 0;
                }
            }

            psInsert.setInt(1, per.getId());
            psInsert.setString(2, per.getNom());
            psInsert.setString(3, per.getCorreo());
            psInsert.setString(4, per.getTelefono());
            psInsert.setString(5, per.getClave());

            r = psInsert.executeUpdate();

            if (r == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    public int Actualizar(Persona per) {
        int r = 0;
        String sql = "UPDATE tbempleado SET Nombres=?, Correo=?, Telefono=?, Clave=? WHERE Id=?";

        try (Connection con = conectar.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, per.getNom());
            ps.setString(2, per.getCorreo());
            ps.setString(3, per.getTelefono());
            ps.setString(4, per.getClave());
            ps.setInt(5, per.getId());

            r = ps.executeUpdate();

            if (r == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return r;
    }

    public int Delete(int id) {
        int r = 0;
        String sql = "DELETE FROM tbempleado WHERE Id=?";
        try (Connection con = conectar.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            r = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    public int login(Persona per) {
        String sql = "SELECT COUNT(*) FROM tbempleado WHERE Nombres = ? AND Clave = ?";
        try (Connection con = conectar.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, per.getNom());
            ps.setString(2, per.getClave());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
