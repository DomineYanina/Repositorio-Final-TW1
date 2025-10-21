package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoPedidoExistente;
import com.tallerwebi.presentacion.MuebleViewModel;
import com.tallerwebi.presentacion.PedidoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioPedidoImpl implements ServicioPedido {
    private RepositorioPedido repositorioPedido;

    @Autowired
    public ServicioPedidoImpl(RepositorioPedido repositorioPedido) {
        this.repositorioPedido = repositorioPedido;
    }

    @Override
    public PedidoViewModel crearPedido(PedidoViewModel pedidoViewModel) {
        if(repositorioPedido.existeCodigo(pedidoViewModel.getCodigo())) {
            throw new CodigoPedidoExistente();
        } else {
            Pedido pedido = new Pedido();
            pedido.setCodigo(pedidoViewModel.getCodigo());
            pedido.setEstado(EstadoPedido.No_Iniciado);

            Pedido pedidoGuardado = repositorioPedido.guardar(pedido);

            PedidoViewModel pedidoViewModelCreado = new PedidoViewModel();
            pedidoViewModelCreado.setId(pedidoGuardado.getId());
            pedidoViewModelCreado.setCodigo(pedidoGuardado.getCodigo());
            pedidoViewModelCreado.setEstado(pedidoGuardado.getEstado());

            return pedidoViewModelCreado;
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return repositorioPedido.obtenerTodos();
    }

    @Override
    public List<Pedido> obtenerEntregados() {
        return repositorioPedido.obtenerEntregados();
    }

    @Override
    public PedidoViewModel avanzarEstadoPedido(String ped123) {
        Pedido pedido = repositorioPedido.buscarPorCodigo(ped123);
        switch(pedido.getEstado()) {
            case No_Iniciado:
                pedido.setEstado(EstadoPedido.En_Disenio);
                break;
            case En_Disenio:
                pedido.setEstado(EstadoPedido.En_Fabricacion);
                break;
            case En_Fabricacion:
                pedido.setEstado(EstadoPedido.Entregado);
                break;
            case Entregado:
                // No hay más estados para avanzar
                break;
        }
        Pedido pedidoActualizado = repositorioPedido.modificar(pedido);

        PedidoViewModel pedidoViewModelActualizado = new PedidoViewModel();
        pedidoViewModelActualizado.setId(pedidoActualizado.getId());
        pedidoViewModelActualizado.setCodigo(pedidoActualizado.getCodigo());
        pedidoViewModelActualizado.setEstado(pedidoActualizado.getEstado());
        return pedidoViewModelActualizado;
    }

    @Override
    public PedidoViewModel buscarPedidoPorId(Long idPedido) {
        Pedido pedido = repositorioPedido.buscarPorId(idPedido);
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setId(pedido.getId());
        pedidoViewModel.setCodigo(pedido.getCodigo());
        pedidoViewModel.setEstado(pedido.getEstado());
        return pedidoViewModel;
    }

    @Override
    public Pedido agregarMuebleAPedido(PedidoViewModel pedidoViewModel, MuebleViewModel muebleViewModel, int cantidadMuebles) {
        Mueble mueble = new Mueble();
        mueble.setCodigo(muebleViewModel.getCodigo());
        mueble.setCosto(muebleViewModel.getCosto());

        Pedido pedido = repositorioPedido.buscarPorCodigo(pedidoViewModel.getCodigo());
        for (int i = 0; i < cantidadMuebles; i++) {
            pedido.agregarMueble(mueble);
        }
        return repositorioPedido.modificar(pedido);
    }
}
