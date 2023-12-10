package com.charmeleon.modeloDAO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.charmeleon.modelo.Conexion;
import com.charmeleon.modelo.Persona;

import java.sql.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PersonaDAOTest {

    @Mock
    private Conexion conectar;
    @Mock
    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private PreparedStatement psSelect;
    @Mock
    private PreparedStatement psInsert;
    @Mock
    private ResultSet rs;
    @Mock
    private ResultSet rsSelect;

    private PersonaDAO personaDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(conectar.getConnection()).thenReturn(con);

        // Configura los PreparedStatement y ResultSet para los métodos diferentes
        psSelect = mock(PreparedStatement.class);
        psInsert = mock(PreparedStatement.class);
        rsSelect = mock(ResultSet.class);

        // Configura los PreparedStatement y ResultSet para el método listar()
        when(con.prepareStatement("SELECT Id, Nombres, Correo, Telefono, '***' AS Clave FROM tbempleado"))
                .thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        // Configura el comportamiento del ResultSet para el método listar()
        when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(rs.getInt("Id")).thenReturn(1).thenReturn(2);
        when(rs.getString("Nombres")).thenReturn("Nombre 1").thenReturn("Nombre 2");
        when(rs.getString("Correo")).thenReturn("correo1@example.com").thenReturn("correo2@example.com");
        when(rs.getString("Telefono")).thenReturn("123456").thenReturn("654321");
        when(rs.getString("Clave")).thenReturn("***").thenReturn("***");

        when(con.prepareStatement(anyString())).thenAnswer(invocation -> {
            String sql = invocation.getArgument(0);
            if (sql.equals("SELECT COUNT(*) FROM tbempleado WHERE Id = ?")) {
                return psSelect;
            } else if (sql
                    .equals("INSERT INTO tbempleado (Id, Nombres, Correo, Telefono, Clave) VALUES (?, ?, ?, ?, ?)")) {
                return psInsert;
            } else {
                return ps;
            }
        });

        when(psSelect.executeQuery()).thenReturn(rsSelect);
        when(rsSelect.next()).thenReturn(false); // Simula que no hay coincidencias existentes
        when(psInsert.executeUpdate()).thenReturn(1);

        personaDAO = new PersonaDAO(conectar);
    }

    @Test
    public void testListar() throws SQLException {
    // Configura las respuestas simuladas del ResultSet
    when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    when(rs.getInt("Id")).thenReturn(1).thenReturn(2);
    when(rs.getString("Nombres")).thenReturn("Nombre 1").thenReturn("Nombre 2");
    when(rs.getString("Correo")).thenReturn("correo1@example.com").thenReturn("correo2@example.com");
    when(rs.getString("Telefono")).thenReturn("123456").thenReturn("654321");
    when(rs.getString("Clave")).thenReturn("***").thenReturn("***");

    List<Persona> resultado = personaDAO.listar();

    assertNotNull(resultado);
    assertEquals(2, resultado.size()); // Verifica la cantidad de Personas devueltas

    // Verifica los detalles de las Personas devueltas
    Persona primeraPersona = resultado.get(0);
    Persona segundaPersona = resultado.get(1);

    assertEquals(1, primeraPersona.getId());
    assertEquals("Nombre 1", primeraPersona.getNom());
    // Repite las verificaciones para los demás campos

    assertEquals(2, segundaPersona.getId());
    // Repite las verificaciones para los demás campos

    verify(ps, times(1)).executeQuery();
    }

@Test
    public void testListarConExcepcion() throws SQLException {
    when(con.prepareStatement(any(String.class))).thenThrow(new SQLException());

    List<Persona> resultado = personaDAO.listar();
    assertTrue(resultado.isEmpty()); // Asumiendo que en caso de error, se retorna una lista vacía
    }

    @Test
    public void testAgregar() throws SQLException {
        Persona persona = new Persona();
        persona.setId(1); // Configura los detalles de la Persona
        persona.setNom("Nombre");
        persona.setCorreo("correo@example.com");
        persona.setTelefono("123456789");
        persona.setClave("clave");

        // Configura el comportamiento simulado para verificar si la persona ya existe
        when(con.prepareStatement(anyString())).thenAnswer(invocation -> {
            String sql = invocation.getArgument(0);
            if (sql.startsWith("SELECT COUNT(*) FROM tbempleado WHERE Id = ?")) {
                return psSelect;
            } else if (sql.startsWith(
                    "INSERT INTO tbempleado (Id, Nombres, Correo, Telefono, Clave) VALUES (?, ?, ?, ?, ?)")) {
                return psInsert;
            } else {
                return ps; // Podrías no necesitar esta línea si sólo usas psSelect y psInsert
            }
        });

        when(psSelect.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // Simula que la persona no existe

        when(psInsert.executeUpdate()).thenReturn(1); // Simula una inserción exitosa

        int resultado = personaDAO.agregar(persona);

        assertEquals(1, resultado); // Verifica que el resultado es el esperado

        // Verifica que se hayan llamado los métodos correctos en los objetos
        // PreparedStatement
        verify(psSelect, times(1)).setInt(1, persona.getId());
        verify(psInsert, times(1)).setInt(1, persona.getId());
        verify(psInsert, times(1)).setString(2, persona.getNom());
        verify(psInsert, times(1)).setString(3, persona.getCorreo());
        verify(psInsert, times(1)).setString(4, persona.getTelefono());
        verify(psInsert, times(1)).setString(5, persona.getClave());
        verify(psInsert, times(1)).executeUpdate();
    }

    @Test
    public void testAgregarConExcepcion() throws SQLException {
        Persona persona = new Persona();
        // Configura los detalles de la Persona

        when(con.prepareStatement(anyString())).thenThrow(new SQLException());

        int resultado = personaDAO.agregar(persona);

        assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
        verify(con, times(1)).prepareStatement(anyString());
    }

    @Test
    public void testAgregarPersonaExistente() throws SQLException {
        Persona persona = new Persona();
        persona.setId(1); // Asumiendo que este es el ID de la persona que estás probando
        // Configura los detalles restantes de la Persona...

        // Configura el comportamiento simulado para verificar si la persona ya existe
        when(con.prepareStatement(anyString())).thenReturn(psSelect);
        when(psSelect.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true); // Simula que la persona ya existe
        when(rs.getInt(1)).thenReturn(1); // Simula que hay 1 entrada existente para ese ID

        int resultado = personaDAO.agregar(persona);

        assertEquals(0, resultado); // Verifica que el resultado es 0, persona existente
        verify(psSelect, times(1)).executeQuery();
        verify(psSelect, times(1)).setInt(1, persona.getId()); // Verifica que se setea el ID en el PreparedStatement
    }

    @Test
    public void testActualizar() throws SQLException {
        Persona persona = new Persona();
        persona.setId(1); // Configura los detalles de la Persona
        persona.setNom("Nombre Actualizado");
        persona.setCorreo("correo.actualizado@example.com");
        persona.setTelefono("987654321");
        persona.setClave("claveActualizada");

        when(ps.executeUpdate()).thenReturn(1);

        int resultado = personaDAO.Actualizar(persona);

        assertEquals(1, resultado);

        // Verifica que los métodos setters del preparedStatement se llamen con los
        // valores correctos
        verify(ps, times(1)).setString(1, persona.getNom());
        verify(ps, times(1)).setString(2, persona.getCorreo());
        verify(ps, times(1)).setString(3, persona.getTelefono());
        verify(ps, times(1)).setString(4, persona.getClave());
        verify(ps, times(1)).setInt(5, persona.getId());
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testActualizarConExcepcion() throws SQLException {
        Persona persona = new Persona();
        // Configura los detalles de la Persona

        when(ps.executeUpdate()).thenThrow(new SQLException());

        int resultado = personaDAO.Actualizar(persona);

        assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testDelete() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1); // Simula una eliminación exitosa
    
        int resultado = personaDAO.Delete(1);
    
        assertEquals(1, resultado);
    
        // Verifica que los métodos setters del preparedStatement se llamen con los valores correctos
        verify(ps, times(1)).setInt(1, 1);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testDeleteConExcepcion() throws SQLException {
        when(ps.executeUpdate()).thenThrow(new SQLException());
    
        int resultado = personaDAO.Delete(1);
    
        assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
        verify(ps, times(1)).setInt(1, 1);
        verify(ps, times(1)).executeUpdate();
    }

    @Test
    public void testLogin() throws SQLException {
        Persona persona = new Persona();
        persona.setNom("Usuario");
        persona.setClave("Contraseña");

        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true); // Simula que encuentra una coincidencia
        when(rs.getInt(1)).thenReturn(1); // Simula una respuesta exitosa

        int resultado = personaDAO.login(persona);

        assertEquals(1, resultado); // Verifica el resultado del login

        // Verifica que los métodos setters del preparedStatement se llamen con los
        // valores correctos
        verify(ps, times(1)).setString(1, persona.getNom());
        verify(ps, times(1)).setString(2, persona.getClave());
        verify(ps, times(1)).executeQuery();
    }

    @Test
    public void testLoginConExcepcion() throws SQLException {
        Persona persona = new Persona();
        // Configura los detalles de la Persona

        when(ps.executeQuery()).thenThrow(new SQLException());

        int resultado = personaDAO.login(persona);

        assertEquals(0, resultado); // Asumiendo que en caso de error se retorna 0
        verify(ps, times(1)).executeQuery();
    }

    @Test
    public void testLoginFallido() throws SQLException {
        Persona persona = new Persona();
        // Configura los detalles de la Persona

        when(rs.next()).thenReturn(false); // Simula que no encuentra una coincidencia

        int resultado = personaDAO.login(persona);

        assertEquals(0, resultado); // Asumiendo que si no se encuentra, se retorna 0
        verify(ps, times(1)).executeQuery();
    }

}
