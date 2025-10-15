package com.tallerwebi.dominio.excepcion;

public class VehiculoInhabilitadoException extends AsignacionConductorException {
    public VehiculoInhabilitadoException() {
        super("El vehículo seleccionado está inhabilitado");
    }
}
