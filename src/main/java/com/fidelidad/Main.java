package com.fidelidad;

import com.fidelidad.repositorio.CompraRepositorio;
import com.fidelidad.repositorio.CompraRepositorioMemoria;
import com.fidelidad.servicio.ClienteServicio;
import com.fidelidad.repositorio.ClienteRepositorio;
import com.fidelidad.repositorio.ClienteRepositorioMemoria;
import com.fidelidad.servicio.CompraServicio;

import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final ClienteRepositorio clienteRepo = new ClienteRepositorioMemoria();
    private static final ClienteServicio clienteServicio = new ClienteServicio(clienteRepo);
    private static final CompraRepositorio compraRepo = new CompraRepositorioMemoria();
    private static final CompraServicio compraServicio = new CompraServicio(compraRepo, clienteRepo);

    private static final Scanner scanner = new Scanner(System.in);

    private static final String ERROR_PREFIX = "⚠️ Error: ";

    public static void main(String[] args) {
        boolean menuprincipal = false;

        while(!menuprincipal){
            logger.info("\n Bienvenidos al Sistema de Fidelidad ");
            logger.info("1. Gestión de Clientes");
            logger.info("2. Gestión de Compras");
            logger.info("3. Mostrar Puntos y Nivel de un Cliente");
            logger.info("4. Salir");
            logger.info("Seleccionar una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    menuGestionClientes();
                    break;
                case "2":
                    menuGestionCompras();
                    break;
                case "3":
                     {
                         logger.info("Ingrese ID del cliente: ");
                        String id = scanner.nextLine();
                        try {
                            String resumen = clienteServicio.obtenerResumen(id);
                            logger.info(resumen);
                        } catch (IllegalArgumentException e) {
                            logger.warn((ERROR_PREFIX)+ "{}", e.getMessage());
                        }
                    }

                    break;
                case "4":
                    menuprincipal = true;
                    break;
                default:
                    logger.warn("Opción inválida.");
            }

        }
    }

    private static void menuGestionClientes() {
        boolean volver = false;

        while(!volver){
            logger.info("\n Gestión de Clientes ");
            logger.info("1. Crear cliente");
            logger.info("2. Listar clientes");
            logger.info("3. Editar cliente");
            logger.info("4. Eliminar cliente");
            logger.info("5. Volver");
            logger.info("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    crearCliente();
                    break;
                case "2":
                    listarClientes();
                    break;
                case "3":
                    editarCliente();
                    break;
                case "4":
                    eliminarCliente();
                    break;
                case "5":
                    volver = true;
                    break;
                default:
                    logger.warn("Opción inválida, por favor ingrese una nueva opcion.");
            }
        }
    }

    /**
     * Métodos para Gestión de clientes
     */
    private static void crearCliente(){
        logger.info("ID: ");
        String id = scanner.nextLine();
        logger.info("Nombre: ");
        String nombre = scanner.nextLine();
        logger.info("Correo: ");
        String correo = scanner.nextLine();

        try {
            clienteServicio.crearCliente(id, nombre, correo);
            logger.info("✅ Cliente creado correctamente.");
        } catch (Exception e) {
            logger.warn(ERROR_PREFIX + "{}", e.getMessage());
        }
    }

    private static void listarClientes(){
        var clientes = clienteServicio.listarClientes();
        if(clientes.isEmpty()){
            logger.warn("No hay clientes registrados.");
        } else {
            logger.info("Listado de clientes:");
            for (var cliente : clientes) {
                logger.info("ID: {} | Nombre: {} | Correo: {} | Puntos: {} | Nivel: {}",
                        cliente.getId(),
                        cliente.getNombre(),
                        cliente.getCorreo(),
                        cliente.getPuntos(),
                        cliente.getNivel());
            }
        }
    }

    private static void editarCliente(){
        logger.info("ID del cliente a editar: ");
        String id = scanner.nextLine();

        logger.info("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        logger.info("Nuevo correo: ");
        String nuevoCorreo = scanner.nextLine();

        try {
            clienteServicio.editarCliente(id, nuevoNombre, nuevoCorreo);
            logger.info("✅ Cliente actualizado.");
        } catch (Exception e) {
            logger.warn(ERROR_PREFIX + "{}", e.getMessage());
        }
    }

    private static void eliminarCliente(){
        logger.info("ID del cliente a eliminar: ");
        String id = scanner.nextLine();

        clienteServicio.eliminarCliente(id);
        logger.info("✅ Cliente eliminado (si existía).");
    }

    /***********************************
     * Métodos para Gestión de Compras *
     ***********************************/
    private static void menuGestionCompras() {
        boolean volver = false;

        while (!volver) {
            logger.info("\n Gestión de Compras ");
            logger.info("1. Registrar compra");
            logger.info("2. Listar compras por cliente");
            logger.info("3. Volver");
            logger.info("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    registrarCompra();
                    break;
                case "2":
                    listarComprasPorCliente();
                    break;
                case "3":
                    volver = true;
                    break;
                default:
                    logger.warn("Opción inválida.");
            }
        }
    }

    private static void registrarCompra() {
        logger.info("ID compra: ");
        String idCompra = scanner.nextLine();
        logger.info("ID cliente: ");
        String idCliente = scanner.nextLine();
        logger.info("Monto ($): ");
        int monto = Integer.parseInt(scanner.nextLine());

        try {
            compraServicio.registrarCompra(idCompra, idCliente, monto);
            logger.info("✅ Compra registrada con éxito.");
        } catch (Exception e) {
            logger.warn(ERROR_PREFIX + "{}", e.getMessage());
        }
    }

    private static void listarComprasPorCliente() {
        logger.info("ID cliente: ");
        String idCliente = scanner.nextLine();

        List<Compra> compras = compraServicio.obtenerComprasPorCliente(idCliente);

        if (compras.isEmpty()) {
            logger.warn("⚠️ No hay compras registradas para este cliente.");
        } else {
            logger.info("📋 Historial de compras:");
            for (Compra c : compras) {
                logger.info("ID: {} | Fecha: {} | Monto: {} | Puntos otorgados: {}",
                        c.getIdCompra(), c.getFecha(), c.getMonto(), c.calcularPuntos());
            }
        }
    }
}