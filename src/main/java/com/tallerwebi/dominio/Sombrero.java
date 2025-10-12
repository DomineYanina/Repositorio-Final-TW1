package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Sombrero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TipoSombrero tipo;

    @ManyToOne
    @JoinColumn(name = "lote_id")
    private Lote lote;

    public Sombrero() {
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

    public Lote getLote() {
        return lote;
    }

    public void setLote(Lote lote) {
        this.lote = lote;
    }
}
