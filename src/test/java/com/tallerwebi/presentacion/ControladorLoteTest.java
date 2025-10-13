package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.ServicioLote;
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

        List<LoteViewModel> listaLotes = (List<LoteViewModel>) mav.getModel().get("listaLotes");
        assertThat(listaLotes.size(), is(1));
        assertThat(listaLotes.get(0).getId(), is(1L));

        verify(servicioLote, times(1)).crearLote(loteDTO);
        verify(servicioLote, times(1)).obtenerTodosLosLotes();
    }

    @Test
    public void testQueAlIntentarCrearUnLoteConIdExistenteGenereQueSeMantengaEnLaMismaVistaYSeAgregueUnMensajeDeError() {
        Lote lote = null; // Simula que no se pudo crear el lote porque el ID ya existe
        when(servicioLote.crearLote(any(LoteViewModel.class))).thenReturn(lote);

        ModelAndView mav = controladorLote.crearLote(loteDTO);
        assertThat(mav.getViewName(), is("crearLote"));
        assertThat(mav.getModel(), hasKey("error"));
    }
}
