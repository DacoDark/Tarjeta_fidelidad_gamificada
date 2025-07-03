package com.fidelidad.repositorio;

import com.fidelidad.Cliente;
import com.fidelidad.Nivel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteRepositorioTest {

    private ClienteRepositorioTest repo;

    @BeforeEach
    void setUp() {
        repo = new ClienteRepositorioTest();
    }

    @Test
    void agregarYobtenerCliente() {
        Cliente c = new Cliente("1","David","david@example.com");
        repo.guardar(c);

        Optional<Cliente> resultado = repo.buscarPorId("1");
        assertTrue(resultado.isPresent());
        assertEquals("Ana", resultado.get().getNombre());
    }

    @Test
    void listarTodosLosClientes(){
        repo.guardar(new Cliente("1","Ana","ana@gmail.com"));
        repo.guardar(new Cliente("2","Luis","luis@gmail.com"));

        List<Cliente> lista = repo.listar();
        assertEquals(2, lista.size());
    }

    @Test
    void poderactualizarCliente() {
        Cliente c = new Cliente("1","Ana","ana@gmail.com");
        repo.guardar(c);
        c.setNombre("Carmen");
        repo.guardar(c);

        assertEquals("Carmen", repo.buscarPorId("1").get().getNombre());
    }

    @Test
    void poderEliminarCliente() {
        Cliente c = new Cliente("1","Ana","ana@gmail.com");
        repo.guardar(c);
        repo.eleminar("1");

        assertFalse(repo.buscarPorId("1").isPresent());
    }
}
