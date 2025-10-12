package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private EstadoLote estado;

    @OneToMany(mappedBy = "lote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sombrero> sombreros = new ArrayList<Sombrero>();

    public Lote() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoLote getEstado() {
        return estado;
    }

    public void setEstado(EstadoLote estado) {
        this.estado = estado;
    }

    public List<Sombrero> getSombreros() {
        return sombreros;
    }

    public void setSombreros(List<Sombrero> sombreros) {
        this.sombreros = sombreros;
    }

    public void agregarSombrero(Sombrero sombrero) {
        sombreros.add(sombrero);
        sombrero.setLote(this);
    }

    public void removerSombrero(Sombrero sombrero) {
        sombreros.remove(sombrero);
        sombrero.setLote(null);
    }
}
