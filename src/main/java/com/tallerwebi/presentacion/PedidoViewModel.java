package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoPedido;

public class PedidoViewModel {
    private Long id;
    private String codigo;
    private EstadoPedido estado;

    public PedidoViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
