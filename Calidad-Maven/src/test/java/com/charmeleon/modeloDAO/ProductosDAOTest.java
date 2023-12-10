package com.charmeleon.modeloDAO;

import com.charmeleon.modelo.Conexion;
import com.charmeleon.modelo.Productos;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductosDAOTest {
    private Conexion conectarMock;
    private Connection conMock;
    private PreparedStatement psMock;
    private PreparedStatement psSelectMock;
    private PreparedStatement psInsertMock;
    private PreparedStatement psUpdateMock;
    private PreparedStatement psDeleteMock;
    private ResultSet rsMock;
    private ProductosDAO productosDAO;

    @BeforeEach
    public void setUp() {
        conectarMock = mock(Conexion.class);
        conMock = mock(Connection.class);
        psMock = mock(PreparedStatement.class);
        psSelectMock = mock(PreparedStatement.class);
        psInsertMock = mock(PreparedStatement.class);
        psUpdateMock = mock(PreparedStatement.class);
        psDeleteMock = mock(PreparedStatement.class);
        rsMock = mock(ResultSet.class);

        when(conectarMock.getConnection()).thenReturn(conMock);

        try {
            when(conMock.prepareStatement(any(String.class))).thenAnswer(invocation -> {
                String sql = invocation.getArgument(0);
                if (sql.startsWith("SELECT COUNT(*) FROM tbproducto")) {
                    when(psSelectMock.executeQuery()).thenReturn(rsMock);
                    return psSelectMock;
                } else if (sql.startsWith("INSERT INTO tbproducto")) {
                    return psInsertMock;
                } else if (sql.startsWith("UPDATE tbproducto")) {
                    return psUpdateMock;
                } else if (sql.startsWith("DELETE FROM tbproducto")) {
                    return psDeleteMock;
                } else if (sql
                        .startsWith("SELECT Id, Nombre, Descripcion, Precio, Cantidad, Categoria FROM tbproducto")) {
                    when(psMock.executeQuery()).thenReturn(rsMock);
                    return psMock;
                }
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        productosDAO = new ProductosDAO(conectarMock);
    }

    @Test
    public void testListarProductos() throws SQLException {
        // Configura los comportamientos de rsMock para simular los resultados de la base de datos
        when(rsMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rsMock.getInt("Id")).thenReturn(1).thenReturn(2);
        when(rsMock.getString("Nombre")).thenReturn("Producto 1").thenReturn("Producto 2");
        // ... Repite para los otros campos como Descripcion, Precio, etc.
    
        // Realiza la llamada al método y verifica los resultados
        List<Productos> resultado = productosDAO.listarProductos();
    
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    
        // Verifica que los métodos de PreparedStatement y ResultSet se llamen correctamente
        verify(psMock, times(1)).executeQuery();
    }

    @Test
    public void testListarProductosConExcepcion() throws SQLException {
        
        when(conMock.prepareStatement(any(String.class))).thenThrow(new SQLException());
    
        List<Productos> resultado = productosDAO.listarProductos();
        assertTrue(resultado.isEmpty()); // Verifica que la lista esté vacía en caso de error
    }

    @Test
    public void testAgregarProducto() throws SQLException {
        // Configuración de los mocks para simular la lógica de la base de datos
        when(psSelectMock.executeQuery()).thenReturn(rsMock);
        when(rsMock.next()).thenReturn(false); // Simula que no hay productos existentes con el mismo ID
        when(psInsertMock.executeUpdate()).thenReturn(1); // Simula una inserción exitosa

        // Creación del producto a agregar
        Productos producto = new Productos();
        producto.setId(1);
        producto.setNombre("Producto Test");
        producto.setDescripcion("Descripción Test");
        producto.setPrecio(100.0);
        producto.setCantidad(10);
        producto.setCategoria("Categoría Test");

        // Llamada al método agregarProducto
        int resultado = productosDAO.agregarProducto(producto);

        // Verificación de los resultados
        assertEquals(1, resultado); // Verifica que el resultado de la inserción sea exitoso

        // Verificación de que los métodos de PreparedStatement se llamen correctamente
        verify(psSelectMock, times(1)).setInt(1, producto.getId());
        verify(psSelectMock, times(1)).executeQuery();
        verify(psInsertMock, times(1)).setInt(1, producto.getId());
        verify(psInsertMock, times(1)).setString(2, producto.getNombre());
        verify(psInsertMock, times(1)).setString(3, producto.getDescripcion());
        verify(psInsertMock, times(1)).setDouble(4, producto.getPrecio());
        verify(psInsertMock, times(1)).setInt(5, producto.getCantidad());
        verify(psInsertMock, times(1)).setString(6, producto.getCategoria());
        verify(psInsertMock, times(1)).executeUpdate();
    }

    @Test
    public void testAgregarProductoConExcepcion() throws SQLException {
        Productos producto = new Productos();
        producto.setPrecio(10.0); // Configura los detalles del Producto

        // Configura el mock para lanzar una excepción al intentar ejecutar la
        // actualización
        when(psInsertMock.executeUpdate()).thenThrow(new SQLException());

        int resultado = productosDAO.agregarProducto(producto);
        assertEquals(0, resultado); // Verifica que en caso de error se retorne 0

        // Verifica que se haya intentado ejecutar la actualización
        verify(psInsertMock, times(1)).executeUpdate();
    }

    @Test
    public void testAgregarProductoExistente() throws SQLException {
        Productos producto = new Productos();
        producto.setId(1); // Asegúrate de que el ID está configurado
        producto.setPrecio(19.99); // Asegúrate de que el precio no sea nulo

        when(psSelectMock.executeQuery()).thenReturn(rsMock);
        when(rsMock.next()).thenReturn(true); // Simula que hay un producto existente

        int resultado = productosDAO.agregarProducto(producto);
        assertEquals(0, resultado); // Asumiendo que si el producto ya existe, se retorna 0
    }

    @Test
    public void testObtenerProductoPorId() throws SQLException {
        // Configura los comportamientos de rsMock para simular los resultados de la base de datos
        when(rsMock.next()).thenReturn(true);
        when(rsMock.getInt("Id")).thenReturn(1);
        when(rsMock.getString("Nombre")).thenReturn("Producto 1");
        // Configura otros campos como Descripcion, Precio, etc.

        // Llamada al método obtenerProductoPorId
        Productos producto = productosDAO.obtenerProductoPorId(1);

        assertNotNull(producto);
        assertEquals(1, producto.getId());
        assertEquals("Producto 1", producto.getNombre());
        // Verifica otros campos

        // Verifica que los métodos de PreparedStatement y ResultSet se llamen correctamente
        verify(psMock, times(1)).setInt(1, 1);
        verify(psMock, times(1)).executeQuery();
    }

 @Test
    public void testEliminarProductoConExcepcion() throws SQLException {
    when(psMock.executeUpdate()).thenThrow(new SQLException());

    int resultado = productosDAO.eliminarProducto(1);
    assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
}

}
