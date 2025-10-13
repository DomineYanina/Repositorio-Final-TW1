package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.LoteViewModel;
import com.tallerwebi.presentacion.SombreroViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ServicioLoteTest {

    @Mock
    private RepositorioLote repositorioLote;

    @InjectMocks
    private ServicioLoteImpl servicioLote;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Rollback
    public void testCrearLote() {
        LoteViewModel loteViewModel = new LoteViewModel();
        loteViewModel.setId(1L);
        loteViewModel.setEstado(EstadoLote.No_Iniciado);

        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        when(repositorioLote.agregar(any(Lote.class))).thenReturn(lote);
        Lote lote1 = servicioLote.crearLote(loteViewModel);

        assertNotNull(lote1);
        assertEquals(lote1.getId(), 1L);
        assertEquals(lote1.getEstado(), EstadoLote.No_Iniciado);
    }

    @Test
    @Rollback
    public void testQueAlIntentarCrearUnLoteConIdExistenteLanceUnaExcepcion() {
        LoteViewModel loteViewModel = new LoteViewModel();
        loteViewModel.setId(1L);
        loteViewModel.setEstado(EstadoLote.No_Iniciado);

        Lote loteExistente = new Lote();
        loteExistente.setId(1L);
        loteExistente.setEstado(EstadoLote.No_Iniciado);

        when(repositorioLote.buscarPorId(1L)).thenReturn(loteExistente);

        assertThrows(IllegalArgumentException.class, () -> {
            servicioLote.crearLote(loteViewModel);
        });
    }

    @Test
    @Rollback
    public void testQueAlAgregarSombrerosAlLoteSeActualiceCorrectamenteLaBaseDeDatos() {
        LoteViewModel loteViewModel = new LoteViewModel();
        loteViewModel.setId(1L);
        loteViewModel.setEstado(EstadoLote.No_Iniciado);

        SombreroViewModel sombrero = new SombreroViewModel();
        sombrero.setId(1L);
        sombrero.setTipo(TipoSombrero.CAP);
        sombrero.setValorProduccion(100.0);

        Lote loteExistente = new Lote();
        loteExistente.setId(1L);
        loteExistente.setEstado(EstadoLote.No_Iniciado);

        Lote loteResultadoFinal = new Lote();
        loteResultadoFinal.setId(1L);
        loteResultadoFinal.setEstado(EstadoLote.No_Iniciado);
        loteResultadoFinal.agregarSombrero(new Sombrero(1L, TipoSombrero.CAP, 100.0));
        loteResultadoFinal.agregarSombrero(new Sombrero(1L, TipoSombrero.CAP, 100.0));

        when(repositorioLote.buscarPorId(anyLong())).thenReturn(loteExistente);

        when(repositorioLote.modificar(any(Lote.class))).thenReturn(loteResultadoFinal);

        Lote lote1 = servicioLote.agregarSombreros(loteViewModel,sombrero,2);

        Double precioLote = lote1.getSombreros().stream().mapToDouble(Sombrero::getValorProduccion).sum();

        assertNotNull(lote1);
        assertEquals(lote1.getSombreros().size(), 2);
        assertEquals(precioLote, 200.0);
    }

    @Test
    @Rollback
    public void testQueAlHacerClickEnAvanzarEstadoEnUnLoteCuyoEstadoEsNoIniciadoPaseASerEnProceso() {
        Lote lotePrevio = new Lote();
        lotePrevio.setId(1L);
        lotePrevio.setEstado(EstadoLote.No_Iniciado);

        Lote loteFinalEsperado = new Lote();
        loteFinalEsperado.setId(1L);
        loteFinalEsperado.setEstado(EstadoLote.En_Progreso);

        LoteViewModel loteViewModel = new LoteViewModel();
        loteViewModel.setId(1L);
        loteViewModel.setEstado(EstadoLote.No_Iniciado);

        when(repositorioLote.buscarPorId(anyLong())).thenReturn(lotePrevio);

        when(repositorioLote.modificar(any(Lote.class))).thenReturn(loteFinalEsperado);

        Lote loteActualizado = servicioLote.avanzarEstado(loteViewModel);

        assertNotNull(loteActualizado);
        assertEquals(loteActualizado.getEstado(), EstadoLote.En_Progreso);
    }

    @Test
    @Rollback
    public void testQueAlHacerClickEnAvanzarEstadoEnUnLoteCuyoEstadoEsEnProgresoPaseASerCompletado() {
        Lote lotePrevio = new Lote();
        lotePrevio.setId(1L);
        lotePrevio.setEstado(EstadoLote.En_Progreso);

        Lote loteFinaleEsperado = new Lote();
        loteFinaleEsperado.setId(1L);
        loteFinaleEsperado.setEstado(EstadoLote.Completado);

        LoteViewModel loteViewModel = new LoteViewModel();
        loteViewModel.setId(1L);
        loteViewModel.setEstado(EstadoLote.En_Progreso);

        when(repositorioLote.buscarPorId(anyLong())).thenReturn(lotePrevio);
        when(repositorioLote.modificar(any(Lote.class))).thenReturn(loteFinaleEsperado);

        Lote loteActualizado = servicioLote.avanzarEstado(loteViewModel);
        assertNotNull(loteActualizado);
        assertEquals(loteActualizado.getEstado(), EstadoLote.Completado);
    }

    @Test
    @Rollback
    public void testQueAlHacerClickEnAvanzarEstadoEnUnLoteCuyoEstadoEsCompletadoPermanezcaComoCompletado() {
        Lote lotePrevio = new Lote();
        lotePrevio.setId(1L);
        lotePrevio.setEstado(EstadoLote.Completado);

        Lote loteFinaleEsperado = new Lote();
        loteFinaleEsperado.setId(1L);
        loteFinaleEsperado.setEstado(EstadoLote.Completado);

        LoteViewModel loteViewModel = new LoteViewModel();
        loteViewModel.setId(1L);
        loteViewModel.setEstado(EstadoLote.Completado);

        when(repositorioLote.buscarPorId(anyLong())).thenReturn(lotePrevio);
        when(repositorioLote.modificar(any(Lote.class))).thenReturn(loteFinaleEsperado);

        Lote loteActualizado = servicioLote.avanzarEstado(loteViewModel);
        assertNotNull(loteActualizado);
        assertEquals(loteActualizado.getEstado(), EstadoLote.Completado);
    }

    @Test
    @Rollback
    public void testQueAlCrear2LotesFinalizadosY2LotesEnProgresoYBusqueLosLotesFinalizadosSoloMeDevuelvaLosLotesFinalizados(){
        Lote loteFinalizado1 = new Lote();
        loteFinalizado1.setId(1L);
        loteFinalizado1.setEstado(EstadoLote.Completado);

        Lote loteFinalizado2 = new Lote();
        loteFinalizado2.setId(2L);
        loteFinalizado2.setEstado(EstadoLote.Completado);

        Lote loteEnProgreso1 = new Lote();
        loteEnProgreso1.setId(3L);
        loteEnProgreso1.setEstado(EstadoLote.En_Progreso);

        Lote loteEnProgreso2 = new Lote();
        loteEnProgreso2.setId(4L);
        loteEnProgreso2.setEstado(EstadoLote.En_Progreso);

        List<Lote> lotesFinalizados = List.of(loteFinalizado1, loteFinalizado2);

        when(repositorioLote.buscarLotesFinalizados()).thenReturn(lotesFinalizados);

        List<Lote> lotesObtenidos = servicioLote.obtenerLotesFinalizados();

        verify(repositorioLote, times(1)).buscarLotesFinalizados();
        assertEquals(lotesObtenidos.size(), 2);
    }

    @Test
    @Rollback
    public void testQueAlCrear2LotesEnProgresoY2LotesNoIniciadosYBusqueLosLotesFinalizadosNoMeDevuelvaNingunLote(){
        Lote loteEnProgreso1 = new Lote();
        loteEnProgreso1.setId(1L);
        loteEnProgreso1.setEstado(EstadoLote.En_Progreso);

        Lote loteEnProgreso2 = new Lote();
        loteEnProgreso2.setId(2L);
        loteEnProgreso2.setEstado(EstadoLote.En_Progreso);

        Lote loteNoIniciado1 = new Lote();
        loteNoIniciado1.setId(3L);
        loteNoIniciado1.setEstado(EstadoLote.No_Iniciado);

        Lote loteNoIniciado2 = new Lote();
        loteNoIniciado2.setId(4L);
        loteNoIniciado2.setEstado(EstadoLote.No_Iniciado);

        List<Lote> lotesFinalizados = List.of();

        when(repositorioLote.buscarLotesFinalizados()).thenReturn(lotesFinalizados);

        List<Lote> lotesObtenidos = servicioLote.obtenerLotesFinalizados();

        verify(repositorioLote, times(1)).buscarLotesFinalizados();
        assertEquals(lotesObtenidos.size(), 0);
    }

    @Test
    @Rollback
    public void testQueAlCrear2LotesNoIniciadosY2LotesFinalizadosYBusqueTodosLosLotesMeDevuelvaLos4Lotes(){
        Lote loteFinalizado1 = new Lote();
        loteFinalizado1.setId(1L);
        loteFinalizado1.setEstado(EstadoLote.Completado);

        Lote loteFinalizado2 = new Lote();
        loteFinalizado2.setId(2L);
        loteFinalizado2.setEstado(EstadoLote.Completado);

        Lote loteNoIniciado1 = new Lote();
        loteNoIniciado1.setId(3L);
        loteNoIniciado1.setEstado(EstadoLote.No_Iniciado);

        Lote loteNoIniciado2 = new Lote();
        loteNoIniciado2.setId(4L);
        loteNoIniciado2.setEstado(EstadoLote.No_Iniciado);

        List<Lote> lotesAlmacenados = List.of(loteFinalizado1, loteFinalizado2, loteNoIniciado1, loteNoIniciado2);

        when(repositorioLote.obtenerTodosLosLotes()).thenReturn(lotesAlmacenados);

        List<Lote> lotesObtenidos = servicioLote.obtenerTodosLosLotes();

        verify(repositorioLote, times(1)).obtenerTodosLosLotes();
        assertEquals(lotesObtenidos.size(), 4);
    }


}
