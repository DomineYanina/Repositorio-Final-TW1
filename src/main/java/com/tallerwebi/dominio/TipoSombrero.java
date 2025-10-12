package com.tallerwebi.dominio;

public enum TipoSombrero {
    COWBOY("Cowboy"),
    FEDORA("Fedora"),
    PANAMA("Panama"),
    BOWLER("Bowler"),
    TOP_HAT("Top Hat"),
    BEANIE("Beanie"),
    CAP("Cap"),
    SUN_HAT("Sun Hat");

    private String descripcion;

    TipoSombrero(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
