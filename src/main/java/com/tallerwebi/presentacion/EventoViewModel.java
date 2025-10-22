package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoEvento;

public class EventoViewModel {
    private Long id;
    private String codigo;
    private EstadoEvento estado;

    public EventoViewModel() {
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

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }
}
