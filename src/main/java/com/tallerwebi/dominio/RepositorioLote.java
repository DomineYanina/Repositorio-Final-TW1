package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLote {
    Lote agregar(Lote lote);

    Lote modificar(Lote lote2);

    List<Sombrero> obtenerSombrerosPorLote(Long id);

    Lote buscarPorId(Long id);

    List<Lote> buscarLotesFinalizados();

    List<Lote> obtenerTodosLosLotes();
}
