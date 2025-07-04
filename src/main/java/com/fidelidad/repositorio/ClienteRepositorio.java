package com.fidelidad.repositorio;

import com.fidelidad.Cliente;

import java.util.*;

public interface ClienteRepositorio {

    void guardar(Cliente cliente);
    Cliente buscarPorId(String id);
    List<Cliente> obtenerTodos();
    void eliminar(String id);
}
