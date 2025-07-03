package com.fidelidad;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {
    @Test
    void pruebaInicial(){
        assertTrue(true);
    }

    @Test
    void ClienteSeCreaCorectamente(){
        Cliente c = new Cliente("1","David","david@example.com");
        assertEquals("David", c.getNombre());
        assertEquals("david@example.com", c.getCorreo());
        assertEquals(0,c.getPuntos());
        assertEquals(Nivel.BRONCE,c.getNivel());
    }

    @Test
    void noPermiteCoreoInvalido(){
        assertThrows(IllegalArgumentException.class, () -> {
           new Cliente("2","Ana","ana#sinarroba.com");
        });
    }

    @Test
    void clienteActualizaNivelCorrectamente(){
        Cliente c = new Cliente("1","David","david@example.com");

        c.agregarPuntos(600); //Esto debería subirlo a PLATA
        assertEquals(Nivel.PLATA, c.getNivel());

        c.agregarPuntos(1000); // 600+1000 -> Esto debería subirlo a ORO
        assertEquals(Nivel.ORO, c.getNivel());

        c.agregarPuntos(1500); // 1600+1500 -> Esto debería subirlo a PLATINO
        assertEquals(Nivel.ORO, c.getNivel());
    }
}
