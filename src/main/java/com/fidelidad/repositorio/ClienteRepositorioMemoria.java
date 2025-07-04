package com.fidelidad.repositorio;

import com.fidelidad.Cliente;

import java.util.*;

public class ClienteRepositorioMemoria implements ClienteRepositorio{

    private final Map<String, Cliente> base = new HashMap<>();

    @Override
    public void guardar(Cliente cliente) {
    }

    @Override
    public Cliente buscarPorId(String id) {
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return null;
    }

    @Override
    public void eliminar(String id) {
    }
}
