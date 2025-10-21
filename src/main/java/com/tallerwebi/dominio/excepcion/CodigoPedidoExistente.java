package com.tallerwebi.dominio.excepcion;

public class CodigoPedidoExistente extends RuntimeException {
    public CodigoPedidoExistente() {
        super("Código de pedido existente en otro registro");
    }
}
