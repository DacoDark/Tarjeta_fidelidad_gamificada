package com.fidelidad.repositorio;

import com.fidelidad.Cliente;
import com.fidelidad.repositorio.ClienteRepositorio;
import com.fidelidad.Nivel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteRepositorioTest {

    private ClienteRepositorio repo;

    @BeforeEach
    void setUp() {
        repo = new ClienteRepositorioMemoria();
    }

    @Test
    void agregarYobtenerCliente() {
        Cliente c = new Cliente("1","David","david@example.com");
        repo.guardar(c);

        Cliente resultado = repo.buscarPorId("1");
        assertNotNull(resultado);
        assertEquals("David", resultado.getNombre());
    }

    @Test
    void listarTodosLosClientes(){
        repo.guardar(new Cliente("1","Ana","ana@gmail.com"));
        repo.guardar(new Cliente("2","Luis","luis@gmail.com"));

        List<Cliente> lista = repo.obtenerTodos();
        assertEquals(2, lista.size());
    }

    @Test
    void poderActualizarCliente() {
        Cliente c = new Cliente("1","Ana","ana@gmail.com");
        repo.guardar(c);

        c.setNombre("Carmen");
        repo.guardar(c); //Sobrescribe el nombre

        Cliente actualizado = repo.buscarPorId("1");
        assertEquals("Carmen", actualizado.getNombre());
    }

    @Test
    void poderEliminarCliente() {
        Cliente c = new Cliente("1","Ana","ana@gmail.com");
        repo.guardar(c);
        repo.eliminar("1");

        Cliente eliminado = repo.buscarPorId("1");
        assertNull(eliminado);
    }
}
