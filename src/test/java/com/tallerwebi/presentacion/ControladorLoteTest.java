package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CodigoExistenteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

public class ControladorLoteTest {

    @Mock
    private ServicioLote servicioLote;

    @InjectMocks
    private ControladorLote controladorLote;

    private LoteViewModel loteDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        loteDTO = new LoteViewModel();
        loteDTO.setId(1L);
        loteDTO.setEstado(EstadoLote.No_Iniciado);
    }

    @Test
    public void testQueAlIntentarCrearUnLoteConIdNoExistenteMeRedirijaALaSiguienteVistaConUnaListaDeLosLotesYaCreadosQueIncluyaElReciénCreado() {
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        when(servicioLote.crearLote(any(LoteViewModel.class))).thenReturn(lote);

        List<Lote> listaLotesCrud = List.of(lote);
        when(servicioLote.obtenerTodosLosLotes()).thenReturn(listaLotesCrud);

        ModelAndView mav = controladorLote.crearLote(loteDTO);
        assertThat(mav.getViewName(), is("lotes"));
        assertThat(mav.getModel(), hasKey("listaLotes"));
        assertThat(mav.getModel(), hasKey("listaLotesFinalizados"));

        List<LoteViewModel> listaLotes = (List<LoteViewModel>) mav.getModel().get("listaLotes");
        List<LoteViewModel> listaLotesFinalizados = (List<LoteViewModel>) mav.getModel().get("listaLotesFinalizados");
        assertThat(listaLotes.size(), is(1));
        assertThat(listaLotes.get(0).getId(), is(1L));
        assertThat(listaLotesFinalizados.size(), is(0));

        verify(servicioLote, times(1)).crearLote(loteDTO);
        verify(servicioLote, times(1)).obtenerTodosLosLotes();
        verify(servicioLote, times(1)).obtenerLotesFinalizados();
    }

    @Test
    public void testQueAlIntentarCrearUnLoteConIdExistenteGenereQueSeMantengaEnLaMismaVistaYSeAgregueUnMensajeDeError() {
        Lote lote = null; // Simula que no se pudo crear el lote porque el ID ya existe
        when(servicioLote.crearLote(any(LoteViewModel.class))).thenThrow(new CodigoExistenteException());

        ModelAndView mav = controladorLote.crearLote(loteDTO);
        assertThat(mav.getViewName(), is("crearLote"));
        assertThat(mav.getModel(), hasKey("error"));
        assertEquals("Código existente en otro lote", mav.getModel().get("error"));
    }

    @Test
    public void testQueAlHacerClickEnElBotónParaAgregarSombrerosAUnLoteMeRedirijaALaVistaDeAgregarSombrerosConElIdDelLoteSeleccionado() {
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        when(servicioLote.obtenerLotePorId(anyLong())).thenReturn(lote);

        ModelAndView mav = controladorLote.irAAgregarSombreros(lote.getId());
        assertThat(mav.getViewName(), is("agregarSombreros"));
        assertThat(mav.getModel(), hasKey("lote"));

        LoteViewModel loteDTO = (LoteViewModel) mav.getModel().get("lote");
        assertThat(loteDTO.getId(), is(1L));
    }

    @Test
    public void testQueLuegoDeAgregarSombrerosALoteMeRedirijaALaVistaConTodosLosLotesActualizada(){
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        Sombrero sombrero = new Sombrero();
        sombrero.setId(1L);
        sombrero.setTipo(TipoSombrero.BEANIE);
        sombrero.setValorProduccion(100.0);

        SombreroViewModel sombreroDTO = new SombreroViewModel();
        sombreroDTO.setId(1L);
        sombreroDTO.setTipo(TipoSombrero.BEANIE);
        sombreroDTO.setValorProduccion(100.0);

        lote.agregarSombrero(sombrero);

        when(servicioLote.agregarSombreros(any(LoteViewModel.class), any(SombreroViewModel.class), anyInt())).thenReturn(lote);
        when(servicioLote.obtenerLotePorId(anyLong())).thenReturn(lote);

        List<Lote> listaLotesCrud = List.of(lote);
        when(servicioLote.obtenerTodosLosLotes()).thenReturn(listaLotesCrud);
        when(servicioLote.obtenerLotesFinalizados()).thenReturn(List.of());

        ModelAndView mav = controladorLote.agregarSombrero(sombreroDTO,1L ,1);

        assertThat(mav.getViewName(), is("lotes"));
        assertThat(mav.getModel(), hasKey("listaLotes"));
        assertThat(mav.getModel(), hasKey("listaLotesFinalizados"));

        List<LoteViewModel> listaLotes = (List<LoteViewModel>) mav.getModel().get("listaLotes");
        List<LoteViewModel> listaLotesFinalizados = (List<LoteViewModel>) mav.getModel().get("listaLotesFinalizados");
        assertThat(listaLotes.size(), is(1));
        assertThat(listaLotes.get(0).getId(), is(1L));
        assertThat(listaLotesFinalizados.size(), is(0));

        verify(servicioLote, times(1)).agregarSombreros(any(LoteViewModel.class), any(SombreroViewModel.class), anyInt());
        verify(servicioLote, times(1)).obtenerTodosLosLotes();
        verify(servicioLote, times(1)).obtenerLotesFinalizados();
    }

    @Test
    public void testQueAlAgregar11SombrerosAUnLoteSeAgreguenCorrectamenteLos11Sombreros(){
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        Sombrero sombrero = new Sombrero();
        sombrero.setId(1L);
        sombrero.setTipo(TipoSombrero.BEANIE);
        sombrero.setValorProduccion(100.0);

        SombreroViewModel sombreroDTO = new SombreroViewModel();
        sombreroDTO.setId(1L);
        sombreroDTO.setTipo(TipoSombrero.BEANIE);
        sombreroDTO.setValorProduccion(100.0);

        for(int i = 0; i < 11; i++) {
            lote.agregarSombrero(sombrero);
        }

        when(servicioLote.agregarSombreros(any(LoteViewModel.class), any(SombreroViewModel.class), anyInt())).thenReturn(lote);
        when(servicioLote.obtenerLotePorId(anyLong())).thenReturn(lote);

        List<Lote> listaLotesCrud = List.of(lote);
        when(servicioLote.obtenerTodosLosLotes()).thenReturn(listaLotesCrud);
        when(servicioLote.obtenerLotesFinalizados()).thenReturn(List.of());

        ModelAndView mav = controladorLote.agregarSombrero(sombreroDTO,1L ,11);

        assertThat(mav.getViewName(), is("lotes"));
        assertThat(mav.getModel(), hasKey("listaLotes"));
        assertThat(mav.getModel(), hasKey("listaLotesFinalizados"));

        List<LoteViewModel> listaLotes = (List<LoteViewModel>) mav.getModel().get("listaLotes");
        List<LoteViewModel> listaLotesFinalizados = (List<LoteViewModel>) mav.getModel().get("listaLotesFinalizados");
        assertThat(listaLotes.size(), is(1));
        assertThat(listaLotes.get(0).getId(), is(1L));
        assertThat(listaLotesFinalizados.size(), is(0));
        assertThat(listaLotes.get(0).getSombreros().size(), is(11));

        verify(servicioLote, times(1)).agregarSombreros(any(LoteViewModel.class), any(SombreroViewModel.class), anyInt());
    }
}
