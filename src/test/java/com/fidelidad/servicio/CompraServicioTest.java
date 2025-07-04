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

    @Test
    void noRecibeBonusEnPrimeraYSegundaCompraDia(){
        Cliente cliente = new Cliente("2", "Juan", "Juan@mail.com");
        CompraRepositorio repo = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(repo);

        servicio.registrarCompra("c1", cliente, 1000, LocalDate.now());
        assertEquals(10, cliente.getPuntos(), "Puntos Base en primera compra");

        servicio.registrarCompra("c1", cliente, 1000, LocalDate.now());
        assertEquals(20, cliente.getPuntos(), "Puntos Base en segunda compra");
    }

    @Test
    void recibeBonusCadaTresComprasDelMismoDia() {
        Cliente cliente = new Cliente("3", "Pedro", "pedro@mail.com");
        CompraRepositorio repo = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(repo);

        // Realiza 10 compras el mismo día de $1000
        for (int i = 1; i <= 10; i++) {
            servicio.registrarCompra("c" + i, cliente, 1000, LocalDate.now());
        }

        // Cada compra da 10 puntos base
        // En la 3 a 10 se otorgan +10 pts bonus (8 veces)
        int puntosBase = 10 * 10; //100
        int bonus = 8 * 10; //80
        int puntosEsperados = puntosBase + bonus;
        assertEquals(puntosEsperados, cliente.getPuntos());
    }
}
