package com.tallerwebi.dominio;

public enum EstadoLote {

    No_Iniciado("No Iniciado"),
    En_Progreso("En Progreso"),
    Completado("Completado");

    private String descripcion;

    EstadoLote(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
