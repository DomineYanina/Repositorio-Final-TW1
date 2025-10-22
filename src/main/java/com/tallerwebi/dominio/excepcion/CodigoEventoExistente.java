package com.tallerwebi.dominio.excepcion;

public class CodigoEventoExistente extends RuntimeException {
    public CodigoEventoExistente() {
        super("Código de evento existente en otro registro");
    }
}
