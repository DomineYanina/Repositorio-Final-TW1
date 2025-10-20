package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.TipoContenido;

public class EdicionViewModel {
    private long numeroEdicion;
    private Double costoProduccionUnitario;
    private TipoContenido tipoContenido;
    private Long publicacion;

    public EdicionViewModel() {
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

    public Long getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Long publicacion) {
        this.publicacion = publicacion;
    }
}
