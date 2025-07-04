package com.fidelidad.servicio;


import com.fidelidad.Cliente;
import com.fidelidad.repositorio.ClienteRepositorio;

import java.util.List;

public class ClienteServicio {

    private final ClienteRepositorio repositorio;

    public ClienteServicio(ClienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void crearCliente(String id, String nombre, String correo){
        if (!correo.contains("@")) {
            throw new IllegalArgumentException("El correo no es valido");
        }

        Cliente cliente = new Cliente(id, nombre, correo);
        repositorio.guardar(cliente);
    }

    public List<Cliente> listarClientes(){
        return repositorio.obtenerTodos();
    }

    public void editarCliente(String id, String newnombre, String newcorreo){
        Cliente cliente = repositorio.buscarPorId(id);

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        if (!newnombre.contains("@")) {
            throw new IllegalArgumentException("El correo no es valido");
        }
        cliente.setNombre(newnombre);
        cliente.setCorreo(newcorreo);
    }
}
