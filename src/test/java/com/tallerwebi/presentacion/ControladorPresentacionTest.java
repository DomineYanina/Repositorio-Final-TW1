package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoPublicacion;
import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.ServicioPublicacionImpl;
import com.tallerwebi.dominio.TipoContenido;
import com.tallerwebi.dominio.excepcion.CodigoEdicionExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ControladorPresentacionTest {
    @Mock
    private ServicioPublicacionImpl servicioPublicacion;
    @InjectMocks
    private ControladorPresentacion controladorPresentacion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueAlQuererAccederALaVistaDeCreacionDeUnaPublicacionMeRedirijaCorrectamente(){
        ModelAndView modeloVista = controladorPresentacion.irACrearPresentacion();

        assertNotNull(modeloVista);
        assertEquals("crearPublicacion", modeloVista.getViewName());
        assertThat(modeloVista.getModel(),hasKey("publicacionVM"));
    }

    @Test
    public void testQueMePermitaAlmacenarCorrectamenteUnaPublicacionCuyoCodigoNoExistaYMeRedirijaALaListaDePublicaciones(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        Publicacion publicacion = new Publicacion();
        publicacion.setCodigo(1L);
        publicacion.setEstado(EstadoPublicacion.EN_DISENIO);

        List<Publicacion> listaPublicaciones = List.of(publicacion);
        List<Publicacion> publicacionesArchivadas = List.of();

        when(servicioPublicacion.crearPublicacion(any(PublicacionViewModel.class))).thenReturn(publicacionVM);
        when(servicioPublicacion.obtenerPublicacionesArchivadas()).thenReturn(publicacionesArchivadas);
        when(servicioPublicacion.obtenerTodasLasPublicaciones()).thenReturn(listaPublicaciones);

        ModelAndView modeloVista = controladorPresentacion.crearPresentacion(publicacionVM);

        assertNotNull(modeloVista);
        assertEquals(modeloVista.getViewName(), "listaPublicaciones");
        assertThat(modeloVista.getModel(),hasKey("publicacionesArchivadas"));
        assertThat(modeloVista.getModel(),hasKey("listaPublicaciones"));
        assertEquals(0, ((List<Publicacion>) modeloVista.getModel().get("publicacionesArchivadas")).size());
        assertEquals(1, ((List<Publicacion>) modeloVista.getModel().get("listaPublicaciones")).size());
    }

    @Test
    public void testQueAlIntentarAlmacenarUnaPublicacionConUnCodigoExistenteMeRedirijaALaVistaDeCreacionDePublicacionConLaExcepcion(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        when(servicioPublicacion.crearPublicacion(any(PublicacionViewModel.class))).thenThrow(new CodigoEdicionExistenteException());

        ModelAndView modeloVista = controladorPresentacion.crearPresentacion(publicacionVM);

        assertNotNull(modeloVista);
        assertEquals(modeloVista.getViewName(), "crearPublicacion");
        assertThat(modeloVista.getModel(),hasKey("publicacionVM"));
        assertThat(modeloVista.getModel(),hasKey("error"));
        assertEquals("Ya existe una publicación con ese código.", modeloVista.getModel().get("error"));
    }

    @Test
    public void testQueMeRedirijaCorrectamenteALAVistaDeCreacionDeEdicionAlSolicitarAgregarUnaEdicion(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        when(servicioPublicacion.buscarPorCodigo(1L)).thenReturn(publicacionVM);

        ModelAndView modeloVista = controladorPresentacion.irAAgregarEdicion(1L);

        assertNotNull(modeloVista);
        assertEquals("agregarEdicion", modeloVista.getViewName());
        assertThat(modeloVista.getModel(),hasKey("publicacionVM"));
        assertThat(modeloVista.getModel(),hasKey("edicionVM"));
    }

    @Test
    public void testQueMePermitaAgregarCorrectamenteUnaEdicionAUnaPublicacionYMeRedirijaALaListaDePublicaciones(){
        PublicacionViewModel publicacionVM = new PublicacionViewModel();
        publicacionVM.setCodigo(1L);
        publicacionVM.setEstado(EstadoPublicacion.EN_DISENIO);

        EdicionViewModel edicionVM = new EdicionViewModel();
        edicionVM.setNumeroEdicion(1);
        edicionVM.setTipoContenido(TipoContenido.Mensual);

        when(servicioPublicacion.buscarPorCodigo(1L)).thenReturn(publicacionVM);


        ModelAndView modeloVista = controladorPresentacion.agregarEdicion(1L, edicionVM);
    }
}
