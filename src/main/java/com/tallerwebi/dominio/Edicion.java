package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Edicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long numeroEdicion;
    private Double costoProduccionUnitario;
    private TipoContenido tipoContenido;
    @ManyToOne
    @JoinColumn(name = "publicacion_id")
    private Publicacion publicacion;

    public Edicion() {
    }

    public long getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(long numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    public Double getCostoProduccionUnitario() {
        return costoProduccionUnitario;
    }

    public void setCostoProduccionUnitario(Double costoProduccionUnitario) {
        this.costoProduccionUnitario = costoProduccionUnitario;
    }

    public TipoContenido getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }
}
