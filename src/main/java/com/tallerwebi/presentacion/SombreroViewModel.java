package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.TipoSombrero;

public class SombreroViewModel {
    private Long id;
    private TipoSombrero tipo;
    private Long lote;

    public SombreroViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoSombrero getTipo() {
        return tipo;
    }

    public void setTipo(TipoSombrero tipo) {
        this.tipo = tipo;
    }

    public Long getLote() {
        return lote;
    }

    public void setLote(Long lote) {
        this.lote = lote;
    }
}
