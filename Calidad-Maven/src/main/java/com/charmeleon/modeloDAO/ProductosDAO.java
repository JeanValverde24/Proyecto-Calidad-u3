package com.charmeleon.modeloDAO; // Corregido el nombre del paquete según la convención de nombres

import com.charmeleon.modelo.Conexion;
import com.charmeleon.modelo.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {
    private PreparedStatement ps;
    private ResultSet rs;
    private Connection con;
    private Conexion conectar;

    public ProductosDAO(Conexion conectar) {
        this.conectar = conectar;
        this.con = conectar.getConnection();
    }

    public List<Productos> listarProductos() {
        List<Productos> datos = new ArrayList<>();
        String sql = "SELECT Id, Nombre, Descripcion, Precio, Cantidad, Categoria FROM tbproducto";

        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Productos producto = new Productos();
                producto.setId(rs.getInt("Id"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setCategoria(rs.getString("Categoria"));
                datos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return datos;
    }

    public int agregarProducto(Productos producto) {
        int resultado = 0;
        String sqlSelect = "SELECT COUNT(*) FROM tbproducto WHERE Id = ?";
        String sqlInsert = "INSERT INTO tbproducto (Id, Nombre, Descripcion, Precio, Cantidad, Categoria) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement psSelect = null;
        PreparedStatement psInsert = null;
        ResultSet rsSelect = null;

        try {
            con = conectar.getConnection();
            psSelect = con.prepareStatement(sqlSelect);
            psSelect.setInt(1, producto.getId());
            rsSelect = psSelect.executeQuery();

            if (rsSelect.next()) {
                int count = rsSelect.getInt(1);
                if (count > 0) {
                    return 0;
                }
            }

            psInsert = con.prepareStatement(sqlInsert);
            psInsert.setInt(1, producto.getId());
            psInsert.setString(2, producto.getNombre());
            psInsert.setString(3, producto.getDescripcion());
            psInsert.setDouble(4, producto.getPrecio());
            psInsert.setInt(5, producto.getCantidad());
            psInsert.setString(6, producto.getCategoria());
            resultado = psInsert.executeUpdate();

            if (resultado == 1) {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(psSelect, psInsert, rsSelect);
        }
        return resultado;
    }

    public int eliminarProducto(int id) {
        int resultado = 0;
        String sql = "DELETE FROM tbproducto WHERE Id = ?";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            resultado = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return resultado;
    }

    public Productos obtenerProductoPorId(int id) {
        Productos producto = null;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(
                    "SELECT Id, Nombre, Descripcion, Precio, Cantidad, Categoria FROM tbproducto WHERE Id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto = new Productos();
                producto.setId(rs.getInt("Id"));
                producto.setNombre(rs.getString("Nombre"));
                producto.setDescripcion(rs.getString("Descripcion"));
                producto.setPrecio(rs.getDouble("Precio"));
                producto.setCantidad(rs.getInt("Cantidad"));
                producto.setCategoria(rs.getString("Categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return producto;
    }

    public int actualizarProducto(Productos producto) {
        int resultado = 0;
        try {
            con = conectar.getConnection();
            String sql = "UPDATE tbproducto SET Nombre = ?, Descripcion = ?, Precio = ?, Cantidad = ?, Categoria = ? WHERE Id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getCantidad());
            ps.setString(5, producto.getCategoria());
            ps.setInt(6, producto.getId());
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos(ps, rs);
        }
        return resultado;
    }

    private void cerrarRecursos(PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        cerrarConexion(con); // Llamada al método para cerrar la conexión
    }

    private void cerrarRecursos(PreparedStatement ps1, PreparedStatement ps2, ResultSet rs) {
        cerrarRecursos(ps1, rs); // Cierra el primer PreparedStatement y el ResultSet
        cerrarRecursos(ps2, null); // Cierra el segundo PreparedStatement
    }

    private void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}