package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.LoteViewModel;
import com.tallerwebi.presentacion.SombreroViewModel;

import java.util.List;

public interface ServicioLote {
    Lote crearLote(LoteViewModel loteViewModel);

    Lote agregarSombreros(LoteViewModel loteViewModel, SombreroViewModel sombrero, int i);

    Lote avanzarEstado(LoteViewModel loteViewModel);

    List<Lote> obtenerLotesFinalizados();

    List<Lote> obtenerTodosLosLotes();

    Lote obtenerLotePorId(Long idLote);
}
