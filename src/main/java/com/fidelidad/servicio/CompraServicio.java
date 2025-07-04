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

        //Obtener cuantas compras ha hecho el cliente hoy
        long comprasHoy = compraRepositorio
                .obtenerPorClienteYFecha(cliente.getId(), fecha)
                        .size();

        //Si ya tiene 2, está sería la tercera compra y se le dan 10puntos de bonus. Se agrega para 2+ compras.
        if(comprasHoy >= 2) {
            puntos += 10;
        }

        cliente.agregarPuntos(puntos);
        compraRepositorio.guardar(compra);
    }
}
