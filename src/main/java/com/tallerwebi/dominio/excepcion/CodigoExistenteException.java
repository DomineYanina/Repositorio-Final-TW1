package com.tallerwebi.dominio.excepcion;

public class CodigoExistenteException extends RuntimeException {
    public CodigoExistenteException() {
        super("Código existente en otro lote");
    }
}
