package com.charmeleon.modeloDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.charmeleon.modelo.Boleta;
import com.charmeleon.modelo.BoletaDetalle;
import com.charmeleon.modelo.Conexion;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoletaDAOTest {

    @Mock
    private Conexion conectar;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;

    private BoletaDAO boletaDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(conectar.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        boletaDAO = new BoletaDAO(conectar);
    }

    @Test
    public void testAgregarBoleta() throws SQLException {
        // Creación y configuración del objeto Boleta con datos de prueba
        Boleta boleta = new Boleta();
        boleta.setNBoleta(1);
        boleta.setCliente("Cliente de prueba");
        boleta.setDni(12345678);
        boleta.setFechaE(new java.util.Date());
        boleta.setHora("10:00");

        // Configuración de la respuesta esperada y comportamiento del preparedStatement
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Llamada al método bajo prueba
        int resultado = boletaDAO.agregarBoleta(boleta);

        // Verificar el resultado esperado
        assertEquals(1, resultado);

        // Verifica que los métodos setters del preparedStatement se llamen con los
        // valores correctos
        verify(preparedStatement, times(1)).setInt(1, boleta.getNBoleta());
        verify(preparedStatement, times(1)).setString(2, boleta.getCliente());
        verify(preparedStatement, times(1)).setInt(3, boleta.getDni());
        verify(preparedStatement, times(1)).setDate(4, new java.sql.Date(boleta.getFechaE().getTime()));
        verify(preparedStatement, times(1)).setString(5, boleta.getHora());

        // Verifica que se haya ejecutado la inserción
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testListarBoletas() throws SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("NBoleta")).thenReturn(1);
        when(resultSet.getString("Cliente")).thenReturn("Cliente Prueba");
        when(resultSet.getInt("Dni")).thenReturn(12345678);
        when(resultSet.getDate("FechaE")).thenReturn(new java.sql.Date(0));
        when(resultSet.getString("Hora")).thenReturn("10:00");

        List<Boleta> boletas = boletaDAO.listarBoletas();

        assertFalse(boletas.isEmpty());
        Boleta boletaTest = boletas.get(0);
        assertEquals(1, boletaTest.getNBoleta());
        assertEquals("Cliente Prueba", boletaTest.getCliente());
        assertEquals(12345678, boletaTest.getDni());
        assertEquals("10:00", boletaTest.getHora());

        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, atLeastOnce()).next();
    }

    @Test
    public void testEliminarBoleta() throws SQLException {
        int nBoletaTest = 1;
        when(preparedStatement.executeUpdate()).thenReturn(1);

        int resultado = boletaDAO.eliminarBoleta(nBoletaTest);

        assertEquals(1, resultado);
        verify(preparedStatement, times(1)).setInt(1, nBoletaTest);
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testObtenerBoletaPorNBoleta() throws SQLException {
        int nBoletaTest = 1;
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("NBoleta")).thenReturn(nBoletaTest);
        when(resultSet.getString("Cliente")).thenReturn("Cliente Prueba");
        when(resultSet.getInt("Dni")).thenReturn(12345678);
        when(resultSet.getDate("FechaE")).thenReturn(new java.sql.Date(0));
        when(resultSet.getString("Hora")).thenReturn("10:00");

        Boleta boleta = boletaDAO.obtenerBoletaPorNBoleta(nBoletaTest);

        assertNotNull(boleta);
        assertEquals(nBoletaTest, boleta.getNBoleta());
        assertEquals("Cliente Prueba", boleta.getCliente());
        assertEquals(12345678, boleta.getDni());
        assertEquals("10:00", boleta.getHora());

        verify(preparedStatement, times(1)).setInt(1, nBoletaTest);
        verify(preparedStatement, times(1)).executeQuery();
    }

    @Test
    public void testInsertarBoletaDetalle() throws SQLException {
        // Creación y configuración del objeto BoletaDetalle con datos de prueba
        BoletaDetalle boletaDetalle = new BoletaDetalle();
        boletaDetalle.setProducto(123); // Asume que estos son los métodos y tipos de datos correctos
        boletaDetalle.setDescripcion("Producto de prueba");
        boletaDetalle.setCantidad(10);
        boletaDetalle.setMoneda("USD");
        boletaDetalle.setPrecio(100.0);
        boletaDetalle.setSubtotal(1000.0);
        boletaDetalle.setTotal(1100.0); // Incluyendo impuestos, por ejemplo
        boletaDetalle.setFkboelta(1); // ID de la boleta asociada

        // Configuración de la respuesta esperada y comportamiento del preparedStatement
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Llamada al método bajo prueba
        boletaDAO.insertarBoletaDetalle(boletaDetalle);

        // Verifica que los métodos setters del preparedStatement se llamen con los
        // valores correctos
        verify(preparedStatement, times(1)).setInt(1, boletaDetalle.getProducto());
        verify(preparedStatement, times(1)).setString(2, boletaDetalle.getDescripcion());
        verify(preparedStatement, times(1)).setInt(3, boletaDetalle.getCantidad());
        verify(preparedStatement, times(1)).setString(4, boletaDetalle.getMoneda());
        verify(preparedStatement, times(1)).setDouble(5, boletaDetalle.getPrecio());
        verify(preparedStatement, times(1)).setDouble(6, boletaDetalle.getSubtotal());
        verify(preparedStatement, times(1)).setDouble(7, boletaDetalle.getTotal());
        verify(preparedStatement, times(1)).setInt(8, boletaDetalle.getFkboelta());

        // Verifica que se haya ejecutado la actualización
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    public void testActualizarBoleta() throws SQLException {
        // Creación y configuración del objeto Boleta con datos de prueba
        Boleta boleta = new Boleta();
        boleta.setNBoleta(1); // Asume que este es el identificador de la boleta
        boleta.setCliente("Cliente Actualizado");
        boleta.setDni(98765432);
        boleta.setFechaE(new java.sql.Date(System.currentTimeMillis())); // Fecha actual
        boleta.setHora("15:00");

        // Configuración de la respuesta esperada y comportamiento del preparedStatement
        when(preparedStatement.executeUpdate()).thenReturn(1);

        // Llamada al método bajo prueba
        int resultado = boletaDAO.actualizarBoleta(boleta);

        // Verificar el resultado esperado
        assertEquals(1, resultado);

        // Verifica que los métodos setters del preparedStatement se llamen con los
        // valores correctos
        verify(preparedStatement, times(1)).setString(1, boleta.getCliente());
        verify(preparedStatement, times(1)).setInt(2, boleta.getDni());
        verify(preparedStatement, times(1)).setDate(3, new java.sql.Date(boleta.getFechaE().getTime()));
        verify(preparedStatement, times(1)).setString(4, boleta.getHora());
        verify(preparedStatement, times(1)).setInt(5, boleta.getNBoleta());

        // Verifica que se haya ejecutado la actualización
        verify(preparedStatement, times(1)).executeUpdate();
    }

}
