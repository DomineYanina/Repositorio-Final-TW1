package com.tallerwebi.dominio.excepcion;

public class CodigoEdicionExistenteException extends RuntimeException {
    public CodigoEdicionExistenteException() {
        super("Ya existe una publicación con ese código.");
    }
}
