package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ConductorViewModel;
import com.tallerwebi.presentacion.VehiculoViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ServicioVehiculoTest {
    @Mock
    private RepositorioVehiculo repositorioVehiculo;
    @Mock
    private RepositorioConductor repositorioConductor;

    @InjectMocks
    private ServicioVehiculoImpl servicioVehiculo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearUnVehiculo() {
        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setMarca("Toyota");
        vehiculoViewModel.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoViewModel.getMarca());
        vehiculo.setEstado(vehiculoViewModel.getEstado());

        when(repositorioVehiculo.crear(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo vehiculoCreado = servicioVehiculo.crear(vehiculoViewModel);

        assertNotNull(vehiculoCreado);
        assertEquals("Toyota", vehiculoCreado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoCreado.getEstado());
    }

    @Test
    @Rollback
    public void testQueAlModificarUnVehiculoLosDatosSeActualicenCorrectamente(){
        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setMarca("Toyota");
        vehiculoViewModel.setEstado(EstadoVehiculo.Habilitado);
        vehiculoViewModel.setId(1L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoViewModel.getMarca());
        vehiculo.setEstado(vehiculoViewModel.getEstado());
        vehiculo.setId(1L);

        when(repositorioVehiculo.crear(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo vehiculoCreado = servicioVehiculo.crear(vehiculoViewModel);

        assertNotNull(vehiculoCreado);
        assertEquals("Toyota", vehiculoCreado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoCreado.getEstado());

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculoCreado);

        // Modificar los datos del vehículo
        vehiculoCreado.setMarca("Honda");
        vehiculoCreado.setEstado(EstadoVehiculo.Inhabilitado);

        when(repositorioVehiculo.modificar(any(Vehiculo.class))).thenReturn(vehiculoCreado);

        VehiculoViewModel vehiculoAModificar = new VehiculoViewModel();
        vehiculoAModificar.setMarca("Honda");
        vehiculoAModificar.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculoAModificar.setId(vehiculoCreado.getId());

        Vehiculo vehiculoModificado = servicioVehiculo.modificar(vehiculoAModificar);

        assertNotNull(vehiculoModificado);
        assertEquals("Honda", vehiculoModificado.getMarca());
        assertEquals(EstadoVehiculo.Inhabilitado, vehiculoModificado.getEstado());
    }

    @Test
    @Rollback
    public void testQueNoMePermitaAsignarUnConductorInhabilitadoAUnVehiculoHabilitado(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Inhabilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setId(conductor.getId());
        conductorViewModel.setNombre(conductor.getNombre());
        conductorViewModel.setEdad(conductor.getEdad());
        conductorViewModel.setEstado(conductor.getEstado());
        conductorViewModel.setSexo(conductor.getSexo());
        conductorViewModel.setCedula(conductor.getCedula());

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(vehiculo.getId());
        vehiculoViewModel.setMarca(vehiculo.getMarca());
        vehiculoViewModel.setEstado(vehiculo.getEstado());
        vehiculoViewModel.setConductor(conductorViewModel.getId());

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculo);
        when(repositorioConductor.buscar(any(Long.class))).thenReturn(conductor);

        Vehiculo vehiculoModificado = servicioVehiculo.asignarConductor(vehiculoViewModel);

        assertNull(vehiculoModificado);
    }

    @Test
    @Rollback
    public void testQueNoMePermitaAsignarUnConductorHabilitadoAUnVehiculoInhabilitado(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Habilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculo.setId(1L);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setId(conductor.getId());
        conductorViewModel.setNombre(conductor.getNombre());
        conductorViewModel.setEdad(conductor.getEdad());
        conductorViewModel.setEstado(conductor.getEstado());
        conductorViewModel.setSexo(conductor.getSexo());
        conductorViewModel.setCedula(conductor.getCedula());

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(vehiculo.getId());
        vehiculoViewModel.setMarca(vehiculo.getMarca());
        vehiculoViewModel.setEstado(vehiculo.getEstado());
        vehiculoViewModel.setConductor(conductorViewModel.getId());

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculo);
        when(repositorioConductor.buscar(any(Long.class))).thenReturn(conductor);

        Vehiculo vehiculoModificado = servicioVehiculo.asignarConductor(vehiculoViewModel);

        assertNull(vehiculoModificado);
    }

    @Test
    @Rollback
    public void testQueMePermitaAsignarUnConductorHabilitadoAUnVehiculoHabilitado(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Habilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculoModificado1 = new Vehiculo();
        vehiculoModificado1.setId(vehiculo.getId());
        vehiculoModificado1.setMarca(vehiculo.getMarca());
        vehiculoModificado1.setEstado(vehiculo.getEstado());
        vehiculoModificado1.setConductor(conductor);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setId(conductor.getId());
        conductorViewModel.setNombre(conductor.getNombre());
        conductorViewModel.setEdad(conductor.getEdad());
        conductorViewModel.setEstado(conductor.getEstado());
        conductorViewModel.setSexo(conductor.getSexo());
        conductorViewModel.setCedula(conductor.getCedula());

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(vehiculo.getId());
        vehiculoViewModel.setMarca(vehiculo.getMarca());
        vehiculoViewModel.setEstado(vehiculo.getEstado());
        vehiculoViewModel.setConductor(conductorViewModel.getId());

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculo);
        when(repositorioConductor.buscar(any(Long.class))).thenReturn(conductor);
        when(repositorioVehiculo.modificar(any(Vehiculo.class))).thenReturn(vehiculoModificado1);

        Vehiculo vehiculoModificado = servicioVehiculo.asignarConductor(vehiculoViewModel);

        assertNotNull(vehiculoModificado);
        assertEquals(conductor, vehiculoModificado.getConductor());
        assertEquals(vehiculo.getMarca(), vehiculoModificado.getMarca());
        assertEquals(vehiculo.getEstado(), vehiculoModificado.getEstado());
    }

    @Test
    @Rollback
    public void testQueNoMePermitaAsignarleUnConductorAUnVehiculoQueYaTieneUnConductorAsignado(){
        Conductor conductor1 = new Conductor();
        conductor1.setNombre("Juan Perez");
        conductor1.setId(1L);
        conductor1.setEdad(50);
        conductor1.setEstado(EstadoConductor.Habilitado);
        conductor1.setSexo("Masculino");
        conductor1.setCedula("123ABC");

        Conductor conductor2 = new Conductor();
        conductor2.setNombre("Carlos Lopez");
        conductor2.setId(2L);
        conductor2.setEdad(40);
        conductor2.setEstado(EstadoConductor.Habilitado);
        conductor2.setSexo("Masculino");
        conductor2.setCedula("456DEF");

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);
        vehiculo.setConductor(conductor1);

        ConductorViewModel conductorViewModel = new ConductorViewModel();
        conductorViewModel.setId(conductor2.getId());
        conductorViewModel.setNombre(conductor2.getNombre());
        conductorViewModel.setEdad(conductor2.getEdad());
        conductorViewModel.setEstado(conductor2.getEstado());
        conductorViewModel.setSexo(conductor2.getSexo());
        conductorViewModel.setCedula(conductor2.getCedula());

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setId(vehiculo.getId());
        vehiculoViewModel.setMarca(vehiculo.getMarca());
        vehiculoViewModel.setEstado(vehiculo.getEstado());
        vehiculoViewModel.setConductor(conductorViewModel.getId());

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculo);
        when(repositorioConductor.buscar(2L)).thenReturn(conductor2);
        when(repositorioConductor.buscar(1L)).thenReturn(conductor1);

        Vehiculo vehiculoModificado = servicioVehiculo.asignarConductor(vehiculoViewModel);

        assertNull(vehiculoModificado);
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnVehiculoDeInhabilitadoAHabilitado(){
        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setMarca("Toyota");
        vehiculoViewModel.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculoViewModel.setId(1L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoViewModel.getMarca());
        vehiculo.setEstado(vehiculoViewModel.getEstado());
        vehiculo.setId(1L);

        when(repositorioVehiculo.crear(any(Vehiculo.class))).thenReturn(vehiculo);

        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculoViewModel.setEstado(EstadoVehiculo.Habilitado);

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculo);
        when(repositorioVehiculo.modificar(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo vehiculoModificado = servicioVehiculo.modificar(vehiculoViewModel);

        assertNotNull(vehiculoModificado);
        assertEquals(vehiculoModificado.getEstado(), EstadoVehiculo.Habilitado);
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnVehiculoDeHabilitadoAInhabilitado(){
        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setMarca("Toyota");
        vehiculoViewModel.setEstado(EstadoVehiculo.Habilitado);
        vehiculoViewModel.setId(1L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoViewModel.getMarca());
        vehiculo.setEstado(vehiculoViewModel.getEstado());
        vehiculo.setId(1L);

        when(repositorioVehiculo.crear(any(Vehiculo.class))).thenReturn(vehiculo);

        vehiculo.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculoViewModel.setEstado(EstadoVehiculo.Inhabilitado);

        when(repositorioVehiculo.buscar(any(Long.class))).thenReturn(vehiculo);
        when(repositorioVehiculo.modificar(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo vehiculoModificado = servicioVehiculo.modificar(vehiculoViewModel);

        assertNotNull(vehiculoModificado);
        assertEquals(vehiculoModificado.getEstado(), EstadoVehiculo.Inhabilitado);
    }

    @Test
    @Rollback
    public void testQueAlCrearUnVehiculoPorDefectoSeLeAsigneElEstadoHabilitado(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setId(1L);
        vehiculo.setEstado(EstadoVehiculo.Habilitado);

        VehiculoViewModel vehiculoViewModel = new VehiculoViewModel();
        vehiculoViewModel.setMarca(vehiculo.getMarca());
        vehiculoViewModel.setId(vehiculo.getId());

        when(repositorioVehiculo.crear(any(Vehiculo.class))).thenReturn(vehiculo);

        Vehiculo vehiculoCreado = servicioVehiculo.crear(vehiculoViewModel);
        assertNotNull(vehiculoCreado);
        assertEquals("Toyota", vehiculoCreado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoCreado.getEstado());
    }

    @Test
    @Rollback
    public void testQueSiTengoAlmacenados3VehiculosHabilitadosY1InhabilitadoYSolicitoLosHabilitadosMeDevuelvaSoloLos3Habilitados(){
        Vehiculo vehiculoH1 = new Vehiculo();
        vehiculoH1.setMarca("Toyota");
        vehiculoH1.setId(1L);
        vehiculoH1.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH2 = new Vehiculo();
        vehiculoH2.setMarca("Honda");
        vehiculoH2.setId(2L);
        vehiculoH2.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH3 = new Vehiculo();
        vehiculoH3.setMarca("Ford");
        vehiculoH3.setId(3L);
        vehiculoH3.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoI1 = new Vehiculo();
        vehiculoI1.setMarca("Chevrolet");
        vehiculoI1.setId(4L);
        vehiculoI1.setEstado(EstadoVehiculo.Inhabilitado);

        when(repositorioVehiculo.crear(vehiculoH1)).thenReturn(vehiculoH1);
        when(repositorioVehiculo.crear(vehiculoH2)).thenReturn(vehiculoH2);
        when(repositorioVehiculo.crear(vehiculoH3)).thenReturn(vehiculoH3);
        when(repositorioVehiculo.crear(vehiculoI1)).thenReturn(vehiculoI1);

        List<Vehiculo> vehiculosHabilitados = List.of(vehiculoH1, vehiculoH2, vehiculoH3);
        when(repositorioVehiculo.buscarHabilitados()).thenReturn(vehiculosHabilitados);

        List<Vehiculo> vehiculosEncontrados = servicioVehiculo.buscarTodosHabilitados();

        assertEquals(vehiculosEncontrados.size(), 3);
        assertEquals(vehiculosHabilitados.get(0).getId(), vehiculosEncontrados.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengoAlmacenados3VehiculosHabilitadosY1InhabilitadoYSolicitoLosInhabilitadosMeDevuelvaSoloElInhabilitado(){
        Vehiculo vehiculoH1 = new Vehiculo();
        vehiculoH1.setMarca("Toyota");
        vehiculoH1.setId(1L);
        vehiculoH1.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH2 = new Vehiculo();
        vehiculoH2.setMarca("Honda");
        vehiculoH2.setId(2L);
        vehiculoH2.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH3 = new Vehiculo();
        vehiculoH3.setMarca("Ford");
        vehiculoH3.setId(3L);
        vehiculoH3.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoI1 = new Vehiculo();
        vehiculoI1.setMarca("Chevrolet");
        vehiculoI1.setId(4L);
        vehiculoI1.setEstado(EstadoVehiculo.Inhabilitado);

        when(repositorioVehiculo.crear(vehiculoH1)).thenReturn(vehiculoH1);
        when(repositorioVehiculo.crear(vehiculoH2)).thenReturn(vehiculoH2);
        when(repositorioVehiculo.crear(vehiculoH3)).thenReturn(vehiculoH3);
        when(repositorioVehiculo.crear(vehiculoI1)).thenReturn(vehiculoI1);

        List<Vehiculo> vehiculoInhabilitados = List.of(vehiculoI1);
        when(repositorioVehiculo.buscarInhabilitados()).thenReturn(vehiculoInhabilitados);

        List<Vehiculo> vehiculosEncontrados = servicioVehiculo.buscarTodosInhabilitados();

        assertEquals(vehiculosEncontrados.size(), 1);
        assertEquals(vehiculoInhabilitados.get(0).getId(), vehiculosEncontrados.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengoAlmacenados3VehiculosHabilitadosY1InhabilitadoYSolicitoTodosMeDevuelvaLos4Vehiculos(){
        Vehiculo vehiculoH1 = new Vehiculo();
        vehiculoH1.setMarca("Toyota");
        vehiculoH1.setId(1L);
        vehiculoH1.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH2 = new Vehiculo();
        vehiculoH2.setMarca("Honda");
        vehiculoH2.setId(2L);
        vehiculoH2.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH3 = new Vehiculo();
        vehiculoH3.setMarca("Ford");
        vehiculoH3.setId(3L);
        vehiculoH3.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoI1 = new Vehiculo();
        vehiculoI1.setMarca("Chevrolet");
        vehiculoI1.setId(4L);
        vehiculoI1.setEstado(EstadoVehiculo.Inhabilitado);

        when(repositorioVehiculo.crear(vehiculoH1)).thenReturn(vehiculoH1);
        when(repositorioVehiculo.crear(vehiculoH2)).thenReturn(vehiculoH2);
        when(repositorioVehiculo.crear(vehiculoH3)).thenReturn(vehiculoH3);
        when(repositorioVehiculo.crear(vehiculoI1)).thenReturn(vehiculoI1);

        List<Vehiculo> vehiculos = List.of(vehiculoH1, vehiculoH2, vehiculoH3, vehiculoI1);
        when(repositorioVehiculo.buscarTodos()).thenReturn(vehiculos);

        List<Vehiculo> vehiculosEncontrados = servicioVehiculo.buscarTodos();

        assertEquals(vehiculosEncontrados.size(), 4);
        assertEquals(vehiculos.get(0).getId(), vehiculosEncontrados.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengo2VehiculosHabilitadosYSolicitoLosInhabilitadosMeDevuelvaUnaListaVacia(){
        Vehiculo vehiculoH1 = new Vehiculo();
        vehiculoH1.setMarca("Toyota");
        vehiculoH1.setId(1L);
        vehiculoH1.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoH2 = new Vehiculo();
        vehiculoH2.setMarca("Honda");
        vehiculoH2.setId(2L);
        vehiculoH2.setEstado(EstadoVehiculo.Habilitado);

        when(repositorioVehiculo.crear(vehiculoH1)).thenReturn(vehiculoH1);
        when(repositorioVehiculo.crear(vehiculoH2)).thenReturn(vehiculoH2);

        List<Vehiculo> vehiculos = List.of();
        when(repositorioVehiculo.buscarInhabilitados()).thenReturn(vehiculos);

        List<Vehiculo> vehiculosEncontrados = servicioVehiculo.buscarTodosInhabilitados();
        assertTrue(vehiculosEncontrados.isEmpty());
    }

    @Test
    @Rollback
    public void testQueSiTengo2VehiculosInhabilitadosYSolicitoLosHabilitadosMeDevuelvaUnaListaVacia(){
        Vehiculo vehiculoH1 = new Vehiculo();
        vehiculoH1.setMarca("Toyota");
        vehiculoH1.setId(1L);
        vehiculoH1.setEstado(EstadoVehiculo.Inhabilitado);

        Vehiculo vehiculoH2 = new Vehiculo();
        vehiculoH2.setMarca("Honda");
        vehiculoH2.setId(2L);
        vehiculoH2.setEstado(EstadoVehiculo.Inhabilitado);

        when(repositorioVehiculo.crear(vehiculoH1)).thenReturn(vehiculoH1);
        when(repositorioVehiculo.crear(vehiculoH2)).thenReturn(vehiculoH2);

        List<Vehiculo> vehiculos = List.of();
        when(repositorioVehiculo.buscarHabilitados()).thenReturn(vehiculos);

        List<Vehiculo> vehiculosEncontrados = servicioVehiculo.buscarTodosHabilitados();
        assertTrue(vehiculosEncontrados.isEmpty());
    }
}
