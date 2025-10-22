package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoEventoExistente;
import com.tallerwebi.presentacion.EventoViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioEventoTest {

    @Mock
    private RepositorioEvento repositorioEvento;

    @InjectMocks
    private ServicioEventoImpl servicioEvento;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueMePermitaCrearCorrectamenteUnEventoConEstadoInicialPlanificadoCuyoCodigoAunNoExiste(){
        EventoViewModel eventoViewModel = new EventoViewModel();
        eventoViewModel.setCodigo("EVT100");

        Evento evento = new Evento();
        evento.setCodigo(eventoViewModel.getCodigo());
        evento.setEstado(EstadoEvento.Planificado);
        evento.setId(1L);

        when(repositorioEvento.guardar(any(Evento.class))).thenReturn(evento);
        when(repositorioEvento.existeCodigo("EVT100")).thenReturn(false);

        EventoViewModel eventoCreado = servicioEvento.crearEvento(eventoViewModel);

        assertNotNull(eventoCreado);
        assertEquals("EVT100", eventoCreado.getCodigo());
        assertEquals(EstadoEvento.Planificado, evento.getEstado());
        verify(repositorioEvento, times(1)).guardar(any(Evento.class));
        verify(repositorioEvento, times(1)).existeCodigo(anyString());
    }

    @Test
    public void testQueAlIntentarCrearUnEventoCuyoCodigoYaExisteMeDevuelvaUnaExcepcion(){
        EventoViewModel eventoViewModel = new EventoViewModel();
        eventoViewModel.setCodigo("EVT100");

        when(repositorioEvento.existeCodigo("EVT100")).thenReturn(true);

        assertThrows(CodigoEventoExistente.class, () -> {
            servicioEvento.crearEvento(eventoViewModel);
        });
        verify(repositorioEvento, times(1)).existeCodigo(anyString());
    }

    @Test
    public void testQueAlCambiarElEstadoDeUnEventoCuyoEstadoEsPlanificadoDebePasarASerEnCurso(){
        EventoViewModel eventoViewModel = new EventoViewModel();
        eventoViewModel.setCodigo("EVT200");
        eventoViewModel.setEstado(EstadoEvento.Planificado);
        eventoViewModel.setId(1L);

        Evento eventoEntrada = new Evento();
        eventoEntrada.setCodigo(eventoViewModel.getCodigo());
        eventoEntrada.setEstado(EstadoEvento.Planificado);
        eventoEntrada.setId(1L);

        Evento eventoSalida = new Evento();
        eventoSalida.setCodigo(eventoViewModel.getCodigo());
        eventoSalida.setEstado(EstadoEvento.EnCurso);
        eventoSalida.setId(1L);

        when(repositorioEvento.buscarPorCodigo(anyString())).thenReturn(eventoEntrada);
        when(repositorioEvento.modificar(any(Evento.class))).thenReturn(eventoSalida);

        EventoViewModel eventoModificado = servicioEvento.avanzarEstado(eventoViewModel);

        assertNotNull(eventoModificado);
        assertEquals(EstadoEvento.EnCurso, eventoModificado.getEstado());
        verify(repositorioEvento, times(1)).modificar(any(Evento.class));
    }

    @Test
    public void testQueAlCambiarElEstadoDeUnEventoCuyoEstadoEsEnCursoDebePasarASerFinalizado(){
        EventoViewModel eventoViewModel = new EventoViewModel();
        eventoViewModel.setCodigo("EVT200");
        eventoViewModel.setEstado(EstadoEvento.EnCurso);
        eventoViewModel.setId(1L);

        Evento eventoEntrada = new Evento();
        eventoEntrada.setCodigo(eventoViewModel.getCodigo());
        eventoEntrada.setEstado(EstadoEvento.EnCurso);
        eventoEntrada.setId(1L);

        Evento eventoSalida = new Evento();
        eventoSalida.setCodigo(eventoViewModel.getCodigo());
        eventoSalida.setEstado(EstadoEvento.Finalizado);
        eventoSalida.setId(1L);

        when(repositorioEvento.buscarPorCodigo(anyString())).thenReturn(eventoEntrada);
        when(repositorioEvento.modificar(any(Evento.class))).thenReturn(eventoSalida);

        EventoViewModel eventoModificado = servicioEvento.avanzarEstado(eventoViewModel);

        assertNotNull(eventoModificado);
        assertEquals(EstadoEvento.Finalizado, eventoModificado.getEstado());
        verify(repositorioEvento, times(1)).modificar(any(Evento.class));
    }

    @Test
    public void testQueAlCambiarElEstadoDeUnEventoCuyoEstadoEsFinalizadoElEstadoNoCambie(){
        EventoViewModel eventoViewModel = new EventoViewModel();
        eventoViewModel.setCodigo("EVT200");
        eventoViewModel.setEstado(EstadoEvento.Finalizado);
        eventoViewModel.setId(1L);

        Evento eventoEntrada = new Evento();
        eventoEntrada.setCodigo(eventoViewModel.getCodigo());
        eventoEntrada.setEstado(EstadoEvento.Finalizado);
        eventoEntrada.setId(1L);

        Evento eventoSalida = new Evento();
        eventoSalida.setCodigo(eventoViewModel.getCodigo());
        eventoSalida.setEstado(EstadoEvento.Finalizado);
        eventoSalida.setId(1L);

        when(repositorioEvento.buscarPorCodigo(anyString())).thenReturn(eventoEntrada);
        when(repositorioEvento.modificar(any(Evento.class))).thenReturn(eventoSalida);

        EventoViewModel eventoModificado = servicioEvento.avanzarEstado(eventoViewModel);

        assertNotNull(eventoModificado);
        assertEquals(EstadoEvento.Finalizado, eventoModificado.getEstado());
        verify(repositorioEvento, times(1)).modificar(any(Evento.class));
    }

}
