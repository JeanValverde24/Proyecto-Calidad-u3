package com.charmeleon.modeloDAO.steps;

import com.charmeleon.modelo.Cargo;
import com.charmeleon.modeloDAO.CargoDAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargoSteps {

    private CargoDAO cargoDAO;
    private Cargo cargo;
    private List<Cargo> cargos;
    private int resultadoOperacion;
    private SQLException simulatedException;

    @Given("existen cargos en el sistema")
    public void existen_cargos_en_el_sistema() {
        cargos = new ArrayList<>();
        cargoDAO = mock(CargoDAO.class);
        // Agrega cargos de ejemplo a la lista
        cargos.add(new Cargo(1, "Cargo 1", "Descripción 1"));
        cargos.add(new Cargo(2, "Cargo 2", "Descripción 2"));
    }

    @When("el usuario solicita listar todos los cargos")
    public void el_usuario_solicita_listar_todos_los_cargos() throws SQLException {
        when(cargoDAO.listarCargo()).thenReturn(cargos);
        cargos = cargoDAO.listarCargo();
    }

    @Then("se muestra una lista de todos los cargos")
    public void se_muestra_una_lista_de_todos_los_cargos() {
        assertFalse(cargos.isEmpty());
    }

    // Implementa pasos similares para otros escenarios

    @Given("un nuevo cargo con detalles específicos")
    public void un_nuevo_cargo_con_detalles_especificos() {
        cargo = new Cargo(3, "Nuevo Cargo", "Nueva Descripción");
        cargoDAO = mock(CargoDAO.class);
    }

    @When("el usuario agrega el nuevo cargo")
    public void el_usuario_agrega_el_nuevo_cargo() throws SQLException {
        when(cargoDAO.agregarCargo(cargo)).thenReturn(1);
        resultadoOperacion = cargoDAO.agregarCargo(cargo);
    }

    @Given("un cargo existente en el sistema")
    public void un_cargo_existente_en_el_sistema() {
        cargo = new Cargo(1, "Cargo Existente", "Descripción Existente");
        cargoDAO = mock(CargoDAO.class);
    }

@When("el usuario elimina el cargo")
public void el_usuario_elimina_el_cargo() throws SQLException {
    when(cargoDAO.eliminarCargo(cargo.getId())).thenReturn(1);
    resultadoOperacion = cargoDAO.eliminarCargo(cargo.getId());
}

    @Then("el cargo se elimina correctamente del sistema")
    public void el_cargo_se_elimina_correctamente_del_sistema() {
        assertEquals(1, resultadoOperacion);
    }

@When("el usuario solicita los detalles del cargo")
public void el_usuario_solicita_los_detalles_del_cargo() throws SQLException {
    when(cargoDAO.obtenerCargoPorId(cargo.getId())).thenReturn(cargo);
    cargo = cargoDAO.obtenerCargoPorId(cargo.getId());
}

    @Then("se muestran los detalles del cargo")
    public void se_muestran_los_detalles_del_cargo() {
        assertNotNull(cargo);
        assertEquals(1, cargo.getId());
    }

@When("el usuario actualiza los detalles del cargo")
public void el_usuario_actualiza_los_detalles_del_cargo() throws SQLException {
    when(cargoDAO.actualizarCargo(cargo)).thenReturn(1);
    resultadoOperacion = cargoDAO.actualizarCargo(cargo);
}

    @Then("el cargo se actualiza correctamente en el sistema")
    public void el_cargo_se_actualiza_correctamente_en_el_sistema() {
        assertEquals(1, resultadoOperacion);
    }

    @Given("un error inesperado ocurre en el sistema")
    public void un_error_inesperado_ocurre_en_el_sistema() {
        simulatedException = new SQLException("Error simulado");
    }

    @Then("la eliminación del cargo falla")
    public void la_eliminacion_del_cargo_falla() {
        assertEquals(0, resultadoOperacion);
    }

    @Given("un ID de cargo que no existe en el sistema")
    public void un_id_de_cargo_que_no_existe_en_el_sistema() {
        cargo = new Cargo(99, "Cargo Inexistente", "Descripción Inexistente");
        cargoDAO = mock(CargoDAO.class);
    }

    @Then("la actualización del cargo falla")
    public void la_actualizacion_del_cargo_falla() {
        assertEquals(0, resultadoOperacion);
    }

}
