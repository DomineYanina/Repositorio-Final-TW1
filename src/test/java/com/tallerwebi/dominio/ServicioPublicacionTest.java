package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoEdicionExistenteException;
import com.tallerwebi.infraestructura.RepositorioPublicacionImpl;
import com.tallerwebi.presentacion.PublicacionViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ServicioPublicacionTest {
    @Mock
    private RepositorioPublicacionImpl repositorioPublicacion;

    @InjectMocks
    private ServicioPublicacionImpl servicioPublicacion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueSiIntentoIngresarUnaPublicacionConUnCodigoNoExistenteLaCreeCorrectamente() {
        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.EN_DISENIO);

        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        when(repositorioPublicacion.guardar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.crearPublicacion(publicacionVM);

        assertNotNull(resultado);
        assertEquals(publicacion.getCodigo(), resultado.getCodigo());
        assertEquals(publicacion.getEstado(), resultado.getEstado());
    }

    @Test
    public void testQueSiIntentoIngresarUnaPublicacionConUnCodigoExistenteMeLanceUnaExcepcion(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        when(repositorioPublicacion.codigoExistente(anyLong())).thenReturn(true);
        try {
            servicioPublicacion.crearPublicacion(publicacionVM);
        } catch (CodigoEdicionExistenteException e) {
            assertEquals("Ya existe una publicación con ese código.", e.getMessage());
        }
    }

    @Test
    public void testQueSiIntentoCambiarElEstadoDeUnaPublicacionCuyoEstadoEsEnDisenioPaseASerEnImpresion(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        Publicacion publicacionO = new Publicacion();
        publicacionO.setCodigo(1L);
        publicacionO.setEstado(EstadoPublicacion.EN_IMPRESION);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.EN_IMPRESION);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacionO);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.avanzarEstado(publicacionVM);

        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.EN_IMPRESION, resultado.getEstado());
    }

    @Test
    public void testQueSiIntentoCambiarElEstadoDeUnaPublicacionCuyoEstadoEsEnImpresionPaseASerPublicada(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_IMPRESION);

        Publicacion publicacionO = new Publicacion();
        publicacionO.setCodigo(1L);
        publicacionO.setEstado(EstadoPublicacion.EN_IMPRESION);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.PUBLICADA);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacionO);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.avanzarEstado(publicacionVM);

        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.PUBLICADA, resultado.getEstado());
    }

    @Test
    public void testQueSiIntentoCambiarElEstadoDeUnaPublicacionCuyoEstadoEsPublicadaNoCambie(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.PUBLICADA);

        Publicacion publicacionO = new Publicacion();
        publicacionO.setCodigo(1L);
        publicacionO.setEstado(EstadoPublicacion.PUBLICADA);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacionO);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacionO);

        PublicacionViewModel resultado = servicioPublicacion.avanzarEstado(publicacionVM);

        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.PUBLICADA, resultado.getEstado());
    }

    @Test
    public void testQueSiIntentoCambiarElEstadoDeUnaPublicacionCuyoEstadoEsArchivadaNoCambie(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.ARCHIVADA);

        Publicacion publicacionO = new Publicacion();
        publicacionO.setCodigo(1L);
        publicacionO.setEstado(EstadoPublicacion.ARCHIVADA);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacionO);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacionO);

        PublicacionViewModel resultado = servicioPublicacion.avanzarEstado(publicacionVM);

        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.ARCHIVADA, resultado.getEstado());
    }

    @Test
    public void testQueMePermitaArchivarUnaPublicacionCuyoEstadoEsEnDisenio(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.ARCHIVADA);

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setCodigo(1L);
        publicacion2.setEstado(EstadoPublicacion.EN_DISENIO);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacion2);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.archivarPublicacion(publicacionVM);
        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.ARCHIVADA, resultado.getEstado());
    }

    @Test
    public void testQueMePermitaArchivarUnaPublicacionCuyoEstadoEsEnImpresion(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_IMPRESION);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.ARCHIVADA);

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setCodigo(1L);
        publicacion2.setEstado(EstadoPublicacion.EN_IMPRESION);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacion2);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.archivarPublicacion(publicacionVM);
        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.ARCHIVADA, resultado.getEstado());
    }

    @Test
    public void testQueAlIntentarArchivarUnaPublicacionCuyoEstadoEsPublicadaElEstadoNoCambie(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.PUBLICADA);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.PUBLICADA);

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setCodigo(1L);
        publicacion2.setEstado(EstadoPublicacion.PUBLICADA);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacion2);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.archivarPublicacion(publicacionVM);
        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.PUBLICADA, resultado.getEstado());
    }

    @Test
    public void testQueAlIntentarArchivarUnaPublicacionCuyoEstadoEsArchivadaElEstadoNoCambie(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.ARCHIVADA);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.ARCHIVADA);

        Publicacion publicacion2 = new Publicacion();
        publicacion2.setCodigo(1L);
        publicacion2.setEstado(EstadoPublicacion.ARCHIVADA);

        when(repositorioPublicacion.buscarPorCodigo(anyLong())).thenReturn(publicacion2);
        when(repositorioPublicacion.modificar(any(Publicacion.class))).thenReturn(publicacion);

        PublicacionViewModel resultado = servicioPublicacion.archivarPublicacion(publicacionVM);
        assertNotNull(resultado);
        assertEquals(EstadoPublicacion.ARCHIVADA, resultado.getEstado());
    }

}
