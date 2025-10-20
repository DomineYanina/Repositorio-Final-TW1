package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoEdicionExistenteException;
import com.tallerwebi.presentacion.PublicacionViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioPublicacionImpl implements ServicioPublicacion {

    private RepositorioPublicacion repositorioPublicacion;

    @Autowired
    public ServicioPublicacionImpl(RepositorioPublicacion repositorioPublicacion) {
        this.repositorioPublicacion = repositorioPublicacion;
    }


    @Override
    public PublicacionViewModel crearPublicacion(PublicacionViewModel publicacionVM) {
        if(repositorioPublicacion.codigoExistente(publicacionVM.getCodigo())) {
            throw new CodigoEdicionExistenteException();
        } else{
            Publicacion publicacion = new Publicacion();
            publicacion.setCodigo(publicacionVM.getCodigo());
            publicacion.setEstado(publicacionVM.getEstado());

            Publicacion publicacionAlmacenada = repositorioPublicacion.guardar(publicacion);
            PublicacionViewModel resultado = new PublicacionViewModel();
            resultado.setCodigo(publicacionAlmacenada.getCodigo());
            resultado.setEstado(publicacionAlmacenada.getEstado());

            return resultado;
        }
    }

    @Override
    public PublicacionViewModel avanzarEstado(PublicacionViewModel publicacionVM) {
        Publicacion publicacion = repositorioPublicacion.buscarPorCodigo(publicacionVM.getCodigo());
        switch(publicacion.getEstado()) {
            case EN_DISENIO:
                publicacion.setEstado(EstadoPublicacion.EN_IMPRESION);
                break;
            case EN_IMPRESION:
                publicacion.setEstado(EstadoPublicacion.PUBLICADA);
                break;
            case PUBLICADA:
                break;
            case ARCHIVADA:
                break;
        }

        Publicacion publicacionActualizada = repositorioPublicacion.modificar(publicacion);

        PublicacionViewModel resultado = new PublicacionViewModel();
        resultado.setCodigo(publicacionActualizada.getCodigo());
        resultado.setEstado(publicacionActualizada.getEstado());

        return resultado;
    }

    @Override
    public PublicacionViewModel archivarPublicacion(PublicacionViewModel publicacionVM) {
        Publicacion publicacion = repositorioPublicacion.buscarPorCodigo(publicacionVM.getCodigo());

        switch(publicacion.getEstado()) {
            case EN_DISENIO:
                publicacion.setEstado(EstadoPublicacion.ARCHIVADA);
                break;
            case EN_IMPRESION:
                publicacion.setEstado(EstadoPublicacion.ARCHIVADA);
                break;
            case PUBLICADA:
                break;
            case ARCHIVADA:
                break;
        }

        Publicacion publicacionActualizada = repositorioPublicacion.modificar(publicacion);

        PublicacionViewModel resultado = new PublicacionViewModel();
        resultado.setCodigo(publicacionActualizada.getCodigo());
        resultado.setEstado(publicacionActualizada.getEstado());

        return resultado;
    }

    @Override
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        return repositorioPublicacion.obtenerTodasLasPublicaciones();
    }

    @Override
    public List<Publicacion> obtenerPublicacionesArchivadas() {
        return repositorioPublicacion.obtenerTodasLasPublicacionesArchivadas();
    }

    @Override
    public PublicacionViewModel buscarPorCodigo(long l) {
        Publicacion publicacion = repositorioPublicacion.buscarPorCodigo(l);
        PublicacionViewModel resultado = new PublicacionViewModel();
        resultado.setCodigo(publicacion.getCodigo());
        resultado.setEstado(publicacion.getEstado());
        return resultado;
    }

}
