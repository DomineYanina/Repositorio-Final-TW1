package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.MuebleViewModel;
import com.tallerwebi.presentacion.PedidoViewModel;

import java.util.List;

public interface ServicioPedido {
    PedidoViewModel crearPedido(PedidoViewModel pedidoViewModel);

    List<Pedido> obtenerTodos();

    List<Pedido> obtenerEntregados();

    PedidoViewModel avanzarEstadoPedido(String ped123);

    PedidoViewModel buscarPedidoPorId(Long pedido);

    Pedido agregarMuebleAPedido(PedidoViewModel pedidoViewModel, MuebleViewModel muebleViewModel, int cantidadMuebles);
}
