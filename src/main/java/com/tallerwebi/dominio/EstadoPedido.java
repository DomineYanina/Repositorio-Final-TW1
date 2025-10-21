package com.tallerwebi.dominio;

public enum EstadoPedido {
    No_Iniciado("No Iniciado"),
    En_Disenio("En Diseño"),
    En_Fabricacion("En Fabricación"),
    Entregado("Entregado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }
}
