package com.fidelidad;

public enum Nivel {
    BRONCE(0, 499, 0),
    PLATA(500, 1499,0.2),
    ORO(1500,2999,0.5),
    PLATINO(3000, Integer.MAX_VALUE, 1.0);

    private int minPuntos;
    private int maxPuntos;
    private final double bono;

    Nivel(int minPuntos, int maxPuntos, double bono) {
        this.minPuntos = minPuntos;
        this.maxPuntos = maxPuntos;
        this.bono = bono;
    }

    public double getBono(){
        return bono;
    }

    public boolean estaDentroDelRango(int puntos){
        return puntos >= minPuntos && puntos <= maxPuntos;
    }

    public static Nivel calcularNivel(int puntosTotales){
        for (Nivel nivel : values()) {
            if (nivel.estaDentroDelRango(puntosTotales)) {
                return nivel;
            }
        }
        return BRONCE;
    }
}
