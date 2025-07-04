package com.fidelidad.repositorio;

import com.fidelidad.Cliente;
import com.fidelidad.Compra;

import java.time.LocalDate;
import java.util.List;

public interface CompraRepositorio {
    void guardar(Compra compra);
    List<Compra> obtenerPorCliente(String idCliente);
    List<Compra> obtenerPorClienteYFecha(String idCliente, LocalDate fecha);
    List<Compra> obtenerTodas();
}
