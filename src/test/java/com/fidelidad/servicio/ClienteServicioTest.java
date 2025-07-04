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
}
