package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioConductor {
    Conductor crear(Conductor conductor);

    Conductor modificar(Conductor conductorAlmacenado);

    Conductor buscar(long l);

    List<Conductor> buscarHabilitados();

    List<Conductor> buscarInhabilitados();

    List<Conductor> buscarTodos();
}
