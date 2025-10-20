package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class RepositorioPublicacionTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPublicacion repositorioPublicacion;

    @BeforeEach
    public void init() {
        repositorioPublicacion = new RepositorioPublicacionImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearUnaNuevaPublicacion(){
        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(4L);
        publicacion.setEstado(EstadoPublicacion.EN_DISENIO);

        Publicacion publicacionAlmacenada = repositorioPublicacion.guardar(publicacion);

        assertNotNull(publicacionAlmacenada);
        assertEquals(4L, publicacionAlmacenada.getCodigo());
        assertEquals(EstadoPublicacion.EN_DISENIO, publicacionAlmacenada.getEstado());
    }

    @Test
    @Rollback
    public void testQueAlAlmacenar3PublicacionesEnLaBaseDeDatosYSolicitarUnaListaDeLasMismasMeRetorneUnaListaCon3Publicaciones(){
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setCodigo(1L);
        publicacion1.setEstado(EstadoPublicacion.EN_DISENIO);
        repositorioPublicacion.guardar(publicacion1);

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setCodigo(2L);
        publicacion2.setEstado(EstadoPublicacion.PUBLICADA);
        repositorioPublicacion.guardar(publicacion2);

        Publicacion publicacion3 = new Publicacion();
        publicacion3.setCodigo(3L);
        publicacion3.setEstado(EstadoPublicacion.ARCHIVADA);
        repositorioPublicacion.guardar(publicacion3);

        List<Publicacion> publicaciones = repositorioPublicacion.obtenerTodasLasPublicaciones();

        assertNotNull(publicaciones);
        assertEquals(3, publicaciones.size());
    }

    @Test
    @Rollback
    public void testQueAlAlmacenarUnaPublicacionPublicadaY3ArchivadasYSolicitarUnaListaDeLasArchivadasMeRetorneUnaListaCon3Publicaciones(){
        Publicacion publicacion1 = new Publicacion();
        publicacion1.setCodigo(1L);
        publicacion1.setEstado(EstadoPublicacion.PUBLICADA);
        repositorioPublicacion.guardar(publicacion1);

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setCodigo(2L);
        publicacion2.setEstado(EstadoPublicacion.ARCHIVADA);
        repositorioPublicacion.guardar(publicacion2);

        Publicacion publicacion3 = new Publicacion();
        publicacion3.setCodigo(3L);
        publicacion3.setEstado(EstadoPublicacion.ARCHIVADA);
        repositorioPublicacion.guardar(publicacion3);

        Publicacion publicacion4 = new Publicacion();
        publicacion4.setCodigo(4L);
        publicacion4.setEstado(EstadoPublicacion.ARCHIVADA);
        repositorioPublicacion.guardar(publicacion4);

        List<Publicacion> todasLasPublicaciones = repositorioPublicacion.obtenerTodasLasPublicaciones();
        List<Publicacion> publicaciones = repositorioPublicacion.obtenerTodasLasPublicacionesArchivadas();


        assertNotNull(publicaciones);
        assertEquals(4, todasLasPublicaciones.size());
        assertEquals(3, publicaciones.size());
    }

    @Test
    @Rollback
    public void queAlNoAlmacenarPublicacionesYSolicitarUnaListaDeLasMismasMeRetorneUnaListaVacia(){
        List<Publicacion> publicaciones = repositorioPublicacion.obtenerTodasLasPublicaciones();

        assertNotNull(publicaciones);
        assertEquals(0, publicaciones.size());
    }

    @Test
    @Rollback
    public void testQueMePermitaAgregarUnaEdicionAUnaPublicacionExistente() {
        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.EN_DISENIO);

        Publicacion publicacionAlmacenada = repositorioPublicacion.guardar(publicacion);

        Edicion edicion = new Edicion();
        edicion.setNumeroEdicion(1L);
        edicion.setCostoProduccionUnitario(100.0);
        edicion.setTipoContenido(TipoContenido.Mensual);

        publicacionAlmacenada.agregarEdicion(edicion);

        Publicacion publicacionModificada = repositorioPublicacion.modificar(publicacionAlmacenada);

        assertNotNull(publicacionModificada);
        assertEquals(1L, publicacionModificada.getCodigo());
        assertEquals(EstadoPublicacion.EN_DISENIO, publicacionModificada.getEstado());
        assertEquals(1, publicacionModificada.getEdiciones().size());
    }

    @Test
    @Rollback
    public void testQueMePermitaModificarElEstadoDeUnaPublicacionExistente() {
        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.EN_DISENIO);

        Publicacion publicacionAlmacenada = repositorioPublicacion.guardar(publicacion);

        publicacionAlmacenada.setEstado(EstadoPublicacion.PUBLICADA);

        Publicacion publicacionModificada = repositorioPublicacion.modificar(publicacionAlmacenada);

        assertNotNull(publicacionModificada);
        assertEquals(1L, publicacionModificada.getCodigo());
        assertEquals(EstadoPublicacion.PUBLICADA, publicacionModificada.getEstado());
    }


}
