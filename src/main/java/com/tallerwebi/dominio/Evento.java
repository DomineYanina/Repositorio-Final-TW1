package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private EstadoEvento estado;
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Participante> participantes = new ArrayList<>();

    public Evento() {
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

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public void agregarParticipante(Participante participante) {
        participantes.add(participante);
        participante.setEvento(this);
    }

    public void eliminarParticipante(Participante participante) {
        participantes.remove(participante);
        participante.setEvento(null);
    }

    public Double calcularTotalRecaudado() {
        return participantes.stream()
                .mapToDouble(Participante::getValorCuota)
                .sum();
    }
}

