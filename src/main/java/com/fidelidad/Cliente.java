package com.fidelidad;

public class Cliente {
    private String nombre;
    private int puntos;
    private Nivel nivel;

    public Cliente(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
    }

    public void registrarCompra(double monto) {
        int puntosGanados = (int) monto;
        puntos += puntosGanados + nivel.bono(puntosGanados);
        actualizarNivel();
    }

    private void actualizarNivel() {
        if (puntos >= 10000) nivel = Nivel.PLATINO;
        else if (puntos >= 5000) nivel = Nivel.ORO;
        else if (puntos >= 1000) nivel = Nivel.PLATA;
        else nivel = Nivel.BRONCE;
    }

    // Getters para pruebas
    public int getPuntos() { return puntos; }
    public Nivel getNivel() { return nivel; }
}