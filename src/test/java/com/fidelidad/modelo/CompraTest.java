package com.fidelidad.modelo;

import com.fidelidad.Cliente;
import com.fidelidad.Nivel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CompraTest {

    @Test
    void puntosBaseCalculoCorrecto(){
        Cliente cliente = new Cliente("1","miguel","miguel@gmail.com");
        Compra compra = new Compra("c1", c, 850, LocalDate.now());

        int puntos = compra.calcularPuntos();
        assertEquals(8, puntos); // 850/100 = 8.5 -> se redondea hacia abajo
    }

    @Test
    void aplicarMultiplicadorSegunNivel(){
        Cliente cliente = new Cliente("1","marco","marco@gmail.com");
        c.setNivel(Nivel.ORO);
        Compra compra = new Compra("c1", cliente, 1000, LocalDate.now());

        int puntos = compra.calcularPuntos();
        assertEquals(15, puntos); //10x1.5 = 15
    }
}
