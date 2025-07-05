package com.fidelidad.servicio;

import com.fidelidad.Cliente;
import com.fidelidad.Compra;
import com.fidelidad.repositorio.ClienteRepositorio;
import com.fidelidad.repositorio.ClienteRepositorioMemoria;
import com.fidelidad.repositorio.CompraRepositorio;
import com.fidelidad.repositorio.CompraRepositorioMemoria;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CompraServicioTest {

    @Test
    void clienteRecibeBonusTerceraCompraDia() {
        // Repositorios y servicio
        ClienteRepositorio clienteRepositorio = new ClienteRepositorioMemoria();
        CompraRepositorio compraRepositorio = new CompraRepositorioMemoria();
        CompraServicio compraServicio = new CompraServicio(compraRepositorio, clienteRepositorio);

        // Cliente de prueba
        Cliente cliente = new Cliente("1", "David", "david@gmail.com");
        clienteRepositorio.guardar(cliente);

        // Simula 2 compras anteriores hoy
        compraServicio.registrarCompra("c1", cliente.getId(), 500); // 5 pts
        compraServicio.registrarCompra("c2", cliente.getId(), 500); // 5 pts

        // La 3ra compra activa el bonus (+10 pts)
        compraServicio.registrarCompra("c3", cliente.getId(), 1000); // 10 pts base + 10 bonus

        // Total esperado:
        // c1: 5 pts
        // c2: 5 pts
        // c3: 10 pts + 10 bonus
        assertEquals(30, cliente.getPuntos());
        assertEquals(1, cliente.getStreakDias());
    }

    @Test
    void noRecibeBonusEnPrimeraYSegundaCompraDia(){
        // Repositorios en memoria
        ClienteRepositorio clienteRepositorio = new ClienteRepositorioMemoria();
        CompraRepositorio compraRepositorio = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(compraRepositorio, clienteRepositorio);

        Cliente cliente = new Cliente("2", "Juan", "Juan@mail.com");
        clienteRepositorio.guardar(cliente);

        //Se simula una primera compra para obtención de puntos
        servicio.registrarCompra("c1",cliente.getId(),1000);

        assertEquals(10, cliente.getPuntos(), "Puntos Base en primera compra");

        //Se simula una segunda compra para obtención de puntos
        servicio.registrarCompra("c2",cliente.getId(),1000);
        assertEquals(20, cliente.getPuntos(), "Puntos Base en segunda compra");
    }

    @Test
    void recibeBonusDespuesDeTresComprasDelMismoDia() {
        // Repositorios en memoria
        ClienteRepositorio clienteRepositorio = new ClienteRepositorioMemoria();
        CompraRepositorio compraRepositorio = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(compraRepositorio, clienteRepositorio);

        // Se crea el cliente al cual se le registran las compras
        Cliente cliente = new Cliente("3", "Pedro", "pedro@mail.com");
        clienteRepositorio.guardar(cliente);

        // Realiza 10 compras el mismo día de $1000
        for (int i = 1; i <= 10; i++) {
            servicio.registrarCompra("c"+i,cliente.getId(),1000);
        }

        // Cada compra da 10 puntos base
        // En la 3 a 10 se otorgan +10 pts bonus (8 veces)
        int puntosBase = 10 * 10; //100
        int bonus = 8 * 10; //80
        int puntosEsperados = puntosBase + bonus;
        assertEquals(puntosEsperados, cliente.getPuntos());
    }

    @Test
    void bonusSeReiniciaAlCambiarDeDia() {
        // Repositorios y servicio
        ClienteRepositorio clienteRepositorio = new ClienteRepositorioMemoria();
        CompraRepositorio compraRepositorio = new CompraRepositorioMemoria();
        CompraServicio compraServicio = new CompraServicio(compraRepositorio, clienteRepositorio);

        // Crear cliente
        Cliente cliente = new Cliente("1", "David", "david@gmail.com");
        clienteRepositorio.guardar(cliente);

        // Simulamos 2 compras AYER
        LocalDate ayer = LocalDate.now().minusDays(1);
        compraRepositorio.guardar(new Compra("c1", cliente, 500, ayer));
        compraRepositorio.guardar(new Compra("c2", cliente, 500, ayer));

        // Hacemos una compra HOY
        compraServicio.registrarCompra("c3", "1", 1000); // Primera compra del día

        // No debería aplicar bonus, ya que es la única compra de hoy
        int puntosEsperados = 10; // 1000/100 = 10 (nivel BRONCE, sin bonus)

        assertEquals(puntosEsperados, cliente.getPuntos());
        assertEquals(0, cliente.getStreakDias()); // no se cumple la condición
    }


    @Test
    void puedeObtenerComprasPorCliente() {
        ClienteRepositorio clienteRepositorio = new ClienteRepositorioMemoria();
        CompraRepositorio compraRepositorio = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(compraRepositorio, clienteRepositorio);

        Cliente cliente = new Cliente("1", "David", "david@mail.com");
        clienteRepositorio.guardar(cliente);

        servicio.registrarCompra("c1",cliente.getId(),1000);
        servicio.registrarCompra("c2",cliente.getId(),2000);
        servicio.registrarCompra("c3",cliente.getId(),1500);

        List<Compra> compras = servicio.obtenerComprasPorCliente("1");
        assertEquals(3, compras.size());
    }

    @Test
    void lanzaExcepcionSiClienteNoExiste() {
        ClienteRepositorio clienteRepo = new ClienteRepositorioMemoria();
        CompraRepositorio compraRepo = new CompraRepositorioMemoria();
        CompraServicio servicio = new CompraServicio(compraRepo, clienteRepo);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> servicio.registrarCompra("c1", "id-invalido", 1000));

        assertEquals("Cliente no encontrado", ex.getMessage());
    }


}
