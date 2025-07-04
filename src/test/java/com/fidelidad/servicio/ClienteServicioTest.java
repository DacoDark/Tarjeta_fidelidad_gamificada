package com.fidelidad.servicio;

import com.fidelidad.Cliente;
import com.fidelidad.repositorio.ClienteRepositorio;
import com.fidelidad.repositorio.ClienteRepositorioMemoria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServicioTest {

    @Test
    void puedeAgregarClienteValido() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        servicio.crearCliente("1", "David", "david@mail.com");

        Cliente cliente = repo.buscarPorId("1");
        assertNotNull(cliente);
        assertEquals("David", cliente.getNombre());
        assertEquals("david@mail.com",cliente.getCorreo());
    }

    @Test
    void noPermiteCorreoInvalido() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        assertThrows(IllegalArgumentException.class, () -> servicio.crearCliente("1", "David", "davidmail.com"));
    }

    @Test
    void puedeListarClientes() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        servicio.crearCliente("1", "David", "david@mail.com");
        servicio.crearCliente("2", "Fran", "Fran@mail.com");

        assertEquals(2,servicio.listarClientes().size());
    }

    @Test
    void puedeEditarNombreYCorreoCliente() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        servicio.crearCliente("1", "Deivid", "deivid@mail.com");

        servicio.editarCliente("1", "David","david@mail.com");

        Cliente cliente = repo.buscarPorId("1");
        assertEquals("David",cliente.getNombre());
        assertEquals("david@mail.com",cliente.getCorreo());
    }

    @Test
    void editarLanzaExcepcionSiClienteNoExiste() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        assertThrows(IllegalArgumentException.class, () ->
                servicio.editarCliente("999", "Otro", "otro@mail.com"));
    }

    @Test
    void noPermiteCorreoInvalidoEditado() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);
        servicio.crearCliente("1", "Luis", "luis@mail.com");

        assertThrows(IllegalArgumentException.class, () ->
                servicio.editarCliente("1", "Luis", "correoSinArroba"));
    }

    @Test
    void puedeEliminarCliente(){
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        servicio.crearCliente("1", "Marisol", "marisol@mail.com");
        assertNotNull(repo.buscarPorId("1"));

        servicio.eliminarCliente("1");
        assertNull(repo.buscarPorId("1"));
    }

    @Test
    void puedeMostrarResumenDePuntosYNivel() {
        ClienteRepositorio repo = new ClienteRepositorioMemoria();
        ClienteServicio servicio = new ClienteServicio(repo);

        Cliente cliente = new Cliente("1", "Lucía", "lucia@mail.com");
        cliente.agregarPuntos(1600); // Esto la sube a nivel ORO
        repo.guardar(cliente);

        String resumen = servicio.obtenerResumen("1");

        assertTrue(resumen.contains("Lucía"));
        assertTrue(resumen.contains("ORO"));
        assertTrue(resumen.contains("1600"));
    }

}
