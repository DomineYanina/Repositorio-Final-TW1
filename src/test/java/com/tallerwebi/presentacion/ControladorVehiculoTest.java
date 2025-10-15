package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ConductorInhabilitadoException;
import com.tallerwebi.dominio.excepcion.VehiculoInhabilitadoException;
import com.tallerwebi.dominio.excepcion.VehiculoYaAsignadoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ControladorVehiculoTest {

    @Mock
    private ServicioVehiculo servicioVehiculo;

    @Mock
    private ServicioConductor servicioConductor;

    @InjectMocks
    private ControladorVehiculo controladorVehiculo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQueAlIntentarAccederAlRegistroDeVehiculosMeLleveALaVistaCorrecta() {
        ModelAndView mav = controladorVehiculo.irARegistrar();
        assertNotNull(mav);
        assertEquals("registrarVehiculo", mav.getViewName());
        assertThat(mav.getModel(), hasKey("vehiculoDTO"));
    }

    @Test
    public void testQueAlRegistrarUnVehiculoMeRedirijaALaVistaCorrectaConUnaListaDeVehiculosHabilitadosYUnaConTodosLosVehiculos(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Marca Ejemplo");
        vehiculo.setPatente("Patente Ejemplo");

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setMarca("Marca Ejemplo");
        vehiculoViewModel.setPatente("Patente Ejemplo");

        when(servicioVehiculo.crear(any(VehiculoViewModel.class))).thenReturn(vehiculo);
        when(servicioVehiculo.buscarTodosHabilitados()).thenReturn(Collections.singletonList(vehiculo));
        when(servicioVehiculo.buscarTodos()).thenReturn(Collections.singletonList(vehiculo));

        ModelAndView mav = controladorVehiculo.registrar(vehiculoViewModel);
        assertNotNull(mav);
        assertEquals("listarVehiculos", mav.getViewName());
        assertThat(mav.getModel(), hasKey("vehiculosHabilitados"));
        assertThat(mav.getModel(), hasKey("todosLosVehiculos"));

        assertEquals(1, ((java.util.List<Vehiculo>) mav.getModel().get("vehiculosHabilitados")).size());
        assertEquals(1, ((java.util.List<Vehiculo>) mav.getModel().get("todosLosVehiculos")).size());
    }

    @Test
    public void testQueAlTratarDeAsignarConductorAUnVehiculoQueYaTieneConductorNoLoAsigneYMeMuestreError(){
        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setNombre("Nombre Ejemplo");
        conductor.setSexo("Femenino");
        conductor.setNacionalidad("Nacionalidad Ejemplo");
        conductor.setEstado(EstadoConductor.Habilitado);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Marca Ejemplo");
        vehiculo.setPatente("Patente Ejemplo");
        vehiculo.setConductor(conductor);
        vehiculo.setEstado(EstadoVehiculo.Habilitado);

        Conductor conductorNuevo = new Conductor();
        conductorNuevo.setId(2L);
        conductorNuevo.setNombre("Nombre Ejemplo 2");
        conductorNuevo.setSexo("Masculino");
        conductorNuevo.setNacionalidad("Nacionalidad Ejemplo 2");
        conductorNuevo.setEstado(EstadoConductor.Habilitado);

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(1L);
        vehiculoViewModel.setConductor(2L);
        vehiculoViewModel.setMarca("Marca Ejemplo 2");
        vehiculoViewModel.setPatente("Patente Ejemplo 2");
        vehiculoViewModel.setEstado(EstadoVehiculo.Habilitado);

        when(servicioVehiculo.buscarPorId(any(Long.class))).thenReturn(vehiculo);
        when(servicioConductor.buscarPorId(1L)).thenReturn(conductor);
        when(servicioConductor.buscarPorId(2L)).thenReturn(conductorNuevo);
        when(servicioVehiculo.asignarConductor(any(VehiculoViewModel.class))).thenThrow(new VehiculoYaAsignadoException());

        ModelAndView mav = controladorVehiculo.asignarConductor(vehiculoViewModel);

        assertNotNull(mav);
        assertEquals("listarVehiculos", mav.getViewName());
        assertThat(mav.getModel(), hasKey("vehiculosHabilitados"));
        assertThat(mav.getModel(), hasKey("todosLosVehiculos"));
        assertThat(mav.getModel(), hasKey("error"));
        assertEquals("Este vehículo ya tiene un conductor asignado", mav.getModel().get("error"));
    }

    @Test
    public void testQueAlIntentarAsignarleUnConductorInhabilitadoAUnVehiculoHabilitadoNoSeAsigneYMeDeError(){
        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setNombre("Nombre Ejemplo");
        conductor.setSexo("Femenino");
        conductor.setNacionalidad("Nacionalidad Ejemplo");
        conductor.setEstado(EstadoConductor.Inhabilitado);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Marca Ejemplo");
        vehiculo.setPatente("Patente Ejemplo");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(1L);
        vehiculoViewModel.setConductor(1L);
        vehiculoViewModel.setMarca("Marca Ejemplo");
        vehiculoViewModel.setPatente("Patente Ejemplo");

        when(servicioVehiculo.buscarPorId(any(Long.class))).thenReturn(vehiculo);
        when(servicioConductor.buscarPorId(any(Long.class))).thenReturn(conductor);
        when(servicioVehiculo.asignarConductor(any(VehiculoViewModel.class))).thenThrow(new ConductorInhabilitadoException());

        ModelAndView mav = controladorVehiculo.asignarConductor(vehiculoViewModel);

        assertNotNull(mav);
        assertEquals("listarVehiculos", mav.getViewName());
        assertThat(mav.getModel(), hasKey("vehiculosHabilitados"));
        assertThat(mav.getModel(), hasKey("todosLosVehiculos"));
        assertThat(mav.getModel(), hasKey("error"));
        assertEquals("El conductor seleccionado está inhabilitado", mav.getModel().get("error"));
    }

    @Test
    public void testQueAlIntentarAsignarleUnConductorHabilitadoAUnVehiculoInhabilitadoNoSeAsigneYMeDeError(){
        Conductor conductor = new Conductor();
        conductor.setId(1L);
        conductor.setNombre("Nombre Ejemplo");
        conductor.setSexo("Femenino");
        conductor.setNacionalidad("Nacionalidad Ejemplo");
        conductor.setEstado(EstadoConductor.Habilitado);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(1L);
        vehiculo.setMarca("Marca Ejemplo");
        vehiculo.setPatente("Patente Ejemplo");
        vehiculo.setEstado(EstadoVehiculo.Inhabilitado);

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(1L);
        vehiculoViewModel.setConductor(1L);
        vehiculoViewModel.setMarca("Marca Ejemplo");
        vehiculoViewModel.setPatente("Patente Ejemplo");

        when(servicioVehiculo.buscarPorId(any(Long.class))).thenReturn(vehiculo);
        when(servicioConductor.buscarPorId(any(Long.class))).thenReturn(conductor);
        when(servicioVehiculo.asignarConductor(any(VehiculoViewModel.class))).thenThrow(new VehiculoInhabilitadoException());

        ModelAndView mav = controladorVehiculo.asignarConductor(vehiculoViewModel);

        assertNotNull(mav);
        assertEquals("listarVehiculos", mav.getViewName());
        assertThat(mav.getModel(), hasKey("vehiculosHabilitados"));
        assertThat(mav.getModel(), hasKey("todosLosVehiculos"));
        assertThat(mav.getModel(), hasKey("error"));
        assertEquals("El vehículo seleccionado está inhabilitado", mav.getModel().get("error"));
    }
}
