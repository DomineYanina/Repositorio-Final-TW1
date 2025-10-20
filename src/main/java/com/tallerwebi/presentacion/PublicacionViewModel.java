package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Edicion;
import com.tallerwebi.dominio.EstadoPublicacion;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class PublicacionViewModel {
    private long codigo;
    private EstadoPublicacion estado;
    private List<Long> ediciones = new ArrayList<>();

    public PublicacionViewModel() {
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public EstadoPublicacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoPublicacion estado) {
        this.estado = estado;
    }

    public List<Long> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<Long> ediciones) {
        this.ediciones = ediciones;
    }

    public void agregarEdicion(Long edicionId) {
        ediciones.add(edicionId);
    }

    public void eliminarEdicion(Long edicionId) {
        ediciones.remove(edicionId);
    }
}
