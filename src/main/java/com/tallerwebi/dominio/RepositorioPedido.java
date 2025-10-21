package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPedido {
    Pedido guardar(Pedido pedido);

    List<Pedido> obtenerTodos();

    List<Pedido> obtenerEntregados();

    Pedido modificar(Pedido pedido);

    boolean existeCodigo(String ped123);

    Pedido buscarPorCodigo(String ped123);

    Pedido buscarPorId(Long pedido);
}
