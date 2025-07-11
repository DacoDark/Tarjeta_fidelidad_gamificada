package com.fidelidad;

import java.time.LocalDate;

public class Compra {

    private final String idCompra;
    private final Cliente cliente;
    private int monto;
    private final LocalDate fecha;

    public Compra(String idCompra, Cliente cliente, int monto, LocalDate fecha) {
        this.idCompra = idCompra;
        this.cliente = cliente;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int calcularPuntos(){
        int puntosBase = monto/100;
        double bonificacion = cliente.getNivel().getBono();
        return (int) Math.floor(puntosBase * (1 + bonificacion));
    }

    public Cliente getCliente() { return cliente; }
    public LocalDate getFecha() { return fecha; }
    public String getIdCompra() { return idCompra; }
    public int getMonto() { return monto; }
}
