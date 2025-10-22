package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEvento {
    Evento guardar(Evento evento);

    Evento buscarPorCodigo(String codigo);

    Evento modificar(Evento eventoCreado);

    List<Evento> obtenerFinalizados();

    List<Evento> obtenerTodos();

    boolean existeCodigo(String codigo);
}
