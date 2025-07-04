package com.fidelidad.repositorio;

import com.fidelidad.Cliente;

import java.util.*;

public class ClienteRepositorioMemoria implements ClienteRepositorio{

    private final Map<String, Cliente> base = new HashMap<>();

    @Override
    public void guardar(Cliente cliente) {
        base.put(cliente.getId(), cliente);
    }

    @Override
    public Cliente buscarPorId(String id) {
        return base.get(id);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(base.values());
    }

    @Override
    public void eliminar(String id) {
        base.remove(id);
    }
}
