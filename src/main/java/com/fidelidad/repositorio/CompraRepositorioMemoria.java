package com.fidelidad.repositorio;

import com.fidelidad.Compra;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraRepositorioMemoria implements CompraRepositorio{

    private final List<Compra> compras = new ArrayList<>();

    @Override
    public void guardar(Compra compra) {
        compras.add(compra);
    }

    @Override
    public List<Compra> obtenerPorCliente(String idCliente) {
        return compras.stream()
                .filter(c -> c.getCliente().getId().equals(idCliente))
                .toList();
    }
    @Override
    public List<Compra> obtenerPorClienteYFecha(String idCliente, LocalDate fecha) {
        return compras.stream()
                .filter(c-> c.getCliente().getId().equals(idCliente)
                    && c.getFecha().equals(fecha))
                .toList();
    }

    @Override
    public List<Compra> obtenerTodas() {
        return new ArrayList<>(compras);
    }
}
