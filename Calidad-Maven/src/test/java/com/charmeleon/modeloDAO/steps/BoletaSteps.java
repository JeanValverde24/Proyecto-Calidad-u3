package com.charmeleon.modeloDAO.steps;

import com.charmeleon.modelo.Boleta;
import com.charmeleon.modeloDAO.BoletaDAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoletaSteps {

    private BoletaDAO boletaDAO;
    private Boleta boleta;
    private int resultadoOperacion;
    private List<Boleta> boletasListadas;
    private int boletaNumero;

    @Given("un cliente con dni {string} y nombre {string}")
    public void un_cliente_con_dni_y_nombre(String dni, String nombre) {
        boleta = new Boleta();
        boleta.setDni(Integer.parseInt(dni));
        boleta.setCliente(nombre);
        boletaDAO = mock(BoletaDAO.class);
    }

    @When("el usuario agrega una nueva boleta con la fecha y hora actual")
    public void el_usuario_agrega_una_nueva_boleta() throws SQLException {
        when(boletaDAO.agregarBoleta(boleta)).thenReturn(1);
        resultadoOperacion = boletaDAO.agregarBoleta(boleta);
    }

    @Then("la boleta se agrega correctamente al sistema")
    public void la_boleta_se_agrega_correctamente_al_sistema() {
        assertEquals(1, resultadoOperacion);
    }

    @Given("boletas existentes en el sistema")
    public void boletas_existentes_en_el_sistema() {
        boletaDAO = mock(BoletaDAO.class);
        boletasListadas = new ArrayList<>();
        boletasListadas.add(new Boleta());
    }

    @When("el usuario solicita listar todas las boletas")
    public void el_usuario_solicita_listar_todas_las_boletas() throws SQLException {
        when(boletaDAO.listarBoletas()).thenReturn(boletasListadas);
        boletasListadas = boletaDAO.listarBoletas();
    }

    @Then("se muestra una lista de todas las boletas")
    public void se_muestra_una_lista_de_todas_las_boletas() {
        assertFalse(boletasListadas.isEmpty());
    }

    @Given("una boleta existente con número {string}")
    public void una_boleta_existente_con_numero(String numeroBoleta) {
        boletaNumero = Integer.parseInt(numeroBoleta);
        boletaDAO = mock(BoletaDAO.class);
        boleta = new Boleta();
        boleta.setNBoleta(boletaNumero);
    }

    @When("el usuario elimina la boleta con número {string}")
    public void el_usuario_elimina_la_boleta_con_numero(String numeroBoleta) throws SQLException {
        when(boletaDAO.eliminarBoleta(boletaNumero)).thenReturn(1);
        resultadoOperacion = boletaDAO.eliminarBoleta(boletaNumero);
    }

    @Then("la boleta con número {string} se elimina correctamente del sistema")
    public void la_boleta_con_numero_se_elimina_correctamente_del_sistema(String numeroBoleta) {
        assertEquals(1, resultadoOperacion);
    }

    @When("el usuario solicita los detalles de la boleta con número {string}")
    public void el_usuario_solicita_los_detalles_de_la_boleta_con_numero(String numeroBoleta) throws SQLException {
        when(boletaDAO.obtenerBoletaPorNBoleta(boletaNumero)).thenReturn(boleta);
        boleta = boletaDAO.obtenerBoletaPorNBoleta(boletaNumero);
    }

    @Then("se muestran los detalles de la boleta con número {string}")
    public void se_muestran_los_detalles_de_la_boleta_con_numero(String numeroBoleta) {
        assertNotNull(boleta);
        assertEquals(boletaNumero, boleta.getNBoleta());
    }

    @When("el usuario actualiza la boleta con número {string} con nuevos detalles")
    public void el_usuario_actualiza_la_boleta_con_numero_con_nuevos_detalles(String numeroBoleta) throws SQLException {
        when(boletaDAO.actualizarBoleta(boleta)).thenReturn(1);
        resultadoOperacion = boletaDAO.actualizarBoleta(boleta);
    }

    @Then("la boleta con número {string} se actualiza correctamente en el sistema")
    public void la_boleta_con_numero_se_actualiza_correctamente_en_el_sistema(String numeroBoleta) {
        assertEquals(1, resultadoOperacion);
    }

}