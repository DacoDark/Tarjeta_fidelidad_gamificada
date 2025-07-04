package com.fidelidad.servicio;


import com.fidelidad.Cliente;
import com.fidelidad.repositorio.ClienteRepositorio;

public class ClienteServicio {

    private final ClienteRepositorio repositorio;

    public ClienteServicio(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void crearCliente(String id, String nombre, String correo){
        Cliente cliente = new Cliente(id, nombre, correo);
        repositorio.guardar(cliente);
    }
}
