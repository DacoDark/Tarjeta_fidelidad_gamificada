package com.fidelidad.servicio;

import com.fidelidad.Compra;
import com.fidelidad.Cliente;
import com.fidelidad.repositorio.CompraRepositorio;

import java.time.LocalDate;

public class CompraServicio {

    private final CompraRepositorio compraRepositorio;

    public CompraServicio(CompraRepositorio compraRepositorio) {
        this.compraRepositorio = compraRepositorio;
    }

    public void registrarCompra(String idCompra,Cliente cliente, int monto, LocalDate fecha) {
        Compra compra = new Compra(idCompra, cliente, monto, fecha);
        int puntos = compra.calcularPuntos();

        cliente.agregarPuntos(puntos);
        compraRepositorio.guardar(compra);
    }
}
