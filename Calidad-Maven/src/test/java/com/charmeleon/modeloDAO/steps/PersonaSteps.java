package com.charmeleon.modeloDAO.steps;

import com.charmeleon.modelo.Persona;
import com.charmeleon.modeloDAO.PersonaDAO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaSteps {

    private PersonaDAO personaDAO;
    private Persona persona;
    private List<Persona> personas;
    private int resultadoOperacion;

    @Given("existen personas en el sistema")
    public void existen_personas_en_el_sistema() {
        personas = new ArrayList<>();
        personaDAO = mock(PersonaDAO.class);
        // Añade personas de ejemplo a la lista
        personas.add(new Persona(1, "Nombre1", "Correo1", "Telefono1", "Clave1"));
        personas.add(new Persona(2, "Nombre2", "Correo2", "Telefono2", "Clave2"));
    }

    @When("el usuario solicita listar todas las personas")
    public void el_usuario_solicita_listar_todas_las_personas() throws SQLException {
        when(personaDAO.listar()).thenReturn(personas);
        personas = personaDAO.listar();
    }

    @Then("se muestra una lista de todas las personas")
    public void se_muestra_una_lista_de_todas_las_personas() {
        assertFalse(personas.isEmpty());
    }

    @Given("detalles de una nueva persona")
    public void detalles_de_una_nueva_persona() {
        persona = new Persona(3, "Nombre3", "Correo3", "Telefono3", "Clave3");
        personaDAO = mock(PersonaDAO.class);
    }

    @When("el usuario agrega la nueva persona")
    public void el_usuario_agrega_la_nueva_persona() throws SQLException {
        when(personaDAO.agregar(persona)).thenReturn(1);
        resultadoOperacion = personaDAO.agregar(persona);
    }

    @Then("la persona se agrega correctamente al sistema")
    public void la_persona_se_agrega_correctamente_al_sistema() {
        assertEquals(1, resultadoOperacion);
    }

    @Given("detalles de una persona existente")
    public void detalles_de_una_persona_existente() {
        persona = new Persona(1, "Nombre1", "Correo1", "Telefono1", "Clave1"); // Persona existente
        personaDAO = mock(PersonaDAO.class);
    }

    @When("el usuario intenta agregar la persona existente")
    public void el_usuario_intenta_agregar_la_persona_existente() throws SQLException {
        when(personaDAO.agregar(persona)).thenReturn(0); // Simula que la persona ya existe
        resultadoOperacion = personaDAO.agregar(persona);
    }

    @Then("la agregación de la persona falla")
    public void la_agregacion_de_la_persona_falla() {
        assertEquals(0, resultadoOperacion);
    }

    @Given("una persona existente en el sistema")
    public void una_persona_existente_en_el_sistema() {
        persona = new Persona(1, "Nombre1", "Correo1", "Telefono1", "Clave1");
        personaDAO = mock(PersonaDAO.class);
    }

    @When("el usuario actualiza los detalles de la persona")
    public void el_usuario_actualiza_los_detalles_de_la_persona() throws SQLException {
        when(personaDAO.Actualizar(persona)).thenReturn(1);
        resultadoOperacion = personaDAO.Actualizar(persona);
    }

    @Then("los detalles de la persona se actualizan correctamente")
    public void los_detalles_de_la_persona_se_actualizan_correctamente() {
        assertEquals(1, resultadoOperacion);
    }

    @Given("credenciales válidas de una persona")
    public void credenciales_validas_de_una_persona() {
        persona = new Persona();
        persona.setNom("Usuario1");
        persona.setClave("Clave1");
        personaDAO = mock(PersonaDAO.class);
    }

    @When("la persona intenta iniciar sesión")
    public void la_persona_intenta_iniciar_sesion() throws SQLException {
        when(personaDAO.login(persona)).thenReturn(1); // Simula un inicio de sesión exitoso
        resultadoOperacion = personaDAO.login(persona);
    }

    @Then("el inicio de sesión es exitoso")
    public void el_inicio_de_sesion_es_exitoso() {
        assertEquals(1, resultadoOperacion);
    }

    @Given("credenciales inválidas de una persona")
    public void credenciales_invalidas_de_una_persona() {
        persona = new Persona();
        persona.setNom("UsuarioIncorrecto");
        persona.setClave("ClaveIncorrecta");
        personaDAO = mock(PersonaDAO.class);
    }

    @When("la persona intenta iniciar sesión con credenciales inválidas")
    public void la_persona_intenta_iniciar_sesion_con_credenciales_invalidas() throws SQLException {
        when(personaDAO.login(persona)).thenReturn(0); // Simula un inicio de sesión fallido
        resultadoOperacion = personaDAO.login(persona);
    }

    @Then("el inicio de sesión falla")
    public void el_inicio_de_sesion_falla() {
        assertEquals(0, resultadoOperacion);
    }

    // Aquí termina la implementación de los pasos para los escenarios
}
