package com.tallerwebi.dominio;

public enum EstadoEvento {
    Planificado("Planificado"),
    EnCurso("En Curso"),
    Finalizado("Finalizado");

    private String descripcion;

    private EstadoEvento(String descripcion) {
        this.descripcion = descripcion;
    }
}
