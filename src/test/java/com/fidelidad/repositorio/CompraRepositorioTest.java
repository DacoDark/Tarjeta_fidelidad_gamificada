package com.fidelidad.repositorio;

import com.fidelidad.Cliente;
import com.fidelidad.Compra;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompraRepositorioTest {

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
        Cliente cliente2 = new Cliente("2", "Ana", "ana@email.com");

        LocalDate compraHoy = LocalDate.now();
        LocalDate compraAyer = compraHoy.minusDays(1);

        // 1. Coincide cliente y fecha
        Compra compraValida = new Compra("c1", cliente, 500, compraHoy);
        repo.guardar(compraValida);

        // 2. Mismo cliente, otra fecha
        Compra compraFechaDistinta = new Compra("c2", cliente, 1000, compraAyer);
        repo.guardar(compraFechaDistinta);

        // 3.  cliente 2, misma fecha
        Compra compraOtroCliente = new Compra("c3", cliente2, 1000, compraHoy);
        repo.guardar(compraOtroCliente);

        //Prueba
        List<Compra> resultado = repo.obtenerPorClienteYFecha("1", compraHoy);
        assertEquals(1, resultado.size());
        assertEquals("c1", resultado.getFirst().getIdCompra());
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
