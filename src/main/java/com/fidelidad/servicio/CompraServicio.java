package com.fidelidad.servicio;

import com.fidelidad.Compra;
import com.fidelidad.Cliente;
import com.fidelidad.repositorio.ClienteRepositorio;
import com.fidelidad.repositorio.CompraRepositorio;

import java.time.LocalDate;
import java.util.List;

public class CompraServicio {

    private final CompraRepositorio compraRepositorio;
    private final ClienteRepositorio clienteRepositorio;

    public CompraServicio(CompraRepositorio compraRepositorio, ClienteRepositorio clienteRepositorio) {
        this.compraRepositorio = compraRepositorio;
        this.clienteRepositorio = clienteRepositorio;
    }

    public void registrarCompra(String idCompra,String idCliente, int monto) {
        Cliente cliente = clienteRepositorio.buscarPorId(idCliente);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no encontrado");
        }

        LocalDate hoy = LocalDate.now();

        //Obtenemos compras del cliente en el día
        List<Compra> comprasHoy = compraRepositorio.obtenerPorClienteYFecha(idCliente, hoy);

        //Calculamos los puntos base
        int puntosBase = monto/100;

        //aplicamos bono por nivel
        double bono = cliente.getNivel().getBono();
        int puntosGanados = (int) Math.floor(puntosBase * (1 + bono));

        //bonus por streak 3 o más compras hoy +10pts
        if (comprasHoy.size() >= 2) {
            puntosGanados += 10;
            cliente.incrementarStreakDias();
        } else {
            cliente.reiniciarStreakDias();
        }

        //Agregar puntos al cliente
        cliente.agregarPuntos(puntosGanados);

        //Registramos la compra
        Compra compra = new Compra(idCompra, cliente, monto, hoy);
        compraRepositorio.guardar(compra);
    }

    public List<Compra> obtenerComprasPorCliente(String idCliente) {
        return compraRepositorio.obtenerPorCliente(idCliente);
    }
}
