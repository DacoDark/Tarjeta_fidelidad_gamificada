package com.fidelidad.servicio;

import com.fidelidad.Cliente;
import com.fidelidad.Compra;
import com.fidelidad.repositorio.CompraRepositorio;
import com.fidelidad.repositorio.CompraRepositorioMemoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompraServicioTest {

    @Test
    void clienteRecibeBonusTerceraCompraDia(){
        Cliente cliente = new Cliente("1","David","david@gmail.com");
        CompraRepositorio repo = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(repo);

        //Se simulan 2 compras hoy para david
        servicio.registrarCompra("c1", cliente, 1000, LocalDate.now());
        servicio.registrarCompra("c2", cliente, 1000, LocalDate.now());

        int puntosAntes = cliente.getPuntos();

        //Tercera compra - solo se considera el caso de 1000 (recordar globalizar y asignar puntos base por cada $100->1pts)
        servicio.registrarCompra("c3", cliente, 1000, LocalDate.now());
        int puntosBase = 1000/100; //Aquí globalizar.
        int puntosEsperados = puntosAntes + puntosBase + 10; //+10bonus tercera compra
        assertEquals(puntosEsperados, cliente.getPuntos());
    }
}
