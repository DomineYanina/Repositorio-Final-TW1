package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.EventoViewModel;

public interface ServicioEvento {
    EventoViewModel crearEvento(EventoViewModel eventoViewModel);

    EventoViewModel avanzarEstado(EventoViewModel eventoViewModel);
}
