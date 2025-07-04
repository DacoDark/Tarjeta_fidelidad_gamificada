package com.fidelidad.repositorio;

import com.fidelidad.Cliente;
import com.fidelidad.Compra;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompraRepositorioTest {

    @Test
    void guardarYobtenerCompraPorCliente(){
        CompraRepositorio repo = new CompraRepositorioMemoria();
        Cliente cliente = new Cliente("1","David","david@example.com");

        Compra compra1 = new Compra("c1", cliente, 500, LocalDate.now());
        Compra compra2 = new Compra("c2", cliente, 1000, LocalDate.now());

        repo.guardar(compra1);
        repo.guardar(compra2);

        List<Compra> compras = repo.obtenerPorCliente("1");

        assertEquals(2, compras.size());
        assertTrue(compras.contains(compra1));
        assertTrue(compras.contains(compra2));
    }

    @Test
    void obtenerPorClienteYFechaFiltraCorrectamente(){
        CompraRepositorio repo = new CompraRepositorioMemoria();
        Cliente cliente = new Cliente("1","David","david@example.com");

        Compra compraHoy = new Compra("c1", cliente, 500, LocalDate.now());
        Compra compraAyer = new Compra("c2", cliente, 1000, LocalDate.now().minusDays(1));

        repo.guardar(compraHoy);
        repo.guardar(compraAyer);

        List<Compra> comprasHoy = repo.obtenerPorClienteYFecha("1", LocalDate.now());
        assertEquals(1, comprasHoy.size());
        assertTrue(comprasHoy.contains(compraHoy));
    }

    @Test
    void obtenerTodasDevuelveTodasLasCompras() {
        CompraRepositorio repo = new CompraRepositorioMemoria();
        Cliente cliente1 = new Cliente("1", "Ana", "ana@mail.com");
        Cliente cliente2 = new Cliente("2", "Luis", "luis@mail.com");

        Compra c1 = new Compra("c1", cliente1, 300, LocalDate.now());
        Compra c2 = new Compra("c2", cliente2, 700, LocalDate.now());

        repo.guardar(c1);
        repo.guardar(c2);

        List<Compra> todas = repo.obtenerTodas();
        assertEquals(2, todas.size());
        assertTrue(todas.contains(c1));
        assertTrue(todas.contains(c2));
    }
}
