package com.fidelidad;

import com.fidelidad.repositorio.CompraRepositorio;
import com.fidelidad.repositorio.CompraRepositorioMemoria;
import com.fidelidad.servicio.ClienteServicio;
import com.fidelidad.repositorio.ClienteRepositorio;
import com.fidelidad.repositorio.ClienteRepositorioMemoria;
import com.fidelidad.servicio.CompraServicio;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final ClienteRepositorio clienteRepo = new ClienteRepositorioMemoria();
    private static final ClienteServicio clienteServicio = new ClienteServicio(clienteRepo);
    private static final CompraRepositorio compraRepo = new CompraRepositorioMemoria();
    private static final CompraServicio compraServicio = new CompraServicio(compraRepo, clienteRepo);

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean menuprincipal = false;

        while(!menuprincipal){
            System.out.println("\n Bienvenidos al Sistema de Fidelidad ");
            System.out.println("1. Gestión de Clientes");
            System.out.println("2. Gestión de Compras");
            System.out.println("3. Mostrar Puntos y Nivel de un Cliente");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();
            switch (opcion) {
                case "1":
                    menuGestionClientes();
                    break;
                case "2":
                    menuGestionCompras();
                    break;
                case "3":
                    //menuVerPuntos();
                    break;
                case "4":
                    menuprincipal = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        }
    }

    private static void menuGestionClientes() {
        boolean volver = false;

        while(!volver){
            System.out.println("\n Gestión de Clientes ");
            System.out.println("1. Crear cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Editar cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Volver");
            System.out.print("Seleccione una opción: ");
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
                    System.out.println("Opción inválida.");
            }
        }
    }

    /**
     * Métodos para Gestión de clientes
     */
    private static void crearCliente(){
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Correo: ");
        String correo = scanner.nextLine();

        try {
            clienteServicio.crearCliente(id, nombre, correo);
            System.out.println("✅ Cliente creado correctamente.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    private static void listarClientes(){
        var clientes = clienteServicio.listarClientes();
        if(clientes.isEmpty()){
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.println("Listado de clientes:");
            for (var cliente : clientes) {
                System.out.printf("ID: %s | Nombre: %s | Correo: %s | Puntos: %d | Nivel: %s%n",
                        cliente.getId(), cliente.getNombre(), cliente.getCorreo(), cliente.getPuntos(), cliente.getNivel());
            }
        }
    }

    private static void editarCliente(){
        System.out.print("ID del cliente a editar: ");
        String id = scanner.nextLine();

        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nuevo correo: ");
        String nuevoCorreo = scanner.nextLine();

        try {
            clienteServicio.editarCliente(id, nuevoNombre, nuevoCorreo);
            System.out.println("✅ Cliente actualizado.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    private static void eliminarCliente(){
        System.out.print("ID del cliente a eliminar: ");
        String id = scanner.nextLine();

        clienteServicio.eliminarCliente(id);
        System.out.println("✅ Cliente eliminado (si existía).");
    }

    /***********************************
     * Métodos para Gestión de Compras *
     ***********************************/
    private static void menuGestionCompras() {
        boolean volver = false;

        while (!volver) {
            System.out.println("\n Gestión de Compras ");
            System.out.println("1. Registrar compra");
            System.out.println("2. Listar compras por cliente");
            System.out.println("3. Volver");
            System.out.print("Seleccione una opción: ");
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
                    System.out.println("Opción inválida.");
            }
        }
    }

    private static void registrarCompra() {
        System.out.print("ID compra: ");
        String idCompra = scanner.nextLine();
        System.out.print("ID cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Monto ($): ");
        int monto = Integer.parseInt(scanner.nextLine());

        try {
            compraServicio.registrarCompra(idCompra, idCliente, monto);
            System.out.println("✅ Compra registrada con éxito.");
        } catch (Exception e) {
            System.out.println("⚠️ Error: " + e.getMessage());
        }
    }

    private static void listarComprasPorCliente() {
        System.out.print("ID cliente: ");
        String idCliente = scanner.nextLine();

        List<Compra> compras = compraServicio.obtenerComprasPorCliente(idCliente);

        if (compras.isEmpty()) {
            System.out.println("⚠️ No hay compras registradas para este cliente.");
        } else {
            System.out.println("📋 Historial de compras:");
            for (Compra c : compras) {
                System.out.printf("ID: %s | Fecha: %s | Monto: %s | Puntos otorgados: %d%n",
                        c.getIdCompra(), c.getFecha(), c.getMonto(), c.calcularPuntos());
            }
        }
    }
}