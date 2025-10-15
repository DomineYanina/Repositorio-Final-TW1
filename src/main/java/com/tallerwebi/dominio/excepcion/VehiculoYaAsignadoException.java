package com.tallerwebi.dominio.excepcion;

public class VehiculoYaAsignadoException extends AsignacionConductorException {
    public VehiculoYaAsignadoException() {
        super("Este vehículo ya tiene un conductor asignado");
    }
}
