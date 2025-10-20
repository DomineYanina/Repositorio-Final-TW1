package com.tallerwebi.dominio;

public enum EstadoPublicacion {
    EN_DISENIO("En diseño"),
    EN_IMPRESION("En impresión"),
    PUBLICADA("Publicada"),
    ARCHIVADA("Archivada");

    private final String descripcion;

    EstadoPublicacion(String descripcion) {
        this.descripcion = descripcion;
    }
}
