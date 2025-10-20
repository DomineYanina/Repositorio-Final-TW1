package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long codigo;
    private EstadoPublicacion estado;
    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Edicion> ediciones = new ArrayList<>();

    public Publicacion() {
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

    public List<Edicion> getEdiciones() {
        return ediciones;
    }

    public void setEdiciones(List<Edicion> ediciones) {
        this.ediciones = ediciones;
    }

    public void agregarEdicion(Edicion edicion) {
        ediciones.add(edicion);
        edicion.setPublicacion(this);
    }

    public void eliminarEdicion(Edicion edicion) {
        ediciones.remove(edicion);
        edicion.setPublicacion(null);
    }

    public Double getCostoTotalProduccion() {
        return ediciones.stream()
                        .mapToDouble(Edicion::getCostoProduccionUnitario)
                        .sum();
    }
}
