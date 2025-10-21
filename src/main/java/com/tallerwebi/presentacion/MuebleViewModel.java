package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.TipoMueble;

public class MuebleViewModel {
    private Long id;
    private String codigo;
    private Double costo;
    private TipoMueble tipo;
    private Long pedido;

    public MuebleViewModel() {
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

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public TipoMueble getTipo() {
        return tipo;
    }

    public void setTipo(TipoMueble tipo) {
        this.tipo = tipo;
    }

    public Long getPedido() {
        return pedido;
    }

    public void setPedido(Long pedido) {
        this.pedido = pedido;
    }
}
