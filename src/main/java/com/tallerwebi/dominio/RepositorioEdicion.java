package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEdicion {
    Edicion guardar(Edicion edicion);

    List<Edicion> obtenerTodasLasEdiciones();
}
