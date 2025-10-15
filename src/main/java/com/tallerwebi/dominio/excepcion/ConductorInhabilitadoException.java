package com.tallerwebi.dominio.excepcion;

public class ConductorInhabilitadoException extends AsignacionConductorException {
    public ConductorInhabilitadoException() {
        super("El conductor seleccionado está inhabilitado");
    }
}
