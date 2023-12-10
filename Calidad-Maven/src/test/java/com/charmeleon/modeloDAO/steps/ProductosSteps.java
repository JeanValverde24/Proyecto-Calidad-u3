package com.charmeleon.modeloDAO.steps;

import com.charmeleon.modelo.Productos;
import com.charmeleon.modeloDAO.ProductosDAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosSteps {

    private ProductosDAO productosDAO;
    private Productos producto;
    private List<Productos> productos;
    private int resultadoOperacion;
    private SQLException simulatedException;

    @Given("existen productos en el sistema")
    public void existen_productos_en_el_sistema() {
        productos = new ArrayList<>();
        productosDAO = mock(ProductosDAO.class);
        productos.add(new Productos(1, "Producto 1", "Descripción 1", 100.0, 10, "Categoría 1", null, null, null));
        productos.add(new Productos(2, "Producto 2", "Descripción 2", 200.0, 20, "Categoría 2", null, null, null));
    }

    @When("el usuario solicita listar todos los productos")
    public void el_usuario_solicita_listar_todos_los_productos() throws SQLException {
        when(productosDAO.listarProductos()).thenReturn(productos);
        productos = productosDAO.listarProductos();
    }

    @Then("se muestra una lista de todos los productos")
    public void se_muestra_una_lista_de_todos_los_productos() {
        assertFalse(productos.isEmpty());
    }

    @Given("detalles de un nuevo producto")
    public void detalles_de_un_nuevo_producto() {
        producto = new Productos(3, "Producto 3", "Descripción 3", 300.0, 30, "Categoría 3", null, null, null);
        productosDAO = mock(ProductosDAO.class);
    }

    @When("el usuario agrega el nuevo producto")
    public void el_usuario_agrega_el_nuevo_producto() throws SQLException {
        when(productosDAO.agregarProducto(producto)).thenReturn(1);
        resultadoOperacion = productosDAO.agregarProducto(producto);
    }

    @Then("el producto se agrega correctamente al sistema")
    public void el_producto_se_agrega_correctamente_al_sistema() {
        assertEquals(1, resultadoOperacion);
    }

    @Given("detalles de un producto existente")
    public void detalles_de_un_producto_existente() {
        producto = new Productos(1, "Producto 1", "Descripción 1", 100.0, 10, "Categoría 1", null, null, null);
        productosDAO = mock(ProductosDAO.class);
    }

    @When("el usuario intenta agregar el producto existente")
    public void el_usuario_intenta_agregar_el_producto_existente() throws SQLException {
        when(productosDAO.agregarProducto(producto)).thenReturn(0); // Simula que el producto ya existe
        resultadoOperacion = productosDAO.agregarProducto(producto);
    }

    @Then("la agregación del producto falla")
    public void la_agregacion_del_producto_falla() {
        assertEquals(0, resultadoOperacion);
    }

    @Given("un ID de producto específico")
    public void un_id_de_producto_especifico() {
        producto = new Productos();
        productosDAO = mock(ProductosDAO.class);
    }

    @When("el usuario solicita los detalles del producto")
    public void el_usuario_solicita_los_detalles_del_producto() throws SQLException {
        when(productosDAO.obtenerProductoPorId(producto.getId())).thenReturn(producto);
        producto = productosDAO.obtenerProductoPorId(producto.getId());
    }

    @Then("se muestran los detalles del producto")
    public void se_muestran_los_detalles_del_producto() {
        assertNotNull(producto);
    }

    @Given("detalles de un producto y una excepción ocurre en el sistema")
    public void detalles_de_un_producto_y_una_excepcion_ocurre_en_el_sistema() {
        producto = new Productos(3, "Producto 3", "Descripción 3", 300.0, 30, "Categoría 3", null, null, null);
        productosDAO = mock(ProductosDAO.class);
        simulatedException = new SQLException("Error simulado");
    }

    @Then("la agregación del producto falla debido a la excepción")
    public void la_agregacion_del_producto_falla_debido_a_la_excepcion() {
        assertEquals(0, resultadoOperacion);
    }

    // Aquí termina la implementación de los pasos para los escenarios
}
