package com.tallerwebi.dominio;

public interface RepositorioConductor {
    Conductor crear(Conductor conductor);

    Conductor modificar(Conductor conductorAlmacenado);

    Conductor buscar(long l);
}
