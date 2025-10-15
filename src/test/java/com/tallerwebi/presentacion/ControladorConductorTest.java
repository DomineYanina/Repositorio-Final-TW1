package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ControladorConductorTest {

    @Mock
    private ServicioConductor servicioConductor;

    @InjectMocks
    private ControladorConductor controladorConductor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQueAlIntentarAccederAlRegistroDeConductoresMeLleveALaVistaCorrecta() {
        ModelAndView mav = controladorConductor.irARegistrar();
        assertNotNull(mav);
        assertEquals("registrarConductor", mav.getViewName());
        assertThat(mav.getModel(), hasKey("conductorDTO"));
    }

    @Test
    public void testQueAlRegistrarUnConductorMeRedirijaALaVistaCorrectaConUnaListaDeConductoresHabilitadosYUnaConTodosLosConductores(){
        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setNombre("Nombre Ejemplo");
        conductor.setSexo("Femenino");
        conductor.setNacionalidad("Nacionalidad Ejemplo");

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setNombre("Nombre Ejemplo");
        conductorViewModel.setSexo("Femenino");
        conductorViewModel.setNacionalidad("Nacionalidad Ejemplo");

        when(servicioConductor.crear(any(ConductorViewModel.class))).thenReturn(conductor);

        List<Conductor> listaConductor = Collections.singletonList(conductor);

        when(servicioConductor.buscarTodosHabilitados()).thenReturn(listaConductor);
        when(servicioConductor.buscarTodos()).thenReturn(listaConductor);

        ModelAndView mav = controladorConductor.registrar(conductorViewModel);
        assertNotNull(mav);
        assertEquals("listarConductores", mav.getViewName());
        assertThat(mav.getModel(), hasKey("conductoresHabilitados"));
        assertThat(mav.getModel(), hasKey("todosLosConductores"));
        verify(servicioConductor, times(1)).crear(any(ConductorViewModel.class));
        verify(servicioConductor, times(1)).buscarTodosHabilitados();
        verify(servicioConductor, times(1)).buscarTodos();

        List<Conductor> conductoresAdjuntados = (List<Conductor>) mav.getModel().get("conductoresHabilitados");
        List<Conductor> todosLosConductoresAdjuntados = (List<Conductor>) mav.getModel().get("todosLosConductores");
        assertNotNull(conductoresAdjuntados);
        assertNotNull(todosLosConductoresAdjuntados);
        assertEquals(1, conductoresAdjuntados.size());
        assertEquals(1, todosLosConductoresAdjuntados.size());
    }

}
