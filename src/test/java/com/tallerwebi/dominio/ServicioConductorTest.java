package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ConductorViewModel;
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
import static org.mockito.Mockito.when;

public class ServicioConductorTest {

    @Mock
    private RepositorioConductor repositorioConductor;

    @InjectMocks
    private ServicioConductorImpl servicioConductor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearCorrectamenteUnConductorConElEstadoHabilitadoPorDefecto() {
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");
        conductor.setEstado(EstadoConductor.Habilitado);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setNombre("Juan Perez");
        conductorViewModel.setEdad(50);
        conductorViewModel.setSexo("Masculino");
        conductorViewModel.setCedula("123ABC");
        conductorViewModel.setId(1L);

        when(repositorioConductor.crear(any(Conductor.class))).thenReturn(conductor);

        Conductor conductorCreado = servicioConductor.crear(conductorViewModel);

        assertNotNull(conductorCreado);
        assertEquals("Juan Perez", conductorCreado.getNombre());
        assertEquals(EstadoConductor.Habilitado, conductorCreado.getEstado());
        assertEquals(50, conductorCreado.getEdad());
        assertEquals("Masculino", conductorCreado.getSexo());
        assertEquals("123ABC", conductorCreado.getCedula());
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeHabilitadoAInhabilitadoCorrectamente(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");
        conductor.setEstado(EstadoConductor.Habilitado);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setNombre("Juan Perez");
        conductorViewModel.setEdad(50);
        conductorViewModel.setSexo("Masculino");
        conductorViewModel.setCedula("123ABC");
        conductorViewModel.setId(1L);

        when(repositorioConductor.crear(any(Conductor.class))).thenReturn(conductor);

        Conductor conductorCreado = servicioConductor.crear(conductorViewModel);
        when(repositorioConductor.buscar(anyLong())).thenReturn(conductorCreado);

        conductorCreado.setEstado(EstadoConductor.Inhabilitado);

        when(repositorioConductor.modificar(any(Conductor.class))).thenReturn(conductorCreado);

        conductorViewModel.setEstado(EstadoConductor.Inhabilitado);

        Conductor conductorModificado = servicioConductor.modificar(conductorViewModel);

        assertNotNull(conductorModificado);
        assertEquals(EstadoConductor.Inhabilitado, conductorModificado.getEstado());
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeInhabilitadoAHabilitadoCorrectamente(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");
        conductor.setEstado(EstadoConductor.Inhabilitado);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setNombre("Juan Perez");
        conductorViewModel.setEdad(50);
        conductorViewModel.setSexo("Masculino");
        conductorViewModel.setCedula("123ABC");
        conductorViewModel.setId(1L);

        when(repositorioConductor.crear(any(Conductor.class))).thenReturn(conductor);

        Conductor conductorCreado = servicioConductor.crear(conductorViewModel);
        when(repositorioConductor.buscar(anyLong())).thenReturn(conductorCreado);

        conductorCreado.setEstado(EstadoConductor.Habilitado);

        when(repositorioConductor.modificar(any(Conductor.class))).thenReturn(conductorCreado);

        conductorViewModel.setEstado(EstadoConductor.Habilitado);

        Conductor conductorModificado = servicioConductor.modificar(conductorViewModel);

        assertNotNull(conductorModificado);
        assertEquals(EstadoConductor.Habilitado, conductorModificado.getEstado());
    }

    @Test
    @Rollback
    public void testQueSiTengoAlmacenados3ConductoresHabilitadosY1InhabilitadoYSolicitoLosHabilitadosMeDevuelvaSoloLos3Habilitados(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH3 = new Conductor();
        conductorH3.setNombre("Conductor Habilitado 3");
        conductorH3.setId(3L);
        conductorH3.setEdad(45);
        conductorH3.setSexo("Masculino");
        conductorH3.setCedula("HAB3");
        conductorH3.setEstado(EstadoConductor.Habilitado);

        Conductor conductorI1 = new Conductor();
        conductorI1.setNombre("Conductor Inhabilitado 1");
        conductorI1.setId(4L);
        conductorI1.setEdad(50);
        conductorI1.setSexo("Femenino");
        conductorI1.setCedula("INH1");
        conductorI1.setEstado(EstadoConductor.Inhabilitado);

        when(repositorioConductor.crear(conductorH1)).thenReturn(conductorH1);
        when(repositorioConductor.crear(conductorH2)).thenReturn(conductorH2);
        when(repositorioConductor.crear(conductorH3)).thenReturn(conductorH3);
        when(repositorioConductor.crear(conductorI1)).thenReturn(conductorI1);

        List<Conductor> conductoresHabilitados = List.of(conductorH1, conductorH2, conductorH3);
        when(repositorioConductor.buscarHabilitados()).thenReturn(conductoresHabilitados);

        List<Conductor> resultado = servicioConductor.buscarTodosHabilitados();
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals(conductoresHabilitados.get(0).getId(), resultado.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengoAlmacenados3ConductoresHabilitadosY1InhabilitadoYSolicitoLosInhabilitadosMeDevuelvaSoloElInhabilitados(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH3 = new Conductor();
        conductorH3.setNombre("Conductor Habilitado 3");
        conductorH3.setId(3L);
        conductorH3.setEdad(45);
        conductorH3.setSexo("Masculino");
        conductorH3.setCedula("HAB3");
        conductorH3.setEstado(EstadoConductor.Habilitado);

        Conductor conductorI1 = new Conductor();
        conductorI1.setNombre("Conductor Inhabilitado 1");
        conductorI1.setId(4L);
        conductorI1.setEdad(50);
        conductorI1.setSexo("Femenino");
        conductorI1.setCedula("INH1");
        conductorI1.setEstado(EstadoConductor.Inhabilitado);

        when(repositorioConductor.crear(conductorH1)).thenReturn(conductorH1);
        when(repositorioConductor.crear(conductorH2)).thenReturn(conductorH2);
        when(repositorioConductor.crear(conductorH3)).thenReturn(conductorH3);
        when(repositorioConductor.crear(conductorI1)).thenReturn(conductorI1);

        List<Conductor> conductoresInhabilitados = List.of(conductorI1);
        when(repositorioConductor.buscarInhabilitados()).thenReturn(conductoresInhabilitados);

        List<Conductor> resultado = servicioConductor.buscarTodosInhabilitados();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(conductoresInhabilitados.get(0).getId(), resultado.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengoAlmacenados3ConductoresHabilitadosY1InhabilitadoYSolicitoTodosMeDevuelvaLos4(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH3 = new Conductor();
        conductorH3.setNombre("Conductor Habilitado 3");
        conductorH3.setId(3L);
        conductorH3.setEdad(45);
        conductorH3.setSexo("Masculino");
        conductorH3.setCedula("HAB3");
        conductorH3.setEstado(EstadoConductor.Habilitado);

        Conductor conductorI1 = new Conductor();
        conductorI1.setNombre("Conductor Inhabilitado 1");
        conductorI1.setId(4L);
        conductorI1.setEdad(50);
        conductorI1.setSexo("Femenino");
        conductorI1.setCedula("INH1");
        conductorI1.setEstado(EstadoConductor.Inhabilitado);

        when(repositorioConductor.crear(conductorH1)).thenReturn(conductorH1);
        when(repositorioConductor.crear(conductorH2)).thenReturn(conductorH2);
        when(repositorioConductor.crear(conductorH3)).thenReturn(conductorH3);
        when(repositorioConductor.crear(conductorI1)).thenReturn(conductorI1);

        List<Conductor> conductores = List.of(conductorH1, conductorH2, conductorH3, conductorI1);
        when(repositorioConductor.buscarTodos()).thenReturn(conductores);

        List<Conductor> resultado = servicioConductor.buscarTodos();
        assertNotNull(resultado);
        assertEquals(4, resultado.size());
        assertEquals(conductores.get(0).getId(), resultado.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengo2ConductoresHabilitadosYSolicitoLosInhabilitadosMeDevuelvaUnaListaVacia(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Habilitado);

        when(repositorioConductor.crear(conductorH1)).thenReturn(conductorH1);
        when(repositorioConductor.crear(conductorH2)).thenReturn(conductorH2);

        List<Conductor> conductoresInhabilitados = List.of();
        when(repositorioConductor.buscarInhabilitados()).thenReturn(conductoresInhabilitados);

        List<Conductor> resultado = servicioConductor.buscarTodosInhabilitados();
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    @Rollback
    public void testQueSiTengo2ConductoresInhabilitadosYSolicitoLosHabilitadosMeDevuelvaUnaListaVacia(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Inhabilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Inhabilitado);

        when(repositorioConductor.crear(conductorH1)).thenReturn(conductorH1);
        when(repositorioConductor.crear(conductorH2)).thenReturn(conductorH2);

        List<Conductor> conductoresHabilitados = List.of();
        when(repositorioConductor.buscarHabilitados()).thenReturn(conductoresHabilitados);

        List<Conductor> resultado = servicioConductor.buscarTodosHabilitados();
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

}
