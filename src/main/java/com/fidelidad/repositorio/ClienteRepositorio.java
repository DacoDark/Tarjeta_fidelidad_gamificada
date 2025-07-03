package com.fidelidad.repositorio;

import com.fidelidad.Cliente;

import java.util.*;

public class ClienteRepositorio {

    private final Map<String, Cliente> base = new HashMap<>();

    public void guardar(Cliente cliente) {
        base.put(cliente.getId(), cliente);
    }

    public Optional<Cliente> buscarPorId(String id) {
        return Optional.ofNullable(base.get(id));
    }

    public List<Cliente> listar() {
        return new ArrayList<>(base.values());
    }

    public void eliminar(String id) {
        base.remove(id);
    }
}
