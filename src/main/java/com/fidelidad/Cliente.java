package com.fidelidad;

public class Cliente {
    private final String id;
    private String nombre;
    private String correo;
    private int puntos;
    private Nivel nivel;
    private int streakDias;

    public Cliente(String id, String nombre, String correo) {
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("Correo no valido");
        }
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.puntos = 0;
        this.nivel = Nivel.BRONCE;
        this.streakDias = 0;
    }
    // Getters para pruebas

    public String getId() {return id;}
    public String getCorreo() {
        return correo;
    }
    public String getNombre() { return nombre;}
    public int getPuntos() { return puntos; }
    public Nivel getNivel() { return nivel; }

    public void setNombre(String nombre) { this.nombre = nombre;}
    public void setNivel(Nivel nivel) { this.nivel = nivel;}
    public void setCorreo(String correo) { this.correo = correo;}

    //Metodo para aplicar el cálculo de nivel después de cada compra
    public void agregarPuntos(int puntos) {
        this.puntos += puntos;
        actualizarNivel();
    }

    private void actualizarNivel() {
        this.nivel = Nivel.calcularNivel(this.puntos);
    }

    public void incrementarStreakDias() {
        streakDias++;
    }

    public void reiniciarStreakDias() {
        streakDias = 0;
    }

    public int getStreakDias() {
        return streakDias;
    }
}