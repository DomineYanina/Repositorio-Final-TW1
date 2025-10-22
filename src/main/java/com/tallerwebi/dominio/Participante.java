package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Participante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idInscripcion;
    private Double valorCuota;
    private TipoPase tipoPase;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    public Participante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Double getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(Double valorCuota) {
        this.valorCuota = valorCuota;
    }

    public TipoPase getTipoPase() {
        return tipoPase;
    }

    public void setTipoPase(TipoPase tipoPase) {
        this.tipoPase = tipoPase;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
