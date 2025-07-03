package com.fidelidad;

public class Cliente {
    private final String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int strakDias;

    public Cliente(String id, String nombre, String correo) {
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("Correo no valido");
        }
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.strakDias = 0;
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

    public String getCorreo() {
        return correo;
    }
    public String getNombre() { return nombre;}
    public int getPuntos() { return puntos; }
    public Nivel getNivel() { return nivel; }
}