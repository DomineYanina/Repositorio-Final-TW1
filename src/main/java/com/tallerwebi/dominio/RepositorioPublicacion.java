package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPublicacion {
    Publicacion guardar(Publicacion publicacion);

    List<Publicacion> obtenerTodasLasPublicaciones();

    Publicacion modificar(Publicacion publicacionAlmacenada);

    List<Publicacion> obtenerTodasLasPublicacionesArchivadas();

    boolean codigoExistente(long codigo);

    Publicacion buscarPorCodigo(long codigo);
}
