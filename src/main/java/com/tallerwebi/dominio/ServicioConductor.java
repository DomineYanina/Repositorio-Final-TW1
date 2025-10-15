package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ConductorViewModel;

import java.util.List;

public interface ServicioConductor {
    Conductor crear(ConductorViewModel conductorViewModel);

    Conductor modificar(ConductorViewModel conductorViewModel);

    List<Conductor> buscarTodosHabilitados();

    List<Conductor> buscarTodosInhabilitados();

    List<Conductor> buscarTodos();

    Conductor buscarPorId(Long conductor);
}
