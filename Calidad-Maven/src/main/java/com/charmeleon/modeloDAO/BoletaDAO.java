package com.charmeleon.modeloDAO;

import com.charmeleon.modelo.Boleta;
import com.charmeleon.modelo.BoletaDetalle;
import com.charmeleon.modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoletaDAO {
    private final Conexion conectar;

    public BoletaDAO(Conexion conectar) {
        this.conectar = conectar;
    }

    public List<Boleta> listarBoletas() {
        List<Boleta> datos = new ArrayList<>();
        String sql = "SELECT NBoleta, Cliente, Dni, FechaE, Hora FROM tbboleta";
        try (Connection connection = conectar.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Boleta boleta = new Boleta();
                boleta.setNBoleta(resultSet.getInt("NBoleta"));
                boleta.setCliente(resultSet.getString("Cliente"));
                boleta.setDni(resultSet.getInt("Dni"));
                boleta.setFechaE(resultSet.getDate("FechaE"));
                boleta.setHora(resultSet.getString("Hora"));
                datos.add(boleta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datos;
    }

    public int agregarBoleta(Boleta boleta) {
        int resultado = 0;
        String sql = "INSERT INTO tbboleta (NBoleta, Cliente, Dni, FechaE, Hora) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = conectar.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, boleta.getNBoleta());
            preparedStatement.setString(2, boleta.getCliente());
            preparedStatement.setInt(3, boleta.getDni());
            preparedStatement.setDate(4, new java.sql.Date(boleta.getFechaE().getTime()));
            preparedStatement.setString(5, boleta.getHora());

            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public int eliminarBoleta(int NBoleta) {
        int resultado = 0;
        String sql = "DELETE FROM tbboleta WHERE NBoleta = ?";
        try (Connection connection = conectar.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, NBoleta);
            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Boleta obtenerBoletaPorNBoleta(int NBoleta) {
        Boleta boleta = null;
        String sql = "SELECT NBoleta, Cliente, Dni, FechaE, Hora FROM tbboleta WHERE NBoleta = ?";
        try (Connection connection = conectar.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, NBoleta);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    boleta = new Boleta();
                    boleta.setNBoleta(resultSet.getInt("NBoleta"));
                    boleta.setCliente(resultSet.getString("Cliente"));
                    boleta.setDni(resultSet.getInt("Dni"));
                    boleta.setFechaE(resultSet.getDate("FechaE"));
                    boleta.setHora(resultSet.getString("Hora"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boleta;
    }

    public void insertarBoletaDetalle(BoletaDetalle boletaDetalle) {
        String sql = "INSERT INTO tbboletadetalle (Producto, Descripcion, Cantidad, Moneda, Precio, Subtotal, Total, fkboleta) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = conectar.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, boletaDetalle.getProducto());
            preparedStatement.setString(2, boletaDetalle.getDescripcion());
            preparedStatement.setInt(3, boletaDetalle.getCantidad());
            preparedStatement.setString(4, boletaDetalle.getMoneda());
            preparedStatement.setDouble(5, boletaDetalle.getPrecio());
            preparedStatement.setDouble(6, boletaDetalle.getSubtotal());
            preparedStatement.setDouble(7, boletaDetalle.getTotal());
            preparedStatement.setInt(8, boletaDetalle.getFkboelta());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int actualizarBoleta(Boleta boleta) {
        int resultado = 0;
        String sql = "UPDATE tbboleta SET Cliente = ?, Dni = ?, FechaE = ?, Hora = ? WHERE NBoleta = ?";
        try (Connection connection = conectar.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, boleta.getCliente());
            preparedStatement.setInt(2, boleta.getDni());
            preparedStatement.setDate(3, new java.sql.Date(boleta.getFechaE().getTime()));
            preparedStatement.setString(4, boleta.getHora());
            preparedStatement.setInt(5, boleta.getNBoleta());

            resultado = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
