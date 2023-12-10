package com.charmeleon.modeloDAO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.charmeleon.modelo.Cargo;
import com.charmeleon.modelo.Conexion;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CargoDAOTest {

    @Mock
    private Conexion conectar;
    @Mock
    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    private CargoDAO cargoDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(conectar.getConnection()).thenReturn(con);
        when(con.prepareStatement(any(String.class))).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(ps.executeUpdate()).thenReturn(1); // Para los métodos que usan executeUpdate

        cargoDAO = new CargoDAO(conectar);
    }

    @Test
    public void testListarCargo() throws SQLException {
        // Configura las respuestas simuladas del ResultSet
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("Id")).thenReturn(1).thenReturn(2);
        when(rs.getString("Nombre")).thenReturn("Cargo 1").thenReturn("Cargo 2");
        when(rs.getString("Descripcion")).thenReturn("Descripción 1").thenReturn("Descripción 2");

        List<Cargo> cargos = cargoDAO.listarCargo();

        assertNotNull(cargos);
        assertEquals(2, cargos.size()); // Verificar que la lista tiene 2 cargos

        // Verificar los detalles de los cargos
        Cargo primerCargo = cargos.get(0);
        Cargo segundoCargo = cargos.get(1);
        assertEquals(1, primerCargo.getId());
        assertEquals("Cargo 1", primerCargo.getNombre());
        assertEquals("Descripción 1", primerCargo.getDescripcion());
        assertEquals(2, segundoCargo.getId());
        assertEquals("Cargo 2", segundoCargo.getNombre());
        assertEquals("Descripción 2", segundoCargo.getDescripcion());

        // Verificar que los métodos de la base de datos se llaman correctamente
        verify(ps, times(1)).executeQuery();
        verify(rs, times(3)).next(); // Verifica que se haya llamado next() el número correcto de veces
    }

    @Test
    public void testAgregarCargo() throws SQLException {
        Cargo cargo = new Cargo();
        cargo.setId(1); // Configurar con valores de ejemplo
        cargo.setNombre("Nombre del Cargo");
        cargo.setDescripcion("Descripción del Cargo");

        int resultado = cargoDAO.agregarCargo(cargo);

        assertEquals(1, resultado);

        // Verificar que los setters del preparedStatement se llamen con los valores
        // correctos
        verify(ps, times(1)).setInt(1, cargo.getId());
        verify(ps, times(1)).setString(2, cargo.getNombre());
        verify(ps, times(1)).setString(3, cargo.getDescripcion());

        // Verificar que se haya ejecutado la actualización
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testEliminarCargo() throws SQLException {
    // Simula una respuesta exitosa de la base de datos
    when(ps.executeUpdate()).thenReturn(1);

    int resultado = cargoDAO.eliminarCargo(1); // ID de prueba

    assertEquals(1, resultado);
    verify(ps, times(1)).setInt(1, 1);
    verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testEliminarCargoConExcepcion() throws SQLException {
    // Simula una excepción al intentar ejecutar la actualización
    when(ps.executeUpdate()).thenThrow(new SQLException());

    int resultado = cargoDAO.eliminarCargo(1); // ID de prueba

    assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
    verify(ps, times(1)).setInt(1, 1);
    verify(ps, times(1)).executeUpdate();
}

@Test
public void testObtenerCargoPorId() throws SQLException {
    when(rs.next()).thenReturn(true);
    when(rs.getInt("Id")).thenReturn(1);
    when(rs.getString("Nombre")).thenReturn("Cargo Test");
    when(rs.getString("Descripcion")).thenReturn("Descripción Test");

    Cargo cargo = cargoDAO.obtenerCargoPorId(1);

    assertNotNull(cargo);
    assertEquals(1, cargo.getId());
    assertEquals("Cargo Test", cargo.getNombre());
    assertEquals("Descripción Test", cargo.getDescripcion());

    verify(ps, times(1)).executeQuery();
}

@Test
public void testObtenerCargoPorIdConExcepcion() throws SQLException {
    when(ps.executeQuery()).thenThrow(new SQLException());

    Cargo cargo = cargoDAO.obtenerCargoPorId(1);

    assertNull(cargo); // Asumiendo que en caso de error se retorna null
    verify(ps, times(1)).executeQuery();
}

@Test
public void testObtenerCargoPorIdNoEncontrado() throws SQLException {
    when(rs.next()).thenReturn(false);

    Cargo cargo = cargoDAO.obtenerCargoPorId(1);

    assertNull(cargo); // Cuando no se encuentra el Cargo
    verify(ps, times(1)).executeQuery();
}

    @Test
    public void testActualizarCargo() throws SQLException {
        Cargo cargo = new Cargo();
        cargo.setId(1); // Asumiendo que el ID es necesario
        cargo.setNombre("Nombre Actualizado");
        cargo.setDescripcion("Descripción Actualizada");

        when(ps.executeUpdate()).thenReturn(1);

        int resultado = cargoDAO.actualizarCargo(cargo);

        assertEquals(1, resultado);
        verify(ps, times(1)).setString(1, cargo.getNombre());
        verify(ps, times(1)).setString(2, cargo.getDescripcion());
        verify(ps, times(1)).setInt(3, cargo.getId());
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testActualizarCargoConExcepcion() throws SQLException {
        Cargo cargo = new Cargo();
        // Configura el cargo

        when(ps.executeUpdate()).thenThrow(new SQLException());

        int resultado = cargoDAO.actualizarCargo(cargo);

        assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
        verify(ps, times(1)).executeUpdate();
    }

}
