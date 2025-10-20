package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.PublicacionViewModel;
import java.util.List;

public interface ServicioPublicacion {
    PublicacionViewModel crearPublicacion(PublicacionViewModel publicacionVM);

    PublicacionViewModel avanzarEstado(PublicacionViewModel publicacionVM);

    PublicacionViewModel archivarPublicacion(PublicacionViewModel publicacionVM);

    List<Publicacion> obtenerTodasLasPublicaciones();

    List<Publicacion> obtenerPublicacionesArchivadas();

    PublicacionViewModel buscarPorCodigo(long l);
}
