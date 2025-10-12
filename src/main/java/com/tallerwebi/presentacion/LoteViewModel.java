package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Sombrero;

import java.util.ArrayList;
import java.util.List;

public class LoteViewModel {
    private Long id;
    private EstadoLote estado;
    private List<Sombrero> sombreros = new ArrayList<Sombrero>();

    public LoteViewModel() {
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
    }

    public void removerSombrero(Sombrero sombrero) {
        sombreros.remove(sombrero);
    }
}
